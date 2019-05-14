
package TempService;

import com.google.gson.Gson;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import Sensor.Sensor;


public class GruppEncoder implements Encoder.Text<Sensor>  {

    @Override
    public String encode(Sensor sensor) throws EncodeException {
        Gson gson = new Gson();
        String json = gson.toJson(sensor);
        return json;
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

