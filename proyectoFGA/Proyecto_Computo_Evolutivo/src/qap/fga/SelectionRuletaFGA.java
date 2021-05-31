package qap.fga;

import java.util.Random;
import gaframework.*;
import java.util.*;

/**
 * Operador de seleccion de ruleta para el fluid genetic algorithm
 **/
public class SelectionRuletaFGA<F> {

    private Random generator;
    private int numSeleccionados;

    /**
     * Crea una instancia de SelectionRuletaFGA en la que seleccionar una catidad
     * de individuos espacificado
     * @param cantidadSelec La cantidad de individuos que seleccionara
     **/
    public SelectionRuletaFGA(int cantidadSelec) {
        this.generator = new Random();
        this.numSeleccionados = cantidadSelec;
    }

    /**
     * Crea una instancia de SelectionRuletaFGA en la que seleccionar una catidad
     * de individuos espacificado
     * Se incluye la posibilidad de especificar la semilla que se usara para
     * seleccionar a los individuos de la poblacion
     * El objetivo es poder repetir los experimentos
     * @param cantidadSelec La cantidad de individuos que seleccionara
     * @param seed La semilla que se usara para seleccionar a los individuos
     **/
    public SelectionRuletaFGA(long seed, int cantidadSelec) {
        this.generator = new Random(seed);
        this.numSeleccionados = cantidadSelec;
    }



    /**
     * Selecciona el numero especificado en el constructor de individuos 
     * de la poblacion con una probabildad proporcional a su fitness
     * @param poblacion La poblacion de individuos
     * @return Una lista con los individuos seleccionados
     **/
    public LinkedList<IndividualFGA<F>> select(List<IndividualFGA<F>> poblacion) {
        double[] ruleta = crearRuleta(poblacion);
        //Random generator = new Random();;
        double prob = 0.0;
        int j = 0;
        LinkedList<IndividualFGA<F>> seleccionados = new LinkedList<>();
        for (int i = 0; i < this.numSeleccionados; i++) {
            prob = generator.nextDouble();
            j = 0;
            while (j < ruleta.length && prob > ruleta[j])
                j++;
            if (j < ruleta.length)
                seleccionados.add(poblacion.get(j));
            else
                seleccionados.add(poblacion.get(ruleta.length - 1));
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
    private double[] crearRuleta(List<IndividualFGA<F>> poblacion) {
        int cantidad = poblacion.size();
        double[] valFitness = new double[cantidad];
        for (int k = 0; k < cantidad; k++)
            valFitness[k] = poblacion.get(k).getFitness();
        //double[] valFitness = poblacion.getFitnessArray();
        double valTotal = 0;
        for (int i = 0; i < valFitness.length; i++) 
            valTotal = valTotal + valFitness[i]; 
        double[] ruleta = new double[valFitness.length];
        for (int j = 0; j < ruleta.length; j++) {
            ruleta[j] = valFitness[j] / valTotal;//ruleta[j] = (poblacion.getIndividual(j).getFitness()) / valTotal;
            if (j != 0)
                ruleta[j] = ruleta[j -1] + ruleta[j];
        }
        return ruleta;
    }
}