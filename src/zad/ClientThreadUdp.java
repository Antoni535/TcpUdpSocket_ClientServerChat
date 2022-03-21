package zad;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class ClientThreadUdp extends Thread {
    private DatagramSocket socket;
    public ClientThreadUdp(DatagramSocket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        while(true) {
            byte[] receiveBuffer = new byte[1024];
            Arrays.fill(receiveBuffer, (byte) 0);
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            try {
                socket.receive(receivePacket);
            } catch (IOException e) {
                break;
            }
            if (receivePacket.getData()!=null) {
                String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(msg);
            }else{
                break;
            }
        }
    }
}
