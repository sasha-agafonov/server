import java.net.*;
import java.io.*;
import java.util.*;

// This class executes the list command in client's request.
public class List {

    private Socket socket = null;
    private String buff = null;
    private int listSize = 0;

    public List(Socket socket) {
        this.socket = socket;
    }

    public void listFiles() {

        // get the number of files on the server and
        // load all filenames into buffer.
        String[] filenames;
        File file = new File("./serverFiles");
        filenames = file.list();
        listSize = file.list().length;
        buff = String.join("\n", filenames);
    }

    public void sendList() {
        try {

            // prepare to send data to the client.
            BufferedOutputStream out = new BufferedOutputStream( socket.getOutputStream());
            String sizeRead;

            // -1 bytes to be read will inform the client that the server list is empty.
            if (listSize == 0) {
                sizeRead = "-1\n";
            }
            else {
                sizeRead = Integer.toString(buff.getBytes().length) + "\n";
            }

            // send the number of bytes that the client should
            // read from the stream first, then the actual list.
            out.write(sizeRead.getBytes());
            out.write(buff.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            System.err.println("Error: could not send the list to client.");
        }
    }
}
