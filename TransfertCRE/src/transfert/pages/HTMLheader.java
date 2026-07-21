/**
 * **********************************************************************
 * Projet                 : PRESTIJ - AUTOMATISATION IJ   
 * Package                : transfert.pages
 * Class                  : HTMLheader 
 * ----------------------------------------------------------------------               
 * Objet                  : Chargement du <header> et <footer>
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 31 07 2018 
 *----------------------------------------------------------------------
 * \\apache-tomcat-7.0.69\work\Catalina\localhost\transfert\org\apache\jsp
 ********************************************************************* 
 */
package transfert.pages;

public class HTMLheader {

	private String Titre; 
	private String Pied;
	private StringBuffer sbh = new StringBuffer();
	private StringBuffer sbf = new StringBuffer();
	
	// ctor
	public HTMLheader (String titre, String pied) {
		this.Titre = titre;
		this.Pied = pied;
	}
    
	// HTML <header> BASE
	public StringBuffer GetPageHeaderBase() {

		// Vider le buffer
		sbh.delete(0, sbh.length());
		
		// <head>
		sbh.append("<!DOCTYPE html>");
        sbh.append("<html>");
        sbh.append("<head>");
                    
            // <meta>
            sbh.append("<meta charset=\"utf-8\">");
		    sbh.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
            sbh.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
          
            // <link>
            sbh.append("<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"img/icons8.png\">");
            sbh.append("<link rel=\"stylesheet\" href=\"bootstrap-3.3.7-dist/css/bootstrap.min.css\" type=\"text/css\" media=\"screen\">");
            sbh.append("<link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.12.1/themes/ui-lightness/jquery-ui.css\" type=\"text/css\" media=\"screen\">");
            sbh.append("<link rel=\"stylesheet\" href=\"css/transfert-bootstrap.css\" type=\"text/css\" media=\"screen\">");
               
            // <script>
            sbh.append("<script type=\"text/javascript\" src=\"//code.jquery.com/jquery-1.12.4.js\"></script>");
            sbh.append("<script type=\"text/javascript\" src=\"//code.jquery.com/ui/1.12.1/jquery-ui.js\"></script>");
            sbh.append("<script type=\"text/javascript\" src=\"bootstrap-3.3.7-dist/js/bootstrap.min.js\"></script>");   
            
 	        sbh.append("<!-- Si la version du navigateur est inférieure a IE 9,.... -->");
	        sbh.append("<!--[if lt IE 9]>");
	        sbh.append("  <script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>");
	        sbh.append("  <script src=\"https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js\"></script>");
	        sbh.append("  <script src=\"https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js\"></script>");
	        sbh.append("<![endif]-->");		           
	    
            sbh.append("<title>"  + Titre + "</title>");
        
        sbh.append("</head>\r\n");

		return sbh;
		 
	} 

	// HTML <header> CRE (Chargement pour page sélection CRE)
	public StringBuffer GetPageHeaderCRE(String SourceAutoCompletion) {

		// Vider le buffer
		sbh.delete(0, sbh.length());

		// <head> 
		sbh.append("<!DOCTYPE html>");
        sbh.append("<html>");
        sbh.append("<head>");
                     
            // <meta>
            sbh.append("<meta charset=\"utf-8\">");
		    sbh.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
            sbh.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        
            // <link> 
            sbh.append("<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"img/icons8.png\">");
            sbh.append("<link rel=\"stylesheet\" href=\"bootstrap-3.3.7-dist/css/bootstrap.min.css\" type=\"text/css\" media=\"screen\">");
            sbh.append("<link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.12.1/themes/ui-lightness/jquery-ui.css\" type=\"text/css\" media=\"screen\">");
            sbh.append("<link rel=\"stylesheet\" href=\"css/transfert-bootstrap.css\" type=\"text/css\" media=\"screen\">");
            sbh.append("<link rel=\"stylesheet\" href=\"css/datatables.min.css\" type=\"text/css\" media=\"screen\">");
  
            // <script> 
            sbh.append("<script src=\"//code.jquery.com/jquery-1.12.4.js\"></script>");
            sbh.append("<script src=\"//code.jquery.com/ui/1.12.1/jquery-ui.js\"></script>");
            sbh.append("<script src=\"bootstrap-3.3.7-dist/js/bootstrap.min.js\"></script>");   
            
            sbh.append("<script type=\"text/javascript\" src=\"js/datatables.js\"></script>");
            sbh.append("<script type=\"text/javascript\" src=\"js/transfert.js\"></script>");
            sbh.append("<script type=\"text/javascript\" src=\"js/plug-in-jquery.js\"></script>");

            // Initialisation script autocompletion
            sbh.append("<script type=\"text/javascript\">");
            sbh.append("$(function() {");
            sbh.append(SourceAutoCompletion + ";");
            sbh.append("$(\"#typecou\").autocomplete({");
            sbh.append("source: liste");		 
    				   //minLength: 1
            sbh.append("});});");
    		sbh.append("</script>");    			
            // Fin Initialisation script autocompletion
    		
 	        sbh.append("<!-- Si la version du navigateur est inférieure a IE 9,.... -->");
	        sbh.append("<!--[if lt IE 9]>");
	        sbh.append("  <script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>");
	        sbh.append("  <script src=\"https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js\"></script>");
	        sbh.append("  <script src=\"https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js\"></script>");
	        sbh.append("<![endif]-->");		           
	    
            sbh.append("<title>"  + Titre + "</title>");
        
        sbh.append("</head>\r\n");

		return sbh;
		 
	} 

	// HTML <footer>
	public StringBuffer GetPageFooter() {
		
		// Vider le buffer
		sbf.delete(0, sbh.length());

		// <footer>
		sbf.append("<footer class=\"footer\">");
        sbf.append("<div class=\"footer\">");
        //sbf.append("<h3>Groupe Pasteur Mutualité - Version 1.0</h3>");
        sbf.append(Pied);    
        sbf.append("</div>");
        sbf.append("</footer>\r\n");
        
        return sbf;
	}       
 	
}  // Fin class 

