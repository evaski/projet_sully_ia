package sully;

import java.util.Random;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.*;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
     
public class Actions {
       private static final double TAILLE_ROBOT = 330;
       public final static int intervalleMS = 15;
       //Attribue pour les roues
       private String vitesse; // Niveau de vitesse actuel (rapide, moyen, lent)
       private MovePilot pilot;
       private EV3LargeRegulatedMotor portRoueGauche; // Moteur de la roue gauche
       private EV3LargeRegulatedMotor portRoueDroite; // Moteur de la roue droite
       private Wheel roueGauche;
       private Wheel roueDroite;
       private Chassis chassis;
       // Attributs pour les pinces
       private EV3LargeRegulatedMotor pinces; // Moteur contrôlant les pinces
       private boolean estOuverte;   // État des pinces (ouvertes ou fermées)
       private static int boussole;
       //Constructeur initialisant les attributs pour les pinces et les moteurs
       public Actions(String vitesse) {
       	// Initialisation des moteurs des roues
       	this.portRoueGauche = new EV3LargeRegulatedMotor(MotorPort.A); //Initialise le moteur gauche
       	this.portRoueDroite = new EV3LargeRegulatedMotor(MotorPort.B);// Initialise le moteur droit
       	//Permet d'obtenir le diamètre des roues avec offset permettant d'indiquer la distance horizontale entre la roue et l'axe central du robot
       	this.roueGauche = WheeledChassis.modelWheel(portRoueGauche, 56).offset(-70);
       	this.roueDroite = WheeledChassis.modelWheel(portRoueDroite, 56).offset(70);
           this.chassis = new WheeledChassis(new Wheel[] { roueGauche, roueDroite }, WheeledChassis.TYPE_DIFFERENTIAL);
           this.pilot = new MovePilot(chassis);
           //Configuration de la vitesse
           setVitesse(vitesse);
           this.vitesse = vitesse;
           // Initialisation du moteur des pinces
           this.pinces = new EV3LargeRegulatedMotor(MotorPort.D);// Initialise le moteur des pinces
           pinces.setSpeed(pinces.getMaxSpeed()); // Permet au moteur de fonctionner à sa vitesse maximale
           this.estOuverte = false; // État initial des pinces
       }
      
       /*
        * Méthodes pour les roues
        */
       //Permet de déterminer la vitesse que l'on souhaite en fonction du paramètre v
       public int getBoussole() {
               return boussole;
       }
       public void setBoussole(int nouvelle_boussole) {
               boussole = nouvelle_boussole;
       }
       public String getVitesse() {
               return this.vitesse;
       }
       public void setVitesse(String v) {
               if ("rapide".equals(v)) {
                       pilot.setLinearAcceleration(300);
                       pilot.setAngularAcceleration(2000);
                       pilot.setAngularSpeed(1000);
               }
               else if ("lent".equals(v)) {
                       pilot.setAngularSpeed(35);
               }
       }
       // Permet d'avancer et de faire une autre action en simultané
       public void avancer_deS(double distance) {
               pilot.travel(distance, true);
       }
       // Permet de faire avancer le robot en fonction de distance pris en paramètre
       public void avancer_deA(double distance) {
               pilot.travel(distance, false);
       }
       // Permet de reculer et de faire une autre action en simultané
       public void reculer_deS(double distance) {
               pilot.travel(-distance, true);
       }
       //Permet de faire reculer le robot en fonction de distance pris en paramètre
       public void reculer_deA(double distance) {
               pilot.travel(-distance, false);
       }
       // Permet de tourner et de faire une autre action en simultané
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
       // Permet d'arrêter immédiatement le robot
       public void stop() {
               pilot.stop();
       }
       //Vérifie si le robot est en mouvement
       public boolean getisMoving() {
               return pilot.isMoving();
       }
       //Fait effectuer au robot des mouvements aléatoires
       public void mouvement_aleatoire() {
               Random random = new Random();
               double distance = random.nextDouble()*100; // Distance aléatoire entre 0 et 100 cm
               double angle = random.nextDouble()*360; // Angle aléatoire entre 0 et 360 degrés
               boolean direction = random.nextBoolean(); // Direction aléatoire
	       //System.out.println("Distance aléatoire : " + distance);
               System.out.println("Angle aléatoire : " + angle);
               //System.out.println("Direction : " + direction);
               //Si true tourne à gauche donc la valeur de l'angle en positif
               double gauche = angle;
               this.setVitesse("rapide");
               avancer_deA(distance);
               this.setVitesse("lent");
               tourner_deS(gauche);
               while (this.getisMoving()== true) {
                       boussole+= 1;
                       Delay.msDelay(intervalleMS);
               }
       }
       /*
        * Méthodes pour les pinces
        */
      
       // Retourne l'état des pinces (ouvertes ou fermées)
       public boolean getEstOuvert() {
               return this.estOuverte;
       }
       // Retourne l'état des pinces (ouvertes ou fermées)
       public void ouvrir_pinces() {
               pinces.rotate(1000);// Ouvre les pinces à 90 degrés
               estOuverte = true;
       }
       //Ferme les pinces
       public void fermer_pinces() {
               pinces.rotate(-1000);
               estOuverte = false;
       }
       //Permet au robot d'attraper un palet
       public void attraper_palet() {
               this.stop();
               this.ouvrir_pinces();
               this.avancer_deA(320);
       }
       // Permet au robot de déposer un palet
       public void deposer_palet() {
               this.ouvrir_pinces();
               this.reculer_deA(TAILLE_ROBOT);
               this.tourner_deA(180);
       }
       //Permet au robot d'éviter un obstacle
       public void eviter_obstacle() {
               this.tourner_deA(-90);
               this.avancer_deA(TAILLE_ROBOT);
               this.tourner_deA(90);
               this.avancer_deA(TAILLE_ROBOT);
       }
}
