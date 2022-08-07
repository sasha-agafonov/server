import java.net.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

// this class is used to log, process and manage
// the execution of clients' requests.
public class Protocol {

    private Socket socket = null;
    private String input = null;

    public Protocol(String input, Socket socket) {
        this.input = input;
        this.socket = socket;

        // log every client request.
        logClientRequest(input);

        // handles list requests.
        if (input.compareTo("list") == 0) {
            List myList = new List(socket);
            myList.listFiles();
            myList.sendList();
        }

        // handles get requests.
        else if (input.startsWith("get")) {
            String[] splitted = input.split(" ");
            Get myGet = new Get(splitted[1], socket);
            myGet.sendFile();
        }

        // cannot process the request.
        else {
            System.err.println("Error: unknown command received from client.");
        }
    }

    // log client requests by appending to a log file.
    public void logClientRequest(String input) {

        // get client IP
        InetAddress newInet = socket.getInetAddress();

        // get and format current date and time
        SimpleDateFormat newFormatter = new SimpleDateFormat("dd/MM/yyyy ':' HH:mm:ss");
        Date currentDate = new Date();
        newFormatter.format(currentDate);

        try {
            File log = new File("log.txt");

            // create the log.txt file if it's missing.
            if (!log.exists()) {
                log.createNewFile();
            }

            // append to the file
            FileWriter newFileWriter = new FileWriter(log, true);
            newFileWriter.write(newFormatter.format(currentDate) + " : "
                    + newInet.getHostAddress() + " : " + input + '\n');
            newFileWriter.close();
        } catch (IOException e) {
            System.err.println("Error: could not write to the log file.");
        }
    }
}
