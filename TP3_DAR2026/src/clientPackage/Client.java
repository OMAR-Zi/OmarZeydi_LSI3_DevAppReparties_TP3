package clientPackage;

import common.Calcul;
import java.io.*;
import java.net.*;

import java.util.List;
import java.util.Scanner;

import java.util.Locale;



public class Client {
    static final String IP_SERVEUR = "localhost";
    static final int PORT = 1234;

    public static void main(String[] args) throws IOException {
        String ip = (args.length > 0) ? args[0] : IP_SERVEUR;
        InetSocketAddress adresseServeur = new InetSocketAddress(InetAddress.getByName(ip), PORT);

        System.out.println("Je suis un client pas encore connecté…");
        try (Socket s = new Socket(); Scanner scanner = new Scanner(System.in)) {
            scanner.useLocale(Locale.US);
            s.connect(adresseServeur, 5000);
            System.out.println("je suis un client connecté à " + ip + ":" + PORT);
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            System.out.println("Serveur : " + br.readLine());

            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());

            while (true) {
                String op;
                do {
                    System.out.print("Operator (+ - * /, E to exit): ");
                    op = scanner.next();
                } while (!op.equals("E") && !List.of("+", "-", "*", "/").contains(op));

                if (op.equals("E")) {
                    out.writeObject(new Calcul(0, 0, "E"));
                    out.flush();
                    break;
                }

                System.out.print("First number: ");
                double first = scanner.nextDouble();

                double second;
                do {
                    System.out.print("Second number: ");
                    second = scanner.nextDouble();
                    if (op.equals("/") && second == 0) {
                        System.out.println("Cannot divide by zero.");
                    }
                } while (op.equals("/") && second == 0);

                out.writeObject(new Calcul(first, second, op));
                out.flush();
                System.out.println("Résultat : " + in.readDouble());
            }
        }
    }
    /*private static String lireLigne(InputStream in) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int c;
        while ((c = in.read()) != -1 && c != '\n') {
            if (c != '\r') buf.write(c);
        }
        return buf.toString(StandardCharsets.US_ASCII);
    }*/
}

