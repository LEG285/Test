/**
 * *********************************************************************
 * Projet                 : PRESTIJ - AUTOMATISATION IJ   
 * Package                : transfertoutils
 * Class                  : RequeteFluxGestip.java 
 *----------------------------------------------------------------------               
 * Objet                  : Recherche des flux GESTIP suivant critères saisis 
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 18 04 2018 
 *----------------------------------------------------------------------- 
 * MODIF : MANTIS1957  AUTEUR : TLE            DATE : 03/07/2018       
 * OBJET : Modification recherche Statut du CR GESTIP pour prendre en compte 
 *         le statut par siren/nir  
 *********************************************************************** 
 */
package transfert.requetes;

import java.sql.*;
import java.util.*;

import transfert.outils.BoiteOutils;
import transfert.outils.ConnexionDriverJDBCIbm;


/**
 * @author tleguillou
 *
 */
public class RequeteFluxGestip {

	// Déclaration constantes
	private static final long serialVersionUID = 1L;
	
	// Tables GESTIP
    private final String TBLSOCIETE ="GEST_SOCA"; 
	private final String TBLASSURE = "GEST_ASSUA";
	private final String TBLCOUVERTURE = "GEST_COUVA";
	private final String TBLINFO = "FICHE_INFO";
	
	// Table ARLGESTIP et CRGESTIP
    private final String TBLARL = "ARL_DGARL";	
    private final String TBLCR = "CR_DGDIAG";
    
	// Variables SQL JDBC GESTIP
	private Statement stmtGESTIP;
	private ResultSet requeteflux;
	private String queryGESTIP;

	// Variables en entrée
	private String serveur;
	private String login;
    private String mdp;
    private String bib;
    private String parametres[] = new String[8];
    private String recap_selection; 
	
	// Variables renvoyées
	private boolean OK;
	private String MessageErreur;
	private Vector<String> c11 = new Vector<String>();
	private Vector<String> c21 = new Vector<String>();
	private Vector<String> c31 = new Vector<String>();
	private Vector<String> c41 = new Vector<String>();
	private Vector<String> c51 = new Vector<String>();
	private Vector<String> c61 = new Vector<String>();
	private Vector<String> c71 = new Vector<String>();
	private Vector<String> c81 = new Vector<String>();
	
	private Vector<String> c32 = new Vector<String>();
	private Vector<String> c42 = new Vector<String>();
	private Vector<String> c62 = new Vector<String>();
	private Vector<String> c72 = new Vector<String>();
	private Vector<String> c82 = new Vector<String>();
	
	private Vector<String> c43 = new Vector<String>();
	private Vector<String> c44 = new Vector<String>();
	private Vector<String> c45 = new Vector<String>();

	// Variables travails 
	private String madatedebut;
	private String madatefin;
	
	private String StatutRetourARL;
	private String NiveauRetourARL;
	private String LibelleRetourARL;
	
	private String StatutRetourCR;
	private String NiveauRetourCR;
	private String LibelleRetourCR;
	
	// Utilitaires
	BoiteOutils FormatDate = new BoiteOutils();
	
	// Ctor droit d'import
	public RequeteFluxGestip(String serveurP, String loginP,  String mdpP, String bibP, String formulaireP[]) {
		
		this.serveur = serveurP;
		this.login = loginP;
		this.mdp = mdpP;
		this.bib = bibP;
	    for (int i = 0; i < formulaireP.length; i++) { parametres[i] = formulaireP[i]; }
		
	} // end of Ctor

