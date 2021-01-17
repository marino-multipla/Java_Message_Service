package client;

import server.Dedicated_Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * DATE: 17/01/2021
 * AUTHOR: Maccagni Giacomo
 * DESCRIPTION: This is the Message Client Server that will
 * be used in order to interact with the Message Service;
 */
public class Message_Client {

    private static final int port = 4445;
    private static final String host = "127.0.0.1";

    public static void main(String[] args) {

        /*
         * Remember to allow multiple instances of a class inside IntelliJ configuration and
         * start instances form the code page.
         */

        start_client();
    }

    /**
     * This method is used to start client;
     */
    private static void start_client() {

        //DECLARE variables
        Socket socket = null;
        PrintWriter socket_writer = null;
        BufferedReader socket_reader = null;
        String incoming_message = null;
        String my_message = null;

        try {
            //Create connection with the Main Server
            socket = new Socket(host , port);

            //Initialize the reader on the socket
            socket_reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //Initialize the writer on the socket
            socket_writer = new PrintWriter(socket.getOutputStream(), true);

            //Initialize input buffer to input command line
            BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Wait message from Dedicated Server");
            incoming_message = socket_reader.readLine();
            System.out.println(incoming_message);

            incoming_message = socket_reader.readLine();
            System.out.println(incoming_message);

            //Write your name
            try {
                my_message = inputBuffer.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            socket_writer.println(my_message);
            socket_writer.flush();

            incoming_message = socket_reader.readLine();
            System.out.println(incoming_message);

            incoming_message = socket_reader.readLine();
            System.out.println(incoming_message);

            //Write what are you doing
            try {
                my_message = inputBuffer.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            socket_writer.println(my_message);
            socket_writer.flush();

            incoming_message = socket_reader.readLine();
            System.out.println(incoming_message);

            incoming_message = socket_reader.readLine();
            System.out.println(incoming_message);

            incoming_message = socket_reader.readLine();
            System.out.println(incoming_message);

            while(!my_message.equals("QUIT")){

                //Write what are you doing
                try {
                    my_message = inputBuffer.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                socket_writer.println(my_message);
                socket_writer.flush();

            }//end while cycle over my_message

            //WAIT last message
            incoming_message = socket_reader.readLine();
            System.out.println(incoming_message);

            try { inputBuffer.close(); } catch (IOException e) { }

        } catch (UnknownHostException e) {
            System.err.println("Host "+host+" non conosciuto");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Connessione a "+host+" non riuscita");
            System.exit(1);
        }finally{ //Alla fine chiudo sempre il reader, il writer e il socket
            try { socket_reader.close(); } catch (IOException e) {}
            socket_writer.close();
            if(!socket.isClosed()){
                try {socket.close(); } catch (IOException e) {}
            }
        }

    }//end of method start_client(...)

}//end of class Message_Client
