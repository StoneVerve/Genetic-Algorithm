package qap.ags;

import java.util.Random;
import java.io.File;
import java.io.IOException;
import gaframework.*;
import java.util.*;

/**
 * Implementacion del operador de seleccion de ruleta
 **/
public class SelectionRuleta<G,P> implements SelectionOp<G,P> {

    private long semilla;
    private int numSeleccionados;

    /**
     * Genera un nuevo operador de seleccion que selecciona una cantidad especificada
     * de individuos utilizando una semilla. El objetivo es para recrear experimentos
     * @param cantidadSelec La cantidad de inviduos que seleccionara
     * @param seed La semilla usada para seleccionar
     **/
    public SelectionRuleta(long seed, int cantidadSelec) {
        this.semilla = seed;
        this.numSeleccionados = cantidadSelec;
    }

    /**
     * Genera un nuevo operador de seleccion que selecciona una cantidad especificada
     * de individuos
     * @param cantidadSelec La cantidad de inviduos que seleccionara
     **/
    public SelectionRuleta(int cantidadSelec) {
        this.numSeleccionados = cantidadSelec;
        semilla = 4352;
    }



    /**
     * Selecciona el numero especificado en el constructor de individuos 
     * de la poblacion con una probabildad proporcional a su fitness
     * @param poblacion La poblacion de individuos
     * @return Una lista con los individuos seleccionados
     **/
    public LinkedList<Individual<G,P>> select(Population<G,P> poblacion) {
        double[] ruleta = crearRuleta(poblacion);
        Random generator = new Random();;
        double prob = 0.0;
        int j = 0;
        LinkedList<Individual<G,P>> seleccionados = new LinkedList<>();
        for (int i = 0; i < this.numSeleccionados; i++) {
            prob = generator.nextDouble();
            j = 0;
            while (j < ruleta.length && prob > ruleta[j])
                j++;
            if (j < ruleta.length)
                seleccionados.add(poblacion.getIndividual(j));
            else
                seleccionados.add(poblacion.getIndividual(ruleta.length - 1));
        }
        return seleccionados;
    }



    /**
     * Crea un arreglo con el rango de valores para cada individuo correspondiente
     * a la probabilidad de ser elegido, este concuerda con una ruleta donde la
     * probabilidad de ser elegido corresponde a un segmento del circulo proporcional
     * a su fitness
     * @param poblacion La poblacion de individuos a partir de los cuales de formara
     * la ruleta
     * @return Un arreglo con los segmentos de la recta [0,1] correspondientes a los
     * individuos
     **/
    private double[] crearRuleta(Population<G,P> poblacion) {
        double[] valFitness = poblacion.getFitnessArray();
        double valTotal = 0;
        for (int i = 0; i < valFitness.length; i++) 
            valTotal = valTotal + valFitness[i]; 
        double[] ruleta = new double[valFitness.length];
        for (int j = 0; j < ruleta.length; j++) {
            ruleta[j] = (poblacion.getIndividual(j).getFitness()) / valTotal;
            if (j != 0)
                ruleta[j] = ruleta[j -1] + ruleta[j];
        }
        return ruleta;
    }
}