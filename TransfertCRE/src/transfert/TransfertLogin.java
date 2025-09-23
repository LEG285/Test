/* **********************************************************************
 * Projet                 : PRESTIJ - AUTOMATISATION des FLUX IJ   
 * Package                : transfert
 * Class                  : TransfertLogin.java 
 *----------------------------------------------------------------------               
 * Objet                  : Saisie PageLogin de connexion IBM i 
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 23 03 2018 (Dernière modif = date maj fichier)
 *----------------------------------------------------------------------
 * Chemin des class jsp   :
 * C:\apache-tomcat-7.0.69\work\Catalina\localhost\transfert\org\apache\jsp
 ********************************************************************* */
package transfert;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;
import java.io.*;

//IBM DB2 Use to communicate with AS400.  
import com.ibm.as400.access.*;

@WebServlet(description = "Saisie login utilisateur Prestij", urlPatterns = { "/transfert.TransfertLogin" })
/**
 * Servlet implementation class TransfertLogin
 */
public class TransfertLogin extends HttpServlet {
	
	// Déclaration variables et constantes 
	private static final long serialVersionUID = 1L;
    static final String TITRE = "Connexion sur le serveur : ";
	static final String ESP = "&nbsp;";                           // one space
	static String string_date;                                    // Date                        
	static String string_heure;                                   // Heure  
	   
	// Use to connect on AS400.
	static String Serveur = null;                                   
	static AS400 systemI  = null;                                  // server system i  
		 
	// Initialisation variables HTTP  
	static String Programme, Adresse, Context, Scheme,  User, Acceuil;
	static String Servlet, Pack, Workspace;	   
	
	// Variables paramètres servlet serveurs/version CSS/version JS 
    static String serveurDB2, serveurLOC, serveurDEV, serveurREC, serveurPROD,
                  versionCSS, versionJS, lien, bibFic;
       
    /**
     * @see HttpServlet#HttpServlet()
    */
    public TransfertLogin() {
          super();
           // TODO Auto-generated constructor stub
    }
  
