import java.util.Random;

public class GuerrierRouge extends Thread {
    private double pdv;
    private double pdvMax;
    private double pda;
    private double efficacite;
    private String trait;
    private GuerrierBleu target = null;
    private boolean arret = false;
    private boolean mort = false;

    GuerrierRouge(double pdvMax, double pda, double efficacite, String trait){
        this.pdvMax = pdvMax;
        this.pdv = pdvMax;
        this.pda = pda;
        this.efficacite = efficacite;
        this.trait = trait;
    }

    GuerrierRouge(double pdvMax, double pdv, double pda, double efficacite, String trait){
        this.pdvMax = pdvMax;
        this.pdv = pdv;
        this.pda = pda;
        this.efficacite = efficacite;
        this.trait = trait;
    }

    @Override
    public void run() {
        Random rand = new Random();

        target = Terrain.guerrierBleuList.get(rand.nextInt(Terrain.guerrierBleuList.size()));

        //Temps que sa cile n'est pas morte l'attaquer
        while(!target.isMort()){
            try {
                Thread.sleep((int) (rand.nextInt(300) + 200));
                attaquer(rand);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //S'il a gagné son combat alors fair ele traitement de fin de manche
        if (!mort){
            changeTarget(rand);
        }

    }

    //La fonction permettant de faire le traitement lorsque que la cible est morte
    public void changeTarget(Random rand){
        switch (trait){
            case "Healer" -> {
                System.out.println("Soldat Rouge : Je dois aller dormir ZzZzZ");
                //Se soigner au maximum
                this.pdv = pdvMax;
                this.arret = true;
            }
            case "Forgeron" -> {
                System.out.println("Soldat Rouge : Mon épée est abimée ...");
                //Améliorer son arme
                this.pda *= 1.25;
                this.arret = true;
            }
            case "Combatant" -> {
                System.out.println("Soldat Rouge : Encore du combat !!!!!!!");

//                //Lui donnner une nouvelle cible
//                target = Terrain.guerrierBleuList.get(rand.nextInt(Terrain.guerrierBleuList.size()));
//                while(target.isMort()){
//                    target = Terrain.guerrierBleuList.get(rand.nextInt(Terrain.guerrierBleuList.size()));
//                }
//
//                //Faire pareil que dans le run
//                while(!target.isMort()){
//                    try {
//                        Thread.sleep((int) (rand.nextInt(300) + 200));
//                        attaquer(rand);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                //S'il a gagné son combat alors fair ele traitement de fin de manche
//                if (!mort){
//                    changeTarget(rand);
//                }
                this.arret = true;
            }
            case "Peureux" -> {
                System.out.println("Soldat Rouge : J'ai peur :-( ");
                //Attendre la prochaine manche
                this.arret = true;
            }
            case "Gros Buveur" -> {
                System.out.println("Soldat Rouge : Je suis complètement mort, allons boire");
                //Réduire son efficatité
                this.efficacite /= 2;
                this.arret = true;
            }

        }
    }

    //Fonction pour attaquer la cible du guerrier
    public void attaquer(Random rand) throws InterruptedException {
        double proba = rand.nextFloat();
        double degats = 0;
        if (proba <= efficacite){
            degats = pda;
        }
//        else {
//            System.out.println("Mince j'ai raté mon coup ...");
//        }
        target.sePrendreUnCoupDansLaFace(degats);
    }

    //Fonction pour se prendre des dégats
    public synchronized void sePrendreUnCoupDansLaFace(double degat) throws InterruptedException {
        pdv -= degat;

        //S'il est mort alors l'arrêter
        if (pdv <= 0){
            System.out.println("Soldat Rouge : AHHH je suis mort");
            this.mort = true;
            this.arret = true;
        }
    }

    public double getPdv() {
        return pdv;
    }

    public void setPdv(double pdv) {
        this.pdv = pdv;
    }

    public double getPda() {
        return pda;
    }

    public void setPda(double pda) {
        this.pda = pda;
    }

    public double getEfficacite() {
        return efficacite;
    }

    public void setEfficacite(double efficacite) {
        this.efficacite = efficacite;
    }

    public String getTrait() {
        return trait;
    }

    public void setTrait(String trait) {
        this.trait = trait;
    }

    public GuerrierBleu getTarget() {
        return target;
    }

    public void setTarget(GuerrierBleu target) {
        this.target = target;
    }

    public String toString(){
        return "Guerrier rouge " + pdv + " pdv, " + pda + " pda " + efficacite + " efficacité, " + trait;
    }

    public boolean isArret() {
        return arret;
    }

    public void setArret(boolean arret) {
        this.arret = arret;
    }

    public double getPdvMax() {
        return pdvMax;
    }

    public void setPdvMax(double pdvMax) {
        this.pdvMax = pdvMax;
    }

    public boolean isMort() {
        return mort;
    }

    public void setMort(boolean mort) {
        this.mort = mort;
    }
}
