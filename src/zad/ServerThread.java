package zad;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;



public class ServerThread extends Thread {
    private BufferedReader in;
    private JavaTcpServer server;
    private String clientId;
    public ServerThread(BufferedReader i,JavaTcpServer serv,String clientId){
        this.in=i;
        this.server=serv;
        this.clientId=clientId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = null;
                try {
                    msg = in.readLine();
                    if (msg.equals(clientId + '#' + null)) {
                        server.closeClient(clientId);
                        break;
                    }
                } catch (SocketException e) {
                    server.closeClient(clientId);
                    break;
                }
                String[] split = msg.split("#");
                String id=split[0];
                String m =split[1];
                System.out.println("received msg: " + m + " from " + id);
                server.send("You got message: " + m + " from " + id, id);

            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
