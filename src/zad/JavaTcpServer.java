package zad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;

public class JavaTcpServer {
    private Hashtable<String,Socket> clients = new Hashtable<>();
    private List<Integer> ports=new ArrayList<>();
    private DatagramSocket socket = null;
    private ServerSocket serverSocket = null;
    public void startServer() throws IOException {
        System.out.println("JAVA TCP SERVER");
        int portNumber = 12345;
        try {
            serverSocket = new ServerSocket(portNumber);
            socket = new DatagramSocket(portNumber);
            ServerThreadUdp serverThreadUdp=new ServerThreadUdp(socket,this);
            serverThreadUdp.start();
            while(true){
                // accept client
                Socket clientSocket = serverSocket.accept();
                ports.add(clientSocket.getPort());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String id;
                id=in.readLine();
                System.out.println("client "+id+" connected");
                clients.put(id,clientSocket);

                ServerThread serverThread=new ServerThread(in,this,id);
                serverThread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            Set<String> keys=clients.keySet();
            for (String key:keys){
                clients.get(key).close();
            }
            if (serverSocket != null){
                serverSocket.close();
            }
            if (socket != null){
                socket.close();
            }
        }
    }

    public void send(String message,String id) throws IOException {
        Set<String> keys=clients.keySet();
        for (String key:keys) {
            if(!key.equals(id)){
                PrintWriter out = new PrintWriter(clients.get(key).getOutputStream(), true);
                out.println(message);
            }
        }
    }

    public void sendUdp(String message, InetAddress address, int port) throws IOException {
        for (Integer p: ports){
            if (port!=p){
                byte[] sendBuffer = ("You got message"+message).getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, p);
                socket.send(sendPacket);
            }
        }
    }

    public void closeClient(String id) throws IOException {
        if(clients.get(id)!=null)
            ports.remove((Integer) clients.get(id).getPort());
            clients.get(id).close();
        clients.remove(id);
    }
}
