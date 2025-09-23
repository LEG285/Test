<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
                    
<meta charset="utf-8"> 
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
        

<link rel="shortcut icon" type="image/x-icon" href="img/favicon.ico">
<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" media="screen">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/ui-lightness/jquery-ui.css\" type="text/css" media="screen">
<link rel="stylesheet" href="css/transfert-bootstrap.css" type="text/css" media="screen">
               
<script type="text/javascript" src="//code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript" src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>   
            
<!-- Si la version du navigateur est inférieure a IE 9,.... -->
<!--[if lt IE 9]>
<script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>
<script src=\"https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js\"></script>
<script src=\"https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js\"></script>
<![endif]-->		           
	    
<title>Page web GPM </title>
        
</head>  

<%-- <form action="/web-application/" method="POST">
<p><font color=red>${error_login}</font></p>
Prenom : <input name="prenom" value="Alex" type="text" />
Mot de Passe : <input name="password" value="admin" type="password" />
<input type="submit">

</form> --%>
       
<h2>Voici mon prénom : ${ prenom }</h2>

       
<body class="login" onload="javascript:document.login.nom.focus();document.body.style.cursor='default'">
<img src="img/GPM_Logo_H_Couleur.png" width=130 height=70 align="left">
<br><br><br><br><br>
        
<form name="login" action="http://localhost:8080/Gpm/Acceuil"  method="POST">
<fieldset class="fieldset"><legend class="legend">TITRE DE LA PAGE</legend>
<br><br>
<label for="nom" class="login">Utilisateur . . . . . . . . . . . . . . . . . . :</label>
<input class="login" type="text" placeholder="Login AS/400" id="nom" name="nom" size="20" maxlength="20" required/>
<br><br>
           
<label for="motdepasse" class="login">Mot de passe . . . . . . . . . . . . . . . :</label>
<input class="login" type="password" placeholder="Password AS/400" id="motdepasse" name="motdepasse"  size="20" maxlength="20" required/>
           
<button class="btn btn-primary" name="valider" type="submit">
<span class="glyphicon glyphicon-ok"></span> Valider </button>
           
<br><br><br>
           
<span class="erreur">${ error_login }</span> 

<%--
<span class="erreur">
<% 
      String erreur = (String) request.getAttribute("erreur_login");
      out.println( erreur );
%>
</span> 
--%>

</fieldset>

</form>

</body>
</html>