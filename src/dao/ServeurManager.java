package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import domaine.*;

/**
 * 
 * @author PapiH4ck3R
 * @since 11/07/19
 * @version 0.0.1
 * 
 * Classe effectuant les opérations CRUD sur les serveurs
 *  
 */
public class ServeurManager implements ServeurDAOInterface
{
	private Connection connexion;
	private PreparedStatement statement = null;
	private ResultSet result = null; 
	private AdminManager adminManager;
	private SalleManager salleManager;
	
	public ServeurManager(Connection con) 
	{
		this.setConnexion(con);
		this.adminManager = new AdminManager(this.connexion);
		this.salleManager = new SalleManager(this.connexion);
	}
	
	public void setConnexion(Connection con) 
	{
		this.connexion = con;
	}
	
	public Connection getConnexion() 
	{
		return this.connexion;
	}

	@Override
	public void add(Serveur serveur)
	{
		try 
		{
			this.statement = this.connexion.prepareStatement("INSERT INTO serveur values(?, ?, ?, ?)");
			this.statement.setString(1, serveur.getNumServ());
			this.statement.setString(2, serveur.getNomServ());
			this.statement.setInt(3, serveur.getAdmin().getId());
			this.statement.setString(4, serveur.getSalle().getNumSalle());
			this.statement.executeUpdate();
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public ArrayList<Serveur> findAll()
	{
		 ArrayList<Serveur> liste = new ArrayList<Serveur>();
		 try 
		 {
		       this.statement = this.connexion.prepareStatement("SELECT * FROM serveur");
		       this.result = this.statement.executeQuery();
		       while(this.result.next()) 
		       {
		    	   Admin admin = this.adminManager.findById(this.result.getInt("admin"));
		    	   Salle salle = this.salleManager.findById(this.result.getString("salle"));
		    	   Serveur serveur = new Serveur(this.result.getString("numserv"), this.result.getString("nomserv"), admin, salle);
		    	   liste.add(serveur);
		       }
		 }
		 catch(Exception e) 
		 {
			 System.out.println(e.getMessage());
		 }
		return liste;
	}

	@Override
	public void update(Serveur serveur)
	{
		try 
		{
			this.statement = this.connexion.prepareStatement("UPDATE serveur SET nomserv = ?, admin = ?, salle = ? WHERE numserv = ?");
			this.statement.setString(1, serveur.getNomServ());
			this.statement.setInt(2, serveur.getAdmin().getId());
			this.statement.setString(3, serveur.getSalle().getNumSalle());
			this.statement.setString(4, serveur.getNumServ());
			this.statement.executeUpdate();
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public Serveur findById(String numServeur)
	{
		Serveur serveur = null;
		try 
		{
			this.statement = this.connexion.prepareStatement("SELECT * FROM serveur WHERE numserv = ?");
			this.statement.setString(1, numServeur);
			this.result = this.statement.executeQuery();
			if(this.result.next()) 
			{
				Admin admin = this.adminManager.findById(this.result.getInt("admin"));
				Salle salle = this.salleManager.findById(this.result.getString("salle"));
				serveur = new Serveur(this.result.getString("numserv"), this.result.getString("nomserv"), admin, salle);
			}
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
		return serveur;
	}

	@Override
	public void remove(String numServeur)
	{
		try 
		{
			this.statement = this.connexion.prepareStatement("DELETE FROM serveur WHERE numserv = ?");
			this.statement.setString(1, numServeur);
			this.statement.executeUpdate();
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public ArrayList<Serveur> findAllServerBySalle(Salle salle)
	{
		 ArrayList<Serveur> liste = new ArrayList<Serveur>();
		 try 
		 {
		       this.statement = this.connexion.prepareStatement("SELECT * FROM serveur WHERE salle = ?");
		       this.statement.setString(1, salle.getNumSalle());
		       this.result = this.statement.executeQuery();
		       while(this.result.next()) 
		       {
		    	   Admin admin = this.adminManager.findById(this.result.getInt("admin"));
		    	   Salle room = this.salleManager.findById(this.result.getString("salle"));
		    	   Serveur serveur = new Serveur(this.result.getString("numserv"), this.result.getString("nomserv"), admin, room);
		    	   liste.add(serveur);
		       }
		 }
		 catch(Exception e) 
		 {
			 System.out.println(e.getMessage());
		 }
		return liste;
	}

	@Override
	public ArrayList<Serveur> findAllServerByAdmin(Admin admin)
	{
		 ArrayList<Serveur> liste = new ArrayList<Serveur>();
		 try 
		 {
		       this.statement = this.connexion.prepareStatement("SELECT * FROM serveur WHERE admin = ?");
		       this.statement.setInt(1, admin.getId());
		       this.result = this.statement.executeQuery();
		       while(this.result.next()) 
		       {
		    	   Admin administrateur = this.adminManager.findById(this.result.getInt("admin"));
		    	   Salle room = this.salleManager.findById(this.result.getString("salle"));
		    	   Serveur serveur = new Serveur(this.result.getString("numserv"), this.result.getString("nomserv"), administrateur, room);
		    	   liste.add(serveur);
		       }
		 }
		 catch(Exception e) 
		 {
			 System.out.println(e.getMessage());
		 }
		return liste;
	}

}