	// Lecture GESTIP
	public void LectureGestip() {

		
	    // Clear vectors (*all)
		// Ligne 1
        c11.removeAllElements(); 
        c21.removeAllElements();
        c31.removeAllElements();
        c41.removeAllElements();
        c51.removeAllElements();
        c61.removeAllElements(); 
        c71.removeAllElements();
        c81.removeAllElements();
        
        // Ligne 2
        c32.removeAllElements();
        c42.removeAllElements();
        c62.removeAllElements();
        c72.removeAllElements();
        c82.removeAllElements();        
        
        // Ligne 3/4/5
        c43.removeAllElements();
        c44.removeAllElements();
        c45.removeAllElements();
		
		// Flag état recherche
		OK    = true;

		// Initialisation Requete QRY
		madatedebut = FormatDate.FormatPeriodeAAMMJJ(parametres[5]) ;
		madatefin = FormatDate.FormatPeriodeAAMMJJ(parametres[6]);
          
		queryGESTIP = "SELECT "
                     + "d.code as CodeOrg, d.libelle as Deleg, "                    
                     + "a.idfluxe as Idflux, a.idfluxd as Idfichier, "      
                     + "a.codea as Assure,  a.codes as Societe, "                              
                     + "a.nir as Numss,  a.contextea as typeASS, " 
                     + "a.nom as Nom, a.prenom as Prenom, "
                     + "b.codes as Csociete, b.siret as Siret, b.contextes as TypeSOC, "      
                     + "c.idcouver as Idcouv, c.contextec as TypeCOUV, "
                     + "c.datedebut as Dtdebut, c.datefin as Dtfin, c.datedebret as DtRetro, " 
                     + "a.dateextrac as Datedemande, a.datexml as Datexml "
                     + "FROM " + TBLASSURE + " a " 
                     + "LEFT OUTER JOIN " + TBLSOCIETE + " b "      
                     + "ON "                                                                  
                     + "b.codes = a.codes AND " 
                     + "b.idfluxe = a.idfluxe AND "                     
                     + "b.idfluxd = a.idfluxd AND "
                     + "b.organisme = a.organisme "                 
                     + "LEFT OUTER JOIN " + TBLCOUVERTURE + " c "                                
                     + "ON "                                                                 
                     + "c.codes = a.codes AND " 
                     + "c.idfluxe = a.idfluxe AND "                     
                     + "c.idfluxd = a.idfluxd AND "
                     + "c.organisme = a.organisme AND "             
                     + "c.codea = a.codea "                                                  
                     + "LEFT OUTER JOIN " + TBLINFO + " d "                                
                     + "ON d.code = a.organisme";                                              
 
		// Sélection / assuré  
		if (!parametres[0].equals("")) {

			queryGESTIP = queryGESTIP   
			            + " WHERE a.codea = " + parametres[0]     
			            + " AND " 
			            + "a.dateextrac BETWEEN '" + madatedebut + "' CONCAT ' 00:00:00'"
			            + " AND "
			            + "'" + madatefin + "' CONCAT ' 23:59:59'";                         

			// Récap sélection Assuré
		    recap_selection = " pour assur\351 " + parametres[0] + " du " + parametres[5] + " au " + parametres[6]; 
		
		}
		// Sélection / N° SS 
		else if (!parametres[1].equals("")) {

			queryGESTIP = queryGESTIP
				       	+ " WHERE a.NIR = '" + parametres[1] + "'"
			            + " AND" 
			            + " a.dateextrac BETWEEN '" + madatedebut + "' CONCAT ' 00:00:00'"
			            + " AND "
			            + "'" + madatefin + "' CONCAT ' 23:59:59'";
			
			// Récap sélection N° SS
		    recap_selection = " pour Num\351ro SS " + parametres[1] + " du " + parametres[5] + " au " + parametres[6]; 
			
		}
		// Sélection Nom - Prenom 
		else if (!parametres[2].equals("")) {
			
			// Requete par Nom uniquement 
			if (parametres[3].equals("")) {  // Requete par Nom uniquement
				
			   queryGESTIP = queryGESTIP 
					       + " WHERE a.NOM like '" + parametres[2] + "%'"
      		               + " AND" 
					       + " a.dateextrac BETWEEN '" + madatedebut + "' CONCAT ' 00:00:00'"
					       + " AND "
					       + "'" + madatefin + "' CONCAT ' 23:59:59'";
			   
				// Récap sélection nom
			    recap_selection = " pour " + parametres[2] + " du " + parametres[5] + " au " + parametres[6]; 
			   
 			}
			else {                           // Requete par Nom et Prenom
				
			   queryGESTIP = queryGESTIP
				           + " WHERE a.NOM like '" + parametres[2] + "%'"
					       + " AND "  
				           + " a.PRENOM = '" + parametres[3] + "'" 
				           + " AND" 
				           + " a.dateextrac BETWEEN '" + madatedebut + "' CONCAT ' 00:00:00'"
				           + " AND "
				           + "'" + madatefin + "' CONCAT ' 23:59:59'";

				// Récap sélection nom-prénom
			    recap_selection = " pour " + parametres[2] + " " + parametres[3] + " du " + parametres[5] + " au " + parametres[6]; 
			   
 			}
			 
		}
		
		// Sélection Siren - Siret 
		else if (!parametres[4].equals("")) {
			
			queryGESTIP = queryGESTIP 
					    + " WHERE b.siret like '" + parametres[4] + "%'"
			            + " AND" 
   		                + " a.dateextrac BETWEEN '" + madatedebut + "' CONCAT ' 00:00:00'"
					    + " AND "
					    + "'" + madatefin + "' CONCAT ' 23:59:59'";
			
			// Récap sélection SIRET 
			recap_selection = " pour Siren/Siret " + parametres[4] + " du " + parametres[5] + " au " + parametres[6]; 

		}		
		
		// Order by chrono descendant  
		//queryGESTIP = queryGESTIP + " ORDER BY a.dateextrac desc"; 
		            
		// Lecture tables SIRIUS
		try {
		
			// Lecture
			stmtGESTIP = ConnexionDriverJDBCIbm.ouvrir(serveur,login, mdp, bib).createStatement();
			requeteflux = stmtGESTIP.executeQuery(queryGESTIP);
			while (requeteflux.next()) {
				
				// Recherche Etat ARLGESTIP (Recherche avec l'Id fichier)
				StatutRetourARL  = " ";
				NiveauRetourARL  = " ";
				LibelleRetourARL = " ";
				rechercheEtatARL(requeteflux.getString("CodeOrg") + "-" + requeteflux.getString("Idflux"));
				
				// Recherche Etat CRGESTIP (Recherche avec l'Id fichier, le siret et le NIR)
				StatutRetourCR  = " ";
				NiveauRetourCR  = " ";
				LibelleRetourCR = " ";
				rechercheEtatCR(requeteflux.getString("CodeOrg") + "-" + requeteflux.getString("Idfichier"), 
						        requeteflux.getString("Siret"),
						        requeteflux.getString("Numss"));
				
				// Filtrer l'affichage suivant l'état du statut
				if (parametres[7].equals("Tous")) {setVectorsAffichage();}
				else if (parametres[7].equals(NiveauRetourARL + " " + StatutRetourARL)) {setVectorsAffichage();}
				else if (parametres[7].equals(NiveauRetourCR + " " + StatutRetourCR)) {setVectorsAffichage();}
			
			}
			
			if (c11.size() != 0 ) {MessageErreur = "I" + c11.size() + " résultat(s) trouvé(s)" + recap_selection + " avec statut " + parametres[7];}
			if (c11.size() == 0 ) {MessageErreur = "E" + c11.size() + " résultat(s) trouvé(s)" + recap_selection + " avec statut " + parametres[7];}
			
			requeteflux.close();
			stmtGESTIP.close();
			
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur SQL Exception... " + e);
			OK = false;
			MessageErreur = "EErreur SQL Exception... " + e;
		}
	
	
	} // Fin de LectureGestip

