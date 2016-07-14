package observer;
 
import java.io.IOException;
import java.util.Scanner;
 
public class MainObserver {
   
    private static Scanner scan;
 
    public static void main(String args[]) {
        scan = new Scanner(System.in);
        PullObserver observer = new PullObserver(1337);
       
        while (true) {
            try {
                observer.refresh();
            } catch (IOException e) {
            }
           
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
        }
    }  
}