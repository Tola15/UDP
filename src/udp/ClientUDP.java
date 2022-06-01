package udptime;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author franc
 */
public class Client {
	//numero di porta
	int port = 2000;
	//indirizzo del server
	InetAddress serverAddress;
	//socket UDP
	DatagramSocket dSocket;
	//Datagramma UDP con la richiesta da inviare al server
	DatagramPacket outPacket;
	//Datagramma UDP di risposta ricevuto dal server
	DatagramPacket inPacket;
    
	//buffer per i dati da inviare
   	 byte[] buffer;
   	 //messaggio di richiesta
   	 String message="RICHIESTA DATA E ORA";
   	 //messaggio di risposta
   	 String response;
           	 
	public Client(){ //costruttore del client per collegarsi al server
    	try {    
        	serverAddress = InetAddress.getLocalHost();
    	} catch (UnknownHostException ex) {
        	Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
    	}
    	System.out.println("Indirizzo del server trovato!");
	}
	public void Scrivi(){
    	try {
        	dSocket = new DatagramSocket();
        	//si prepara il datagramma con i dati da inviare
        	outPacket = new DatagramPacket(message.getBytes(), message.length(), serverAddress, port);
   		 
        	//si inviano i dati
        	dSocket.send(outPacket);
    	} catch (SocketException ex) {
        	Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
    	} catch (IOException ex) {
        	Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
    	}
	}
	public void Leggi(){
    	//si prepara il buffer di ricezione
    	buffer = new byte[256];
    	//e il datagramma UDP per ricevere i dati del buffer
    	inPacket = new DatagramPacket(buffer, buffer.length);
   	 
    	try {
        	//si accetta il datagramma di risposta
        	dSocket.receive(inPacket);
    	} catch (IOException ex) {
        	Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
    	}
   		 
    	//si estrae il messaggio
    	response = new String(inPacket.getData(), 0, inPacket.getLength());
   		 
	}
}

