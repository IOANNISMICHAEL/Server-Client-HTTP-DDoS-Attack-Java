import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

 
public class Ser_side
{
 
    private static Socket sock ;
 
    public static void main(String[] args)
    {
        try
        {
 //set the default port for the server to run. Let's use the default HTTPS port
            int port = 80;
            ServerSocket serverSocket = new ServerSocket(port);
            //System.out.println(" "+port);
 
            //Below, message is receieved from client side, it is multiplied and sent back 
            //like a handshake 
            while(true)
            {
                
                sock  = serverSocket.accept();
                InputStream is = sock.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String num = br.readLine();
                System.out.println("Received HTTP/POST "+num);
 
                
                String returnMessage;
                try
                {
                    int numberInIntFormat = Integer.parseInt(num);
                    int mult_val = numberInIntFormat*4;
                    returnMessage = String.valueOf(mult_val) + " HTTP/RESPONSE\n";
                }
                catch(NumberFormatException e)
                {
                    //
                    returnMessage = "invalid value, try again\n";
                }
 
                
                OutputStream os = sock.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(returnMessage);
                System.out.println("Reurn:  "+returnMessage);
                bw.flush();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                sock.close();
            }
            catch(Exception e){}
        }
    }
}