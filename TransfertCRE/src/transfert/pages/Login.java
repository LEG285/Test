/**
 * **********************************************************************
 * Projet                 : PRESTIJ - AUTOMATISATION IJ   
 * Package                : transfert.pages
 * Class                  : PageLogin.java 
 *----------------------------------------------------------------------               
 * Objet                  : Page HTML PageLogin   
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 31 07 2018
 *----------------------------------------------------------------------
 * Chemin des class jsp   :
 * \\apache-tomcat-7.0.69\work\Catalina\localhost\transfert\org\apache\jsp
 ********************************************************************* 
 */
package transfert.pages;

public class Login {

	private final String TITRE = "Connexion sur le serveur : ";
	private String Adresse, Context, Scheme, Servlet, Serveur, Erreur; 
	private StringBuffer sb = new StringBuffer();
	
	// ctor
	public Login (String[] arguments) {
		
		this.Scheme  = arguments[0];
		this.Adresse = arguments[1];
		this.Context = arguments[2];
        this.Servlet = arguments[3];
        this.Serveur = arguments[4];
        this.Erreur  = arguments[5];
		
	}
    
	// Page HTML login
	public StringBuffer GetPageLogin() {

		// <head>
		HTMLheader header = new HTMLheader("CRE - Transfert CRE inter-syst&egrave;me", "Groupe Pasteur Mutualit&eacute; - Version 1.0");
		sb.append(header.GetPageHeaderBase());
		
        // <body>
        sb.append("<body class=\"login\" onload=\"javascript:document.login.nom.focus();document.body.style.cursor='default';\">\r\n");
        sb.append("<img src=\"img/GPM_Logo_H_Couleur.png\" width=130 height=70 align=\"left\"> \r\n");
        sb.append("<br><br><br><br><br>\r\n");
        
        // <form>
        sb.append("<form name=\"" + "login" + "\"" + " onsubmit=\"" + "return ValiderForm(this.form)" + "\"" + 
                    " action=\"" + Scheme + Adresse + Context + Servlet + "\"" + " method=\"" + "post" + "\"" + ">");
        sb.append("<fieldset class=\"fieldset\"><legend class=\"legend\">" + TITRE + Serveur + "</legend>\r\n");
        sb.append("<br><br>\r\n");
           sb.append("<label for=\"nom\" class=\"login\">Utilisateur . . . . . . . . . . . . . . . . . . :</label>\r\n");
           sb.append("<input class=\"login\" type=\"text\" placeholder=\"Login AS/400\" id=\"nom\" name=\"nom\" size=\"20\" maxlength=\"20\" required/>\r\n");
           sb.append("<br><br>\r\n");
           
           sb.append("<label for=\"motdepasse\" class=\"login\">Mot de passe . . . . . . . . . . . . . . . :</label>\r\n");
           sb.append("<input class=\"login\" type=\"password\" placeholder=\"Password AS/400\" id=\"motdepasse\" name=\"motdepasse\"  size=\"20\" maxlength=\"20\" required/>\r\n");
           
           sb.append("<button class=\"btn btn-primary\" name=\"valider\" type=\"submit\"> \r\n");
           sb.append("<span class=\"glyphicon glyphicon-ok\"></span> Valider </button> \r\n");
           
           sb.append("<br><br><br>\r\n");
           
           if (Erreur.equals("Utilisateur obligatoire")) {
        	   sb.append("<span class=\"" +"erreur" + "\"" + ">" + Erreur.toString() + "</span>");
        	}
           if (Erreur.equals("Mot de passe obligatoire")) {
        	   sb.append("<span class=\"" +"erreur" + "\"" + ">" + Erreur.toString() + "</span>");
           }
           if (Erreur.equals("Profil IBM inconnu ou mot de passe incorrect")) {
        	   sb.append("<span class=\"" +"erreur" + "\"" + ">" + Erreur.toString() + "</span>");
           }
           
        sb.append(" </fieldset>");
        sb.append("</form>");
        sb.append("</body>\r\n");
        
        // Footer 
        sb.append(header.GetPageFooter());
        
        sb.append("</html>\r\n");
		
		return sb;
		
	}
	
}  // Fin class déconnexion