	// Rechercher état du dernier ARL reçu
	public void rechercheEtatARL(String Identifiant) {
		
		// Initialisation requêtes 
     	String queryARL = "SELECT "
                 + "prof_diag, id_diag, tps_diag, activite, "
				 + "etat, cause, libelle, date_trt "        
             	 + "FROM " + TBLARL + " " 
				 + "WHERE id_diag= '" + Identifiant + "' "
         		 + "ORDER BY tps_diag DESC "
				 + "FETCH FIRST 1 ROW ONLY";		
		
		// Lecture ARLGESTIP
		try {
			
			Statement stmtARL = ConnexionDriverJDBCIbm.ouvrir(serveur,login, mdp, bib).createStatement();
			ResultSet requeteARL = stmtARL.executeQuery(queryARL);
			while (requeteARL.next()) {
				
				// Récupération Statut
				if      (requeteARL.getString("etat").equals("A")) {StatutRetourARL = "Accepté";}
				else if (requeteARL.getString("etat").equals("R")) {StatutRetourARL = "Rejeté"; }		
				
				// Récupération Libelle		
				LibelleRetourARL = requeteARL.getString("libelle");
				
				// Niveau ARL
				NiveauRetourARL = "ARL";
				
			}
			requeteARL.close();
			stmtARL.close();
   		    
   		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur SQL Exception... " + e);
			OK = false;
			MessageErreur = "EErreur SQL Exception... " + e;
		}
	
	} // Fin etat ARL 

