/**
 * *********************************************************************
 * Projet                 : PRESTIJ - AUTOMATISATION IJ   
 * Package                : transfert.requetes
 * Class                  : RequeteBpijDetail.java 
 *----------------------------------------------------------------------               
 * Objet                  : Recherche détail BPIJ suivant paramètres 
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 29 05 2018 
 *********************************************************************** 
 */
package transfert.requetes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import transfert.outils.BoiteOutils;
import transfert.outils.ConnexionDriverJDBCIbm;

/**
 * @author tleguillou
 *
*/
public class RequeteBpijDetail {

	// Déclaration constantes
	private static final long serialVersionUID = 1L;
	
	// Tables SQL 
	private final String COUVERTURE = "COUSAPV";
	private final String ASSURE = "ASSURE";
	private final String SOCGESTIP = "FICHE_SOC";
	private final String BPIJPRESTATION = "BPIJA_PRES";
    
	// Variables SQL JDBC BPIJ
	private Statement stmtBPIJCumul;
	private ResultSet requeteCumul;
	private Statement stmtBPIJDetail;
	private ResultSet requeteDetail;
	private Statement stmtBPIJInfo;
	private ResultSet requeteInfo;
	
	private String queryBPIJDetail;
	private String queryBPIJCumul;
	private String queryBPIJInfo;

	// Variables en entrée
	private String serveur, login, mdp, bib;
    private String idoc, numss, siren, natass;
 	
	// Variables à renvoyées
	private boolean OK , trouve;
	private String MessageErreur;
	private String Total="0";
	private String Csoc="?";
	private String Nbjf="0";
	private String Raisoc="?";
	private String Cass="?";
	private String Nom="?";
	private String Prenom="?";
	private String Ddebut="?";
	private String Dfin="?";
	private String Option="?";
	private String Risque="?";	
	
	// Lignes détail prestation 
	private Vector<String> c21 = new Vector<String>();
	private Vector<String> c22 = new Vector<String>();
	private Vector<String> c23 = new Vector<String>();
	private Vector<String> c24 = new Vector<String>();
	private Vector<String> c25 = new Vector<String>();
	private Vector<String> c26 = new Vector<String>();
	private Vector<String> c27  = new Vector<String>();
	private Vector<String> c28 = new Vector<String>();
	private Vector<String> c29 = new Vector<String>();
	
	// Utilitaires
	BoiteOutils FormatDate = new BoiteOutils();
	
	// Ctor droit d'import
	public RequeteBpijDetail(String serveurP, String loginP,  String mdpP, 
			                 String bibP, String idocP, String numssP, String sirenP, String natassP) {
			                 
		this.serveur = serveurP;
		this.login   = loginP;
		this.mdp     = mdpP;
		this.bib     = bibP;
		this.idoc    = idocP;
        this.numss   = numssP;
        this.siren   = sirenP;
        this.natass  = natassP;
        
	} // end of Ctor

