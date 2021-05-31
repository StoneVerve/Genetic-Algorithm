package qap.fga;

import gaframework.Phenotype;

/**
 * Interfaz de una funcion born an individual de un fga
 **/
public interface BornAnIndividual<F> {

    
    /**
     * Genera un nuevo fenotipo a partir de un cromosoma y el bluePrint de la 
     * poblacion a la que pertenece el cromosoma
     * @param bluePrint El bluePrint de la poblacion
     * @param crom El cromosoma a partir del cual se generara el fenotipo
     * @return Un fenotipo correspondiente al cromosoma
     **/
    public Phenotype<F> generateIndividual(double[] bluePrint, Chromosome<F> crom);

    /**
     * Genera un nuevo fenotipo al azar
     * @param longitud La longitud del fenotipo
     * @return Un nuevo fenotipo de longitud dada
     **/
    public Phenotype<F> newRandomPhenotype(int longitud);
}