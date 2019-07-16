package dao;

import java.rmi.*;
import java.util.*;
import domaine.*;

/**
 * 
 * @author PapiH4ck3R
 * @since 11/07/19
 * @version 0.0.1
 *  
 */
public interface ServeurDAOInterface extends Remote 
{
	public void add(Serveur serveur);
	public ArrayList <Serveur> findAll();
	public ArrayList <Serveur> findAllServerBySalle(Salle salle);
	public ArrayList <Serveur> findAllServerByAdmin(Admin admin);
	public void update(Serveur serveur);
	public Serveur findById(String numServeur);
	public void remove(String numServeur);
}
