import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Terrain {
    public static List<GuerrierBleu> guerrierBleuList = new ArrayList<>();
    public static List<GuerrierRouge> guerrierRougeList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Random rand = new Random();
        String[] traits = {"Healer", "Forgeron", "Combatant", "Peureux", "Gros buveur"};
        int nbGuerrier = 20;
        String type = "";

        for(int i = 0 ; i<nbGuerrier; i++){
           guerrierBleuList.add(new GuerrierBleu((double)Math.round(rand.nextDouble() * 10000) / 100, (double)Math.round(rand.nextDouble() * 100) / 10, (double)(rand.nextInt(20) + 81 ) / 100,traits[rand.nextInt(5)] ));
           guerrierRougeList.add(new GuerrierRouge((double)Math.round(rand.nextDouble() * 10000) / 100, (double)Math.round(rand.nextDouble() * 100) / 10, (double)(rand.nextInt(20) + 81 ) / 100, traits[rand.nextInt(5)] ));
            System.out.println(guerrierBleuList.get(i));
            System.out.println(guerrierRougeList.get(i));
        }


        while(true){

            System.out.println("\n ------------------ Début de la manche ------------------ \n");

            //Lancer tous les guerrier
            for (GuerrierBleu guerrierBleu : guerrierBleuList) {
                guerrierBleu.start();
            }
            for (GuerrierRouge guerrierRouge : guerrierRougeList) {
                guerrierRouge.start();
            }

            //Attendre la fin de leur execution
            for (GuerrierBleu guerrierBleu : guerrierBleuList) {
                guerrierBleu.join();
            }
            for (GuerrierRouge guerrierRouge : guerrierRougeList) {
                guerrierRouge.join();
            }

            List<GuerrierBleu> guerrierBleuListTemp = new ArrayList<>();
            List<GuerrierRouge> guerrierRougeListTemp = new ArrayList<>();
            //Récupérer ceux encore en vie pour la manche suivante
            for (GuerrierBleu guerrierBleu : guerrierBleuList) {
                if (!guerrierBleu.isMort()) {
                    guerrierBleuListTemp.add(new GuerrierBleu(guerrierBleu.getPdvMax(), guerrierBleu.getPdv(), guerrierBleu.getPda(), guerrierBleu.getEfficacite(), guerrierBleu.getTrait()));
                }
            }

            for (GuerrierRouge guerrierRouge : guerrierRougeList) {

                if (!guerrierRouge.isMort()) {
                    guerrierRougeListTemp.add(new GuerrierRouge(guerrierRouge.getPdvMax(), guerrierRouge.getPdv(), guerrierRouge.getPda(), guerrierRouge.getEfficacite(), guerrierRouge.getTrait()));

                }
            }

            guerrierBleuList = guerrierBleuListTemp;
            guerrierRougeList = guerrierRougeListTemp;

            System.out.println("Il reste " + guerrierRougeList.size() + " soldats rouges et " + guerrierBleuList.size() + " soldats bleus" );

            //Définir un/des gagnants
            if (guerrierBleuList.size() == 0 && guerrierRougeList.size() == 0){
                type = "Bleu et rouge"; break;
            }
            else if (guerrierBleuListTemp.size() == 0){
                type = "bleu"; break;
            }
            else if(guerrierRougeList.size() == 0){
                type = "rouge"; break;
            }
        }

        System.out.println("\nNous avons une équipe gagnante : " + type);
    }

}
