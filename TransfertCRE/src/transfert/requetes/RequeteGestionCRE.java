/* Projet                 : TRANSFERT - Transfert automatique des CRE    
 * Package                : transfert.requetes
 * Class                  : RequeteGestionCRE.java 
 *----------------------------------------------------------------------               
 * Objet                  : Lecture des CRE s�lectionn�s et transfert vers le syst�me choisi 
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 19 06 2025 
 *----------------------------------------------------------------------- 
 *********************************************************************** 
 */
package transfert.requetes;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

import transfert.outils.BoiteOutils;
import transfert.outils.ConnexionDriverJDBCIbm;

/**
 * @author tleguillou
 *
 */
public class RequeteGestionCRE {

	// D�claration constantes
	private static final long serialVersionUID = 1L;
	
	// Tables des CRE 
    private final String TBLBCOUSFE = "BCOUSFE"; 
	private final String TBLBCOUSFD = "BCOUSFD";
	
	// Variables SQL JDBC 
 	private static ConnexionDriverJDBCIbm Connexion = null;   
	private Statement stmtlectCRE;
	private Statement stmtinsertCRE;
	private Statement stmtcompteurCRE;
 	
 	// BCOUSFE
	private ResultSet requetelectCRE;
	private ResultSet requetecompteurCRE;
	private String querylectCRESelect;
	private String querylectCRESelectCount;
	private String querylectCRE;
	private boolean TrouveCRE; 

	// Variables SQL JDBC CRE INSERT
	private ResultSet requeteinsertCRE;
	private boolean TrouveCREInsert; 
	
	// Variables en entr�e
	private String serveur;
	private String login;
    private String mdp;
    private String bib;
	private String serveurC;
	private String loginC;
    private String mdpC;
    private String bibC;
    
    private String parametres[] = new String[7];
	
	// Variables de travail 
	private boolean OK;
	private int CompteurLu;
	private int CompteurEcrit;
	private final int COMPTEURCREPERMIS  = 20;
	private String MessageErreur;
	private String MadateCreation;
	
	// Champs entete BCOUSFE
	private Vector<BigDecimal> bcousfeFECCOU = new Vector<BigDecimal>();                   
	private Vector<String> bcousfeFEYCOU = new Vector<String>();   
	private Vector<String> bcousfeFELNCOU = new Vector<String>(); 
	private Vector<BigDecimal> bcousfeFENADH = new Vector<BigDecimal>(); 
	private Vector<BigDecimal> bcousfeFENACT = new Vector<BigDecimal>() ;
	private Vector<String> bcousfeFELNDEST = new Vector<String>();
	private Vector<String> bcousfeFEUTILI = new Vector<String>();
	private Vector<String> bcousfeFEIMPR = new Vector<String>() ;
	private Vector<BigDecimal> bcousfeFENENT = new Vector<BigDecimal>() ;
	private Vector<BigDecimal> bcousfeFENSEN = new Vector<BigDecimal>() ;
	private Vector<BigDecimal> bcousfeFENCPT = new Vector<BigDecimal>() ;
	private Vector<String> bcousfeFEYDES  = new Vector<String>();
	private Vector<java.sql.Date> bcousfeFEDCRT = new Vector<java.sql.Date>() ;
	private Vector<java.sql.Date> bcousfeFEDARC = new Vector<java.sql.Date>();  
	private Vector<java.sql.Time> bcousfeFEHEUR = new Vector<java.sql.Time>();
	private Vector<String> bcousfeFETRTTP = new Vector<String>();
	private Vector<String> bcousfeFETRTBT = new Vector<String>();
	private Vector<BigDecimal> bcousfeFENTPA = new Vector<BigDecimal>();
	private Vector<String> bcousfeFECCDI = new Vector<String>();
	private Vector<String> bcousfeFEREFEX = new Vector<String>();
	private Vector<BigDecimal> bcousfeFENAGC = new Vector<BigDecimal>();
	private Vector<String> bcousfeFETYPR = new Vector<String>();
	private Vector<String> bcousfeFESPEC = new Vector<String>();

