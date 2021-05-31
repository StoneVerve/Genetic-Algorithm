package qap.ags;

import gaframework.Genotype;
import gaframework.MutationOp;
import java.util.*;

/**
 * Implementación del operador de mutación  para una instacia del problema QAP
 * El operados consiste en seleccionar de forma aleatoria dos genes del genotipo
 * e intercambiarlos de posicion, en caso de que se selecciones el mismo gen
 * se toma como uno de los genes el anterior si selecciono el ultimo gen del 
 * genotipo y el siguiente en cualquier otro caso
 * 
 **/
public class MutadorSwap<G> implements MutationOp<G> {

    private double probMutation;
    private Random generator;
    private Random selector;

    /**
     * Construye un operador de mutación swap con una probabilidad de mutación
     * indicada
     * @param probMut La probabilidad de mutación
     * @param semillaSelec La semilla que se usara para generar los numeros 
     * aleatorios para escoger los genes 
     * @param semillaMut La semilla que se usara para decidir si se muta o no
     **/
    public MutadorSwap(double probMut, long semillaSelec, long semillaMut) {
        probMutation = probMut;
        selector = new Random(semillaSelec);
        generator = new Random(semillaMut);
    }

    /**
     * Construye un operador de mutación swap con una probabilidad de mutación
     * indicada
     * @param probMut La probabilidad de mutación
     **/
    public MutadorSwap(double probMut) {
        probMutation = probMut;
        selector = new Random();
        generator = new Random();
    }

    /**
     * Decide si mutar un genotipo o no con una probabilidad de mutación dada en 
     * el constructor de la instancia
     * @param genotyoe El genotipo a mutar
     * @return El genotipo mutado
     **/
    public Genotype<G> mutate(Genotype<G> genotype) {
        int size = genotype.size();
        G gen;
        Genotype<G> mutatedGeno = new Genotype<>(size);
        double dice = generator.nextDouble();
        if (dice <= this.probMutation) {
            int index1 = selector.nextInt(size);
            int index2 = selector.nextInt(size);
            if (index1 == index2) {
                if (index1 == (size - 1))
                    index1 = index1 - 1;
                else
                    index1 = index1 + 1;
            }
            G gen1 = genotype.getGene(index1);
            G gen2 = genotype.getGene(index2);
            boolean swap = false;
            for (int j = 0; j < size; j++) {
                if (j != index1 && j != index2)
                    mutatedGeno.setGene(j,genotype.getGene(j));
                else if (!swap) {
                    mutatedGeno.setGene(index1,gen2);
                    mutatedGeno.setGene(index2,gen1);
                    swap = true;
                }
            }
        } else
            mutatedGeno = genotype;
        return mutatedGeno;
    }
}