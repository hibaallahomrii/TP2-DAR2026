package clientPackage;

import java.io.*;
import java.net.*;
import serializationPackage.Operation;

public class Client2_2 {
    public static void main(String[] args) {
        try {
            System.out.println("Connexion au serveur...");
            Socket socket = new Socket("localhost", 1234);
            System.out.println("Connecté au serveur !");

            // Création de l'objet opération (Ex: 55 * 25)
            Operation operation = new Operation(55, '*', 25);

            // Envoyer l'objet au serveur
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(operation);
            System.out.println("Objet envoyé au serveur : 55 * 25");

            // Recevoir l'objet mis à jour (contenant le résultat) du serveur
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Operation operationRecue = (Operation) ois.readObject();

            // Affichage du résultat
            System.out.println("Résultat reçu du serveur ---> " + operationRecue.getResultat());

            // Fermeture des flux et de la socket
            ois.close();
            oos.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}