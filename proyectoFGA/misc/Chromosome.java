package qap.fga;

import java.util.*;

/**
 * Clase que modela un cromosoma perteneciente al FGA para QAP
 **/
public class ChromosomeQAP {

    private int[] objects;
    private double[] predispositions;

	/**
	 * Crea un cromosoma vacio con cuya con una longitud definida
	 * @param length La longitud del cromosoma
	 **/
    public Chromosome(int length) {
        predispositions = new double[length];
        objects = new int[length];
    }

	/**
	 * Crea un cromosoma con dado un arreglo de predisposiciones y otro de objects
	 * donde la predisposicion en el indice i correponde al object en el indice i
	 * @param objects El arreglo de objects
	 * @param predispositions El arreglo de predisposiciones
	 **/
    public Chromosome(int[] objects, double[] predispositions) {
        if (objects.length == predispositions.length) {
            this.predispositions = predispositions;
            this.objects = objects;
        } else {
            throw new IllegalArgumentException("El numero de objects y predisposiciones" +
                                               " no es el mismo");
        }
    }

	/**
	 * Regresa la predisposicion correpondiente al objeto i
	 * @param index La predisposicion correpondiente al objeto que se quiere obtener
	 * @return La predisposicion en el indice i
	 **/
    public double getPredispositionAt(int index) {
        if (index >= predispositions.length)
            throw new IndexOutOfBoundsException("El indice no existe");
        else
            return predispositions[index];
    }

	/**
	 * Regresa el gen correpondiente a la posicion i
	 * @param index La posicion del gen que se quiere obtener
	 * @return El gen correspondiente a la posicion indicada
	 **/
    public int getobjectAt(int index) {
        if (index >= objects.length)
            throw new IndexOutOfBoundsException("El indice no existe");
        else
            return objects[index];
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
	 * Asigna un objeto en el indice indicado
	 * @param index El objeto a cambiar
	 * @param object El nuevo objeto
	 **/ 
    public void setGen(int index, int object) {
        if (index >= objects.length)
            throw new IndexOutOfBoundsException("El indice no existe");
        else
            objects[index] = object;
    }

	/**
	 * Da el tamano del cromosoma (numero de objetos)
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
        String salida = "";
        for (int i = 0; i < predispositions.length; i++) 
            salida = salida + predispositions[i] + "|";
        salida = salida + '\n';
        for (int j = 0; j < predispositions.length; j++) 
            salida = salida + objects[j] + "|";
        return salida;
    }

	/*
	 * Falta hacer el metodo pero me da flojera
    public boolean equals(Object o) {
        @SuppressWarnings("unchecked")
        Chromosome<G> objeto = (Chromosome<G>) o;
        int tamanoAct = this.size();
        boolean genActIgual = true;
        boolean predActIgual = false;
        if (tamanoAct == objeto.size() && ) {
            for (int i = 0; i < tamanoAct && genActIgual && predActIgual;i++){
                genActIgual = objeto.getGenAt(i).equals(this.getGenAt(i));
                predActIgual = objeto.getPredispositionAt(i) == this.getPredispositionAt(i);
            }
            return true;
        } else
            return false;
    }*/

}