	// champs detail BCOUSFD
	private Vector<BigDecimal> bcousfdFECCOU = new Vector<BigDecimal>(); 
	private Vector<String> bcousfdFECBCOU = new Vector<String>();
	private Vector<BigDecimal> bcousfdFEVERS = new Vector<BigDecimal>();
	private Vector<BigDecimal> bcousfdFENLIGC = new Vector<BigDecimal>();
	private Vector<String> bcousfdFENIBLO = new Vector<String>();
	private Vector<BigDecimal> bcousfdFENBBLO = new Vector<BigDecimal>();
	private Vector<BigDecimal> bcousfdFELGBLO = new Vector<BigDecimal>();
	private Vector<String> bcousfdFEZDETCOU = new Vector<String>();
	
	// Variables de travail 
	private java.sql.Date Datenulle = java.sql.Date.valueOf("0001-01-01");  // = 0001-01-01
	private java.sql.Time Timenulle = java.sql.Time.valueOf("00:00:00");    // = 00:00:00
	
	// Utilitaires
	BoiteOutils FormatDate = new BoiteOutils();
	
	// Ctor RequeteGestionCRE
	public RequeteGestionCRE(String serveurLec, String loginLec, String mdpLec, String bibLec,
			                 String serveurInsert, String loginInsert,  String mdpInsert, String bibInsert, 
			                 String formulaireP[]) {
		
		this.serveur      = serveurLec;
		this.login        = loginLec;
		this.mdp          = mdpLec;
		this.bib          = bibLec;
		this.serveurC     = serveurInsert;
		this.loginC       = loginInsert;
		this.mdpC         = mdpInsert;
		this.bibC         = bibInsert;
	    this.TrouveCRE    = false;
	    this.CompteurLu   = 0;
		this.CompteurEcrit= 0;
	    for (int i = 0; i < formulaireP.length; i++) {parametres[i] = formulaireP[i];}
	    
	}   // end of Ctor

