/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lucas
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ObjectOutputStream output = null;
        ObjectInputStream input = null;

        try {
            //estabelecer conexão entre o Cliente e Servidor
            Socket socket = new Socket("localhost", 5555);

            //streams de entrada e saída
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            ClientObject cliente = new ClientObject();

            // enviando mensagem ao socket
            output.writeObject(cliente);

            // liberando o buffer par envio
            output.flush();

            // esperando resposta
            String msg = input.readUTF();
            System.out.println("Resposta: " + msg);

            output.close();
            input.close();
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
