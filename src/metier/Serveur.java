package metier;

import java.sql.Connection;

import dao.*;
import domaine.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

import service.DatabaseConnexionManager;

/**
 * 
 * @author PapiH4ck3R
 * @since 11/07/19
 * @version 0.0.1
 *  
 */
@SuppressWarnings("serial")
public class Serveur extends JFrame implements ActionListener
{
	 private JTextArea zonerecep;
	 private JButton qt;
	 private JPanel pan1,pan2;
	 
	 public Serveur() 
	 {

	     this.zonerecep = new JTextArea(15,40);
	     this.qt = new JButton("Quitter");
	     this.setTitle(" Serveur TCP Multiclients");
	     this.pan1 = new JPanel();
	     this.pan2 = new JPanel();
	     this.pan1.add(new JScrollPane(zonerecep));
	     this.qt.addActionListener(this);
	     this.pan2.add(qt);
	     this.add(pan1,BorderLayout.CENTER);
	     this.add(pan2,BorderLayout.SOUTH);
	     this.setTitle("Serveur TCP Swing");
	     this.setSize(500,600);
	     this.setLocationRelativeTo(null);
	     this.setVisible(true);
	     
	     try
		    {
		        @SuppressWarnings("resource")
				ServerSocket serveur = new ServerSocket(8000);
		        this.zonerecep.append("Le Serveur a démarré... "+"\n");
		        int numclient=1;
		        while(true)
		        {
		            Socket socket = serveur.accept();
		            InetAddress adr = socket.getInetAddress();
		            String ipclient = adr.getHostAddress();
		            String nomclient= adr.getHostName();
		            zonerecep.append("Client n°" + numclient + " - Adresse IP:" + ipclient + "\n");
		            zonerecep.append("Nom machine cliente: " + nomclient + "\n");
		            Service s = new Service(socket);
		            s.start();
		            numclient++;
		        }

		    }
		    catch(IOException ex)
		    {
		        System.out.println(ex.getMessage());
		    }
	 }
	 
	 //CLASSE INTERNE
	 class Service extends Thread 
	 {
		 private DatabaseConnexionManager dbConnexionManager;
		 private Connection connexion;
		 private  Socket socket;
		 private AdminManager adminManager;
		 private SalleManager salleManager;
		 private ServeurManager serveurManager;
		 
		 public Service(Socket socket) 
		 {
			 this.socket = socket;
			 this.dbConnexionManager = new DatabaseConnexionManager("jdbc:mysql", "localhost/datacenter", "root", "");
			 this.connexion = this.dbConnexionManager.getConnexion("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/datacenter", "root", "");
			 this.adminManager = new AdminManager(this.connexion);
			 this.salleManager = new SalleManager(this.connexion);
			 this.serveurManager = new ServeurManager(this.connexion);
		 }
		 
		 public void run() 
		 {
			 try 
			 {
				 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		         ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		         @SuppressWarnings("unused")
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		         DataInputStream in = new DataInputStream(socket.getInputStream());
		       	
		         String mode;
		         
		         do 
		         {
		        	 mode = String.valueOf(ois.readObject());
		             zonerecep.append("Mode en cours d'expoitation: ==> " + mode + " <==\n"); 
		             
		             /*-------------- C.R.U.D Admin --------------*/
		             
		             if(mode.equals("Ajout d'admin")) 
		             {
		            	 this.adminManager.add((Admin) ois.readObject());
		             }
		             else if(mode.equals("Listing d'admin")) 
		             {
		            	 oos.writeObject(this.adminManager.findAll());
		            	 oos.flush();
		             }
		             else if(mode.equals("Modification d'admin")) 
		             {
		            	 this.adminManager.update((Admin) ois.readObject());
		             }
		             else if(mode.equals("Recherche d'admin")) 
		             {
		            	 oos.writeObject(this.adminManager.findById(in.readInt()));
		            	 oos.flush();
		             }
		             else if(mode.equals("Suppression d'admin")) 
		             {
		            	 this.adminManager.remove(in.readInt());
		             }
		             
		             /*-------------- C.R.U.D Salle --------------*/

		             else if(mode.equals("Ajout de salle")) 
		             {
		            	 this.salleManager.add((Salle) ois.readObject());
		             }
		             else if(mode.equals("Listing de salle")) 
		             {
		            	 oos.writeObject(this.salleManager.findAll());
		            	 oos.flush();
		             }
		             else if(mode.equals("Modification de salle")) 
		             {
		            	 this.salleManager.update((Salle) ois.readObject());
		             }
		             else if(mode.equals("Recherche de salle")) 
		             {
		            	 oos.writeObject(this.salleManager.findById(ois.readObject().toString()));
		            	 oos.flush();
		             }
		             else if(mode.equals("Suppression de salle")) 
		             {
		            	 this.salleManager.remove(ois.readObject().toString());
		             }
		             
		             /*-------------- C.R.U.D Serveur --------------*/

		             else if(mode.equals("Ajout de serveur")) 
		             {
		            	 this.serveurManager.add((domaine.Serveur) ois.readObject());
		             }
		             else if(mode.equals("Listing de serveur")) 
		             {
		            	 oos.writeObject(this.serveurManager.findAll());
		            	 oos.flush();
		             }
		             else if(mode.equals("Modification de serveur")) 
		             {
		            	 this.serveurManager.update((domaine.Serveur) ois.readObject());
		             }
		             else if(mode.equals("Recherche de serveur")) 
		             {
		            	 oos.writeObject(this.serveurManager.findById(ois.readObject().toString()));
		            	 oos.flush();
		             }
		             else if(mode.equals("Suppression de serveur")) 
		             {
		            	 this.serveurManager.remove(in.readUTF());
		             }
		             else if(mode.equals("Listing de serveur par admin")) 
		             {
		            	 oos.writeObject(this.serveurManager.findAllServerByAdmin((Admin) ois.readObject()));
		            	 oos.flush();
		             }
		             else if(mode.equals("Listing de serveur par salle")) 
		             {
		            	 oos.writeObject(this.serveurManager.findAllServerBySalle((Salle) ois.readObject()));
		            	 oos.flush();
		             }
		             else if (mode.equals("Fin"))
	                 {
	                      zonerecep.append("Connexion terminée pour un client !\n");
	                      oos.flush();
	                     
	                 }
		         }
		         while(true);
			 }
			 catch(Exception e) 
			 {
				 //System.out.println("*******" + e.getMessage());
				 e.printStackTrace();
			 }
		 }
		 
	 } // Fin de la classe interne

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
        dispose();
        System.exit(0);
	}
	
	public static void main(String args[]) 
	{
		new Serveur();
	}
}
