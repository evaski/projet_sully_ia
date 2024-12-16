package sully;
        
import java.util.ArrayList;
import java.util.List;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.filter.MeanFilter;

public class Capteurs {
       // Constantes
       public final static int nombreTotalPoints = 100;
       public final static int intervalleMS = 15;
       // Attributs
       private double distanceInitiale; // Distance initiale mesurée
       private double distanceFinale; // Distance finale mesurée
       
       private EV3UltrasonicSensor ultrasonicSensor; // Capteur pour mesurer la distance
       private EV3TouchSensor pressionSensor; // Capteur pour détecter la pression
       private double orientation; // Orientation actuelle du robot (en degrés)
       
       private Actions robot;
       private EV3ColorSensor colorSensor; // Capteur de couleur
       // Constructeur
       public Capteurs() {
               // Initialisation des attributs
               this.distanceInitiale = 0.0;
               this.distanceFinale = 0.0;
               pressionSensor = new EV3TouchSensor(SensorPort.S2); // Initialisation du capteur tactile sur le port S2
               distances = new ArrayList<>(); // Initialisation de la liste des distances
               this.orientation = 0.0; // Orientation initiale définie à 0
               this.historiqueOrientations = new ArrayList<>(); // Initialisation de l'historique des orientations
               ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S3); // Initialisation du capteur de couleur sur le port S4
       }
     //Méthode pour obtenir la couleur détectée par le capteur de couleur
       public int get_couleur() {
               colorSensor.setFloodlight(true);
               int couleur = colorSensor.getColorID();
               return couleur;
       }
          //Méthode pour obtenir la distance mesurée par le capteur ultrasonique
       public double getDistance() {
               SampleProvider distanceProvider = ultrasonicSensor.getDistanceMode();
               float[] sample = new float[distanceProvider.sampleSize()];
               distanceProvider.fetchSample(sample, 0);
               return sample[0]; // Retourne la distance mesurée en mètres
       }
     //Méthode pour obtenir l'historique des distances mesurées
            
                //Méthode pour obtenir la pression détectée par le capteur tactile
       public float get_pression() {
               SampleProvider pressionProvider = pressionSensor.getMode(0); // Mode de lecture analogique
               float[] sample = new float[pressionProvider.sampleSize()];
               pressionProvider.fetchSample(sample, 0);
               pressionSensor.close(); // Fermer le capteur tactile
               return sample[0]; // Retourne la valeur mesurée
       }
     }
