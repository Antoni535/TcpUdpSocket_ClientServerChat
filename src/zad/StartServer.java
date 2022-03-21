package zad;

import java.io.IOException;

public class StartServer {

    public static void main(String[] args) throws IOException {
        JavaTcpServer javaTcpServer=new JavaTcpServer();
        javaTcpServer.startServer();
    }
}
