package sully;

import java.util.ArrayList;
import java.util.Random;
import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.utility.Delay;

/**
 * Fichier git !!!!!!!!!
 */
public class Actions {

	private static final double WHEEL_DIAMETER = 5.6; // Diamètre de la roue en cm
	private static final double ROBOT_TRACK_WIDTH = 11.2; // Distance entre les roues en cm
	private static final int MOTOR_SPEED_PINCE = 1500; // Vitesse des moteurs en degrés par seconde
	private static final double TAILLE_ROBOT = 33; // A CHANGER AVEC UNE MESURE PLUS PRÉCISE 

	private static final int MOTOR_SPEED_ROUES = 1000; // A NE PAS CHANGER 


	private NXTRegulatedMotor leftMotor;      // Moteur gauche
	private NXTRegulatedMotor rightMotor;     // Moteur droit
	private NXTRegulatedMotor clawMotor;      // Moteur pour les pinces

	private boolean isClawOpen;   // État des pinces 

	private int motorSpeed;       // Vitesse des moteurs, réglable
	private double distanceThreshold; // Seuil pour la détection d'un obstacle ou d'un palet 
	// Position du robot (optionnel, si tu veux gérer la localisation)
	private double positionX;     // Position X
	private double positionY;     // Position Y
	private double currentAngle;   // Angle actuel du robot;
	private ArrayList<Object> distanceValues;
	private ArrayList<Object> orientationValues;

	public Actions() {
		this.leftMotor = Motor.A; // Initialise le moteur gauche (j'ai vérifié c'est bien ça) 
		this.rightMotor = Motor.B; // Initialise le moteur droit
		this.clawMotor = Motor.D; // Initialise le moteur des pinces
		this.isClawOpen = false; // État initial des pinces
		this.motorSpeed = 100; // Vitesse par défaut
		this.distanceThreshold = 20.0; // Seuil par défaut pour la détection (À changer peut être) 
		this.distanceValues = new ArrayList<>(); // Liste pour les distances
		this.orientationValues = new ArrayList<>(); // Liste pour les orientations
		this.positionX = 0.0; // Position initiale (À changer peut être) 
		this.positionY = 0.0; // Position initiale (À changer peut être) 
		this.currentAngle = 0.0; // Angle initial

	}

	// Méthode permettant d'ouvrir les pinces
	public void ouvrir_pince() {
		if(isClawOpen == true) {
			System.out.print("On ne peut pas ouvrir les pinces car elles sont déjà ouvertes");
			Delay.msDelay(1000);
		}
		else {
			Motor.D.setSpeed(MOTOR_SPEED_PINCE); // Régle la vitesse du moteur
			Motor.D.rotate(1000); // Ouvre les pinces à 90 degrés
			isClawOpen = true; 
			System.out.println("Les pinces sont maintenant ouvertes.");
			Delay.msDelay(1000);
		}
	}

	// Méthode permettant de fermer les pinces
	public void fermer_pince() {
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
	}

	// Méthode permettant au robot de tourner d'un certain angle
	public void tourner_de(double angle) {
		double wheelCircumference = Math.PI * WHEEL_DIAMETER; // Circonférence de la roue

		// Calculer le nombre de rotations nécessaires pour tourner l'angle spécifié
		// (Angle / 360) * (robotWidth / wheelDiameter) pour convertir l'angle en distance
		double distanceToRotate = (angle / 360) * Math.PI * ROBOT_TRACK_WIDTH;

		// Convertir la distance à tourner en rotations de la roue
		double rotationsNeeded = distanceToRotate / wheelCircumference; // Nombre de rotations nécessaires
		int degreesToRotate = (int) (rotationsNeeded * 360); // Convertir en degrés

		// Réglage de la vitesse des moteurs
		leftMotor.setSpeed(MOTOR_SPEED_ROUES);
		rightMotor.setSpeed(MOTOR_SPEED_ROUES);

		// Tourner à gauche (moteur droit tourne en avant, moteur gauche en arrière)
		leftMotor.rotate(-degreesToRotate, true);  // Moteur gauche tourne à l'envers
		rightMotor.rotate(degreesToRotate, false);  // Moteur droit tourne en avant

		System.out.println("Rotation de " + angle + " degrés."); 
	}

	public boolean isMoving() {
		return this.isMoving();
	}

	// Méthode permettant au robot d'avancer d'une distance spécifique 
	public void avancer_de(double distance) {
		if (distance <= 0) {
			throw new IllegalArgumentException("La distance doit être prossitive");

		}

		// Calculer la rotation nécessaire pour parcourir la distance demandée
		double wheelCircumference = Math.PI * WHEEL_DIAMETER;
		double rotationsNeeded = distance / wheelCircumference;
		int motorDegrees = (int) (rotationsNeeded * 360); // Convertir les rotations en degrés

		Motor.A.setSpeed(MOTOR_SPEED_ROUES); 
		Motor.B.setSpeed(MOTOR_SPEED_ROUES);


		Motor.A.rotate(motorDegrees, true); // Lancer le moteur A en mode asynchrone
		Motor.B.rotate(motorDegrees);

		Delay.msDelay(1000); 
	}

