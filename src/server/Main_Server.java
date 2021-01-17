package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * DATE: 17/01/2021
 * AUTHOR: Maccagni Giacomo
 * DESCRIPTION: This is the Main Server that will manage all incoming connections;
 */
public class Main_Server {

    private static final int port = 4445;

   public static void main(String[] args) {
        start_listening();
    }

    /**
     * This method is used to start listening for incoming connections;
     */
    private static void start_listening() {

        //DECLARE variables
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        Dedicated_Server dedicated_server = null;
        Thread thread = null;


        try {
            //Start the Main Server on listening on new incoming connections
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("I can not start listening on port: "+port);
            System.exit(1);
        }

        //Manage incoming connections and start Dedicated Server
        while(true){

            try {
                //Start listening for new incoming connections
                System.out.println("Listening for new incoming connections");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed");
                System.exit(1);
            }

            //When a new incoming connection is accepted the execution arrives here
            System.out.println("New incoming connection is arrived");

            //Initialize a DedicatedServer and pass it to a Thread
            dedicated_server = new Dedicated_Server(clientSocket);
            thread = new Thread(dedicated_server);
            thread.start();

            try {
                //Sleep some seconds in order to let the thread to start
                Thread.sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
                System.exit(1);
            }

            //Then return into listening phase

        }//end while cycle over manage incoming connection

        /*
        if(!serverSocket.isClosed()){
            try { serverSocket.close();} catch (IOException e) {e.printStackTrace();}
        }*/

    }//end of method start_listening(...)

}//end of class Main_Server

