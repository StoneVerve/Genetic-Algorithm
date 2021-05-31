package qap.fga;

import java.util.*;
import gaframework.*;


/**
 * Modela una poblacion de cromosomas para el algoritmo genetico FGA
 **/
public class PopulationFGA<F> {



    private BornAnIndividual<F> funcionBorn;
    private FitnessFunction<F> funcionFitness;
    private ArrayList<Chromosome<F>> poblacion;
    private int longitudChrom;
    private int generacion;
    private double[] bluePrint; 


    /**
     * Crea una nueva poblacion de cromosomas que perteneceran a una generacion
     * especificada
     * @param bornF La funcion born an individual que genera individuos a partir
     * de los cromosomas de la poblacion
     * @param fit La funcion fitness para evaluar a los individuos generados
     * @oaram longChrom La longitud que tendran los cromosomas de la poblacion
     * @param generacion La generacion de la poblacion
     *
     **/
    public PopulationFGA(BornAnIndividual<F> bornF, FitnessFunction<F> fit,
                         int longChrom, int generacion) {
        funcionBorn = bornF;
        funcionFitness = fit;
        longitudChrom = longChrom;
        this.generacion = generacion;
        poblacion = new ArrayList<>();
        bluePrint = new double[longitudChrom];//new Chromosome<>(longitudChrom);
    }



    /**
     * Regresa la generacion de la poblacion
     * @return La generacion de la poblacion
     **/
    public int getGeneracion() {
        return generacion;
    }

    /**
     * Regresa la funcion born an Individual de la poblacion
     * @return La funcion Born an Individual de la poblacion
     **/
    public BornAnIndividual<F> getFuncionBorn() {
        return funcionBorn;
    }

    /**
     * Regresa la funcion fitness de la poblacion
     * @return La funcion fitness de la poblacion
     **/
    public FitnessFunction<F> getFuncionFitness() {
        return funcionFitness;
    }


    /**
     * Regresa la longitud definida para los cromosomas de la poblacion
     * @return La longitud definida de los cromosomas
     **/
    public int getLongitudChrom() {
        return longitudChrom;
    }

    /** 
     * Regresa el tamaño de la poblacion
     * @return La cantidad de cromosomas en la poblacion
     **/
    public int size() {
        return poblacion.size();
    }


    /**
     * Descarta las poblacion actual he inicia una nueva poblacion con generacion
     * en 1 y donde todos los cromosomas tiene la predisposicion en 0.50
     * @param cantidad La cantidad de individuos que tendra la poblacion
     **/
    public void incializa(int cantidad) {
        generacion = 1;
        Phenotype<F> pheno;
        Chromosome<F> chrom;
        poblacion = new ArrayList<>(cantidad);
        double[] preds = new double[longitudChrom];
        for (int i = 0; i < longitudChrom; i++) 
            preds[i] = 0.50;
        ArrayList<Chromosome<F>> pob = new ArrayList<>(cantidad);
        while (cantidad > 0) {
            pheno = funcionBorn.newRandomPhenotype(longitudChrom);
            pob.add(new Chromosome<F>(pheno, preds));
            cantidad--;
        }
        bluePrint = preds;
        poblacion = pob;
    }

    /**
     * Genera una lista de Individuos correspondientes a cada cromosoma de la poblacion
     * @return Una lista de individuos correspodinetes a los cromosomas de la poblacion
     **/
    public ArrayList<IndividualFGA<F>> generaIndividuos() {
        ArrayList<IndividualFGA<F>> individuos = new ArrayList<IndividualFGA<F>>(poblacion.size());
        Phenotype<F> indivAct;
        double fitness;
        for (Chromosome<F> e : poblacion) {
            indivAct = funcionBorn.generateIndividual(bluePrint, e);
            fitness = funcionFitness.evaluate(indivAct);
            //System.out.println("Cromosoma " + e.toString());
            //System.out.println("Fenotipo " + indivAct.toString());
            individuos.add(new IndividualFGA<F>(e, indivAct, fitness));
        }
        return individuos;
    }


    /**
     * Agrega un nuevo cromosoma a la poblacion, si la longitud del cromosoma no
     * concuerda con la longiutd definida para la poblacion se lanza una excepcion
     * @param chromo El cromosoma que se va a agregar a la poblacion
     **/
    public void agregaCromosoma(Chromosome<F> chromo) {
        if (chromo.size() != longitudChrom) {
            throw new IllegalArgumentException("El tamaño del cromosoma no concuerda" +
                                               " con la longitud de cromosoma definida");
        }
        double subTotal = 0.0;
        int cantidad = poblacion.size();
        int cantidadNueva = cantidad + 1;
        for (int i = 0; i < bluePrint.length; i++) {
            subTotal = bluePrint[i] * cantidad;
            subTotal = subTotal + chromo.getPredispositionAt(i);
            bluePrint[i] = subTotal / (cantidadNueva);
        }
        poblacion.add(chromo);
    }



}