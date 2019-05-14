package TempService;


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
import TempServer.TempServer;
import java.net.URISyntaxException;


@ServerEndpoint(value="/Seensor/", decoders = {GruppDecoder.class},encoders = {GruppEncoder.class})

public class GruppEndpoint {
    TempServer gs = new TempServer();
    private Session session;
    private static final Set<GruppEndpoint> GruppEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException, URISyntaxException {
        this.session = session;
        GruppEndpoints.add(this);
        users.put(session.getId(), "Sensor");

        Sensor message = new Sensor();
        message.setSensorName("Connected");

        broadcast(message);
    }
   @OnMessage
    public void onMessage(Session session, Sensor message) throws IOException, EncodeException, URISyntaxException {
        if (GruppEndpoints.contains(this)){
            message = gs.getvalue();
            broadcast(message);
        }
    }
   @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        GruppEndpoints.remove(this);
        Sensor message = new Sensor();
        message.setSensorId(users.get(session.getId()));
        message.setSensorName("Disconnected!");
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
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
