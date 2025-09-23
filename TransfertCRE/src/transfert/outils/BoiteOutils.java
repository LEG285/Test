/**
 * *********************************************************************
 * Projet                 : PRESTIJ - AUTOMATISATION IJ   
 * Package                : transfertoutils
 * Class                  : BoiteOutils.java 
 *----------------------------------------------------------------------               
 * Objet                  : Utilitaires applicatif PRESTIJ 
 *----------------------------------------------------------------------
 * Auteur                 : Thierry Le Guillou 
 * Date version initiale  : 18 04 2018 
 *********************************************************************** 
 */
package transfert.outils;

import java.sql.Date;

public class BoiteOutils {

	// Variables priv�es
	private String timestampJJMMAA;
	private int hauteur;

	// Ctor TimeStampcode
	public BoiteOutils(){
		//this.timestampJJMMAA = Ptimestamp;
	} // end of Ctor

	// Ctor hauteur
	public BoiteOutils(int Phauteur) {
		this.hauteur = Phauteur;
	}

	// CalculHauteur : Calcul hauteur page GESTIP 
	public int CalculHauteur() {

		int valeur = (hauteur * 200);
		if (valeur > 400) valeur = 400;
		return (valeur);

	} // fin CalculHauteur

	// CalculHauteurBPIJ : Calcul hauteur détail assuré BPIJ
	public int CalculHauteurBPIJ() {

		int valeur = (hauteur * 90);
		//if (valeur > 560) valeur = 560;
		return (valeur);

	} // fin CalculHauteur	
	
	// Formattage période JJ-MM-AAAA
	public String FormatPeriodeJJMMAA(String timestampAAMMJJ) {

 		String Retour;
		try {
			Retour= timestampAAMMJJ.substring(8, 10) + "-" + timestampAAMMJJ.substring(5, 7) + "-" + timestampAAMMJJ.substring(0, 4); 
		} catch (Exception e) {
			Retour = "00-00-0000";
		}
		return Retour;
	}	

	// Formattage p�riode AAAA-MM-JJ
	public String FormatPeriodeAAMMJJ(String timestampJJMMAA) {

 		String Retour;
		try {
			Retour= timestampJJMMAA.substring(6, 10) + "-" + timestampJJMMAA.substring(3, 5) + "-" + timestampJJMMAA.substring(0, 2); 
		} catch (Exception e) {
			Retour = "0000-00-00";
		}
		return Retour;
	}	
	
	// Formattage p�riode JJ-MM-AAAA
	public String FormatDateJJMMAA(Date dateAAMMJJ) {
		
		String Retour;
		if (dateAAMMJJ != null) {
			
		   String StrDate = dateAAMMJJ.toString(); 
 		
		   try { Retour= StrDate.substring(8, 10) + "-" + StrDate.substring(5, 7) + "-" + StrDate.substring(0, 4);}
		   catch (Exception e) {Retour = "00-00-0000";}
		
		}
		else {
			
			Retour = "";	   
		}

		return Retour;

	}		
	
	// Formattage p�riode JJ-MM-AAAA
	public String FormatDateJJMMAA(String dateAAAAMMJJ) {
		
		String Retour;
		if (dateAAAAMMJJ != null) {
			
		   String StrDate = dateAAAAMMJJ; 
 		
		   try { Retour= StrDate.substring(6, 8) + "-" + StrDate.substring(4, 6) + "-" + StrDate.substring(0, 4);}
		   catch (Exception e) {Retour = "00-00-0000";}
		
		}
		else {
			
			Retour = "";	   
		}

		return Retour;

	}		
	
	
	
	
	// Controle si zone num�rique
	/*
	public boolean ControleNumerique() {
		boolean reponse = true;
		// Test si lg du codes
		if (this.code.length() >= 1) {
			// Test si n� commande est bien un int
			try {
				long i = Long.parseLong(this.code);
			} catch (Exception e) {
				reponse = false;
			}
		} else {
			reponse = false;
		}
		// Return
		return (reponse);
	}
	*/
	
}
