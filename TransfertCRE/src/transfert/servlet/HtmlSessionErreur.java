/* **********************************************************************
 * Projet                 : PRESTIJ - AUTOMATISATION des FLUX IJ   
 * Package                : transfert.servlet
 * Class                  : PrestijHtmlSessionError.java 
 *----------------------------------------------------------------------               
 * Objet                  : Session Error   
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 18 05 2018 
 *----------------------------------------------------------------------
 * Chemin class jsp       : \apache-tomcat-7.0.69\work\Catalina\localhost\transfert\...
 ********************************************************************* */
package transfert.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import transfert.pages.*;

/**
 * Servlet implementation class PresponsetijHtmlSessionError
 */
@WebServlet("/transfert.servlet.HtmlSessionErreur")
public class HtmlSessionErreur extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	// Variables paramètres servlet s
	static String Serveur, servlet, versionCSS, versionJS, lienRetourAppli;

	// Initialisation variables HTTP
	static String Programme, Adresse, Context, Scheme;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HtmlSessionErreur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * HttpServlet#init()
	*/
	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		// Récup paramètres servlet
		versionCSS  = getInitParameter("versionCSS");
		versionJS   = getInitParameter("versionJS");
		lienRetourAppli = getInitParameter("lienAppli");

		// Valeurs dans init()
		System.out.println("init servlet : " + config.getServletName());

	} // end of init()
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
	synchronized protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// Récupération des paramètres HTTP
		int PortN = request.getServerPort();
		String Port = new String();
		Port = String.valueOf(PortN);
		Serveur = request.getServerName();
		Adresse = request.getServerName() + ":" + Port;
		Context = request.getContextPath() + "/";
		Scheme = request.getScheme() + "://";

		// Chargement arguments pages HTML
		String [] argument = new String[] {Scheme, Adresse, Context, lienRetourAppli};
		SessionErreur pageHTML = new SessionErreur(argument);

		// Preparation flux HTML
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-Cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-Cache");
		response.setContentLength(pageHTML.GetPageSessionErreur().length());
		
		// flux html
		try {response.getOutputStream().println(pageHTML.GetPageSessionErreur().toString());}
		catch (Exception e) {System.out.println("HTML page error : " + e.getMessage());}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	*/
	synchronized protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		doGet(request, response);
	}

} 
