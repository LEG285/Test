/**
 * **********************************************************************
 * Projet                 : Transfert CRE    
 * Package                : transfert.pages
 * Class                  : PageFormulaire.java 
 *----------------------------------------------------------------------               
 * Objet                  : Page HTML PageFormulaire   
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 31 07 2018
 *----------------------------------------------------------------------
 * Chemin des class jsp   :
 * \\apache-tomcat-7.0.69\work\Catalina\localhost\transfert\org\apache\jsp
 ********************************************************************* 
 */
package transfert.pages;

public class PageFormulaire {
	
	private String Adresse, Context, Scheme, Servlet, Serveur, Erreur, lienQuitter; 
	private StringBuffer sb = new StringBuffer();
	private String Param;
	
	// ctor
	public PageFormulaire (String[] arguments) {
		this.Scheme  = arguments[0];
		this.Adresse = arguments[1];
		this.Context = arguments[2];
        this.Serveur = arguments[3];
        this.lienQuitter = arguments[4];
	}	

	/**
	 * @see HcrLoginHtmlPage(HttpServletRequest res, String Erreur)
	 */
	public StringBuffer GetPageFormulaire(String Erreur, String TypeErreur) {
		
		// Initialisation etat de la page HTML
		boolean EtatPageHTML = Erreur.isEmpty();
        String chaine = "var liste = [" + "\"" + "!ERREUR!" + "\"" + "]";  // TODO A initialiser ???
		
		// Affichage page HTML

		//	PrintWriter out;
		//	out = res.getWriter();

			// Flux HTML
			// <head>
			//sb.append(header.GetPageHeaderCRE(chaine).toString());
		      
			// <head>
			HTMLheader header = new HTMLheader("CRE - Transfert CRE inter-syst&egrave;me",
            "&copy; Groupe Pasteur Mutualit&eacute; - Version 1.1");			
  		    sb.append(header.GetPageHeaderCRE(chaine));
		
			// <body>
		    sb.append("<body class=\"import\" onload=\"javascript:document.formulairegestip.cass.focus();\">\r\n");
		    sb.append("<img src=\"img/GPM1.jpg\" width=120 height=120 align=\"left\">\r\n");
		    sb.append("<script>\r\n");
		    sb.append("document.writeln('<h3 align=\"center\">S&eacutelection pour transfert CRE au ' + DateJour +'</h3><br>');\r\n");
		    sb.append("</script>\r\n");

		    // Partie Formulaire
		    sb.append("<div class=\"gestip-div1\">\r\n");
		    sb.append("<form name=\"formulairegestip\" id=\"myform\" onsubmit=\"return ValidationGlobale(this.form)\">\r\n");
		     
		    // Bouton Quitter
			sb.append("  <button class=\"btn btn-danger btn-sm\" type=\"button\" "
				        + "  onClick=\"location.href='" + Scheme + Adresse + Context + lienQuitter + "';\">");
			sb.append("  <span class=\"glyphicon glyphicon-off\"></span>");
			sb.append(" D&eacute;connexion");
			sb.append("  </button>");
		    
			// ID CRE D�but ----------------------------------------------------------------------
            sb.append("<br><br>");  
		    sb.append("<fieldset class=\"fieldset\"><legend class=\"legend\">ID CRE</legend>\r\n");
		    sb.append("  <label for=\"assure\" class=\"gestip-div1-la1\" >N&deg; CRE &agrave; transf&eacute;rer :</label>\r\n");
		      
		    sb.append("  <textarea id=\"gt1\" class=\"gestip-div1-i\" textarea name=\"cass\" rows=\"10\" cols=\"9\" maxlength=\"102\" \r\n");
		    sb.append("  onblur=\"codeAssure('blur')\" onfocus=\"codeAssure('focus')\" onkeypress=\"VerificationCar(event);\">\r\n");
		    sb.append("  </textarea>&nbsp;&nbsp;&nbsp;&nbsp;\r\n");  

		    sb.append("  <textarea id=\"gt2\" class=\"gestip-div1-i\" textarea name=\"cass2\" rows=\"10\" cols=\"9\" maxlength=\"102\" \r\n");
		    sb.append("  onblur=\"codeAssure2('blur')\" onfocus=\"codeAssure2('focus')\" onkeypress=\"VerificationCar(event);\">\r\n");
		    sb.append("  </textarea>\r\n");  
		      
		    sb.append("</fieldset>\r\n"); 
		    // ID CRE Fin -----------------------------------------------------------------------

		    // Autres critères Début ------------------------------------------------------------
		    sb.append("<br>");
		    sb.append("<fieldset class=\"fieldset\"><legend class=\"legend\">Autres crit&egrave;res</legend>");
		      
            // Type courrier 
            sb.append("<label for=\"idtype\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Type courrier :&nbsp;&nbsp;</label>\r\n");
            sb.append("<input id=\"typecou\" class=\"gestip-div1-1\" type=\"text\" name=\"typeCou\" size=\"8\" maxlength=\"8\" onfocus=\"typeCourrier('focus')\" onblur=\"typeCourrier('blur')\" onkeypress=\"VerificationCar(event);\" >\r\n");		      
		      
            // Adherent 
            sb.append("<label for=\"idadh\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Code adh&eacute;rent :</label>\r\n");
            sb.append("<input id=\"codeadh\" class=\"gestip-div1-1\" type=\"text\" name=\"codeAdh\" size=\"6\" maxlength=\"6\" onfocus=\"codeAdherent('focus')\" onblur=\"codeAdherent('blur')\" onkeypress=\"VerificationCar(event);\"/>\r\n");
		        
            // Date CRE 
            sb.append("<label for=\"periode\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date CRE :</label>\r\n");
            sb.append("<input id=\"datecr\" class=\"gestip-div1-1\" type=\"text\" name=\"dateCr\" onblur=\"dateCRE('blur')\" onfocus=\"dateCRE('focus')\" onkeypress=\"VerificationCar(event);\"/>\r\n");  
		      
            sb.append("</fieldset>\r\n"); 
		    // Autres critères Fin ------------------------------------------------------------		      
		    
		    // Environnement  ----------------------------------------------------------------------
		    sb.append("<br><br>"); 
			sb.append("<fieldset class=\"fieldset\"><legend class=\"legend\">Environnements</legend>\r\n");
	          
			// Liste sytème source ------------------------
		    sb.append("<label for=\"statut\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Depuis syst&egrave;me source :&nbsp;</label>\r\n");
		    sb.append("<select name=\"stat1\" id=\"ListSource\" class=\"gestip-div1-i\" "
		            		+ "onblur=\"source('blur')\" onfocus=\"source('focus')\" onchange=\"source('blur');\">");
	        sb.append("<option selected=\"selected\"></option>");
	        sb.append("<option>PRODUCTION</option>");
	        sb.append("<option>RECETTE</option>");
	        sb.append("<option>DEV</option>");
	        sb.append("</select>"); 			    

			// Liste système cible ------------------------
		    sb.append("<label for=\"statut\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Vers syst&egrave;me cible :&nbsp;&nbsp;</label>\r\n");
		    sb.append(" <select name=\"stat2\" id=\"ListCible\" onblur=\"cible('blur')\" class=\"gestip-div1-i\" "
		                + "onfocus=\"cible('focus')\" onchange=\"cible('blur');\" >");
            sb.append("<option selected=\"selected\"></option>");
            sb.append("<option>RECETTE</option>");
            sb.append("<option>RECETTE et DEV</option>");
            sb.append("<option>DEV</option>");
            sb.append("</select>"); 
			   
		    // Valider Formulaire                            
	        sb.append("<button class=\"btn btn-primary\" name=\"entrer\" type=\"submit\"> \r\n");
	        sb.append("  <span class=\"glyphicon glyphicon-ok\"></span>");
	        sb.append(" Valider ");
			sb.append("</button>");   
		    sb.append("<br>\r\n");
		      
		    sb.append("</fieldset>");

	        // Message après traitement  
		    if (TypeErreur.equals("I")) {sb.append("<span id=\"Msg\" class=\"" + "info" + "\"" + ">"   + Erreur.toString() + "</span>");}
            if (TypeErreur.equals("E")) {sb.append("<span id=\"Msg\" class=\"" + "erreur" + "\"" + ">" + Erreur.toString() + "</span>");}
		    if (TypeErreur.equals("A")) {sb.append("<span id=\"Msg\" class=\"" + "info" + "\"" + ">" + Erreur.toString() + "</span>");}
		    if (Erreur.isEmpty())       {sb.append("<span id=\"Msg\" class=\"" +"erreur" + "\"" + ">&nbsp;&nbsp</span>");}
		    
		    //	sb.append("<script>\r\n");
		    //	sb.append("alert(" + "\"" + Erreur.toString() + "\"" + ")");
		    //	sb.append("</script>\r\n");
		    // }
		    
		    sb.append("</form>\r\n");
		    sb.append("</div>\r\n");			
		    sb.append("</body>\r\n");
		    
		    // Pied
			sb.append(header.GetPageFooter().toString());

			sb.append("</html>\r\n");

			//out.flush();
			//out.close();

		return sb;

	} // end of CREHtmlPageResultat
	
}
