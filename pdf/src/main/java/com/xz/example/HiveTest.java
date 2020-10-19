//package net.itxw.example;
//
//import java.sql.*;
//
///**
// * @Author: itxw
// * @Date: 2019/1/9
// */
//public class HiveTest {
//    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
//
//    public boolean run() {
//
//        try {
//            Class.forName(driverName);
//            Connection con = null;
//            con = DriverManager.getConnection("jdbc:hive2://10.10.22.133:10000/datacenter", "root", "123456");
//            Statement stmt = con.createStatement();
//            ResultSet res = null;
//
//            String sql = "select count(1) from subject_total_score";
//
//            System.out.println("Running: " + sql);
//           // stmt.executeUpdate("insert into subject_total_score values('108377','431000-85c2a97077e143c18b76d3a1f680f133','郴州2018年7月期末教学抽测(高中一年级)','a14f29a9-bc61-4f46-a522-469058856ef8','陈鹏屹','788fa4bc-2fbb-4fa5-b49d-e20e9421370e','1707班','aa385f4a-9c26-4e72-a37e-2998046866be','临武县第一中学','558','900','','','',''),('108377','431000-85c2a97077e143c18b76d3a1f680f133','郴州2018年7月期末教学抽测(高中一年级)','a14f29a9-bc61-4f46-a522-469058856ef8','陈鹏屹','788fa4bc-2fbb-4fa5-b49d-e20e9421370e','1707班','aa385f4a-9c26-4e72-a37e-2998046866be','临武县第一中学','558','900','','','',''),('108377','431000-85c2a97077e143c18b76d3a1f680f133','郴州2018年7月期末教学抽测(高中一年级)','a14f29a9-bc61-4f46-a522-469058856ef8','陈鹏屹','788fa4bc-2fbb-4fa5-b49d-e20e9421370e','1707班','aa385f4a-9c26-4e72-a37e-2998046866be','临武县第一中学','558','900','','','',''),('108377','431000-85c2a97077e143c18b76d3a1f680f133','郴州2018年7月期末教学抽测(高中一年级)','a14f29a9-bc61-4f46-a522-469058856ef8','陈鹏屹','788fa4bc-2fbb-4fa5-b49d-e20e9421370e','1707班','aa385f4a-9c26-4e72-a37e-2998046866be','临武县第一中学','558','900','','','',''),('108377','431000-85c2a97077e143c18b76d3a1f680f133','郴州2018年7月期末教学抽测(高中一年级)','a14f29a9-bc61-4f46-a522-469058856ef8','陈鹏屹','788fa4bc-2fbb-4fa5-b49d-e20e9421370e','1707班','aa385f4a-9c26-4e72-a37e-2998046866be','临武县第一中学','558','900','','','',''),('108377','431000-85c2a97077e143c18b76d3a1f680f133','郴州2018年7月期末教学抽测(高中一年级)','a14f29a9-bc61-4f46-a522-469058856ef8','陈鹏屹','788fa4bc-2fbb-4fa5-b49d-e20e9421370e','1707班','aa385f4a-9c26-4e72-a37e-2998046866be','临武县第一中学','558','900','','','',''),('108377','431000-85c2a97077e143c18b76d3a1f680f133','郴州2018年7月期末教学抽测(高中一年级)','a14f29a9-bc61-4f46-a522-469058856ef8','陈鹏屹','788fa4bc-2fbb-4fa5-b49d-e20e9421370e','1707班','aa385f4a-9c26-4e72-a37e-2998046866be','临武县第一中学','558','900','','','',''),('108377','431000-85c2a97077e143c18b76d3a1f680f133','郴州2018年7月期末教学抽测(高中一年级)','a14f29a9-bc61-4f46-a522-469058856ef8','陈鹏屹','788fa4bc-2fbb-4fa5-b49d-e20e9421370e','1707班','aa385f4a-9c26-4e72-a37e-2998046866be','临武县第一中学','558','900','','','',''),('108377','431000-85c2a97077e143c18b76d3a1f680f133','郴州2018年7月期末教学抽测(高中一年级)','a14f29a9-bc61-4f46-a522-469058856ef8','陈鹏屹','788fa4bc-2fbb-4fa5-b49d-e20e9421370e','1707班','aa385f4a-9c26-4e72-a37e-2998046866be','临武县第一中学','558','900','','','',''),('108377','431000-85c2a97077e143c18b76d3a1f680f133','郴州2018年7月期末教学抽测(高中一年级)','a14f29a9-bc61-4f46-a522-469058856ef8','陈鹏屹','788fa4bc-2fbb-4fa5-b49d-e20e9421370e','1707班','aa385f4a-9c26-4e72-a37e-2998046866be','临武县第一中学','558','900','','','',''),('108377','431000-85c2a97077e143c18b76d3a1f680f133','郴州2018年7月期末教学抽测(高中一年级)','a14f29a9-bc61-4f46-a522-469058856ef8','陈鹏屹','788fa4bc-2fbb-4fa5-b49d-e20e9421370e','1707班','aa385f4a-9c26-4e72-a37e-2998046866be','临武县第一中学','558','900','','','',''),('108377','431000-85c2a97077e143c18b76d3a1f680f133','郴州2018年7月期末教学抽测(高中一年级)','a14f29a9-bc61-4f46-a522-469058856ef8','陈鹏屹','788fa4bc-2fbb-4fa5-b49d-e20e9421370e','1707班','aa385f4a-9c26-4e72-a37e-2998046866be','临武县第一中学','558','900','','','','')");
//
//          // stmt.executeUpdate("create table t23(id int,name string)");
//           //stmt.executeUpdate("create table quest_score_copy as select * from quest_score where project_id !='431000-85c2a97077e143c18b76d3a1f680f133' ");
//
//            //stmt.executeUpdate("insert into t23(id) values(1)");
//
//            res = stmt.executeQuery(sql);
//
//            System.out.println("ok");
//            while (res.next()) {
//                System.out.println("###");
//                System.out.println(res.getString(1));
//
//            }
//            res.close();
//            stmt.close();
//            con.close();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("error");
//            return false;
//        }
//
//    }
//
//    public static void main(String[] args) throws SQLException {
//        HiveTest hiveJdbcClient = new HiveTest();
//        hiveJdbcClient.run();
//    }
//}
