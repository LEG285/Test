/* **********************************************************************
 * Projet                 : TRANSFERT - Transfert des CRE entre système  
 * Package                : transfert.servlet
 * Class                  : PageSelection.java 
 *----------------------------------------------------------------------               
 * Objet                  : Sélection des CRE   
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 13/06/2025
 *----------------------------------------------------------------------
 * Chemin class jsp       : \apache-tomcat-7.0.69\work\Catalina\localhost\transfert\...
 ********************************************************************* */
package transfert.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import transfert.pages.HTMLheader;
import transfert.requetes.*;
  
/** 
 * Servlet implementation class PageSelection
 */ 
@WebServlet(description = "Selection transfert CRE", urlPatterns = { "/transfert.servlet.PageSelection" })
public class PageSelection extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Variables paramètres servlet serveurs/version CSS/version JS 
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
	 
	// Paramètres formulaire
	static String p_formulaire[] = new String[4]; 
	//static String TabCRE[] = {"","","","","","","","","","","","","","","","","","","",""}; 
	
	// Résultat requête
	static String Message;
	static boolean OKKO; 
	
	// Préparation page
	HTMLheader header = new HTMLheader("CRE - Transfert CRE inter-syst&egrave;me",
			                           "Groupe Pasteur Mutualit&eacute; - Version 1.0");

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PageSelection() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * HttpServlet#init()
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		// Nom servlet
		Servlet = config.getServletName();
		// Récupération paramètres associés au servlet
	 	//serveurLOC  = getInitParameter("serveurLOC");
	 	//bibFic      = getInitParameter("bibFichier");
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

	} // end of init()

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	synchronized protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Appel Direct doPost
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	synchronized protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
		// Envoi flux HTML
		int PortN = request.getServerPort();
		String Port = new String();
		Port = String.valueOf(PortN);
		Serveur = request.getServerName();
		Adresse = request.getServerName() + ":" + Port;
		Context = request.getContextPath() + "/";
		Scheme  = request.getScheme() + "://";
	
		// Récup informations session utilisateur
		HttpSession sessionHTTP = request.getSession(false);
		Boolean parametres = false;

		
		// Variables de travail
		String TabCRE[] = {"","","","","","","","","","","","","","","","","","","","","","","","","","","",""};		
		
		// Session expirée --> Page Erreur
		if (sessionHTTP == null) {

			// ------------ A remettre en place après les DEV ---------------------------------------------
			//request.getRequestDispatcher(lienErreur).forward(request, response);  // a mettre en commentaire pour dév.
			
			// ------------ Creation d'une session http temporaire pour dév. -----> A retirer après les DEV
			HttpSession SessionLogin = request.getSession(true);
			// if session is not new then we are switching the session and start
			// a new one
			//if (!(SessionLogin.isNew())) {
				//SessionLogin.invalidate();
				//SessionLogin = request.getSession(true);
			//}
 
			// Juste le profil 
			SessionLogin.setAttribute("Utilisateur", "LEGUILLOU");
			SessionLogin.setAttribute("Serveur", "localhost");
			SessionLogin.setAttribute("MotdePasse", "xyzabcd");
			SessionLogin.setAttribute("BibFichier", "AVTGPMDTA");

			// Récup informations session utilisateur
			sessionHTTP = request.getSession(false);
			
		}
			
		// Session OK --> Page HTML
		if (sessionHTTP != null) {

			// Initialisation informations générales 
			//Serveur = (String) sessionHTTP.getAttribute("Serveur");
			//Bib = (String) sessionHTTP.getAttribute("BibFichier");
			loginSession = (String) sessionHTTP.getAttribute("Utilisateur");
			mdpSession = (String) sessionHTTP.getAttribute("MotdePasse");
			
			// Initialisation tableaux des paramètres du formulaire
			// Code CRE col 1  
			if (request.getParameter("cass") != null) {p_formulaire[0] = request.getParameter("cass");}
			else {p_formulaire[0] = "";}

			// Code CRE col 2 
			if (request.getParameter("cass2") != null) {p_formulaire[1] = request.getParameter("cass2");}
			else {p_formulaire[1] = "";}
			
			// Serveur source
			if (request.getParameter("stat1") != null) {p_formulaire[2] = request.getParameter("stat1");}
   		    else {p_formulaire[2] = "";}

			// Serveur cible 
			if (request.getParameter("stat2") != null) {p_formulaire[3] = request.getParameter("stat2");}
   		    else {p_formulaire[3] = "";}
			
			// TODO : Prévoir controle des paramètres reçus avant envoi de la requête 
			// ToolBox ctrl = new ToolBox(p_code);			
            // Controler les codes CRE saisis
				
			// Analyse paramètre du formulaire 
			if (!p_formulaire[0].equals("") || !p_formulaire[1].equals("") ) {
					
					// Gestion paramètres saisis
					parametres = true;  
					 
					p_formulaire[0] = p_formulaire[0].replace("\r\n", "");
					//System.out.println("cass...  " + p_formulaire[0]);
					
					p_formulaire[1] = p_formulaire[1].replace("\r\n", "");
					//System.out.println("cass...  " + p_formulaire[1]);
					
		 			// Chargement des n° de CRE saisis 
					int  j = 0;
					
					// Formulaire n° CRE 1
					for (int i = 0; i < p_formulaire[0].trim().length(); i = i +11) {
						 
						 try {TabCRE[j] = p_formulaire[0].substring(i, i+11);}
						 catch (Exception e) {TabCRE[j] = "0";}
						 //System.out.println("NumCRE..." + TabCRE[j]);
						 j++;
							  
					}
					// Formulaire n° CRE 2 
					for (int i = 0; i < p_formulaire[1].trim().length(); i = i +11) {
						 
						 try {TabCRE[j] = p_formulaire[1].substring(i, i+11);}
						 catch (Exception e) {TabCRE[j] = "0";}
						 //System.out.println("NumCRE..." + TabCRE[j]);
						 j++;
							  
					}
					
					// PROD source 
					if (p_formulaire[2].equals("PRODUCTION")) {
					   Serveur = serveurPROD;
					   Login  = loginPROD;
					   Mdp = mdpPROD;
					   Bib = bibPROD;
					}
					// REC source
					if (p_formulaire[2].equals("RECETTE")) {
					   Serveur = serveurREC;
					   Login  = loginREC;
					   Mdp = mdpREC;
					   Bib = bibREC;
					}
					// DEV source
					if (p_formulaire[2].equals("DEV")) {
					   Serveur = serveurDEV;
					   Login = loginSession;
					   Mdp = mdpSession;
					   Bib = bibDEV;
					}
 
					// RECETTE cible -----------------------
					if (p_formulaire[3].equals("RECETTE")) {
					   ServeurC = serveurREC;
					   LoginC  = loginREC;
					   MdpC = mdpREC;
					   BibC = bibREC;
					}
					// RECETTE + DEV cible (A developer)
					if (p_formulaire[3].equals("RECETTE et DEV")) {
					   ServeurC = serveurREC;
					   LoginC  = loginREC;
					   MdpC = mdpREC;
					   BibC = bibREC;
					}
					// DEV cible
					if (p_formulaire[3].equals("DEV")) {
					   ServeurC = serveurDEV;
					   BibC = bibDEV;
					   LoginC = loginSession;
					   MdpC= mdpSession;
					}
					
					// Initialisation requete CRE ----------------------------------------------
					RequeteGestionCRE requeteCRE = new RequeteGestionCRE(Serveur, Login, Mdp, Bib,
							                                             ServeurC, LoginC, MdpC, BibC, 
							                                             p_formulaire);
					
					// Process de lecture &  Insertion CRE -------------------------------------------
					//Message = "E" + " Aucun CRE traité , relancer votre requête..." ;
					for (int i = 0; i < TabCRE.length; i++) {  
						if  (!TabCRE[i].equals("")) { 
					        requeteCRE.LectureCRE(TabCRE[i]);
		  			        // Insert CRE si OK -------------------------------------------------------
					        if (requeteCRE.getOKKO())  {requeteCRE.InsertCRE(TabCRE[i]);}
					        if (!requeteCRE.getOKKO()) {break;}
						}
					}
 
					if (requeteCRE.getOKKO()) {    // Si retour OK afficher message OK
						Message = "I" + " Transfert CRE OK !" ;
					}                 
					else {                         // Si retour KO afficher message Erreur
						Message = requeteCRE.getMessageErreur();
			        }
			}			
			
			// Si paramètres --> affichage page HTML résultat 
			if (parametres) {
			 	
				if (Message.substring(0, 1).equals("I")) {          // Info / page résultat 
					// Affichage page résultat si OK (Erreur "I" pourinformation)
					CREHtmlPageResultat(response, Message.substring(1, Message.length()), Message.substring(0, 1));
	                // Voir possibilité d'une progression				
				} 
				else {                                           // Erreur / Suite requête CRE
					// Affichage formulaire et message d'erreur
					CREHtmlFormulaire(response, Message.substring(1, Message.length()));
				}

                // Pour présentation projet / page de maintenance 				
				//response.sendRedirect(request.getContextPath() + "/" + "PageMaintenance.html");

			}
			// Si pas de paramètres --> Affichage formulaire vide à remplir
			else  {CREHtmlFormulaire(response, "");}
		
		}   // Fin de test session diff. de null
		
	}   // End of doPost()

	/** 
	 * @see CREHtmlFormulaire(HttpServletRequest res, String Erreur)
	*/
	protected boolean CREHtmlFormulaire(HttpServletResponse res, String LibelleMessage ) {

		// Initialisation Cache
		res.setContentType("text/html");
		res.setHeader("Pragma", "No-Cache");
		res.setDateHeader("Expires", 0);
		res.setHeader("Cache-Control", "no-Cache");
 
		// Initialisation état page HTML
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
			  
			 // ID CRE Début ----------------------------------------------------------------------
              out.println("<br><br><br>"); 
		      out.println("<fieldset class=\"fieldset\"><legend class=\"legend\">ID CRE</legend>\r\n");
		      out.println("  <label for=\"assure\" class=\"gestip-div1-la1\" >N° CRE à transférer :</label>\r\n");
		         
		      out.println("  <textarea id=\"gt1\" class=\"gestip-div1-i\" textarea name=\"cass\" rows=\"15\" cols=\"9\" maxlength=\"168\" \r\n");
		      out.println("  onblur=\"codeAssure('blur')\" onfocus=\"codeAssure('focus')\" onkeypress=\"VerificationCar(event);\">\r\n");
		      out.println("  </textarea>&nbsp;&nbsp;&nbsp;&nbsp;\r\n");  

		      out.println("  <textarea id=\"gt2\" class=\"gestip-div1-i\" textarea name=\"cass2\" rows=\"15\" cols=\"9\" maxlength=\"168\" \r\n");
		      out.println("  onblur=\"codeAssure2('blur')\" onfocus=\"codeAssure2('focus')\" onkeypress=\"VerificationCar(event);\">\r\n");
		      out.println("  </textarea>\r\n");  
		      
		      out.println("</fieldset>\r\n"); 
		      // ID CRE Fin -----------------------------------------------------------------------
		      
		      // Environnement  ----------------------------------------------------------------------
		      out.println("<br><br><br>"); 
			  out.println("<fieldset class=\"fieldset\"><legend class=\"legend\">ENVIRONNEMENT</legend>\r\n");
	          
			  // Liste environnement source ------------------------
		      out.println("<label for=\"statut\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Depuis environnement de :&nbsp;&nbsp;</label>\r\n");
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
              if (!LibelleMessage.isEmpty()) {
            	  //out.println("<span id=\"Msg\" class=\"" +"erreur" + "\"" + ">" + LibelleMessage.toString() + "</span>");}
            	  out.println("<script>");
            	     out.println("alert("+ "I am an alert box!");
                 out.println("</script>");
              }
              
              
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
		    
			// ID CRE Début ----------------------------------------------------------------------
            out.println("<br><br><br>"); 
		    out.println("<fieldset class=\"fieldset\"><legend class=\"legend\">ID CRE</legend>\r\n");
		    out.println("  <label for=\"assure\" class=\"gestip-div1-la1\" >N° CRE à transférer :</label>\r\n");
		      
		    out.println("  <textarea id=\"gt1\" class=\"gestip-div1-i\" textarea name=\"cass\" rows=\"15\" cols=\"9\" maxlength=\"168\" \r\n");
		    out.println("  onblur=\"codeAssure('blur')\" onfocus=\"codeAssure('focus')\" onkeypress=\"VerificationCar(event);\">\r\n");
		    out.println("  </textarea>&nbsp;&nbsp;&nbsp;&nbsp;\r\n");  

		    out.println("  <textarea id=\"gt2\" class=\"gestip-div1-i\" textarea name=\"cass2\" rows=\"15\" cols=\"9\" maxlength=\"168\" \r\n");
		    out.println("  onblur=\"codeAssure2('blur')\" onfocus=\"codeAssure2('focus')\" onkeypress=\"VerificationCar(event);\">\r\n");
		    out.println("  </textarea>\r\n");  
		      
		    out.println("</fieldset>\r\n"); 
		    // ID CRE Fin -----------------------------------------------------------------------

		    // Environnement  ----------------------------------------------------------------------
		    out.println("<br><br><br>"); 
			out.println("<fieldset class=\"fieldset\"><legend class=\"legend\">ENVIRONNEMENT</legend>\r\n");
	          
			// Liste sytème source ------------------------
		    out.println("<label for=\"statut\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Depuis syst&egrave;me source :&nbsp;&nbsp;</label>\r\n");
		    out.println("<select name=\"stat1\" id=\"ListSource\" class=\"gestip-div1-i\" "
		            		+ "onblur=\"source('blur')\" onfocus=\"source('focus')\" onchange=\"source('blur');\">");
	        out.println("<option selected=\"selected\"></option>");
	        out.println("<option>PRODUCTION</option>");
	        out.println("<option>RECETTE</option>");
	        out.println("<option>DEV</option>");
	        out.println("</select>"); 			    

			// Liste système cible ------------------------
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

	        // Info. requête 
		    if (TypeErreur.equals("I")) {out.println("<span id=\"Msg\" class=\"" +"info" + "\"" + ">"   + Erreur.toString() + "</span>");}
            if (TypeErreur.equals("E")) {out.println("<span id=\"Msg\" class=\"" +"erreur" + "\"" + ">" + Erreur.toString() + "</span>");}
		      
		    out.println("</fieldset>");
		      
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

} // end of class
