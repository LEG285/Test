/****************************************************************
prestij.js  : javascript applications prestij - Automatisation IJ 
              Création le 03/04/18
****************************************************************/

// Variables globales
var allowed = false;
var charsAllowed = "0123456789";

// Date et heure
var ObjetDate = new Date();
var Jour  = ObjetDate.getDate();
var Mois  = ObjetDate.getMonth();
var Annee = ObjetDate.getFullYear(); 
var heure = ObjetDate.getHours();
var minutes = ObjetDate.getMinutes();

// Array des Mois en clair
var TableMois = new Array( 
"Janvier",
"F&eacute;vrier",
"Mars",
"Avril",
"Mai",
"Juin",  
"Juillet",
"Ao&ucirc;t",
"Septembre",
"Octobre", 
"Novembre",
"D&eacute;cembre"
);    
var DateJour  = Jour + " " + TableMois[Mois] + " " + Annee ;
var HeureJour = heure + "h" + minutes +"m";
var touche1;

// Array des erreurs détectés  
var Erreur = new Array();

// Fonction VerificationCar   
function VerificationCar(e) {
    
	touche1 = e.keyCode;
	return;
	
} // Fin VerificationCar

// Gestion de la saisie code assuré
function codeAssure(type) {
    
    // Controle si erreur sur champ N° SS
    if (Erreur[2]) {
        document.getElementById("gt2").focus();
        return;
    }
    
    // Controle si erreur sur champ N° SIREN
    if (Erreur[5]) {
        document.getElementById("gt5").focus();
        return;
    }
    
    // Curseur
    document.body.style.cursor = 'default'; 
    
    // Remise à 0 de tous les champs saisis
    document.getElementById("gt2").value= ""; 
    document.getElementById("gt3").value= ""; 
    document.getElementById("gt4").value= "";   
    document.getElementById("gt5").value= "";  
    document.getElementById("datedebut6").value= "";  
    document.getElementById("datefin7").value= "";  
    
    // Initialisation couleur d'origine
    document.getElementById("gt1").style.backgroundColor = ""; 
    document.getElementById("gt1").style.color = "black";

    document.getElementById("gt2").style.backgroundColor = ""; 
    document.getElementById("gt2").style.color = "black";

    document.getElementById("gt3").style.backgroundColor = ""; 
    document.getElementById("gt3").style.color = "black";
    
    document.getElementById("gt4").style.backgroundColor = ""; 
    document.getElementById("gt4").style.color = "black";

    document.getElementById("gt5").style.backgroundColor = ""; 
    document.getElementById("gt5").style.color = "black";

    document.getElementById("datedebut6").style.backgroundColor = ""; 
    document.getElementById("datedebut6").style.color = "black";  
    
    document.getElementById("datefin7").style.backgroundColor = ""; 
    document.getElementById("datefin7").style.color = "black";  
    
    document.getElementById("ListStatut").style.backgroundColor = ""; 
    document.getElementById("ListStatut").style.color = "black";  
    
    // Récupération valeur code assuré (numérique sur 7 maxi)
    var assure = document.getElementById("gt1").value;
    Erreur[1] = false; 
    
    // Focus du code Assuré 
    if (type == "focus" && assure == "") {
       document.getElementById("gt1").style.backgroundColor = "yellow"; 
       document.getElementById("gt1").style.color = "black";     
    }
    if (type == "focus" && assure != "") {
       if (isNaN(assure)) {
          document.getElementById("gt1").value= "";
       }
       else {
          document.getElementById("gt1").style.backgroundColor = "yellow"; 
          document.getElementById("gt1").style.color = "black"; 
       }
    }
     
    // Blur du code Assuré
    if (type == "blur" && assure != "") {
       if (isNaN(assure)) {   // Saisie KO 
           
           console.log(touche1);
           
          if (touche1 != 13) {   // Touche Entrée 
                alert("Le code assur\u00e9doit \u00eatre num\u00e9rique ! ");
             Erreur[1] = true;
             document.getElementById("gt1").focus();
             document.getElementById("gt1").value= "";
          }
       
       }
       else {   // Saisie OK -> passer directement à la période
          document.getElementById("gt1").style.backgroundColor = "";
          document.getElementById("gt1").style.color = "green";
          // RAZ champs saisis
          document.getElementById("gt2").value= ""; 
          document.getElementById("gt3").value= ""; 
          document.getElementById("gt4").value= "";   
          document.getElementById("gt5").value= "";  
          document.getElementById("datedebut6").focus();   
       }
        
    }
    if (type == "blur" && assure == "") {
        document.getElementById("gt1").style.backgroundColor = "";
    }

    return;
}

