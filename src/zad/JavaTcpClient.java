package zad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class JavaTcpClient {

    public static void main(String[] args) throws IOException {

        String hostName = "localhost";
        int portNumber = 12345;
        Socket socket = null;
        DatagramSocket datagramSocket = null;
        MulticastSocket multicastSocket=null;

        try {
            // create socket
            socket = new Socket(hostName, portNumber);
            datagramSocket = new DatagramSocket(socket.getLocalPort());
            multicastSocket =new MulticastSocket(6666);
            InetAddress multicastAddress=InetAddress.getByName("225.0.0.1");
            InetSocketAddress groupAddress=new InetSocketAddress(multicastAddress,7777);
            multicastSocket.joinGroup(groupAddress,null);

            String id;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Podaj swoje id: ");
            id=reader.readLine();
            // in & out streams
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(id);

            ClientThread clientThread=new ClientThread(in);
            clientThread.start();
            ClientThreadUdp clientThreadUdp=new ClientThreadUdp(datagramSocket);
            clientThreadUdp.start();
            ClientThreadMulticast clientThreadMulticast=new ClientThreadMulticast(multicastSocket);
            clientThreadMulticast.start();

            String msg=id+'#';
            while(true){
                String s=null;
                s=reader.readLine();
                if(s!=null && (s.equals("U") || s.equals("u"))){
                    s=null;
                    s=reader.readLine();
                    InetAddress address = InetAddress.getByName(hostName);
                    while(s!=null && !s.equals("q") && !s.equals("Q")) {
                        byte[] sendBuffer = ("(UDP): "+s+" from "+id).getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                        datagramSocket.send(sendPacket);
                        s=reader.readLine();
                    }
                }else if(s!=null && (s.equals("M") || s.equals("m"))){
                    s=null;
                    s=reader.readLine();
                    while(s!=null && !s.equals("q") && !s.equals("Q")) {
                        byte[] sendBuffer = ("(Multicast): "+s+" from "+id).getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, multicastAddress, 6666);
                        multicastSocket.send(sendPacket);
                        s=reader.readLine();
                    }
                }
                else{
                    out.println(msg+s);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null){
                socket.close();
            }
            if(datagramSocket!=null){
                datagramSocket.close();
            }
            if(multicastSocket!=null){
                multicastSocket.close();
            }
        }
    }

}
