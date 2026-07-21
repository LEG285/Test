/* **********************************************************************
 * Projet                 : TRANSFERT - Transfert des CRE entre syst�me  
 * Package                : transfert.servlet
 * Class                  : PageSelection2.java 
 *----------------------------------------------------------------------               
 * Objet                  : Sélection CRE nouvelle version avec nouveaux critères  
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 03/12/2025
 *----------------------------------------------------------------------
 * Chemin class jsp       : \apache-tomcat-7.0.69\work\Catalina\localhost\transfert\...
 ********************************************************************* */
package transfert.servlet; 

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import transfert.pages.HTMLheader;
import transfert.pages.PageFormulaire;
import transfert.requetes.RequeteGestionCRE;
 
/**
 * Servlet implementation class PageSelection2
 */
@WebServlet(description = "Selection CRE nouvelle version", urlPatterns = { "/PageSelection2" })
public class PageSelection3 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
        
	// Variables paramêtres servlet serveurs/version CSS/version JS 
    static String serveurLOC, serveurDEV, serveurREC, serveurPROD; 
    static String loginREC, loginPROD;
    static String mdpREC, mdpPROD;
    static String bibDEV, bibREC, bibPROD;
    
	static String Serveur, Login, Mdp, Bib;
	static String ServeurC, LoginC, MdpC, BibC;
	
	static String servlet, versionCSS, versionJS;
	static String lienQuitter, lienErreur, retourMenu;

	// Initialisation variables HTTP
	static String Servlet, Programme, Adresse, Context, Scheme;
	static String loginSession, mdpSession;
	 
	// Variables gestion formulaire
	static String p_formulaire[] = new String[7]; 
	//static String TabCRE[] = {"","","","","","","","","","","","","","","","","","","",""}; 
	
	// Résultat requête
	static String Message;
	static boolean OKKO; 
	static String chaine; 
	
	// Préparation html header 
	HTMLheader header = new HTMLheader("CRE - Transfert CRE inter-syst&egrave;me",
			                           "&copy; Groupe Pasteur Mutualit&eacute; - Version 1.1");	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PageSelection3() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);

		// Nom servlet
		Servlet = config.getServletName();
		// Récupération paramètres associés au servlet
	 	// serveurLOC  = getInitParameter("serveurLOC");
	 	// bibFic      = getInitParameter("bibFichier");
        // DEV
	 	serveurDEV  = getInitParameter("serveurDEV");
        bibDEV      = getInitParameter("bibDEV");
        // REC
	 	serveurREC  = getInitParameter("serveurREC");
        loginREC    = getInitParameter("loginREC");
        mdpREC      = getInitParameter("mdpREC");
        bibREC      = getInitParameter("bibREC");
        // PROD
	 	serveurPROD = getInitParameter("serveurPROD");
        loginPROD   = getInitParameter("loginPROD");
        mdpPROD     = getInitParameter("mdpPROD"); 
        bibPROD     = getInitParameter("bibPROD");
        // Liens navigation
		retourMenu = getInitParameter("retourMenu");
 		lienQuitter = getInitParameter("lienQuitter"); 		
		lienErreur  = getInitParameter("lienErreur");
		
		// Initialisation variable(s) pour AutoCompletion ------------------------------------------------
		RequeteGestionCRE listAutoCompletion = new RequeteGestionCRE(serveurDEV, "AVTTST06", "AVTTST06");
        chaine = "var liste = " + listAutoCompletion.chargementlistAutoCompletion();
    	if (!listAutoCompletion.getOKKO()) {chaine = "var liste = [" + "\"" + "!ERREUR!" + "\"" + "]";}
    	//System.out.println(chaine);
		// ----------------------------------------------------------------------------------------------

		// Fin init Servlet 
    	System.out.println("Initialisation servlet :" + Servlet);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		// Appel direct doPost
		doPost(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Envoi flux HTML
		int PortN = request.getServerPort();
		String Port = new String();
		Port = String.valueOf(PortN);
		Serveur = request.getServerName();
		Adresse = request.getServerName() + ":" + Port;
		Context = request.getContextPath() + "/";
		Scheme  = request.getScheme() + "://";
	
		// R�cup informations session utilisateur
		HttpSession sessionHTTP = request.getSession(false);
		Boolean parametres = false;
		Boolean ParNumeroCRE = false; 
		
		// Variables de travail
		//                  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20   
		String TabCRE[] = {"","","","","","","","","","","","","","","","","","","",""};		
		
		// Session expirée --> Page Erreur
		if (sessionHTTP == null) {

			// ------------ A remettre en place après DEV ---------------------------------------------
			//request.getRequestDispatcher(lienErreur).forward(request, response);  // a mettre en commentaire si dev
			
			// ------------ Creation d'une session http temporaire pour dév. -----> A retirer après DEV
			HttpSession SessionLogin = request.getSession(true);
			//if session is not new then we are switching the session and start a new one
			//(Si session n'est pas nouvelle alors nous switchons la session et démarrons une nouvelle) 
			//if (!(SessionLogin.isNew())) {
			//   SessionLogin.invalidate();
			//   SessionLogin = request.getSession(true);
			//}
  
			// Juste le profil 
			SessionLogin.setAttribute("Utilisateur", "AVTTST06");
			SessionLogin.setAttribute("Serveur", "localhost");
			SessionLogin.setAttribute("MotdePasse", "AVTTST06");
			SessionLogin.setAttribute("BibFichier", "AVTGPMDTA");

			// Récup des informations session utilisateur
			sessionHTTP = request.getSession(false);
			
		}
			
		// Session OK --> Page HTML
		if (sessionHTTP != null) {

			// Initialisation informations generales 
			//Serveur = (String) sessionHTTP.getAttribute("Serveur");
			//Bib = (String) sessionHTTP.getAttribute("BibFichier");
			loginSession = (String) sessionHTTP.getAttribute("Utilisateur");
			mdpSession = (String) sessionHTTP.getAttribute("MotdePasse");
			
			// Initialisation tableaux avec les parametres du formulaire
			// Code CRE col 1  
			if (request.getParameter("cass") != null) {p_formulaire[0] = request.getParameter("cass");}
			else {p_formulaire[0] = "";}

			// Code CRE col 2 
			if (request.getParameter("cass2") != null) {p_formulaire[1] = request.getParameter("cass2");}
			else {p_formulaire[1] = "";}
			
			// Type courrier   
			if (request.getParameter("typeCou") != null) {p_formulaire[2] = request.getParameter("typeCou");}
			else {p_formulaire[2] = "";}			
			
			// Code adhérent    
			if (request.getParameter("codeAdh") != null) {p_formulaire[3] = request.getParameter("codeAdh");}
			else {p_formulaire[3] = "";}			
			
			// Date écran     
			if (request.getParameter("dateCr") != null) {p_formulaire[4] = request.getParameter("dateCr");}
			else {p_formulaire[4] = "";}			
			
			// Serveur source
			if (request.getParameter("stat1") != null) {p_formulaire[5] = request.getParameter("stat1");}
   		    else {p_formulaire[5] = "";}

			// Serveur cible 
			if (request.getParameter("stat2") != null) {p_formulaire[6] = request.getParameter("stat2");}
   		    else {p_formulaire[6] = "";}
				
			// Analyse param�tres du formulaire 
			if (!p_formulaire[0].equals("") || !p_formulaire[1].equals("") ||
				!p_formulaire[2].equals("") || !p_formulaire[3].equals("") || 
				!p_formulaire[4].equals("")) {
					
					parametres = true;  // Paramètres saisis  
					
					p_formulaire[0] = p_formulaire[0].replace("\r\n", "");
					//System.out.println("cass...  " + p_formulaire[0]);
					
					p_formulaire[1] = p_formulaire[1].replace("\r\n", "");
					//System.out.println("cass...  " + p_formulaire[1]);
					
		 			// Chargement des n° de CRE saisis 
					int  j = 0;
					// Formulaire n� CRE 1
					for (int i = 0; i < p_formulaire[0].trim().length(); i = i + 11) {
						 
						 try {TabCRE[j] = p_formulaire[0].substring(i, i+11);}
						 catch (Exception e) {TabCRE[j] = "0";}
						 //System.out.println("NumCRE..." + TabCRE[j]);
						 j++;
							  
					}
					// Formulaire n° CRE 2 
					for (int i = 0; i < p_formulaire[1].trim().length(); i = i + 11) {
						 
						 try {TabCRE[j] = p_formulaire[1].substring(i, i + 11);}
						 catch (Exception e) {TabCRE[j] = "0";}
						 //System.out.println("NumCRE..." + TabCRE[j]);
						 j++;
							  
					}
					
					// PROD source 
					if (p_formulaire[5].equals("PRODUCTION")) {
					   Serveur = serveurPROD;
					   Login  = loginPROD;
					   Mdp = mdpPROD;
					   Bib = bibPROD;
					}
					// REC source
					if (p_formulaire[5].equals("RECETTE")) {
					   Serveur = serveurREC;
					   Login  = loginREC;
					   Mdp = mdpREC;
					   Bib = bibREC;
					}
					// DEV source
					if (p_formulaire[5].equals("DEV")) {
					   Serveur = serveurDEV;
					   Login = loginSession;
					   Mdp = mdpSession;
					   Bib = bibDEV;
					}
 
					// RECETTE cible 
					if (p_formulaire[6].equals("RECETTE")) {
					   ServeurC = serveurREC;
					   LoginC  = loginREC;
					   MdpC = mdpREC;
					   BibC = bibREC;
					}
					// RECETTE + DEV cible (A developer)
					if (p_formulaire[6].equals("RECETTE et DEV")) {
					   ServeurC = serveurREC;
					   LoginC  = loginREC;
					   MdpC = mdpREC;
					   BibC = bibREC;
					}
					// DEV cible
					if (p_formulaire[6].equals("DEV")) {
					   ServeurC = serveurDEV;
					   BibC = bibDEV;
					   LoginC = loginSession;
					   MdpC= mdpSession;
					}
					
					// Initialisation requete CRE ----------------------------------------------
					RequeteGestionCRE requeteCRE = new RequeteGestionCRE(Serveur, Login, Mdp, Bib,
							                                             ServeurC, LoginC, MdpC, BibC, 
							                                             p_formulaire);
					 
					// Process lecture & Insertion CRE par n° CRE --------------------------------
					for (int i = 0; i < TabCRE.length; i++) {  
						if  (!TabCRE[i].equals("")) { 
							ParNumeroCRE = true;
					        requeteCRE.lectureCRE(TabCRE[i]);
		  			        // Insert CRE si OK -----------------------------------------------------
					        if (requeteCRE.getOKKO())  {requeteCRE.insertCRE();}
					        //if (!requeteCRE.getOKKO()) {break;}
						}
						 
					}  
 
					// Process de lecture &  Insertion CRE par autres critères ----------------------
					if (!ParNumeroCRE) {
						//requeteCRE.LectureCREAutreCriteres(p_formulaire[2], p_formulaire[3], p_formulaire[4] );
						requeteCRE.lectureCREAutreCriteres(); 
	  			        // Insert CRE si OK ---------------------------------------------------------
				        if (requeteCRE.getOKKO())  {requeteCRE.insertCRE();}
				        //if (!requeteCRE.getOKKO()) {break;}
					}
					 
					// Message type "E" = Erreur
					// Message type "I" = Information
					if (requeteCRE.getOKKO()) {          // Si retour OK afficher message OK
						Message = "I" + " Terminé ! ----> CRE lu(s) en " + p_formulaire[5] + " : " 
					              + String.valueOf(requeteCRE.getCompteurLu()) + 
								  " ----> CRE écrit(s) en " + p_formulaire[6] + " : " 
					              + String.valueOf(requeteCRE.getCompteurEcrit());
					}                 
					else {                               // Si retour KO afficher message Erreur
						Message = requeteCRE.getMessageErreur();
			        }
					
			}  // Fin test 		 
			
			// Si paramétres --> affichage page HTML résultat 
			if (parametres) {
			 	
				if (Message.substring(0, 1).equals("I") || Message.substring(0, 1).equals("A")) { // Info/Alert sur page résultat 
					// Affichage page résultat si OK (type "I" pour info, "A" pour Alert)
					//CREHtmlPageResultat(response, Message.substring(1, Message.length()), Message.substring(0, 1));
					
					CREHtmlPageResultat(response, Message.substring(1, Message.length()), Message.substring(0, 1));
			        					
	                // Voir possibilité d'une progression....				
				} 
				else {                                           // Erreur sur page résultat / Suite requ�te CRE
					// Affichage formulaire et message d'erreur
					CREHtmlPageResultat(response, Message.substring(1, Message.length()), Message.substring(0, 1));
				}
 
                // Pour présentation projet / page de maintenance 				
				//response.sendRedirect(request.getContextPath() + "/" + "PageMaintenance.html");

			} 
			// Si pas de paramètres --> Affichage formulaire vide à remplir
			else {
				CREHtmlPageResultat(response, "", "");
			}
			
		}   // Fin de test session diff. de null
	
	} 
	
	/**
	 * @see HcrLoginHtmlPage(HttpServletRequest res, String Erreur)
	 */
	protected boolean CREHtmlPageResultat(HttpServletResponse res, String Erreur, String TypeErreur) {

		 // Chargement arguments pages HTML
		 String [] argument = new String[] {Scheme, Adresse, Context, Serveur, lienQuitter};
		 PageFormulaire HtmlResult = new PageFormulaire(argument);
		 
		 // Initialisation Cache   
		 res.setContentType("text/html");
		 res.setHeader("Pragma","No-Cache");
		 res.setDateHeader("Expires", 0);
		 res.setHeader("Cache-Control","no-Cache");			
		
		// Initialisation etat de la page HTML
		boolean EtatPageHTML = Erreur.isEmpty();
		
	     // Envoi flux html
		 try {res.getOutputStream().println(HtmlResult.GetPageFormulaire(Erreur,  TypeErreur).toString());}
		 catch (Exception e) {System.out.println("HTML page error : " + e.getMessage());}

		return EtatPageHTML;

	} // end of CREHtmlPageResultat

}
