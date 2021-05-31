package qap.ags;

import java.io.File;
import java.io.IOException;
import gaframework.*;
import java.util.*;
import qap.*;

/**
 * Implementacion de un algoritmo genetico simple para solucionar instacias del
 * problema de asignacion cuadratico
 **/
public class GeneticAlgorithmQAP implements GeneticAlgorithm<ArrayList<Boolean>, Integer> {

    private QuadraticAP problema;
    private Codificador codificador;
    private CorrectorQAP corrector;
    private FitnessFunction<Integer> funcionFitness;
    private SelectionOp<ArrayList<Boolean>,Integer> selector;
    private CrossoverOp<ArrayList<Boolean>> cruzador;
    private MutationOp<ArrayList<Boolean>> mutador;
    private TerminationCondition<ArrayList<Boolean>, Integer> condTerminacion;
    private Breeder<ArrayList<Boolean>,Integer> breeder;




    /**
     * Crea un nuevo algoritmo genetico simple
     * @param problema El nombre del archivo con las matrices de flujos y distancias
     * que representan a la instancia del problema QAP
     * @param fitness La funcion fitness
     * @param selector El operador de seleccion
     * @param cruzador El operador de cruzamiento
     * @param mutador El operador de mutacion
     * @param condicionParo La condicion de paro
     **/
    public GeneticAlgorithmQAP(String problema, FitnessFunction<Integer> fitness,
                               SelectionOp<ArrayList<Boolean>, Integer> selector,
                               CrossoverOp<ArrayList<Boolean>> cruzador,
                               MutationOp<ArrayList<Boolean>> mutador,
                               TerminationCondition<ArrayList<Boolean>, Integer> condicionParo) {

        this.problema = new QuadraticAP(new File (problema));
        codificador = new Codificador(this.problema.getDimension());
        corrector = new CorrectorQAP(this.problema.getDimension());
        this.selector = selector;
        this.cruzador = cruzador;
        this.mutador = mutador;
        condTerminacion = condicionParo;
        if (fitness == null)
            funcionFitness = new FitnessFunctionQAP(this.problema);
         else 
            funcionFitness = fitness;
        breeder = new Breeder<>(this.codificador, this.corrector, funcionFitness);
    }


    /**
     * Genera una nueva generacion de una poblacion al aplicar el algoritmo genetico simple
     * @param actual La poblacion actual a la que se aplicara el algoritmo
     * @return Una poblacion que representa a la siguiente generacion (Una iteracion
     * del algoritmo)
     **/
    public Population<ArrayList<Boolean>,Integer> iteration(Population<ArrayList<Boolean>,Integer> actual) {
        int numIndividuos = actual.size();
        Population<ArrayList<Boolean>,Integer> nuevaPoblacion = new Population<>(actual.getGeneration() + 1);
        int m = 0;
        while (nuevaPoblacion.size() < numIndividuos) {
            List<Individual<ArrayList<Boolean>, Integer>> seleccionados = selector.select(actual);
            LinkedList<Genotype<ArrayList<Boolean>>> genoSeleccionados = new LinkedList<>();
            for (Individual<ArrayList<Boolean>, Integer> e : seleccionados)
                genoSeleccionados.add(e.getGenotype());
            List<Genotype<ArrayList<Boolean>>> hijos = cruzador.crossover(genoSeleccionados);
            LinkedList<Genotype<ArrayList<Boolean>>> hijosBien = new LinkedList<>();
            for (Genotype<ArrayList<Boolean>> e : hijos)
                hijosBien.add(corrector.repairGenotype(e));
            LinkedList<Genotype<ArrayList<Boolean>>> hijosMutados = new LinkedList<>();
            for (Genotype<ArrayList<Boolean>> e : hijosBien)
                hijosMutados.add(mutador.mutate(e));
            LinkedList<Individual<ArrayList<Boolean>, Integer>> nuevosIndividuos = new LinkedList<>();
            for (Genotype<ArrayList<Boolean>> e : hijosMutados) 
                nuevosIndividuos.add(breeder.newIndividual(corrector.repairGenotype(e)));
            int dif = numIndividuos - (nuevaPoblacion.size() + nuevosIndividuos.size());
            if (dif < 0) {
                while (dif < 0) {
                    nuevosIndividuos.remove();
                    dif++;
                }
            }
            for (Individual<ArrayList<Boolean>, Integer> e : nuevosIndividuos)
                nuevaPoblacion.addIndividual(e);
        }
        return nuevaPoblacion;
    }
    
    
    

    /**
     * Ejecuta el algoritmo genetico sobre una poblacion de 100 individuos hasta 
     * cumplir la condicion de paro
     **/
    public void run() {
        Population<ArrayList<Boolean>,Integer> poblacionActual;
        poblacionActual = breeder.newRandomPopulation(100);
        while(!condTerminacion.conditionReached(poblacionActual)) {
            System.out.println("Generacion Actual:  " + poblacionActual.getGeneration());
            poblacionActual = iteration(poblacionActual);
        }
        Individual<ArrayList<Boolean>, Integer> solucion = poblacionActual.getBestIndividual();
        System.out.println("Mejor solucion " + solucion.getPhenotype().toString());
        //System.out.println("Fitness " + solucion.getFitness());
        int dim = problema.getDimension();
        int costo = 0;
        int obj1;
        int obj2;
        Phenotype<Integer> fenotipo = solucion.getPhenotype();
        for (int i = 0; i < (dim - 1); i++) {
            obj1 = fenotipo.getAllele(i).intValue() - 1;
            for (int j = i + 1; j < dim; j++) {
                obj2 =fenotipo.getAllele(j).intValue() - 1; 
                costo = costo + problema.getDistanciaEntre(i,j) * problema.getFlujoEntre(obj1,obj2);
            }
        }
        System.out.println("Costo de la solucion: " + costo);

    }


}