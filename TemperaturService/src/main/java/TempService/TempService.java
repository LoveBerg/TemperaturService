package TempService;


import TempServer.TempServer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import Sensor.Sensor;


@Path("/TempService")


public class TempService {
    TempServer GS = new TempServer();

    
    @GET
    @Path("/GetTemp")
    @Produces (MediaType.APPLICATION_JSON)
    public List<Sensor> GetLuftfuktighet() throws ClassNotFoundException, IOException, FileNotFoundException, SQLException 
    {    
    return GS.Displayluftfuktighet();
    }    
    @GET
    @Path("/GetLatestValue")
    @Produces (MediaType.APPLICATION_JSON)
    public Sensor GetLatestValue() throws ClassNotFoundException, IOException 
    {     
    return GS.getvalue();
    }  
    @GET
    @Path("/test")
    @Produces (MediaType.APPLICATION_JSON)
    public List<Sensor> test() throws ClassNotFoundException, IOException 
    {     
    return GS.getvalue2();
    } 
}