// Gestion Numéro de Sécurité Sociale
function numeroSS(type) {
    
    // Controle si erreur sur champ N° assuré
    if (Erreur[1]) {
        document.getElementById("gt1").focus();
        return;
    }
    
    // Controle si prenom sans nom 
    //if (Erreur[3]) {
    //    document.getElementById("gt3").focus();
    //    return;
    //}
    
    // Controle si erreur sur champ N° SIREN
    if (Erreur[5]) {
        document.getElementById("gt5").focus();
        return;
    }
    
    // Récupération valeur numéro SS (numérique sur 13)
    var numeross = document.getElementById("gt2").value;
    Erreur[2] = false; 
    
    // Gestion du Focus numéro SS 
    if (type == "focus" && numeross == "") {
       document.getElementById("gt2").style.backgroundColor = "yellow"; 
       document.getElementById("gt2").style.color = "black"; 
    }
    if (type == "focus" && numeross != "") {
       if (isNaN(numeross)) {
          //document.getElementById("gt2").value= "";
       }
       else {
          document.getElementById("gt2").style.backgroundColor = "yellow"; 
          document.getElementById("gt2").style.color = "black"; 
          
       }
    }
     
    // Gestion du Blur numero SS
    if (type == "blur" && numeross != "") {
       if (isNaN(numeross)) {
           
          if (touche1 != 13) {   // Touche Entrée 
  
             alert("Le num\u00e9ro de s\u00e9curit\u00e9 sociale doit \u00e9tre num\u00e9rique !");
             Erreur[2] =true;
             document.getElementById("gt2").focus();
             document.getElementById("gt2").value= "";
              
         }
                       
       }
       else {   // Saisie OK -> passer directement à la période
          document.getElementById("gt2").style.backgroundColor = "";
          document.getElementById("gt2").style.color = "green";
          // RAZ champs saisis
          document.getElementById("gt1").value= ""; 
          document.getElementById("gt3").value= ""; 
          document.getElementById("gt4").value= "";  
          document.getElementById("gt5").value= "";  
          document.getElementById("datedebut6").focus();   
       }
        
    }
    if (type == "blur" && numeross == "") {
        document.getElementById("gt2").style.backgroundColor = "";
     }

    return;
}

// Gestion nomAssure
function nomAssure(type) {
    
    // Controle si erreur sur champ code assuré
    if (Erreur[1]) {
        document.getElementById("gt1").focus();
        return;
    }

    // Controle si erreur sur champ N° SS
    if (Erreur[2]) {
        document.getElementById("gt2").focus();
        return;
    }
    
    // Controle si erreur sur champ N° SIREN
    if (Erreur[5]) {
        document.getElementById("gt5").focus();
        return;
    }
    
    // Récupération valeur nom assure avec transco majuscule
    document.getElementById("gt3").value = document.getElementById("gt3").value.toUpperCase();
    var nomassure = document.getElementById("gt3").value;
    
    // Focus nom assure
    if (type == "focus" && nomassure == "") {
       document.getElementById("gt3").style.backgroundColor = "yellow"; 
       document.getElementById("gt3").style.color = "black"; 
    }
    if (type == "focus" && nomassure != "") {
        document.getElementById("gt3").style.backgroundColor = "yellow"; 
        document.getElementById("gt3").style.color = "black";  
    }
     
    // Blur nom assure
    if (type == "blur" && nomassure != "") { // Saisie OK -> Passer au prénom        
        document.getElementById("gt3").style.backgroundColor = "";
        document.getElementById("gt3").style.color = "green";
        // RAZ champs saisis
        document.getElementById("gt1").value= ""; 
        document.getElementById("gt2").value= ""; 
        document.getElementById("gt5").value= ""; 
        document.getElementById("gt4").focus();
    }
    if (type == "blur" && nomassure == "") {
        document.getElementById("gt3").style.backgroundColor = "";
    }

    return;
}

