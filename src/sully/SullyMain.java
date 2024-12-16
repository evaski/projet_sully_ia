package sullybis;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;     
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.utility.Delay;

public class SullyMain{
       public final static int intervalleMS = 15;
       public final static float seuil = (float) 0.05;
       public final static int nombreTotalPoints_lent = 675;
       public enum Etat{
               Debut,
               RechercherPalet,
               PrendrePalet,
               RamenerPalet,
               Fin
       }
       public static void run(Actions robot, Capteurs capteurs) {
               Etat etat = Etat.Debut;
               //Etat etat = Etat.RechercherPalet;
               while(true) {
                       switch(etat) {
                       case Debut:
                               robot.setVitesse("tour");
                               robot.ouvrir_pinces();
                               robot.avancer_deA(650);//avancer jusqu'au palet à mesure
                               robot.fermer_pinces();
                               robot.setVitesse("rapide");
                               robot.tourner_deA(30);
                               robot.avancer_deA(350);
                               robot.tourner_deA(-25);
                               robot.avancer_deA(1600);
                               robot.ouvrir_pinces();
                               robot.reculer_deA(200);
                               robot.fermer_pinces();
                               robot.tourner_deA(-135);
                               robot.ouvrir_pinces();
                               robot.avancer_deA(320);
                               robot.fermer_pinces();
                               robot.tourner_deA(150);
                               robot.avancer_deA(500);
                               robot.ouvrir_pinces();
                               robot.reculer_deA(300);
                               robot.fermer_pinces();
                               robot.tourner_deA(-170);
                               robot.avancer_deA(500);
                               robot.attraper_palet();
                               robot.fermer_pinces();
                               robot.tourner_deA(170);
                               robot.avancer_deA(1150);
                               robot.ouvrir_pinces();
                               robot.reculer_deA(300);
                               robot.fermer_pinces();
                               robot.tourner_deA(180);
                               robot.setBoussole(378);
                               robot.avancer_deA(200);
                               etat = Etat.RechercherPalet;
                       case RechercherPalet:
                               if(rechercher_palet(robot,capteurs, "lent") == true) {
                                       etat = Etat.PrendrePalet;
                               }
                               robot.mouvement_aleatoire();
                       case PrendrePalet:
                               robot.avancer_deS(1700);
                               while(capteurs.getDistance() > 0.32) {}
                               robot.attraper_palet();
                               robot.avancer_deS(0.10);
                               if(capteurs.get_pression() > 0) {
                                       robot.fermer_pinces();
                                       etat = Etat.RamenerPalet;
                               }
                               else {
                                       robot.fermer_pinces();
                                       etat = Etat.RechercherPalet;
                               }
                       case RamenerPalet:
                               double angle = getAnglePoints(robot.getBoussole(), robot, "lent");
                               robot.tourner_deA(-angle);
                               robot.avancer_deS(1500);
                               while(capteurs.getDistance()> 0.32) {} // il faut encore ajouter si on rencontre un obstacle
                               robot.stop();
                               robot.deposer_palet();
                               robot.fermer_pinces();
                       }
               }
       }
       public static boolean rechercher_palet(Actions robot, Capteurs capteurs, String vitesse) {
               robot.setVitesse("lent");
               int boussoleTemp = robot.getBoussole();
               double distance_courrante = capteurs.getDistance();
               boussoleTemp +=1;
               robot.tourner_deS(360); // Robot tourne sur lui-même
               // Attente avant une nouvelle mesure
               while (robot.getisMoving() == true) {
                       double nouvelle_distance = capteurs.getDistance();
                       boussoleTemp += 1; // Incrémenter la boussole pour chaque étape
                       Delay.msDelay(intervalleMS);
                       // Afficher les mesures en cours
                       if (Math.abs(distance_courrante - nouvelle_distance) > seuil && nouvelle_distance > 0.65) {
                               robot.stop();
                               robot.setBoussole(boussoleTemp);
                               System.out.print(boussoleTemp);
                               return true;
                       }
                       distance_courrante = nouvelle_distance; // Mise à jour pour la prochaine itération
               }
               // Mise à jour finale de la boussole si rien n'a été trouvé
               robot.setBoussole(boussoleTemp);
               System.out.println("Boussole lente finale (palet non trouvé) : " + robot.getBoussole());
               return false; // Palet non trouvé
       }
       public static double getAnglePoints(int nombrePointsMesures, Actions robot, String vitesse) {
               double angleMesure = ((double) (nombrePointsMesures* 360.0) / nombreTotalPoints_lent) ;
               return angleMesure%=360;
       }
       public static void main(String[] args) {
               Capteurs capteurs = new Capteurs();
               Actions robot = new Actions("lent");
               Button.waitForAnyPress();
               run(robot, capteurs);
       }
}
