package qap.ags;

import gaframework.*;
import java.util.*;

/**
 * Implementacion de la operacion Cruzamiento de un punto
 */
public class CruzamientoUnPunto<G> implements CrossoverOp<G>{

    private double probCruza;

    /**
     * Se construye un objeto para poder realizar el operador de cruzamiento
     * @param probCruza - La probabilidad de que suceda el cruzamiento
     */
    public CruzamientoUnPunto(double probCruza){
    this.probCruza = probCruza;
    }

    /**
     * Método para obtener la probabilidad de cruzamiento
     * @return probCruza - La probabilidad de cruzamiento
     */
    public double getProbCruza(){
    return this.probCruza;
    }
    
    /**
     * Método para hacer cruzamiento de un punto
     * Seleccionamos un punto al azar de dos genotipos
     * Combinamos los dos genotipos para generar dos nuevos
     * @param lista - La lista de genotipos de la poblacion actual
     * @return lista - La lista con los dos nuevos genotipos que generamos
     */
    @Override
    public List<Genotype<G>> crossover(List<Genotype<G>> lista){
        Random random = new Random();
        double probCruza = Math.random();
        double probabilidad = this.getProbCruza();
        LinkedList<Genotype<G>> hijos = new LinkedList<>();
       if(!lista.isEmpty() && lista.size() > 1){
            if(probabilidad > probCruza){
                Genotype<G> gen1 = lista.get(0);
                Genotype<G> gen2 = lista.get(1);
                int tam = gen1.size();
                int punto = random.nextInt(tam - 1) + 1;
                Genotype<G> cop1 = new Genotype<>(tam);
                Genotype<G> cop2 = new Genotype<>(tam);
                for(int i=0;i < punto;i++){
                    G g1 = gen1.getGene(i);
                    cop1.setGene(i,g1);
                    G g2 = gen2.getGene(i);
                    cop2.setGene(i,g2);
                }
                for(int j=punto; j < tam;j++){
                    G g3 = gen2.getGene(j);
                    cop1.setGene(j,g3);
                    G g4 = gen1.getGene(j);
                    cop2.setGene(j,g4);
                }
                hijos.add(cop1);
                hijos.add(cop2);
            } else
                hijos.add(lista.get(0));
                hijos.add(lista.get(1));
        }
        return hijos;
    }
}