package qap.fga;

import java.io.File;
import java.io.IOException;
import gaframework.*;
import java.util.*;
import qap.QuadraticAP;
import qap.FitnessFunctionQAP;

/**
 * Modela un algoritmo genetico fluido para resolver una instancia del problema
 * de asignacion cuadratico QAP
 **/
public class FluidGeneticAlgorithm {

    private QuadraticAP problema;
    private FitnessFunction<Integer> funcionFitness;
    private SelectionRuletaFGA<Integer> selector;
    private CrossoverFGA<Integer> cruzador;
    private TerminationConditionFGA<Integer> condTerminacion;
    private BornAnIndividual<Integer> funcionBorn;




    /**
     * Crea un nuevo fluid genetic algorithm
     * @param problema El nombre del archivo con la instancia del problema QAP
     * @param fitness La funcion fitness
     * @param selector El operador de seleccion
     * @param cruzador El operador de cruze
     * @param funcionB La funcion Born an Individual
     * @param condicionParo La condicion de paro
     **/
    public FluidGeneticAlgorithm(String problema, FitnessFunction<Integer> fitness,
                               SelectionRuletaFGA<Integer> selector,
                               CrossoverFGA<Integer> cruzador,
                               BornAnIndividual<Integer> funcionB,
                               TerminationConditionFGA<Integer> condicionParo) {

        this.problema = new QuadraticAP(new File (problema));
        this.selector = selector;
        this.cruzador = cruzador;
        this.funcionBorn = funcionB;
        condTerminacion = condicionParo;
        if (fitness == null)
            funcionFitness = new FitnessFunctionQAP(this.problema);
         else 
            funcionFitness = fitness;
    }

    /**
     * Genera una nueva generacion de una poblacion al aplicar el FGA
     * @param actual La poblacion actual a la que se aplicara el algoritmo
     * @return Una poblacion que representa a la siguiente generacion (Una iteracion
     * del algoritmo)
     **/
    public PopulationFGA<Integer> iteration(PopulationFGA<Integer> actual) {
        int numIndividuos = actual.size();
        PopulationFGA<Integer> nuevaPoblacion = new PopulationFGA<>(funcionBorn,
                                                                    funcionFitness,
                                                                    actual.getLongitudChrom(),
                                                                    actual.getGeneracion() + 1);
        ArrayList<IndividualFGA<Integer>> individuos = actual.generaIndividuos();
        while (nuevaPoblacion.size() < numIndividuos) {
            LinkedList<IndividualFGA<Integer>> seleccionados = selector.select(individuos);
            Chromosome<Integer> hijo = cruzador.cruzaIndividuos(seleccionados.get(0), seleccionados.get(1));
            nuevaPoblacion.agregaCromosoma(hijo);
        }
        return nuevaPoblacion;
    }


    /**
     * Ejecuta el fluid genetic algorithm sobre una poblacion de 200 cromosomas hasta 
     * cumplir la condicion de paro
     **/
    public void run() {
        PopulationFGA<Integer> poblacionActual = new PopulationFGA<>(funcionBorn,
                                                                    funcionFitness,
                                                                    problema.getDimension(),
                                                                    1);
        poblacionActual.incializa(200);
        while(!condTerminacion.conditionReached(poblacionActual)) {
            System.out.println("Generacion Actual:  " + poblacionActual.getGeneracion());
            poblacionActual = iteration(poblacionActual);
        }
        ArrayList<IndividualFGA<Integer>> individuos = poblacionActual.generaIndividuos();
        IndividualFGA<Integer> mejor = individuos.get(0);
        for (IndividualFGA<Integer> e : individuos) {
            if (mejor.getFitness() <= e.getFitness())
                mejor = e;
        }
        System.out.println("Mejor solucion " + '\n' +  mejor.toString());
        //System.out.println("Fitness " + mejor.getFitness());
        int dim = problema.getDimension();
        int costo = 0;
        int obj1;
        int obj2;
        Phenotype<Integer> fenotipo = mejor.getRepActual();
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