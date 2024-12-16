package sully ;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.utility.Delay;
   

public class Tests {
	
	Capteurs c; 
	Actions a; 
	public final static int intervalleMS = 15;
	// Constructeur 
	
	public Tests() {
		this.c = new Capteurs(); 
		this.a = new Actions("lent"); 
	}

	// Tests unitaire  pour la classe Capteurs 
	public void testGetDistance() {
		System.out.print(c.getDistance());
		Delay.msDelay(10000);
	}
	
	public void testGetPression() {
		System.out.print(c.get_pression());
		Delay.msDelay(10000);
	}
	
	public void testGetCouleur() {
		System.out.print(c.get_couleur()); 
		Delay.msDelay(10000);
	}
	
	//Tests unitaire pour la classe actions 
	
	public void testFermerPinces() {
		a.fermer_pinces();
	}
	
	public void testOuvrirPinces() {
		a.ouvrir_pinces();
	}
	
	public void testTourner_deA() {
		a.tourner_deA(360); 
		a.tourner_deA(90);
		a.tourner_deA(45);
			
	}
	
	public void testAvancer_deA() {
		a.avancer_deA(300);
		a.avancer_deA(15);
	}
	
	public void testReculer_deA() {
		a.reculer_deA(300);
	}
	
	public void TestGetIsMoving() {
		while(a.getisMoving() == true) {
			System.out.print("Le robot est en train de bouger");
			Delay.msDelay(intervalleMS);
		}
	}
	
	//Tests d'intégrations pour la classe capteurs 
	
	public void testGet_angle_trigo() {
		 a.tourner_deA(90);
		 //System.out.print(c.get_angle_trigo());
		 Delay.msDelay(10000);
	}
	
	public void testChoisir_methode_angle() {
		a.tourner_deA(90);
		//System.out.print(c.choisir_methode_angle());
		Delay.msDelay(10000);
	}
	
	//Tests d'intégrations pour la classe actions 
	
	public void testAvancer_deS() {
		a.avancer_deS(500);
		while(a.getisMoving()== true) {
			System.out.print(c.getDistance());
			Delay.msDelay(intervalleMS);
		}
	}
	
	public void testTourner_deS() {
		a.tourner_deS(360);
		while(a.getisMoving()== true) {
			System.out.print(c.getDistance());
			Delay.msDelay(intervalleMS);
		}
	}
	
	public void testReculer_deS() {
		a.reculer_deS(500);
		while(a.getisMoving()== true) {
			System.out.print(c.getDistance());
			Delay.msDelay(intervalleMS);
		}
	}
	
	public void testMouvement_aleatoire() {
		a.mouvement_aleatoire();
		a.mouvement_aleatoire(); // expliquer ici qu'on a modifié la fonction pour afficher les angles et du coup je faire deux fois permet de voir si prend bien des valeurs différentes 
	}
	
	public void testAttraper_palet() {
		a.attraper_palet(); 
	}
	
	public void testEviter_obstacle() {
		a.eviter_obstacle();
	}
	
	public void testSetVitesse() {
		a.setVitesse("lent");
		a.avancer_deA(300); 
		a.setVitesse("rapide");
		a.avancer_deA(300);
	}
	
	// Tests d'intégrations pour la classe main 
	
	public void testGet_angle_points() {
		a.tourner_deS(360); 
		//System.out.print(getAnglePoints(a.getBoussole(), a, "lent"));
		Delay.msDelay(10000);
	}
	
	public void testRechercher_palet() {
		//System.out.print(rechercher_palet(a,c, "lent")); 
		Delay.msDelay(10000);
	}
	
}

