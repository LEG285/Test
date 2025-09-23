/**
 * **********************************************************************
 * Projet                 : PRESTIJ - AUTOMATISATION IJ   
 * Package                : transfertoutils
 * Class                  : PageDeconnexion.java 
 *----------------------------------------------------------------------               
 * Objet                  : Deconnexion d'une session transfert
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 27 03 2018 
 ********************************************************************* 
 */
package transfert.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.*;
import javax.servlet.http.*;

import transfert.outils.*;
import transfert.pages.*;

import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class prestijDeconnexion
 */
@WebServlet("/transfert.servlet.PageDeconnexion")
public class PageDeconnexion extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Initialisation variables connexions
	static String Serveur = null;
	static String Programme, Adresse, Context, Scheme, User;
	static String Servlet;
	static String login;
	static String string_date;
	static String string_heure;

	// Param�tres servlet serveur, version CSS, version JS, lien
	static String serveurSYS, versionCSS, versionJS, lien;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PageDeconnexion() {
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
		versionCSS = getInitParameter("versionCSS");
		versionJS = getInitParameter("versionJS");
		lien = getInitParameter("lien");

		// Messages console dans ini()
		System.out.println("init servlet : " + Servlet);
		// System.out.println( "(ini) Servlet initialis� avec param�tres : " );
		// System.out.println( "(ini) Nom du systeme : " + serveurSYS );
		// System.out.println( "(ini) version CSS : " + versionCSS );
		// System.out.println( "(ini) version JS : " + versionJS );
		// System.out.println( "(ini) lien : " + lien );

	}   // end of init

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	synchronized protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	synchronized protected void doGet(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {

		// TODO Auto-generated method stub

		// Initialisation port + adresse + programme serveur web
		int PortN = request.getServerPort();
		String Port = new String();
		Port = String.valueOf(PortN);
		Serveur = request.getServerName();
		Adresse = request.getServerName() + ":" + Port;
		Context = request.getContextPath() + "/";
		Scheme = request.getScheme() + "://";

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

		// Fermeture session
		HttpSession session = request.getSession(false);
		if (session != null) {
			
			login = (String) session.getAttribute("Utilisateur");
			System.out.println("Déconnexion du login : " + login + " le " + string_date + " à " + string_heure);
			
			// Déconnexion session
			session.invalidate();
		   
			// Déconnexion JDBC
			try {ConnexionDriverJDBCIbm.fermer();}
			catch (Exception e) {System.out.println("Erreur Fermeture ... " + e);}
		
		}
		
		// Chargement arguments pages HTML
		String [] argument = new String[] {Scheme, Adresse, Context, lien};
		Deconnexion pageHTML = new Deconnexion(argument);
		
		// Préparation envoi flux HTML
		res.setContentType("text/html");
		res.setHeader("Pragma", "No-Cache");
		res.setDateHeader("Expires", 0); 
		res.setHeader("Cache-Control", "no-Cache");
		res.setContentLength(pageHTML.GetPageDeconnexion().length());
		
		// Envoi flux html
		try {res.getOutputStream().println(pageHTML.GetPageDeconnexion().toString());}
		catch (Exception e) {System.out.println("HTML page error : " + e.getMessage());}
		
	} // Fin doGet()

} // Fin class
