<!DOCTYPE html>
<html>
<head>
    <title>PRESTIJ - Consultation des flux</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/ui-lightness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.12.4.js"></script>
    <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" type="text/css" href="css/transfert-bpij.css" media="screen" />
    <script type="text/javascript" src="js/transfert.js"></script>
</head>

<body class="bpij" onload="javascript:document.formulaireBPIJ.idflux.focus();">
    <img src="img/logo_home.jpg" width="120" height="60" align="left">

    <script>
        document.writeln('<h3 align="center">Consultation détail du BPIJ au ' + DateJour + '</h3><br>');
    </script>

    <!-- Partie 1 Affichage -->
    <div class="bpij-div1">

          <input class="div1-s2" type="button" value="Retour menu" />

          <input class="div1-s2" type="button" value="Page précédente"/>
          
          <a class="deconnexion" href="http://localhost:8080/hcr.HcrDeconnexion">Déconnexion</a>

          <br><br>
          <fieldset>
               
                <!-- IDflux -->
                <label for="idflux" class="div1-la1">IDflux</label>
                <label class="div1-laa">20180518522125fdfddfd</label>
                
                <!-- IDfichier -->
                <label for="idfichier" >IDfichier</label>
                <label class="div1-laa">2018051899999ds</label>
                
                <!-- Assuré -->
                <br><br><label for="assure" class="div1-la1">N° assuré</label>
                <label class="div1-laa">2452578</label>
                
                <!-- Numero SS -->
                <label for="nss">Numéro SS</label>
                <label class="div1-laa">1610175111044</label>
                
                <!-- Nom -->
                <br><br><label for="nom" class="div1-la1">Nom</label>
                <label class="div1-laa">DUPOND DE NEMOURS</label>
                
                <!-- Prenom -->
                <label for="prenom">Prénom</label>
                <label for="prenom" class="div1-laa">ALFRED GAETAN RENE</label>
                
                <!-- Siren / Siret -->
                <br><br><label for="siren" class="div1-la1">SIREN</label>
                <label class="div1-laa">4217220404</label>

                <!-- Raison sociale  -->
                <label for="raisoc">Raison sociale</label>
                <label class="div1-laa">STE DE LA MOTTE PICQUET</label>
                
           </fieldset>

           <fieldset>
               
                <label for="nature" class="div1-la1">Nature Assurance</label>
                <label class="div1-laa">MA</label>
                
                <label for="statut">Statut intégration</label>
                <label class="div1-laa">EN_GEDXXXX</label>
                
                <br><br><label for="ano" class="div1-la1">Anomalie détectée</label>
                <label class="div1-laa">ANO - Anomalie type ANO</label>
                
                <br><br><label for="periode" class="div1-la1">Période du</label>
                <label class="div1-laa">12-03-2018</label>

                <label for="periode">au</label>
                <label class="div1-laa" >07-05-2018</label>
                
                <br><br><label for="montant" class="div1-la1">Montant Total</label>
                <label class="div1-laa">1589,58</label>
                
                <br><span class="info">Message : Critères de sélection incorrectes !</span>
                
           </fieldset>

    </div>

    <!--       Partie 2 - DÃ©tail BPIJ   -->
    
    <!-- En-tete -->
    <div align="center" class="bpij2-div2">
        <table class="bpij2-div2-table1">
            <tr>
                <td class="b2-h-cp">Code Prest</td>
                <td class="b2-h-lp">Libellé Prestation</td>
                <td class="b2-h-dp">Date Debut Prest</td>
                <td class="b2-h-dp">Date Fin Prest</td>
                <td class="b2-h-sin">N° Sinistre</td>
                <td class="b2-h-nbij">Nb IJ</td>
                <td class="b2-h-ijsub">IJSub</td>
                <td class="b2-h-pu">PU</td>
                <td class="b2-h-mt">Montant</td>
            </tr>
        </table>
    </div>

    <!-- DÃ©tail -->
    <div align="center" class="bpij2-div3" style="height:100px;">
        <table class="bpij2-div3-table2" style="height:100px;">
          
           <!-- Lignes dÃ©tail -->
            <tr>
                <td class="b2-d-cp">ijnor</td>
                <td class="b2-d-lp">X</td>
                <td class="b2-d-dp">X</td>
                <td class="b2-d-dp">X</td>
                <td class="b2-d-sin">X</td>
                <td class="b2-d-nbij">X</td>
                <td class="b2-d-ijsub">X</td>
                <td class="b2-d-pu">X</td>
                <td class="b2-d-mt">X</td>
            </tr>
         
            <tr>
                <td class="b2-d-cp">IJNORX</td>
                <td class="b2-d-lp">RETENUE CONTRIB. REMBOURSEMENT DETTE SOCIALExxxx</td>
                <td class="b2-d-dp">15-01-2018</td>
                <td class="b2-d-dp">15-01-2018</td>
                <td class="b2-d-sin">NUMSIN123</td>
                <td class="b2-d-nbij">987</td>
                <td class="b2-d-ijsub">IJSUB</td>
                <td class="b2-d-pu">1234567,89</td>
                <td class="b2-d-mt">1234567,89</td>
            </tr>
            
            <tr>
                <td class="b2-d-cp">IJNORX</td>
                <td class="b2-d-lp">RETENUE CONTRIB. REMBOURSEMENT DETTE SOCIALExxxx</td>
                <td class="b2-d-dp">15-01-2018</td>
                <td class="b2-d-dp">15-01-2018</td>
                <td class="b2-d-sin">NUMSIN123</td>
                <td class="b2-d-nbij">987</td>
                <td class="b2-d-ijsub">IJSUB</td>
                <td class="b2-d-pu">1234567,89</td>
                <td class="b2-d-mt">1234567,89</td>
            </tr>

            <tr>
                <td class="b2-d-cp">IJNORX</td>
                <td class="b2-d-lp">RETENUE CONTRIB. REMBOURSEMENT DETTE SOCIALExxxx</td>
                <td class="b2-d-dp">15-01-2018</td>
                <td class="b2-d-dp">15-01-2018</td>
                <td class="b2-d-sin">NUMSIN123</td>
                <td class="b2-d-nbij">987</td>
                <td class="b2-d-ijsub">IJSUB</td>
                <td class="b2-d-pu">1234567,89</td>
                <td class="b2-d-mt">1234567,89</td>
            </tr>
       
            <tr>
                <td class="b2-d-cp">IJNORX</td>
                <td class="b2-d-lp">RETENUE CONTRIB. REMBOURSEMENT DETTE SOCIALExxxx</td>
                <td class="b2-d-dp">15-01-2018</td>
                <td class="b2-d-dp">15-01-2018</td>
                <td class="b2-d-sin">NUMSIN123</td>
                <td class="b2-d-nbij">987</td>
                <td class="b2-d-ijsub">IJSUB</td>
                <td class="b2-d-pu">1234567,89</td>
                <td class="b2-d-mt">1234567,89</td>
            </tr>
       
        </table>
    </div>    
    
</body>
</html>