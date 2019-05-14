package TempServer;

import AzureDeviceClient.AzureDeviceClient;
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
    AzureDeviceClient adc = new AzureDeviceClient();

    public TempServer() {
        adc.start();
        System.out.print("start");


        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
    
    
    public List<Sensor> Displayluftfuktighet() throws ClassNotFoundException, FileNotFoundException, IOException{
        List<Sensor> jfList = new ArrayList<>();

        System.out.print("FUNKA");
        
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");    
    
    Properties p = new Properties();
    p.load(new FileInputStream("/Users/Joel/NetBeansProjects/TemperaturService/TemperaturService/src/main/java/TempServer/settings.properties"));
    try(Connection con = DriverManager.getConnection(p.getProperty("connection"));)
        {
         Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("Select id,value,sensorname,created from Tempsensor");
        while(rs.next()){

            Sensor jfsensor = new Sensor();
            int id = rs.getInt("id");
            float value = rs.getFloat("value");
            String sensorname = rs.getString("sensorname");
            Timestamp time = rs.getTimestamp("created");
            Date date = new Date();
 
            
            
            
         
            jfsensor.setSensorId(Integer.toString(id));
            jfsensor.setSensorName(sensorname);
            jfsensor.setSensorValue(Float.toString(value));
            jfsensor.setTime(String.valueOf(time));
       
            
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
   
            jfsensor.setSensorValue(Float.toString(value));
                        jfsensor.setTime(String.valueOf(time));

    
         return jfsensor;
    }
        public List<Sensor> getvalue2(){
        Sensor jfsensor = new Sensor();
        
            Date date = new Date();
            int id= 1220;
            float value  = 155;
            String sensorname = "jippi";
            Timestamp time = new Timestamp(date.getTime());
   
            jfsensor.setSensorValue(Float.toString(value));
            jfsensor.setTime(String.valueOf(time));
            List<Sensor> jfList = new ArrayList<>();
            jfList.add(jfsensor);

    
         return jfList;
    }
     public Sensor getvalue3(){
        Sensor jfsensor = new Sensor();
        
            Date date = new Date();
            int id= 1220;
            float value  = 155;
            String sensorname = "jippi";
            Timestamp time = new Timestamp(date.getTime());
            

            
            jfsensor.setTime(String.valueOf(time));

    
         return jfsensor;
    }
     
     public String GetTemp() throws IOException, URISyntaxException{
     
       String temp = adc.gettemp();
       System.out.print("Skickar vidare temp..");
       return temp;
     }
    
}
