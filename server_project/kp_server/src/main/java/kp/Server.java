package kp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = null;
        Socket socket = null;

        try
        {
            server = new ServerSocket(2525);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        while (true)
        {
            try
            {
                socket = server.accept();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            new ServerThread(socket).start();
        }

    }
}
