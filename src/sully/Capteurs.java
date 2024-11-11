package sully;

import java.util.ArrayList;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;


public class Capteurs {
	// Constantes 
	final static int nombreTotalPoints = 100;
	final static int intervalleMS = 15;  

	// Attributs 
	private double distanceInitiale;
	private double distanceFinale;
	private ArrayList<Double> distances;
	private EV3UltrasonicSensor ultrasonicSensor;
	private EV3TouchSensor pressionSensor;

	private Actions robot; 

	// Constructeur
	public Capteurs() {
		// Initialisation des attributs
		this.distanceInitiale = 0.0;
		this.distanceFinale = 0.0;
		ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
		pressionSensor = new EV3TouchSensor(SensorPort.S3);
		distances = new ArrayList<>();
	}


	public double getDistance() {
		SampleProvider distanceProvider = ultrasonicSensor.getDistanceMode();
		float[] sample = new float[distanceProvider.sampleSize()];
		distanceProvider.fetchSample(sample, 0);
		return sample[0]; // La distance mesurée en mètres
	}

	public int getCouleur() {
		return 0; 
	}

	// ATTENTION pour l'instant je peux stocker les distances que quand je tourne il faudra voir si on en a besoin a un autre moment 
	public double tempsPourTourner(double angle) {
		long startTime = System.currentTimeMillis(); // Démarre le chronomètre
		robot.tourner_de(angle); 
		long endTime = System.currentTimeMillis(); // Arrête le chronomètre

		long tempsEcoule = endTime - startTime; // Calcule le temps écoulé
		return tempsEcoule; 
	}

	public ArrayList<Double> stockerDistance(int temps){

		long startTime = System.currentTimeMillis();
		distances.clear();
		while (System.currentTimeMillis() - startTime < temps) { 
			distances.add(this.getDistance()); 

			try {
				Thread.sleep(intervalleMS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// permet d'attendre le prochain intervalle 

		return distances ; 
	}

	public List<Integer> detecterDiscontinuite(){
		int l = distances.size();
		List<Integer> indicesDiff = new ArrayList<>();
		int seuil = 10; // A CHANGER JE SAIS PAS DU TOUT COMMENT ON VA DEFINIR CE SEUIL 

		for (int i = 0; i< l; i++) {
			float diff = (float) Math.abs(distances.get(i+1) - distances.get(i));
			if (diff > seuil) {
				indicesDiff.add(i);
			}
		}
		return indicesDiff;
	}


	public void closeUltrasonicSensor() {
		ultrasonicSensor.close(); // Fermer le capteur lorsqu'il n'est plus nécessaire
	}

	public float get_pression() {
		SampleProvider pressionProvider = pressionSensor.getMode(0); // Mode de lecture analogique
		float[] sample = new float[pressionProvider.sampleSize()];
		pressionProvider.fetchSample(sample, 0);
		return sample[0];
	}

	public void closepressionSensor() {
		pressionSensor.close(); // Fermer le capteur lorsque ce n'est plus nécessaire
	}

	// Méthode privée pour lire la distance (simule la lecture d'un capteur)
	private double lireDistance() {
		// Code pour lire la distance depuis le capteur à ultrasons
		// Simule une valeur aléatoire pour l'exemple
		return this.getDistance();  // À remplacer par la lecture réelle du capteur
	}
	// Méthode pour calculer l'angle de rotation en degrés avec la trigonométrie
	/*public double getAngleTrigo() {
				// Mesure de la distance avant la rotation
				this.distanceInitiale = this.lireDistance();
				System.out.println("Distance initiale: " + distanceInitiale + " cm");
				// Effectuer la rotation du robot (simulation)
				// Ajoutez ici le code pour tourner le robot
				// Mesure de la distance après la rotation
				this.distanceFinale = lireDistance();
				System.out.println("Distance finale: " + distanceFinale + " cm");
				// Vérification que les distances ne sont pas nulles pour éviter la division par zéro
				if (distanceInitiale == 0 || distanceFinale == 0) {
					System.err.println("Les distances ne peuvent pas être égales à zéro.");
					return -1; // Indiquer une erreur
				}
				// Calcul de l'angle de rotation en utilisant la trigonométrie
				double cosTheta = distanceFinale / distanceInitiale;
				double angleRadians = Math.acos(cosTheta); // Calcul de l'angle en radians
				double angleDegres = Math.toDegrees(angleRadians); // Conversion en degrés
				System.out.printf("Angle de rotation: %.2f degrés%n", angleDegres);
				return angleDegres;
			}*/ 

	// Méthode pour calculer l'angle de rotation en degrés à partir du nombre de points mesurés
	public double getAnglePoints(int nombrePointsMesures) {
		// Vérification que le nombre total de points est supérieur à zéro
		if (nombreTotalPoints <= 0) {
			System.err.println("Le nombre total de points doit être supérieur à zéro.");
			return -1; // Indiquer une erreur
		}
		// Vérification que le nombre de points mesurés est valide
		if (nombrePointsMesures < 0 || nombrePointsMesures > nombreTotalPoints) {
			System.err.println("Le nombre de points mesurés doit être compris entre 0 et " + nombreTotalPoints);
			return -1; // Indiquer une erreur
		}
		// Calcul de l'angle par produit en croix
		double angleMesure = ((double) nombrePointsMesures / nombreTotalPoints) * 360.0;
		//System.out.printf("Angle mesuré: %.2f degrés%n", angleMesure);
		return angleMesure;
	}

	/* public double choisir_methode_mesure_dangle(int nombrePointsMesures) {
				// Calcul des angles à partir des deux méthodes
				double angleTrigo = getAngleTrigo();
				double anglePoints = getAnglePoints(nombrePointsMesures);
				// Vérification des erreurs pour les deux méthodes
				if (angleTrigo == -1 || anglePoints == -1) {
					System.err.println("Erreur lors du calcul des angles.");
					return -1; // Indiquer une erreur si l'un des calculs a échoué
				}
				// Comparaison des deux angles
				double difference = Math.abs(angleTrigo - anglePoints);
				//System.out.printf("Différence entre les angles: %.2f degrés%n", difference);
				// Privilégier la méthode basée sur les points si la différence est significative
				if (difference > 10.0) { // Par exemple, une différence de plus de 10 degrés
					//System.out.println("Utilisation de la méthode getAnglePoints");
					return anglePoints; // Retourner l'angle basé sur les points
				} else {
					System.out.println("Utilisation de la méthode getAngleTrigo");
					return angleTrigo; // Retourner l'angle basé sur les distances
				}
			}*/ 

}


