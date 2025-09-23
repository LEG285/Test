/**
 * *********************************************************************
 * Projet                 : PRESTIJ - AUTOMATISATION IJ   
 * Package                : transfertoutils
 * Class                  : RequeteDroitAcces.java 
 *----------------------------------------------------------------------               
 * Objet                  : Lecture des droits d'accès applicatif par profil 
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 27 03 2018 
 *********************************************************************** 
 */
package transfertoutils;

import java.sql.*;
import java.util.*;

/**
 * @author tleguillou
 *
 */
public class RequeteDroitAcces {

	// Déclaration constantes
	private static final long serialVersionUID = 1L;

	// Variables SQL JDBC
	private Statement stmt;
	private ResultSet requetedroit;
	private String query;

	// Variables en entrée
	private String serveur;
	private String login;
    private String mdp;
    private String bib;
	
	// Variables renvoyées
	private boolean OK;
	private Vector<String> utilisateur = new Vector<String>();
	private Vector<String> programme = new Vector<String>();

	// Ctor droit d'import
	public RequeteDroitAcces(String serveurP, String loginP,  String mdpP, String bibP) {
		
		this.serveur = serveurP;
		this.login = loginP;
		this.mdp = mdpP;
		this.bib = bibP;
		
	} // end of Ctor

	// Lecture des droits associé au login
	public void LectureDroit() {

		// Flag état recherche
		OK    = true;
		
		// Chargement des options relatives à ce profil  
		query = "SELECT a.utlnom, a.prgnom FROM DRTPRF a";
		query += " WHERE a.utlnom = '" + login.toUpperCase() + "' AND";
		query += " a.prgnom like 'WEBPR%'";
		
		try {
			// Lecture
			stmt = ConnexionDriverJDBCIbm.ouvrir(serveur,login, mdp, bib).createStatement();
			requetedroit = stmt.executeQuery(query);
			while (requetedroit.next()) {
				utilisateur.addElement(requetedroit.getString(1));
				programme.addElement(requetedroit.getString(2));
			}
			requetedroit.close();
			stmt.close();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur SQL Exception... " + e);
			OK = false;
		}
	}

	// getter OK/KO
	public boolean getOKKO() {
		return OK;
	}

	// getter utilisateur
	public Vector<String> getutilisateur() {
		return utilisateur;
	}

	// getter programme
	public Vector<String> getprogramme() {
		return programme;
	}

}
