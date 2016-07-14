package observer;
 
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
 
 
public class PullObserver {
    private DatagramSocket observable;
    private byte[] receiveData = new byte[1024];
   
   
    public PullObserver(int port) {
        InetAddress localhost;
        try {
            localhost = InetAddress.getByName("localhost");
            observable = new DatagramSocket();
            observable.connect(localhost, port);
        } catch (Exception e) {
        }
    }
   
    public void refresh() throws IOException {
        String request = "???";
        DatagramPacket sendPacket = new DatagramPacket(request.getBytes(), request.getBytes().length);
        observable.send(sendPacket);
       
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        observable.receive(receivePacket);
       
        String response = new String(receivePacket.getData()).substring(0, receivePacket.getLength());
        System.out.println("Response: " + response);
    }
}