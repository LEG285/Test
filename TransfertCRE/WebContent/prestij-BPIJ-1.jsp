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
        document.writeln('<h3 align="center">Recherche des flux BPIJ au ' + DateJour + '</h3><br>');
    </script>

    <!-- Partie 1 Formulaire -->
    <div class="bpij-div1">

        <form name="formulaireBPIJ" id="myform" onsubmit="return ValidationGlobale_BPIJ(this.form)">

            <input class="div1-s2" type="button" value="Retour menu" onClick="window.location.href="/>

            <a class="deconnexion" href="http://localhost:8080/hcr.HcrDeconnexion">Déconnexion</a>

            <br><br>
            <fieldset>
                <legend>Référence</legend>

                <!-- IDflux -->
                <label for="idflux" class="div1-la1">IDflux</label>
                <input id="bt1" class="div1-i" type="text" name="idflux" size="25" maxlength="25" onblur="idflux_B('blur')" onfocus="idflux_B('focus')" onkeypress="VerificationCar(event);" />

                <!-- IDfichier -->
                <label for="idfichier">IDfichier</label>
                <input id="bt2" class="div1-i" type="text" name="idfichier" size="25" maxlength="25" onfocus="idfichier_B('focus')" onblur="idfichier_B('blur')" onkeypress="VerificationCar(event);" />

                <!-- Assuré -->
                <br><label for="assure" class="div1-la1">N° assuré</label>
                <input id="bt3" class="div1-i" type="text" name="cass" size="7" maxlength="7" onblur="codeAssure_B('blur')" onfocus="codeAssure_B('focus')" onkeypress="VerificationCar(event);" />

                <!-- Numero SS -->
                <label for="nss">Numéro SS</label>
                <input id="bt4" class="div1-i" type="text" name="nss" size="13" maxlength="13" onfocus="numeroSS_B('focus')" onblur="numeroSS_B('blur')" onkeypress="VerificationCar(event);" />

                <!-- Nom -->
                <br><label for="nom" class="div1-la1">Nom</label>
                <input id="bt5" class="div1-i" type="text" name="nom" size="50" maxlength="50" onblur="nomAssure_B('blur')" onfocus="nomAssure_B('focus')" onkeypress="VerificationCar(event);" />

                <!-- Prenom -->
                <label for="prenom">Prénom</label>
                <input id="bt6" class="div1-i" type="text" name="prenom" size="50" maxlength="50" onblur="prenomAssure_B('blur')" onfocus="prenomAssure_B('focus')" onkeypress="VerificationCar(event);" />
            </fieldset>

            <fieldset>

                <label for="nature" class="div1-la1">Nature Assurance</label>
                <select name="nat" id="ListNature" class="div1-i" onblur="nature_B('blur')" onfocus="nature_B('focus')" onchange="nature_B('blur');">
                    <option selected="selected">TOUS</option>
                    <option>ADO - I.J. ADOPTION</option>   
                    <option>ARA - Allocation maternité réduite pour adoption</option>
                    <option>ARM Allocation forfaitaire repos maternel</option>
                    <option>ASM I.J MALADIE MAJOREE + 6 MOIS</option>
                    <option>ASN I.J MALADIE NORMALE + 6 MOIS</option>
                    <option>AVP Alloc. accomp. fin de Vie cessa. act. temps Pl.</option>
                    <option>AVR Alloc. Accomp. fin de Vie cessation act. Réd.</option>
                    <option>CAR CARENCE</option>
                    <option>CIJ COMPLEMENT IJ > PLAFOND -CRPCEN-</option>
                    <option>CIN CONSTAT D'INDU</option>
                    <option>CUM	I.J.MAJOREES CURE</option>
                    <option>CUN	I.J. NORMALES CURE</option>
                    <option>CUR	I.J.REDUITES CURE (RENTE)</option>
                    <option>EEN	ALLOCATION EXPOSITION ARRET + 6 MOIS ET 3 ENFANTS</option>
                    <option>EME	ALLOCATION EXPOSITION MAJOREE 3 ENFANTS</option>
                    <option>EMN	ALLOCATION EXPOSITION ARRET + 6 MOIS </option>
                    <option>ENO	ALLOCATION EXPOSITION NORMALE</option>
                    <option>IPA	INDEMNITE PATERNITE PAMC</option>
                    <option>IPC	INDEMNITE PATERNITE CONJOINT PAMC</option>
                    <option>IPD	FORF.24H>DUREE>12</option>
                    <option>IPI	INDEMNITE PATERNITE CONJOINT INFIRMIER</option>
                    <option>IPS	PERTE DE SALAIRE</option>
                    <option>IRA	INDEM.REMPL.MATER REDUITE(ADOPTION)</option>
                    <option>IRC	INDEM. DE REMPL.CJTES.COLLABORATRICES</option>
                    <option>IRG	MAJOR.INDEM.REMPL.MATER(A.GEMELL.)</option>
                    <option>IRM INDEMN.REMPLACEMENT MATER NORMALE </option>
                    <option>IRP MAJOR.INDEM.REMPL.MATER(ET PATHOL)</option>
                    <option>ISM	IJ SUPPLEMENTAIRE MATERNITE</option>
                    <option>ITI INDEMNITE TEMPORAIRE D'INAPTITUDE</option>
                    <option>IJMAJ I.J. MAJOREES</option>                                                     
                    <option>MIJ	I.J. MINIMUM  MAJOREE</option>
                    <option>MIN	I.J. MINIMUM  NORMALE </option>
                    <option>MIT	I.J. MI-TEMPS</option>
                    <option>NEN	ALLOCATION NUIT MAJOREE ARRET + 6 MOIS ET 3 ENFANTS</option>
                    <option>NME ALLOCATION NUIT MAJOREE 3 ENFANTS</option>
                    <option>NMN ALLOCATION NUIT MAJOREE ARRET + 6 MOIS</option>
                    <option>NNO ALLOCATION NUIT NORMALE</option>
                    <option>IJNOR I.J. NORMALES</option>
                    <option>PER	I.J. PATERNITE</option>
                    <option>POS I.J. POSNATALES</option>
                    <option>PRE I.J. PRENATALES</option>
                    <option>REN I.J. REDUITES POUR RENTE</option>
                    <option>RPR RECUPERATION INDU</option>
                    <option>RETCRD RETENUE R.D.S.</option>
                    <option>RETCSJ RETENUE C.S.G.</option>
                    <option>RETRCJ RETENUE REGUL.CONTRIB. SOCIALE GENERALISEE </option>    
                    <option>RETRRD RETENUE REGUL. REMBOURSEMENT DETTE SOCIALE</option>
                    <option>REGRCJ REGULARISATION REGUL.CONTRIB. SOCIALE GENERALISEE</option>
                    <option>REGRRD REGULARISATION REGUL.REMBOURSEMENT DETTE SOCIALE</option>
              </select>

                <label for="periode">Période du</label>
                <input id="Bdatedebut" class="div1-i" type="text" name="Bdebut" onblur="dateDebut_B('blur')" onfocus="dateDebut_B('focus')" onkeypress="VerificationCar(event);" />

                <label for="periode2">au</label>
                <input id="Bdatefin" class="div1-i" type="text" name="Bfin" onblur="dateFin_B('blur')" onfocus="dateFin_B('focus')" onkeypress="VerificationCar(event);" />

                <!-- Boutons validation -->
                <input class="div1-s1" type="submit" name="entrer" value="Valider" />

                <br><span class="info">Critères de sélection incorrectes !</span>
            </fieldset>

        </form>

    </div>

    <!--       Partie 2 - niveau 1 DÃ©tail IDFLUX     -->
    
    <!-- En-tete niveau 1-->
    <div align="center" class="bpij-div2">
        <table class="bpij-div2-table1">
            <tr>
                <td class="b-h-idflux">IDflux</td>
                <td class="b-h-idfichier">IDfichier</td>
                <td class="b-h-dtrans">Date transfert</td>
                <td class="b-h-ip">IP libellé</td>
                <td class="b-h-siren">Siren déclarant</td>
            </tr>
        </table>
    </div>

    <!-- ligne IDFLUX niveau 1 -->
    <div align="center" class="bpij-div3" style="height:40px;">
        <table class="bpij-div3-table2" style="height:40px;">
            <tr>
                <td class="b-d-idflux">1234567890123456789012345</td>
                <td class="b-d-idfichier">1234567890123456789012345</td>
                <td class="b-d-dtrans">15-01-2018</td>
                <td class="b-d-ip">123456789012345</td>
                <td class="b-d-siren">12345678901234</td>
            </tr>
        </table>
    </div>
    
    <!--       Partie 2 - niveau 2 DÃ©tail IDfichier     -->
    <!-- En-tete niveau 2-->
    <div align="center" class="bpij-div2">
        <table class="bpij-div2-table1">
            <tr>
                <td class="b-h-ss">Numéro SS</td>
                <td class="b-h-nom">Nom / Prénom</td>
                <td class="b-h-nature">Nat. Ass.</td>
                <td class="b-h-periode">Période</td>
                <td class="b-h-statut">Statu intég.</td>
                <td class="b-h-detail">Détail</td>
            </tr>
        </table>
    </div>

    <!-- Détail niveau 2 -->
    <div align="center" class="bpij-div3" style="height:100px;">
        <table class="bpij-div3-table3" style="height:100px;">
          
           <!-- Ligne 1 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">RETRCJ</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail"><a class="lien-bouton-visu" href="#" onclick="return Visualisation();">Visu</a></td>
            </tr>
            <!-- Ligne 1 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>
            
            <!-- Ligne 2 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail"><a class="lien-bouton-visu" href="#" onclick="return Visualisation();">Visu</a></td>
            <!-- Ligne 2 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>
            
           <!-- Ligne 3 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                 <td class="b-d-detail"><a class="lien-bouton-visu" href="#" onclick="return Visualisation();">Visu</a></td>
            </tr>
            <!-- Ligne 3 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>
                                   
           <!-- Ligne 4 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                 <td class="b-d-detail"><a class="lien-bouton-visu" href="#" onclick="return Visualisation();">Visu</a></td>
            </tr>
            <!-- Ligne 4 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>    
                    
           <!-- Ligne 5 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 5 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
            
           <!-- Ligne 6 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 6 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>        
                 
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
            
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             

                       <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
            
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
            
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
            
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             

           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
                                                            
        </table>
    </div>    
    
        <!-- En-tete niveau 1-->
    <div align="center" class="bpij-div2">
        <table class="bpij-div2-table1">
            <tr>
                <td class="b-h-idflux">IDflux</td>
                <td class="b-h-idfichier">IDfichier</td>
                <td class="b-h-dtrans">Date transfert</td>
                <td class="b-h-ip">IP libellé</td>
                <td class="b-h-siren">Siren déclarant</td>
            </tr>
        </table>
    </div>

    <!-- ligne IDFLUX niveau 1 -->
    <div align="center" class="bpij-div3" style="height:40px;">
        <table class="bpij-div3-table2" style="height:40px;">
            <tr>
                <td class="b-d-idflux">1234567890123456789012345</td>
                <td class="b-d-idfichier">1234567890123456789012345</td>
                <td class="b-d-dtrans">15-01-2018</td>
                <td class="b-d-ip">123456789012345</td>
                <td class="b-d-siren">12345678901234</td>
            </tr>
        </table>
    </div>
    
    <!--       Partie 2 - niveau 2 Détail IDfichier     -->
    <!-- En-tete niveau 2-->
    <div align="center" class="bpij-div2">
        <table class="bpij-div2-table1">
            <tr>
                <td class="b-h-ss">Numéro SS</td>
                <td class="b-h-nom">Nom / Prénom</td>
                <td class="b-h-nature">Nat. Ass.</td>
                <td class="b-h-periode">Période</td>
                <td class="b-h-statut">Statu intég.</td>
                <td class="b-h-detail">Détail</td>
            </tr>
        </table>
    </div>

    <!-- Détail niveau 2 -->
    <div align="center" class="bpij-div3" style="height:100px;">
        <table class="bpij-div3-table3" style="height:100px;">
          
           <!-- Ligne 1 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 1 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>
            
            <!-- Ligne 2 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 2 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>
            
           <!-- Ligne 3 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 3 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>
                                   
           <!-- Ligne 4 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 4 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>    
                    
           <!-- Ligne 5 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 5 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
            
           <!-- Ligne 6 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 6 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>        
                 
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
            
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             

                       <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
            
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
            
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
            
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             

           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
                                                            
        </table>
    </div>     
    
        <!-- En-tete niveau 1-->
    <div align="center" class="bpij-div2">
        <table class="bpij-div2-table1">
            <tr>
                <td class="b-h-idflux">IDflux</td>
                <td class="b-h-idfichier">IDfichier</td>
                <td class="b-h-dtrans">Date transfert</td>
                <td class="b-h-ip">IP libellé</td>
                <td class="b-h-siren">Siren déclarant</td>
            </tr>
        </table>
    </div>

    <!-- ligne IDFLUX niveau 1 -->
    <div align="center" class="bpij-div3" style="height:40px;">
        <table class="bpij-div3-table2" style="height:40px;">
            <tr>
                <td class="b-d-idflux">1234567890123456789012345</td>
                <td class="b-d-idfichier">1234567890123456789012345</td>
                <td class="b-d-dtrans">15-01-2018</td>
                <td class="b-d-ip">123456789012345</td>
                <td class="b-d-siren">12345678901234</td>
            </tr>
        </table>
    </div>
    
    <!--       Partie 2 - niveau 2 Détail IDfichier     -->
    <!-- En-tete niveau 2-->
    <div align="center" class="bpij-div2">
        <table class="bpij-div2-table1">
            <tr>
                <td class="b-h-ss">Numéro SS</td>
                <td class="b-h-nom">Nom / Prénom</td>
                <td class="b-h-nature">Nat. Ass.</td>
                <td class="b-h-periode">Période</td>
                <td class="b-h-statut">Statu intég.</td>
                <td class="b-h-detail">Détail</td>
            </tr>
        </table>
    </div>

    <!-- DÃ©tail niveau 2 -->
    <div align="center" class="bpij-div3" style="height:100px;">
        <table class="bpij-div3-table3" style="height:100px;">
          
           <!-- Ligne 1 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 1 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>
            
            <!-- Ligne 2 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 2 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>
            
           <!-- Ligne 3 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 3 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>
                                   
           <!-- Ligne 4 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 4 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>    
                    
           <!-- Ligne 5 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 5 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
            
           <!-- Ligne 6 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 6 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>        
                 
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
            
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             

                       <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
            
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
            
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
            
           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prÃ©nom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             

           <!-- Ligne 7 Nom -->
            <tr>
                <td class="b-d-ss">1610175111044</td>
                <td class="b-d-nom">DEVALEGAMA-GAMACHARIGEYSE</td>
                <td class="b-d-nature">IJNOR</td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut">EN GEDXXXX</td>
                <td class="b-d-detail">Visu</td>
            </tr>
            <!-- Ligne 7 prénom -->
            <tr>
                <td class="b-d-ss"></td>
                <td class="b-d-nom">RAVI CHINTHAKA</td>
                <td class="b-d-nature"></td>
                <td class="b-d-periode">15-01-2018</td>
                <td class="b-d-statut"></td>
                <td class="b-d-detail"></td>
            </tr>             
                                                            
        </table>
    </div>     
 </body>
</html>