	// -------------------------------------------------------------------------------------	 
    // Lecture CRE Autres Crit�res
	// --------------------------------------------------------------------------------------	 
	public void lectureCREAutreCriteres() {
 
	    // CLEAR variables 
		// BCOUSFE
        bcousfeFECCOU.removeAllElements();        
 		bcousfeFEYCOU.removeAllElements();      
 		bcousfeFELNCOU.removeAllElements(); 
		bcousfeFENADH.removeAllElements();  
 		bcousfeFENACT.removeAllElements();  
		bcousfeFELNDEST.removeAllElements();
		bcousfeFEUTILI.removeAllElements(); 
		bcousfeFEIMPR.removeAllElements();  
		bcousfeFENENT.removeAllElements();  
		bcousfeFENSEN.removeAllElements();  
		bcousfeFENCPT.removeAllElements();  
		bcousfeFEYDES.removeAllElements();  
        bcousfeFEDCRT.removeAllElements();  
        bcousfeFEDARC.removeAllElements();    
 		bcousfeFEHEUR.removeAllElements();  
		bcousfeFETRTTP.removeAllElements(); 
  	    bcousfeFETRTBT.removeAllElements(); 
		bcousfeFENTPA.removeAllElements(); 
		bcousfeFECCDI.removeAllElements();  
		bcousfeFEREFEX.removeAllElements();
		bcousfeFENAGC.removeAllElements();  
		bcousfeFETYPR.removeAllElements();  
		bcousfeFESPEC.removeAllElements();
		
	    // BCOUSFD
	    bcousfdFECCOU.removeAllElements(); 
	    bcousfdFECBCOU.removeAllElements();
	    bcousfdFEVERS.removeAllElements();
	    bcousfdFENLIGC.removeAllElements();
	    bcousfdFENIBLO.removeAllElements();
	    bcousfdFENBBLO.removeAllElements();
	    bcousfdFELGBLO.removeAllElements();
	    bcousfdFEZDETCOU.removeAllElements();		
		
		OK = true;
		
		// Construction requetes 
		querylectCRESelect = "SELECT * FROM " + bib + "." + TBLBCOUSFE + " ";
		querylectCRESelectCount = "SELECT COUNT(*) FROM " + bib + "."+ TBLBCOUSFE + " ";;
		// 1) TYPE COURRIER / ADHERENT
		if (!parametres[2].equals("") && !parametres[3].equals("")) {
			querylectCRE = "WHERE FEYCOU = '" + parametres[2] + "' "
            	          + "AND FENADH = '" + parametres[3] + "'"; 
		}
        // 2) TYPE COURRIER / DATE 
		if (!parametres[2].equals("") && !parametres[4].equals("")) {
			MadateCreation = FormatDate.FormatPeriodeAAMMJJ(parametres[4]) ;
			querylectCRE = "WHERE FEYCOU = '" + parametres[2] + "' "
            	           + "AND FEDCRT = '" + MadateCreation + "'"; 
		}		
		// 3) ADHERENT / DATE
		if (!parametres[3].equals("") && !parametres[4].equals("")) {
			MadateCreation = FormatDate.FormatPeriodeAAMMJJ(parametres[4]) ;
			querylectCRE = "WHERE FENADH = '" + parametres[3] + "' "
            	           + "AND FEDCRT = '" + MadateCreation + "'"; 
		} 
		// 4) TYPE COURRIER / ADHERENT / DATE 
		if (!parametres[2].equals("") && !parametres[3].equals("") && !parametres[4].equals("")) {
			MadateCreation = FormatDate.FormatPeriodeAAMMJJ(parametres[4]) ;
			querylectCRE = "WHERE FEYCOU = '" + parametres[2] + "' "
            	           + "AND FENADH = '" + parametres[3] + "' "
            	           + "AND FEDCRT = '" + MadateCreation + "'"; 
		}
		
        if (compteurCREavantLecture(querylectCRESelectCount + querylectCRE) <= COMPTEURCREPERMIS ) { 
		
		   // Requetes 
		   try {
			   // Ouverture connexion sur serveur source & Lecture BCOUSFE 
			   stmtlectCRE = ConnexionDriverJDBCIbm.ouvrir(serveur,login, mdp, bib).createStatement();
			   requetelectCRE = stmtlectCRE.executeQuery(querylectCRESelect + querylectCRE);
			   while (requetelectCRE.next()) {
				   bcousfeFECCOU.addElement(requetelectCRE.getBigDecimal(1));                  
				   bcousfeFEYCOU.addElement(requetelectCRE.getString(2));  
				   bcousfeFELNCOU.addElement(requetelectCRE.getString(3)); 
				   bcousfeFENADH.addElement(requetelectCRE.getBigDecimal(4));
				   bcousfeFENACT.addElement(requetelectCRE.getBigDecimal(5));
				   bcousfeFELNDEST.addElement(requetelectCRE.getString(6));
			  	   bcousfeFEUTILI.addElement(requetelectCRE.getString(7));
			   	   bcousfeFEIMPR.addElement(requetelectCRE.getString(8));
			 	   bcousfeFENENT.addElement(requetelectCRE.getBigDecimal(9));
				   bcousfeFENSEN.addElement(requetelectCRE.getBigDecimal(10));
				   bcousfeFENCPT.addElement(requetelectCRE.getBigDecimal(11));
				   bcousfeFEYDES.addElement(requetelectCRE.getString(12));
				   bcousfeFEDCRT.addElement(requetelectCRE.getDate(13));
				   bcousfeFEDARC.addElement(requetelectCRE.getDate(14));
				   bcousfeFEHEUR.addElement(requetelectCRE.getTime(15));
				   bcousfeFETRTTP.addElement(requetelectCRE.getString(16));
				   bcousfeFETRTBT.addElement(requetelectCRE.getString(17));
				   bcousfeFENTPA.addElement(requetelectCRE.getBigDecimal(18));
				   bcousfeFECCDI.addElement(requetelectCRE.getString(19));
				   bcousfeFEREFEX.addElement(requetelectCRE.getString(20));
				   bcousfeFENAGC.addElement(requetelectCRE.getBigDecimal(21));
				   bcousfeFETYPR.addElement(requetelectCRE.getString(22));
				   bcousfeFESPEC.addElement(requetelectCRE.getString(23));
				   // Indicateur CRE trouv�
	    		   TrouveCRE = true;
	    		   // Recherche CRE d�tail
	    		   rechercheCREDetail(String.valueOf(requetelectCRE.getBigDecimal(1)));
	    		   // Initialisation compteur lu
	    		   if (OK) {CompteurLu++;}
			   }
			   requetelectCRE.close();
			
	        } 
	        catch (SQLException e) {
		      // TODO Auto-generated catch block
		      System.out.println("Erreur SQL Exception... " + e);
		      OK = false;
		      MessageErreur = "EErreur SQL Exception... " + e;
	        }

	        // Non trouv� BCOUSFE / BCOUSFD---------------------------------------------- 	
		    if (!TrouveCRE) {
			   OK = false;
		       MessageErreur = "E" + " Aucun CRE pour les crit�res choisis... " ;
		    }

         }	// Fin test compteur CRE <= 20
         else { 
        	 OK = false;
		     MessageErreur = "A" + " Nombre CRE lus sup�rieur � la limite autoris�e, modifier les crit�res... " ;       
         } 
        
         //	---------------------------------------------------------------------------	 
		 // D�connexion Total JDBC serveur source 
		 // ---------------------------------------------------------------------------
		 try { 
			 ConnexionDriverJDBCIbm.fermer();
		     System.out.println("D�connexion totale ** OK ** du serveur " + serveur);
	     }
	     catch (Exception e) {
			 OK = false;
		     MessageErreur = "E" + " Probl�me d�connexion totale du serveur " + serveur;
	    	 System.out.println("Erreur d�connexion totale ... " + e);
	     }
		 
	} // Fin Lecture CRE a transf�rer

