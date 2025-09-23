/* **********************************************************************
 * Projet                 : PRESTIJ - AUTOMATISATION des FLUX IJ   
 * Package                : transfert
 * Class                  : PrestijAcceuil.java 
 *----------------------------------------------------------------------               
 * Objet                  : Portail d'acceuil application PRESTIJ  
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 26 03 2018 
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

import transfertoutils.RequeteDroitAcces;

//import hcr.tools.RequeteJDBCIBMi;

/**
 * Servlet implementation class PrestijAcceuil
 */
@WebServlet(description = "Acceuil", urlPatterns = { "/PrestijAcceuil" })
public class PrestijAcceuil extends HttpServlet {
	
	// Déclaration variables et constantes 
	private static final long serialVersionUID = 1L;

	// Initialisation variables connexions  
	static String Programme, Adresse, Context, Scheme,  User, Servlet;
	
	// Paramètres servlet serveur, version CSS, version JS, lien,.... 
    static String serveurSYS, versionCSS, versionJS;  
    static String Serveur, Login, Mdp, Bib;	
     
    static String lien1, titl1;  
    static String lien2, titl2;
    static String lien3, titl3;
    static String lien4, titl4;
    static String lienQuitter, titlQ, lienErreur;
    
    // Variables SQL
	static Boolean OK;
    static Vector<String> utilisateur = new Vector<String>();
    static Vector<String> programme = new Vector<String>();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrestijAcceuil() { 
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#init()
    */
    @Override
 	public void init(ServletConfig config) throws ServletException {
    
 		super.init(config);
 		
 		// Nom servlet local
 		Servlet = config.getServletName();
 			
 		// Paramètre du servlet 
 	 	versionCSS  = getInitParameter("versionCSS");
 	 	versionJS   = getInitParameter("versionJS"); 
 		lien1       = getInitParameter("lien1"); 
 		lien2       = getInitParameter("lien2"); 
 		lien3       = getInitParameter("lien3");
 		lien4       = getInitParameter("lien4"); 
 		lienQuitter = getInitParameter("lienQuitter"); 
 		lienErreur  = getInitParameter("lienErreur");

 		// "title" associé au lien
 		titl1 = "Param&eacute;trage de l'application PRESTIJ " ;
 	    titl2 = "Rechercher des flux GESTIP, CR et ARL associ&eacute; " ; 
 	    titl3 = "Rechercher des flux BPIJ avec leur statut d'int&eacute;gration "; 
 	    titl4 = "Consulter des flux archiv&eacute;s " ; 
 	    titlQ = "Fermer la session et quitter l'application" ;
 		
        // Messages console dans ini() 
 		System.out.println( "init servlet : " + Servlet );
 	 	// System.out.println( "(ini) Servlet initialisé avec paramètres : " );
 	    // System.out.println( "(ini) version CSS    : " + versionCSS );
 		
    }  // end of init   
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    synchronized protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
   @Override
	synchronized protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub

		// Initialisation port + adresse + programme serveur web
		int PortN = request.getServerPort();
		String Port = new String();
		Port = String.valueOf(PortN);
		Serveur = request.getServerName();
		Adresse = request.getServerName() + ":" + Port;
		Context = request.getContextPath() + "/";
        Scheme  = request.getScheme() + "://";

		// Récup informations session utilisateur
		HttpSession sessionHTTP = request.getSession(false);
		
		// Session expirée --> Page Erreur 
		if (sessionHTTP == null) {
			prestijHtmlSessionError(response);
		}
		
		// Session OK  --> Page HTML
		if (sessionHTTP != null) {
			
			Serveur = (String) sessionHTTP.getAttribute("Serveur");
			Login = (String) sessionHTTP.getAttribute("Utilisateur");
			Mdp = (String) sessionHTTP.getAttribute("MotdePasse");
			Bib = (String) sessionHTTP.getAttribute("BibFichier");

			// Recherche droits associés à ce login
			RequeteDroitAcces requete = new RequeteDroitAcces(Serveur, Login, Mdp, Bib);

			requete.LectureDroit();
			OK = requete.getOKKO();
			utilisateur = requete.getutilisateur();
			programme = requete.getprogramme();

			// Initialisation Cache
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Pragma", "No-Cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control", "no-Cache");

			// Affichage flux html
			try {

				PrintWriter out;
				out = response.getWriter();

				// Ecriture flux html
				out.println("<!DOCTYPE html>\r\n");
				out.println("<html>");
				out.println("<head>\r\n");
				out.println("<title>PRESTIJ - Consultation des flux</title>\r\n");
				out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n");
				out.println("<link rel=stylesheet type=\"text/css\" href=\"css/transfert.css\" media=\"screen\"/>\r\n");
				out.println("<meta charset=\"utf-8\">\r\n");
				out.println("</head>\r\n");

				out.println("<body id=\"body-menu\">\r\n");

				// Entete
				out.println("<div id=\"entete\">    \r\n");
				out.println("  <img src=\"img/logo_home.jpg\" width=130 height=70 align=\"left\">\r\n");
				out.println("  <center>\r\n");
				out.println("  <h2>Consultation des flux GESTIP et BPIJ</h2>\r\n");
				out.println("  </center>\r\n");
				out.println("</div>\r\n");
				out.println("<br>\r\n");

				// Corps partie gauche - menu
				out.println("<div id=\"main\" >\r\n");
				out.println("   <div id=\"menu\">\r\n");
				out.println("      <legend id=\"legend-menu\">Bienvenue " + Login + " !</legend>\r\n");

				// Lien1 vers Option 1 - Paramétrage
				if (programme.contains("WEBPRPARAM") && OK) {
					out.println("<a class=\"bouton-menu1\" href=\"" + Scheme + Adresse + Context + lien1 + "\"" + "\r\n");
					out.println("title=\"" + titl1 + "\""
							+ ">Param&eacute;trage PRESTIJ&nbsp;<img width=15 height=15 src=\"img/bon.png\"></a>\r\n");
				} else {
					out.println("<a class=\"bouton-menu1\" href=\"#" + "\"" + "\r\n");
					out.println("title=\"" + titl1 + "non autoris&eacute;" + "\""
							+ ">Param&eacute;trage PRESTIJ&nbsp;<img width=15 height=15 src=\"img/mauvais.png\"></a>\r\n");
				}

				// Lien2 vers Option 2 - Gestion des flux GESTIP
				if (programme.contains("WEBPRGESTI") && OK) {
					out.println("<a class=\"bouton-menu2\" href=\"" + Scheme + Adresse + Context + lien2 + "\"" + "\r\n");
					out.println("title=\"" + titl2 + "\""
							+ ">Rechercher des flux GESTIP&nbsp;<img width=15 height=15 src=\"img/bon.png\"></a>\r\n");
				} else {
					out.println("<a class=\"bouton-menu2\" href=\"#" + "\"" + "\r\n");
					out.println("title=\"" + titl2 + "non autoris&eacute;" + "\""
							+ ">Rechercher des flux GESTIP&nbsp;<img width=15 height=15 src=\"img/mauvais.png\"></a>\r\n");
				}

				// Lien3 vers Option 3 - Gestion des flux BPIJ
				if (programme.contains("WEBPRBPIJ ") && OK) {
					out.println("<a class=\"bouton-menu3\" href=\"" + Scheme + Adresse + Context + lien3 + "\"" + "\r\n");
					out.println("title=\"" + titl3 + "\""
							+ ">Rechercher des flux BPIJ&nbsp;<img width=15 height=15 src=\"img/bon.png\"></a>\r\n");
				} else {
					out.println("<a class=\"bouton-menu3\" href=\"#" + "\"" + "\r\n");
					out.println("title=\"" + titl3 + "non autoris&eacute;" + "\""
							+ ">Rechercher des flux BPIJ&nbsp;<img width=15 height=15 src=\"img/mauvais.png\"></a>\r\n");
				}
 
				// Lien4 vers Option 4 - Consultation des flux archivés
				if (programme.contains("WEBPRCONS") && OK) {
					out.println("<a class=\"bouton-menu4\" href=\"" + Scheme + Adresse + Context + lien4 + "\"" + "\r\n");
					out.println("title=\"" + titl4 + "\""
							+ ">Consulter des flux archiv\351s&nbsp;<img width=15 height=15 src=\"img/bon.png\"></a>\r\n");
				} 
				else {
					out.println("<a class=\"bouton-menu4\" href=\"#" + "\"" + "\r\n");
					out.println("title=\"" + titl4 + "non autoris&eacute;" + "\""
							+ ">Consulter des flux archiv\351s&nbsp;<img width=15 height=15 src=\"img/mauvais.png\"></a>\r\n");
				}

				// Lien Quitter - Déconnexion
				out.println("<a class=\"deconnexion-menu\" href=\"" + Scheme + Adresse + Context + lienQuitter + "\""
						+ ">D&eacute;connexion</a> \r\n");

				// Message d'erreur lors de la requête SQL
				if (!OK) {
					out.println("<p class=\"erreur-menu\">Attention !!! Anomalie SQL, voir avec le support informatique...</p>\r\n");
				}

				// Corps partie droite - Images
				out.println("<p id=\"version-menu\">** Version 1.0 **</p>\r\n");
				out.println("</div>\r\n");
				
				out.println("  <div id=\"contenu\" >\r\n");
				out.println("     <img class=\"menu-image\" src=\"img/logo_cgam.jpg\">\r\n");
				out.println("     <img class=\"menu-image\" src=\"img/logo_gps.jpg\" >\r\n");
				out.println("</div>\r\n");

				out.println("</div>\r\n");

				// Pied
				out.println("<div id=\"footer\">\r\n");
				out.println("</div>\r\n");
				out.println("</body>\r\n");
				out.println("</html>");

			} 
			catch (java.lang.Throwable t) {
				System.out.println("HTML page error : " + t.getMessage());
			}

		} // Fin de test Session
		
	} // Fin de doGet()


	protected void prestijHtmlSessionError (HttpServletResponse res) {
		
	     // Initialisation Cache page Error
	     res.setContentType("text/html");
	     res.setHeader("Pragma","No-Cache");
	     res.setDateHeader("Expires", 0);
	     res.setHeader("Cache-Control","no-Cache");
	      
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
			  out.close();
		        
		 }
		 catch(Exception e) {System.out.println("HTML page error : " + e.getMessage());}
		    
	} // end prestijTranscoHTMLError ()	
	
} // Fin de class
