/**
 * **********************************************************************
 * Projet                 : TRANSFERT - Transfert des CRE entre système    
 * Package                : transfert.outils
 * Class                  : ConnexionDriverJDBCIbm.java  
 *------------------------------------------------ ----------------------               
 * Objet                  : Ouverture d'une connexion sur le serveur d'execution
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 24 06 2025 
 ********************************************************************* 
 */
package transfert.outils;

import java.sql.*;

public class ConnexionDriverJDBCIbm {

	private String serveurDB, login, mdp , bib;

	// Objets Connection
	private static Connection connect;
	private String DRIVER = "com.ibm.as400.access.AS400JDBCDriver";
	
	String jdbcURL = "jdbc:derby:courrierdb;create=true";
	
	
	
	// Constructeur privé jdbc... initie les connections
	private ConnexionDriverJDBCIbm(String ServeurDataBaseP, String LoginP, String MdpP, String bibP) {

		// Variables privées
		this.serveurDB = ServeurDataBaseP;
		this.login = LoginP;
		this.mdp = MdpP;
		this.bib = bibP;

		// Connection database IBM i via AS400JDBCDriver
		try {
			//Class.forName(DRIVER); A supprimer
			//connect = DriverManager.getConnection("jdbc:as400://" + serveurDB + "/" + bib , login, mdp);
			//System.out.println("Connexion **OK** sur jdbc:as400//" + serveurDB + "/" + bib);
			connect = DriverManager.getConnection(jdbcURL);	
			System.out.println("Connexion **OK** sur DataBase Derby");
		} 
		catch (Exception e) {
			System.out.println("Erreur SQL Exception JDBC" + e);
		}

	}

	// Retourne l'instance et la créée si elle n'existe pas
	public static Connection ouvrir(String ServeurDataBaseP, String LoginP, String MdpP, String bibP) {
		if (connect == null) {new ConnexionDriverJDBCIbm(ServeurDataBaseP, LoginP, MdpP, bibP);}
		return connect;
	}
    
	// Fermeture connexion
	public static void fermer() throws Exception {
		try {
			if (connect != null) {
				connect.close();
				connect = null;
				//System.out.println("Fermeture connexion ** OK ** Serveur " );
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Fermeture connexion ** KO !**  " + e);
		}

		return;
	}

} // Fin de class ConnexionDriverJDBCIbm