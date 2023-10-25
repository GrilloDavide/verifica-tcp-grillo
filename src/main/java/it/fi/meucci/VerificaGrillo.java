package it.fi.meucci;

import java.io.IOException;
import java.net.ServerSocket;

public class VerificaGrillo {
    public static void main( String[] args ) throws IOException{

        Server myServer = new Server(new ServerSocket(6789));
        for(;;){
            myServer.serverStart();
            myServer.startComm();
        }
        
    }
}