package dao;

import java.util.ArrayList;
import java.sql.*;
import domaine.Admin;


/**
 * 
 * @author PapiH4ck3R
 * @since 11/07/19
 * @version 0.0.1
 * 
 * Classe effectuant les opérations CRUD sur les administrateurs
 *  
 */
public class AdminManager implements AdminDAOInterface
{
	private Connection connexion;
	private PreparedStatement statement = null;
	private ResultSet result = null; 
	
	public AdminManager(Connection con) 
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
	public void add(Admin admin)
	{
		try 
		{
			this.statement = this.connexion.prepareStatement("INSERT INTO admin (nom, prenom) values(?, ?)");
			this.statement.setString(1, admin.getNom());
			this.statement.setString(2, admin.getPrenom());
			this.statement.executeUpdate();
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}

	@Override
	public ArrayList<Admin> findAll() 
	{
		 ArrayList<Admin> liste = new ArrayList<Admin>();
		 try 
		 {
		       this.statement = this.connexion.prepareStatement("SELECT * FROM admin");
		       this.result = this.statement.executeQuery();
		       while(this.result.next()) 
		       {
		    	   Admin admin = new Admin(this.result.getString("nom"), this.result.getString("prenom"));
		    	   admin.setId(Integer.parseInt(this.result.getString("id")));
		    	   liste.add(admin);
		       }
		 }
		 catch(Exception e) 
		 {
			 System.out.println(e.getMessage());
		 }
		return liste;
	}

	@Override
	public void update(Admin admin)
	{
		try 
		{
			this.statement = this.connexion.prepareStatement("UPDATE admin SET nom = ?, prenom = ? WHERE id = ?");
			this.statement.setString(1, admin.getNom());
			this.statement.setString(2, admin.getPrenom());
			this.statement.setInt(3, admin.getId());
			this.statement.executeUpdate();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public Admin findById(int id)
	{
		Admin admin = null;
		try 
		{
			this.statement = this.connexion.prepareStatement("SELECT * FROM admin WHERE id = ?");
			this.statement.setInt(1, id);
			this.result = this.statement.executeQuery();
			if(this.result.next()) 
			{
				admin = new Admin(this.result.getString("nom"), this.result.getString("prenom"));
				admin.setId(Integer.parseInt(this.result.getString("id")));
			}
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
		return admin;
	}

	@Override
	public void remove(int id)
	{
		try 
		{
			this.statement = this.connexion.prepareStatement("DELETE FROM admin WHERE id = ?");
			this.statement.setInt(1, id);
			this.statement.executeUpdate();
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
		
	}

}
