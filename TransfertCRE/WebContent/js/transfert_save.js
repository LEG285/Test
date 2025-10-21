/****************************************************************
 transfert_save.js  : javascript applications transfert CRE 
                      Le 16/06/2025
                      Ce fichier est une sauvegarde du fichier transfer.js
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

// Erreurs détectées / Champs
var Erreur = new Array();    

//********************************************************
// Fonction Verification Caractère
//********************************************************
function VerificationCar(e) {
    
	touche1 = e.keyCode;
	return;
	
} // Fin VerificationCar

//********************************************************
//     Gestion formulaire de saisie
//******************************************************** 

// Codes CRE Colonne 1 -----------------------------------  
function codeAssure(type) {
    
    // Initialisation curseur
    document.body.style.cursor = 'default';  
    
    // Initialisation couleur 
    document.getElementById("gt1").style.backgroundColor = ""; 
    document.getElementById("gt1").style.color = "black";
 
    document.getElementById("gt2").style.backgroundColor = ""; 
    document.getElementById("gt2").style.color = "black";
    
	document.getElementById("typecou").style.backgroundColor = ""; 
	document.getElementById("typecou").style.color = "black"; 	
	
	document.getElementById("codeadh").style.backgroundColor = ""; 
	document.getElementById("codeadh").style.color = "black"; 	
	
	document.getElementById("datecr").style.backgroundColor = ""; 
	document.getElementById("datecr").style.color = "black";	
	
    document.getElementById("ListSource").style.backgroundColor = ""; 
    document.getElementById("ListSource").style.color = "black";  
  
    document.getElementById("ListCible").style.backgroundColor = ""; 
    document.getElementById("ListCible").style.color = "black";  
    
	// Initialisation code erreur
	//Erreur[0]=false;Erreur[1]=false;Erreur[2]=false;Erreur[3]=false;Erreur[4]=false; 
 
     var assure = document.getElementById("gt1").value;
	//console.log("Longueur code CRE saisi " + assure.length);
    
    // Focus du code Assuré 
    if (type == "focus" && assure == "") {
       document.getElementById("gt1").style.backgroundColor = "yellow"; 
       document.getElementById("gt1").style.color = "black";     
    }
    if (type == "focus" && assure != "") {
       if (isNaN(assure)) {
         // document.getElementById("gt1").value= "";
       }
       else { 
          document.getElementById("gt1").style.backgroundColor = "yellow"; 
          document.getElementById("gt1").style.color = "black"; 
       }
	   
    }
	  
	// ---------------------------------------------------------------
	// Controler longueur de chaque code assuré saisie dans la textearea
	// Solution a trouver, ajout d'un message ou autre pour la saisie .... 
	// --------------------------------------------------------------- 
    //	if (type == "blur" && assure.trim != "") {
    //		
    //	   var position = assure.indexOf("\n", 0); 
    //	   
    //	   assure = assure.replace(/(\r\n|\n|\r|\t)/gm,"");
    //	   
    //	   console.log("1 " + assure.slice(0, 11));
    //	   console.log("2 " + assure.slice(11, 22));
    //	   console.log("3 " + assure.slice(22, 33));
    //	   console.log("4 " + assure.slice(33, 44));
    //	   console.log("5 " + assure.slice(44, 55));
    //	   console.log("6 " + assure.slice(55, 66));
    //	   console.log("7 " + assure.slice(66, 77));
    //	   console.log("8 " + assure.slice(77, 88));
    //	   console.log("9 " + assure.slice(88, 99));
    //	   console.log("10 " + assure.slice(99, 110));
    //	   
    //	}
	   
	  // var longueur = assure.lenght;
	   
	   //if (assure.length != 11) {
	   //	  alert("La longueur du code CRE doit toujours \u00eatre de 11 caract\u00e9res ! elle est : " + assure.length );
	   //	  Erreur[0] = true;
	   //	  document.getElementById("gt1").focus();
	   //	  // document.getElementById("gt1").value= "";
	   //	  document.getElementById("Msg").innerHTML = "  ";
	   //  }
	   
//	   if (position != 11 && position != -1) {      // Saisie KO  ----------------
//	      //console.log(touche1);
//	      //if (touche1 != 13) {   // <> de la touche Entrée 
//	          alert("La longueur du code CRE doit toujours \u00eatre de 11 caract\u00e9res ! ");
//	          Erreur[0] = true;
//	          document.getElementById("gt1").focus();
//	          //document.getElementById("gt1").value= "";
//	          document.getElementById("Msg").innerHTML = "  ";
//	      //}
//	   }      
//	    
//	   var position = assure.indexOf("\n", 12); 
//	   if (position != 23 && position != -1) {       // Saisie KO  ----------------
//	   	      //console.log(touche1);
//	   	      //if (touche1 != 13) {   // <> de la touche Entrée 
//	   	          alert("La longueur du code CRE doit toujours \u00eatre de 11 caract\u00e9res ! ");
//	   	          Erreur[0] = true;
//	   	          document.getElementById("gt1").focus();
//	   	         // document.getElementById("gt1").value= "";
//	   	          document.getElementById("Msg").innerHTML = "  ";
//	   	      //}
//	   	 }	     
	   
//	}  
	
    // Blur 
    if (type == "blur" && assure.trim != "") {
	   assure = assure.replace(/(\r\n|\n|\r)/gm,"");
       if (isNaN(assure)) {       // Saisie KO  ----------------
          //console.log(touche1);
          if (touche1 != 13) {    // <> de la touche Entrée 
             alert("Le code CRE doit toujours \u00eatre num\u00e9rique ! ");
             Erreur[0] = true;
             document.getElementById("gt1").focus();
             document.getElementById("gt1").value= ""; 
             document.getElementById("Msg").innerHTML = "  ";
          }
       }
       else {                   // Saisie OK & Pas d'erreur -> passer au champ suivant 
		   if (!Erreur[0]) { 
              document.getElementById("gt1").style.backgroundColor = "";
              document.getElementById("gt1").style.color = "green";
              document.getElementById("gt2").focus();
		    }   
       }
	   
    }
    
    if (type == "blur" && assure.trim == "") {
        document.getElementById("gt1").style.backgroundColor = "";
    }

    return;     // Fin fonction codeAssure colonne 1
}

// Code CRE 2 Colonne 2 --------------------------
function codeAssure2(type) {
    
    // Initialisation 
    document.body.style.cursor = 'default';  
    var assure2 = document.getElementById("gt2").value;
    
    // Focus du code Assuré 
    if (type == "focus" && assure2.trim == "") {
       document.getElementById("gt2").style.backgroundColor = "yellow"; 
       document.getElementById("gt2").style.color = "black";     
    }
    if (type == "focus" && assure2.trim != "") {
       if (isNaN(assure2)) {
         //document.getElementById("gt2").value= "";
       }
       else { 
          document.getElementById("gt2").style.backgroundColor = "yellow"; 
          document.getElementById("gt2").style.color = "black"; 
       }
       
    }
     
    // Blur du code Assuré
    if (type == "blur" && assure2.trim != "") {
	   assure2 = assure2.replace(/(\r\n|\n|\r)/gm,"");
       if (isNaN(assure2)) {       // Saisie KO -------- 
          //console.log(touche1);
          if (touche1 != 13) {     // <> de la touche Entrée 
             alert("Le code CRE doit toujours \u00eatre num\u00e9rique ! ");
             Erreur[1] = true;
             document.getElementById("gt2").focus();
             document.getElementById("gt2").value= "";
             document.getElementById("Msg").innerHTML = "  "; 
          }
       
       }
       else {   // Saisie OK & pas d'erreur -> passer au champ suivant 
		if (!Erreur[1]) {
          document.getElementById("gt2").style.backgroundColor = "";
          document.getElementById("gt2").style.color = "green";
          //document.getElementById("typecou").focus();
		  }
       }
        
    }
    if (type == "blur" && assure2.trim == "") {
        document.getElementById("gt2").style.backgroundColor = "";
    }

    return;     // Fin fonction codeAssure colonne 2
}

// Type courrier  ----------------------------------------------
function typeCourrier(type) {
    
    // Initialisation 
    document.body.style.cursor = 'default';  
    var typecourrier = document.getElementById("typecou").value;
    
    // Focus  
    if (type == "focus" && typecourrier == "") {
       document.getElementById("typecou").style.backgroundColor = "yellow"; 
       document.getElementById("typecou").style.color = "black";     
    }
	
    // Blur 
    if (type == "blur" && typecourrier.trim != "") {  // Saisie OK 
		document.getElementById("typecou").style.backgroundColor = "";
		document.getElementById("typecou").style.color = "green";
		//document.getElementById("codeadh").focus();    
     }
        
    if (type == "blur" && typecourrier.trim == "") {
        document.getElementById("typecou").style.backgroundColor = "";
    }

    return;     // Fin fonction
}

// Code adhérent  -----------------------------------------------
function codeAdherent(type) {
    
    // Initialisation 
    document.body.style.cursor = 'default';  
    var codeadh = document.getElementById("codeadh").value;
    
    // Focus 
    if (type == "focus" && codeadh == "" && !Erreur[3]) {
       document.getElementById("codeadh").style.backgroundColor = "yellow"; 
       document.getElementById("codeadh").style.color = "black";     
    }
	
    // Blur 
    if (type == "blur" && codeadh.trim != "") {     
		if (isNaN(codeadh)) {       // Saisie KO  ----------------
		    //console.log(touche1);
		    if (touche1 != 13) {    // <> de la touche Entrée 
		       alert("Le code adh\u00e9rent doit toujours \u00eatre num\u00e9rique ! ");
		       Erreur[3] = true;
		       document.getElementById("codeadh").focus();
		       document.getElementById("codeadh").value= ""; 
		       document.getElementById("Msg").innerHTML = "  ";
			   //document.getElementById("codeadh").style.backgroundColor = "red";
			   //return;
		    }
		 }
		 else {
			if (!Erreur[3]) { 
			   document.getElementById("codeadh").style.backgroundColor = "";
			   document.getElementById("codeadh").style.color = "green";
			   //document.getElementById("datecr").focus();	
			}		
		 }		
     }
        
    if (type == "blur" && codeadh.trim == "") {
        document.getElementById("codeadh").style.backgroundColor = "";
    }

    return;     // Fin fonction 
}

// Date CRE ---------------------------------------------------
function dateCRE(type) {
    
	// Erreur CRE colonne 1 
	if (Erreur[0]) {
	    document.getElementById("gt1").focus();
	    return;
	}
	// Erreur CRE colonne 2 
	if (Erreur[1]) {
	    document.getElementById("gt2").focus();
	    return;
	}
	
    // Errreur Adhérent
    if (Erreur[3]) {
        document.getElementById("codeadh").focus();
        return;
    }
	
    // Récupération de la date fin (JJ/MM/AAAA)
    var dateCRE = document.getElementById("datecr").value;
    
    // Focus 
    if ( type == "focus" ) {
       document.getElementById("datecr").style.backgroundColor = "yellow"; 
       document.getElementById("datecr").style.color = "black"; 
    }
    
	// Blur 
    if ( type == "blur" ) {
       document.getElementById("datecr").style.backgroundColor = "";
       document.getElementById("datecr").style.color = "green";
	   //document.getElementById("ListSource").focus();
    }
        
    return;
}

// Système source -------------------------------------------------
function source(type) { 
    
    // Controle si erreur sur code colonne 1 
    if (Erreur[0]) {
        document.getElementById("gt1").focus();
        return;
    }
    // Controle si erreur sur code colonne 2 
    if (Erreur[1]) {
        document.getElementById("gt2").focus();
        return;
    }
	
	// Controle si erreur sur adhérent 
	if (Erreur[3]) {
	   document.getElementById("codeadh").focus();
	   return;
	}
    
    // Récupération du statut 
    var ObjListe = document.getElementById('ListSource');
    var SelIndex = ObjListe.selectedIndex;
    var vsource  = ObjListe.options[ObjListe.selectedIndex].value;
    
    // Focus 
    if (type == "focus" && vsource == "") {
       document.getElementById("ListSource").style.backgroundColor = "yellow"; 
       document.getElementById("ListSource").style.color = "black";
    }
    if (type == "focus" && vsource != "") {
        document.getElementById("ListSource").style.backgroundColor = "yellow"; 
        document.getElementById("ListSource").style.color = "black";  
    }

    // Blur 
    if (type == "blur" && vsource != "") {
       document.getElementById("ListSource").style.backgroundColor = "";
       document.getElementById("ListSource").style.color = "green";
	   document.getElementById("ListCible").focus();
    }
    
    if (type == "blur" && vsource == "") {
        document.getElementById("ListSource").style.backgroundColor = "";
    }
 
    return;
	
}

// Système cible --------------------------------------------------------  
function cible(type) {
    
   // Controle si erreur sur code colonne 1 
    if (Erreur[0]) {
        document.getElementById("gt1").focus();
        return;
    }
    // Controle si erreur sur code colonne 2 
    if (Erreur[1]) {
        document.getElementById("gt2").focus();
        return;
    }
 
	// Controle si erreur sur adhérent 
	if (Erreur[3]) {
	   document.getElementById("codeadh").focus();
	   return;
	}	
	
    // Récupération système cible 
    var ObjListe = document.getElementById('ListCible');
    var SelIndex = ObjListe.selectedIndex;
    var vcible   = ObjListe.options[ObjListe.selectedIndex].value;
    
    // Focus cible   
    if (type == "focus" && vcible == "") {
       document.getElementById("ListCible").style.backgroundColor = "yellow"; 
       document.getElementById("ListCible").style.color = "black";
    }
    if (type == "focus" && vcible != "") {
        document.getElementById("ListCible").style.backgroundColor = "yellow"; 
        document.getElementById("ListCible").style.color = "black";  
    }

    // Blur sur date fin
    if (type == "blur" && vcible != "") {
       document.getElementById("ListCible").style.backgroundColor = "";
       document.getElementById("ListCible").style.color = "green";
	}
    
    if (type == "blur" && vcible == "") {
        document.getElementById("ListCible").style.backgroundColor = "";
	
    }
 
    return;
}

// Valider Formulaire CRE ---------------------------------
function ValidationGlobale(formulaire) {

    // Récupération de toutes les variables du formulaire 
    var assure     = document.getElementById("gt1").value;
    var assure2    = document.getElementById("gt2").value;
	var typecourrier = document.getElementById("typecou").value;    
	var codeadh = document.getElementById("codeadh").value;
	var dateCRE = document.getElementById("datecr").value;
	
    var ObjListeS  = document.getElementById('ListSource');
    var SelIndex   = ObjListeS.selectedIndex;
    var vsource    = ObjListeS.options[ObjListeS.selectedIndex].value;
   
    var ObjListeC  = document.getElementById('ListCible');
    var SelIndex   = ObjListeC.selectedIndex;
    var vcible     = ObjListeC.options[ObjListeC.selectedIndex].value;
    
    // Controle des données saisies
    
    // 1) Controle validité des CRE saisis 
    // Assure col 1
    assure = assure.replace(/(\r\n|\n|\r)/gm,"");
    if (isNaN(assure)) {
       //alert("Touche appuyée validation : " + touche1);
       //console.log(touche1);
       alert("Le code CRE doit toujours \u00eatre num\u00e9rique ! ");
       Erreur[0] =true;
       document.getElementById("gt1").focus();
      // document.getElementById("gt1").value= "";
       document.getElementById("Msg").innerHTML = "  ";
       return false;
    }  
 
    // Assure col 2
    assure2 = assure2.replace(/(\r\n|\n|\r)/gm,"");
    if (isNaN(assure2)) {
       //alert("Touche appuyée validation : " + touche1);
       //console.log(touche1);
       alert("Le code CRE doit toujours \u00eatre num\u00e9rique ! ");
       Erreur[1] =true;
       document.getElementById("gt2").focus();
    // document.getElementById("gt2").value= "";
       document.getElementById("Msg").innerHTML = "  ";
       return false;
    }
       
    if (assure.trim() == '' && assure2.trim() == '') {
       //document.getElementById("Msg").innerHTML = "  ";
       alert("Le code CRE est obligatoire ! ");
       Erreur[0] =true;
       document.getElementById("gt1").focus();
       //document.getElementById("gt1").value= "";
       document.getElementById("Msg").innerHTML = "  ";
       return false;
     } 
     
    // 2) Controle cohérence des critères saisis dans le formulaire avant envoi requete
 /*   if (criteres == '')  {
       //alert("Attention ! Au moins UN crit\350re doit \352tre renseign\351...");
       document.getElementById("gt1").focus();     
       Erreur[1] =true;
       return false;
    }    
 */
     
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    // un serveur source doit être renseigné
    if (vsource.trim() == '') {
       alert("Renseigner l'environnement de d\u00e9part !");
       Erreur[2] = true;
       document.getElementById("ListSource").focus();
       document.getElementById("Msg").innerHTML = "  ";
       return false;
    }
 
    // un serveur cible doit être renseigné
    if (vcible.trim() == '') {
       alert("Renseigner l'environnement d'arriv\u00e9e !");
       Erreur[2] = true;
       document.getElementById("ListCible").focus();
       document.getElementById("Msg").innerHTML = "  ";
       return false;
    }  
     
    // serveur source doit être <> serveur cible 
    if ((vcible == vsource) | (vsource == "RECETTE" & vcible == "RECETTE et DEV")
         | (vsource == "DEV" & vcible == "RECETTE et DEV")) {
       document.getElementById("Msg").innerHTML = "  ";
       alert("L'environnement de d\u00e9part doit \u00eatre diff\u00e9rent de l'environnement d'arriv\u00e9e  !");
       Erreur[1] = true;
       document.getElementById("ListCible").focus();
       //document.getElementById("Msg").innerHTML = "  ";
       return false; 
    } 
     
    document.body.style.cursor = 'progress';
    return true;    
     
}    // Fin Valider formulaire Transfert des CRE 

