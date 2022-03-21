package zad;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class ServerThreadUdp extends Thread {
    private DatagramSocket socket;
    private JavaTcpServer server;
    public ServerThreadUdp(DatagramSocket socket,JavaTcpServer serv){
        this.socket=socket;
        this.server=serv;
    }

    @Override
    public void run() {
        byte[] receiveBuffer = new byte[1024];

        while(true) {
            try {
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                try {
                    socket.receive(receivePacket);
                } catch (SocketException e){
                    break;
                }
                String msg = new String(receivePacket.getData(),0,receivePacket.getLength());
                System.out.println(msg);
                server.sendUdp(msg,receivePacket.getAddress(),receivePacket.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
