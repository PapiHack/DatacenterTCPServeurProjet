package service;

import java.sql.*;

/**
 * 
 * @author PapiH4ck3R
 * @since 11/07/19
 * @version 0.0.1
 *  
 * Classe ComponentManager permettant de se connecter à une base de donnée quelconque.
 * @param vDriver
 * @param vUrlDb
 * @param vUsername
 * @param vPassword
 * 
 */
public class DatabaseConnexionManager 
{
	
	private String driver;
	private String urlDB;
	private String username;
	private String password;
	
	/**
	 * 
	 * Classe ComponentManager permettant de se connecter à une base de donnée quelconque.
	 * @param vDriver
	 * @param vUrlDb
	 * @param vUsername
	 * @param vPassword
	 * 
	 */
	public DatabaseConnexionManager(String vDriver, String vUrlDb, String vUsername, String vPassword) 
	{
		this.setDriver(vDriver);
		this.setUrldb(vUrlDb);
		this.setUsername(vUsername);
		this.setPassword(vPassword);
	}
	
	/**
	 * 
	 * Fonction getDriver() permettant de retourner le driver.
	 * @return le driver 
	 * 
	 */
	public String getDriver()
	{
		return this.driver;
	}
	
	/**
	 * 
	 * Fonction getUrlDB() retournant l'adresse de la BD.
	 * @return l'url ou l'adresse de la base de donnée.
	 * 
	 */
	public String getUrlDB()
	{
		return this.urlDB;
	}
	
	/**
	 * 
	 * Fonction getUsername() permettant de retourner le nom d'utilisateur.
	 * @return le username
	 * 
	 */
	public String getUsername()
	{
		return this.username;
	}
	
	/**
	 * Méthode getPassword() permet de retourner le mot de passe.
	 * @return le mot de passe
	 */
	public String getPassword()
	{
		return this.password;
	}
	
	/**
	 * Méthode setDriver() prenant un paramétre de type string, permet de mettre à jour le driver.
	 * @param vDriver
	 */
	public void setDriver(String vDriver)
	{
		this.driver = vDriver;
	}
	
	/**
	 * Méthode setUrldb() permettant de changer l'adresse de la base de donnée. Elle prend en paramétre
	 * une variable de type string qui est la nouvelle adresse.
	 * @param urldb
	 */
	public void setUrldb(String urldb)
	{
		this.urlDB = urldb;
	}
	
	/**
	 * Méthode setUsername() permet de changer le nom d'utilisateur de la BD.
	 * @param vUsername
	 */
	public void setUsername(String vUsername)
	{
		this.username = vUsername;
	}
	
	/**
	 * Méthode setPassWord() permet de changer de mot de passe. 
	 * @param vPassword
	 */
	public void setPassword(String vPassword)
	{
		this.password = vPassword;
	}
	
	/**
	 * Méthode permettant d'établir la connection à une BD.
	 * Elle prend en paramétres le driver, l'url de la base de donnée, le username et le password.
	 * @param driverName
	 * @param urlName
	 * @param user
	 * @param pass
	 * @exception java.sql.SQLException est une exception SQL qui sera levée en cas de probléme au niveau 
	 * de la connection
	 * @throws ClassNotFoundException est une exception qui sera levée si un probléme survient au niveau 
	 * du driver     
	 * @return un objet connection à la base de donnée.
	 */
	public java.sql.Connection getConnexion(String driverName, String urlName, String user, String pass)
	{
		
		java.sql.Connection con = null; 
		
		try
		{
			Class.forName(driverName);
			con = DriverManager.getConnection(urlName, user, pass);
		}
		catch(java.sql.SQLException e)
		{
			System.out.println(e.getMessage());
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Driver introuvable !");
			e.printStackTrace();
		}
		
		return con;
	}

}
