package sully;

import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;

public class Actions {
<<<<<<< HEAD



=======
	
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
>>>>>>> branch 'master' of https://github.com/evaski/projet_sully_ia

}


