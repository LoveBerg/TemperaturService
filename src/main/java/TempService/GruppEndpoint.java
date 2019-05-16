package TempService;


import AzureDeviceClient.AzureDeviceClient;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import Sensor.Sensor;
import SensorMessage.SensorMessage;
import TempServer.TempServer;
import com.google.gson.Gson;
import java.net.URISyntaxException;


@ServerEndpoint(value="/Seensor/", decoders = {GruppDecoder.class},encoders = {GruppEncoder.class})

public class GruppEndpoint {
    TempServer gs = new TempServer();
    AzureDeviceClient ad = new AzureDeviceClient();
    private Session session;
    private static final Set<GruppEndpoint> GruppEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException, URISyntaxException, InterruptedException {
        ad.start();
        this.session = session;
        GruppEndpoints.add(this);
        users.put(session.getId(), "Sensor");
                Sensor sensor = new Sensor();
                SensorMessage Sensormsg = new SensorMessage();
        
        while(true){
            String temp = ad.gettemp();
            Thread.sleep(5000);
            
            Gson gson = new Gson();
            SensorMessage smsg = gson.fromJson(temp, SensorMessage.class);
            
            System.out.print("SENSOR: "+ smsg);
                    
            if(temp != null){
                //sensor.setSensorName(temp);
                sensor.setSensorValue((int)smsg.getTemperature());
                System.out.print(temp);
                broadcast(sensor);
                
            }
        }
    }
   @OnMessage
    public void onMessage(Session session, Sensor message) throws IOException, EncodeException, URISyntaxException {
        if (GruppEndpoints.contains(this)){
            message = gs.getvalue2();
            //System.out.print(gs.gettemp());
            broadcast(message);
        }
    }

    private static void broadcast(Sensor message) 
            throws IOException, EncodeException {
        GruppEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote()
                        .sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }    
}
