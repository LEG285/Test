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
package transfert;

import java.io.IOException;
import javax.servlet.ServletException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.*;
import javax.servlet.http.*;

import transfertoutils.*;

import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class prestijDeconnexion
 */
@WebServlet("/PageDeconnexion")
public class PrestijDeconnexion extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Initialisation variables connexions
	static String Serveur = null;
	static String Programme, Adresse, Context, User;
	static String Servlet;
	static String login;
	static String string_date;
	static String string_heure;

	// Paramètres servlet serveur, version CSS, version JS, lien
	static String serveurSYS, versionCSS, versionJS, lien;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PrestijDeconnexion() {
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
		versionCSS = getInitParameter("versionCSS");
		versionJS = getInitParameter("versionJS");
		lien = getInitParameter("lien");

		// Messages console dans ini()
		System.out.println("init servlet : " + Servlet);
		// System.out.println( "(ini) Servlet initialisé avec paramètres : " );
		// System.out.println( "(ini) Nom du systeme : " + serveurSYS );
		// System.out.println( "(ini) version CSS : " + versionCSS );
		// System.out.println( "(ini) version JS : " + versionJS );
		// System.out.println( "(ini) lien : " + lien );

	} // end of init

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	synchronized protected void doGet(HttpServletRequest request, HttpServletResponse res)
			throws ServletException, IOException {

		// TODO Auto-generated method stub

		// Initialisation port + adresse + programme serveur web
		int PortN = request.getServerPort();
		String Port = new String();
		Port = String.valueOf(PortN);
		Serveur = request.getServerName();
		Adresse = request.getServerName() + ":" + Port;
		Context = request.getContextPath() + "/";

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

		// Fermer session
		HttpSession session = request.getSession(false);
		if (session != null) {
			login = (String) session.getAttribute("Utilisateur");
			System.out.println("Déconnexion du login : " + login + " le " + string_date + " à " + string_heure);
			
			// Déconnexion session
			session.invalidate();
		   
			// Déconnexion JDBC
			try {ConnexionDriverJDBCIbm.fermer();}
			catch (Exception e) {System.out.println("Erreur Fermeure ... " + e);}
		
		}
		
		// Initialisation Cache
		res.setContentType("text/html;charset=UTF-8");
		res.setHeader("Pragma", "No-Cache");
		res.setDateHeader("Expires", 0);
		res.setHeader("Cache-Control", "no-Cache");

		// Affichage flux html
		try {

			PrintWriter out;
			out = res.getWriter();

			// Ecriture flux html
		    out.write("<!DOCTYPE html>\r\n");
			out.write("<html>\r\n");
			out.write("<head>\r\n");
			   out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=11,IE=edge,chrome=1\" />\r\n");
			   out.write("<meta http-equiv=\"content-Language\" content=\"fr;charset=UTF-8\" />\r\n");
	           out.println("<link rel=stylesheet type=\"text/css\" href=\"css/transfert.css\" media=\"screen\"/>\r\n");
	           out.println("<script type=\"text/javascript\" src=\"js/transfert.js\"></script>\r\n");
	           out.println("<title>PRESTIJ - Consultation des flux</title>\r\n");
			out.write("</head>\r\n");
			out.write("<body class=\"deconnexion\" onload=\"javascript:document.login.nom.focus();document.body.style.cursor='default'\">  \r\n");
			out.write("<fieldset>\r\n");
			out.write(" <legend>*** Déconnexion effectuée ! ***</legend>\r\n");
			out.write("   <br><br>\r\n");
			out.write("     <a class=\"deconnexion\" href=\"http://" + Adresse + Context + lien + "\""
					+ ">Vous êtes maintenant déconnecté, fermer votre navigateur ou Cliquer ici pour vous revenir à l'application</a>\r\n");
			out.write("     <br><br>  \r\n");
			out.write("</fieldset>\r\n");
			out.write("</body>\t\r\n");
			out.write("</html>");

		}

		catch (java.lang.Throwable t) {
			System.out.println("HTML page error : " + t.getMessage());
		}

	} // Fin doGet()

} // Fin class
