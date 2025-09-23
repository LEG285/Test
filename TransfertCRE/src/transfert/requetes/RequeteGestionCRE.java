/* Projet                 : TRANSFERT - Transfert automatique des CRE    
 * Package                : transfert.requetes
 * Class                  : RequeteGestionCRE.java 
 *----------------------------------------------------------------------               
 * Objet                  : Lecture des CRE sélectionnés et transfert vers le système choisi 
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

	// Déclaration constantes
	private static final long serialVersionUID = 1L;
	
	// Tables des CRE 
    private final String TBLBCOUSFE = "BCOUSFE"; 
	private final String TBLBCOUSFD = "BCOUSFD";
	
	// Variables SQL JDBC 
 	private static ConnexionDriverJDBCIbm Connexion = null;   
	private Statement stmtlectCRE;
	private Statement stmtinsertCRE;
 	
 	// BCOUSFE
	private ResultSet requetelectCRE;
	private String querylectCRE;
	private boolean TrouveCRE; 

	// BCOUSFD
	private String querylectCRED;
	
	// Variables SQL JDBC CRE INSERT
	private ResultSet requeteinsertCRE;  
	private boolean TrouveCREInsert; 
	
	// Variables en entrée
	private String serveur;
	private String login;
    private String mdp;
    private String bib;
	private String serveurC;
	private String loginC;
    private String mdpC;
    private String bibC;
    
    private String parametres[] = new String[4];
	
	// Variables renvoyées
	private boolean OK;
	private String MessageErreur;
	
	// champs entete BCOUSFE
	private BigDecimal bcousfeFECCOU;                   
	private String bcousfeFEYCOU;   
	private String bcousfeFELNCOU; 
	private BigDecimal bcousfeFENADH ; 
	private BigDecimal bcousfeFENACT  ;
	private String bcousfeFELNDEST;
	private String bcousfeFEUTILI ;
	private String bcousfeFEIMPR  ;
	private BigDecimal bcousfeFENENT  ;
	private BigDecimal bcousfeFENSEN  ;
	private BigDecimal bcousfeFENCPT  ;
	private String bcousfeFEYDES  ;
	private java.sql.Date bcousfeFEDCRT  ;
	private java.sql.Date bcousfeFEDARC ;  
	private java.sql.Time bcousfeFEHEUR ;
	private String bcousfeFETRTTP;
	private String bcousfeFETRTBT;
	private BigDecimal bcousfeFENTPA ;
	private String bcousfeFECCDI ;
	private String bcousfeFEREFEX;
	private BigDecimal bcousfeFENAGC ;
	private String bcousfeFETYPR ;
	private String bcousfeFESPEC ;

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
		
		this.serveur = serveurLec;
		this.login = loginLec;
		this.mdp = mdpLec;
		this.bib = bibLec;
		this.serveurC = serveurInsert;
		this.loginC = loginInsert;
		this.mdpC = mdpInsert;
		this.bibC = bibInsert;
	    for (int i = 0; i < formulaireP.length; i++) { parametres[i] = formulaireP[i]; }
		
	}   // end of Ctor

	// -------------------------------------------------	 
    // Lecture lecture entete CRE
	// -------------------------------------------------	 
	public void LectureCRE(String NumCRE) {
 
	    // Clear variables
		// BCOUSFE
        bcousfeFECCOU   = BigDecimal.ZERO;     
		bcousfeFEYCOU   = "";   
		bcousfeFELNCOU  = "";
		bcousfeFENADH   = BigDecimal.ZERO;
 		bcousfeFENACT   = BigDecimal.ZERO;
		bcousfeFELNDEST = "";;
		bcousfeFEUTILI  = "";
		bcousfeFEIMPR   = "";
		bcousfeFENENT   = BigDecimal.ZERO;
		bcousfeFENSEN   = BigDecimal.ZERO;
		bcousfeFENCPT   = BigDecimal.ZERO;
		bcousfeFEYDES   = "";
        bcousfeFEDCRT   = null;
        bcousfeFEDARC   = null;  
 		bcousfeFEHEUR   = null;
		bcousfeFETRTTP  = "";
		bcousfeFETRTBT  = "";
		bcousfeFENTPA   = BigDecimal.ZERO;
		bcousfeFECCDI   = "";
		bcousfeFEREFEX  = "";
		bcousfeFENAGC   = BigDecimal.ZERO;
		bcousfeFETYPR   = "";
		bcousfeFESPEC   = "";
		// BCOUSFD
		bcousfdFECCOU.removeAllElements(); 
		bcousfdFECBCOU.removeAllElements();
		bcousfdFEVERS.removeAllElements();
		bcousfdFENLIGC.removeAllElements();
		bcousfdFENIBLO.removeAllElements();
		bcousfdFENBBLO.removeAllElements();
		bcousfdFELGBLO.removeAllElements();
		bcousfdFEZDETCOU.removeAllElements();
		OK = false;
		TrouveCRE = false;
		 
		// Vérification si n° de CRE saisi est <> de 0
		if (!NumCRE.equals("0")) {
		
	       // Requetes BCOUSFE ------------------------------------------------------------------ 	
	       //select * from bcousfe where FECCOU = 25169000013; 
		   //String test = "25169000013";
		   querylectCRE = "SELECT * "
                 	     + "FROM " + bib + "."+ TBLBCOUSFE + " " 
				         + "WHERE FECCOU = '" + NumCRE + "'";
		   try {
		
			   // Ouverture connexion sur serveur source choisi
			   stmtlectCRE = ConnexionDriverJDBCIbm.ouvrir(serveur,login, mdp, bib).createStatement();
			
			   // Lecture BCOUSFE 
			   requetelectCRE = stmtlectCRE.executeQuery(querylectCRE);
			   while (requetelectCRE.next()) {
				   bcousfeFECCOU  = requetelectCRE.getBigDecimal(1);                  
				   bcousfeFEYCOU  = requetelectCRE.getString(2);  
				   bcousfeFELNCOU = requetelectCRE.getString(3); 
				   bcousfeFENADH  = requetelectCRE.getBigDecimal(4);
				   bcousfeFENACT   = requetelectCRE.getBigDecimal(5);
				   bcousfeFELNDEST = requetelectCRE.getString(6);
			  	   bcousfeFEUTILI =  requetelectCRE.getString(7);;
			   	   bcousfeFEIMPR  =  requetelectCRE.getString(8);;
			 	   bcousfeFENENT  =  requetelectCRE.getBigDecimal(9);
				   bcousfeFENSEN  =  requetelectCRE.getBigDecimal(10);
				   bcousfeFENCPT  =  requetelectCRE.getBigDecimal(11);
				   bcousfeFEYDES  =  requetelectCRE.getString(12);
				   bcousfeFEDCRT  =  requetelectCRE.getDate(13);
				   bcousfeFEDARC  =  requetelectCRE.getDate(14);
				   bcousfeFEHEUR  =  requetelectCRE.getTime(15);;
				   bcousfeFETRTTP =  requetelectCRE.getString(16);;
				   bcousfeFETRTBT =  requetelectCRE.getString(17);; 
				   bcousfeFENTPA  =  requetelectCRE.getBigDecimal(18);
				   bcousfeFECCDI  =  requetelectCRE.getString(19);
				   bcousfeFEREFEX =  requetelectCRE.getString(20);
				   bcousfeFENAGC  =  requetelectCRE.getBigDecimal(21);
				   bcousfeFETYPR  =  requetelectCRE.getString(22);
				   bcousfeFESPEC  =  requetelectCRE.getString(23);
	    		   TrouveCRE = true;
	    		   OK = true;
			   }
			   requetelectCRE.close();
			
//			if (c11.size() != 0 ) {MessageErreur = "I" + c11.size() + " résultat(s) trouvé(s)" + recap_selection + " avec statut " + parametres[7];}
//			if (c11.size() == 0 ) {MessageErreur = "E" + c11.size() + " résultat(s) trouvé(s)" + recap_selection + " avec statut " + parametres[7];}
	         } 
	         catch (SQLException e) {
		        // TODO Auto-generated catch block
		        System.out.println("Erreur SQL Exception... " + e);
		        OK = false;
		        MessageErreur = "EErreur SQL Exception... " + e;
	         }

	        // Requetes BCOUSFD ------------------------------------------------------------------ 	
		   if (TrouveCRE) { 
	
		       querylectCRED = "SELECT * FROM " + bib + "."+ TBLBCOUSFD + " WHERE FECCOU = '" + NumCRE + "'" 
				         + " ORDER BY FENLIGC" ;
		    
		       try {
			      requetelectCRE = stmtlectCRE.executeQuery(querylectCRED);
			      while (requetelectCRE.next()) {
				        bcousfdFECCOU.addElement(requetelectCRE.getBigDecimal(1)); 
				        bcousfdFECBCOU.addElement(requetelectCRE.getString(2));
				        bcousfdFEVERS.addElement(requetelectCRE.getBigDecimal(3)); 
				        bcousfdFENLIGC.addElement(requetelectCRE.getBigDecimal(4));
				        bcousfdFENIBLO.addElement(requetelectCRE.getString(5));
				        bcousfdFENBBLO.addElement(requetelectCRE.getBigDecimal(6));
				        bcousfdFELGBLO.addElement(requetelectCRE.getBigDecimal(7));
				        bcousfdFEZDETCOU.addElement(requetelectCRE.getString(8));
				        OK = true; 
			       }
		           requetelectCRE.close();
		       }	
		       catch (SQLException e) {
			     // TODO Auto-generated catch block
			     System.out.println("Erreur SQL Exception... " + e);
			     OK = false;
			     MessageErreur = "EErreur SQL Exception... " + e;
		       }
		 
		   }
		   else {
			   OK = false;
		       MessageErreur = "E" + " Le numéro de CRE " + NumCRE + " est inconnu en " + parametres[2] + "..." ;
		   }
		
		   // Déconnexion Total JDBC -----------------------------------------------
		   try {ConnexionDriverJDBCIbm.fermer();
		        System.out.println("Déconnexion totale ** OK ** du serveur " + serveur);
	       }
	       catch (Exception e) {System.out.println("Erreur déconnexion totale ... " + e);}

		}
		else {
			OK = false;
		    MessageErreur = "E" + " Format numéro de CRE incorrect, il doit être sur 11 caractères ..." ;
		}
		
		
	} // Fin Lecture CRE a transférer

	// -------------------------------------------------	 
	// Inser CRE dans système cible choisi
	// -------------------------------------------------	 
	public void InsertCRE(String NumCRE) {
 
		// Flag état recherche
		OK = false;
		TrouveCREInsert = false;
		
		System.out.println("Infos CRE....." + bcousfeFECCOU);
		System.out.println("Infos CRE....." + bcousfeFEYCOU);
		System.out.println("Infos CRE....." + bcousfeFENADH);
		
		if (TrouveCRE) {
 	        // select * from bcousfe where FECCOU = 25169000013;
			// Controle avant Insert into bcousfe -------------------------------------
			// String test = "25169000013";
			querylectCRE = "SELECT * " + "FROM " + bibC + "." + TBLBCOUSFE + " " + "WHERE FECCOU = '" + NumCRE + "'";
			try {
 			    // Ouverture connexion sur serveur source choisi pour Insert
				stmtinsertCRE = ConnexionDriverJDBCIbm.ouvrir(serveurC, loginC, mdpC, bibC).createStatement();
			    // Lecture BCOUSFE / Systeme cible pour controle si présence CRE ==> ERREUR 
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
			
			// INSERT DANS BCOUSFE	
			try {
		    	  String Values_BCOUSFE = " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		    	  String Insert_BCOUSFE = "INSERT INTO  " + bibC + "." + TBLBCOUSFE;    
		    	  PreparedStatement e_BCOUSFE = 
		    	  ConnexionDriverJDBCIbm.ouvrir(serveurC, loginC, mdpC, bibC).prepareStatement(Insert_BCOUSFE + Values_BCOUSFE);
		    	  // Insert  
		    	  if (!TrouveCREInsert) {                                                      
		    		  e_BCOUSFE.setBigDecimal(1, bcousfeFECCOU);
		    		  e_BCOUSFE.setString(2, bcousfeFEYCOU);
		    		  e_BCOUSFE.setString(3, bcousfeFELNCOU);
		    		  e_BCOUSFE.setBigDecimal(4, bcousfeFENADH);
		    		  e_BCOUSFE.setBigDecimal(5, bcousfeFENACT);
		    		  e_BCOUSFE.setString(6, bcousfeFELNDEST);
		    		  e_BCOUSFE.setString(7, bcousfeFEUTILI);
		    		  e_BCOUSFE.setString(8, bcousfeFEIMPR);
		    		  e_BCOUSFE.setBigDecimal(9, bcousfeFENENT);
		    		  e_BCOUSFE.setBigDecimal(10, bcousfeFENSEN);
		    		  e_BCOUSFE.setBigDecimal(11, bcousfeFENCPT);
		    		  e_BCOUSFE.setString(12, bcousfeFEYDES);
		    		  // Date création -------------------------
					  if (bcousfeFEDCRT == null) {e_BCOUSFE.setDate(13, Datenulle);} 
					  if (bcousfeFEDCRT != null) {e_BCOUSFE.setDate(13, bcousfeFEDCRT);} 
					  // Date archivage ------------------------
					  if (bcousfeFEDARC == null) {e_BCOUSFE.setDate(14, Datenulle);} 
					  if (bcousfeFEDARC != null) {e_BCOUSFE.setDate(14, bcousfeFEDARC);} 
		    		  // Heure création ------------------------ 
					  if (bcousfeFEHEUR == null) {e_BCOUSFE.setTime(15, Timenulle);}
					  if (bcousfeFEHEUR != null) {e_BCOUSFE.setTime(15, bcousfeFEHEUR);} 
		    		  e_BCOUSFE.setString(16, bcousfeFETRTTP);
		    		  e_BCOUSFE.setString(17, bcousfeFETRTBT);
		    		  e_BCOUSFE.setBigDecimal(18, bcousfeFENTPA);
		    		  e_BCOUSFE.setString(19, bcousfeFECCDI);
		    		  e_BCOUSFE.setString(20, bcousfeFEREFEX);
		    		  e_BCOUSFE.setBigDecimal(21, bcousfeFENAGC);
		    		  e_BCOUSFE.setString(22, bcousfeFETYPR);
		    		  e_BCOUSFE.setString(23, bcousfeFESPEC);
		    		  e_BCOUSFE.executeUpdate();
		    		  e_BCOUSFE.close();
	      			  OK = true;
		    	  }
			
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Erreur SQL Exception... " + e);
				OK = false;
				MessageErreur = "EErreur SQL Exception... " + e;
			}
		    	  
////		if (c11.size() != 0 ) {MessageErreur = "I" + c11.size() + " résultat(s) trouvé(s)" + recap_selection + " avec statut " + parametres[7];}
////		if (c11.size() == 0 ) {MessageErreur = "E" + c11.size() + " résultat(s) trouvé(s)" + recap_selection + " avec statut " + parametres[7];}
				
			// INSERT DANS BCOUSFD	
			try {
			  	  String Values_BCOUSFD = " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				  String Insert_BCOUSFD = "INSERT INTO  " + bibC + "." + TBLBCOUSFD;    
				  PreparedStatement e_BCOUSFD = 
				  ConnexionDriverJDBCIbm.ouvrir(serveurC, loginC, mdpC, bibC).prepareStatement(Insert_BCOUSFD + Values_BCOUSFD);
				  // Insert  
				  if (!TrouveCREInsert) {    
			    	 for (int i = 0; i < bcousfdFECCOU.size(); i++) {              // OK --> Boucle Maj BCOUSFD
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
				    	 OK = true;
				     }
				  }	 
			}
			catch (SQLException e) {
			   // TODO Auto-generated catch block
			   System.out.println("Erreur SQL Exception... " + e);
			   OK = false;
			   MessageErreur = "EErreur SQL Exception... " + e;
			}
			 
   		    if (TrouveCREInsert) {
   		    	OK = false; 
   		    	MessageErreur = "E" + " Le numéro de CRE " + NumCRE + " est déjà présent en " + parametres[3] + ","   
   		    			            + " Il n'y a eu aucune MAJ pour ce CRE, ni pour les suivants...";
   		    	
   		    }
			
			// Déconnexion Total JDBC -------------------------------------
			try {
				ConnexionDriverJDBCIbm.fermer();
				System.out.println("Déconnexion totale ** OK ** du serveur " + serveurC);
			}
			catch (Exception e) {System.out.println("Erreur déconnexion totale ... " + e);}

		}
		
		
	} // Fin INSERT CRE ----------------------------------------------- 
	
	// getter OK/KO --------------------------------------------------
	public boolean getOKKO() {
		return OK;
	}
	
	// getter MessageErreur -----------------------------------------
	public String getMessageErreur() {
		return MessageErreur;
	}
		
}

