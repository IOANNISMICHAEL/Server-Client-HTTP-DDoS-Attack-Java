import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Scanner;
 
public class client_side
{
 
    private static Socket sock;
 
    public static void main(String args[]) throws SocketException
    {
        try
        {
            int port = 80 ;String host = "192.168.0.4";//host and port set
            InetAddress addr = InetAddress.getByName(host);
            sock = new Socket(addr, port);//opening new socket 
            System.out.println(addr);//giving the addr to the view
 
           
            OutputStream os = sock.getOutputStream();
            OutputStreamWriter str_wrt = new OutputStreamWriter(os);
            BufferedWriter buf_wrt= new BufferedWriter(str_wrt);
 
            String num = "";
 System.out.println("Give number for handshake");Scanner scanner = new Scanner(System.in);
 num=scanner.nextLine();
 
            String outbound = num + "\n";
            buf_wrt.write(outbound);
            buf_wrt.flush();
            System.out.println("Outbound message to the server: "+outbound);
 
            
            InputStream is = sock.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader buf_rdr = new BufferedReader(isr);
            String msg = buf_rdr.readLine();
            System.out.println("Server side outcome : " +msg);
        }
       
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
         
            try
            {
                sock.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
    }
}