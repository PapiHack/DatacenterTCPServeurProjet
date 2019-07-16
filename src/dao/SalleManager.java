package dao;

import java.util.ArrayList;
import java.sql.*;
import domaine.Salle;


/**
 * 
 * @author PapiH4ck3R
 * @since 11/07/19
 * @version 0.0.1
 * 
 * Classe effectuant les opérations CRUD sur les salles
 *  
 */
public class SalleManager implements SalleDAOInterface
{
	private Connection connexion;
	private PreparedStatement statement = null;
	private ResultSet result = null; 
	
	public SalleManager(Connection con) 
	{
		this.setConnexion(con);
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
	public void add(Salle salle) 
	{
		try 
		{
			this.statement = this.connexion.prepareStatement("INSERT INTO salle values(?, ?)");
			this.statement.setString(1, salle.getNumSalle());
			this.statement.setString(2, salle.getNomSalle());
			this.statement.executeUpdate();
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}

	@Override
	public ArrayList<Salle> findAll() 
	{
		 ArrayList<Salle> liste = new ArrayList<Salle>();
		 try 
		 {
		       this.statement = this.connexion.prepareStatement("SELECT * FROM salle");
		       this.result = this.statement.executeQuery();
		       while(this.result.next()) 
		       {
		    	   Salle salle = new Salle(this.result.getString("numsalle"), this.result.getString("nomsalle"));
		    	   liste.add(salle);
		       }
		 }
		 catch(Exception e) 
		 {
			 System.out.println(e.getMessage());
		 }
		return liste;
	}

	@Override
	public void update(Salle salle) 
	{
		try 
		{
			this.statement = this.connexion.prepareStatement("UPDATE salle SET nomsalle = ? WHERE numsalle = ?");
			this.statement.setString(1, salle.getNomSalle());
			this.statement.setString(2, salle.getNumSalle());
			this.statement.executeUpdate();
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}

	@Override
	public Salle findById(String numsalle) 
	{
		Salle salle = null;
		try 
		{
			this.statement = this.connexion.prepareStatement("SELECT * FROM salle WHERE numsalle = ?");
			this.statement.setString(1, numsalle);
			this.result = this.statement.executeQuery();
			if(this.result.next()) 
			{
				salle = new Salle(this.result.getString("numsalle"), this.result.getString("nomsalle"));
			}
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
		return salle;
	}

	@Override
	public void remove(String numsalle) 
	{
		try 
		{
			this.statement = this.connexion.prepareStatement("DELETE FROM salle WHERE numsalle = ?");
			this.statement.setString(1, numsalle);
			this.statement.executeUpdate();
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}

}
