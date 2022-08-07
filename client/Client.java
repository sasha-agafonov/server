import java.net.*;
import java.io.*;
import java.util.*;

// this class resembles the client and allows to connect to server.
// It also uses ClientList and ClientGet classes to execute list and get
// commands, that is, send and receive data from server.
public class Client {

    public static void main( String[] args ) {
        Socket clientSocket = null;

        // managing the list command
        if (args.length == 1 && args[0].compareTo("list") == 0) {
            try {
                clientSocket = new Socket("localhost", 8888);
                System.out.println("\nConnected to server.");
                ClientList listRequest = new ClientList( clientSocket );

                // communicating with server
                listRequest.sendListRequest();
                listRequest.fetchList();
                clientSocket.close();
                System.exit(0);
            } catch (IOException e) {
                System.err.println("Error: unable to connect. Failed to create a socket for port 8888.");
                System.exit(-1);
            }
        }

        // managing the get command
        else if (args.length == 2 && args[0].compareTo("get") == 0) {
            String filename = args[1];
            try {
                clientSocket = new Socket("localhost", 8888);
                System.out.println("Connected to server.");
                ClientGet getRequest = new ClientGet(clientSocket, filename);

                // communicating with server
                getRequest.sendGetRequest();
                getRequest.fetchFile();
                clientSocket.close();
                System.exit(0);
            } catch (IOException e) {
                System.err.println("Error: unable to connect. Failed to create a socket for port 8888.");
                System.exit(-1);
            }
        }

        // wrong or missing commands
        else {
            if (args.length == 0) {
                System.err.println("Error: please provide a command.\n"
                        + "This application supports the following commands: list and get. Use list as a single argument.\n"
                        + "Use get together with a filename as the second argument.");
            }
            else {
                System.err.println("Error: unknown command\n"
                        + "This application supports the following commands: list and get. Use list as a single argument.\n"
                        + "Use get together with a filename as the second argument.");
            }
            System.exit(-1);
        }
    }
}
