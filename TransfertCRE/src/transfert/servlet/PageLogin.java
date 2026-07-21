/* **********************************************************************
 * Projet                 : TRANSFERT - Transfert des CRE entre système   
 * Package                : transfert.servlet
 * Class                  : PageLogin.java 
 *----------------------------------------------------------------------               
 * Objet                  : Connexion sur IBM i 
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 13/06/2025      
 *----------------------------------------------------------------------
 * Chemin des class jsp   :
 * C:\apache-tomcat-7.0.69\work\Catalina\localhost\transfert\org\apache\jsp
 ********************************************************************* */
package transfert.servlet;

import javax.servlet.*; 
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;
import java.io.*;

//IBM DB2 Use to communicate with AS400.  
import com.ibm.as400.access.*;
import transfert.pages.*;
    
//@WebServlet(description = "Saisie login utilisateur Transfert", urlPatterns = { "/transfert.servlet.PageLogin" })
@WebServlet(description = "Saisie login utilisateur Transfert CRE", urlPatterns = { "/PageLogin" })
/**
 * Servlet implementation class TransfertLogin  
 */     
public class PageLogin extends HttpServlet {  
	         
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
	static String MapServlet;
	
	// Variables paramètres servlet serveurs/version CSS/version JS 
    static String serveurDB2, serveurLOC, serveurDEV, serveurREC, serveurPROD,
                  versionCSS, versionJS, lien, bibFic;
       
    /**
     * @see HttpServlet#HttpServlet()
    */
    public PageLogin() {
          super();
    }
    
    /**
     * @see HttpServlet#init()
    */
    @Override
	public void init(ServletConfig config) throws ServletException {
    
		super.init(config);

		// Nom servlet
		Servlet    = config.getServletName();
		// Récupération paramètres associés au servlet
	 	versionCSS = getInitParameter("versionCSS");
	 	versionJS  = getInitParameter("versionJS"); 
		lien       = getInitParameter("lien");  
		MapServlet = getInitParameter("mapping");
		
        // Message console dans ini()  
		System.out.println( "Initialisation servlet : " + Servlet );
	    
    }  // end of init          
     
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
    @Override
	synchronized protected void doGet(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
 
		  // Initialisation port + adresse + programme serveur web
		  int    PortN = request.getServerPort();
	      String Port  = new String(); 
	      Port         = String.valueOf(PortN);   
	      Serveur      = request.getServerName();
	      Adresse      = request.getServerName() + ":" + Port;
          Context      = request.getContextPath() + "/";
          Scheme       = request.getScheme() + "://";
          //HttpServletMapping ddd = request.getHttpServletMapping();
          //MapServlet = "PageLogin"; //ddd.getMatchValue();
          
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
          Scheme       = request.getScheme() + "://";
            
 		  // récupération paramètres servlet 
	      String login = request.getParameter("nom");
	      String motdepasse  = request.getParameter("motdepasse");
	      
 		  /* Validation utilisateur */
	      try {
	    	    ValidationLogin (login);
	    	    ValidationMotdePasse (motdepasse);
	    	    VerificationIBMi (Serveur, login , motdepasse);
	      }
	      catch (Exception e ) {
	    	    ok = prestijLoginHtml (res , e.getMessage());
	      }
 
	      finally {   

	    	  if (ok)  {
	    		  
	    		  // Creation d'une session http (On met juste le profil) 
	    		  HttpSession SessionLogin = request.getSession(true);
	    		  // if session is not new then we are switching the session and start a new one
	    		  if (!(SessionLogin.isNew())) {
	    		        SessionLogin.invalidate();
	    		        SessionLogin = request.getSession(true);
	    		  }   
	    		  SessionLogin.setAttribute("Utilisateur",  login);
	    		  //SessionLogin.setAttribute("Serveur", serveurDB2);
	    		  SessionLogin.setAttribute("MotdePasse",  motdepasse);
	    		  //SessionLogin.setAttribute("BibFichier",  bibFic);
	    		  
	    	      // Si OK Transmission au servlet PageSelection 
	    		  //System.out.println( "Scheme : " + Scheme );
	    		  //this.getServletContext().getRequestDispatcher("/"+ lien).forward(request, res);
	              res.sendRedirect(request.getContextPath() + "/" + lien);  		  
	    		   
	    	  }
	    	  else {  

	    		  /* Si KO ....*/
	  			request.setAttribute("erreur_login", "Le profil IBM i est inconnu ou le mot de passe est incorrect");
	  			//request.getRequestDispatcher("NewFile.jsp").forward(request, res);
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
	    
		// Initialisation variable pour pr�-connections
		String ServeurMaj    = Serveur.toUpperCase();
		String LoginMaj      = Login.toUpperCase();
		String MotdePasseMaj = MotdePasse;
		
		// Détermination de l'environnement d'exécution
		if (ServeurMaj.equals("LOCALHOST"))                 {Serveur = "S44C0638";} // Forcer le serveur DEV 
		//if (ServeurMaj.equals(serveurDEV.toUpperCase()))  {Serveur = serveurDEV;}	
		//if (ServeurMaj.equals(serveurREC.toUpperCase()))  {Serveur = serveurREC;}	
		//if (ServeurMaj.equals(serveurPROD.toUpperCase())) {Serveur = serveurPROD;}

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
	   
	     // Initialisation retour
	     boolean EtatPageHTML = Erreur.isEmpty();
	        
		 // Chargement arguments pages HTML
		 String [] argument = new String[] {Scheme, Adresse, Context,  MapServlet, Serveur, Erreur};
		 Login pageHTML = new Login(argument);
	     
		 // Initialisation Cache   
		 res.setContentType("text/html");
		 res.setHeader("Pragma","No-Cache");
		 res.setDateHeader("Expires", 0);
		 res.setHeader("Cache-Control","no-Cache");			
			
	     // Envoi flux html
		 try {res.getOutputStream().println(pageHTML.GetPageLogin().toString());}
		 catch (Exception e) {System.out.println("HTML page error : " + e.getMessage());}
	     
	     return EtatPageHTML;  
	      
	}  // end prestijLoginHtml

} // End of Class


























