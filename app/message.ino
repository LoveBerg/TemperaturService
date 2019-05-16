#include <Adafruit_Sensor.h>
#include <ArduinoJson.h>
#include <DHT.h>
long latestUpdate = 0;

#if SIMULATED_DATA

void initSensor()
{
    // use SIMULATED_DATA, no sensor need to be inited
}

float readTemperature()
{
    return random(20, 30);
}

float readHumidity()
{
    return random(30, 40);
}

#else

static DHT dht(DHT_PIN, DHT_TYPE);
void initSensor()
{
    dht.begin();
}

float readTemperature()
{
    return dht.readTemperature();
}

float readHumidity()
{
    return dht.readHumidity();
}

#endif

bool readMessage(int messageId, char *payload)
{
    float temperature = readTemperature();
    StaticJsonBuffer<MESSAGE_MAX_LEN> jsonBuffer;
    JsonObject &root = jsonBuffer.createObject();
    root["deviceId"] = DEVICE_ID;
    root["messageId"] = messageId;

    bool hourlyUpdate = false;
    
    // NAN is not the valid json, change it to NULL
    if (std::isnan(temperature))
    {
        root["temperature"] = NULL;
    }
    else
    {
        root["temperature"] = temperature;
    }
    
    //hourlyUpdate = false;
    long currentMillis = millis();
    if(currentMillis - latestUpdate < 360000){
      root["hourlyUpdate"] = true;
      latestUpdate = millis();
      hourlyUpdate = true;
      Serial.println("skickar hourly update");
    }
    else{
      root["hourlyUpdate"] = false;
    }
    
    root.printTo(payload, MESSAGE_MAX_LEN);
    return hourlyUpdate;
}

//void parseTwinMessage(char *message)
//{
//    StaticJsonBuffer<MESSAGE_MAX_LEN> jsonBuffer;
//    JsonObject &root = jsonBuffer.parseObject(message);
//    if (!root.success())
//    {
//        Serial.printf("Parse %s failed.\r\n", message);
//        return;
//    }
//
//    if (root["desired"]["interval"].success())
//    {
//        interval = root["desired"]["interval"];
//    }
//    else if (root.containsKey("interval"))
//    {
//        interval = root["interval"];
//    }
//}
