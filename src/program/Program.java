package program;
 
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;
 
public class Program {
    private String state;
    private DatagramSocket serverSocket;
    private byte[] receiveData = new byte[1024];
    private DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
   
    public Program(int port) {
        try {
            serverSocket = new DatagramSocket(port);
        } catch (SocketException e) {
        }
       
        new Thread(new Runnable(){
 
            public void calculation() {
                Random rand = new Random();
                int x = rand.nextInt(10);
                if (x < 9) {
                    state = "OK";
                } else {
                    state = "ERROR";
                }
            }
 
            @Override
            public void run() {
                while (true) {
                    calculation();
                   
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
           
        }).start();
       
        new Thread(new Runnable(){
           
            private byte[] getResponse() {
                return state.getBytes();
            }
           
            @Override
            public void run() {
               
                while (true) {
                    try {
                        serverSocket.receive(receivePacket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                   
                    String request = new String(receivePacket.getData()).substring(0, receivePacket.getLength());
                    if (request.equals("???")) {
                        InetAddress observerIPAddress = receivePacket.getAddress();
                        int observerPort = receivePacket.getPort();
                        byte[] response = getResponse();
                        DatagramPacket sendPacket = new DatagramPacket(response, response.length);
                       
                        try {
                            serverSocket.connect(observerIPAddress, observerPort);
                            serverSocket.send(sendPacket);
                        } catch (IOException e) {
                        }
                    }
                }
            }
           
        }).start();
    }
 
}