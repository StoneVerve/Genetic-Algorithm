package qap.ags;

import java.io.File;
import java.io.IOException;
import gaframework.*;
import java.util.*;
import qap.*;


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


    public Population<ArrayList<Boolean>,Integer> iteration(Population<ArrayList<Boolean>,Integer> actual) {
		int numIndividuos = actual.size();
		Population<ArrayList<Boolean>,Integer> nuevaPoblacion = new Population<>(actual.getGeneration() + 1);
		// elitista o no? nuevaPoblacion.addIndividual(actual.getBestIndividual());
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
			/*for (Genotype<ArrayList<Boolean>> e : hijosBien) 
				nuevosIndividuos.add(breeder.newIndividual(e));*/
			int dif = numIndividuos - (nuevaPoblacion.size() + nuevosIndividuos.size());
			if (dif < 0) {
				while (dif < 0) {
					nuevosIndividuos.remove();
					dif++;
				}
			}
			for (Individual<ArrayList<Boolean>, Integer> e : nuevosIndividuos)
				nuevaPoblacion.addIndividual(e);
			m++;
			//System.out.println("/*****************/Iteracion " + m);
		}
		return nuevaPoblacion;
	}
	
	
	


    public void run() {
		Population<ArrayList<Boolean>,Integer> poblacionActual;
		poblacionActual = breeder.newRandomPopulation(100);
		while(!condTerminacion.conditionReached(poblacionActual)) {
			System.out.println("Estamos en la generacion " + poblacionActual.getGeneration());
			poblacionActual = iteration(poblacionActual);
		}
		Individual<ArrayList<Boolean>, Integer> solucion = poblacionActual.getBestIndividual();
		System.out.println("Mejor solucion " + solucion.getPhenotype().toString());
		System.out.println("Fitness " + solucion.getFitness());
		int dim = problema.getDimension();
        int costo = 0;
        int obj1;
        int obj2;
        Phenotype<Integer> fenotipo = solucion.getPhenotype();
        /*fenotipo = new Phenotype<>(fenotipo.size());
        fenotipo.setAllele(0,7);
        fenotipo.setAllele(1,5);
        fenotipo.setAllele(2,12);
        fenotipo.setAllele(3,2);
        fenotipo.setAllele(4,1);
        fenotipo.setAllele(5,3);
        fenotipo.setAllele(6,9);
        fenotipo.setAllele(7,11);
        fenotipo.setAllele(8,10);
        fenotipo.setAllele(9,6);
        fenotipo.setAllele(10,8);
        fenotipo.setAllele(11,4);*/
//        7  5 12  2  1  3  9 11 10  6  8  4
    	int tam = fenotipo.size();
    	for (int i = 0; i < dim; i++) {
    		for (int j = 0; j < dim; j++) {
    			for (int k = 0; k < dim; k++) {
    				for (int l = 0; l < dim; l++) {
    					int xIJ;
    					int yKL;
    					if (fenotipo.getAllele(j).intValue() == (i + 1))
    						xIJ = 1;
    					else
    						xIJ = 0;
    					if (fenotipo.getAllele(l).intValue() == (k + 1))
    						yKL = 1;
    					else
    						yKL = 0;
    					costo = costo + problema.getDistanciaEntre(j,l) * problema.getFlujoEntre(k,i) * xIJ * yKL;
    					/*if (fenotipo.getAllele(j).intValue() == (i + 1) &&
    						fenotipo.getAllele(l).intValue() == (k + 1)) {
    						//costo = costo + problema.getDistanciaEntre(j,l) * problema.getFlujoEntre(k,i);
    						//System.out.println("Distancia" + problema.getDistanciaEntre(k,l));
    						//System.out.println("Flujo" + problema.getFlujoEntre(i,j));
    					}*/
    				}
    			}
    		}
    	}/*System.out.println(fenotipo.toString());
    	int tam = fenotipo.size();
    	System.out.println("tamaño " + tam);
    	for (int i = 0; i < tam; i++) {
    		for (int j = i; j < tam; j++) {
    			for (int k = 0; k < tam; k++) {
    				for (int l = k; l < tam; l++) {
    					if (fenotipo.getAllele(i).intValue() == (k + 1) && fenotipo.getAllele(j).intValue() == (l + 1)) {
    						costo = costo + problema.getDistanciaEntre(k,l) * problema.getFlujoEntre(i,j);
    						//System.out.println("Distancia" + problema.getDistanciaEntre(k,l));
    						//System.out.println("Flujo" + problema.getFlujoEntre(i,j));
    						System.out.println("Entramos con");
    						System.out.println("Valor de i " + i);
    						System.out.println("Valor de j " + j);
    						System.out.println("Valor de k " + k);
    						System.out.println("Valor de l " + l);
    					}
    				}
    			}
    		}
    	}*/
    	
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