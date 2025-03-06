package kp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client(Socket socket)
    {
        try
        {
            this.socket = socket;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            closeAllStreams();
        }

    }

    public void sendObjToServer(Object obj)
    {
        try
        {
            out.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
            closeAllStreams();
        }
    }

    public void closeAllStreams()
    {
        try
        {
            if(out != null)
            {out.close();}
            if(in != null)
            {in.close();}
            if(socket != null)
            {socket.close();}
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ObjectInputStream getIn()
    {
        return this.in;
    }

    public ObjectOutputStream getOut()
    {
        return this.out;
    }

}
