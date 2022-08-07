import java.net.*;
import java.io.*;
import java.util.*;

// this class manages the execution of the get command.
public class ClientList {

    private Socket clientSocket = null;

    public ClientList(Socket clientSocket ) {
      this.clientSocket = clientSocket;
    }

    public void sendListRequest() {
        try {

            // send a request to server.
            BufferedOutputStream out = new BufferedOutputStream(clientSocket.getOutputStream());
            String wr = new String("list\n");
            out.write(wr.getBytes());
            out.flush();
        } catch (IOException e) {
            System.err.println("Failed to send a request to server.");
            System.exit(-1);
        }
    }

    public void fetchList() {
        try {

            // get the number of bytes read first.
            InputStreamReader stread = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader inp = new BufferedReader(stread);
            String inputRead = inp.readLine();
            int sizeRead = Integer.parseInt(inputRead);

            // server returns -1 as the number of bytes read if its
            // list is empty.
            if (sizeRead < 0) {
                System.err.println("Server responded with the following error: the list of files on the server is empty.");
                System.exit(-1);
            }

            // read the list into buffer.
            byte[] buff = new byte[sizeRead];
            for (int i = 0; i < sizeRead; i++ ) {
                buff[i] = (byte) inp.read();
            }

            // display the list
            String disp = new String(buff);
            System.out.println("Server responded:");
            System.out.println(disp);
            inp.close();
        } catch (IOException e) {
            System.err.println("Failed to fetch the list from server.");
            System.exit(-1);
        }
    }
}
