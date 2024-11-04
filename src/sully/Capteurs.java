package sully;
public class Capteurs {
	// Attributs privés
	private double distanceInitiale;
	private double distanceFinale;
	private int nombreTotalPoints;
	
	private EV3UltrasonicSensor ultrasonicSensor;
	private EV3TouchSensor pressionSensor;

	// Constructeur
	public Capteurs(int nombreTotalPoints) {
		// Initialisation des attributs
		this.distanceInitiale = 0.0;
		this.distanceFinale = 0.0;
		this.nombreTotalPoints = nombreTotalPoints;
		ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S1);
		pressionSensor = new EV3TouchSensor(SensorPort.S3);
	}
	// Méthode privée pour lire la distance (simule la lecture d'un capteur)
	private double lireDistance() {
		// Code pour lire la distance depuis le capteur à ultrasons
		// Simule une valeur aléatoire pour l'exemple
		return Math.random() * 100; // À remplacer par la lecture réelle du capteur
	}
	// Méthode pour calculer l'angle de rotation en degrés avec la trigonométrie
	public double getAngleTrigo() {
		// Mesure de la distance avant la rotation
		this.distanceInitiale = lireDistance();
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
	}
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
		System.out.printf("Angle mesuré: %.2f degrés%n", angleMesure);
		return angleMesure;
	}
	
	public float getDistance() {
		SampleProvider distanceProvider = ultrasonicSensor.getDistanceMode();
		float[] sample = new float[distanceProvider.sampleSize()];
		distanceProvider.fetchSample(sample, 0);
		return sample[0]; // La distance mesurée en mètres
	}

	public void closeSensor() {
		ultrasonicSensor.close(); // Fermer le capteur lorsqu'il n'est plus nécessaire
	}


	

	/**
	 * Méthode pour récupérer la valeur de la pression à un instant t.
	 * @return pression en unités analogiques (exemple en volts ou autre unité selon le capteur)
	 */
	public float get_pression() {
		SampleProvider pressionProvider = pressionSensor.getMode(0); // Mode de lecture analogique
		float[] sample = new float[pressionProvider.sampleSize()];
		pressionProvider.fetchSample(sample, 0);
		return sample[0];
	}

	public void closeSensors() {
		pressionSensor.close(); // Fermer le capteur lorsque ce n'est plus nécessaire
	}
}


