/* **********************************************************************
 * Projet                 : PRESTIJ - AUTOMATISATION des FLUX IJ   
 * Package                : transfert
 * Class                  : PrestijBPIJ.java 
 *----------------------------------------------------------------------               
 * Objet                  : Rechercher et consultation des flux BPIJ   
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 18 05 2018 
 *----------------------------------------------------------------------
 * Chemin class jsp       : \apache-tomcat-7.0.69\work\Catalina\localhost\transfert\...
 ********************************************************************* */
package transfert;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import transfertoutils.BoiteOutils;
import transfertoutils.RequeteFluxGestip;

/**
 * Servlet implementation class PrestijBpij
 */

/**
 * Servlet implementation class PrestijBpij
 */
@WebServlet(description = "Consultation des flux BPIJ", urlPatterns = { "/PrestijBpij" })
public class PrestijBpij extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Variables paramètres servlet serveur/version CSS/version JS
	static String servlet, versionCSS, versionJS;
	static String Serveur, login, Mdp, Bib, Scheme;

	static String lienQuitter, lienErreur, retourMenu;

	// Initialisation variables HTTP
	static String Servlet, Programme, Adresse, Context;

	// Paramètres formulaire
	static String p_formulaire[] = new String[9];

	// Données renvoyées de la requête IBM i
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
	public PrestijBpij() {
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:").append(request.getContextPath());

		// Appel direct DoPost()
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub

		// Récup paramètres HTTP 
		int PortN = request.getServerPort();
		String Port = new String();
		Port = String.valueOf(PortN);
		Serveur = request.getServerName();
		Adresse = request.getServerName() + ":" + Port;
		Context = request.getContextPath() + "/";
		Scheme = request.getScheme() + "://";

		// Récup informations session utilisateur + paramètres
		HttpSession sessionHTTP = request.getSession(false);
		Boolean parametres = false;

		// Session expirée --> Page Erreur
		if (sessionHTTP == null) {

			// A externaliser !!!
			// prestijHtmlSessionError(response);     // -----> Mis en com. pour le développement  

			//
			// A retirer pour mise en recette 
			// Creation d'une session http temporaire pour le développement 
			//
			sessionHTTP = request.getSession(true);  // if session is not new then we are a new one if switching the session and start  
			if (!(sessionHTTP.isNew())) {
				 sessionHTTP.invalidate();
			     sessionHTTP = request.getSession(true); 
			}
			
			sessionHTTP.setAttribute("Serveur", "cgamrec");
			sessionHTTP.setAttribute("Utilisateur", "TLEGUILLOU");
			sessionHTTP.setAttribute("MotdePasse", "mai2016");
			sessionHTTP.setAttribute("BibFichier", "PRESTIJ");
			 
			// Récup des informations session temporaire  
			request.getSession(false);

		}

		// Session OK --> Page HTML
		if (sessionHTTP != null) {

			// Init. informations générales
			Serveur = (String) sessionHTTP.getAttribute("Serveur");
			login   = (String) sessionHTTP.getAttribute("Utilisateur");
			Mdp     = (String) sessionHTTP.getAttribute("MotdePasse");
			Bib     = (String) sessionHTTP.getAttribute("BibFichier");

			// Init.paramètres du formulaire
			if (request.getParameter("idflux") != null) {p_formulaire[0] = request.getParameter("idflux");}
			else {p_formulaire[0] = "";}
			
			if (request.getParameter("idfichier") != null) {p_formulaire[1] = request.getParameter("idfichier");}
			else {p_formulaire[1] = "";}
			
			if (request.getParameter("cass") != null) {p_formulaire[2] = request.getParameter("cass");}
			else {p_formulaire[2] = "";}
			
			if (request.getParameter("nss") != null) {p_formulaire[3] = request.getParameter("nss");}
			else {p_formulaire[3] = "";}
			
			if (request.getParameter("nom") != null) {p_formulaire[4] = request.getParameter("nom");}
			else {p_formulaire[4] = "";}
			
			if (request.getParameter("prenom") != null) {p_formulaire[5] = request.getParameter("prenom");}
			else {p_formulaire[5] = "";}
			
			if (request.getParameter("nat") != null) {p_formulaire[6] = request.getParameter("nat");}
			else {p_formulaire[6] = "";}
			
			if (request.getParameter("Bdebut") != null) {p_formulaire[7] = request.getParameter("Bdebut");}
			else {p_formulaire[7] = "";}
			
			if (request.getParameter("Bfin") != null) {p_formulaire[8] = request.getParameter("Bfin");}
			else {p_formulaire[8] = "";}
			
			// TODO : Prévoir controle des paramètres reçus avant envoi de la requête
			// ToolBox ctrl = new ToolBox(p_code);

			// Boucle d'analyse du formulaire saisi + extraction données
			for (int i = 0; i < p_formulaire.length; i++) {

				if (!p_formulaire[i].equals("")) {

					parametres = true;
					
					Message = "ITest mise en place des pages";
					
					/* 
					RequeteFluxGestip requete = new RequeteFluxGestip(Serveur, login, Mdp, Bib, p_formulaire);

					requete.LectureGestip();
					OKKO = requete.getOKKO();
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
					
					*/
					
				}
			}

			// Si paramètres --> affichage page HTML résultat
			if (parametres) {

				if (Message.substring(0, 1).equals("I")) { // Info résultat

					// Affichage page résultat si OK (Erreur "I" pourinformation)
					BpijHtmlPageResultat(response, Message.substring(1, Message.length()), Message.substring(0, 1));

				} 
				else {                                  // Erreur Suite requête BPIJ

					// Affichage formulaire et message d'erreur
					BpijHtmlFormulaire(response, Message.substring(1, Message.length()));

				}

			}

			// Si pas de paramètres --> Affichage formulaire vide à remplir
			else {

				BpijHtmlFormulaire(response, "");

			}

		} // Fin de test session diff. de null

	} // Fin de doPost()

	/**
	 * @see GestipHtmlFormulaire(HttpServletRequest res, String Erreur)
	*/
	protected boolean BpijHtmlFormulaire(HttpServletResponse res, String LibelleMessage) {

		// Initialisation Cache page + boolean 
		res.setContentType("text/html");
		res.setHeader("Pragma", "No-Cache");
		res.setDateHeader("Expires", 0);
		res.setHeader("Cache-Control", "no-Cache");

		boolean EtatPageHTML = LibelleMessage.isEmpty();

		// -------------------------------------
		// Envoi du flux HTML
		// -------------------------------------
		try {

			PrintWriter out;
			out = res.getWriter();
			
			// Partie Entete
			out.write("<!DOCTYPE html>\r\n");
			out.write("<html>\r\n");
			out.write("<head>\r\n");
			out.write("    <title>PRESTIJ - Consultation des flux</title>\r\n");
			out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\" />\r\n");
			out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\r\n");
			out.write("    <link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.12.1/themes/ui-lightness/jquery-ui.css\">\r\n");
			out.write("    <script src=\"//code.jquery.com/jquery-1.12.4.js\"></script>\r\n");
			out.write("    <script src=\"//code.jquery.com/ui/1.12.1/jquery-ui.js\"></script>\r\n");
			out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/transfert-bpij.css\" media=\"screen\" />\r\n");
			out.write("    <script type=\"text/javascript\" src=\"js/transfert.js\"></script>\r\n");
			out.write("</head>\r\n");
			
			out.write("<body class=\"bpij\" onload=\"javascript:document.formulaireBPIJ.idflux.focus();\">\r\n");
			out.write("<img src=\"img/logo_home.jpg\" width=\"120\" height=\"60\" align=\"left\">\r\n");
			
			out.write("<script>\r\n");
			out.write("   document.writeln('<h3 align=\"center\">Recherche des flux BPIJ au ' + DateJour + '</h3><br>');\r\n");
			out.write("</script>\r\n");
			
            // Partie 1 Formulaire 			
			out.write("<div class=\"bpij-div1\">\r\n");
			
			out.write("<form name=\"formulaireBPIJ\" id=\"myform\" onsubmit=\"return ValidationGlobale_BPIJ(this.form)\">\r\n");
			
		    out.write("    <input class=\"div1-s2\" type=\"button\" value=\"Retour menu\" "
	                        + "OnClick=\"window.location.href=" + "'" + Scheme + Adresse + Context + retourMenu + "'" + "\"" + "/>\r\n");
		    
            out.write("    <a class=\"deconnexion\" href=\"" + Scheme + Adresse + Context + lienQuitter + "\"" + ">D\351connexion</a>\r\n");

			out.write("    <br><br>\r\n");
			out.write("   <fieldset>\r\n");
			out.write("   <legend>Référence</legend>\r\n");
			
            // IDFlux 			
			out.write("                <label for=\"idflux\" class=\"div1-la1\">IDflux</label>\r\n");
			out.write("                <input id=\"bt1\" class=\"div1-i\" type=\"text\" name=\"idflux\" size=\"25\" maxlength=\"25\" onblur=\"idflux_B('blur')\" onfocus=\"idflux_B('focus')\" onkeypress=\"VerificationCar(event);\" />\r\n");

			// IDfichier
			out.write("                <label for=\"idfichier\">IDfichier</label>\r\n");
			out.write("                <input id=\"bt2\" class=\"div1-i\" type=\"text\" name=\"idfichier\" size=\"25\" maxlength=\"25\" onfocus=\"idfichier_B('focus')\" onblur=\"idfichier_B('blur')\" onkeypress=\"VerificationCar(event);\" />\r\n");
  
			// Assuré 
			out.write("                <br><label for=\"assure\" class=\"div1-la1\">N° assuré</label>\r\n");
			out.write("                <input id=\"bt3\" class=\"div1-i\" type=\"text\" name=\"cass\" size=\"7\" maxlength=\"7\" onblur=\"codeAssure_B('blur')\" onfocus=\"codeAssure_B('focus')\" onkeypress=\"VerificationCar(event);\" />\r\n");
			
            // N° SS 
			out.write("                <label for=\"nss\">Numéro SS</label>\r\n");
			out.write("                <input id=\"bt4\" class=\"div1-i\" type=\"text\" name=\"nss\" size=\"13\" maxlength=\"13\" onfocus=\"numeroSS_B('focus')\" onblur=\"numeroSS_B('blur')\" onkeypress=\"VerificationCar(event);\" />\r\n");

			// Nom 
			out.write("                <br><label for=\"nom\" class=\"div1-la1\">Nom</label>\r\n");
			out.write("                <input id=\"bt5\" class=\"div1-i\" type=\"text\" name=\"nom\" size=\"50\" maxlength=\"50\" onblur=\"nomAssure_B('blur')\" onfocus=\"nomAssure_B('focus')\" onkeypress=\"VerificationCar(event);\" />\r\n");

			// Prénom
			out.write("                <label for=\"prenom\">Prénom</label>\r\n");
			out.write("                <input id=\"bt6\" class=\"div1-i\" type=\"text\" name=\"prenom\" size=\"50\" maxlength=\"50\" onblur=\"prenomAssure_B('blur')\" onfocus=\"prenomAssure_B('focus')\" onkeypress=\"VerificationCar(event);\" />\r\n");
			out.write("  </fieldset>\r\n");
			
			out.write("  <fieldset>\r\n");
			
            // Nature assurance
			out.write("                <label for=\"nature\" class=\"div1-la1\">Nature Assurance</label>\r\n");
			out.write("                <select name=\"nat\" id=\"ListNature\" class=\"div1-i\" onblur=\"nature_B('blur')\" onfocus=\"nature_B('focus')\" onchange=\"nature_B('blur');\">\r\n");
			out.write("                    <option selected=\"selected\">TOUS</option>\r\n");
			out.write("                    <option>ADO - I.J. ADOPTION</option>   \r\n");
			out.write("                    <option>ARA - Allocation maternité réduite pour adoption</option>\r\n");
			out.write("                    <option>ARM Allocation forfaitaire repos maternel</option>\r\n");
			out.write("                    <option>ASM I.J MALADIE MAJOREE + 6 MOIS</option>\r\n");
			out.write("                    <option>ASN I.J MALADIE NORMALE + 6 MOIS</option>\r\n");
			out.write("                    <option>AVP Alloc. accomp. fin de Vie cessa. act. temps Pl.</option>\r\n");
			out.write("                    <option>AVR Alloc. Accomp. fin de Vie cessation act. Réd.</option>\r\n");
			out.write("                    <option>CAR CARENCE</option>\r\n");
			out.write("                    <option>CIJ COMPLEMENT IJ > PLAFOND -CRPCEN-</option>\r\n");
			out.write("                    <option>CIN CONSTAT D'INDU</option>\r\n");
			out.write("                    <option>CUM\tI.J.MAJOREES CURE</option>\r\n");
			out.write("                    <option>CUN\tI.J. NORMALES CURE</option>\r\n");
			out.write("                    <option>CUR\tI.J.REDUITES CURE (RENTE)</option>\r\n");
			out.write("                    <option>EEN\tALLOCATION EXPOSITION ARRET + 6 MOIS ET 3 ENFANTS</option>\r\n");
			out.write("                    <option>EME\tALLOCATION EXPOSITION MAJOREE 3 ENFANTS</option>\r\n");
			out.write("                    <option>EMN\tALLOCATION EXPOSITION ARRET + 6 MOIS </option>\r\n");
			out.write("                    <option>ENO\tALLOCATION EXPOSITION NORMALE</option>\r\n");
			out.write("                    <option>IPA\tINDEMNITE PATERNITE PAMC</option>\r\n");
			out.write("                    <option>IPC\tINDEMNITE PATERNITE CONJOINT PAMC</option>\r\n");
			out.write("                    <option>IPD\tFORF.24H>DUREE>12</option>\r\n");
			out.write("                    <option>IPI\tINDEMNITE PATERNITE CONJOINT INFIRMIER</option>\r\n");
			out.write("                    <option>IPS\tPERTE DE SALAIRE</option>\r\n");
			out.write("                    <option>IRA\tINDEM.REMPL.MATER REDUITE(ADOPTION)</option>\r\n");
			out.write("                    <option>IRC\tINDEM. DE REMPL.CJTES.COLLABORATRICES</option>\r\n");
			out.write("                    <option>IRG\tMAJOR.INDEM.REMPL.MATER(A.GEMELL.)</option>\r\n");
			out.write("                    <option>IRM INDEMN.REMPLACEMENT MATER NORMALE </option>\r\n");
			out.write("                    <option>IRP MAJOR.INDEM.REMPL.MATER(ET PATHOL)</option>\r\n");
			out.write("                    <option>ISM\tIJ SUPPLEMENTAIRE MATERNITE</option>\r\n");
			out.write("                    <option>ITI INDEMNITE TEMPORAIRE D'INAPTITUDE</option>\r\n");
			out.write("                    <option>IJMAJ I.J. MAJOREES</option>\r\n");
			out.write("                    <option>MIJ\tI.J. MINIMUM  MAJOREE</option>\r\n");
			out.write("                    <option>MIN\tI.J. MINIMUM  NORMALE </option>\r\n");
			out.write("                    <option>MIT\tI.J. MI-TEMPS</option>\r\n");
			out.write("                    <option>NEN\tALLOCATION NUIT MAJOREE ARRET + 6 MOIS ET 3 ENFANTS</option>\r\n");
			out.write("                    <option>NME ALLOCATION NUIT MAJOREE 3 ENFANTS</option>\r\n");
			out.write("                    <option>NMN ALLOCATION NUIT MAJOREE ARRET + 6 MOIS</option>\r\n");
			out.write("                    <option>NNO ALLOCATION NUIT NORMALE</option>\r\n");
			out.write("                    <option>IJNOR I.J. NORMALES</option>\r\n");
			out.write("                    <option>PER\tI.J. PATERNITE</option>\r\n");
			out.write("                    <option>POS I.J. POSNATALES</option>\r\n");
			out.write("                    <option>PRE I.J. PRENATALES</option>\r\n");
			out.write("                    <option>REN I.J. REDUITES POUR RENTE</option>\r\n");
			out.write("                    <option>RPR RECUPERATION INDU</option>\r\n");
			out.write("                    <option>RETCRD RETENUE R.D.S.</option>\r\n");
			out.write("                    <option>RETCSJ RETENUE C.S.G.</option>\r\n");
			out.write("                    <option>RETRCJ RETENUE REGUL.CONTRIB. SOCIALE GENERALISEE </option>    \r\n");
			out.write("                    <option>RETRRD RETENUE REGUL. REMBOURSEMENT DETTE SOCIALE</option>\r\n");
			out.write("                    <option>REGRCJ REGULARISATION REGUL.CONTRIB. SOCIALE GENERALISEE</option>\r\n");
			out.write("                    <option>REGRRD REGULARISATION REGUL.REMBOURSEMENT DETTE SOCIALE</option>\r\n");
			out.write("              </select>\r\n");
			
			// Période 
			out.write("                <label for=\"periode\">Période du</label>\r\n");
			out.write("                <input id=\"Bdatedebut\" class=\"div1-i\" type=\"text\" name=\"Bdebut\" onblur=\"dateDebut_B('blur')\" onfocus=\"dateDebut_B('focus')\" onkeypress=\"VerificationCar(event);\" />\r\n");
			
			out.write("                <label for=\"periode2\">au</label>\r\n");
			out.write("                <input id=\"Bdatefin\" class=\"div1-i\" type=\"text\" name=\"Bfin\" onblur=\"dateFin_B('blur')\" onfocus=\"dateFin_B('focus')\" onkeypress=\"VerificationCar(event);\" />\r\n");
			out.write("                <!-- Boutons validation -->\r\n");
			out.write("                <input class=\"div1-s1\" type=\"submit\" name=\"entrer\" value=\"Valider\" />\r\n");


			out.write("                <br><span class=\"info\">Critères de sélection incorrectes !</span>\r\n");
			out.write("  </fieldset>\r\n");
			
			
			out.write("</form>\r\n");
			
			out.write("</div>\r\n");
		    out.write("</body>\r\n");
		    out.write("</html>");
		      
			out.flush();
			out.close();
			

		} catch (Exception e) {System.out.println("HTML page error : " + e.getMessage());}
		
		return EtatPageHTML;

	} // end of BpijHtmlFormulaire()

	/**
	 * @see BPIJHtmlPageResultat(HttpServletRequest res, String Erreur)
	 */
	protected boolean BpijHtmlPageResultat(HttpServletResponse res, String Erreur, String TypeErreur) {

		// Initialisation Cache page + boolean
		res.setContentType("text/html");
		res.setHeader("Pragma", "No-Cache");
		res.setDateHeader("Expires", 0);
		res.setHeader("Cache-Control", "no-Cache");

		boolean EtatPageHTML = Erreur.isEmpty();

		// -------------------------------------
		// Envoi du flux HTML
		// -------------------------------------
		try {

			PrintWriter out;
			out = res.getWriter();
			
			// Partie Entete
			out.write("<!DOCTYPE html>\r\n");
			out.write("<html>\r\n");
			out.write("<head>\r\n");
			out.write("    <title>PRESTIJ - Consultation des flux</title>\r\n");
			out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\" />\r\n");
			out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\r\n");
			out.write("    <link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.12.1/themes/ui-lightness/jquery-ui.css\">\r\n");
			out.write("    <script src=\"//code.jquery.com/jquery-1.12.4.js\"></script>\r\n");
			out.write("    <script src=\"//code.jquery.com/ui/1.12.1/jquery-ui.js\"></script>\r\n");
			out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/transfert-bpij.css\" media=\"screen\" />\r\n");
			out.write("    <script type=\"text/javascript\" src=\"js/transfert.js\"></script>\r\n");
			out.write("</head>\r\n");
			
			out.write("<body class=\"bpij\" onload=\"javascript:document.formulaireBPIJ.idflux.focus();\">\r\n");
			out.write("<img src=\"img/logo_home.jpg\" width=\"120\" height=\"60\" align=\"left\">\r\n");
			
			out.write("<script>\r\n");
			out.write("   document.writeln('<h3 align=\"center\">Recherche des flux BPIJ au ' + DateJour + '</h3><br>');\r\n");
			out.write("</script>\r\n");
			
            // Partie 1 Formulaire 			
			out.write("<div class=\"bpij-div1\">\r\n");
			
			out.write("<form name=\"formulaireBPIJ\" id=\"myform\" onsubmit=\"return ValidationGlobale_BPIJ(this.form)\">\r\n");
			
		    out.write("    <input class=\"div1-s2\" type=\"button\" value=\"Retour menu\" "
	                        + "OnClick=\"window.location.href=" + "'" + Scheme + Adresse + Context + retourMenu + "'" + "\"" + "/>\r\n");
		    
            out.write("    <a class=\"deconnexion\" href=\"" + Scheme + Adresse + Context + lienQuitter + "\"" + ">D\351connexion</a>\r\n");

			out.write("    <br><br>\r\n");
			out.write("   <fieldset>\r\n");
			out.write("   <legend>Référence</legend>\r\n");
			
            // IDFlux 			
			out.write("                <label for=\"idflux\" class=\"div1-la1\">IDflux</label>\r\n");
			out.write("                <input id=\"bt1\" class=\"div1-i\" type=\"text\" name=\"idflux\" size=\"25\" maxlength=\"25\" onblur=\"idflux_B('blur')\" onfocus=\"idflux_B('focus')\" onkeypress=\"VerificationCar(event);\" />\r\n");

			// IDfichier
			out.write("                <label for=\"idfichier\">IDfichier</label>\r\n");
			out.write("                <input id=\"bt2\" class=\"div1-i\" type=\"text\" name=\"idfichier\" size=\"25\" maxlength=\"25\" onfocus=\"idfichier_B('focus')\" onblur=\"idfichier_B('blur')\" onkeypress=\"VerificationCar(event);\" />\r\n");
  
			// Assuré 
			out.write("                <br><label for=\"assure\" class=\"div1-la1\">N° assuré</label>\r\n");
			out.write("                <input id=\"bt3\" class=\"div1-i\" type=\"text\" name=\"cass\" size=\"7\" maxlength=\"7\" onblur=\"codeAssure_B('blur')\" onfocus=\"codeAssure_B('focus')\" onkeypress=\"VerificationCar(event);\" />\r\n");
			
            // N° SS 
			out.write("                <label for=\"nss\">Numéro SS</label>\r\n");
			out.write("                <input id=\"bt4\" class=\"div1-i\" type=\"text\" name=\"nss\" size=\"13\" maxlength=\"13\" onfocus=\"numeroSS_B('focus')\" onblur=\"numeroSS_B('blur')\" onkeypress=\"VerificationCar(event);\" />\r\n");

			// Nom 
			out.write("                <br><label for=\"nom\" class=\"div1-la1\">Nom</label>\r\n");
			out.write("                <input id=\"bt5\" class=\"div1-i\" type=\"text\" name=\"nom\" size=\"50\" maxlength=\"50\" onblur=\"nomAssure_B('blur')\" onfocus=\"nomAssure_B('focus')\" onkeypress=\"VerificationCar(event);\" />\r\n");

			// Prénom
			out.write("                <label for=\"prenom\">Prénom</label>\r\n");
			out.write("                <input id=\"bt6\" class=\"div1-i\" type=\"text\" name=\"prenom\" size=\"50\" maxlength=\"50\" onblur=\"prenomAssure_B('blur')\" onfocus=\"prenomAssure_B('focus')\" onkeypress=\"VerificationCar(event);\" />\r\n");
			out.write("  </fieldset>\r\n");
			
			out.write("  <fieldset>\r\n");
			
            // Nature assurance
			out.write("                <label for=\"nature\" class=\"div1-la1\">Nature Assurance</label>\r\n");
			out.write("                <select name=\"nat\" id=\"ListNature\" class=\"div1-i\" onblur=\"nature_B('blur')\" onfocus=\"nature_B('focus')\" onchange=\"nature_B('blur');\">\r\n");
			out.write("                    <option selected=\"selected\">TOUS</option>\r\n");
			out.write("                    <option>ADO - I.J. ADOPTION</option>   \r\n");
			out.write("                    <option>ARA - Allocation maternité réduite pour adoption</option>\r\n");
			out.write("                    <option>ARM Allocation forfaitaire repos maternel</option>\r\n");
			out.write("                    <option>ASM I.J MALADIE MAJOREE + 6 MOIS</option>\r\n");
			out.write("                    <option>ASN I.J MALADIE NORMALE + 6 MOIS</option>\r\n");
			out.write("                    <option>AVP Alloc. accomp. fin de Vie cessa. act. temps Pl.</option>\r\n");
			out.write("                    <option>AVR Alloc. Accomp. fin de Vie cessation act. Réd.</option>\r\n");
			out.write("                    <option>CAR CARENCE</option>\r\n");
			out.write("                    <option>CIJ COMPLEMENT IJ > PLAFOND -CRPCEN-</option>\r\n");
			out.write("                    <option>CIN CONSTAT D'INDU</option>\r\n");
			out.write("                    <option>CUM\tI.J.MAJOREES CURE</option>\r\n");
			out.write("                    <option>CUN\tI.J. NORMALES CURE</option>\r\n");
			out.write("                    <option>CUR\tI.J.REDUITES CURE (RENTE)</option>\r\n");
			out.write("                    <option>EEN\tALLOCATION EXPOSITION ARRET + 6 MOIS ET 3 ENFANTS</option>\r\n");
			out.write("                    <option>EME\tALLOCATION EXPOSITION MAJOREE 3 ENFANTS</option>\r\n");
			out.write("                    <option>EMN\tALLOCATION EXPOSITION ARRET + 6 MOIS </option>\r\n");
			out.write("                    <option>ENO\tALLOCATION EXPOSITION NORMALE</option>\r\n");
			out.write("                    <option>IPA\tINDEMNITE PATERNITE PAMC</option>\r\n");
			out.write("                    <option>IPC\tINDEMNITE PATERNITE CONJOINT PAMC</option>\r\n");
			out.write("                    <option>IPD\tFORF.24H>DUREE>12</option>\r\n");
			out.write("                    <option>IPI\tINDEMNITE PATERNITE CONJOINT INFIRMIER</option>\r\n");
			out.write("                    <option>IPS\tPERTE DE SALAIRE</option>\r\n");
			out.write("                    <option>IRA\tINDEM.REMPL.MATER REDUITE(ADOPTION)</option>\r\n");
			out.write("                    <option>IRC\tINDEM. DE REMPL.CJTES.COLLABORATRICES</option>\r\n");
			out.write("                    <option>IRG\tMAJOR.INDEM.REMPL.MATER(A.GEMELL.)</option>\r\n");
			out.write("                    <option>IRM INDEMN.REMPLACEMENT MATER NORMALE </option>\r\n");
			out.write("                    <option>IRP MAJOR.INDEM.REMPL.MATER(ET PATHOL)</option>\r\n");
			out.write("                    <option>ISM\tIJ SUPPLEMENTAIRE MATERNITE</option>\r\n");
			out.write("                    <option>ITI INDEMNITE TEMPORAIRE D'INAPTITUDE</option>\r\n");
			out.write("                    <option>IJMAJ I.J. MAJOREES</option>\r\n");
			out.write("                    <option>MIJ\tI.J. MINIMUM  MAJOREE</option>\r\n");
			out.write("                    <option>MIN\tI.J. MINIMUM  NORMALE </option>\r\n");
			out.write("                    <option>MIT\tI.J. MI-TEMPS</option>\r\n");
			out.write("                    <option>NEN\tALLOCATION NUIT MAJOREE ARRET + 6 MOIS ET 3 ENFANTS</option>\r\n");
			out.write("                    <option>NME ALLOCATION NUIT MAJOREE 3 ENFANTS</option>\r\n");
			out.write("                    <option>NMN ALLOCATION NUIT MAJOREE ARRET + 6 MOIS</option>\r\n");
			out.write("                    <option>NNO ALLOCATION NUIT NORMALE</option>\r\n");
			out.write("                    <option>IJNOR I.J. NORMALES</option>\r\n");
			out.write("                    <option>PER\tI.J. PATERNITE</option>\r\n");
			out.write("                    <option>POS I.J. POSNATALES</option>\r\n");
			out.write("                    <option>PRE I.J. PRENATALES</option>\r\n");
			out.write("                    <option>REN I.J. REDUITES POUR RENTE</option>\r\n");
			out.write("                    <option>RPR RECUPERATION INDU</option>\r\n");
			out.write("                    <option>RETCRD RETENUE R.D.S.</option>\r\n");
			out.write("                    <option>RETCSJ RETENUE C.S.G.</option>\r\n");
			out.write("                    <option>RETRCJ RETENUE REGUL.CONTRIB. SOCIALE GENERALISEE </option>    \r\n");
			out.write("                    <option>RETRRD RETENUE REGUL. REMBOURSEMENT DETTE SOCIALE</option>\r\n");
			out.write("                    <option>REGRCJ REGULARISATION REGUL.CONTRIB. SOCIALE GENERALISEE</option>\r\n");
			out.write("                    <option>REGRRD REGULARISATION REGUL.REMBOURSEMENT DETTE SOCIALE</option>\r\n");
			out.write("              </select>\r\n");
			
			// Période 
			out.write("                <label for=\"periode\">Période du</label>\r\n");
			out.write("                <input id=\"Bdatedebut\" class=\"div1-i\" type=\"text\" name=\"Bdebut\" onblur=\"dateDebut_B('blur')\" onfocus=\"dateDebut_B('focus')\" onkeypress=\"VerificationCar(event);\" />\r\n");
			
			out.write("                <label for=\"periode2\">au</label>\r\n");
			out.write("                <input id=\"Bdatefin\" class=\"div1-i\" type=\"text\" name=\"Bfin\" onblur=\"dateFin_B('blur')\" onfocus=\"dateFin_B('focus')\" onkeypress=\"VerificationCar(event);\" />\r\n");
			out.write("                <!-- Boutons validation -->\r\n");
			out.write("                <input class=\"div1-s1\" type=\"submit\" name=\"entrer\" value=\"Valider\" />\r\n");

			out.write("                <br><span class=\"info\">Critères de sélection incorrectes !</span>\r\n");
			
			out.write("  </fieldset>\r\n");
			
			out.write("</form>\r\n");
			
			out.write("</div>\r\n");			

		     out.write("    <!--       Partie 2 - niveau 1 DÃ©tail IDFLUX     -->\r\n");
		      out.write("    \r\n");
		      out.write("    <!-- En-tete niveau 1-->\r\n");
		      out.write("    <div align=\"center\" class=\"bpij-div2\">\r\n");
		      out.write("        <table class=\"bpij-div2-table1\">\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-h-idflux\">IDflux</td>\r\n");
		      out.write("                <td class=\"b-h-idfichier\">IDfichier</td>\r\n");
		      out.write("                <td class=\"b-h-dtrans\">Date transfert</td>\r\n");
		      out.write("                <td class=\"b-h-ip\">IP libellé</td>\r\n");
		      out.write("                <td class=\"b-h-siren\">Siren déclarant</td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("        </table>\r\n");
		      out.write("    </div>\r\n");
		      
		      out.write("    <!-- ligne IDFLUX niveau 1 -->\r\n");
		      out.write("    <div align=\"center\" class=\"bpij-div3\" style=\"height:40px;\">\r\n");
		      out.write("        <table class=\"bpij-div3-table2\" style=\"height:40px;\">\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-idflux\">1</td>\r\n");
		      out.write("                <td class=\"b-d-idfichier\">1234567890123456789012345</td>\r\n");
		      out.write("                <td class=\"b-d-dtrans\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-ip\">123456789012345</td>\r\n");
		      out.write("                <td class=\"b-d-siren\">12345678901234</td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("        </table>\r\n");
		      out.write("    </div>\r\n");
		      out.write("    \r\n");
		      out.write("    <!--       Partie 2 - niveau 2 DÃ©tail IDfichier     -->\r\n");
		      out.write("    <!-- En-tete niveau 2-->\r\n");
		      out.write("    <div align=\"center\" class=\"bpij-div2\">\r\n");
		      out.write("        <table class=\"bpij-div2-table1\">\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-h-ss\">Numéro SS</td>\r\n");
		      out.write("                <td class=\"b-h-nom\">Nom / Prénom</td>\r\n");
		      out.write("                <td class=\"b-h-nature\">Nat. Ass.</td>\r\n");
		      out.write("                <td class=\"b-h-periode\">Période</td>\r\n");
		      out.write("                <td class=\"b-h-statut\">Statu intég.</td>\r\n");
		      out.write("                <td class=\"b-h-detail\">Détail</td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("        </table>\r\n");
		      out.write("    </div>\r\n");
		      out.write("\r\n");
		      out.write("    <!-- Détail niveau 2 -->\r\n");
		      out.write("    <div align=\"center\" class=\"bpij-div3\" style=\"height:100px;\">\r\n");
		      out.write("        <table class=\"bpij-div3-table3\" style=\"height:100px;\">\r\n");
		      out.write("          \r\n");
		      out.write("           <!-- Ligne 1 Nom -->\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-ss\">1610175111044</td>\r\n");
		      out.write("                <td class=\"b-d-nom\">DEVALEGAMA-GAMACHARIGEYSE</td>\r\n");
		      out.write("                <td class=\"b-d-nature\">RETRCJ</td>\r\n");
		      out.write("                <td class=\"b-d-periode\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-statut\">EN GEDXXXX</td>\r\n");
		      out.write("                <td class=\"b-d-detail\"><a class=\"lien-bouton-visu\" href=\"#\" onclick=\"return Visualisation();\">Visu</a></td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("            <!-- Ligne 1 prÃ©nom -->\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-ss\"></td>\r\n");
		      out.write("                <td class=\"b-d-nom\">RAVI CHINTHAKA</td>\r\n");
		      out.write("                <td class=\"b-d-nature\"></td>\r\n");
		      out.write("                <td class=\"b-d-periode\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-statut\"></td>\r\n");
		      out.write("                <td class=\"b-d-detail\"></td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("            \r\n");
		      out.write("            <!-- Ligne 2 Nom -->\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-ss\">1610175111044</td>\r\n");
		      out.write("                <td class=\"b-d-nom\">DEVALEGAMA-GAMACHARIGEYSE</td>\r\n");
		      out.write("                <td class=\"b-d-nature\">IJNOR</td>\r\n");
		      out.write("                <td class=\"b-d-periode\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-statut\">EN GEDXXXX</td>\r\n");
		      out.write("                <td class=\"b-d-detail\"><a class=\"lien-bouton-visu\" href=\"#\" onclick=\"return Visualisation();\">Visu</a></td>\r\n");
		      out.write("            <!-- Ligne 2 prÃ©nom -->\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-ss\"></td>\r\n");
		      out.write("                <td class=\"b-d-nom\">RAVI CHINTHAKA</td>\r\n");
		      out.write("                <td class=\"b-d-nature\"></td>\r\n");
		      out.write("                <td class=\"b-d-periode\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-statut\"></td>\r\n");
		      out.write("                <td class=\"b-d-detail\"></td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("            \r\n");
		      out.write("           <!-- Ligne 3 Nom -->\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-ss\">1610175111044</td>\r\n");
		      out.write("                <td class=\"b-d-nom\">DEVALEGAMA-GAMACHARIGEYSE</td>\r\n");
		      out.write("                <td class=\"b-d-nature\">IJNOR</td>\r\n");
		      out.write("                <td class=\"b-d-periode\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-statut\">EN GEDXXXX</td>\r\n");
		      out.write("                 <td class=\"b-d-detail\"><a class=\"lien-bouton-visu\" href=\"#\" onclick=\"return Visualisation();\">Visu</a></td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("            <!-- Ligne 3 prÃ©nom -->\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-ss\"></td>\r\n");
		      out.write("                <td class=\"b-d-nom\">RAVI CHINTHAKA</td>\r\n");
		      out.write("                <td class=\"b-d-nature\"></td>\r\n");
		      out.write("                <td class=\"b-d-periode\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-statut\"></td>\r\n");
		      out.write("                <td class=\"b-d-detail\"></td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("                                   \r\n");
		      out.write("           <!-- Ligne 4 Nom -->\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-ss\">1610175111044</td>\r\n");
		      out.write("                <td class=\"b-d-nom\">DEVALEGAMA-GAMACHARIGEYSE</td>\r\n");
		      out.write("                <td class=\"b-d-nature\">IJNOR</td>\r\n");
		      out.write("                <td class=\"b-d-periode\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-statut\">EN GEDXXXX</td>\r\n");
		      out.write("                 <td class=\"b-d-detail\"><a class=\"lien-bouton-visu\" href=\"#\" onclick=\"return Visualisation();\">Visu</a></td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("            <!-- Ligne 4 prÃ©nom -->\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-ss\"></td>\r\n");
		      out.write("                <td class=\"b-d-nom\">RAVI CHINTHAKA</td>\r\n");
		      out.write("                <td class=\"b-d-nature\"></td>\r\n");
		      out.write("                <td class=\"b-d-periode\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-statut\"></td>\r\n");
		      out.write("                <td class=\"b-d-detail\"></td>\r\n");
		      out.write("            </tr>    \r\n");
		      out.write("                    \r\n");
		      out.write("           <!-- Ligne 5 Nom -->\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-ss\">1610175111044</td>\r\n");
		      out.write("                <td class=\"b-d-nom\">DEVALEGAMA-GAMACHARIGEYSE</td>\r\n");
		      out.write("                <td class=\"b-d-nature\">IJNOR</td>\r\n");
		      out.write("                <td class=\"b-d-periode\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-statut\">EN GEDXXXX</td>\r\n");
		      out.write("                <td class=\"b-d-detail\"><a class=\"lien-bouton-visu\" href=\"#\" onclick=\"return Visualisation();\">Visu</a></td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("            <!-- Ligne 5 prÃ©nom -->\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-ss\"></td>\r\n");
		      out.write("                <td class=\"b-d-nom\">RAVI CHINTHAKA</td>\r\n");
		      out.write("                <td class=\"b-d-nature\"></td>\r\n");
		      out.write("                <td class=\"b-d-periode\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-statut\"></td>\r\n");
		      out.write("                <td class=\"b-d-detail\"></td>\r\n");
		      out.write("            </tr>             \r\n");
		      
		      out.write("        </table>\r\n");
		      out.write("    </div>    \r\n");
		      
		      out.write("        <!-- En-tete niveau 1-->\r\n");
		      out.write("    <div align=\"center\" class=\"bpij-div2\">\r\n");
		      out.write("        <table class=\"bpij-div2-table1\">\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-h-idflux\">IDflux</td>\r\n");
		      out.write("                <td class=\"b-h-idfichier\">IDfichier</td>\r\n");
		      out.write("                <td class=\"b-h-dtrans\">Date transfert</td>\r\n");
		      out.write("                <td class=\"b-h-ip\">IP libellé</td>\r\n");
		      out.write("                <td class=\"b-h-siren\">Siren déclarant</td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("        </table>\r\n");
		      out.write("    </div>\r\n");
		      
		      out.write("    <!-- ligne IDFLUX niveau 1 -->\r\n");
		      out.write("    <div align=\"center\" class=\"bpij-div3\" style=\"height:40px;\">\r\n");
		      out.write("        <table class=\"bpij-div3-table2\" style=\"height:40px;\">\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-idflux\">2</td>\r\n");
		      out.write("                <td class=\"b-d-idfichier\">1234567890123456789012345</td>\r\n");
		      out.write("                <td class=\"b-d-dtrans\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-ip\">123456789012345</td>\r\n");
		      out.write("                <td class=\"b-d-siren\">12345678901234</td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("        </table>\r\n");
		      out.write("    </div>\r\n");
		      
		      out.write("    <!--       Partie 2 - niveau 2 Détail IDfichier     -->\r\n");
		      out.write("    <!-- En-tete niveau 2-->\r\n");
		      out.write("    <div align=\"center\" class=\"bpij-div2\">\r\n");
		      out.write("        <table class=\"bpij-div2-table1\">\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-h-ss\">Numéro SS</td>\r\n");
		      out.write("                <td class=\"b-h-nom\">Nom / Prénom</td>\r\n");
		      out.write("                <td class=\"b-h-nature\">Nat. Ass.</td>\r\n");
		      out.write("                <td class=\"b-h-periode\">Période</td>\r\n");
		      out.write("                <td class=\"b-h-statut\">Statu intég.</td>\r\n");
		      out.write("                <td class=\"b-h-detail\">Détail</td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("        </table>\r\n");
		      out.write("    </div>\r\n");
		      
		      out.write("    <!-- Détail niveau 2 -->\r\n");
		      out.write("    <div align=\"center\" class=\"bpij-div3\" style=\"height:100px;\">\r\n");
		      out.write("        <table class=\"bpij-div3-table3\" style=\"height:100px;\">\r\n");
		      out.write("          \r\n");
		      out.write("           <!-- Ligne 1 Nom -->\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-ss\">1610175111044</td>\r\n");
		      out.write("                <td class=\"b-d-nom\">DEVALEGAMA-GAMACHARIGEYSE</td>\r\n");
		      out.write("                <td class=\"b-d-nature\">IJNOR</td>\r\n");
		      out.write("                <td class=\"b-d-periode\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-statut\">EN GEDXXXX</td>\r\n");
		      out.write("                 <td class=\"b-d-detail\"><a class=\"lien-bouton-visu\" href=\"#\" onclick=\"return Visualisation();\">Visu</a></td>\r\n");
		      out.write("            </tr>\r\n");
		      
		      out.write("            <!-- Ligne 1 prÃ©nom -->\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-ss\"></td>\r\n");
		      out.write("                <td class=\"b-d-nom\">RAVI CHINTHAKA</td>\r\n");
		      out.write("                <td class=\"b-d-nature\"></td>\r\n");
		      out.write("                <td class=\"b-d-periode\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-statut\"></td>\r\n");
		      out.write("                <td class=\"b-d-detail\"></td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("            \r\n");
		      out.write("            <!-- Ligne 2 Nom -->\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-ss\">1610175111044</td>\r\n");
		      out.write("                <td class=\"b-d-nom\">DEVALEGAMA-GAMACHARIGEYSE</td>\r\n");
		      out.write("                <td class=\"b-d-nature\">IJNOR</td>\r\n");
		      out.write("                <td class=\"b-d-periode\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-statut\">EN GEDXXXX</td>\r\n");
	          out.write("                <td class=\"b-d-detail\"><a class=\"lien-bouton-visu\" href=\"#\" onclick=\"return Visualisation();\">Visu</a></td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("            <!-- Ligne 2 prÃ©nom -->\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-ss\"></td>\r\n");
		      out.write("                <td class=\"b-d-nom\">RAVI CHINTHAKA</td>\r\n");
		      out.write("                <td class=\"b-d-nature\"></td>\r\n");
		      out.write("                <td class=\"b-d-periode\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-statut\"></td>\r\n");
		      out.write("                <td class=\"b-d-detail\"></td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("            \r\n");
		      out.write("           <!-- Ligne 3 Nom -->\r\n");
		      out.write("            <tr>\r\n");
		      out.write("                <td class=\"b-d-ss\">1610175111044</td>\r\n");
		      out.write("                <td class=\"b-d-nom\">DEVALEGAMA-GAMACHARIGEYSE</td>\r\n");
		      out.write("                <td class=\"b-d-nature\">IJNOR</td>\r\n");
		      out.write("                <td class=\"b-d-periode\">15-01-2018</td>\r\n");
		      out.write("                <td class=\"b-d-statut\">EN GEDXXXX</td>\r\n");
		      out.write("                 <td class=\"b-d-detail\"><a class=\"lien-bouton-visu\" href=\"#\" onclick=\"return Visualisation();\">Visu</a></td>\r\n");
		      out.write("            </tr>\r\n");
		      out.write("        </table>\r\n");
		      out.write("    </div>     \r\n");
		      
			out.write("</body>");
			out.write("</html>");

			out.flush();
			out.close();

		}
		
		catch (Exception e) {System.out.println("HTML page error : " + e.getMessage());}
		
		return EtatPageHTML;

	} // end of BpijHtmlPageResultat

	/**
	 * @see prestijHTMLSessionError(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void prestijHtmlSessionError(HttpServletResponse res) {

		// Initialisation Cache page Error
		res.setContentType("text/html");
		res.setHeader("Pragma", "No-Cache");
		res.setDateHeader("Expires", 0);
		res.setHeader("Cache-Control", "no-Cache");

		// -------------------------------------
		// Envoi du flux HTML Erreur 
		// -------------------------------------
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
			out.println("<h3 style=\"color:red;font-size: 20px; font-weight:bold;" + "\""
					+ ">--- Désolé, votre session a expiré ---" + "</h3>");
			out.println("<a style=\"color:red;font-size: 20px; font-weight:bold;" + "\"");
			out.println(" href=\"" + Scheme + Adresse + Context + lienErreur + "\"" + "\"");
			out.println(">Merci de fermer votre navigateur ou de cliquer ici pour vous revenir à l'application</a>");
			out.println("</div>\r\n");
			out.println("</body>");
			out.println("</html>");

			out.flush();
		} catch (Exception e) {
			System.out.println("HTML page error : " + e.getMessage());
		}

	}

}
