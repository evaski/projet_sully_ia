package sully ;

public class SullyMain {

	 public static void main(String[] args) {
		 Actions actions = new Actions(); 
		 
		 actions.avancer_de(30); 
		 System.out.print("Test pour que le robot avance de 30cm");
		 
		 actions.ouvrir_pince(); 
		 System.out.print("Test pour voir s'il ouvre les pinces");
		 
		 actions.fermer_pince(); 
		 System.out.print("Test pour voir s'il ferme les pinces"); 
		 
		 actions.reculer(30); 
		 actions.tourner_de(90); 
	 }

}
