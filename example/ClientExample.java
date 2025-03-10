package org.example;

import java.io.*;
import java.net.Socket;

public class ClientExample {

    public static void main(String[] args) {
        try {
            // Creem un socket que intenta connectar a localhost al port 5555
            Socket sck = new Socket("localhost", 8080);

            // Obtenim el OutputStream. És com el System.out però escriu al client en lloc de a la consola
            PrintWriter out = new PrintWriter(sck.getOutputStream(), true);

            // Obtenim l'InputStream. És com el Scanner però llegeix del socket.
            InputStream input = sck.getInputStream();

            // Equivalent al Scanner per a llegir de consola
            OutputStream output = sck.getOutputStream();



            // Mostra el missatge de benvinguda del servidor
            byte[] receivedData = new byte[3];
            int inputRead = input.read(receivedData);
            System.out.println("Read: [0] " + receivedData[0] + " [1] " + receivedData[1] + " [2] " + receivedData[2]);

            // Tot el text que escrivim a la consola, s'enviarà pel socket
            receivedData[0] = 2;
            while (inputRead != -1) {
                // Write
                output.write(receivedData);
                output.flush();
                System.out.println("Escribim: [0] " + receivedData[0] + " [1] " + receivedData[1] + " [2] " + receivedData[2]);

                // Read
                inputRead = input.read(receivedData);
                System.out.println("Read: [0] " + receivedData[0] + " [1] " + receivedData[1] + " [2] " + receivedData[2]);
            }

            out.close();
            sck.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