// Gestion prenomAssure
function prenomAssure(type) {

    // Controle si erreur sur champ code assuré
    if (Erreur[1]) {
        document.getElementById("gt1").focus();
        return;
    }

    // Controle si erreur sur champ N° SS
    if (Erreur[2]) {
        document.getElementById("gt2").focus();
        return;
    }
    
    // Controle si erreur sur champ N° SIREN
    if (Erreur[5]) {
        document.getElementById("gt5").focus();
        return;
    }

    // Récupération valeur prenom assure + transco majuscule
    document.getElementById("gt4").value = document.getElementById("gt4").value.toUpperCase();
    var prenomassure = document.getElementById("gt4").value;
    
    // Focus prenom assure
    if (type == "focus" && prenomassure == "") {
       document.getElementById("gt4").style.backgroundColor = "yellow"; 
       document.getElementById("gt4").style.color = "black"; 
    }
    if (type == "focus" && prenomassure != "") {
        document.getElementById("gt4").style.backgroundColor = "yellow"; 
        document.getElementById("gt4").style.color = "black";  
    }
     
    // Blur prenom assure
    if (type == "blur" && prenomassure != "") { 
       // Si Prenom sans nom alors erreur 
       //if (prenomassure != '' && document.getElementById("gt3").value == '' ) {
       //    alert("Attention ! Si le prénom est renseigné alors le nom est obligatoire...");
       //  document.getElementById("gt3").focus();
       //     Erreur[3] =true;
       //} 
       //else { 
        
        // Saisie OK -> Passer directement à la période
        document.getElementById("gt4").style.backgroundColor = "";
        document.getElementById("gt4").style.color = "green";
        // RAZ champs saisis
        document.getElementById("gt1").value= ""; 
        document.getElementById("gt2").value= ""; 
        document.getElementById("gt5").value= "";  
        document.getElementById("datedebut6").focus();
           
       //}
    }
    if (type == "blur" && prenomassure == "") {
        document.getElementById("gt4").style.backgroundColor = "";
    }

    return;
}

// Gestion Siren - Siret
function siren(type) {

    // Controle si erreur sur champ code assuré
    if (Erreur[1]) {
        document.getElementById("gt1").focus();
        return;
    }

    // Controle si erreur sur champ N° SS
    if (Erreur[2]) {
        document.getElementById("gt2").focus();
        return;
    }

    // Récupération valeur numéro Siren (numérique sur 14)
    var sirensiret = document.getElementById("gt5").value;
    Erreur[5] = false; 
    
    // Focus sur  siren 
    if (type == "focus" && sirensiret == "") {
       document.getElementById("gt5").style.backgroundColor = "yellow"; 
       document.getElementById("gt5").style.color = "black"; 
    }
    if (type == "focus" && sirensiret != "") {
       if (isNaN(sirensiret)) {
          //document.getElementById("gt5").value= "";
          //Erreur[5] = true;
       }
       else {
          document.getElementById("gt5").style.backgroundColor = "yellow"; 
          document.getElementById("gt5").style.color = "black";  
       }
    }  
     
    // Blur sur siren
    if (type == "blur" && sirensiret != "") {
       if (isNaN(sirensiret)) {
           
          if (touche1 != 13) {   // Touche Entrée 

           
             alert("Le Siren / Siret doit \u00e9tre num\u00e9rique !");
             Erreur[5] = true;
             document.getElementById("gt5").focus();
             document.getElementById("gt5").value= "";
              
          }
       }
       else {  // Saisie OK -> Passer directement à la période
          document.getElementById("gt5").style.backgroundColor = "";
          document.getElementById("gt5").style.color = "green";
          // RAZ champs saisis
          document.getElementById("gt1").value= ""; 
          document.getElementById("gt2").value= ""; 
          document.getElementById("gt3").value= ""; 
          document.getElementById("gt4").value= "";  
          document.getElementById("datedebut6").focus();    
       }
        
    }
    if (type == "blur" && sirensiret == "") {
        document.getElementById("gt5").style.backgroundColor = "";
    }

    return;
}

