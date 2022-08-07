import java.net.*;
import java.io.*;
import java.util.*;

// This class executes the get command in client's request.
public class Get {

    private String filename = null;
    private Socket socket = null;

    public Get(String filename, Socket socket) {
        this.filename = filename;
        this.socket = socket;
    }

    public void sendFile() {
        try {

            // attempt to find a file with the given filename on the server.
            File file = new File("./serverFiles/" + filename);
            if (file.exists()) {

                // get the size and the contents of the file
                long fileSize = file.length();
                byte[] size = (Long.toString(fileSize) + "\n").getBytes();
                FileInputStream fileStream = new FileInputStream(file);
                BufferedInputStream inp = new BufferedInputStream(fileStream);
                BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
                // send the number of bytes to be read first.
                out.write(size);
                byte[] buff = new byte[32768];

                // output the contents to stream until EOF is reached.
                while (inp.read(buff) != -1) {
                    out.write(buff);
                }
                inp.close();
                out.flush();
                out.close();
            }

            // file not found
            else {
                inexistentFile();
            }
        } catch (IOException e) {
            System.err.println("Error: could not send the requested file to client.");
        }
    }

    // respond to client about being unable to find the requested file.
    public void inexistentFile() {
        try {
            BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
            String sizeRead;
            
            // -1 bytes to be read will indicate to client that the file is missing.
            sizeRead = "-1\n";
            out.write(sizeRead.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            System.err.println("Error: could not respond to client.");
        }
    }
}
