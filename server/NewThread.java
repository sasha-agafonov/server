import java.net.*;
import java.io.*;
import java.util.*;

// this class is used to receive a request from a client.
// The request is then passed to Protocol for processing.
public class NewThread extends Thread {
  
    private Socket socket = null;

    public NewThread(Socket socket) {
        super("NewThread");
        this.socket = socket;
    }

    public void run() {
        try {

            // get the client's request.
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inpt = in.readLine();

            // hand over to Protocol to process the request.
            Protocol newProtocol = new Protocol(inpt, socket);
            in.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("Error: could not read data from client.");
        }
    }
}
