package serverPackage;
import java.io.*;
import java.net.*;
import common.ClientProcess;


public class Server{
    static final int PORT = 1234;
    public static void main(String[] args) {
        int i=0;
        try (ServerSocket sk = new ServerSocket(PORT)) {

            System.out.println("Je suis un serveur en attente la connexion d'un client ");

            while (true) {
                Socket s = sk.accept();
                i++;
                System.out.println("Client N:" + i + "est connecté" + s.getRemoteSocketAddress());
                new ClientProcess(s, i).start();
            }
        } catch (IOException e) {e.printStackTrace();}





    }



}
