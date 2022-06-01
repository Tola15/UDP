package udptime;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**  
 *
 * @author franc
 */
public class Server {
    
   		 /* porta del server maggiore di 1024
   	  * oppure la porta 13 standard del protocollo Daytime
   	  */
   	 int port=2000;
   	 
   	 //oggetto Socket UDP
   	 DatagramSocket dSocket;
   	 //datagramma UDP ricevuto dal client
   	 DatagramPacket inPacket;
   	 //datagramma UDP di risposta da inviare
   	 DatagramPacket outPacket;
   	 //Buffer per il contenuto del segmento da ricevere
   	 byte[] buffer;
   	 //Testo dei messaggi in I/O
   	 String messageIn, messageOut;
   	 //Data e ora correnti
   	 Date d;
           	 
	public Server(){
    	try {
        	//si crea il socket e si associa alla porta specifica
        	dSocket = new DatagramSocket(port);
    	} catch (SocketException ex) {
        	Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
    	}
    	System.out.println("Apertura porta in corso!");
	}
    
	public void lettura(){
    	buffer = new byte[256];
   	 
    	//si crea un datagramma UDP in cui trasportare il buffer di lunghezza length
    	inPacket = new DatagramPacket(buffer, buffer.length);
   			 
    	try {
        	//si ricevono i byte dal client e si blocca finchè arrivano i pacchetti
        	dSocket.receive(inPacket);
    	} catch (IOException ex) {
        	Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
    	}
   			 
    	//si recupera l'indirizzo IP e la porta UDP del client
    	InetAddress clientAddress = inPacket.getAddress();
    	int clientPort = inPacket.getPort();
   			 
    	//si stampa a video il messaggio ricevuto dal client
    	messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
    	System.out.println("SONO IL CLIENT " + clientAddress +
                        	":" + clientPort + "> " + messageIn);
   			 
	}
    
    
    
    
	public void scrittura(){
    	//si recupera l'indirizzo IP e la porta UDP del client
    	InetAddress clientAddress = inPacket.getAddress();
    	int clientPort = inPacket.getPort();
   	 
    	d = new Date();
    	//si crea il messaggio del server in uscita associandolo alla connessione aperta con il client
    	messageOut = d.toString();
   			 
    	//si crea un datagramma UDP in cui trasportare il messaggio di lunghezza length
    	outPacket = new DatagramPacket(messageOut.getBytes(), messageOut.length(), clientAddress, clientPort);
                	try {
                    	//si invia il messaggio al client
                    	dSocket.send(outPacket);
                	} catch (IOException ex) {
                    	Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                	}
	}
