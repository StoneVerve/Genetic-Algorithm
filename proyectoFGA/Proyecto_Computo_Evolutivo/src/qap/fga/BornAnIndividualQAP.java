package qap.fga;

import gaframework.Phenotype;
import java.util.Random;
import java.util.LinkedList;

/**
 * Implementacion de la funcion Born an Individual
 **/
public class BornAnIndividualQAP implements BornAnIndividual<Integer> {

    private double globalLR;
    private double diversityR;

    /**
     * Crea una funcion Born an Individual con un global learning rate y
     * un diversity rate dados
     * @param globarLR El global learning rate
     * @param diversirtyR El diversity rate
     **/
    public BornAnIndividualQAP(double globalLR, double diversirtyR) {
        if (globalLR < 0.0 || globalLR > 1.0 || 
            diversirtyR < 0.0 || diversirtyR > 1.0 ) {
                throw new IllegalArgumentException("El global learning rate y el" +
                                                    " diversity rate tiene que ser" +
                                                    " mayor igual que cero y menor" +
                                                    " o igual que uno");
        } else {
            this.globalLR = globalLR;
            this.diversityR = diversityR;
        }
    }


    /**
     * Genera un nuevo fenotipo a partir de un cromosoma y el bluePrint de la 
     * poblacion a la que pertenece el cromosoma
     * @param bluePrint El bluePrint de la poblacion
     * @param crom El cromosoma a partir del cual se generara el fenotipo
     * @return Un fenotipo correspondiente al cromosoma
     **/
    public Phenotype<Integer> generateIndividual(double[] bluePrint, Chromosome<Integer> crom) {
        double[] probEfective = new double[bluePrint.length];
        for (int i = 0; i < probEfective.length; i++) {
            probEfective[i] = calcularProbEfectiva(bluePrint[i], crom.getPredispositionAt(i));
        }
        LinkedList<Integer> candidatos = new LinkedList<>();
        for (int j = 0; j < probEfective.length; j++)
            candidatos.add(new Integer(j + 1));
        Random generator = new Random();
        Phenotype<Integer> individual = new Phenotype<>(probEfective.length);
        Integer act;
        double probRes = 0.0;
        for (int k = 0; k < probEfective.length; k++) {
            probRes = generator.nextDouble();
            act = new Integer(crom.getAlleleAt(k));
            if (probRes < probEfective[k] && candidatos.contains(act)) {
                individual.setAllele(k, act);
                candidatos.remove(act);
            } else {
                Integer seleccionado = candidatos.get(generator.nextInt(candidatos.size()));
                individual.setAllele(k, seleccionado);
                candidatos.remove(seleccionado);
            }
        }
        return individual;
    }

    /**
     * Calcula la probabilidad efectiva de una celda
     * @param pvb La probabilidad del blue print de una celda
     * @param pvc La probabilidad del cromosoma de una celda
     * @return La probabilidad efectiva de una celda
     **/
    private double calcularProbEfectiva(double pvb, double pvc) {
        double probEf = pvb * globalLR + (1 - globalLR) * pvc;
        if (probEf < diversityR)
            probEf = diversityR;
        else if(probEf > (1 - diversityR))
            probEf = (1 - diversityR);
        return probEf;
    }

    /**
     * Genera un nuevo fenotipo al azar
     * @param longitud La longitud del fenotipo
     * @return Un nuevo fenotipo de longitud dada
     **/
    public Phenotype<Integer> newRandomPhenotype(int longitud) {
        Random generator = new Random();
        int act = 0;
        Phenotype<Integer> nuevo = new Phenotype<>(longitud);
        for (int i = 0; i < longitud; i++)
            nuevo.setAllele(i, new Integer(generator.nextInt(longitud) + 1));
        return nuevo;
    }
}