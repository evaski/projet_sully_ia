package sully;

import java.util.Random;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.*;
import lejos.robotics.navigation.MovePilot;

public class Actions {

	private static final double TAILLE_ROBOT = 330; // A CHANGER AVEC UNE MESURE PLUS PRÉCISE

	//Attribue pour les roues
	private MovePilot pilot;
	private EV3LargeRegulatedMotor portRoueGauche;
	private EV3LargeRegulatedMotor portRoueDroite;
	private Wheel roueGauche;
	private Wheel roueDroite;
	private Chassis chassis;

	// Attribues pour les pinces
	private EV3LargeRegulatedMotor pinces; 
	private boolean estOuverte;   // État des pinces


	//Constructeur initialisant les attributs pour les pinces et les moteurs
	public Actions(String vitesse) { // pour l'instant j'ai fait avec un argument vitesse et en appelant la méthode setVitesse parce que je pense ça ne marche pas sans

		//partie pour les roues
		this.portRoueGauche = new EV3LargeRegulatedMotor(MotorPort.A); //Initialise le moteur gauche
		this.portRoueDroite = new EV3LargeRegulatedMotor(MotorPort.B);// Initialise le moteur droit
		//Permet d'obtenir le diamètre des roues avec offset permettant d'indiquer la distance horizontale entre la roue et l'axe central du robot
		this.roueGauche = WheeledChassis.modelWheel(portRoueGauche, 56).offset(-70);
		this.roueDroite = WheeledChassis.modelWheel(portRoueDroite, 56).offset(70);
		this.chassis = new WheeledChassis(new Wheel[] { roueGauche, roueDroite }, WheeledChassis.TYPE_DIFFERENTIAL);
		this.pilot = new MovePilot(chassis);
		setVitesse(vitesse);

		//partie pour les pinces
		this.pinces = new EV3LargeRegulatedMotor(MotorPort.D);// Initialise le moteur des pinces
		pinces.setSpeed(pinces.getMaxSpeed()); // Permet au moteur de fonctionner à sa vitesse maximale.
		this.estOuverte = false; // État initial des pinces

	}


	//---------------------------------------------------------Méthodes pour les roues------------------------------------------------------------------------

	//Permet de déterminer la vitesse que l'on souhaite en fonction du paramètre v (je suis pas sur de la structure de la méthode qu'elle marche comme ça)
	public void setVitesse(String v) {
		if ("rapide".equals(v)) {
			pilot.setLinearAcceleration(300);
			pilot.setAngularAcceleration(2000);
			pilot.setAngularSpeed(1000);
		} else if ("moyen".equals(v)) {
			pilot.setLinearAcceleration(150);
			pilot.setAngularAcceleration(1000);
		} else if ("lent".equals(v)) {
			pilot.setLinearAcceleration(75);
			pilot.setAngularAcceleration(150);
		} else if ("best".equals(v)) {
			pilot.setLinearAcceleration(120);
			pilot.setAngularAcceleration(400);
			pilot.setAngularSpeed(280);
		} else if ("tour".equals(v)) {
			pilot.setAngularSpeed(35);
		}
	}

	
	public void avancer_deS(double distance) {
		pilot.travel(distance, true);
	}

	// Permet de faire avancer le robot en fonction de distance pris en paramètre
	public void avancer_deA(double distance) {
		pilot.travel(distance, false);
	}

	public void reculer_deS(double distance) {
		pilot.travel(-distance, false);
	}
	//Permet de faire reculer le robot en fonction de distance pris en paramètre
	public void reculer_deA(double distance) {
		pilot.travel(-distance, false);
	}

	public void tourner_deS(double angle) {
		pilot.rotate(angle, true);

	}
	//Permet de faire tourner le robot en fonction du degré de l'angle pris en paramètre
	public void tourner_deA(double angle) {
		pilot.rotate(angle, false);

	}


	//Retourne l'instance pilot
	public MovePilot getPilot() {
		return pilot;
	}

	// Permet d'arrêter le robot
	public void stop() {
		pilot.stop();
	}

	//Retourne true si le robot est en mouvement
	public boolean getisMoving() {
		return pilot.isMoving();
	}

	public void mouvement_aleatoire() {
		Random random = new Random();
		//Choisit un chiffre aléatoire entre 0cm et 100cm pour la distance
		double distance = random.nextDouble()*100;
		//Choisit un chiffre entre 0 et 360 degré pour l'angle
		double angle = random.nextDouble()*360;
		//Renvoi true ou false aléatoirement
		boolean direction = random.nextBoolean();

		System.out.println("Distance aléatoire : " + distance);
		System.out.println("Angle aléatoire : " + angle);
		System.out.println("Direction : " + direction);

		//Si true tourne à gauche donc la valeur de l'angle en positif
		if(direction = true) {
			double gauche = angle;
			tourner_deS(gauche);
			avancer_deS(distance);
		}

		//Si false tourne à droite donc la valeur de l'angle en négatif
		if(direction = false) {
			double droite = angle*(-1);
			tourner_deS(droite);
			avancer_deS(distance);

		}   
	}


	//---------------------------------------------------------Méthodes pour les pinces------------------------------------------------------------------------

	/*ancienne méthode
	 * public void ouvrir_pince() {
         if(estOuverte == true) {
              System.out.print("On ne peut pas ouvrir les pinces car elles sont déjà ouvertes");
              Delay.msDelay(1000);
         }
         else {
              Motor.D.setSpeed(MOTOR_SPEED_PINCE); // Régle la vitesse du moteur
              Motor.D.rotate(1000); // Ouvre les pinces à 90 degrés
              estOuverte = true;
              System.out.println("Les pinces sont maintenant ouvertes.");
              Delay.msDelay(1000);
         }
     }

     ancienne méthode
	 * public void fermer_pince() {

         if(isClawOpen == false) {
              System.out.print("Les pinces sont déjà fermées.");
              Delay.msDelay(1000);
         }
         else {
              Motor.D.setSpeed(MOTOR_SPEED_PINCE); // Régle la vitesse du moteur
              Motor.D.rotate(-1000); // Ferme les pinces (rotation inverse)
              isClawOpen = false;
              Delay.msDelay(1000);
              System.out.println("Les pinces sont maintenant fermées.");
         }
     }*/

	//Permet de renvoyé l'instance est ouverte pour savoir l'état des pinces
	public boolean getEstOuvert() {
		return this.estOuverte;
	}

	// Permet l'ouverture des pinces
	public void ouvrir_pinces() {
		pinces.rotate(1000);// Ouvre les pinces à 90 degrés
		estOuverte = true;
	}

	//
	public void fermer_pinces() {
		pinces.rotate(-1000);// Ferme les pinces (rotation inverse)
		estOuverte = false;

	}

	//Permet au robot d'attraber le palet
	public void attraper_palet() { 
		this.stop();
		this.ouvrir_pinces();
		this.avancer_deS(320); 
		this.fermer_pinces();
	}

	//Permet au robot d'éviter un obstacle
	public void eviter_obstacle() {
		// je sais pas comment faire pour choisir de tourner à droite ou à gauche 
		this.tourner_deS(-90);
		this.avancer_deS(TAILLE_ROBOT);
		this.tourner_deS(90);
		this.avancer_deS(TAILLE_ROBOT);
	}

	//Permet de s'arrêter lorsque le robot n'a pas de palet
	public void sarreter_sans_palet() {
		this.stop();
		this.eviter_obstacle();
	}

	/*
     public void avancer_sans_palet(){
         Capteurs c = new Capteurs();
         while(c.detecterDiscontinuite()== null) {
              this.avancer_de(50);
         }
     }*/




}