	// Lecture Ligne(s) Détail BPIJ
	public void LigneDetailBPIJ() {
        
		// Flag état recherche
		OK = true;
		queryBPIJDetail = "SELECT "
				          + "a.cna_prest as CPRES, a.libelle as LPRES, "
				          + "a.dtdebpr as DDEBPR,  a.dtfinpr as DFINPR, a.numsin as NUMSIN, "                    
				          + "a.nbij as NBIJ, a.ijsub as IJSUB, a.pu as PU, a.mt_pr as MT "
			              + "FROM " + BPIJPRESTATION + " a "	
				          + "WHERE "                
		                  + "a.nir = '" + numss + "' " 
				          + "AND "                           
				          + "a.id_ent = '" + idoc + "'"        
	                      + "AND "
				          + "a.sir_ident = '" + siren + "'"        
	                      + "AND "
				          + "a.cna_asrce = '" + natass + "'"        
				          + "ORDER BY "
                          + "a.id_ent, "
                          + "a.sir_ident, "
                          + "a.cd_cpam, "
                          + "a.nir, "
                          + "a.cna_asrce";
 		
		// Lecture détail prestation 
		try {
		
			stmtBPIJDetail = ConnexionDriverJDBCIbm.ouvrir(serveur,login, mdp, bib).createStatement();
			requeteDetail = stmtBPIJDetail.executeQuery(queryBPIJDetail);
			while (requeteDetail.next()) {
				
				c21.addElement(requeteDetail.getString("CPRES"));
				c22.addElement(requeteDetail.getString("LPRES"));
				try{c23.addElement(FormatDate.FormatPeriodeJJMMAA(requeteDetail.getTimestamp("DDEBPR").toString()));}
				catch (SQLException e) {c23.addElement("");}			
				try{c24.addElement(FormatDate.FormatPeriodeJJMMAA(requeteDetail.getTimestamp("DFINPR").toString()));}
				catch (SQLException e) {c24.addElement("");}			
				c25.addElement(requeteDetail.getString("NUMSIN"));
				c26.addElement(requeteDetail.getString("NBIJ"));
			    // a remplacer par un ternary
				if (requeteDetail.getString("IJSUB").equals("false")) {c27.addElement("Non");}
				else if (requeteDetail.getString("IJSUB").equals("true")) {c27.addElement("Oui");}
				else {c27.addElement("?");}
				
				c28.addElement(requeteDetail.getString("PU"));
				c29.addElement(requeteDetail.getString("MT"));
				
			}

			// Message d'information
			if (c21.size() != 0 ) {MessageErreur = "I" + c21.size() + " prestation(s) dans ce bordereau de paiement...";}
			if (c21.size() == 0 ) {MessageErreur = "E" + c21.size() + " prestation(s) dans ce Bordereau de paiement...";}

			requeteDetail.close();
			stmtBPIJDetail.close();
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur SQL Exception lecture detail prestation.... " + e);
			OK = false;
			MessageErreur = "EErreur SQL Exception lecture detail prestation.... " + e;
		}
	
	} // Fin de LectureLigneDetailBPIJ
	
	// Cumul Détail BPIJ
	public void CumulBPIJ() {
        
		// Flag état recherche
		OK = true;
		queryBPIJCumul = "SELECT "
		 	           + "sum(a.mt_pr) AS CUMUL "
			           + "FROM " + BPIJPRESTATION + " a "	
				       + "WHERE "                
		               + "a.nir = '" + numss + "' " 
				       + "AND "                           
				       + "a.id_ent = '" + idoc + "'"        
	                   + "AND "
				       + "a.sir_ident = '" + siren + "'"        
	                   + "AND "
				       + "a.cna_asrce = '" + natass + "'";        
 		
		// Lecture cumul  
		try {
		
			stmtBPIJCumul = ConnexionDriverJDBCIbm.ouvrir(serveur,login, mdp, bib).createStatement();
			requeteCumul = stmtBPIJCumul.executeQuery(queryBPIJCumul);
			while (requeteCumul.next()) {
				Total =requeteCumul.getString("CUMUL");
			}

			requeteCumul.close();
			stmtBPIJCumul.close();
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur SQL Exception cumul prestation.... " + e);
			OK = false;
			MessageErreur = "EErreur SQL Exception cumul prestation.... " + e;
		}
	
	} // Fin de CumulBPIJ

