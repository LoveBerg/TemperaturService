package AzureDeviceClient;


import com.microsoft.azure.sdk.iot.device.*;
import com.microsoft.azure.sdk.iot.device.transport.IotHubConnectionStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AzureDeviceClient extends Thread{
    private static String globalstring;
    
        private static final int D2C_MESSAGE_TIMEOUT = 2000; // 2 seconds
    private static List failedMessageListOnClose = new ArrayList(); // List of messages that failed on close

    /** Used as a counter in the message callback. */
    protected static class Counter
    {
        protected int num;

        public Counter(int num)
        {
            this.num = num;
        }

        public int get()
        {
            return this.num;
        }

        public void increment()
        {
            this.num++;
        }

        @Override
        public String toString()
        {
            return Integer.toString(this.num);
        }
    }
    public String gettemp() throws IOException, URISyntaxException{
        return globalstring;
    }

    
     protected static class MessageCallbackMqtt implements com.microsoft.azure.sdk.iot.device.MessageCallback
    {
        public IotHubMessageResult execute(Message msg, Object context)
        {
            AzureDeviceClient.Counter counter = (AzureDeviceClient.Counter) context;
            System.out.println(
                    "Received message " + counter.toString()
                            + " with content: " + new String(msg.getBytes(), Message.DEFAULT_IOTHUB_MESSAGE_CHARSET));
            for (MessageProperty messageProperty : msg.getProperties())
            {
                System.out.println(messageProperty.getName() + " : " + messageProperty.getValue());
            }
            globalstring = new String(msg.getBytes());
            counter.increment();

            return IotHubMessageResult.COMPLETE;
        }
    }
         protected static class IotHubConnectionStatusChangeCallbackLogger implements IotHubConnectionStatusChangeCallback
    {
        @Override
        public void execute(IotHubConnectionStatus status, IotHubConnectionStatusChangeReason statusChangeReason, Throwable throwable, Object callbackContext)
        {
            System.out.println();
            System.out.println("CONNECTION STATUS UPDATE: " + status);
            System.out.println("CONNECTION STATUS REASON: " + statusChangeReason);
            System.out.println("CONNECTION STATUS THROWABLE: " + (throwable == null ? "null" : throwable.getMessage()));
            System.out.println();

            if (throwable != null)
            {
                throwable.printStackTrace();
            }

            if (status == IotHubConnectionStatus.DISCONNECTED)
            {
                //connection was lost, and is not being re-established. Look at provided exception for
                // how to resolve this issue. Cannot send messages until this issue is resolved, and you manually
                // re-open the device client
            }
            else if (status == IotHubConnectionStatus.DISCONNECTED_RETRYING)
            {
                //connection was lost, but is being re-established. Can still send messages, but they won't
                // be sent until the connection is re-established
            }
            else if (status == IotHubConnectionStatus.CONNECTED)
            {
                //Connection was successfully re-established. Can send messages.
            }
        }
    }
    public void run(){
        try {
            getmsg();
        } catch (IOException ex) {
            Logger.getLogger(AzureDeviceClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(AzureDeviceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void getmsg()
            throws IOException, URISyntaxException
    {
        String pathToCertificate = null;
        String connString = "insert connection string";        IotHubClientProtocol protocol;
        protocol = IotHubClientProtocol.MQTT;
        
        
        DeviceClient client = new DeviceClient(connString, protocol);
        if (pathToCertificate != null)
        {
            client.setOption("SetCertificatePath", pathToCertificate);
        }
        if (protocol == IotHubClientProtocol.MQTT)
        {
            MessageCallbackMqtt callback = new MessageCallbackMqtt();
            Counter counter = new Counter(0);
            client.setMessageCallback(callback, counter);
        }

        // Set your token expiry time limit here
        long time = 2400;
        client.setOption("SetSASTokenExpiryTime", time);

        client.registerConnectionStatusChangeCallback(new IotHubConnectionStatusChangeCallbackLogger(), new Object());

        client.open();
        while(!Thread.interrupted()){
            
        }

        System.out.println("Closing"); 
        client.closeNow();
    
        }

}
