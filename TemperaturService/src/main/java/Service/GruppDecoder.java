package Service;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import Sensor.Sensor;
import com.google.gson.Gson;



public class GruppDecoder implements Decoder.Text<Sensor> {

    @Override
    public Sensor decode(String s) throws DecodeException {
        Gson gson = new Gson();
        Sensor sensor = gson.fromJson(s, Sensor.class);
        return  sensor;    
    }

    @Override
    public boolean willDecode(String s) {
          return (s != null);
            
        }

    @Override
    public void init(EndpointConfig endpointConfig) {
        System.out.println("init");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
    
}