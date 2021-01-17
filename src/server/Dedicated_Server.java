package server;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * DATE: 17/01/2021
 * AUTHOR: Maccagni Giacomo
 * DESCRIPTION: This is the Dedicated Server that will manage
 * the connection with a single client;
 */
public class Dedicated_Server implements Runnable {

    private Socket clientSocket;

    public Dedicated_Server(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        start_interaction_with_client();
    }

    private void start_interaction_with_client() {

        //DECLARE variables
        PrintWriter writer = null;
        BufferedReader reader = null;
        String incoming_message = null;
        String dedicated_server_name = null;
        String name = null;

        try {

            dedicated_server_name = "Dedicated server " + Thread.currentThread().getId() + " - ";

            //Initialize the writer on the socket
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            //Initialize the reader on the socket
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //Start messaging
            Thread.sleep(3000);
            writer.println(dedicated_server_name + "Hello!");

            Thread.sleep(3000);
            writer.println(dedicated_server_name + "What is your name?");

            //Wait the name from the client
            name = reader.readLine();

            Thread.sleep(3000);
            writer.println(dedicated_server_name + "Hello " + name);
            System.out.println(dedicated_server_name + "Started a session with " + name);

            Thread.sleep(3000);
            writer.println(dedicated_server_name + "How are you? What are you doing?");

            //Wait the response from the client
            incoming_message = reader.readLine();

            Thread.sleep(3000);
            writer.println(dedicated_server_name + "Sounds good!");

            Thread.sleep(3000);
            writer.println(dedicated_server_name + "Now it is " + getTimeNow());

            Thread.sleep(3000);
            writer.println(dedicated_server_name + " Send me the QUIT word in order to terminate the session or send me what you want and i will " +
                    "save all your messages inside my repository");


            while (!incoming_message.equals("QUIT")) {

                //Wait the response from the client
                incoming_message = reader.readLine();

                //SAVE message
                save_message("Data/"+name+Thread.currentThread().getId()+"/","messages",incoming_message, true);

            }//end while cycle over incoming_message

            Thread.sleep(3000);
            writer.println("QUIT received, so the session will be terminated");
            System.out.println(dedicated_server_name + "QUIT received, so the session will be terminated with " + name);

        } catch (Exception e) {
            System.err.println("Comunication error");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            writer.close();
            if (!clientSocket.isClosed()) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }//end of method start_interaction_with_client(...)

    /**
     * This function is used to parse milliseconds in date time in the FORMAT yyyy-MM-dd_HH.mm.ss
     */
    private static String getTimeNow() throws Exception {
        try {
            SimpleDateFormat time_formatter_V1 = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
            return time_formatter_V1.format(System.currentTimeMillis());
        } catch (Exception e) {
            throw e;
        }
    }//end of method getTimeNow(...)

    /**
     * This function is used to save a message inside a TXT file;
     * <p>
     * If append=true the new lines will be added to the existing file;
     * If append=false a new file will be created;
     * <p>
     * The function returns 1 if is correctly executed;
     * The function returns 0 if an error occurred;
     */
    public static int save_message(final String path,
                                   final String fileName, String message,
                                   boolean append) {
        //DECLARE variables
        BufferedWriter bw = null;
        FileWriter fw = null;
        File f_pathDirectory = null;
        String methodName = null;

        try {

            //CREATE path directory if does not exists
            f_pathDirectory = new File(path);
            if (f_pathDirectory.exists() == false) {
                f_pathDirectory.mkdirs();
            }

            fw = new FileWriter(path + fileName + ".txt", append);
            bw = new BufferedWriter(fw);

            bw.write(getTimeNow() + " - " + message);
            bw.newLine();

            return 1;

        }//end of try block
        catch (Exception e) {

            return 0;

        } finally {
            try {

                //FREE variables
                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

                bw = null;
                fw = null;
                f_pathDirectory = null;

            } catch (Exception e) {
                return 0;
            }
        }//end of finally block

    }//end of method save_message(...)

}//end of class Dedicated_Server
