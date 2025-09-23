/* **********************************************************************
 * Projet                 : PRESTIJ - AUTOMATISATION des FLUX IJ   
 * Package                : transfert
 * Class                  : PageSelection.java 
 *----------------------------------------------------------------------               
 * Objet                  : Rechercher et consultation des flux GESTIP  
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 28 03 2018 
 *----------------------------------------------------------------------
 * Chemin class jsp       : \apache-tomcat-7.0.69\work\Catalina\localhost\transfert\...
 ********************************************************************* */
package transfert;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import transfertoutils.*;

/**
 * Servlet implementation class PageSelection
 */
@WebServlet(description = "Consultation des flux GESTIP", urlPatterns = { "/PageSelection" })
public class PrestijGestip extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Variables paramètres servlet serveur/version CSS/version JS
	static String servlet, versionCSS, versionJS;
	static String Serveur, login, Mdp, Bib, Scheme ;
	
	static String lienQuitter, lienErreur, retourMenu;

	// Initialisation variables HTTP
	static String Servlet, Programme, Adresse, Context;

	// Paramètres formulaire
	static String p_formulaire[] = new String[8]; 
	
	// Données renvoyées de la requête  IBM i
	static Vector<String> col11 = new Vector<String>();
	static Vector<String> col21 = new Vector<String>();
	static Vector<String> col31 = new Vector<String>();
	static Vector<String> col41 = new Vector<String>();
	static Vector<String> col51 = new Vector<String>();
	static Vector<String> col61 = new Vector<String>();
	static Vector<String> col71 = new Vector<String>();
	static Vector<String> col81 = new Vector<String>();
	
	static Vector<String> col32 = new Vector<String>();
	static Vector<String> col42 = new Vector<String>();
	static Vector<String> col62 = new Vector<String>();
	static Vector<String> col72 = new Vector<String>();
	static Vector<String> col82 = new Vector<String>();
	
	static Vector<String> col43 = new Vector<String>();
	static Vector<String> col44 = new Vector<String>();
	static Vector<String> col45 = new Vector<String>();
	
	// Résultat requête
	static String Message;
	static boolean OKKO; 

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PrestijGestip() {
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

		// Récupération des Paramètres associés au servlet
		versionCSS = getInitParameter("versionCSS");
		versionJS = getInitParameter("versionJS");
		retourMenu = getInitParameter("retourMenu");
 		lienQuitter = getInitParameter("lienQuitter"); 		
		lienErreur = getInitParameter("lienErreur");

		// Valeurs dans ini()
		System.out.println("init servlet : " + Servlet);

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

		// Session expirée --> Page Erreur
		if (sessionHTTP == null) {

			// A externaliser !!!
			prestijHtmlSessionError(response);   // -----> a mettre en commentaire le temps du developpement
            
			/*
			// Creation d'une session http temporaire pour dév. -----> A retirer pour mise en recette
			HttpSession SessionLogin = request.getSession(true);
			// if session is not new then we are switching the session and start
			// a new one
			if (!(SessionLogin.isNew())) {
				SessionLogin.invalidate();
				SessionLogin = request.getSession(true);
			}

			SessionLogin.setAttribute("Serveur", "cgamrec");
			SessionLogin.setAttribute("Utilisateur", "TLEGUILLOU");
			SessionLogin.setAttribute("MotdePasse", "mai2016");
			SessionLogin.setAttribute("BibFichier", "PRESTIJ");

			// Récup informations session utilisateur
			sessionHTTP = request.getSession(false);
		    */
			
		}
        
			
		// Session OK --> Page HTML
		if (sessionHTTP != null) {

			// Initialisation informations générales 
			Serveur = (String) sessionHTTP.getAttribute("Serveur");
			login = (String) sessionHTTP.getAttribute("Utilisateur");
			Mdp = (String) sessionHTTP.getAttribute("MotdePasse");
			Bib = (String) sessionHTTP.getAttribute("BibFichier");

			// Initialisation tableaux des paramètres du formulaire 
			if (request.getParameter("cass") != null) {p_formulaire[0] = request.getParameter("cass");}
			else {p_formulaire[0] = "";}
			if (request.getParameter("nss") != null) {p_formulaire[1] = request.getParameter("nss");}
			else {p_formulaire[1] = "";}
			if (request.getParameter("nom") != null) {p_formulaire[2] = request.getParameter("nom");}
			else {p_formulaire[2] = "";}
			if (request.getParameter("prenom") != null) {p_formulaire[3] = request.getParameter("prenom");}
			else {p_formulaire[3] = "";}
			if (request.getParameter("siret") != null) {p_formulaire[4] = request.getParameter("siret");}
			else {p_formulaire[4] = "";}
			if (request.getParameter("debut") != null) {p_formulaire[5] = request.getParameter("debut");}
			else {p_formulaire[5] = "";}
			if (request.getParameter("fin") != null) {p_formulaire[6] = request.getParameter("fin");}
			else {p_formulaire[6] = "";}
			if (request.getParameter("stat") != null) {p_formulaire[7] = request.getParameter("stat");}
   		    else {p_formulaire[7] = "";}

			// TODO : Prévoir controle des paramètres reçus avant envoi de la requête 
			// ToolBox ctrl = new ToolBox(p_code);			

			// Boucle d'analyse du formulaire saisi + extraction données
			for (int i = 0; i < p_formulaire.length; i++) {
				
				if (!p_formulaire[i].equals("")) {
					
					parametres = true;
					RequeteFluxGestip requete = new RequeteFluxGestip(Serveur, login, Mdp, Bib, p_formulaire);
				    
					requete.LectureGestip();
					OKKO  = requete.getOKKO();
					Message = requete.getMessageErreur();
					col11 = requete.getc11();
					col21 = requete.getc21();
					col31 = requete.getc31();
					col41 = requete.getc41();
					col51 = requete.getc51();
					col61 = requete.getc61();
					col71 = requete.getc71();
					col81 = requete.getc81();
					
					col32 = requete.getc32();
					col42 = requete.getc42();
					col62 = requete.getc62();
					col72 = requete.getc72();
					col82 = requete.getc82();
					
					col43 = requete.getc43();
					col44 = requete.getc44();
					col45 = requete.getc45();
					
					break;    
				}			
			}
			
			// Si paramètres --> affichage page HTML résultat 
			if (parametres) {
				
				if (Message.substring(0, 1).equals("I")) {  // Info / page résultat 
					
					// Affichage page résultat si OK (Erreur "I" pourinformation)
					GestipHtmlPageResultat(response, Message.substring(1, Message.length()), Message.substring(0, 1));

				} 
				else {                                      // Erreur / Suite requête GESTIP
					
					// Affichage formulaire et message d'erreur
					GestipHtmlFormulaire(response, Message.substring(1, Message.length()));
					
				}
			
			}
			
			// Si pas de paramètres --> Affichage formulaire vide à remplir
			else  {

				GestipHtmlFormulaire(response, "");

			} 

		} // Fin de test session diff. de null

	} // end of doPost()

	/** 
	 * @see GestipHtmlFormulaire(HttpServletRequest res, String Erreur)
	 */
	protected boolean GestipHtmlFormulaire(HttpServletResponse res, String LibelleMessage) {

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
			
			// Envoi du flux HTML
			
			// Partie Entete
		      out.println("<!DOCTYPE html>\r\n");
		      out.println("<html>\r\n");
		      out.println("<head>\r\n");
		      out.println("  <title>PRESTIJ - Consultation des flux</title>\r\n");
		      out.println("  <meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\" />\r\n");
		      out.println("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n");
		      out.println("  <link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.12.1/themes/ui-lightness/jquery-ui.css\">\r\n");
		      out.println("  <link rel=stylesheet type=\"text/css\" href=\"css/transfert.css\" media=\"screen\"/>\r\n");
		      out.println("  <script src=\"//code.jquery.com/jquery-1.12.4.js\"></script>  \r\n");
		      out.println("  <script src=\"//code.jquery.com/ui/1.12.1/jquery-ui.js\"></script>    \r\n");
		      out.println("  <script type=\"text/javascript\" src=\"js/transfert.js\"></script>\r\n");
		      out.println("</head>\r\n");
		      
			    // Partie Body
		      out.println("<body class=\"import\" onload=\"javascript:document.formulairegestip.cass.focus();\">\r\n");
		      out.println("<img src=\"img/logo_home.jpg\" width=120 height=60 align=\"left\">\r\n");
		      out.println("<script>    \r\n");
		      out.println("document.writeln('<h3 align=\"center\">Recherche et consultation des flux GESTIP au ' + DateJour +'</h3><br>');\r\n");
		      out.println("</script>   \r\n");

		      // Partie Formulaire 
		      out.println("<div class=\"gestip-div1\">\r\n");
		      out.println("<form name=\"formulairegestip\" id=\"myform\" onsubmit=\"return ValidationGlobale(this.form)\">\r\n");
		      
		      out.println("<fieldset><legend>Référence</legend>  \r\n");
		      
		      out.println("  <label for=\"assure\" class=\"gestip-div1-la1\">N° assur\351</label>                \r\n");
		      out.println("  <input id=\"gt1\" class=\"gestip-div1-i\" type=\"text\" name=\"cass\" \r\n");
		      out.println("         size=\"7\" maxlength=\"7\" onblur=\"codeAssure('blur')\" onfocus=\"codeAssure('focus')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		      
		      out.println("   <br><label for=\"nss\" class=\"gestip-div1-la1\">Numéro SS</label>\r\n");
		      out.println("  <input id=\"gt2\" class=\"gestip-div1-i\" type=\"text\"  name=\"nss\" size=\"13\" \r\n");
		      out.println("        maxlength=\"13\" onfocus=\"numeroSS('focus')\" onblur=\"numeroSS('blur')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		      
		      out.println("    <br><label for=\"nom\" class=\"gestip-div1-la1\">Nom</label>\r\n");
		      out.println("    <input id=\"gt3\" class=\"gestip-div1-i\" type=\"text\" name=\"nom\" size=\"50\" \r\n");
		      out.println("         maxlength=\"50\" onblur=\"nomAssure('blur')\" onfocus=\"nomAssure('focus')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		      
		      out.println("     <br><label for=\"prenom\" class=\"gestip-div1-la1\">Pr\351nom</label>\r\n");
		      out.println("       <input id=\"gt4\" class=\"gestip-div1-i\" type=\"text\" name=\"prenom\" size=\"50\" \r\n");
		      out.println("             maxlength=\"50\" onblur=\"prenomAssure('blur')\" onfocus=\"prenomAssure('focus')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		      out.println("</fieldset>\r\n");
		      
		      out.println("<br>\r\n");
		      
		      out.println("<fieldset>\r\n");
		      out.println("    <label for=\"siren\" class=\"gestip-div1-la1\">Siren/Siret</label>\r\n");
		      out.println("    <input id=\"gt5\" class=\"gestip-div1-i\" type=\"text\"  name=\"siret\" size=\"14\"\r\n");
		      out.println("        maxlength=\"14\" onblur=\"siren('blur')\" onfocus=\"siren('focus')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		      out.println("</fieldset>\r\n");
		      
		      out.println("<br> \r\n");
		      
		      out.println("<fieldset>\r\n");
		      out.println("  <label for=\"periode\" class=\"gestip-div1-la1\">Période du</label>\r\n");
		      out.println("  <input id=\"datedebut6\" class=\"gestip-div1-i\" type=\"text\" name=\"debut\"\r\n");
		      out.println("         onblur=\"dateDebut('blur')\" onfocus=\"dateDebut('focus')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		      out.println("   <label for=\"periode2\">au</label>\r\n");
		      out.println("   <input id=\"datefin7\" class=\"gestip-div1-i\" type=\"text\" name=\"fin\"\r\n");
		      out.println("       onblur=\"dateFin('blur')\" onfocus=\"dateFin('focus')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		      out.println("   <label for=\"statut\">&nbsp;&nbsp;&nbsp;&nbsp;Statut&nbsp;&nbsp;</label>\r\n");
		      
		      
		      out.println(" <select name=\"stat\" id=\"ListStatut\" onblur=\"statut('blur')\" class=\"gestip-div1-i\" "
		      		                + "onfocus=\"statut('focus')\" onchange=\"statut('blur');\" >");
   	              out.println("<option selected=\"selected\">Tous</option>");
		          out.println("<option>ARL Accepté</option>");
		          out.println("<option>ARL Rejeté</option>");
		          out.println("<option>CR Accepté</option>");
		          out.println("<option>CR Rejeté</option>");
              out.println("</select>"); 
		      
		      // Partie Boutons                            
		      out.println("    <input class=\"gestip-div1-s1\" type=\"submit\" name=\"entrer\" value=\"Valider\" />\r\n");
		      out.println("    <input class=\"gestip-div1-s2\" type=\"button\" value=\"Retour menu\" "
		      		                    + "OnClick=\"window.location.href=" + "'" + Scheme + Adresse + Context + retourMenu + "'" + "\"" + "/>\r\n"); 
		      out.println("   <a class=\"deconnexion\" href=\"" + Scheme + Adresse + Context + lienQuitter + "\"" + ">D\351connexion</a>\r\n");
		      out.println("   <br><br>");
		      
	          // Affichage erreurs  
              if (!LibelleMessage.isEmpty()) {out.println("<span class=\"" +"erreur" + "\"" + ">" + LibelleMessage.toString() + "</span>");}
		      
		      out.println("</fieldset>\r\n");
		      out.println("</form>\r\n");
		      out.println("</div>\r\n");
		      out.println("</body>\r\n");
		      out.println("</html>");		 
			
			out.flush();
			out.close();

		} 
		catch (Exception e) {
			System.out.println("HTML page error : " + e.getMessage());
		}

		return EtatPageHTML;

	} // end of GestipHtmlFormulaire()

	/**
	 * @see HcrLoginHtmlPage(HttpServletRequest res, String Erreur)
	 */
	protected boolean GestipHtmlPageResultat(HttpServletResponse res, String Erreur, String TypeErreur) {

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

			// Envoi du flux HTML
			// Partie Entete
		      out.println("<!DOCTYPE html>\r\n");
		      out.println("<html>\r\n");
		      out.println("<head>\r\n");
		      out.println("  <title>PRESTIJ - Consultation des flux</title>\r\n");
		      out.println("  <meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\" />\r\n");
		      out.println("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n");
		      out.println("  <link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.12.1/themes/ui-lightness/jquery-ui.css\">\r\n");
		      out.println("  <link rel=stylesheet type=\"text/css\" href=\"css/transfert.css\" media=\"screen\"/>\r\n");
		      out.println("  <script src=\"//code.jquery.com/jquery-1.12.4.js\"></script>  \r\n");
		      out.println("  <script src=\"//code.jquery.com/ui/1.12.1/jquery-ui.js\"></script>    \r\n");
		      out.println("  <script type=\"text/javascript\" src=\"js/transfert.js\"></script>\r\n");
		      out.println("</head>\r\n");
		      
			    // Partie Body
		      out.println("<body class=\"import\" onload=\"javascript:document.formulairegestip.cass.focus();\">\r\n");
		      out.println("<img src=\"img/logo_home.jpg\" width=120 height=60 align=\"left\">\r\n");
		      out.println("<script>    \r\n");
		      out.println("document.writeln('<h3 align=\"center\">Recherche et consultation des flux GESTIP au ' + DateJour +'</h3><br>');\r\n");
		      out.println("</script>   \r\n");

		      // Partie Formulaire 
		      out.println("<div class=\"gestip-div1\">\r\n");
		      out.println("<form name=\"formulairegestip\" id=\"myform\" onsubmit=\"return ValidationGlobale(this.form)\">\r\n");
		      out.println("<fieldset><legend>Référence</legend>  \r\n");
		      
		      out.println("  <label for=\"assure\" class=\"gestip-div1-la1\">N° assur\351</label>                \r\n");
		      out.println("  <input id=\"gt1\" class=\"gestip-div1-i\" type=\"text\" name=\"cass\" \r\n");
		      out.println("         size=\"7\" maxlength=\"7\" onblur=\"codeAssure('blur')\" onfocus=\"codeAssure('focus')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		      
		      out.println("   <br><label for=\"nss\" class=\"gestip-div1-la1\">Numéro SS</label>\r\n");
		      out.println("  <input id=\"gt2\" class=\"gestip-div1-i\" type=\"text\"  name=\"nss\" size=\"13\" \r\n");
		      out.println("        maxlength=\"13\" onfocus=\"numeroSS('focus')\" onblur=\"numeroSS('blur')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		      
		      out.println("    <br><label for=\"nom\" class=\"gestip-div1-la1\">Nom</label>\r\n");
		      out.println("    <input id=\"gt3\" class=\"gestip-div1-i\" type=\"text\" name=\"nom\" size=\"50\" \r\n");
		      out.println("         maxlength=\"50\" onblur=\"nomAssure('blur')\" onfocus=\"nomAssure('focus')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		      
		      out.println("     <br><label for=\"prenom\" class=\"gestip-div1-la1\">Pr\351nom</label>\r\n");
		      out.println("       <input id=\"gt4\" class=\"gestip-div1-i\" type=\"text\" name=\"prenom\" size=\"50\" \r\n");
		      out.println("             maxlength=\"50\" onblur=\"prenomAssure('blur')\" onfocus=\"prenomAssure('focus')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		      out.println("</fieldset>\r\n");
		      
		      out.println("<br>");
		      
		      out.println("<fieldset>\r\n");
		      out.println("    <label for=\"siren\" class=\"gestip-div1-la1\">Siren/Siret</label>\r\n");
		      out.println("    <input id=\"gt5\" class=\"gestip-div1-i\" type=\"text\"  name=\"siret\" size=\"14\"\r\n");
		      out.println("        maxlength=\"14\" onblur=\"siren('blur')\" onfocus=\"siren('focus')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		      out.println("</fieldset>\r\n");
		      
		      out.println("<br>");
		      out.println("<fieldset>");
		      out.println("  <label for=\"periode\" class=\"gestip-div1-la1\">Période du</label>\r\n");
		      out.println("  <input id=\"datedebut6\" class=\"gestip-div1-i\" type=\"text\" name=\"debut\"\r\n");
		      out.println("         onblur=\"dateDebut('blur')\" onfocus=\"dateDebut('focus')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		      out.println("   <label for=\"periode2\">au</label>\r\n");
		      out.println("   <input id=\"datefin7\" class=\"gestip-div1-i\" type=\"text\" name=\"fin\"\r\n");
		      out.println("       onblur=\"dateFin('blur')\" onfocus=\"dateFin('focus')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		      
		      out.println("   <label for=\"statut\">&nbsp;&nbsp;&nbsp;&nbsp;Statut&nbsp;&nbsp;</label>\r\n");
		      
		      out.println(" <select name=\"stat\" id=\"ListStatut\" onblur=\"statut('blur')\" onfocus=\"statut('focus')\" onchange=\"statut('blur');\" >");
	              out.println("<option selected=\"selected\">Tous</option>");
	              out.println("<option>ARL Accepté</option>");
	              out.println("<option>ARL Rejeté</option>");
	              out.println("<option>CR Accepté</option>");
	              out.println("<option>CR Rejeté</option>");
              out.println("</select>");
        		  
		      // Partie Boutons                            
		      out.println("    <input class=\"gestip-div1-s1\" type=\"submit\" name=\"entrer\" value=\"Valider\" />\r\n");
		      out.println("    <input class=\"gestip-div1-s2\" type=\"button\" value=\"Retour menu\" "
		      		                    + "OnClick=\"window.location.href=" + "'" + Scheme + Adresse + Context + retourMenu + "'" + "\"" + "/>\r\n"); 
		      out.println("   <a class=\"deconnexion\" href=\"" + Scheme + Adresse + Context + lienQuitter + "\"" + ">D\351connexion</a>\r\n");
		      out.println("<br><br>");
		      
	          // Affichage message information requete  
		      if (TypeErreur.equals("I")) {out.println("<span class=\"" +"info" + "\"" + ">"   + Erreur.toString() + "</span>");}
              if (TypeErreur.equals("E")) {out.println("<span class=\"" +"erreur" + "\"" + ">" + Erreur.toString() + "</span>");}
		      
		      out.println("</fieldset>");
		      out.println("</form>\r\n");
		      out.println("</div>\r\n");			
			
		      // Tableau résultat 
		      // Entete Tableau
		      out.println("<div align=\"center\" class=\"gestip-div2\">\r\n");
		      out.println(" <table class=\"gestip-div2-table1\" >\r\n");
		      out.println("    <tr>\r\n");
		      out.println("    <td class=\"g-h-assu\">Assuré</td>\r\n");
		      out.println("    <td class=\"g-h-ss\">N° SS</td>                \r\n");
		      out.println("    <td class=\"g-h-nom\">Nom / Prénom</td>\r\n");
		      out.println("    <td class=\"g-h-demi\">Demande Inscription</td>                \r\n");
		      out.println("    <td class=\"g-h-dtdem\">Date Demande</td>                \r\n");
		      out.println("    <td class=\"g-h-stdem\">Statut demande</td>\r\n");
		      out.println("    <td class=\"g-h-nivrj\">Niveau</td>\r\n");
		      out.println("    <td class=\"g-h-librj\">Code et Libellé</td> \r\n");
		      out.println("    </tr>\r\n");
		      out.println(" </table>\r\n");
		      out.println("</div>\r\n");

		      // Détail Tableau
		      
		      // Détermination hauteur de ma page résultat
		      BoiteOutils h = new BoiteOutils(col11.size());
		      out.println("<div align=\"center\" class=\"gestip-div3\" style=\"" + "height:" + h.CalculHauteur() + "px;" + "\"" + ">");
		      out.println("<table class=\"gestip-div3-table2\" style=\"" + "height:" + h.CalculHauteur() + "px;" + "\"" + ">");
		      
		      // Boucle détail info GESTIP
		      for (int i = 0; i < col11.size(); i++) {
		    	  
                 // Ligne 1 Assuré, N° SS, Nom,.... 
		         out.println("<tr>");
		            out.println("  <td class=\"g-d-assu-deb\">"  + col11.elementAt(i) + "</td>");
		            out.println("  <td class=\"g-d-ss-deb\">"    + col21.elementAt(i) + "</td>");
		            out.println("  <td class=\"g-d-nom-deb\">"   + col31.elementAt(i) + "</td>");
		            out.println("  <td class=\"g-d-demi-deb\">"  + col41.elementAt(i) + "</td>");
		            out.println("  <td class=\"g-d-dtdem-deb\">" + col51.elementAt(i) + "</td>");
		            
				 if (col61.elementAt(i).equals("Accepté")) {
					out.println("  <td class=\"g-d-stdem\">" + col61.elementAt(i) + "</td>"); // Statut ARL Accepté														// ARL
					out.println("  <td class=\"g-d-nivrj\">" + col71.elementAt(i) + "</td>"); // Niveau ARL Accepté
					out.println("  <td class=\"g-d-librj\">" + col81.elementAt(i) + "</td>"); // Libelle ARL Accepté
				 }

				 if (col61.elementAt(i).equals("Rejeté")) {
					out.println("  <td class=\"g-d-stdem-R\">" + col61.elementAt(i) + "</td>"); // Statut ARL Rejeté
					out.println("  <td class=\"g-d-nivrj-R\">" + col71.elementAt(i) + "</td>"); // Niveau ARL Rejeté
					out.println("  <td class=\"g-d-librj-R\">" + col81.elementAt(i) + "</td>"); // Libelle ARL Rejeté
				 }
		         out.println("</tr>");
		         
		         // Ligne 2 Prenom....
		         out.println("<tr>");
		            out.println("  <td class=\"g-d-assu\" ></td>");
		            out.println("  <td class=\"g-d-ss\"   ></td>");
		            out.println("  <td class=\"g-d-nom\"  >"  + col32.elementAt(i) + "</td>");
		            out.println("  <td class=\"g-d-demi\" >"  + col42.elementAt(i) + "</td>");
		            out.println("  <td class=\"g-d-dtdem\"></td>");
					if (col62.elementAt(i).equals("Accepté")) {		            
		               out.println("  <td class=\"g-d-stdem\">"  + col62.elementAt(i) + "</td>"); // Statut CR Accepté 
		               out.println("  <td class=\"g-d-nivrj\">"  + col72.elementAt(i) + "</td>"); // Niveau CR
		               out.println("  <td class=\"g-d-librj\">"  + col82.elementAt(i) + "</td>"); // Libelle CR
					}
					if (col62.elementAt(i).equals("Rejeté")) {		            
			           out.println("  <td class=\"g-d-stdem-R\">"  + col62.elementAt(i) + "</td>"); // Statut CR Rejeté 
			           out.println("  <td class=\"g-d-nivrj-R\">"  + col72.elementAt(i) + "</td>"); // Niveau CR
			           out.println("  <td class=\"g-d-librj-R\">"  + col82.elementAt(i) + "</td>"); // Libelle CR
					}
		         out.println("</tr>");
		         
		         // Ligne 3 Siren....
		         out.println("<tr>");
		            out.println("  <td class=\"g-d-assu\" ></td>");
		            out.println("  <td class=\"g-d-ss\"   ></td>");
		            out.println("  <td class=\"g-d-nom\"  ></td>");
		            out.println("  <td class=\"g-d-demi\" >"  + col43.elementAt(i) + "</td>");
		            out.println("  <td class=\"g-d-dtdem\"></td>");
		            out.println("  <td class=\"g-d-stdem\"></td>");
		            out.println("  <td class=\"g-d-nivrj\"></td>");
		            out.println("  <td class=\"g-d-librj\"></td>");
		         out.println("</tr>");
		         
                // Ligne 4 NIR / N°SS Type demande salarie 
		         out.println("<tr>");
		            out.println("  <td class=\"g-d-assu\" ></td>");
		            out.println("  <td class=\"g-d-ss\"   ></td>");
		            out.println("  <td class=\"g-d-nom\"  ></td>");
		            out.println("  <td class=\"g-d-demi\" >" + col44.elementAt(i) + "</td>");
		            out.println("  <td class=\"g-d-dtdem\"></td>");
		            out.println("  <td class=\"g-d-stdem\"></td>");
		            out.println("  <td class=\"g-d-nivrj\"></td>");
		            out.println("  <td class=\"g-d-librj\"></td>");
		         out.println("</tr>");
		         
                // Ligne 5 ID Couverture... 
		         out.println("<tr>");
		            out.println("  <td class=\"g-d-assu-fin\" ></td>");
		            out.println("  <td class=\"g-d-ss-fin\"   ></td>");
		            out.println("  <td class=\"g-d-nom-fin\"  ></td>");
		            out.println("  <td class=\"g-d-demi-fin\" >" + col45.elementAt(i) + "</td>");
		            out.println("  <td class=\"g-d-dtdem-fin\"></td>");
		            out.println("  <td class=\"g-d-stdem-fin\"></td>");
		            out.println("  <td class=\"g-d-nivrj-fin\"></td>");
		            out.println("  <td class=\"g-d-librj-fin\"></td>");
		         out.println("</tr>");

		      }
		      
		      out.println("</table>");
			  out.println("</div>");
			
			  out.println("</body>");
			  out.println("</html>");

			  out.flush();
			  out.close();

		} catch (Exception e) {
			System.out.println("HTML page error : " + e.getMessage());
		}

		return EtatPageHTML;

	} // end of GestipHtmlPageResultat
	
	/**
	 * @see prestijHTMLSessionError(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void prestijHtmlSessionError(HttpServletResponse res) {

		// Initialisation Cache page Error
		res.setContentType("text/html");
		res.setHeader("Pragma", "No-Cache");
		res.setDateHeader("Expires", 0);
		res.setHeader("Cache-Control", "no-Cache");

		// Affichage page Error
		try {

			PrintWriter out;
			out = res.getWriter();

		    // <head>
	 	    out.println("<!DOCTYPE html>");                            
			out.println("<html>");
			out.println("<head>\r\n");
			   out.println("<title>PRESTIJ - Consultation des flux</title>\r\n");
			   out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n");
			   out.println("<link rel=stylesheet type=\"text/css\" href=\"css/transfert.css\" media=\"screen\"/>\r\n");
			   out.println("<meta charset=\"utf-8\">    \r\n");
			out.println("</head> \r\n");
			  
	          // <body>
		      out.println("<body class=\"erreur-session" + "\"" + ">");  
		      out.println("<div class=\"erreur-session" + "\"" + ">");
		          out.println("<h3 style=\"color:red;font-size: 20px; font-weight:bold;" + "\"" + ">--- Désolé, votre session a expiré ---" + "</h3>");
		          out.println("<a style=\"color:red;font-size: 20px; font-weight:bold;" + "\""); 
		          out.println(" href=\"" + Scheme + Adresse + Context + lienErreur + "\"" +  "\"");
		          out.println(">Merci de fermer votre navigateur ou de cliquer ici pour vous revenir à l'application</a>");
			  out.println("</div>\r\n");   
		      out.println("</body>");	
			  out.println("</html>");
			    
			  out.flush();
		} 
		catch (Exception e) {
			System.out.println("HTML page error : " + e.getMessage());
		}

	}

} // end of class
