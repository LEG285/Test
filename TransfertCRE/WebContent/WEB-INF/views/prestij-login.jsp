<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-Language" content="fr;charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="shortcut icon"type="image/x-icon" href="favicon.ico"/>
  <link rel=stylesheet type="text/css" href="css/transfert.css" media="screen"/>
  <script type="text/javascript" src="js/transfert.js"></script>
  <title>PRESTIJ - Consultation des flux</title>
</head>
<body class="login" onload="javascript:document.login.nom.focus();document.body.style.cursor='default';">
<img src="img/logo_home.jpg" width=130 height=70 align="left"> 
<br><br><br><br><br>
<form name="login" onsubmit="return ValiderForm(this.form)" action="http://127.0.0.1:59932/transfert/transfert.html" method="post">
 <fieldset><legend>Connexion sur le serveur : cgamrec</legend>
   <br><br>
   <label for="nom" class="login">Utilisateur . . . . . . . . . . . . . . . . . . :</label>
   <input class="login" type="text" placeholder="Login AS/400" id="nom" name="nom" size="20" maxlength="10" required/>
   <input class="valider-login" type="submit" name="valider" value="VALIDER"/>     
   <br><br>
   <label for="motdepasse" class="login">Mot de passe . . . . . . . . . . . . . . . :</label>
   <input class="login" type="password" placeholder="Password AS/400" id="motdepasse" name="motdepasse"  size="20" maxlength="10" required/>
   <br><br><br>
 </fieldset> 
</form>
</body>
</html>