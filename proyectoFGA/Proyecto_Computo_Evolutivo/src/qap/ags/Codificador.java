package qap.ags;

import gaframework.*;
import java.util.*;

/**
 * Codifica fenotipos y decodifica genotipos
 **/
public class Codificador implements Codification<ArrayList<Boolean>,Integer> {

    private int longitudRep;
    private int numObjetos;
    private Random generators;

    /**
     * Contructor que recibe la dimension del problema
     * @param objetos La cantidad de objetos (y de ciudades) que tendra el problema
     **/
    public Codificador(int objetos) {
        if (objetos < 0) {
            this.longitudRep = 0;
            this.numObjetos = 0;
        } else {
            this.longitudRep = Integer.toBinaryString(objetos).length();
            this.numObjetos = objetos;
        }
        generators = new Random();
    }



    /**
     * Regresa la longitud de la representacion binaria usada para los identificadores
     * de los objetos
     * @return La longitud de representacion (numero de bits)
     **/
    public int getLongitudRep() {
        return this.longitudRep;
    }



    /**
     * Regresa el numero de objetos (dimension del problema) que se manejan
     * @return El numero de obejtos
     **/
    public int getNumObjetos() {
        return this.numObjetos;
    }


    /* 
     * Decodifica un genotipo que representa una forma de organizar los obejtos
     * en las distintos lugares como un fenotipo de Integer
     * @param genotipo El genotipo a decodificar
     * @return El fenotipo correspondiente al genotipo
     */
    public Phenotype<Integer> decode(Genotype<ArrayList<Boolean>> genotipo) {
        int numGenes = genotipo.size();
        Phenotype<Integer> fenotipo = new Phenotype<>(numGenes);
        for(int i = 0; i < numGenes;i++)
            fenotipo.setAllele(i,binarioToInt(genotipo.getGene(i)));
        return fenotipo;
    }



    /* 
     * Codifica un fenotipo que representa un ordemamiento de los objetos
     *  en un genotipo de Listas de
     * Boolean para modelar su representacion en binario
     * @param fenotipo El fenotipo a codificar
     * @return El genotipo correspondiente al fenotipo
     */
    
    public Genotype<ArrayList<Boolean>> encode(Phenotype<Integer> fenotipo) {
        int numAlelos = fenotipo.size();
        Genotype<ArrayList<Boolean>> genotipo = new Genotype<>(numAlelos);
        for(int i = 0; i < numAlelos;i++) 
            genotipo.setGene(i,intToBinario(fenotipo.getAllele(i)));
        return genotipo;
    }



    /**
     * Genera un genotipo aleatorio que no necesariamente es valio como solucion
     * del problema
     * @return Un genotipo aleatorio
     **/
    public Genotype<ArrayList<Boolean>> newRandomGenotype() {
         Random generador = new Random();
         Genotype<ArrayList<Boolean>> randomGeno = new Genotype<>(numObjetos);
         ArrayList<Boolean> genActual;
         for (int i = 0; i < numObjetos; i++) {
            genActual = generaGen(generador);
            randomGeno.setGene(i,genActual);
         }
         return randomGeno;
    }



    /**
     * Genera un gen aleatorio utilizando un generado Random
     * @param generador La instancia de random que genera el identificador
     * aleatorio de un objeto del problema
     * @return Un gen aleatorio
     **/
    private ArrayList<Boolean> generaGen(Random generador) {
         Random generator = generador;
         ArrayList<Boolean> genNuevo = new ArrayList<>(longitudRep);
         for (int i = 0;i < longitudRep;i++) 
            genNuevo.add(new Boolean(generator.nextBoolean()));
         return genNuevo;
    }



    /*
     * Codifica un alelo que representa un objeto en su gen correspondiente
     * a la representacion del numero en binario
     * @param t El alelo a obtener su gen
     * @return El gen correspondiente al alelo
     */
    private ArrayList<Boolean> intToBinario(Integer t) {
        String valor = Integer.toBinaryString(t.intValue());
        ArrayList<Boolean> repBinario = new ArrayList<>();
        int longitud = valor.length();
        if (longitud != this.longitudRep) {
            for(int j = 0;j < (this.longitudRep - longitud);j++)
                valor = "0" + valor;
            longitud = longitudRep;
        }
        char actual;
        for (int i = 0; i< longitud; i++) {
            actual = valor.charAt(i);
            if (actual == '1')
                repBinario.add(i, new Boolean(true));
            else if (actual == '0')
                repBinario.add(i, new Boolean(false));
        }
        return repBinario;
    }



    /*
     * Decodifica un gen que representa un objeto en su alelo correspondiente
     * a la representacion del numero en binario
     * @param t El gen a obtener su alelo
     * @return El alelo correspondiente al gen
     */
    private Integer binarioToInt(ArrayList<Boolean> binario){
        String valor = "";
        for (Boolean e : binario) {
            if (e.booleanValue() == true)
                valor = valor + "1";
            else if (e.booleanValue() == false)
                valor = valor + "0";
        }
        return Integer.parseInt(valor, 2);
    }

}