// Gestion Date debut
function dateDebut(type) {
    
    // Controle si erreur sur champ code assuré
    if (Erreur[1]) {
        document.getElementById("gt1").focus();
        return;
    }

    // Controle si erreur sur champ N° SS
    if (Erreur[2]) {
        document.getElementById("gt2").focus();
        return;
    }
    
    // Controle si erreur sur champ N° SIREN
    if (Erreur[5]) {
        document.getElementById("gt5").focus();
        return;
    }

    // Récupération de la date de début (JJ/MM/AAAA)
    var datedebut = document.getElementById("datedebut6").value;
    
    // Focus sur date début  
    if ( type == "focus" ) {
       document.getElementById("datedebut6").style.backgroundColor = "yellow"; 
       document.getElementById("datedebut6").style.color = "black"; 
    }
    // Blur sur date début      
    if ( type == "blur" ) {
       // A prevoir : routine controle date
       document.getElementById("datedebut6").style.backgroundColor = "";
       document.getElementById("datedebut6").style.color = "green";
    }
        
    return;
}

// Gestion Date fin 
function dateFin(type) {
    
    // Controle si erreur sur champ code assuré
    if (Erreur[1]) {
        document.getElementById("gt1").focus();
        return;
    }

    // Controle si erreur sur champ N° SS
    if (Erreur[2]) {
        document.getElementById("gt2").focus();
        return;
    }
    
    // Controle si erreur sur champ N° SIREN
    if (Erreur[5]) {
        document.getElementById("gt5").focus();
        return;
    }

    // Récupération de la date fin (JJ/MM/AAAA)
    var datefin = document.getElementById("datefin7").value;
    
    // Focus sur date fin  
    if ( type == "focus" ) {
       document.getElementById("datefin7").style.backgroundColor = "yellow"; 
       document.getElementById("datefin7").style.color = "black"; 
    }
    
    if ( type == "blur" ) {
       document.getElementById("datefin7").style.backgroundColor = "";
       document.getElementById("datefin7").style.color = "green";
    }
        
    return;
}

// Gestion Statut 
function statut(type) {
    
    // Controle si erreur sur champ code assuré
    if (Erreur[1]) {
        document.getElementById("gt1").focus();
        return;
    }

    // Controle si erreur sur champ N° SS
    if (Erreur[2]) {
        document.getElementById("gt2").focus();
        return;
    }
    
    // Controle si erreur sur champ N° SIREN
    if (Erreur[5]) {
        document.getElementById("gt5").focus();
        return;
    }

    // Ajouter controle sur date début
    // if (Erreur[6])...
    
        // Ajouter controle sur date fin
    // if (Erreur[7])...
    
    // Récupération du statut 
    var ObjListe = document.getElementById('ListStatut');
    var SelIndex = ObjListe.selectedIndex;
    var vstatut = ObjListe.options[ObjListe.selectedIndex].value;
    
    // Focus statutut  
    if (type == "focus" && vstatut == "") {
       document.getElementById("ListStatut").style.backgroundColor = "yellow"; 
       document.getElementById("ListStatut").style.color = "black";
    }
    if (type == "focus" && vstatut != "") {
        document.getElementById("ListStatut").style.backgroundColor = "yellow"; 
       document.getElementById("ListStatut").style.color = "black";  
    }

    // Blur sur date fin
    if (type == "blur" && vstatut != "") {
       document.getElementById("ListStatut").style.backgroundColor = "";
       document.getElementById("ListStatut").style.color = "green";
    }
    
    if (type == "blur" && vstatut == "") {
        document.getElementById("ListStatut").style.backgroundColor = "";
    }
 
    return;
}

