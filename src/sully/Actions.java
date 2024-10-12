package sully;

import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;

public class Actions {

	private static final double WHEEL_DIAMETER = 5.6; // Diamètre de la roue en cm
	private static final double ROBOT_TRACK_WIDTH = 11.2; // Distance entre les roues en cm
	private static final int MOTOR_SPEED = 300; // Vitesse des moteurs en degrés par seconde


	private Motor leftMotor;      // Moteur gauche
	private Motor rightMotor;     // Moteur droit
	private Motor clawMotor;      // Moteur pour les pinces

	// Attribut pour suivre l'état des pinces
	private boolean isClawOpen;   // État des pinces (ouvertes ou fermées)

	// Vitesse des moteurs
	private int motorSpeed;       // Vitesse des moteurs, réglable

	// Seuils pour la détection
	private double distanceThreshold; // Seuil pour la détection d'un obstacle ou d'un palet

	// Position du robot (optionnel, si tu veux gérer la localisation)
	private double positionX;     // Position X
	private double positionY;     // Position Y
	private double currentAngle;   // Angle actuel du robot;

	public Actions() {
		this.leftMotor = Motor.B; // Initialise le moteur gauche
		this.rightMotor = Motor.C; // Initialise le moteur droit
		this.clawMotor = Motor.A; // Initialise le moteur des pinces
		this.isClawOpen = false; // État initial des pinces
		this.motorSpeed = 100; // Vitesse par défaut
		this.distanceThreshold = 20.0; // Seuil par défaut pour la détection
		this.distanceValues = new ArrayList<>(); // Liste pour les distances
		this.orientationValues = new ArrayList<>(); // Liste pour les orientations
		this.positionX = 0.0; // Position initiale
		this.positionY = 0.0; // Position initiale
		this.currentAngle = 0.0; // Angle initial    

	}
<<<<<<< HEAD
	
	public void ouvrir_pince() {
		if(isClawOpen == True) {
			System.out.print("On ne peut pas ouvrir les pinces car elles sont déjà ouvertes");
		}
		else {
			 Motor.A.setSpeed(100); // Régler la vitesse du moteur
	         Motor.A.rotate(90); // Ouvrir les pinces à 90 degrés
	         isClawOpen = true; // Met à jour l'état
	         System.out.println("Les pinces sont maintenant ouvertes.");
		}
	}
	
	public void fermer_pince() {
		if(isClawOpen == False) {
			System.out.print("les pinces sont déjà fermées.")
		}
		else {
			Motor.A.setSpeed(100); // Régler la vitesse du moteur
            Motor.A.rotate(-90); // Fermer les pinces (rotation inverse)
            isClawOpen = false; // Met à jour l'état
            System.out.println("Les pinces sont maintenant fermées.");
		}
	}
	
	public void tourner_de(double angle) {
		
	}
}
=======

	// méthode permettant au robot d'avancer d'une distance spécifique 
	public void avancer_de(double distance) {
		if (distance <= 0) {
			throw new throw IllegalArgumentException("La distnace doit être prossitive");

		}



	}
>>>>>>> branch 'master' of https://github.com/evaski/projet_sully_ia.git


