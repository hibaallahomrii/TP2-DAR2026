package clientPackage;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client2_1 {
    public static void main(String[] args) {
        try {
            System.out.println("=== CLIENT DISTANT (TP2 - Activité 2-1) ===");

            String ipDuServeur = "127.0.0.1";
            int port = 1234;

            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ipDuServeur, port));
            System.out.println("Connecté au serveur avec succès !");

            Scanner scanner = new Scanner(System.in);

            // Demande d'une opération complète à l'utilisateur sous forme de chaîne de caractères
            System.out.print("\nEntrez l'opération complète (ex: 55 * 25) : ");
            String operation = scanner.nextLine();

            // Envoi de la chaîne de caractères au serveur via PrintWriter
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println(operation);

            // Réception de la réponse du serveur
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String reponse = br.readLine();
            System.out.println("Réponse reçue du serveur -> " + reponse);

            // Fermeture des flux et de la socket
            scanner.close();
            socket.close();
            System.out.println("Client déconnecté.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}