<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html>
<html>
<head>
  <title>PRESTIJ - Consultation des flux</title>
  <meta charset="utf-8">    
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/ui-lightness/jquery-ui.css">
  <link rel=stylesheet type="text/css" href="css/transfert.css" media="screen"/>
  <script src="//code.jquery.com/jquery-1.12.4.js"></script>  
  <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>    
  <script type="text/javascript" src="js/transfert.js"></script>
</head>

<body class="import" onload="javascript:document.formulairegestip.cass.focus();">
<img src="img/logo_home.jpg" width=120 height=60 align="left">
    
<script>    
document.writeln('<h3 align="center">Recherche et consultation des flux GESTIP au ' + DateJour +'</h3><br>');
</script>   
    
    <!-- Partie 1 Formulaire -->
    <div class="gestip-div1">
    
       <form name="formulairegestip" id="myform" onsubmit="return ValidationGlobale(this.form)">

          <fieldset><legend>RÃ©fÃ©rence</legend>  
              
              <!-- 1Ã¨re ligne Code Assure / Boutons -->
              <label for="assure" class="gestip-div1-la1">NÂ° assurÃ©</label>                
              <input id="gt1" class="gestip-div1-i" type="text" name="cass" 
                      size="7" maxlength="7" onblur="codeAssure('blur')" onfocus="codeAssure('focus')" onkeypress="VerificationCar(event);"/>

               
              <!-- 2Ã¨me ligne NumÃ©ro SS -->
              <br><label for="nss" class="gestip-div1-la1">NumÃ©ro SS</label>
              <input id="gt2" class="gestip-div1-i" type="text"  name="nss" size="13" 
                     maxlength="13" onfocus="numeroSS('focus')" onblur="numeroSS('blur')" onkeypress="VerificationCar(event);"/>

              <!-- 3Ã¨me ligne Nom -->
              <br><label for="nom" class="gestip-div1-la1">Nom</label>
              <input id="gt3" class="gestip-div1-i" type="text" name="nom" size="50" 
                     maxlength="50" onblur="nomAssure('blur')" onfocus="nomAssure('focus')" onkeypress="VerificationCar(event);"/>

              <!-- 4Ã¨me ligne Prenom -->
              <br><label for="prenom" class="gestip-div1-la1">PrÃ©nom</label>
              <input id="gt4" class="gestip-div1-i" type="text" name="prenom" size="50" 
                     maxlength="50" onblur="prenomAssure('blur')" onfocus="prenomAssure('focus')" onkeypress="VerificationCar(event);"/>
          </fieldset>
           
          <br>
          <fieldset>
              <label for="siren" class="gestip-div1-la1">Siren/Siret</label>
              <input id="gt5" class="gestip-div1-i" type="text"  name="siret" size="14"
                maxlength="14" onblur="siren('blur')" onfocus="siren('focus')" onkeypress="VerificationCar(event);"/>
          </fieldset>
          
          <br> 
          <fieldset>
             
              <label for="periode" class="gestip-div1-la1">PÃ©riode du</label>
              <input id="datedebut6" class="gestip-div1-i" type="text" name="debut"
                     onblur="dateDebut('blur')" onfocus="dateDebut('focus')" onkeypress="VerificationCar(event);"/>
              
              <label for="periode2">au</label>
              
              <input id="datefin7" class="gestip-div1-i" type="text" name="fin"
                     onblur="dateFin('blur')" onfocus="dateFin('focus')" onkeypress="VerificationCar(event);"/>
              
              <label for="statut">&nbsp;&nbsp;&nbsp;&nbsp;Statut&nbsp;&nbsp;</label>
              
              <input id="gt8" class="gestip-div1-i" type="text" name="stat" size="50" maxlength="50"                      
                     onfocus="statut('focus')" onblur="statut('blur')" onkeypress="VerificationCar(event);"/>
               
              <!-- Boutons validation -->   
              <input class="gestip-div1-s1" type="submit" name="entrer" value="Valider" />
              <input class="gestip-div1-s2" type="button" value="Retour menu" onClick="window.location.href=\'http:http://localhost:8080/hcr.HcrDeconnexion"/>
              
              <a class="deconnexion" href="http://localhost:8080/hcr.HcrDeconnexion">DÃ©connexion</a>
              <br><br>
           </fieldset>
           
        </form>
        
    </div>
    
    <!-- Partie 2 Liste -->
    <!-- En-tete -->
    <div align="center" class="gestip-div2">
        <table class="gestip-div2-table1" >
            <tr>
                <td class="g-h-assu">AssurÃ©</td>
                <td class="g-h-ss">NÂ° SS</td>                
                <td class="g-h-nom">Nom / PrÃ©nom</td>
                <td class="g-h-demi">Demande Inscription</td>                
                <td class="g-h-dtdem">Date Demande</td>                
                <td class="g-h-stdem">Statut demande</td>
                <td class="g-h-nivrj">Niveau rejet</td>
                <td class="g-h-librj">Code et LibellÃ© rejet</td> 
            </tr>
        </table>
    </div>
    
    <!-- DÃ©tail ligne assurÃ© -->
    <div align="center" class="gestip-div3">
        <table class="gestip-div3-table2" >
            
            <c:forEach items="${liste}" var="liste" > 
             <tr>            
                         
            </c:forEach>
            
            <!-- Lignes assurÃ© 1 -->
            <tr>    
                <td class="g-d-assu">6501245</td>
                <td class="g-d-ss">1610175111044</td>                
                <td class="g-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="g-d-demi">ADCOL-2018022311292531111</td>                
                <td class="g-d-dtdem">15-01-2018</td>                
                <td class="g-d-stdem">AcceptÃ©e</td>
                <td class="g-d-nivrj">ARL</td>
                <td class="g-d-librj">ERR_SUPCRE01 : LâopÃ©ration SUPCRE nâest pas compatible avec lâÃ©lÃ©ment IP.</td>
            </tr>

            <tr>    
                <td class="g-d-assu"></td>
                <td class="g-d-ss"></td>                
                <td class="g-d-nom">RAVI CHINTHAKA</td>
                <td class="g-d-demi">IP/Nom dÃ©lÃ©gataire de gestion/Type demande ip</td>                
                <td class="g-d-dtdem"></td>                
                <td class="g-d-stdem"></td>
                <td class="g-d-nivrj"></td>
                <td class="g-d-librj"></td>                
            </tr>
            
            <tr>    
                <td class="g-d-assu"></td>
                <td class="g-d-ss"></td>                
                <td class="g-d-nom"></td>
                <td class="g-d-demi">SIREN/NumÃ©ro de siren/Type demande entreprise(CRE/SUP/SUPCRE)</td>                
                <td class="g-d-dtdem"></td>                
                <td class="g-d-stdem"></td>
                <td class="g-d-nivrj"></td>
                <td class="g-d-librj"></td>                
            </tr>            
            
            <tr>    
                <td class="g-d-assu"></td>
                <td class="g-d-ss"></td>                
                <td class="g-d-nom"></td>
                <td class="g-d-demi">NIR/NÂ°SS/Type demande salariÃ©(CRE/SUP/SUPCRE)</td>                
                <td class="g-d-dtdem"></td>                
                <td class="g-d-stdem"></td>
                <td class="g-d-nivrj"></td>
                <td class="g-d-librj"></td>                
            </tr>            
            
            <tr>    
                <td class="g-d-assu-fin"></td>
                <td class="g-d-ss-fin"></td>                
                <td class="g-d-nom-fin"></td>
                <td class="g-d-demi-fin">IDCOUVERTURE/Type demande couv(CRE/SUP/SUPCRE)Date dÃ©but/Date fin Date rÃ©tro</td>                
                <td class="g-d-dtdem-fin"></td>                
                <td class="g-d-stdem-fin"></td>
                <td class="g-d-nivrj-fin"></td>
                <td class="g-d-librj-fin"></td>                
            </tr>            
            
            <!-- Lignes assurÃ© 2 -->
            <tr>    
                <td class="g-d-assu">6501245</td>
                <td class="g-d-ss">1610175111044</td>                
                <td class="g-d-nom">COLAPINTO </td>
                <td class="g-d-demi">IDFLUX / IDFICHIER</td>                
                <td class="g-d-dtdem">15-01-2018</td>                
                <td class="g-d-stdem">AcceptÃ©e</td>
                <td class="g-d-nivrj">ARL</td>
                <td class="g-d-librj">ERR_COHERENCE_CRE : Pour une opÃ©ration de type CRE, lâÃ©lÃ©ment enfant doit avoir...</td>
            </tr>

            <tr>    
                <td class="g-d-assu"></td>
                <td class="g-d-ss"></td>                
                <td class="g-d-nom">CHRISTINE</td>
                <td class="g-d-demi">IP/Nom dÃ©lÃ©gataire de gestion/Type demande ip</td>                
                <td class="g-d-dtdem"></td>                
                <td class="g-d-stdem"></td>
                <td class="g-d-nivrj"></td>
                <td class="g-d-librj"></td>                
            </tr>            
             
            <tr>    
                <td class="g-d-assu"></td>
                <td class="g-d-ss"></td>                
                <td class="g-d-nom"></td>
                <td class="g-d-demi">SIREN/NumÃ©ro de siren/Type demande entreprise(CRE/SUP/SUPCRE)</td>                
                <td class="g-d-dtdem"></td>                
                <td class="g-d-stdem"></td>
                <td class="g-d-nivrj"></td>
                <td class="g-d-librj"></td>                
            </tr>            
            
            <tr>    
                <td class="g-d-assu"></td>
                <td class="g-d-ss"></td>                
                <td class="g-d-nom"></td>
                <td class="g-d-demi">NIR/NÂ°SS/Type demande salariÃ©(CRE/SUP/SUPCRE)</td>                
                <td class="g-d-dtdem"></td>                
                <td class="g-d-stdem"></td>
                <td class="g-d-nivrj"></td>
                <td class="g-d-librj"></td>                
            </tr>            
            
            <tr>    
                <td class="g-d-assu-fin"></td>
                <td class="g-d-ss-fin"></td>                
                <td class="g-d-nom-fin"></td>
                <td class="g-d-demi-fin">IDCOUVERTURE/Type demande couv(CRE/SUP/SUPCRE)Date dÃ©but/Date fin Date rÃ©tro</td>                
                <td class="g-d-dtdem-fin"></td>                
                <td class="g-d-stdem-fin"></td>
                <td class="g-d-nivrj-fin"></td>
                <td class="g-d-librj-fin"></td>                
            </tr>              
           
        </table>
    </div>
</body>
</html>