	// Rechercher état du dernier CR reçu
	public void rechercheEtatCR(String Identifiant, String Entreprise, String Salarie) {
	
		// Initialisation requête
		String queryCR = "SELECT "
                 + "organisme, id_diag, activite, etat, "
				 + "cause, libelle, ent_op, ent_ident "
                 + "sal_op, sal_nir, couv_op, couv_id, "
                 + "couv_ret, date_trt "
             	 + "FROM " + TBLCR + " " 
				 + "WHERE id_diag= '" + Identifiant + "' AND "
				 + "ent_ident = '" + Entreprise + "' AND " 
				 + "sal_nir = '" + Salarie + "' "
	     		 + "ORDER BY date_trt DESC, etat desc";
		
		// Lecture CRGESTIP 
		try {
			
			Statement stmtCR = ConnexionDriverJDBCIbm.ouvrir(serveur,login, mdp, bib).createStatement();
			ResultSet requeteCR = stmtCR.executeQuery(queryCR);
			while (requeteCR.next()) {
				
				// Récupération Statut
				if      (requeteCR.getString("etat").equals("A")) {StatutRetourCR = "Accepté";}
				else if (requeteCR.getString("etat").equals("R")) {StatutRetourCR = "Rejeté"; }
				
				// Récupération Libelle		
				LibelleRetourCR = requeteCR.getString("libelle");
				
				// Niveau CR
				NiveauRetourCR = "CR";
				
				break;
				
			}
			requeteCR.close();
			stmtCR.close();
   		    
   		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur SQL Exception... " + e);
			OK = false;
			MessageErreur = "EErreur SQL Exception... " + e;

		}
	
	} // Fin etat CR

	
	// setter vectors affichage
	public void setVectorsAffichage() {

		try {
			
			// Ligne 1
			
			c11.addElement(requeteflux.getBigDecimal("Assure").toString());
			c21.addElement(requeteflux.getString("Numss"));
			c31.addElement(requeteflux.getString("Nom"));
			c41.addElement(requeteflux.getString("CodeOrg") + "-" + requeteflux.getString("Idflux"));
			c51.addElement(FormatDate.FormatPeriodeJJMMAA(requeteflux.getTimestamp("Datedemande").toString()));
			c61.addElement(StatutRetourARL);
			c71.addElement(NiveauRetourARL);
			c81.addElement(LibelleRetourARL);

			// Ligne 2
			c32.addElement(requeteflux.getString("Prenom"));
			c42.addElement("IP-" + requeteflux.getString("Deleg"));
			c62.addElement(StatutRetourCR);
			c72.addElement(NiveauRetourCR);
			c82.addElement(LibelleRetourCR);

			// Ligne 3
			c43.addElement("SIREN-" + requeteflux.getString("Siret") + " / " + requeteflux.getString("TypeSOC"));

			// Ligne 4
			c44.addElement("NIR-" + requeteflux.getString("Numss") + " / " + requeteflux.getString("typeASS"));

			// Ligne 5
			c45.addElement(requeteflux.getString("Idcouv") + " / " + requeteflux.getString("TypeCOUV") + " / D" +
					       FormatDate.FormatDateJJMMAA(requeteflux.getDate("Dtdebut")) + " / F" + 
					       FormatDate.FormatDateJJMMAA(requeteflux.getDate("Dtfin")) + " / R" +
			               FormatDate.FormatDateJJMMAA(requeteflux.getDate("DtRetro")));

		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur SQL Exception... " + e);
			OK = false;
			MessageErreur = "EErreur SQL Exception... " + e;
		}
		
	}
	
	// getter OK/KO
	public boolean getOKKO() {
		return OK;
	}
	// getter MessageErreur
	public String getMessageErreur() {
		return MessageErreur;
	}
	// getter c11
	public Vector<String> getc11() {
		return c11;
	}
	// getter c21
	public Vector<String> getc21() {
		return c21;
	}
	// getter c31
	public Vector<String> getc31() {
		return c31;
	}
	// getter c41	
	public Vector<String> getc41() {
		return c41;
	}
	// getter c51
	public Vector<String> getc51() {
		return c51;
	}
	// getter c61
	public Vector<String> getc61() {
		return c61;
	}
	// getter c71
	public Vector<String> getc71() {
		return c71;
	}
	// getter c81
	public Vector<String> getc81() {
		return c81;
	}
	// getter c32
	public Vector<String> getc32() {
		return c32;
	}
	// getter c42	
	public Vector<String> getc42() {
		return c42;
	}
	// getter c62
	public Vector<String> getc62() {
		return c62;
	}
	// getter c72	
	public Vector<String> getc72() {
		return c72;
	}
	// getter c82	
	public Vector<String> getc82() {
		return c82;
	}
	// getter c43	
	public Vector<String> getc43() {
		return c43;
	}
	// getter c44	
	public Vector<String> getc44() {
		return c44;
	}
	// getter c45	
	public Vector<String> getc45() {
		return c45;
	}
	
}
