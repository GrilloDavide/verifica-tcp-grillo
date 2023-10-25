package it.fi.meucci;


import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main( String[] args ) throws IOException {
        
        Socket mioSocket = new Socket( InetAddress.getLocalHost() , 6789);
        
        DataOutputStream outServer = new DataOutputStream(mioSocket.getOutputStream());
        Scanner inServer = new Scanner (new InputStreamReader(mioSocket.getInputStream()));
        
        System.out.println( " Connessione effettuata " );

        Scanner inputTastiera = new Scanner(System.in);
        String outputString = " ";
        
        System.out.println("[SERVER] "+inServer.nextLine());
        System.out.println("[SERVER] "+inServer.nextLine());
        System.out.println("[SERVER] "+inServer.nextLine());
        do{
            
            System.out.print("[TU] ");outputString = inputTastiera.nextLine(); 
            outServer.writeBytes(outputString + "\n");
            System.out.println("[SERVER] "+inServer.nextLine());

        }while(!outputString.equals("TERMINA"));

        mioSocket.close();
        inputTastiera.close();
    }

}