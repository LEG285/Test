/**
 *  hello_world.js = Jquery gestion affichage de la page  
 */
// Exemple 2 : Using an object literal for a jQuery feature
// Using an object literal for a jQuery feature
var myFeature = {
  // Mise en forme des div du QCM
  init: function () {
    // Dissimulation des réponses
    $('.reponse').hide();
    // Mise en forme des <div> du QCM
    var q = $('.question');
  
    q.css('background', '#9EEAE0');
    q.css('border-style', 'groove');
    q.css('border-width', '4px');
    q.css('width', '900px');
    q.css('height', '250px');
    q.css('margin', '20px');
    
    $('.texte').css('float', 'left');
    $('.texte').css('width', '90%');
    $('img').css('float', 'right');
    $('img').css('margin-top', '80px');
    // Survol --> Appeler function toutes
    $('#t').hover(function () {
       
      // Effacer et afficher 
       $(function() {
          $('#r1').fadeOut("slow",function(){
             $(this).fadeIn("slow");  
          });
      });
      
      var fruits = [
                { name: "Kiwi", vitamine: "C" },
                { name: "Mangue", vitamine: "A" },
                { name: "Cassis", vitamine: "E" }
                 ];
      //console.log(fruits);
      //console.log(Math.random());
      //console.log("Résultat variable fruits :" + fruits);
      function Personne () {
        console.log('Nouvel objet Personne créé');
      }
      var personne1 = new Personne(); 

      function init() {
        var nom = "Mozilla";     // nom est une variable locale de init
        function afficheNom() {
           console.log(nom); 
        }
        afficheNom();
      }
      init()
      myFeature.toutes();
     }),
      
      // Pas de survol --> Rien  
      myFeature.rien();
   },
   
  // toutes  
  toutes: function () {
      //$('#t').hover(function () {
      // Survol --> Appeler function toutes
      var CheckedValues1 = $('input[type=radio][name=q1]:checked').length;
      var CheckedValues2 = $('input[type=radio][name=q2]:checked').length;
      var CheckedValues3 = $('input[type=radio][name=q3]:checked').length;
      if (CheckedValues1 != 0 && CheckedValues2 != 0 && CheckedValues3 != 0) {
        $('.reponse').show();
        if ($(':radio[id="r1"]:checked').val()) {
          $('#img1').attr('src', 'img/bon.png');
          $('#reponse1').css('color', 'green');
        }
        else {
          $('#img1').attr('src', 'img/mauvais.png');
          $('#reponse1').css('color', 'red');
        }
        if ($(':radio[id="r4"]:checked').val()) {
          $('#img2').attr('src', 'img/bon.png');
          $('#reponse2').css('color', 'green');
        }
        else {
          $('#img2').attr('src', 'img/mauvais.png');
          $('#reponse2').css('color', 'red');
        }
        if ($(':radio[id="r8"]:checked').val()) {
          $('#img3').attr('src', 'img/bon.png');
          $('#reponse3').css('color', 'green');
        }
        else {
          $('#img3').attr('src', 'img/mauvais.png');
          $('#reponse3').css('color', 'red');
        }
      }
    },

  // rien  
  rien: function () {  
     $('#reponse1').hide();
     $('img').each(function () {
     $(this).attr('src', 'img/question.png');
      });  
    }
  
};

$(document).ready(myFeature.init);