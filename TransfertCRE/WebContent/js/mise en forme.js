/**
 * TP : Mise en forme 
 */
$(function () {
  
  // Couleur de fond
  $('#couleur-fond').change(function () {
    var cf = $('#couleur-fond option:selected').val();
    $('#contenu').css('background-color', cf);
  });
  
  // Texte
  $('#texte').change(function () {
    var te = $('#texte option:selected').val();
    if (te == 'Normal') {
      $('#contenu p').css('font-weight', 'normal');
      $('#contenu p').css('font-style', 'normal');
      $('#contenu p').css('text-decoration', 'none');
    }
    if (te == 'Gras') $('#contenu p').css('font-weight', 'bold');
    if (te == 'Italique') $('#contenu p').css('font-style', 'italic');
    if (te == 'Souligne') $('#contenu p').css('text-decoration', 'underline');
  });
  
  // Police
  $('#police').change(function () {
    var ff = '"' + $('#police option:selected').val() + '"';
    $('#contenu p').css('font-family', ff);
  });
  
  // Police 1e phrase
  $('#police-prem-phrase').change(function () {
    var ppp = $('#police-prem-phrase option:selected').val();
    $('#contenu p:first').css('font-family', ppp);
  });
  
  // Premier caractère des phrases
  $('#prem-car-phrases').change(function () {
    var pcp = $('#prem-car-phrases option:selected').val();
    if (pcp == 'Normal') {
      $('p').each(function () {
        var unPar = $(this).html();
        if (unPar.indexOf('<img') == -1) $(this).text($(this).text());
      });
    }
    if (pcp == 'Gras') {
      $('p').each(function () {
        var tableau = $(this).text().split('. ');
        if (tableau.length == 1) {}
        else {
          var tableau2 = $.map(tableau, function (el, ind) {
            if (el[0] != null) return '<b>' + (el[0]) + '</b>' + el.substring(1) + '. ';
          });
          $(this).html(tableau2.join(''));
        }
      });
    }
  });
  
  // Mot en rouge
  $('#couleurMot').click(function () {
    var mot = $('#mot').val();
    var tableau = $('p:first').text().split(' ');
    var tableau2 = $.map(tableau, function (el, ind) {
      if (ind + 1 == mot) return ('<font color="red">' + el + '</font>')
      else return (el);
    });
    $('p:first').html(tableau2.join(' '));
  });
  
  // Bordure des images
  $('#bordure-images').change(function () {
    var bi = $('#bordure-images option:selected').val();
    if (bi == 'Rien') $('img').css('border', '2px solid white');
    if (bi == 'Simple') $('img').css('border', '2px solid red');
    if (bi == 'Double') $('img').css('border', '5px double red');
  });
  
  // RAZ du formulaire
  $('#raz').click(function () {
    location.reload();
  });
  
});