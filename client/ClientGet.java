import java.net.*;
import java.io.*;
import java.util.*;


// this class manages the execution of the get command.
public class ClientGet {

    private Socket clientSocket = null;
    private String filename = null;

    public ClientGet(Socket clientSocket, String filename) {
        this.clientSocket = clientSocket;
        this.filename = filename;
    }

    public void sendGetRequest() {
        try {

            // send a request to server.
            BufferedOutputStream out = new BufferedOutputStream(clientSocket.getOutputStream());
            out.write(("get " + filename + "\n").getBytes());
            out.flush();
        } catch (IOException e) {
            System.err.println("Failed to send a request to server.");
            System.exit(-1);
        }
    }

    public void fetchFile() {
        try {

            // get the number of bytes read first.
            InputStreamReader stread = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader inp = new BufferedReader(stread);
            String rd = inp.readLine();
            int sizeRead = Integer.parseInt(rd);

            // server returns -1 as the number of bytes read if it
            // fails to find the specified file.
            if (sizeRead < 0) {
                System.err.println("Server responded with the following error: no files with the specified filename found.");
                System.exit(-1);
            }

            // read the file into buffer
            byte[] buff = new byte[sizeRead];
            for (int i = 0; i < sizeRead; i++ ) {
                buff[i] = (byte) inp.read();
            }

            // create the file in client directory
            FileOutputStream newFile = new FileOutputStream(filename);
            newFile.write(buff);
            System.out.println("Successfully received the file from server.");
            inp.close();
            newFile.close();
        } catch (IOException e) {
            System.err.println("Failed to fetch the requested file.");
            System.exit(-1);
        }
    }
}
