package qap.fga;

import gaframework.Phenotype;
import java.util.*;

/**
 * Clase que modela un cromosoma perteneciente a FGA, en el se tiene una lista
 * predisposiciones que indican que tan probable es que se genere un individuo
 * con los alelos indicados por las predisposiciones
 **/
public class Chromosome<F> {

    private Phenotype<F> alleles;
    private double[] predispositions;

    /**
     * Crea un cromosoma vacio con cuya con una longitud definida
     * @param length La longitud del cromosoma
     **/
    public Chromosome(int length) {
        predispositions = new double[length];
        alleles = new Phenotype<F>(length);
    }


    /**
     * Crea un nuevo cromosoma dados el fenotipo y las probabilidades de predisposiciones
     * @param phen El fenotipo del cromosoma
     * @param probs Las probabilidades de predisposición
     **/
    public Chromosome(Phenotype<F> phen, double[] probs) {
        if (phen.size() == probs.length) {
            predispositions = probs;
            alleles = phen;
        } else {
            throw new IllegalArgumentException("El numero de genes y predisposiciones" +
                                               " no es el mismo");
        }
    }



    /**
     * Crea un cromosoma con dado un arreglo de predisposiciones y otro de alelos
     * donde la predisposicion en el indice i correponde al alelo en el indice i
     * @param alleles El arreglo de alelos
     * @param predispositions El arreglo de predisposiciones
     **/
    public Chromosome(F[] alleles, double[] predispositions) {
        if (alleles.length == predispositions.length) {
            this.predispositions = predispositions;
            this.alleles = new Phenotype<F>(alleles.length);
            for (int i = 0; i < alleles.length; i++)
                this.alleles.setAllele(i,alleles[i]);
        } else {
            throw new IllegalArgumentException("El numero de genes y predisposiciones" +
                                               " no es el mismo");
        }
    }

    /**
     * Regresa la predisposicion correpondiente al alelo i
     * @param index La predisposicion correpondiente al alelo que se quiere obtener
     * @return La predisposicion en el indice i
     **/
    public double getPredispositionAt(int index) {
        if (index >= predispositions.length)
            throw new IndexOutOfBoundsException("El indice no existe");
        else
            return predispositions[index];
    }

    /**
     * Regresa el alelo correpondiente a la posicion i
     * @param index La posicion del alelo que se quiere obtener
     * @return El alelo correspondiente a la posicion indicada
     **/
    public F getAlleleAt(int index) {
        if (index >= alleles.size())
            throw new IndexOutOfBoundsException("El indice no existe");
        else
            return alleles.getAllele(index);
    }

    /**
     * Asigna una predisposicion en el indice indicado
     * @param index La predisposicion a cambiar
     * @param prob La nueva predisposicion
     **/ 
    public void setPredisposition(int index, double prob) {
        if (index >= predispositions.length)
            throw new IndexOutOfBoundsException("El indice no existe");
        else
            predispositions[index] = prob;
    }


    /**
     * Asigna un alelo en el indice indicado
     * @param index El alelo a cambiar
     * @param alelo El nuevo alelo
     **/ 
    public void setAllele(int index, F allele) {
        if (index >= alleles.size())
            throw new IndexOutOfBoundsException("El indice no existe");
        else
            alleles.setAllele(index, allele);
    }

    /**
     * Da el tamano del cromosoma (numero de genes)
     * @return El tamano del cromosoma
     **/
    public int size() {
        return predispositions.length;
    }

    /**
     * Genera una representacion en cadena del cromosoma con sus predisposiciones
     * @return La representacion en cadena del cromosoma
     **/
    public String toString() {
        String salida = "Cromosoma: " + '\n';
        for (int i = 0; i < predispositions.length; i++) 
            salida = salida + "Celda" + "[" + i + "] {" +
             "Predisposicion [" +  predispositions[i] + "], Valor[" + 
             alleles.getAllele(i).toString() + "]}" +  '\n';
        //salida = salida + '\n';
       // for (int j = 0; j < predispositions.length; j++) 
        //    salida = salida + alleles.getAllele(j).toString() + "|";
        return salida;
    }

}