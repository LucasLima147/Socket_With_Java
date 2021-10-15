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

        /**
         * A criação do output deve vir antes do input: -> quando o output for
         * criado, do servidor, eles irão sincronizar para a criação do
         * cabeçalho
         *
         */
        ObjectOutputStream output = null;
        ObjectInputStream input = null;

        try {
            //estabelecer conexão entre o Cliente e Servidor
            Socket socket = new Socket("localhost", 5555);

            //streams de entrada e saída
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            // enviando mensagem ao socket
            String msg = "HELLOW";
            output.writeUTF(msg);

            // liberrando o buffer par envio
            output.flush();

            // esperando resposta
            msg = input.readUTF();
            System.out.println("Resposta: " + msg);

            output.close();
            input.close();
            socket.close();

            /**
             * O PROTOCOLO DEVE SER SEGUIDO DE FORMA EXATA QUE FOI CONSTRUIDO!!!
             * Ex: se o protocolo diz que precisa receber 5 mensagens, deve ser
             * enviado exatamente 5 mensagem para obter uma respota.
             *
             * Caso fizer algo diferente do protocolo, o programa ficará parado,
             * pois o processo ficará travado (Deadlock)
             *
             */
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
