package qap.fga;

import java.util.*;
import gaframework.*;


/**
 * Operador de cruzamineto de un punto de corte para un algoritmo genetic FGA
 **/
public class CrossoverFGA<F> {


    private double individualLR;
    private double probCruza;
    private Random generatorCruz;
    private Random generatorPoint;


    /**
     * Constructor que recibe la probabilidad de ser cruzados y el individual 
     * learning rate
     * @param probCruza La probabilidad de cruzamiento
     * @param individualLR El individual learning rate
     **/
    public CrossoverFGA(double probCruza, double individualLR) {
        this.probCruza = probCruza;
        this.individualLR = individualLR;
        generatorCruz = new Random();
        generatorPoint = new Random();
    }


    /**
     * Constructor que recibe la probabilidad de ser cruzados y el individual 
     * learning rate, ademas recibe las semillas que se usaran para los generadores
     * que deciden si se cruzan los padres y para encontrar el punto de corte
     * La intencion del constructor es poder recrear experimientos
     * @param probCruza La probabilidad de cruzamiento
     * @param individualLR El individual learning rate
     * @param seedCruz La semilla que se usara para el generador de cruza
     * @param seedPoint La semiila que se usara para el generador del punto
     **/
    public CrossoverFGA(double probCruza, double individualLR, long seedCruz,
                        long seedPoint) {
        this.probCruza = probCruza;
        this.individualLR = individualLR;
        generatorCruz = new Random();
        generatorPoint = new Random();
    }



    /**
     * Cruza dos individuos generando un unico hijo nuevo cuya predisposiciones
     * son alteradas por el individual learning rate
     * @param ind1 El primer padre 
     * @param ind2 El segundo padre
     * @return Un nuevo cromosoma correspondiente al hijo nacido de la cruza de
     * los padres
     **/
    public Chromosome<F> cruzaIndividuos(IndividualFGA<F> ind1, IndividualFGA<F> ind2) {
        int dist = ind2.size();
        Chromosome<F> chrom1 = ind1.getCromosoma();
        Chromosome<F> chrom2 = ind2.getCromosoma();
        Phenotype<F> rep1 = ind1.getRepActual();
        Phenotype<F> rep2 = ind2.getRepActual();
        Chromosome<F> hijo = new Chromosome<>(dist);
        if (generatorCruz.nextDouble() <= probCruza) {
            int puntoCorte = generatorPoint.nextInt(dist);
            if (puntoCorte == (dist - 1))
                puntoCorte = puntoCorte -1;
            if (puntoCorte == 0)
                puntoCorte++;
            // Copiamos la parte del primer padre
            for (int i = 0; i < puntoCorte; i++) {
                hijo.setAllele(i, chrom1.getAlleleAt(i));
                hijo.setPredisposition(i,chrom1.getPredispositionAt(i));
            }
            //Copiamos la parte del segundo padre
            for (int j = puntoCorte; j < dist; j++) {
                hijo.setAllele(j, chrom2.getAlleleAt(j));
                hijo.setPredisposition(j,chrom2.getPredispositionAt(j));
            }
            // Aplicamos el individual Learning Rate
            for (int k = 0; k < puntoCorte; k++) 
                calcularPredis(hijo,k, rep1);
            for (int l = puntoCorte; l < dist; l++)
                calcularPredis(hijo,l,rep2);
        } else
            hijo = ind1.getCromosoma();
        return hijo;
    }

    /**
     * Calcula la predisposicon con el indivudal learning rate
     * @param hijo El hijo cuya predisposcion se alterara
     * @param indicie El indice de la predisposicion a alterar
     * @param padre El fenotipo del padre
     **/
    private void calcularPredis(Chromosome<F> hijo, int indice, Phenotype<F> padre) {
        double predisH = hijo.getPredispositionAt(indice);
        F aleleHijo = hijo.getAlleleAt(indice);
        F alelePadre = padre.getAllele(indice);
        double predisC = 0.0;
            if (alelePadre.equals(aleleHijo)) {
                predisC = predisH + individualLR;
                if (predisC > 1.0)
                    hijo.setPredisposition(indice, 0.9);
                else
                    hijo.setPredisposition(indice, predisC);
            } else {
                predisC = predisH - individualLR;
                if (predisC < 0.0)
                    hijo.setPredisposition(indice, 0.1);
                else 
                    hijo.setPredisposition(indice, predisC);
            }
    }

}