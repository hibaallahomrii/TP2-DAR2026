package serverPackage;

import java.io.*;
import java.net.*;

public class Server2_1 {
    public static void main(String[] args) {
        try {
            System.out.println("=== DÉMARRAGE DU SERVEUR (TP2 - Activité 2-1) ==  =");
            int port = 1234;

            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("127.0.0.1", port));
            System.out.println("Le serveur écoute sur le port " + port + "...");

            // Attente de la connexion du client
            Socket socket = serverSocket.accept();
            System.out.println("Client connecté avec succès !");

            // Flux de communication (Lecture et Écriture en mode texte)
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            // Réception de l'opération complète envoyée par le client (ex: "55 * 25")
            String operation = br.readLine();
            System.out.println("Opération reçue du client : " + operation);

            double resultat = 0;
            boolean erreur = false;
            String messageErreur = "";

            try {
                // Découpage de la chaîne reçue en utilisant l'espace comme séparateur
                // Exemple : "55 * 25" devient un tableau ["55", "*", "25"]
                String[] elements = operation.split(" ");

                if (elements.length == 3) {
                    double a = Double.parseDouble(elements[0]);
                    String operateur = elements[1];
                    double b = Double.parseDouble(elements[2]);

                    switch (operateur) {
                        case "+":
                            resultat = a + b;
                            break;
                        case "-":
                            resultat = a - b;
                            break;
                        case "*":
                            resultat = a * b;
                            break;
                        case "/":
                            if (b != 0) {
                                resultat = a / b;
                            } else {
                                erreur = true;
                                messageErreur = "Erreur : Division par zéro impossible !";
                            }
                            break;
                        default:
                            erreur = true;
                            messageErreur = "Erreur : Opérateur inconnu !";
                            break;
                    }
                } else {
                    erreur = true;
                    messageErreur = "Erreur : Format d'opération invalide (ex attendu : 55 * 25) !";
                }
            } catch (Exception e) {
                erreur = true;
                messageErreur = "Erreur : Syntaxe des nombres incorrecte !";
            }

            // Envoi de la réponse au client
            if (erreur) {
                pw.println(messageErreur);
            } else {
                pw.println("Le résultat est : " + resultat);
            }

            // Fermeture des connexions
            socket.close();
            serverSocket.close();
            System.out.println("Serveur arrêté proprement.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}