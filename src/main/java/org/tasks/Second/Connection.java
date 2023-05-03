package org.tasks.Second;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Connection {

    public boolean connectTCP(String serverAddress, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(serverAddress, port), timeout);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean connectUDP(String serverAddress, int port) {
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.connect(new InetSocketAddress(serverAddress, port));
            socket.close();
            return true;
        } catch (SocketException e) {
            return false;
        }
    }

    public boolean connectTCPWithSSL(String serverAddress, int port, int timeout, boolean trustAllCerts) {
        try {
            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket();
            sslSocket.connect(new InetSocketAddress(serverAddress, port), timeout);

            sslSocket.startHandshake();
            sslSocket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean connect(String serverAddress, int port) {
        return connectTCP(serverAddress, port, 1000);
    }


    public static void main(String[] args) {
        Connection connection = new Connection();
        Scanner console = new Scanner(System.in);
        System.out.print("Input server address: ");
        String serverAddress = console.nextLine();
        System.out.print("Input port: ");
        int port = console.nextInt();
        boolean connect = connection.connect(serverAddress, port);
        System.out.println(connect);
    }

}