    /**
     * @see HttpServlet#init()
    */
    @Override
	public void init(ServletConfig config) throws ServletException {
    
		super.init(config);

		// Nom servlet  
		Servlet = config.getServletName();
		
		// Paramètres servlet 
	 	serveurLOC  = getInitParameter("serveurLOC");
	 	serveurDEV  = getInitParameter("serveurDEV");
	 	serveurREC  = getInitParameter("serveurREC");
	 	serveurPROD = getInitParameter("serveurPROD");
	 	bibFic      = getInitParameter("bibFichier");
	 	
	 	versionCSS = getInitParameter("versionCSS");
	 	versionJS  = getInitParameter("versionJS"); 
		lien       = getInitParameter("lien");  
	 	
        // Message console dans ini() 
		 System.out.println( "init servlet : " + Servlet );
	 	// System.out.println( "(ini) Servlet initialisï¿½e avec paramï¿½tres : " );
	    // System.out.println( "(ini) Nom du systeme : " + serveurSYS );
	    // System.out.println( "(ini) version CSS    : " + versionCSS );
	    // System.out.println( "(ini) version JS     : "  + versionJS );
	    // System.out.println( "(ini) lien           : "  + lien );
	    
    }  // end of init       
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
    @Override
	synchronized protected void doGet(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {

		  // TODO Auto-generated method stub  
	
		  // Initialisation port + adresse + programme serveur web
		  int    PortN = request.getServerPort();
	      String Port  = new String(); 
	      Port         = String.valueOf(PortN); 
	      Serveur      = request.getServerName();
	      Adresse      = request.getServerName() + ":" + Port;
          Context      = request.getContextPath() + "/";
          Scheme        = request.getScheme() + "://";
          
 		  // Envoi flux HTML 
		  prestijLoginHtml (res , "0");  
		  
	}  // End of doGet
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	synchronized protected void doPost(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
    	
 		  // Initialisation  
		  int    PortN = request.getServerPort();
	      String Port  = new String();
	      
	      // Récupération date et heure système  
		  Calendar now = Calendar.getInstance(Locale.FRENCH);
          // date
		  int jour = now.get(Calendar.DAY_OF_MONTH);
		  int mois = now.get(Calendar.MONTH) + 1;
		  int annee = now.get(Calendar.YEAR);
		  // heure  
		  int heure = now.get(Calendar.HOUR);
		  int minute = now.get(Calendar.MINUTE);
		  int seconde = now.get(Calendar.SECOND); 
		  // cast int -> string
		  string_date = String.valueOf(jour) + '/' + String.valueOf(mois) + '/' + String.valueOf(annee);
		  string_heure = String.valueOf(heure) + ':' + String.valueOf(minute) + ':' + String.valueOf(seconde);
	      
	      boolean ok = true;
	      
          // Init 	      
	      Port         = String.valueOf(PortN); 
	      Serveur      = request.getServerName();
	      Adresse      = request.getServerName() + ":" + Port; 
          Context      = request.getContextPath() + "/";
          Scheme        = request.getScheme() + "://";
           
 		   // Récupération paramètres servlet   
	      String login = request.getParameter("nom");
	      String motdepasse  = request.getParameter("motdepasse");
		  		  
		  /* Validation utilisateur */
	      try {ValidationLogin (login);}
	      catch (Exception e ) {ok = prestijLoginHtml (res , e.getMessage());}

	      /* Validation mot de passe */
	      try {ValidationMotdePasse (motdepasse);}
	      catch (Exception e ) {ok = prestijLoginHtml (res , e.getMessage());}
	     
		  /* Validation profil utilisateur IBM i */
	      try {VerificationIBMi (Serveur, login , motdepasse);}
   	      catch (Exception e ) {ok = prestijLoginHtml (res , e.getMessage());}
	      
	      finally {

	    	  if (ok)  {
	    		  
	    		  // Creation d'une session http
	    		  HttpSession SessionLogin = request.getSession(true);
	    		  // if session is not new then we are switching the session and start a new one
	    		  if (!(SessionLogin.isNew())) {
	    		        SessionLogin.invalidate();
	    		        SessionLogin = request.getSession(true);
	    		  } 
	    		  
	    		  SessionLogin.setAttribute("Serveur", serveurDB2);
	    		  SessionLogin.setAttribute("Utilisateur",  login);
	    		  SessionLogin.setAttribute("MotdePasse",  motdepasse);
	    		  SessionLogin.setAttribute("BibFichier",  bibFic);
	    		  
	    	      /* Si OK Transmission au servlet portail PrestijAcceuil  */
	    		  System.out.println( "Connexion **OK** du login : " + login + " le " + string_date + " à " + string_heure);
	    		  System.out.println( "Sheme : " + Scheme );
	    		  this.getServletContext().getRequestDispatcher("/"+ lien).forward(request, res);
	    		  
	    	  }
	    	  else { 
	    		  /* Si KO ....*/
	    		  System.out.println( "Connexion **KO** du login : " + login + " le " + string_date + " à " + string_heure); 
	    	  }
	      
	      }
	     
	} // End of doPost
	
	/**
	 * @see ValidationLogin(String PageLogin)
	 */
	private void ValidationLogin (String Login) throws Exception {
		if (Login.equals("")) {
			throw new Exception("Utilisateur obligatoire");
		}
	}

	/**
	 * @see ValidationMotdePasse(String MotdePasse)
	 */
	private void ValidationMotdePasse (String MotdePasse) throws Exception {
	     if (MotdePasse.equals(""))  {
	    	throw new Exception("Mot de passe obligatoire");
	    }
	}

	/**
	 * @see VerificationIBM(String PageLogin , String MotdePasse)
	 */
	private void VerificationIBMi (String Serveur, String Login , String MotdePasse) throws Exception {
	    
		// Initialisation variable pour prï¿½-connections
		String ServeurMaj    = Serveur.toUpperCase();
		String LoginMaj      = Login.toUpperCase();
		String MotdePasseMaj = MotdePasse;
		
		// Détermination environnement d'exécution
		if (ServeurMaj.equals("LOCALHOST"))               {Serveur = serveurLOC;}
		if (ServeurMaj.equals(serveurDEV.toUpperCase()))  {Serveur = serveurDEV;}	
		if (ServeurMaj.equals(serveurREC.toUpperCase()))  {Serveur = serveurREC;}	
		if (ServeurMaj.equals(serveurPROD.toUpperCase())) {Serveur = serveurPROD;}

		// Affichage du serveur d'exécution
		System.out.println("Nom du serveur d'exécution :" + ServeurMaj);
		
		try {
	        // Create an AS400ConnectionPool.
            AS400ConnectionPool testPool = new AS400ConnectionPool();
            // to the command service is avoided.
            AS400 newConn1 = testPool.getConnection(Serveur, LoginMaj, MotdePasseMaj, AS400.COMMAND);
            // Close the test pool.
            testPool.close();
            serveurDB2 = Serveur;
 	    }
	    catch (Exception e) {
	    	throw new Exception("Profil IBM inconnu ou mot de passe incorrect");
	    }
	}
	
	/**
	* @see prestijLoginHtml(HttpServletRequest res, String Erreur)
	*/ 
	protected boolean prestijLoginHtml(HttpServletResponse res, String Erreur) {
		
	     // Initialisation Cache
	     res.setContentType("text/html");
	     res.setHeader("Pragma","No-Cache");
	     res.setDateHeader("Expires", 0);
	     res.setHeader("Cache-Control","no-Cache");
	   
	     // Initialisation retour
	     boolean EtatPageHTML = Erreur.isEmpty();
	      
	     // Affichage page login 
	     try { 
	    	  
	    	PrintWriter out;   
	        out = res.getWriter();
	
		    // Envoi du flux HTML
	        out.println("<!DOCTYPE html>\r\n");
	        out.println("<html>\r\n");
	        
	        // <head>
	        out.println("<head>\r\n");
	           out.println("<meta http-equiv=\"content-Language\" content=\"fr;charset=UTF-8\"/>\r\n");
	           out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n");
	           out.println("<link rel=\"shortcut icon\"type=\"image/x-icon\" href=\"favicon.ico\"/>\r\n");
	           out.println("<link rel=stylesheet type=\"text/css\" href=\"css/transfert.css\" media=\"screen\"/>\r\n");
	           out.println("<script type=\"text/javascript\" src=\"js/transfert.js\"></script>\r\n");
	           out.println("<title>PRESTIJ - Consultation des flux</title>\r\n");
	        out.println("</head>\r\n");
	        
	        // <body>
	        out.println("<body class=\"login\" onload=\"javascript:document.login.nom.focus();document.body.style.cursor='default';\">\r\n");
	        out.println("<img src=\"img/logo_home.jpg\" width=130 height=70 align=\"left\"> \r\n");
	        out.println("<br><br><br><br><br>\r\n");
	        
	        // <form>
	        out.println("<form name=\"" + "login" + "\"" + " onsubmit=\"" + "return ValiderForm(this.form)" + "\"" + 
                        " action=\"" + Scheme + Adresse + Context + Servlet + "\"" + " method=\"" + "post" + "\"" + ">");
	        out.println("<fieldset><legend>" + TITRE + Serveur + "</legend>\r\n");
	        out.println("<br><br>\r\n");
	           out.println("<label for=\"nom\" class=\"login\">Utilisateur . . . . . . . . . . . . . . . . . . :</label>\r\n");
	           out.println("<input class=\"login\" type=\"text\" placeholder=\"PageLogin AS/400\" id=\"nom\" name=\"nom\" size=\"20\" maxlength=\"20\" required/>\r\n");
	           out.println("<br><br>\r\n");
	           
	           out.println("<label for=\"motdepasse\" class=\"login\">Mot de passe . . . . . . . . . . . . . . . :</label>\r\n");
	           out.println("<input class=\"login\" type=\"password\" placeholder=\"Password AS/400\" id=\"motdepasse\" name=\"motdepasse\"  size=\"20\" maxlength=\"20\" required/>\r\n");
	           out.println("<input class=\"valider-login\" type=\"submit\" name=\"valider\" value=\"VALIDER\"/>     \r\n");
	           out.println("<br><br><br>\r\n");
	           
	           // affichage erreurs  
               if (Erreur.equals("Utilisateur obligatoire")) {
            	   out.println("<span class=\"" +"erreur" + "\"" + ">" + Erreur.toString() + "</span>");
            	}
               if (Erreur.equals("Mot de passe obligatoire")) {
            	   out.println("<span class=\"" +"erreur" + "\"" + ">" + Erreur.toString() + "</span>");
               }
               if (Erreur.equals("Profil IBM inconnu ou mot de passe incorrect")) {
            	   out.println("<span class=\"" +"erreur" + "\"" + ">" + Erreur.toString() + " </span>");
               }
               // Fin d'affichage erreurs
               
	        out.println(" </fieldset> \r\n");
	        out.println("</form>\r\n");
	        
	        out.println("</body>\r\n");
	        out.println("</html>");
	        
		    out.flush();
		    out.close();
	        
	     }
	     catch(Exception e) {
	    	 System.out.println("HTML page error : " + e.getMessage());
	     }
	    
	     return EtatPageHTML;  
	      
	} // end outputError

} // End of Class
