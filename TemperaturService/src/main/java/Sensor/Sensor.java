package Sensor;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "sensor")


public class Sensor {
    private static long serialVersionUID = 1L;
    private String SensorId;
    private String SensorValue;
    private String SensorName;
    private String time;
     
    public void jordfuktighetsSensor(String SensorId,String SensorValue, String SensorName, String time){
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

    public String getSensorValue() {
        return SensorValue;
    }

    public void setSensorValue(String SensorValue) {
        this.SensorValue = SensorValue;
    }

    public String getSensorName() {
        return SensorName;
    }

    public void setSensorName(String SensorName) {
        this.SensorName = SensorName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    } 
}