	// -------------------------------------------------------------------------------------
	// Rechercher CRE d�tail dans BCOUSFD
	// -------------------------------------------------------------------------------------	 
	public void rechercheCREDetail(String NumCRE) {
		
			// Initialisation requ�te
		    //String NumCREString = String.valueOf(Numcre);
	     	String querylectCRED = "SELECT * FROM " + bib + "."+ TBLBCOUSFD + " WHERE FECCOU = '" +  NumCRE + "'" 
			                      + " ORDER BY FENLIGC" ;
	     	
			// Lecture BCOUSFD et chargement tableau
			try {
				Statement stmtCRED = ConnexionDriverJDBCIbm.ouvrir(serveur,login, mdp, bib).createStatement();
				ResultSet requeteCRED = stmtCRED.executeQuery(querylectCRED);
				while (requeteCRED.next()) {
				       bcousfdFECCOU.addElement(requeteCRED.getBigDecimal(1)); 
				       bcousfdFECBCOU.addElement(requeteCRED.getString(2));
				       bcousfdFEVERS.addElement(requeteCRED.getBigDecimal(3)); 
				       bcousfdFENLIGC.addElement(requeteCRED.getBigDecimal(4));
				       bcousfdFENIBLO.addElement(requeteCRED.getString(5));
				       bcousfdFENBBLO.addElement(requeteCRED.getBigDecimal(6));
				       bcousfdFELGBLO.addElement(requeteCRED.getBigDecimal(7));
				       bcousfdFEZDETCOU.addElement(requeteCRED.getString(8));
				}
				requeteCRED.close();
				stmtCRED.close();
	   		} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Erreur SQL Exception... " + e);
				OK = false;
				MessageErreur = "EErreur SQL Exception... " + e;
			}
		
		} // Fin de recherche CRE d�tail  
		
	// -------------------------------------------------------------------------------------	 
    // Lecture CRE par num�ro de CRE
	// -------------------------------------------------------------------------------------	 
	public void lectureCRE(String NumCRE) {
 
	    // CLEAR Vector
		// BCOUSFE
        bcousfeFECCOU.removeAllElements();        
 		bcousfeFEYCOU.removeAllElements();      
 		bcousfeFELNCOU.removeAllElements(); 
		bcousfeFENADH.removeAllElements();  
 		bcousfeFENACT.removeAllElements();  
		bcousfeFELNDEST.removeAllElements();
		bcousfeFEUTILI.removeAllElements(); 
		bcousfeFEIMPR.removeAllElements();  
		bcousfeFENENT.removeAllElements();  
		bcousfeFENSEN.removeAllElements();  
		bcousfeFENCPT.removeAllElements();  
		bcousfeFEYDES.removeAllElements();  
        bcousfeFEDCRT.removeAllElements();  
        bcousfeFEDARC.removeAllElements();    
 		bcousfeFEHEUR.removeAllElements();  
		bcousfeFETRTTP.removeAllElements(); 
  	    bcousfeFETRTBT.removeAllElements(); 
		bcousfeFENTPA.removeAllElements(); 
		bcousfeFECCDI.removeAllElements();  
		bcousfeFEREFEX.removeAllElements();
		bcousfeFENAGC.removeAllElements();  
		bcousfeFETYPR.removeAllElements();  
		bcousfeFESPEC.removeAllElements();  
	    // BCOUSFD
	    bcousfdFECCOU.removeAllElements(); 
	    bcousfdFECBCOU.removeAllElements();
	    bcousfdFEVERS.removeAllElements();
	    bcousfdFENLIGC.removeAllElements();
	    bcousfdFENIBLO.removeAllElements();
	    bcousfdFENBBLO.removeAllElements();
	    bcousfdFELGBLO.removeAllElements();
	    bcousfdFEZDETCOU.removeAllElements();
	     
		OK = true; 
		 
		// V�rification n� de CRE re�u <> de 0 -------------------------------------------------
		if (!NumCRE.equals("0")) {
		
	       //Requetes BCOUSFE ------------------------------------------------------------------ 	
	       //select * from bcousfe where FECCOU = 25169000013; 
		   //String test = "25169000013";
		   querylectCRE = "SELECT * "
                 	     + "FROM " + bib + "."+ TBLBCOUSFE + " " 
				         + "WHERE FECCOU = '" + NumCRE + "'"; 
		   
		   try {
			   
			   // Ouverture connexion sur serveur source choisi
			   stmtlectCRE = ConnexionDriverJDBCIbm.ouvrir(serveur,login, mdp, bib).createStatement();
			
			   // Lecture BCOUSFE 
			   // Initialisation compteur lu
    		   if (OK) {CompteurLu++;}	
    		   
			   requetelectCRE = stmtlectCRE.executeQuery(querylectCRE);
			   while (requetelectCRE.next()) {
				   bcousfeFECCOU.addElement(requetelectCRE.getBigDecimal(1));                  
				   bcousfeFEYCOU.addElement(requetelectCRE.getString(2));  
				   bcousfeFELNCOU.addElement(requetelectCRE.getString(3)); 
				   bcousfeFENADH.addElement(requetelectCRE.getBigDecimal(4));
				   bcousfeFENACT.addElement(requetelectCRE.getBigDecimal(5));
				   bcousfeFELNDEST.addElement(requetelectCRE.getString(6));
			  	   bcousfeFEUTILI.addElement(requetelectCRE.getString(7));
			   	   bcousfeFEIMPR.addElement(requetelectCRE.getString(8));
			 	   bcousfeFENENT.addElement(requetelectCRE.getBigDecimal(9));
				   bcousfeFENSEN.addElement(requetelectCRE.getBigDecimal(10));
				   bcousfeFENCPT.addElement(requetelectCRE.getBigDecimal(11));
				   bcousfeFEYDES.addElement(requetelectCRE.getString(12));
				   bcousfeFEDCRT.addElement(requetelectCRE.getDate(13));
				   bcousfeFEDARC.addElement(requetelectCRE.getDate(14));
				   bcousfeFEHEUR.addElement(requetelectCRE.getTime(15));
				   bcousfeFETRTTP.addElement(requetelectCRE.getString(16));
				   bcousfeFETRTBT.addElement(requetelectCRE.getString(17));
				   bcousfeFENTPA.addElement(requetelectCRE.getBigDecimal(18));
				   bcousfeFECCDI.addElement(requetelectCRE.getString(19));
				   bcousfeFEREFEX.addElement(requetelectCRE.getString(20));
				   bcousfeFENAGC.addElement(requetelectCRE.getBigDecimal(21));
				   bcousfeFETYPR.addElement(requetelectCRE.getString(22));
				   bcousfeFESPEC.addElement(requetelectCRE.getString(23));
				   // Indicateur CRE trouv�
	    		   TrouveCRE = true;
	    		   // Recherche CRE d�tail
	    		   rechercheCREDetail(String.valueOf(requetelectCRE.getBigDecimal(1)));
	    		   // Initialisation compteur lu (d�plac� + haut)
	    		   //if (OK) {CompteurLu++;}	    		   
			   }
			   requetelectCRE.close();
			
	         } 
	         catch (SQLException e) {
		        // TODO Auto-generated catch block
		        System.out.println("Erreur SQL Exception... " + e);
		        OK = false;
		        MessageErreur = "EErreur SQL Exception... " + e;
	         }
		   
		     // Non trouv� BCOUSFE / BCOUSFD---------------------------------------------- 	
			 if (!TrouveCRE) {
				 OK = false;
			     MessageErreur = "E" + " Aucun CRE pour les crit�res choisis... " ;
			 }		
		   
	    }
		
		// -------------------------------------------------------------------------------------- 
		// D�connexion Total JDBC source
		// -------------------------------------------------------------------------------------- 
		try { 
			 ConnexionDriverJDBCIbm.fermer();
		     System.out.println("Déconnexion totale ** OK ** du serveur source " + serveur);
	    }
	    catch (Exception e) {
			 OK = false;
		     MessageErreur = "E" + " Problème d�connexion totale du serveur source " + serveur;
	    	 System.out.println("Erreur déconnexion totale ... " + e);
	    }		
		
	} // Fin Lecture CRE a transf�rer
	
	// -------------------------------------------------	 
	// Insert CRE dans syst�me cible choisi
	// -------------------------------------------------	 
	public void insertCRE() {
 
		// Flag �tat recherche
		OK = true;
		String NumCRE = "";
		
		for (int i = 0; i < bcousfeFECCOU.size(); i++) { 

			TrouveCREInsert = false;
			System.out.println("CRE......." + bcousfeFECCOU.elementAt(i));
			System.out.println("Courrier.." + bcousfeFEYCOU.elementAt(i));
			System.out.println("Adh�rent.." + bcousfeFENADH.elementAt(i));
			System.out.println("Date......" + bcousfeFEDCRT.elementAt(i));
			
			// CONTROLE AVANT INSERT DANS BCOUSFE --------------------------------------------
			// String test = "25169000013";
			NumCRE = String.valueOf(bcousfeFECCOU.elementAt(i));
			querylectCRE = "SELECT * " + "FROM " + bibC + "." + TBLBCOUSFE + " " + "WHERE FECCOU = '" + NumCRE + "'";
			try {
 			    // Ouverture connexion sur serveur source choisi pour Insert
				stmtinsertCRE = ConnexionDriverJDBCIbm.ouvrir(serveurC, loginC, mdpC, bibC).createStatement();
			    // Lecture BCOUSFE / Systeme cible pour controle si pr�sence CRE ==> ERREUR 
				requeteinsertCRE = stmtinsertCRE.executeQuery(querylectCRE);
				while (requeteinsertCRE.next()) {
				TrouveCREInsert = true;
			    }
				requeteinsertCRE.close();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Erreur SQL Exception... " + e);
				OK = false;
				MessageErreur = "EErreur SQL Exception... " + e;
			}
			
			// INSERT DANS BCOUSFE -------------------------------------------------------------
			try {
				
		    	 String Values_BCOUSFE = " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		    	 String Insert_BCOUSFE = "INSERT INTO  " + bibC + "." + TBLBCOUSFE;    
		    	 PreparedStatement e_BCOUSFE = 
		    	 ConnexionDriverJDBCIbm.ouvrir(serveurC, loginC, mdpC, bibC).prepareStatement(Insert_BCOUSFE + Values_BCOUSFE);
		    	 // Insert  
		    	 if (!TrouveCREInsert) { 
		    		      e_BCOUSFE.setBigDecimal(1, bcousfeFECCOU.elementAt(i));
		    		      e_BCOUSFE.setString(2, bcousfeFEYCOU.elementAt(i));
		    		      e_BCOUSFE.setString(3, bcousfeFELNCOU.elementAt(i));
		    		      e_BCOUSFE.setBigDecimal(4, bcousfeFENADH.elementAt(i));
		    		      e_BCOUSFE.setBigDecimal(5, bcousfeFENACT.elementAt(i));
		    		      e_BCOUSFE.setString(6, bcousfeFELNDEST.elementAt(i));
		    		      e_BCOUSFE.setString(7, bcousfeFEUTILI.elementAt(i));
		    		      e_BCOUSFE.setString(8, bcousfeFEIMPR.elementAt(i));
		    		      e_BCOUSFE.setBigDecimal(9, bcousfeFENENT.elementAt(i));
		    		      e_BCOUSFE.setBigDecimal(10, bcousfeFENSEN.elementAt(i));
		    		      e_BCOUSFE.setBigDecimal(11, bcousfeFENCPT.elementAt(i));
		    		      e_BCOUSFE.setString(12, bcousfeFEYDES.elementAt(i));
		    		      // Date cr�ation -------------------------
					      if (bcousfeFEDCRT.elementAt(i) == null) {e_BCOUSFE.setDate(13, Datenulle);} 
					      if (bcousfeFEDCRT.elementAt(i) != null) {e_BCOUSFE.setDate(13, bcousfeFEDCRT.elementAt(i));} 
					      // Date archivage ------------------------
					      if (bcousfeFEDARC.elementAt(i) == null) {e_BCOUSFE.setDate(14, Datenulle);} 
					      if (bcousfeFEDARC.elementAt(i) != null) {e_BCOUSFE.setDate(14, bcousfeFEDARC.elementAt(i));} 
		    		      // Heure cr�ation ------------------------ 
					      if (bcousfeFEHEUR.elementAt(i) == null) {e_BCOUSFE.setTime(15, Timenulle);}
					      if (bcousfeFEHEUR.elementAt(i) != null) {e_BCOUSFE.setTime(15, bcousfeFEHEUR.elementAt(i));} 
		    		      e_BCOUSFE.setString(16, bcousfeFETRTTP.elementAt(i));
		    		      e_BCOUSFE.setString(17, bcousfeFETRTBT.elementAt(i));
		    		      e_BCOUSFE.setBigDecimal(18, bcousfeFENTPA.elementAt(i));
		    		      e_BCOUSFE.setString(19, bcousfeFECCDI.elementAt(i));
		    		      e_BCOUSFE.setString(20, bcousfeFEREFEX.elementAt(i));
		    		      e_BCOUSFE.setBigDecimal(21, bcousfeFENAGC.elementAt(i));
		    		      e_BCOUSFE.setString(22, bcousfeFETYPR.elementAt(i));
		    		      e_BCOUSFE.setString(23, bcousfeFESPEC.elementAt(i));
		    		      e_BCOUSFE.executeUpdate();
		    		      e_BCOUSFE.close();
	      			      // Insert dans CRE DETAIL ---------------------------
	   	    		      insertCREDetail(NumCRE);
	   	    		      // Initialisation compteur ecrit
	   	    		      if (OK) {CompteurEcrit++;}
		    	}
	      			  
		    }
			catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Erreur SQL Exception... " + e);
				OK = false;
				MessageErreur = "EErreur SQL Exception... " + e;
			}
			
			 
		} // Fin Boucle For 			
			
        //if (TrouveCREInsert) {
        //  OK = false; 
        //  MessageErreur = "E" + " Le num�ro de CRE " + NumCRE + " est d�j� pr�sent en " + parametres[3] + ","   
        //                  + " Il n'y a eu aucune MAJ pour ce CRE, ni pour les suivants...";
        //}
			
   		// -------------------------------------------------------------------------
		// D�connexion Total JDBC serveur Cible
   		// ------------------------------------------------------------------------
		 try { 
			 ConnexionDriverJDBCIbm.fermer();
		     System.out.println("D�connexion totale ** OK ** du serveur cible " + serveurC);
	     }
	     catch (Exception e) {
			 OK = false;
		     MessageErreur = "E" + " Probl�me d�connexion totale du serveur cible " + serveurC;
	    	 System.out.println("Erreur d�connexion totale ... " + e);
	     }		
		
	} // Fin INSERT CRE ----------------------------------------------------------------------- 

	// -----------------------------------------------------------------------------------------	 
	// Insert CRE DETAIL dans syst�me cible choisi
	// -----------------------------------------------------------------------------------------	 
	public void insertCREDetail(String NumCRE) {
 
		// INSERT DANS BCOUSFD	---------------------------------------------------------------
		try {
		  	  String Values_BCOUSFD = " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			  String Insert_BCOUSFD = "INSERT INTO  " + bibC + "." + TBLBCOUSFD;    
			  PreparedStatement e_BCOUSFD = 
			  ConnexionDriverJDBCIbm.ouvrir(serveurC, loginC, mdpC, bibC).prepareStatement(Insert_BCOUSFD + Values_BCOUSFD);
			  for (int i = 0; i < bcousfdFECCOU.size(); i++) {                      // Boucle lecture ligne BCOUSFD   
				  if (NumCRE.equals(String.valueOf(bcousfdFECCOU.elementAt(i)))) {
			         e_BCOUSFD.setBigDecimal(1, bcousfdFECCOU.elementAt(i));	  
			 	     e_BCOUSFD.setString(2, bcousfdFECBCOU.elementAt(i));
			         e_BCOUSFD.setBigDecimal(3, bcousfdFEVERS.elementAt(i));
			         e_BCOUSFD.setBigDecimal(4, bcousfdFENLIGC.elementAt(i));
			 	     e_BCOUSFD.setString(5, bcousfdFENIBLO.elementAt(i));
			         e_BCOUSFD.setBigDecimal(6, bcousfdFENBBLO.elementAt(i));
			         e_BCOUSFD.setBigDecimal(7, bcousfdFELGBLO.elementAt(i));
			 	     e_BCOUSFD.setString(8, bcousfdFEZDETCOU.elementAt(i));
				     e_BCOUSFD.executeUpdate();
				     //e_BCOUSFD.close();
				  }
				    	 
			   }
		}
		catch (SQLException e) {
		  // TODO Auto-generated catch block
		  System.out.println("Erreur SQL Exception... " + e);
		  OK = false;
		  MessageErreur = "EErreur SQL Exception... " + e;
		}
		
	} // Fin INSERT CRE DETAIL ----------------------------------------------- 
	
	
	// Compteur CRE suivant crit�res choisis
	public Integer compteurCREavantLecture(String Requete) {

	    int compteur = 0;
		try {
			// Ouverture connexion sur serveur source & requete comptage
			stmtcompteurCRE = ConnexionDriverJDBCIbm.ouvrir(serveur, login, mdp, bib).createStatement();
			requetecompteurCRE = stmtcompteurCRE.executeQuery(Requete);
			while (requetecompteurCRE.next()) {compteur = requetecompteurCRE.getInt(1); }
			requetecompteurCRE.close();	
			//stmtcompteurCRE.close();
		}
		catch (SQLException e) {
		  System.out.println("Erreur SQL Exception... " + e);
		  OK = false;
		  MessageErreur = "EErreur SQL Exception... " + e;
		}
		
		return compteur;
		
	} // Fin Compteur CRE ---------------------------------------------------- 
	
	// getter OK/KO ----------------------------------------------------------
	public boolean getOKKO() {
		return OK;
	}

	// getter MessageErreur --------------------------------------------------
	public String getMessageErreur() {
		return MessageErreur;
	}

	// getter Compteur CRE Lu -------------------------------------------------
	public int getCompteurLu() {
		return CompteurLu;
	}
	
	// getter Compteur CRE Ecrit------------------------------------------------
	public int getCompteurEcrit() {
		return CompteurEcrit;
	}
	
}


















