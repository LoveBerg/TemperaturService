package TempServer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.json.Json;
import Sensor.Sensor;
import java.net.URISyntaxException;

public class TempServer {

    public TempServer(){
    
    }
    
    
    public List<Sensor> Displayluftfuktighet() throws ClassNotFoundException, FileNotFoundException, IOException{
        List<Sensor> jfList = new ArrayList<>();        
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");    
    
    Properties p = new Properties();
    p.load(new FileInputStream("/Users/Joel/NetBeansProjects/TemperaturService/src/main/java/TempServer/settings.properties"));
    try(Connection con = DriverManager.getConnection(p.getProperty("connection"));)
        {
         Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("Select id,value,sensorname,created from Tempsensor");
        while(rs.next()){

            Sensor jfsensor = new Sensor();
            int id = rs.getInt("id");
            float value = rs.getFloat("value");
            Timestamp time = rs.getTimestamp("created");

            jfsensor.setSensorId(Integer.toString(id));
            jfsensor.setSensorValue(50);
            jfsensor.setTime(time);
          
            jfList.add(jfsensor);

        }
        }
        catch(SQLException e){
        e.printStackTrace();   
        System.out.print("något gick fel..");
        }
        return jfList;
       
    }
    
    public List<Sensor> GetLatestValue() throws ClassNotFoundException, FileNotFoundException, IOException{
    List<Sensor> jfList = new ArrayList<>();

        
    Class.forName("com.mysql.cj.jdbc.Driver");
    Properties p = new Properties();
    p.load(new FileInputStream("/Users/Joel/grupparbetet/src/java/GruppServer/settings.properties"));
    try(Connection con = DriverManager.getConnection(p.getProperty("connection"), p.getProperty("username"), p.getProperty("password"));)
        {
         Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("select created,value from Jordfuktighetssensor order by created ASC");
        while(rs.next()){
            Sensor jfsensor = new Sensor();
            float value = rs.getFloat("value");
            Timestamp time = rs.getTimestamp("created");            
            //jfsensor.setSensorValue(value);
            //jfsensor.setTime(time);
            jfList.add(jfsensor);

        }
        }
        catch(SQLException e){
        e.printStackTrace();   
        }
        return jfList;
        
    }
    public Sensor getvalue(){
        Sensor jfsensor = new Sensor();
        
            Date date = new Date();
            int id= 10;
            float value  = 55;
            String sensorname = "må det funka..";
            Timestamp time = new Timestamp(date.getTime());
   
            jfsensor.setSensorValue(20);
                        jfsensor.setTime(time);

    
         return jfsensor;
    }
        public Sensor getvalue2(){
        Sensor jfsensor = new Sensor();
        
            Date date = new Date();
            int id= 1220;
            float value  = 155;
            String sensorname = "jippi";
            Timestamp time = new Timestamp(date.getTime());
   
            jfsensor.setSensorValue(155);
            jfsensor.setSensorName(sensorname);
            jfsensor.setTime(time);
            List<Sensor> jfList = new ArrayList<>();
            jfList.add(jfsensor);

    
         return jfsensor;
    }
     public Sensor getvalue3(){
        Sensor jfsensor = new Sensor();
        
            Date date = new Date();
            int id= 1220;
            float value  = 155;
            String sensorname = "jippi";
            Timestamp time = new Timestamp(date.getTime());
            

            
            jfsensor.setTime(time);

    
         return jfsensor;
    }
     
}
