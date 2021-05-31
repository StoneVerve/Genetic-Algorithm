package qap.fga;

import gaframework.*;
import java.util.*;

/**
 * Representa a un individuo de la poblacion de cromosomas en un estado 
 * especifico
 **/
public class IndividualFGA<F> {

    private double fitness;
    private Chromosome<F> chromosome;
    private Phenotype<F> repActual;


    /**
     * COnstructor restringido para generar un invididuo correspondiente
     * a un cromosoma en un estado
     * @param cromo El cromosoma del individuo
     * @param repAct El fenotipo actual del individuo
     * @param fit El fitness del individuo
     **/
    IndividualFGA(Chromosome<F> cromo, Phenotype<F> repAct, double fit) {
        chromosome = cromo;
        fitness = fit;
        repActual = repAct;
    }

    /**
     * Regresa el fitness del individuo
     * @return El fitness del individuo
     **/
    public double getFitness() {
        return this.fitness;
    }

    /**
     * Regresa el fenotipo del individuo
     * @return El fenotipo
     **/
    public Phenotype<F> getRepActual() {
        return repActual;
    }

    /**
     * Regresa el cromosoma del individuo
     * @return El cromosoma
     **/
    public Chromosome<F> getCromosoma() {
        return this.chromosome;
    }

    /**
     * Regresa el tamano del cromosoma del indiviuo
     * @return El tamano del cromosoma
     **/
    public int size() {
        return chromosome.size();
    }


    /**
     * Genera una representacion en cadean del individuo
     * @return La representacion en cadena el indiviuo
     **/
    @Override
    public String toString() {
        String salida = chromosome.toString();
        salida = salida  + "Individuo Generado: " + repActual.toString();
        return salida;
    }

}