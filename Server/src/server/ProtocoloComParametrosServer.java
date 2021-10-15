/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lucas
 */
public class ProtocoloComParametrosServer {

    // Server Soket
    private ServerSocket serverSoket;

    // cria um novo socket em uma Instância
    private void getServerSoket(int port) throws IOException {
        serverSoket = new ServerSocket(port);
    }

    // ouve o socket até que haja uma nova conexão para ganhar uma nova instância
    private Socket waitConnection() throws IOException {
        Socket socket = serverSoket.accept();
        return socket;
    }

    //irá fechar o socket
    private void closeSocket(Socket socket) throws IOException {

        socket.close();
    }

    //---------------------------------------------------------------
    // enviar ou receber mensagem de acordo com o protoloco
    private void handleConnection(Socket socket) throws IOException {
        ObjectOutputStream output = null;
        ObjectInputStream input = null;

        try {

            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            String msg = input.readUTF();
            System.out.println("Mensagem recebida...");
            output.writeUTF("HELLOW WORD!!!");

            // liberrando o buffer par envio
            output.flush();
            // fechando input e output
            input.close();
            output.close();

        } catch (IOException ex) {
            System.out.println("Erro de tratamento de conexão: " + socket.getInetAddress());
            System.out.println(ex.getMessage());
        } finally {
            // final do tratamento do protocolo - encerrar o socket 
            closeSocket(socket);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            ProtocoloComParametrosServer server = new ProtocoloComParametrosServer();

            // solicita uma nova conexão
            server.getServerSoket(5555);

            // repetição para que o servidor possa atender mais de um cliente
            while (true) {

                // espera que tenha uma conexão para ganhar uma nova instância
                Socket socket = server.waitConnection();    // protocolo de comunição para receber ou enviar mensagem
                System.out.println("Conexão realizada");

                // tratando o protocolo do protocolo de comunicação
                server.handleConnection(socket);
                System.out.println("Protocolo executado ");
            }
        } catch (IOException ex) {
            Logger.getLogger(ProtocoloComParametrosServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
