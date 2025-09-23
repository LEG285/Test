/**
 *  hello_world.js = Jquery gestion affichage de la page  
 */
// Exemple 1 : code chainé
$(function () {
  // Dissimulation des réponses
  $('.reponse').hide();
  // Mise en forme des div du QCM
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
  // Action au survol du lien « Tester toutes les réponses »
  $('#t').hover(function () {
      // Survol --> Appeler function toutes
      toutes();
  }),
      // Pas de survol --> Rien  
      rien();
  });

// Action au survol du lien «Tester réponse 1»
$('#t1').hover(function () {
  // Tester sélection bouton 
  var CheckedValues1 = $('input[type=radio][name=q1]:checked').length;
  $('#s1').html(CheckedValues1)
  if (CheckedValues1 != 0) {
    $('#reponse1').show();
    if ($(':radio[id="r1"]:checked').val()) {
      $('#img1').attr('src', 'img/bon.png');
      $('#reponse1').css('color', 'green');
    }
    else {
      $('#img1').attr('src', 'img/mauvais.png');
      $('#reponse1').css('color', 'red');
    }
  }
}, function () {
  $('#reponse1').hide();
  $('img').each(function () {
    $(this).attr('src', 'img/question.png');
  });
});
// Action au survol du lien « Tester réponse 2»
$('#t2').hover(function () {
  // Tester sélection bouton 
  var CheckedValues2 = $('input[type=radio][name=q2]:checked').length;
  $('#s2').html(CheckedValues2)
  if (CheckedValues2 != 0) {
    $('#reponse2').show();
    if ($(':radio[id="r4"]:checked').val()) {
      $('#img2').attr('src', 'img/bon.png');
      $('#reponse2').css('color', 'green');
    }
    else {
      $('#img2').attr('src', 'img/mauvais.png');
      $('#reponse2').css('color', 'red');
    }
  }
}, function () {
  $('#reponse2').hide();
  $('img').each(function () {
    $(this).attr('src', 'img/question.png');
  });
});
// Action au survol du lien « Tester réponse 3»
$('#t3').hover(function () {
  // Tester sélection bouton 
  var CheckedValues3 = $('input[type=radio][name=q3]:checked').length;
  $('#s3').html(CheckedValues3)
  if (CheckedValues3 != 0) {
    $('#reponse3').show();
    if ($(':radio[id="r8"]:checked').val()) {
      $('#img3').attr('src', 'img/bon.png');
      $('#reponse3').css('color', 'green');
    }
    else {
      $('#img3').attr('src', 'img/mauvais.png');
      $('#reponse3').css('color', 'red');
    }
  }
}, function () {
  $('#reponse3').hide();
  $('img').each(function () {
    $(this).attr('src', 'img/question.png');
  });
  
});


function toutes() {
  // Tester sélection de tous les boutons 
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
}

function rien () {
  $('.reponse').hide();
  $('img').each(function () {
    $(this).attr('src', 'img/question.png');
  });
}


