//*************************************************************************************
// Functions Jquery  
//*************************************************************************************
$(function() { 

// Date sélection CRE 
$("#datecr").datepicker( {
  autoSize: "true",
  altField: "#Bdatedebut",
  closeText: 'Fermer',
  prevText: 'Pr&eacute;c&eacute;dent',
  nextText: 'Suivant',
  currentText: 'Aujourd\'hui',
  monthNames: ['Janvier', 'F&eacute;vrier', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Ao&eacute;t', 'Septembre', 'Octobre', 'Novembre', 'D&eacute;cembre'],
  monthNamesShort: ['Janv.', 'F&eacute;vr.', 'Mars', 'Avril', 'Mai', 'Juin', 'Juil.', 'Ao&eacute;t', 'Sept.', 'Oct.', 'Nov.', 'D&eacute;c.'],
  dayNames: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'],
  dayNamesShort: ['Dim.', 'Lun.', 'Mar.', 'Mer.', 'Jeu.', 'Ven.', 'Sam.'],
  dayNamesMin: ['Di', 'Lu', 'Ma', 'Me', 'Je', 'Ve', 'Sa'],
  weekHeader: 'Sem.',
  dateFormat: 'dd-mm-yy' ,   
  firstDay: "1"
  });    

  // Initialisation date jour       
  //$("#BdateCRE").val($.datepicker.formatDate('dd-mm-yy', new Date()));
  
//Tableau prestation
  $('#gestip').dataTable({
      
      "language": {search: "Rechercher&nbsp;:",
   	               zeroRecords: "Aucun &eacute;l&eacute;ment &agrave; afficher"
   	           },                   
      "order": [],
      "info":  false,
      "scrollY": "400px",
      "scrollCollapse": true,
      "paging": false,
      "columns": [
          null,
          null,
          null,
          null,
          { type: 'date-eu', targets: 0 },
          null,
          null,
          null]
   	           
      });      
  
});
