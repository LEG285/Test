/**
 * *********************************************************************
 * Projet                 : PRESTIJ - AUTOMATISATION IJ   
 * Package                : transfert.requetes
 * Class                  : RequeteFluxBpij.java 
 *----------------------------------------------------------------------               
 * Objet                  : Recherche des flux BPIJ suivant les critères saisis 
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 23 05 2018 
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
public class RequeteFluxBpij {

	// Déclaration constantes
	private static final long serialVersionUID = 1L;
	
	// Tables BPIJ  
    private final String BPIJDOCUMENT   = "BPIJA_EBDO"; 
	private final String BPIJASSURE     = "BPIJA_ASSU";
	private final String BPIJASSURANCE  = "BPIJA_ASRC";
	private final String BPIJPRESTATION = "BPIJA_PRES";
	private final String BPIJENTREPRISE = "BPIJA_ENT";
	private final String ASSURE         = "ASSURE";
    
	// Variables SQL JDBC BPIJ
	private Statement stmtBPIJ;
	private ResultSet requeteflux;
	private String queryBPIJ;

	// Variables en entrée
	private String serveur, login, mdp, bib;
    private String parametres[] = new String[9];
    private String recap_selection = ""; 
	
	// Variables renvoyées
	private boolean OK;
	private String MessageErreur;
	
	// Tableau 1 - IDFichier... 
	private Vector<String> c11 = new Vector<String>();
	private Vector<String> c12 = new Vector<String>();
	private Vector<String> c13 = new Vector<String>();
	private Vector<String> c14 = new Vector<String>();
	private Vector<String> c15 = new Vector<String>();
	private Vector<String> c16 = new Vector<String>();
	private Vector<String> c17 = new Vector<String>();
	private Vector<String> c18 = new Vector<String>();
	
	// Tableau 2 - Numero SS/NOM...
	private Vector<String> c21 = new Vector<String>();
	private Vector<String> c22 = new Vector<String>();
	private Vector<String> c23 = new Vector<String>();
	private Vector<String> c24 = new Vector<String>();
	private Vector<String> c25 = new Vector<String>();
	private Vector<String> c26 = new Vector<String>();
	private Vector<String> c27  = new Vector<String>();
	private Vector<String> c28  = new Vector<String>();
	private Vector<String> c29  = new Vector<String>();
	
	private Vector<String> c30  = new Vector<String>();
	private Vector<String> c31  = new Vector<String>();
	
	// Variables travail 
	private String madatedebut, madatefin;
	private String DateMinPres, DateMaxPres, StatutGED;
	private int compteur = 0, compteurassure = 0; 
	
	// Utilitaires
	BoiteOutils FormatDate = new BoiteOutils();
	
	// Ctor droit d'import
	public RequeteFluxBpij (String serveurP, String loginP,  String mdpP, String bibP, String formulaireP[]) {
		
		this.serveur = serveurP;
		this.login = loginP;
		this.mdp = mdpP;
		this.bib = bibP;
	    for (int i = 0; i < formulaireP.length; i++) { parametres[i] = formulaireP[i]; }
		
	} // end of Ctor

	// Lecture BPIJ
	public void LectureBPIJ() {

	    // Clear vectors (*all)
		//   1) Tableau 1 - IDFichier/...
    	c11.removeAllElements();
    	c12.removeAllElements();
    	c13.removeAllElements();
    	c14.removeAllElements();
    	c15.removeAllElements();
    	c16.removeAllElements();
    	c17.removeAllElements();
    	c18.removeAllElements();
    	
    	//   2) Tableau 2 - Numero SS/NOM...
    	c21.removeAllElements();
    	c22.removeAllElements();
    	c23.removeAllElements();
    	c24.removeAllElements();
    	c25.removeAllElements();
    	c26.removeAllElements();
    	c27.removeAllElements();
       	c28.removeAllElements();
       	
		// Flag état recherche
		OK = true;

		// Initialisation Requête QRY
		madatedebut = FormatDate.FormatPeriodeAAMMJJ(parametres[7]) ;
		madatefin   = FormatDate.FormatPeriodeAAMMJJ(parametres[8]);

		// Statut, date min & max prestation
		DateMinPres = "(SELECT MIN(h.dtdebpr) FROM " + BPIJPRESTATION + " h " 
				      + "WHERE a.organisme = h.organisme AND "                  
		              + "a.id_doc = h.id_ent AND "                        
		              + "b.sir_ident = h.sir_ident AND "                  
		              + "b.nir = h.nir AND " 
		              + "c.cna_asrce = h.cna_asrce)";
	
        DateMaxPres = "(SELECT MAX(h.dtfinpr) FROM " + BPIJPRESTATION + " h " 
                      + "WHERE a.organisme = h.organisme AND " 
                      + "a.id_doc = h.id_ent AND "      
                      + "b.sir_ident = h.sir_ident AND "
                      + "b.nir = h.nir AND "
                      + "c.cna_asrce = h.cna_asrce)";
        
        StatutGED = "(SELECT i.statut FROM " + BPIJPRESTATION + " i " 
                    + "WHERE a.organisme = i.organisme AND " 
                    + "a.id_doc = i.id_ent AND "      
                    + "b.sir_ident = i.sir_ident AND "
                    + "b.nir = i.nir AND "
                    + "c.cna_asrce = i.cna_asrce "
                    + "FETCH FIRST 1 ROW ONLY)"; 
        
		queryBPIJ =	"SELECT "
				    + "a.organisme as ORG_Doc, a.id_ent as IDFIC_E, "
				    + "a.adresse as IDFIC_D, a.tps as DAT_Doc, "                           
				    + "a.id_trans as IP, a.id_doc as ID_Doc, b.organisme as ORG_Assu, "
				    + "b.sir_ident as SIR, b.nir as SS, b.nom as NOM, b.prenom as PRENOM, "
				    + StatutGED + " AS GED, " + "b.ID_ENT as ID_Assu , c.organisme as ORG_Ascr, "      
				    + "c.cna_asrce as NAT_Ass, c.cum_asr as CUM_Ass, c.ID_ENT as ID_Ascr, "
				    + "e.raisoc as RAISOC, f.abencod as CASSURE, "
				    + DateMinPres + " AS DATEMIN, " + DateMaxPres + " AS DATEMAX "
				    + "FROM " + BPIJDOCUMENT + " a "                                           
				    + "LEFT OUTER JOIN " + BPIJASSURE + " b "                                
				    + "ON  "
				    + "a.id_doc = b.id_ent AND " 
				    + "a.organisme = b.organisme "                
				    + "LEFT OUTER JOIN " + BPIJASSURANCE + " c "                                
				    + "ON  "
				    + "a.id_doc = c.id_ent AND "
				    + "a.organisme = c.organisme AND "
				    + "b.nir = c.nir "
				    + "LEFT OUTER JOIN " + BPIJENTREPRISE + " e "                                
				    + "ON  "
				    + "a.organisme = e.organisme AND "
				    + "a.id_doc = e.id_ent AND "
				    + "b.sir_ident = e.sir_ident "
				    + "LEFT OUTER JOIN " + ASSURE + " f "                                
				    + "ON  "
				    + "b.nir = f.assnum "
				    + "WHERE "; // (La période est obligatoire donc il y a toujours une clause where...)     
		
		// Sélection sur IDFichier En-tête  
		if (!parametres[0].equals("")) {

			queryBPIJ = queryBPIJ   
			            + "a.id_ent = '" + parametres[0] + "' "      
			            + "AND "; 

			// Maj récapitulatif sélection IDfichier En-tête
		    recap_selection = " IDflux " + parametres[0]; 
		
		}

		// Sélection sur IDFichier Données  
		else if (!parametres[1].equals("")) {

			queryBPIJ = queryBPIJ   
			            + "a.adresse = '" + parametres[1] + "' "     
			            + "AND "; 

			// Maj récapitulatif sélection IDfichier Données 
		    recap_selection = " IDfichier " + parametres[1]; 
		
		}

		// Sélection sur code assuré SIRIUS 
		else if (!parametres[2].equals("")) {

			queryBPIJ = queryBPIJ
			       	+ "f.abencod = " + parametres[2] + " AND ";
			
			// Récap sélection Code Assuré 
		    recap_selection = " pour N° assur\351 " + parametres[2]; 
			
		}
		
		// Sélection sur n° SS (NIR)
		else if (!parametres[3].equals("")) {

			queryBPIJ = queryBPIJ
				       	+ "b.nir = '" + parametres[3] + "'"
			            + " AND "; 
			
			// Récap sélection N° SS
		    recap_selection = " Num\351ro SS " + parametres[3]; 
			
		}
		
		// Sélection Nom -/et Prenom 
		else if (!parametres[4].equals("")) {
			
			// Requete par Nom uniquement 
			if (parametres[5].equals("")) {  // Requete par Nom uniquement
				
			   queryBPIJ = queryBPIJ 
					       + "b.nom like '" + parametres[4] + "%' "
      		               + "AND "; 
			   
				// Récap sélection nom
			    recap_selection = " " + parametres[4]; 
			   
 			}
			  
			// Requête par Nom et Prenom 
			else {                         
				
			   queryBPIJ = queryBPIJ
				           + "b.nom like '" + parametres[4] + "%' "
					       + "AND "  
				           + "b.prenom = '" + parametres[5] + "' " 
				           + "AND "; 

				// Récap sélection nom-prénom
			    recap_selection = " " + parametres[4] + " " + parametres[5]; 
			   
 			}
			 
		}
		
		// Sélection Nature de l'assurance + Maj récapitulatif    
		if (!parametres[6].equals("TOUS")) {
			
			queryBPIJ = queryBPIJ 
					    + "c.cna_asrce = '" + parametres[6].substring(0, 2) + "' "
					    + "AND ";
			
		    recap_selection = recap_selection 
		    		          + " Nature Assurance " + parametres[6];
		    
		}
		
		// Sélection période Début - Fin
		if (!parametres[7].equals("") && !parametres[8].equals("")) {
			
		   queryBPIJ = queryBPIJ
                       + DateMinPres + " >= '" + madatedebut + "' "
                       + "AND "
                       + DateMaxPres + " <= '" + madatefin + "' ";
		   
	       recap_selection = recap_selection
	    		             + " p\351riode du " + parametres[7] 
	    		             + " au " + parametres[8];
	       
		}
	    
		// Group by ....  
		queryBPIJ = queryBPIJ +
				    " GROUP BY a.organisme, a.id_ent, a.adresse,"
				  + " a.tps, a.id_trans, a.id_doc, b.organisme,"             
				  + " b.sir_ident, b.nir, b.nom, b.prenom,"   
				  + " b.statut, b.ID_ENT, c.organisme, c.cna_asrce,"           
				  + " c.cum_asr, c.ID_ENT , e.raisoc , f.abencod, "      
				  +   DateMinPres + ", " + DateMaxPres;          
		
		// Order by chrono descendant + N° id Fichier + NIR + Nature assurance  
		queryBPIJ = queryBPIJ + 
		            " ORDER BY a.tps DESC, a.id_ent,"
		          + " a.adresse, b.sir_ident, b.nir, c.cna_asrce";                                                 
		
		// Lecture tables SIRIUS
		try {
		
			// Lecture
			stmtBPIJ = ConnexionDriverJDBCIbm.ouvrir(serveur,login, mdp, bib).createStatement();
			requeteflux = stmtBPIJ.executeQuery(queryBPIJ);
			while (requeteflux.next()) {

                // Maj compteur IDFlux/Fichier/Siren 
			    String fic_flux_sir = requeteflux.getString("IDFIC_E") + 
			    		              requeteflux.getString("IDFIC_D") + 
			    		              requeteflux.getString("SIR");
			    if (!c31.contains(fic_flux_sir)) {  
					compteur++;
					compteurassure = 1;
					c18.addElement(String.valueOf(compteurassure));
					c31.addElement(fic_flux_sir);
				}

				// Chargement tableau 1 - IDFichier  
				c11.addElement(requeteflux.getString("IDFIC_E"));
				c12.addElement(requeteflux.getString("IDFIC_D"));
				c13.addElement(FormatDate.FormatPeriodeJJMMAA(requeteflux.getTimestamp("DAT_DOC").toString()));
				c14.addElement(requeteflux.getString("IP"));
				
				c15.addElement(requeteflux.getString("SIR"));
				c16.addElement(requeteflux.getString("ID_Doc"));
			    c17.addElement(requeteflux.getString("RAISOC"));
				
				// Maj compteur Assure + nature par tableau IDflux/IDfichier.....
			    String rupture_assure = fic_flux_sir + requeteflux.getString("SS") + requeteflux.getString("NAT_Ass"); 
			    if (!c30.contains(rupture_assure)) {  
				    c18.setElementAt(String.valueOf(compteurassure++), (compteur-1));
					c30.addElement(rupture_assure);
				}
				
				// Chargement tableau 2 - Détail Assuré
				c21.addElement(requeteflux.getString("SS"));
				c22.addElement(requeteflux.getString("NOM"));
				c23.addElement(requeteflux.getString("NAT_Ass"));
				c24.addElement(FormatDate.FormatPeriodeJJMMAA(requeteflux.getTimestamp("DATEMIN").toString()));
				c25.addElement(requeteflux.getString("GED"));
				c26.addElement(requeteflux.getString("PRENOM"));
				c27.addElement(FormatDate.FormatPeriodeJJMMAA(requeteflux.getTimestamp("DATEMAX").toString()));
				c28.addElement(requeteflux.getString("CUM_Ass"));   // Cumul nature assurance
				c29.addElement(requeteflux.getString("CASSURE"));   // Code assuré SIRIUS 

			}
			
			if (compteur != 0 ) {MessageErreur = "I" + compteur + " r\351sultat(s) trouv\351(s) pour " + recap_selection;}
			if (compteur == 0 ) {MessageErreur = "E" + compteur + " r\351sultat(s) trouv\351(s) pour " + recap_selection;}
			
			requeteflux.close();
			stmtBPIJ.close();
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur SQL Exception... " + e);
			OK = false;
			MessageErreur = "EErreur SQL Exception... " + e;
		}
	
	} // Fin de LectureBPIJ

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
	// getter c12
	public Vector<String> getc12() {
		return c12;
	}
	// getter c13
	public Vector<String> getc13() {
		return c13;
	}
	// getter c14
	public Vector<String> getc14() {
		return c14;
	}
	// getter c15
	public Vector<String> getc15() {
		return c15;
	}
	// getter c16
	public Vector<String> getc16() {
		return c16;
	}	
	// getter c17
	public Vector<String> getc17() {
		return c17;
	}	
	// getter c18
	public Vector<String> getc18() {
		return c18;
	}	
	
	// getter c21
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