// Valider Formulaire
function ValidationGlobale(formulaire) {

    // Récupération de toutes les variables du formulaire 
    var assure     = document.getElementById("gt1").value;
    var numeross   = document.getElementById("gt2").value;
    var nom        = document.getElementById("gt3").value;
    var prenom     = document.getElementById("gt4").value;
    var nom_prenom = nom + prenom;
    var sirensiret = document.getElementById("gt5").value;
    var criteres   = assure + numeross + nom + prenom + sirensiret;
    var datedebut  = document.getElementById("datedebut6").value;
    var datefin    = document.getElementById("datefin7").value;
    
    var ObjListe   = document.getElementById('ListStatut');
    var SelIndex   = ObjListe.selectedIndex;
    var vstatut    = ObjListe.options[ObjListe.selectedIndex].value;
    
    // Controle des données saisies
    
    // 1) Controle validité des données saisies
    // Assure
    if (isNaN(assure)) {
        //alert("Touche appuyée validation : " + touche1);
        console.log(touche1);
        
       alert("Le code assur\u00e9 doit \u00e9tre num\u00e9rique !");
       Erreur[1] =true;
       document.getElementById("gt1").focus();
       document.getElementById("gt1").value= "";
       return false;
     }    
    // N° SS
    if (isNaN(numeross)) {
       alert("Le num\u00e9ro de s\u00e9curit\u00e9 sociale doit \u00e9tre num\u00e9rique !");
       Erreur[2] =true;
       document.getElementById("gt2").focus();
       document.getElementById("gt2").value= "";        
       return false;    
    }    
    // Siren/Siret
    if (isNaN(sirensiret)) {
       alert("Le Siren / Siret doit \u00e9tre num\u00e9rique !");
       Erreur[5] = true;
       document.getElementById("gt5").focus();
       document.getElementById("gt5").value= "";
       return false;
    }
    
    // 2) Controle cohérence des critères saisis dans le formulaire avant envoi requete
    if (criteres == '')  {
       alert("Attention ! au moins UN crit\u00e9re doit \u00e9tre renseign\u00e9...");
       document.getElementById("gt1").focus();     
       Erreur[1] =true;
       return false;
     }    
    
    // Si Assure alors pas de n°SS/Nom/Prenom/Siren
    if (assure != '' && (numeross != '' || nom_prenom != '' || sirensiret != '' )) {
       alert("Attention ! Seule une recherche par code assur\u00e9 OU n°SS OU Nom OU Siren est possible...");
       Erreur[1] =true;
       //document.getElementById("gt1").focus();     
       return false;
     }    
    // Si N° SS alors pas assuré/Nom/Prenom/Siren
    if (numeross != '' && (assure != '' || nom_prenom != '' || sirensiret != '' )) {
       alert("Attention ! Seule une recherche par code assur\u00e9 OU n°SS OU Nom OU Siren est possible...");
       Erreur[1] =true;
       //document.getElementById("gt2").focus();     
       return false;
    }    
    // Si Prenom sans nom alors erreur 
    if (prenom != '' && nom == '' ) {
       alert("Attention ! Si le pr\u00e9nom est renseign\u00e9 alors le nom est obligatoire...");
       Erreur[1] =true;
       document.getElementById("gt3").focus();     
      return false;
    }    
    // Si nom_prenom alors pas assuré/numeross/Siren
    if (nom_prenom != '' && (assure != '' || numeross != ''|| sirensiret != '' )) {
       alert("Attention ! Seule une recherche par code assur\u00e9 OU n°SS OU Nom OU Siren est possible...");
              Erreur[1] =true;
       //document.getElementById("gt3").focus();     
       return false;
    }    
    // Si sirensiret alors pas assuré/numeross/nomprenom
    if (sirensiret != '' && (assure != '' || numeross != '' || nom_prenom != '')) {
       alert("Attention ! Seule une recherche par code assur\u00e9 OU n°SS OU Nom OU Siren est possible...");
              Erreur[1] =true;
       //document.getElementById("gt5").focus();     
       return false;
    }    
    // une date début de période doit être renseignée
    if (datedebut == '') {
       alert("Renseigner une date de d\u00e9but de p\u00e9riode !");
       //Erreur[5] = true;
       document.getElementById("datedebut6").focus();
       return false;
    }
    // une date fin doit de période doit être renseignée
    if (datefin == '') {
       alert("Renseigner une date de fin de p\u00e9riode !");
       //Erreur[5] = true;
       document.getElementById("datefin7").focus();
       return false;
    }
    // un statut demande doit être renseigné
    if (vstatut == '') {
       alert("Renseigner le statut  !");
       //Erreur[5] = true;
       document.getElementById("ListStatut").focus();
       return false;
    }
    
    document.body.style.cursor = 'progress';
    return true;    
     
} // Fin Valider

