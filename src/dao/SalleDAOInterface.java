package dao;

import java.util.*;
import domaine.Salle;

/**
 * 
 * @author PapiH4ck3R
 * @since 11/07/19
 * @version 0.0.1
 *  
 */
public interface SalleDAOInterface
{
	public void add(Salle salle);
	public ArrayList <Salle> findAll();
	public void update(Salle salle);
	public Salle findById(String numsalle);
	public void remove(String numsalle);
}
