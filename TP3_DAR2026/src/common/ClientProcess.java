package common;

import java.io.*;
import java.net.Socket;



public class ClientProcess extends Thread {
    private static int compteurGlobal=0;
    private static synchronized void compterEtAfficher(String detail){
        compteurGlobal++;
        System.out.println("Opération globale n°" + compteurGlobal + " " + detail);
    }
    private final Socket s;
    private final int number;

    public ClientProcess(Socket s, int i) {
        this.s = s;
        number = i;
    }

    public void run() {
        try (Socket socket = s) {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("Bienvenue ! Vous etes le client numero " + number);

            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.flush();

            while (true) {
                Calcul c = (Calcul) in.readObject();
                if (c.getOp().equals("E")) {
                    break;
                }
                double resultat;
                switch (c.getOp()) {
                    case "+": resultat = c.getX() + c.getY(); break;
                    case "-": resultat = c.getX() - c.getY(); break;
                    case "*": resultat = c.getX() * c.getY(); break;
                    case "/": resultat = c.getX() / c.getY(); break;
                    default:  resultat = Double.NaN;
                }
                compterEtAfficher("(client n°" + number + ") : "
                        + c.getX() + " " + c.getOp() + " " + c.getY() + " = " + resultat);
                out.writeDouble(resultat);
                out.flush();
            }
            System.out.println("Client n°" + number + " s'est déconnecté normalement.");
        } catch (StreamCorruptedException e) {
            System.out.println("Client n°" + number + " n'est pas un client Java (ex. Telnet) : connexion fermée.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client n°" + number+" déconnecté : " + e);
        }

    }
}
