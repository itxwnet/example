package com.xz.sparkdemo

import org.apache.log4j.Logger
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.apache.spark.sql.functions._

object TiSparkMain {

  def main(args: Array[String]): Unit = {

    try {
      val isDev = if (args.length >= 1) args(0) == "dev" else false
      val opt = args(0)
      val spark = if (isDev) {
        System.setProperty("hadoop.home.dir", "D:\\bigdata\\hadoop-2.7.3")

        SparkSession.builder().appName("Sparkdemo")
          //.config("spark.some.config.option", "some-value")
          .config("spark.debug.maxToStringFields", "100")
          .master("local[*]")
          .enableHiveSupport()
          .getOrCreate()
      } else {
        SparkSession.builder().appName("spark-demo")
          //.config("spark.some.config.option", "some-value")
          .enableHiveSupport()
          .config("spark.sql.extensions", "org.apache.spark.sql.TiExtensions")
          .config("spark.tispark.pd.addresses", "10.10.22.138:2379")
          //.config("spark.tispark.request.command.priority", "High")
          //.config("spark.tispark.request.isolation.level", "RC")
          .getOrCreate()
      }

      if("writeHive".equals(opt)){
        writeHiveData(spark)
      }else if("readTidbData".equals(opt)){
        readTidbData(spark)
      }else if("writeTidb".equals(opt)){
        writeTidbData(spark)
      }else if("count".equals(opt)){
        var rdd=readTidbData(spark);

        rdd=rdd.groupBy(
          "STUDENT_ID"
          ,"SUBJECT_ID"
          ,"PROJECT_ID","PROJECT_NAME","SCHOOL_ID","SCHOOL_NAME","CLASS_ID","CLASS_NAME","SUBJECT_ID","SUBJECT_NAME"

        ).agg(sum("QUEST_SCORE").as("TOTAL_SCORE")
//          ,
//          first("PROJECT_ID").as("PROJECT_ID")
//          ,
//          first("PROJECT_NAME").as("PROJECT_NAME"),
          //first("SCHOOL_ID").as(("SCHOOL_ID"))
          // ,
//          first("SCHOOL_NAME").as(("SCHOOL_NAME")),
//          first("CLASS_ID")as(("CLASS_ID")),
//          first("CLASS_NAME")as(("CLASS_NAME")),
//          first("SUBJECT_ID")as(("SUBJECT_ID")),
//          first("SUBJECT_NAME")as(("SUBJECT_NAME"))
        )

        rdd=rdd.withColumn("TOTAL_SCORE_SCHOOL_RANK",rank().over(Window.partitionBy("SCHOOL_ID").orderBy(col("TOTAL_SCORE").desc)))

        rdd.persist()
        rdd.count()
        rdd.show()


        val tidbOptions: Map[String, String] = Map(
          "tidb.addr" -> "10.10.22.123",
          "tidb.password" -> "root123",
          "tidb.port" -> "4000",
          "tidb.user" -> "root",
          "spark.tispark.pd.addresses" -> "10.10.22.138:2379"
        )

        rdd.write.format("tidb").options(tidbOptions).option("database", "test").option("table", "431000923a0d14be0244eb8c509ce0a57684bb_TOTAL_SCORE").mode("append").save()


      }




      spark.close()
    } catch {
      case e: Exception => {
        Logger.getLogger(SparkMain.getClass).error("数据计算分析异常", e)
        throw e
      }
    }
  }

  def readMysqlData(sparkSession: SparkSession)={
    var dataset=sparkSession.read.format("jdbc").option("url", "jdbc:mysql://10.10.22.154:3306/datacenter_quest_score").option("dbtable", "project_id_431000923a0d14be0244eb8c509ce0a57684bb").option("user", "root").option("password", "znxunzhi").option("partitionColumn", "ID").option("lowerBound", 1).option("upperBound", 16795720).option("numPartitions", 100).load();
    dataset.persist()
    println(dataset.count())
    dataset.show()
    dataset
  }

  def readTidbData(spark: SparkSession)={
    spark.sql("use test")
    val rdd=spark.table("test.project_id_431000923a0d14be0244eb8c509ce0a57684bb")
    rdd.persist()
    println(rdd.count())
    rdd.show
    rdd
  }

  def readHiveData(sparkSession: SparkSession)={
    sparkSession.sql("use h_test")
    var rdd=sparkSession.table("project_id_431000923a0d14be0244eb8c509ce0a57684bb_test")
    rdd.persist()
    println(rdd.count())
    rdd.show
    rdd
  }

  def writeHiveData(sparkSession: SparkSession)={
    sparkSession.sql("use h_test")
    var dataset=readMysqlData(sparkSession: SparkSession)
    print("mysql 分区数"+dataset.rdd.getNumPartitions)
    dataset.write.saveAsTable("project_id_431000923a0d14be0244eb8c509ce0a57684bb_test")
  }

  def writeTidbData(sparkSession: SparkSession): Unit ={
    var rdd=readHiveData(sparkSession: SparkSession)

    //rdd=rdd.repartition(col("class_id"),col("subject_id"),col("quest_id"))
//    rdd=rdd.repartition(10)
//
//    rdd.persist()
//    println(rdd.count())
//    rdd.show


    print("##################hive 分区数"+rdd.rdd.getNumPartitions+"#######################################################")
    val tidbOptions: Map[String, String] = Map(
      "tidb.addr" -> "10.10.22.123",
      "tidb.password" -> "root123",
      "tidb.port" -> "4000",
      "tidb.user" -> "root",
      "spark.tispark.pd.addresses" -> "10.10.22.138:2379"
//      ,
//      "spark.tispark.request.command.priority" -> "High",
//      "spark.tispark.request.isolation.level" -> "RC"
    )

    rdd.write.format("tidb").options(tidbOptions).option("database", "test").option("table", "project_id_431000923a0d14be0244eb8c509ce0a57684bb_test").mode("append").save()
    print("##################new hive 分区数"+rdd.rdd.getNumPartitions+"#######################################################")
  }





}