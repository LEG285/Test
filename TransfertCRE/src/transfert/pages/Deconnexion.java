/**
 * **********************************************************************
 * Projet                 : TRANSFERT - Transfert des CRE entre système   
 * Package                : transfert.pages
 * Class                  : Deconnexion.java 
 *----------------------------------------------------------------------               
 * Objet                  : Page HTML déconnexion d'une session transfert
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 13/06/2025
 *----------------------------------------------------------------------
 * \\apache-tomcat-7.0.69\work\Catalina\localhost\transfert\org\apache\jsp
 ********************************************************************* 
 */
package transfert.pages;

public class Deconnexion {

	private String Adresse, Context, Scheme, Lien; 
	private StringBuffer sb = new StringBuffer();
	
	// ctor
	public Deconnexion (String[] arguments) {
		
		this.Scheme = arguments[0];
		this.Adresse = arguments[1];
		this.Context = arguments[2];
        this.Lien = arguments[3];
		
	}
    
	// Page HTML Déconnexion
	public StringBuffer GetPageDeconnexion() {

		// <head>
		HTMLheader header = new HTMLheader("CRE - Transfert CRE inter-syst&egrave;me", "Groupe Pasteur Mutualit&eacute; - Version 1.0");
		sb.append(header.GetPageHeaderBase());

		// <body>
		sb.append("<body class=\"dec\" >");
		sb.append("<div class=\"dec\">");
		sb.append("  <img src=\"./img/GPM1.jpg\" width=120 height=90 align=\"left\">");
		sb.append("  <p>");
		sb.append("  *** D&eacute;connexion effectu&eacute;e ! ***");
		sb.append("  <br><br>");
		sb.append(
				"  Vous avez &eacute;t&eacute; correctement d&eacute;connect&eacute;, merci de fermer votre navigateur");
		sb.append("  <br>");
		sb.append("  ou de <a href=\"" + Scheme + Adresse + Context + Lien + "\""
				+ ">Cliquer ici</a> pour revenir &agrave; l'application");
		sb.append("  </p>");
		sb.append("</body>\r\n");
		sb.append(header.GetPageFooter());
		 sb.append("</html>\r\n");

		return sb;
		
	}
	
}  // Fin class déconnexion
