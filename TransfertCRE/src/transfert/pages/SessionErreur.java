/**
 * **********************************************************************
 * Projet                 : TRANSFERT - Transfert des CRE entre système  
 * Package                : presti.pages
 * Class                  : SessionErreur.java 
 *----------------------------------------------------------------------               
 * Objet                  : Page HTML quand erreur de session  
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 13/06/2025
 *----------------------------------------------------------------------
 * \\apache-tomcat-7.0.69\work\Catalina\localhost\transfert\org\apache\jsp
 ********************************************************************* 
 */
package transfert.pages;

public class SessionErreur {

	private String Adresse, Context, Scheme, Lien; 
	private StringBuffer sb = new StringBuffer();
	
	// ctor
	public SessionErreur (String[] arguments) {
		
		this.Scheme = arguments[0];
		this.Adresse = arguments[1];
		this.Context = arguments[2];
        this.Lien = arguments[3];
		
	}
    
	// Page HTML SessionErreur
	public StringBuffer GetPageSessionErreur() {

		// <head>
		HTMLheader header = new HTMLheader("CRE - Transfert CRE inter-syst&egrave;me", "Groupe Pasteur Mutualit&eacute; - Version 1.0");
		sb.append(header.GetPageHeaderBase());
		
		// <body>
		sb.append("<body class=\"erreur-session" + "\"" + ">");
		sb.append("<div class=\"erreur-session" + "\"" + ">");
		
	    sb.append(" <img src=\"./img/GPM1.jpg\" width=120 height=90 align=\"left\">");
	    sb.append("  <p style=\"color:red;font-size: 20px; font-weight:bold;" + "\"" + ">");
	    sb.append("  **** D&eacute;sol&eacute;, votre session a expir&eacute; ! *****");
	    sb.append("  <br><br>");
	    sb.append("  Merci de fermer votre navigateur ou de cliquer ");
	    sb.append("  <a href=\"" + Scheme + Adresse + Context + Lien + "\"" + ">Cliquer ici</a> pour revenir &agrave; l'application");
	    sb.append("  </p>"); 
		
		sb.append("</div>");
		sb.append("</body>\r\n");
		sb.append(header.GetPageFooter());
		sb.append("</html>\r\n");

		return sb;
		
	}
	
}  // Fin class SessionErreur
