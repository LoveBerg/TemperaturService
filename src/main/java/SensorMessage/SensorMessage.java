package SensorMessage;

import Sensor.Sensor;
import com.google.gson.Gson;

public class SensorMessage {

    private String deviceId;
    private int messageId;
    private float temperature;
    private boolean hourlyUpdate;

    public SensorMessage() {

    }

  /*  public void deserializeSensor(String s) {

        System.out.print("deserialize");
        return smsg;

    }*/

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public float getTemperature() {
        System.out.println("ABC: " + temperature);
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public boolean isHourlyUpdate() {
        return hourlyUpdate;
    }

    public void setHourlyUpdate(boolean hourlyUpdate) {
        this.hourlyUpdate = hourlyUpdate;
    }
}