	// Informations BPIJ 
	public void InformationBPIJ(String type) {
        
		// Etat recherche
		OK = true ;
		
		//
		// ------> Cas des Siret normal
		//
		if (type.equals("N")) {
		   queryBPIJInfo = "SELECT "
		   	   	         + "a.csoccod AS CSOC, a.debeff AS DDEBUT, a.fineff AS DFIN, "
			 	         + "a.cntopt AS OPTION, a.cntrsq AS RISQUE, "
			 	         + "b.franchise AS NBJF, b.nom as RAISOC, "
			 	         + "c.abencod AS CASS, c.abennom AS NOM, c.abenprn AS PRENOM, "
			 	         + "c.assnum AS NSS "
			 	         + "FROM " + COUVERTURE + " a "
			 	         + "LEFT OUTER JOIN " + SOCGESTIP + " b "
			 	         + "ON "
			 	         + "a.csoccod = b.societe "
			 	         + "LEFT OUTER JOIN " + ASSURE + " c "
			 	         + "ON "
	   	                 + "a.cbencod = c.abencod " 
					     + "WHERE "                
			             + "c.assnum = '" + numss + "' " 
					     + "AND "                           
					     + "b.siren = '" + siren.substring(0, 9) + "'"        
		                    // + "AND "
		                    // + "b.active = 'O' "  
		                 + "ORDER BY FINEFF "
					     + "FETCH FIRST 1 ROW ONLY";        
		
		   try {
			   stmtBPIJInfo = ConnexionDriverJDBCIbm.ouvrir(serveur,login, mdp, bib).createStatement();
			   requeteInfo = stmtBPIJInfo.executeQuery(queryBPIJInfo);
			   while (requeteInfo.next()) {
				   trouve = true;
				   Csoc = requeteInfo.getString("CSOC");
				   Ddebut = FormatDate.FormatDateJJMMAA(requeteInfo.getString("DDEBUT"));
				   Dfin = FormatDate.FormatDateJJMMAA(requeteInfo.getString("DFIN"));
				   Option = requeteInfo.getString("OPTION");
				   Risque = requeteInfo.getString("RISQUE");
				   Nbjf = requeteInfo.getString("NBJF");
				   Raisoc = requeteInfo.getString("RAISOC");
				   Cass = requeteInfo.getString("CASS");
				   Nom = requeteInfo.getString("NOM");
				   Prenom = requeteInfo.getString("PRENOM");
			   }
			   requeteInfo.close();
			   stmtBPIJInfo.close();
		   }  
		   catch (SQLException e) {
			   // TODO Auto-generated catch block
			   System.out.println("Erreur SQL Exception informations SIREN normal BPIJ.... " + e);
			   OK = false;
			   MessageErreur = "EErreur SQL Exception informations SIREN normalBPIJ.... " + e;
		   }
	
		}
		
		//
		// ------> Cas des Siret fictif 
		//
		else {

			queryBPIJInfo = "SELECT "
			 	      + "a.abencod AS CASS, a.abennom AS NOM, a.abenprn AS PRENOM, "
			 	      + "a.assnum AS NSS "
	   	              + "FROM " + ASSURE + " a "
					  + "WHERE "                
			          + "a.assnum = '" + numss + "' " 
					  + "FETCH FIRST 1 ROW ONLY";        
		
		    try {
			    stmtBPIJInfo = ConnexionDriverJDBCIbm.ouvrir(serveur,login, mdp, bib).createStatement();
			    requeteInfo = stmtBPIJInfo.executeQuery(queryBPIJInfo);
			    while (requeteInfo.next()) {
				    Cass  = requeteInfo.getString("CASS");
				    Nom = requeteInfo.getString("NOM");
				    Prenom = requeteInfo.getString("PRENOM");
				    Csoc = "9999999";
                    Raisoc = "SIRET FICTIF (IJ non subrogées ou IJ multi-employeurs)";
			    }
			    requeteInfo.close();
			    stmtBPIJInfo.close();
		    } 
		    catch (SQLException e) {
			    // TODO Auto-generated catch block
			    System.out.println("Erreur SQL Exception informations BPIJ Siret fictif.... " + e);
			    OK = false;
			    MessageErreur = "EErreur SQL Exception informations BPIJ Siret fictif.... " + e;
		     }
		    
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
	
	//
	// getter CumulPrestation
	//
	public String getCumul() {
	 	return Total;
	}
	
	//
	// getter Code Societe
	//
	public String getCsoc() {
	 	return Csoc;
	}
	// getter Raison Sociale
	public String getRaisoc() {
	 	return Raisoc;
	}
	// getter Code Assuré
	public String getCass() {
	 	return Cass;
	}
	// getter Nom 
	public String getNom() {
	 	return Nom;
	}
	// getter Prenom 
	public String getPrenom() {
	 	return Prenom;
	}
	// getter Date debut
	public String getDdebut() {
	 	return Ddebut;
	}
	// getter Date fin
	public String getDfin() {
	 	return Dfin;
	}
	// getter Option
	public String getOption() {
	 	return Option;
	}
	// getter Risque 
	public String getRisque() {
	 	return Risque;
	}
	
	//
	// getter c21
	//
	public Vector<String> getc21() {
		return c21;
	}
	// getter c22
	public Vector<String> getc22() {
		return c22;
	}
	// getter c23
	public Vector<String> getc23() {
		return c23;
	}
	// getter c24
	public Vector<String> getc24() {
		return c24;
	}
	// getter c25
	public Vector<String> getc25() {
		return c25;
	}
	// getter c26
	public Vector<String> getc26() {
		return c26;
	}
	// getter c27
	public Vector<String> getc27() {
		return c27;
	}
	// getter c28
	public Vector<String> getc28() {
		return c28;
	}
	// getter c29
	public Vector<String> getc29() {
		return c29;
	}
	
}
