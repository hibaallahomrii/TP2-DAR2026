package serverPackage;

import java.io.*;
import java.net.*;
import serializationPackage.Operation;

public class Server2_2 {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Le serveur est en attente de connexion...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connecté avec succès !");

            // Flux pour recevoir l'objet envoyé par le client
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Operation operation = (Operation) ois.readObject();

            System.out.println("Opération reçue : " + operation.getOp1() + " " + operation.getOperateur() + " " + operation.getOp2());

            // Traitement du calcul
            double res = 0;
            switch (operation.getOperateur()) {
                case '+':
                    res = operation.getOp1() + operation.getOp2();
                    break;
                case '-':
                    res = operation.getOp1() - operation.getOp2();
                    break;
                case '*':
                    res = operation.getOp1() * operation.getOp2();
                    break;
                case '/':
                    if (operation.getOp2() != 0) {
                        res = operation.getOp1() / operation.getOp2();
                    } else {
                        throw new ArithmeticException("Division par zéro interdite !");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Opérateur non supporté !");
            }

            // Stocker le résultat dans l'objet
            operation.setResultat(res);

            // Flux pour renvoyer l'objet au client
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(operation);
            System.out.println("Résultat envoyé au client : " + res);

            // Fermeture des flux et des sockets
            oos.close();
            ois.close();
            socket.close();
            serverSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}