// Fonctions JQuery (Datepicker)
$(function() { 
	
// Initialisation Sélection de la date début de période
$( "#datedebut6" ).datepicker( {
    autoSize: "true",
    altField: "#datedebut6",
    closeText: 'Fermer',
    prevText: 'Pr\u00e9c\u00e9dent',
    nextText: 'Suivant',
    currentText: 'Aujourd\'hui',
    monthNames: ['Janvier', 'F\u00e9vrier', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Aout', 'Septembre', 'Octobre', 'Novembre', 'D\u00e9cembre'],
    monthNamesShort: ['Janv.', 'F\u00e9vr.', 'Mars', 'Avril', 'Mai', 'Juin', 'Juil.', 'Aout', 'Sept.', 'Oct.', 'Nov.', 'D\u00e9c.'],
    dayNames: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'],
	dayNamesShort: ['Dim.', 'Lun.', 'Mar.', 'Mer.', 'Jeu.', 'Ven.', 'Sam.'],
	dayNamesMin: ['Di', 'Lu', 'Ma', 'Me', 'Je', 'Ve', 'Sa'],
	weekHeader: 'Sem.',
	dateFormat: 'dd-mm-yy',   
	firstDay: "1"
	});

// Sélection de la date fin de période   
$( "#datefin7" ).datepicker({
  autoSize: "true",
  altField: "#datefin7",
  closeText: 'Fermer',
  prevText: 'Pr\u00e9c\u00e9dent',
  nextText: 'Suivant',
  currentText: 'Aujourd\'hui',
  monthNames: ['Janvier', 'F\u00e9vrier', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Aout', 'Septembre', 'Octobre', 'Novembre', 'D\u00e9cembre'],
  monthNamesShort: ['Janv.', 'F\u00e9vr.', 'Mars', 'Avril', 'Mai', 'Juin', 'Juil.', 'Aout', 'Sept.', 'Oct.', 'Nov.', 'D\u00e9c.'],
  dayNames: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'],
  dayNamesShort: ['Dim.', 'Lun.', 'Mar.', 'Mer.', 'Jeu.', 'Ven.', 'Sam.'],
  dayNamesMin: ['Di', 'Lu', 'Ma', 'Me', 'Je', 'Ve', 'Sa'],
  weekHeader: 'Sem.',
  dateFormat: 'dd-mm-yy',   	  
  firstDay: "1"
   });

});

