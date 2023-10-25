package it.fi.meucci;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Server {
    
    ServerSocket server;
    Socket client;
    BufferedReader inClient;
    DataOutputStream outClient;
    int clientNumber;

    public Server(ServerSocket server) throws IOException{
        this.server = server;
        
    }

    public void serverStart() throws IOException {
        
            System.out.println( " Server in attesa di un client " );
            Socket client = server.accept();
            inClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outClient = new DataOutputStream(client.getOutputStream());
            clientNumber = client.getPort();
            System.out.println("Il client "+clientNumber+" si è collegato");

    }

    public void startComm() throws IOException {

        String inputString;
        double outputNumber;
        
        outClient.writeBytes("--CALCOLATRICE--\n");
        outClient.writeBytes("Inserire l'operazione matematica da compiere per la sua interezza\n");
        outClient.writeBytes("Esempio: scrivere '5 + 5' correttamente diviso dagli spazi\n");


        for(;;){
            double inputNumber1 = 0;
            double inputNumber2 = 0;
            String inputOperator = "";

            inputString = inClient.readLine();

            if(inputString.equals("TERMINA")){
                outClient.writeBytes("Chiusura comunicazione\n");
                break;
            }

            System.out.println("[CLIENT] "+inputString);

            try {
                
                inputNumber1 = (double)Integer.parseInt(inputString.split(" ")[0]);
                inputNumber2 = (double)Integer.parseInt(inputString.split(" ")[2]);
                inputOperator = inputString.split(" ")[1];
                System.out.println("prova 1");
                if(!checkOnOperator(inputOperator)){
                    System.out.println("Errore: Operatore non valido; deve essere '+', '-', '/' o '*' \n");
                    outClient.writeBytes("Errore: Operatore non valido; deve essere '+', '-', '/' o '*' \n");
                    break;
                }
                
                outputNumber = operation(inputOperator, inputNumber1, inputNumber2);
                
                if(Double.isNaN(outputNumber)){
                    outClient.writeBytes(outputNumber+"; zero fratto zero non restituisce un numero\n");
                    
                }else if(Double.isInfinite(outputNumber)){
                    outClient.writeBytes(outputNumber+"; una divisione per zero fara' infinito\n");
                    
                }else{
                    outClient.writeBytes(outputNumber+"\n");
                }
                    
                
            } catch ( ArithmeticException e ){
                System.out.println("Errore: Impossibile dividere per zero");
                outClient.writeBytes("Errore: Impossibile dividere per zero\n");
                e.printStackTrace();
                break;
            } catch ( Throwable e ){
                System.out.println("Errore: Formato non corretto dell'operazione");
                outClient.writeBytes("Errore: Formato non corretto dell'operazione\n");
                e.printStackTrace();
            }      
        }

        System.out.println("Termine connessione con "+clientNumber);
    }

    public boolean checkOnOperator(String inputOperator){
        switch(inputOperator){
            case "+":
                
            case "-":
               
            case "/":
                
            case "*":
                return true;
                
            default:
                return false;
        }
    }

    public double operation(String inputOperator, double inputNumber1, double inputNumber2) throws ArithmeticException{
        
        switch(inputOperator){
            case "+":
                return inputNumber1 + inputNumber2;
            case "-":
                return inputNumber1 - inputNumber2;
                
            case "/":
                return inputNumber1 / inputNumber2;
                
            case "*":
                return inputNumber1 * inputNumber2;
                
            default:
                return -1;//se il codice è arrivato qui, è impossibile che entri nel default
        }


    }
}


