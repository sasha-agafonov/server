import java.net.*;
import java.io.*;
import java.util.concurrent.*;

// this class resembles the basic structure of the server.
public class Server {

    public static void main(String[] args) throws IOException {

        if (args.length != 0) {
            System.err.println("Error: this application does not support any command-line arguments.");
            System.exit(-1);
        }

        else {
            ServerSocket serverSocket = null;
            ExecutorService execService = null;

            // set up the listening port.
            try {
                serverSocket = new ServerSocket(8888);
            } catch (IOException e) {
                System.err.println("Error: could not listen on port 8888.");
                System.exit(-1);
            }
            execService = Executors.newFixedThreadPool(15);
            System.out.println("Server is running.");

            // the main thread runs continuously.
            // the server waits (listens) for new clients.
            while(true) {

                Socket clientSocket = serverSocket.accept();
                execService.submit(new NewThread(clientSocket));
            }
        }
    }
}
