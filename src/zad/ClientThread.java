package zad;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientThread extends Thread {
    private BufferedReader in;
    public ClientThread(BufferedReader i){
        this.in=i;
    }

    @Override
    public void run(){
        while(true) {
            String response = null;
            try {
                response = in.readLine();
            } catch (IOException e) {
                break;
            }
            if (response!=null) {
                System.out.println(response);
            }else{
                break;
            }
        }
    }


}
