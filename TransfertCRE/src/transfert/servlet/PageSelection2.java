/* **********************************************************************
 * Projet                 : TRANSFERT - Transfert des CRE entre syst�me  
 * Package                : transfert.servlet
 * Class                  : PageSelection2.java 
 *----------------------------------------------------------------------               
 * Objet                  : S�lection CRE nouvelle version avec nouveaux crit�res  
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 03/12/2025
 *----------------------------------------------------------------------
 * Chemin class jsp       : \apache-tomcat-7.0.69\work\Catalina\localhost\transfert\...
 ********************************************************************* */
package transfert.servlet;
 
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import transfert.pages.HTMLheader;
import transfert.requetes.*;
 
/**
 * Servlet implementation class PageSelection2
 */
@WebServlet(description = "Selection CRE nouvelle version", urlPatterns = { "/PageSelection2" })
public class PageSelection2 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
        
	// Variables param�tres servlet serveurs/version CSS/version JS 
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
	
	// R�sultat requ�te
	static String Message;
	static boolean OKKO; 
	
	// Pr�paration page
	HTMLheader header = new HTMLheader("CRE - Transfert CRE inter-syst&egrave;me",
			                           "&copy; Groupe Pasteur Mutualit&eacute; - Version 1.1");	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PageSelection2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);

		// Nom servlet
		Servlet = config.getServletName();
		// R�cup�ration param�tres associ�s au servlet
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
		// Valeurs dans ini()
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
		
		// Session expiré --> Page Erreur
		if (sessionHTTP == null) {

			// ------------ A remettre en place apr�s les DEV ---------------------------------------------
			//request.getRequestDispatcher(lienErreur).forward(request, response);  // a mettre en commentaire pour d�v.
			
			// ------------ Creation d'une session http temporaire pour d�v. -----> A retirer apr�s les DEV
			HttpSession SessionLogin = request.getSession(true);
			//if session is not new then we are switching the session and start
			// a new one
			//if (!(SessionLogin.isNew())) {
			//   SessionLogin.invalidate();
			//   SessionLogin = request.getSession(true);
		    //}
 
		// Juste le profil 
			SessionLogin.setAttribute("Utilisateur", "AVTTST06");
		    SessionLogin.setAttribute("Serveur", "localhost");
			SessionLogin.setAttribute("MotdePasse", "AVTTST06");
			SessionLogin.setAttribute("BibFichier", "AVTGPMDTA");

			// R�cup informations session utilisateur
			sessionHTTP = request.getSession(false);
			
		}
			
		// Session OK --> Page HTML
		if (sessionHTTP != null) {

			// Initialisation informations g�n�rales 
			//Serveur = (String) sessionHTTP.getAttribute("Serveur");
			//Bib = (String) sessionHTTP.getAttribute("BibFichier");
			loginSession = (String) sessionHTTP.getAttribute("Utilisateur");
			mdpSession = (String) sessionHTTP.getAttribute("MotdePasse");
			
			// Initialisation tableaux avec les param�tres du formulaire
			// Code CRE col 1  
			if (request.getParameter("cass") != null) {p_formulaire[0] = request.getParameter("cass");}
			else {p_formulaire[0] = "";}

			// Code CRE col 2 
			if (request.getParameter("cass2") != null) {p_formulaire[1] = request.getParameter("cass2");}
			else {p_formulaire[1] = "";}
			
			// Type courrier   
			if (request.getParameter("typeCou") != null) {p_formulaire[2] = request.getParameter("typeCou");}
			else {p_formulaire[2] = "";}			
			
			// Code adh�rent    
			if (request.getParameter("codeAdh") != null) {p_formulaire[3] = request.getParameter("codeAdh");}
			else {p_formulaire[3] = "";}			
			
			// Date �cran     
			if (request.getParameter("dateCr") != null) {p_formulaire[4] = request.getParameter("dateCr");}
			else {p_formulaire[4] = "";}			
			
			// Serveur source
			if (request.getParameter("stat1") != null) {p_formulaire[5] = request.getParameter("stat1");}
   		    else {p_formulaire[5] = "";}

			// Serveur cible 
			if (request.getParameter("stat2") != null) {p_formulaire[6] = request.getParameter("stat2");}
   		    else {p_formulaire[6] = "";}
			
			// TODO : Pr�voir controle des param�tres re�us avant envoi de la requ�te 
			// ToolBox ctrl = new ToolBox(p_code);			
            // Controler les codes CRE saisis
				
			// Analyse param�tres du formulaire 
			if (!p_formulaire[0].equals("") || !p_formulaire[1].equals("") ||
				!p_formulaire[2].equals("") || !p_formulaire[3].equals("") || 
				!p_formulaire[4].equals("")) {
					
					parametres = true;  // Param�tres saisis  
					
					p_formulaire[0] = p_formulaire[0].replace("\r\n", "");
					//System.out.println("cass...  " + p_formulaire[0]);
					
					p_formulaire[1] = p_formulaire[1].replace("\r\n", "");
					//System.out.println("cass...  " + p_formulaire[1]);
					
		 			// Chargement des n� de CRE saisis 
					int  j = 0;
					// Formulaire n� CRE 1
					for (int i = 0; i < p_formulaire[0].trim().length(); i = i + 11) {
						 
						 try {TabCRE[j] = p_formulaire[0].substring(i, i+11);}
						 catch (Exception e) {TabCRE[j] = "0";}
						 //System.out.println("NumCRE..." + TabCRE[j]);
						 j++;
							  
					}
					// Formulaire n� CRE 2 
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
					 
					// Process lecture & Insertion CRE par n� CRE --------------------------------
					for (int i = 0; i < TabCRE.length; i++) {  
						if  (!TabCRE[i].equals("")) { 
							ParNumeroCRE = true;
					        requeteCRE.lectureCRE(TabCRE[i]);
		  			        // Insert CRE si OK -----------------------------------------------------
					        if (requeteCRE.getOKKO())  {requeteCRE.insertCRE();}
					        //if (!requeteCRE.getOKKO()) {break;}
						}
						 
					}  
 
					// Process de lecture &  Insertion CRE par autres crit�res ----------------------
					if (!ParNumeroCRE) {
						//requeteCRE.LectureCREAutreCriteres(p_formulaire[2], p_formulaire[3], p_formulaire[4] );
						requeteCRE.lectureCREAutreCriteres(); 
	  			        // Insert CRE si OK ---------------------------------------------------------
				        if (requeteCRE.getOKKO())  {requeteCRE.insertCRE();}
				        //if (!requeteCRE.getOKKO()) {break;}
					}
					 
					// Message = "E" + " Aucun CRE trait� , relancer votre requ�te..." ;
					// Message type "E" = Erreur
					// Message type "I" = Information
					
					if (requeteCRE.getOKKO()) {          // Si retour OK afficher message OK
						Message = "I" + " Termin� ! ----> CRE lu(s) en " + p_formulaire[5] + " : " 
					              + String.valueOf(requeteCRE.getCompteurLu()) + 
								  " ----> CRE �crit(s) en " + p_formulaire[6] + " : " 
					              + String.valueOf(requeteCRE.getCompteurEcrit());
					}                 
					else {                               // Si retour KO afficher message Erreur
						Message = requeteCRE.getMessageErreur();
			        }
					
			}  // Fin test 		 
			
			// Si param�tres --> affichage page HTML r�sultat 
			if (parametres) {
			 	
				if (Message.substring(0, 1).equals("I") || Message.substring(0, 1).equals("A")) { // Info/Alert sur page r�sultat 
					// Affichage page r�sultat si OK (type "I" pour info, "A" pour Alert)
					CREHtmlPageResultat(response, Message.substring(1, Message.length()), Message.substring(0, 1));
	                // Voir possibilit� d'une progression....				
				} 
				else {                                           // Erreur sur page r�sultat/ Suite requ�te CRE
					// Affichage formulaire et message d'erreur
					CREHtmlFormulaire(response, Message.substring(1, Message.length()));
				}

                // Pour pr�sentation projet / page de maintenance 				
				//response.sendRedirect(request.getContextPath() + "/" + "PageMaintenance.html");

			}
			// Si pas de param�tres --> Affichage formulaire vide � remplir
			else  {CREHtmlFormulaire(response, "");}
		
		}   // Fin de test session diff. de null
	
	}
	
	/** 
	 * @see CREHtmlFormulaire(HttpServletRequest res, String Erreur)
	*/
	protected boolean CREHtmlFormulaire(HttpServletResponse res, String LibelleMessage ) {

		// Initialisation Cache
		res.setContentType("text/html");
		res.setHeader("Pragma", "No-Cache");
		res.setDateHeader("Expires", 0);
		res.setHeader("Cache-Control", "no-Cache");
 
		// Initialisation �tat page HTML
		boolean EtatPageHTML = LibelleMessage.isEmpty();

		// Affichage page login 
		try {

			PrintWriter out;
			out = res.getWriter();
			
			// Flux HTML
			  
			// <head>
			  out.println(header.GetPageHeaderCRE().toString());

			// <body>
		      out.println("<body class=\"import\" onload=\"javascript:document.formulairegestip.cass.focus();\">\r\n");
		      out.println("<img src=\"img/GPM1.jpg\" width=120 height=120 align=\"left\">\r\n");
		      out.println("<script>\r\n");
		      out.println("document.writeln('<h3 align=\"center\">S&eacutelection pour transfert CRE au ' + DateJour +'</h3><br>');\r\n");
		      out.println("</script>\r\n");

		    // Partie Formulaire 
		      out.println("<div class=\"gestip-div1\">\r\n");
		      out.println("<form name=\"formulairegestip\" id=\"myform\" onsubmit=\"return ValidationGlobale(this.form)\">\r\n");
 
		    // Bouton Quitter
			  out.println("  <button class=\"btn btn-danger btn-sm\" type=\"button\" "
				        + "  onClick=\"location.href='" + Scheme + Adresse + Context + lienQuitter + "';\">");
			  out.println("  <span class=\"glyphicon glyphicon-off\"></span>");
			  out.println("D&eacute;connexion");
			  out.println("  </button>");
			   
			 // ID CRE D�but ----------------------------------------------------------------------
              out.println("<br><br>");  
		      out.println("<fieldset class=\"fieldset\"><legend class=\"legend\">ID CRE</legend>\r\n");
		      out.println("  <label for=\"assure\" class=\"gestip-div1-la1\">N&deg; CRE &agrave; transf&eacute;rer :</label>\r\n");
		      //<label for="assure" class="gestip-div1-la1">N° CRE à transf&eacute;rer :</label>
		      
		      out.println("  <textarea id=\"gt1\" class=\"gestip-div1-i\" textarea name=\"cass\" rows=\"10\" cols=\"9\" maxlength=\"102\" \r\n");
		      out.println("  onblur=\"codeAssure('blur')\" onfocus=\"codeAssure('focus')\" onkeypress=\"VerificationCar(event);\">\r\n");
		      out.println("  </textarea>&nbsp;&nbsp;&nbsp;&nbsp;\r\n");  

		      out.println("  <textarea id=\"gt2\" class=\"gestip-div1-i\" textarea name=\"cass2\" rows=\"10\" cols=\"9\" maxlength=\"102\" \r\n");
		      out.println("  onblur=\"codeAssure2('blur')\" onfocus=\"codeAssure2('focus')\" onkeypress=\"VerificationCar(event);\">\r\n");
		      out.println("  </textarea>\r\n");  
		      
		      out.println("</fieldset>\r\n"); 
		      // ID CRE Fin -----------------------------------------------------------------------
		      
		      // Autres crit�res D�but ------------------------------------------------------------
		      out.println("<br>");
		      out.println("<fieldset class=\"fieldset\"><legend class=\"legend\">Autres crit&egrave;res</legend>");
		      
              // Type courrier 
              out.println("<label for=\"idtype\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Type courrier :&nbsp;&nbsp;</label>\r\n");
              out.println("<input id=\"typecou\" class=\"gestip-div1-1\" type=\"text\" name=\"typeCou\" size=\"8\" maxlength=\"8\" onfocus=\"typeCourrier('focus')\" onblur=\"typeCourrier('blur')\" onkeypress=\"VerificationCar(event);\" >\r\n");		      
		      
              // Adh�rent 
              out.println("<label for=\"idadh\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Code adh&eacute;rent :</label>\r\n");
              out.println("<input id=\"codeadh\" class=\"gestip-div1-1\" type=\"text\" name=\"codeAdh\" size=\"6\" maxlength=\"6\" onfocus=\"codeAdherent('focus')\" onblur=\"codeAdherent('blur')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		        
              // Date CRE 
              out.println("<label for=\"periode\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date CRE :</label>\r\n");
              out.println("<input id=\"datecr\" class=\"gestip-div1-1\" type=\"text\" name=\"dateCr\" onblur=\"dateCRE('blur')\" onfocus=\"dateCRE('focus')\" onkeypress=\"VerificationCar(event);\"/>\r\n");  
		      
              out.println("</fieldset>\r\n"); 
		      // Autres crit�res Fin ------------------------------------------------------------		      
		      
		      // Environnement  ----------------------------------------------------------------------
		      out.println("<br><br>"); 
			  out.println("<fieldset class=\"fieldset\"><legend class=\"legend\">Environnements</legend>\r\n");
	          
			  // Liste environnement source ------------------------
		      out.println("<label for=\"statut\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Depuis environnement de :&nbsp;</label>\r\n");
		      out.println("<select name=\"stat1\" id=\"ListSource\" class=\"gestip-div1-i\" "
		            		+ "onblur=\"source('blur')\" onfocus=\"source('focus')\" onchange=\"source('blur');\">");
	          out.println("<option selected=\"selected\"></option>");
	          out.println("<option>PRODUCTION</option>");
	          out.println("<option>RECETTE</option>");
	          out.println("<option>DEV</option>");
	          out.println("</select>"); 			      
  
			  // Liste environnement cible ------------------------
		      out.println("<label for=\"statut\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Vers environnement de :&nbsp;&nbsp;</label>\r\n");
		      out.println(" <select name=\"stat2\" id=\"ListCible\" onblur=\"cible('blur')\" class=\"gestip-div1-i\" "
		                + "onfocus=\"cible('focus')\" onchange=\"cible('blur');\" >");
              out.println("<option selected=\"selected\"></option>");
              out.println("<option>RECETTE</option>");
              out.println("<option>RECETTE et DEV</option>");
              out.println("<option>DEV</option>");
              out.println("</select>"); 
			   
		      // Valider Formulaire                            
	          out.println("<button class=\"btn btn-primary\" name=\"entrer\" type=\"submit\"> \r\n");
	          out.println("  <span class=\"glyphicon glyphicon-ok\"></span>");
	          out.println("Valider");
			  out.println("</button>");
		      out.println("<br>\r\n");
		      
	          // Erreurs   
              if (!LibelleMessage.isEmpty()) {out.println("<span id=\"Msg\" class=\"" +"erreur" + "\"" + ">" + LibelleMessage.toString() + "</span>");}
              if (LibelleMessage.isEmpty())  {out.println("<span id=\"Msg\" class=\"" +"erreur" + "\"" + ">&nbsp;&nbsp</span>");}
		      
		      out.println("</fieldset>\r\n");
		      
		      out.println("</form>\r\n");
		      out.println("</div>\r\n");
		      out.println("</body>\r\n");
		      
			// Pied
			  out.println(header.GetPageFooter().toString());
		      
		      out.println("</html>\r\n");		 
			
			out.flush();
			out.close();

		} 
		catch (Exception e) {System.out.println("HTML page error : " + e.getMessage());}

		return EtatPageHTML;

	} // end of CREHtmlFormulaire()

	/**
	 * @see HcrLoginHtmlPage(HttpServletRequest res, String Erreur)
	 */
	protected boolean CREHtmlPageResultat(HttpServletResponse res, String Erreur, String TypeErreur) {

		// Initialisation Cache
		res.setContentType("text/html");
		res.setHeader("Pragma", "No-Cache");
		res.setDateHeader("Expires", 0);
		res.setHeader("Cache-Control", "no-Cache");

		// Initialisation etat de la page HTML
		boolean EtatPageHTML = Erreur.isEmpty();

		// Affichage page HTML
		try {

			PrintWriter out;
			out = res.getWriter();

			// Flux HTML
			// <head>
			out.println(header.GetPageHeaderCRE().toString());
		      
			// <body>
		    out.println("<body class=\"import\" onload=\"javascript:document.formulairegestip.cass.focus();\">\r\n");
		    out.println("<img src=\"img/GPM1.jpg\" width=120 height=120 align=\"left\">\r\n");
		    out.println("<script>\r\n");
		    out.println("document.writeln('<h3 align=\"center\">S&eacutelection pour transfert CRE au ' + DateJour +'</h3><br>');\r\n");
		    out.println("</script>\r\n");

		    // Partie Formulaire
		    out.println("<div class=\"gestip-div1\">\r\n");
		    out.println("<form name=\"formulairegestip\" id=\"myform\" onsubmit=\"return ValidationGlobale(this.form)\">\r\n");
		     
		    // Bouton Quitter
			out.println("  <button class=\"btn btn-danger btn-sm\" type=\"button\" "
				        + "  onClick=\"location.href='" + Scheme + Adresse + Context + lienQuitter + "';\">");
			out.println("  <span class=\"glyphicon glyphicon-off\"></span>");
			out.println("D&eacute;connexion");
			out.println("  </button>");
		    
			// ID CRE D�but ----------------------------------------------------------------------
            out.println("<br><br>");  
		    out.println("<fieldset class=\"fieldset\"><legend class=\"legend\">ID CRE</legend>\r\n");
		    out.println("  <label for=\"assure\" class=\"gestip-div1-la1\" >N&deg; CRE &agrave; transf&eacuterer :</label>\r\n");
		      
		    out.println("  <textarea id=\"gt1\" class=\"gestip-div1-i\" textarea name=\"cass\" rows=\"10\" cols=\"9\" maxlength=\"102\" \r\n");
		    out.println("  onblur=\"codeAssure('blur')\" onfocus=\"codeAssure('focus')\" onkeypress=\"VerificationCar(event);\">\r\n");
		    out.println("  </textarea>&nbsp;&nbsp;&nbsp;&nbsp;\r\n");  

		    out.println("  <textarea id=\"gt2\" class=\"gestip-div1-i\" textarea name=\"cass2\" rows=\"10\" cols=\"9\" maxlength=\"102\" \r\n");
		    out.println("  onblur=\"codeAssure2('blur')\" onfocus=\"codeAssure2('focus')\" onkeypress=\"VerificationCar(event);\">\r\n");
		    out.println("  </textarea>\r\n");  
		      
		    out.println("</fieldset>\r\n"); 
		    // ID CRE Fin -----------------------------------------------------------------------

		    // Autres crit�res D�but ------------------------------------------------------------
		    out.println("<br>");
		    out.println("<fieldset class=\"fieldset\"><legend class=\"legend\">Autres crit&egrave;res</legend>");
		      
            // Type courrier 
            out.println("<label for=\"idtype\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Type courrier :&nbsp;&nbsp;</label>\r\n");
            out.println("<input id=\"typecou\" class=\"gestip-div1-1\" type=\"text\" name=\"typeCou\" size=\"8\" maxlength=\"8\" onfocus=\"typeCourrier('focus')\" onblur=\"typeCourrier('blur')\" onkeypress=\"VerificationCar(event);\" >\r\n");		      
		      
            // Adh�rent 
            out.println("<label for=\"idadh\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Code adh&eacute;rent :</label>\r\n");
            out.println("<input id=\"codeadh\" class=\"gestip-div1-1\" type=\"text\" name=\"codeAdh\" size=\"6\" maxlength=\"6\" onfocus=\"codeAdherent('focus')\" onblur=\"codeAdherent('blur')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		        
            // Date CRE 
            out.println("<label for=\"periode\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date CRE :</label>\r\n");
            out.println("<input id=\"datecr\" class=\"gestip-div1-1\" type=\"text\" name=\"dateCr\" onblur=\"dateCRE('blur')\" onfocus=\"dateCRE('focus')\" onkeypress=\"VerificationCar(event);\"/>\r\n");  
		      
            out.println("</fieldset>\r\n"); 
		      // Autres crit�res Fin ------------------------------------------------------------		      
		    
	    
		    // Environnement  ----------------------------------------------------------------------
		    out.println("<br><br>"); 
			out.println("<fieldset class=\"fieldset\"><legend class=\"legend\">Environnements</legend>\r\n");
	          
			// Liste syt�me source ------------------------
		    out.println("<label for=\"statut\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Depuis syst&egrave;me source :&nbsp;</label>\r\n");
		    out.println("<select name=\"stat1\" id=\"ListSource\" class=\"gestip-div1-i\" "
		            		+ "onblur=\"source('blur')\" onfocus=\"source('focus')\" onchange=\"source('blur');\">");
	        out.println("<option selected=\"selected\"></option>");
	        out.println("<option>PRODUCTION</option>");
	        out.println("<option>RECETTE</option>");
	        out.println("<option>DEV</option>");
	        out.println("</select>"); 			    

			// Liste syst�me cible ------------------------
		    out.println("<label for=\"statut\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Vers syst&egrave;me cible :&nbsp;&nbsp;</label>\r\n");
		    out.println(" <select name=\"stat2\" id=\"ListCible\" onblur=\"cible('blur')\" class=\"gestip-div1-i\" "
		                + "onfocus=\"cible('focus')\" onchange=\"cible('blur');\" >");
            out.println("<option selected=\"selected\"></option>");
            out.println("<option>RECETTE</option>");
            out.println("<option>RECETTE et DEV</option>");
            out.println("<option>DEV</option>");
            out.println("</select>"); 
			   
		    // Valider Formulaire                            
	        out.println("<button class=\"btn btn-primary\" name=\"entrer\" type=\"submit\"> \r\n");
	        out.println("  <span class=\"glyphicon glyphicon-ok\"></span>");
	        out.println("Valider");
			out.println("</button>");
		    out.println("<br>\r\n");

	        // Info. requ�te 
		    //if (TypeErreur.equals("I")) {out.println("<span id=\"Msg\" class=\"" +"info" + "\"" + ">"   + Erreur.toString() + "</span>");}
            //if (TypeErreur.equals("E")) {out.println("<span id=\"Msg\" class=\"" +"erreur" + "\"" + ">" + Erreur.toString() + "</span>");}
		      
		    out.println("</fieldset>");

	        // Message apr�s traitement  
		    if (TypeErreur.equals("I")) {out.println("<span id=\"Msg\" class=\"" + "info" + "\"" + ">"   + Erreur.toString() + "</span>");}
            if (TypeErreur.equals("E")) {out.println("<span id=\"Msg\" class=\"" + "erreur" + "\"" + ">" + Erreur.toString() + "</span>");}
		    if (TypeErreur.equals("A")) {out.println("<span id=\"Msg\" class=\"" + "info" + "\"" + ">" + Erreur.toString() + "</span>");}
		    //	out.println("<script>\r\n");
		    //	out.println("alert(" + "\"" + Erreur.toString() + "\"" + ")");
		    //	out.println("</script>\r\n");
		    // }
		    
		    out.println("</form>\r\n");
		    out.println("</div>\r\n");			
			 
		    // Pied
			out.println(header.GetPageFooter().toString());

			out.println("</html>\r\n");

			out.flush();
			out.close();

		} 
		catch (Exception e) {System.out.println("HTML page error : " + e.getMessage());}
			

		return EtatPageHTML;

	} // end of CREHtmlPageResultat

}













