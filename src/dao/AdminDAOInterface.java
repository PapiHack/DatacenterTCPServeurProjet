package dao;

import java.util.*;
import domaine.Admin;

/**
 * 
 * @author PapiH4ck3R
 * @since 11/07/19
 * @version 0.0.1
 *  
 */
public interface AdminDAOInterface
{
	public void add(Admin admin);
	public ArrayList <Admin> findAll();
	public void update(Admin admin);
	public Admin findById(int id);
	public void remove(int id);
}
