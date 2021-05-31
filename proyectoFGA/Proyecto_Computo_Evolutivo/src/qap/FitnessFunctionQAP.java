package qap;

import gaframework.*;
import java.util.*;


/**
 * Funcion Fitness para el problema QAP
 **/
public class FitnessFunctionQAP implements FitnessFunction<Integer> {

    private QuadraticAP problema;

    /**
     * Construye una nueva instancia en base a una instancia del QAP
     * @param pro La instancia del problema
     **/
    public FitnessFunctionQAP(QuadraticAP pro) {
        this.problema = pro;
    }


    /**
     * Evalue que tan buena es una solucion (grado de adaptacion) para la 
     * instancia del problema
     * @param fenotipo El fenotipo del individuo que se esta evaluando
     * @return Un valor que mientras mas alto mejor es la solucion (mejor adaptado esta)
     **/
    public double evaluate(Phenotype<Integer> fenotipo) {
        int dim = problema.getDimension();
        int costo = 0;
        int obj1;
        int obj2;
        for (int i = 0; i < (dim - 1); i++) {
            obj1 = fenotipo.getAllele(i).intValue() - 1;
            for (int j = i + 1; j < dim; j++) {
                obj2 =fenotipo.getAllele(j).intValue() - 1; 
                costo = costo + problema.getDistanciaEntre(i,j) * problema.getFlujoEntre(obj1,obj2);
            }
        }
        double fitness = (1.0 / costo) * 100000.0;
        return fitness;
    }
}



