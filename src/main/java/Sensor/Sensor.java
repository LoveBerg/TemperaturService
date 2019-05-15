package Sensor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "sensor")


public class Sensor {
    private static long serialVersionUID = 1L;
    private String SensorId;
    private int SensorValue;
    private String SensorName;
    private Date time;
     
    public void jordfuktighetsSensor(String SensorId,int SensorValue, String SensorName, Date time){
        this.SensorId = SensorId;
        this.SensorValue = SensorValue;
        this.SensorName = SensorName;
        this.time = time;
    }
    public String getSensorId() {
        return SensorId;
    }

    public void setSensorId(String SensorId) {
        this.SensorId = SensorId;
    }

    public int getSensorValue() {
        return SensorValue;
    }

    public void setSensorValue(int SensorValue) {
        this.SensorValue = SensorValue;
    }

    public String getSensorName() {
        return SensorName;
    }

    public void setSensorName(String SensorName) {
        this.SensorName = SensorName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    } 
}