	// méthode permettant au robot d'avancer d'une distance spécifique 
	public void reculer_de(double distance) {
		// Calculer le nombre de rotations nécessaires
		double rotations = distance / WHEEL_DIAMETER;
		int degrees = (int) (rotations * 360); // Convertir en degrés

		// Faire reculer les moteurs
		Motor.A.backward();
		Motor.B.backward();

		// Calculer le temps nécessaire pour reculer
		int speed = 500; // Vitesse en degrés par seconde (à ajuster selon vos besoins)
		int timeInMillis = (int) ((degrees / speed) * 1000); // Temps en millisecondes

		// Attendre que le robot recule la distance spécifiée
		Delay.msDelay(timeInMillis);

		// Arrêter les moteurs
		Motor.A.stop();
		Motor.B.stop();
	}

	// Méthode permettant de faire un mouvement aléatoire
	public void mouvement_aleatoire() {
		Random random = new Random();
		//Choisit un chiffre aléatoire entre 0cm et 100cm pour la distance 
		double distance = random.nextDouble()*100;
		//Choisit un chiffre entre 0 et 360 degré pour l'angle
		double angle = random.nextDouble()*360;
		//Renvoi un true ou false aléatoire
		boolean direction = random.nextBoolean();

		System.out.println("Distance aléatoire : " + distance);
		System.out.println("Angle aléatoire : " + angle);
		System.out.println("Direction : " + direction);

		//Si true tourne à gauche donc la valeur de l'angle en positif
		if(direction = true) {
			double gauche = angle;
			tourner_de(gauche);
			avancer_de(distance);
		}

		//Si false tourne à droite donc la valeur de l'angle en négatif 
		if(direction = false) {
			double droite = angle*(-1);
			tourner_de(droite);
			avancer_de(distance);

		}	 
	}

	// Permet d'orienter le robot vers le nord (vers la ligne blanche) 
	public void tourner_vers_nord() {
		//prend la dernière valeur du tab double de la fonction stockerOrientation()
		/* Capteurs c = new Capteurs();
	double tab = c.stocker_orientation();
	double valeur = c[-1]; 

	double newValeur = valeur*(-1);
	tourner_de(newValeur);
		 */


	}

	public void avancer_sans_palet() {

	}


	public void sarreter_sans_palet(){}

	public void attraper_palet() {
		/*Capteurs c = new Capteurs(); 
		if(c.get_distance() <= 32 ) { // À changer quand on saura à quelle distance le robot il ne détecte plus le palet, pas ça en fait la contition parce que il va arreter de le détecter du coup il y a une autre condition 
				this.sarreter_sans_palet();
				this.ouvrir_pince(); 
				this.avancer_de(32); // pareil il faut changer pour voir si c'est bien ça
				this.fermer_pince(); 
				}
			else {this.avancer_sans_palet(); }*/ 
	}

	public void eviter_obstacle() {
		/*Capteurs c = new Capteurs(); 
		  double tab = c.stocker_distance();
			if( tab[-1] <= 32 ) // Comment je choisis si je tourne à droite ou à gauche 
				this.tourner_de(-90);
				this.avancer_de(TAILLE_ROBOT);
				this.tourner_de(90); 
			} */


	}

	public void stop() {
		leftMotor.stop(true);
		rightMotor.stop(true);
	}

	//Méthode permettant de chercher un palet en faisant tourner le robot sur lui-même et en détectant un discontinuité 
	public boolean rechercher_palet() {
		Capteurs c = new Capteurs(); 
		double valeurActuelle = c.getDistance();
		this.mouvement_aleatoire();
		Delay.msDelay(20);
		double valeurRecente = c.getDistance();
		while ((valeurRecente > valeurActuelle || valeurRecente == valeurActuelle)&& this.isMoving()) {
			valeurActuelle = valeurRecente;
			valeurRecente = c.getDistance();
			if (valeurActuelle - valeurRecente > 5) {
				this.stop();
				if (valeurRecente < 65) this.tourner_de(12);
				return true;
			}
			if (valeurRecente < valeurActuelle) {
				while ((valeurRecente < valeurActuelle || valeurRecente == valeurActuelle) && this.isMoving()) {
					if (valeurActuelle - valeurRecente > 5) {
						this.stop();
						if (valeurRecente < 65) this.tourner_de(12);
						return true;
					}
					valeurActuelle = valeurRecente;
					valeurRecente = c.getDistance();
				}
			}
			Delay.msDelay(20);
		}
		return false; //si échec de la méthode // cas supposé où les palets ne sont pas assez proche ou pas trouvé
	}

	// Méthode permettant de réinitialiser la mesure de l'angle 
	public void reinitialisation_angle() {
	}
}





