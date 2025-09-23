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
package transfert.servlet;

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

import transfert.pages.HTMLheader;
import transfert.requetes.RequeteDroitAcces;

//import hcr.tools.RequeteJDBCIBMi;

/**
 * Servlet implementation class PrestijAcceuil
 */
@WebServlet(description = "Acceuil", urlPatterns = { "/transfert.servlet.PrestijAcceuil" })
public class PrestijAcceuil extends HttpServlet {
	
	// D�claration variables et constantes 
	private static final long serialVersionUID = 1L;

	// Initialisation variables connexions  
	static String Programme, Adresse, Context, Scheme,  User, Servlet;
	
	// Param�tres servlet serveur, version CSS, version JS, lien,.... 
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
 			
 		// Param�tre du servlet 
 	 	versionCSS  = getInitParameter("versionCSS");
 	 	versionJS   = getInitParameter("versionJS"); 
 		lien1       = getInitParameter("lien1"); 
 		lien2       = getInitParameter("lien2"); 
 		lien3       = getInitParameter("lien3");
 		lien4       = getInitParameter("lien4"); 
 		lienQuitter = getInitParameter("lienQuitter"); 
 		lienErreur  = getInitParameter("lienErreur");

 		// "title" associ� au lien
 		titl1 = "Param&eacute;trage de l'application PRESTIJ " ;
 	    titl2 = "Rechercher des flux GESTIP, CR et ARL associ&eacute; " ; 
 	    titl3 = "Rechercher des flux BPIJ avec leur statut d'int&eacute;gration "; 
 	    titl4 = "Consulter des flux archiv&eacute;s " ; 
 	    titlQ = "Fermer la session et quitter l'application" ;
 		
        // Messages console dans ini() 
 		System.out.println( "init servlet : " + Servlet );
 	 	// System.out.println( "(ini) Servlet initialis� avec param�tres : " );
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

		// Prépa page
		HTMLheader header = new HTMLheader("PRESTIJ - Consultation des flux",
				                           "Domaine Pr&eacute;voyance : R&eacute;ception automatis&eacute;e des IJ - Version 1.1  &copy; 2018");

		// Récup informations session utilisateur
		HttpSession sessionHTTP = request.getSession(false);
		
		// Session expirée --> Page Erreur 
		if (sessionHTTP == null) {
			
			request.getRequestDispatcher(lienErreur).forward(request, response);  // A mettre en commentaire pour dév.
		
		}
		
		// Session OK  --> Page HTML
		if (sessionHTTP != null) {
			
			Serveur = (String) sessionHTTP.getAttribute("Serveur");
			Login = (String) sessionHTTP.getAttribute("Utilisateur");
			Mdp = (String) sessionHTTP.getAttribute("MotdePasse");
			Bib = (String) sessionHTTP.getAttribute("BibFichier");

			// Recherche droits associ�s � ce login
			RequeteDroitAcces requete = new RequeteDroitAcces(Serveur, Login, Mdp, Bib);

			requete.LectureDroit();
			OK = requete.getOKKO();
			utilisateur = requete.getutilisateur();
			programme = requete.getprogramme();
			
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Pragma", "No-Cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control", "no-Cache");
			
			// Affichage flux html
			try {
				
				PrintWriter out;
				out = response.getWriter();

				// <head>
				out.println(header.GetPageHeaderBase().toString());

				// <body>
				out.println("<body id=\"body-menu\">");

				// Entete
				out.println("<div id=\"entete\">    \r\n");
				out.println("  <img src=\"img/logo_home.jpg\" width=130 height=70 align=\"left\">\r\n");
				out.println("  <h3>Consultation des flux GESTIP et BPIJ</h3>\r\n");
				out.println("</div>\r\n");
				out.println("<br>\r\n");

				out.println("<div id=\"main\" >\r\n");

				// Corps partie gauche - menu
				out.println("<div id=\"menu\">\r\n");
				out.println("  <legend id=\"legend-menu\" class=\"legend\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Bienvenue " + Login + " !");
				out.println("</legend>\r\n");
				
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
 
				// Lien4 vers Option 4 - Consultation des flux archiv�s
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
				
				
				
				// Déconnexion
				out.println("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>\r\n");
				out.println("  <button class=\"btn btn-danger btn-lg\" type=\"button\" "
					      + "  onClick=\"location.href='" + Scheme + Adresse + Context + lienQuitter + "';\">");
				out.println("  <span class=\"glyphicon glyphicon-off\"></span>");
				out.println("D&eacute;connexion");
				out.println("  </button>");
				


				// Message d'erreur lors de la requête SQL
				if (!OK) {out.println("<p class=\"erreur-menu\">Attention !!! Anomalie SQL, voir avec le support informatique...</p>\r\n");}

				out.println("</div>\r\n");
				  // Corps partie droite - Images
				  out.println("  <div id=\"contenu\" >\r\n");
				  out.println("     <img class=\"menu-image\" src=\"img/logo_cgam.jpg\">\r\n");
				  out.println("     <img class=\"menu-image\" src=\"img/logo_gps.jpg\" >\r\n");
				  out.println("</div>\r\n");
				out.println("</div>\r\n");
				out.println("</body>\r\n");
				
				// Pied
				out.println(header.GetPageFooter().toString());
				
				out.println("</html>\r\n");

			} 
			catch (java.lang.Throwable t) {System.out.println("HTML page error : " + t.getMessage());}

		} // Fin de test Session
		
	} // Fin de doGet()
	
} // Fin de class
