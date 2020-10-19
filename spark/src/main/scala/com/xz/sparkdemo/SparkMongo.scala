package com.xz.sparkdemo

import org.apache.log4j.Logger
import org.apache.spark.sql._
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object SparkMongo {

  val projectId="431000-923a0d14be0244eb8c509ce0a57684bb"
  val mongoDbHost="mongodb://10.10.22.101:30000,10.10.22.102:30000,10.10.22.126:30000"

  def sparkMain(args: Array[String]): Unit = {

    try {
      //获取参数 开发模式
      val isDev = if (args.length >= 1) args(0) == "dev" else false

      //初始化spark
      val spark = if (isDev) {
        System.setProperty("hadoop.home.dir", "D:\\bigdata\\hadoop-2.7.3")

        SparkSession.builder().appName("Sparkdemo")
          //.config("spark.some.config.option", "some-value")
          .config("spark.debug.maxToStringFields", "100")
          .master("local[*]")
          //.enableHiveSupport()
          .getOrCreate()
      } else {
        SparkSession.builder().appName("spark-demo")
          //.config("spark.some.config.option", "some-value")
          .enableHiveSupport()
          .getOrCreate()
      }

      spark.sparkContext.setCheckpointDir("hdfs://master122:8020/tmp/engine/tmpCheckpoint/")

      import spark.implicits._

      //1.cms查询答题卡信息 QueryQuestionByProject
      val questList=CmsAppAuthClient.queryQuestionByProject(projectId)

      val allQuestInfoDataset:Dataset[Row]=spark.createDataFrame(questList,classOf[QuestCardInfo]).withColumnRenamed("paperQuestNum","questionNo").withColumnRenamed("score","questionFullScore")

      allQuestInfoDataset.persist()

      val objectiveQuestInfoDataset=allQuestInfoDataset.filter("subObjTag=='o'").drop("subObjTag")
      val subjectiveQuestInfoDataset=allQuestInfoDataset.filter("subObjTag=='s'").drop("subObjTag")

      //2.查询项目参考学校
      val mongoSchoolUri=mongoDbHost+"/"+projectId+".schools"
      val schoolRdd=spark.read.format("com.mongodb.spark.sql")
        .option("spark.mongodb.input.uri", mongoSchoolUri)
//        .option("spark.mongodb.input.partitioner", "MongoShardedPartitioner")
//        .option("spark.mongodb.input.partitionerOptions.shardkey","_id")
//        .option("spark.mongodb.input.partitioner", "MongoSplitVectorPartitioner")
//        .option("spark.mongodb.input.partitionerOptions.partitionSizeMB",2)
        .load().select("schoolId","schoolName")

      schoolRdd.persist()

      //3.查询答题信息
      var questObjectiveDataset:Dataset[Row]=null
      var questSubjectiveDataset:Dataset[Row]=null

      val schema = StructType(
        Array(
          StructField("objectiveList",
            ArrayType(StructType(Array(
                StructField("questionNo", StringType),StructField("answerContent", StringType, true)
            )))
          ),
          StructField("subjectiveList",
            ArrayType(StructType(Array(
              StructField("questionNo", StringType),StructField("score", DoubleType, true)
            )))
          ),
          StructField("studentId", StringType),
          StructField("classId", StringType),
          StructField("className", StringType),
          StructField("examNo", StringType),
          StructField("schoolId", StringType),
          StructField("schoolStudentNo", StringType),
          StructField("studentName", StringType)
        )
      )

      val subjectIdList=allQuestInfoDataset.select("subjectId").distinct().collect().map(row=>row.getAs[String]("subjectId"))

      for(subjectId <- subjectIdList){
        val mongoUri=mongoDbHost+"/"+projectId+"_"+subjectId+".students"
        var subjectDataset=spark.read.format("com.mongodb.spark.sql")
          .schema(schema)
          .option("spark.mongodb.input.uri", mongoUri)
//          .option("spark.mongodb.input.partitioner", "MongoShardedPartitioner")
//          .option("spark.mongodb.input.partitionerOptions.shardkey","_id")
          .option("spark.mongodb.input.partitioner", "MongoSplitVectorPartitioner")
          .option("spark.mongodb.input.partitionerOptions.partitionSizeMB",20)
          .load()

        //subjectDataset.persist()
        //subjectDataset.checkpoint()

        subjectDataset=subjectDataset.withColumn("subjectId",lit(subjectId));
        subjectDataset=subjectDataset.withColumn("projectId",lit(projectId));

        //客观题
        var subjectObjectiveDataset=subjectDataset.select((subjectDataset.schema.fieldNames.map(f=>{subjectDataset(f)}):+explode($"objectiveList").as("questAnswerInfo")):_*)
        subjectObjectiveDataset=subjectObjectiveDataset.withColumn("questType",lit("subjective"))
        subjectObjectiveDataset=subjectObjectiveDataset.withColumn("questionNo",subjectObjectiveDataset("questAnswerInfo")("questionNo").as("questionNo"))
        subjectObjectiveDataset=subjectObjectiveDataset.withColumn("answerContent",subjectObjectiveDataset("questAnswerInfo")("answerContent").as("answerContent"))
        subjectObjectiveDataset=subjectObjectiveDataset.drop("questAnswerInfo","objectiveList","subjectiveList")

        questObjectiveDataset=if(questObjectiveDataset==null) subjectObjectiveDataset else questObjectiveDataset.unionByName(subjectObjectiveDataset)

        //主观题
        var subjectSubjectiveDataset=subjectDataset.select((subjectDataset.schema.fieldNames.map(f=>{subjectDataset(f)}):+explode($"subjectiveList").as("questAnswerInfo")):_*)
        subjectSubjectiveDataset=subjectSubjectiveDataset.withColumn("questType",lit("objective"))
        subjectSubjectiveDataset=subjectSubjectiveDataset.withColumn("questionNo",subjectSubjectiveDataset("questAnswerInfo")("questionNo").as("questionNo"))
        subjectSubjectiveDataset=subjectSubjectiveDataset.withColumn("questScore",subjectSubjectiveDataset("questAnswerInfo")("score").as("questionScore"))
        subjectSubjectiveDataset=subjectSubjectiveDataset.drop("questAnswerInfo","objectiveList","subjectiveList")

        questSubjectiveDataset=if(questSubjectiveDataset==null) subjectSubjectiveDataset else questSubjectiveDataset.unionByName(subjectSubjectiveDataset)

      }

      questObjectiveDataset=questObjectiveDataset.join(
        objectiveQuestInfoDataset,
        Seq("subjectId","questionNo"),
        "left"
      )
      questObjectiveDataset=questObjectiveDataset.withColumn("questScore",when($"answerContent"===$"answer",$"questionFullScore").otherwise(0))
      .drop("answerContent","answer")

      questSubjectiveDataset=questSubjectiveDataset.join(
        subjectiveQuestInfoDataset,
        Seq("subjectId","questionNo"),
        "left"
      ).drop("answer")

      var questDataset=questObjectiveDataset.unionByName(questSubjectiveDataset)


      questDataset=questDataset.join(schoolRdd,Seq("schoolId"),"left")

      questDataset.persist()
      questDataset.count()
      questDataset.show()
      questDataset.checkpoint()

      //单科
//      var subjectScoreDf=questDataset.groupBy("studentId","subjectId").agg(
//        sum("questScore").as("subjectScore"),
//        first("projectId").as("projectId"),
//        first("schoolId").as("schoolId")
//      )

      //全科
//      var totalScoreDf=subjectScoreDf.groupBy("studentId").agg(
//        sum("subjectScore").as("totalScore"),
//        first("projectId").as("projectId"),
//        first("schoolId").as("schoolId")
//      )

//      totalScoreDf=totalScoreDf.withColumn("totalScoreProjectRank",rank().over(Window.partitionBy("projectId").orderBy($"totalScore".desc)))
//      totalScoreDf=totalScoreDf.withColumn("totalScoreSchoolRank",rank().over(Window.partitionBy("projectId","schoolId").orderBy($"totalScore".desc)))
//
//      totalScoreDf.persist()
      //totalScoreDf.checkpoint()


      //4.小题计算
      /*
      questDataset=questDataset.join(totalScoreDf.select("studentId","totalScoreProjectRank","totalScoreSchoolRank"),Seq("studentId"),"left")

      //QUEST_SCORE_RATE 小题得分率 quest_score_model
      questDataset = questDataset.withColumn("questScoreRate", round($"questScore" / $"questionFullScore", 2))

      //PROJECT_QUEST_SCORE_AVG 项目小题平均分 quest_score_model
      questDataset=questDataset.withColumn("projectQuestScoreAvg",avg("questScore").over(Window.partitionBy("subjectId","questionNo")))

      //SCHOOL_QUEST_SCORE_AVG 单校小题平均分 quest_score_model
      questDataset=questDataset.withColumn("schoolQuestScoreAvg",avg("questScore").over(Window.partitionBy("schoolId", "subjectId","questionNo")))

      //CLASS_QUEST_SCORE_AVG 单班小题平均分 quest_score_model
      questDataset=questDataset.withColumn("classQuestScoreAvg",avg("questScore").over(Window.partitionBy("classId", "subjectId","questionNo")))



      //SCHOOL_TOP10_QUEST_SCORE_AVG 项目全科前10考生小题平均分 quest_score_model
      questDataset = questDataset.join(
        questDataset.where("totalScoreProjectRank <= 10").groupBy("questId").agg(round(avg("questScore"), 2).as("projectTop10QuestScoreAvg")),
        Seq("questId"),"left"
      )


      //SCHOOL_TOP10_QUEST_SCORE_AVG 学校全科前10考生小题平均分 quest_score_model
      questDataset = questDataset.join(
        questDataset.where("totalScoreSchoolRank <= 10").groupBy("questId","schoolId").agg(round(avg("questScore"), 2).as("schoolTop10QuestScoreAvg")),
        Seq("questId","schoolId"), "left"
      )
*/

      questDataset.persist()

      val mongoOutputUri=mongoDbHost+"/"+projectId+".test_quest"

//      questDataset
//        .write
//        .format("com.mongodb.spark.sql")
//        .option("spark.mongodb.output.uri",mongoOutputUri)
//        .mode(SaveMode.Overwrite)
//        .save()

      print("##############################"+questDataset.count())
      questDataset.write.mode(SaveMode.Overwrite).saveAsTable("h_test.test_quest")
//
      spark.close()
    } catch {
      case e: Exception => {
        //Logger.getLogger(SparkMongo.getClass).error("数据计算分析异常", e)
        throw e
      }
    }
  }


  def main(args: Array[String]): Unit = {

    sparkMain(args)



  }

}