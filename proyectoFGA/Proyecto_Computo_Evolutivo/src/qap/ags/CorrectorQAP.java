package qap.ags;

import gaframework.*;
import java.util.*;


/**
 * Clase que corrige los genotipos de una poblacion que pertenece a la representacion
 * de una instancia del problema del QAP
 **/
public class CorrectorQAP implements Corrector<ArrayList<Boolean>> {

    
    private int numObjetos;

    /**
     * Constructor que recibe el numero de objetos (y luegares) presentes en el problema
     * @param obejtos El numero de objetos
     **/
    public CorrectorQAP(int objetos) {
        if (objetos < 0) {
            throw new IllegalArgumentException("El numero de objetos no puede ser" +
                                                " menor o igual a cero");
        } else
            this.numObjetos = objetos;
    }



    /**
     * Repara un genotipo de una instancia del problema QAP, identificando los objetos
     * faltantes y sobrantes, remplazandolas de izquierda a derecha
     * @param g EL genotipo a reparar
     * @return Un genotipo reparado a partir del dado como parametro
     **/
    public Genotype<ArrayList<Boolean>> repairGenotype (Genotype<ArrayList<Boolean>> g) {
        Codificador codif = new Codificador(numObjetos);
        Phenotype<Integer> corregido = new Phenotype<>(numObjetos);
        Phenotype<Integer> porCorregir = codif.decode(g);
        ArrayList<Integer> malos = new ArrayList<>();
        boolean[] objetos = new boolean[numObjetos + 1];
        Integer objetoActual = new Integer(0);
        for (int i = 0; i < numObjetos; i++) {
            objetoActual = porCorregir.getAllele(i);
            if (!fueColocado(objetoActual, objetos)){
                objetos[objetoActual.intValue()] = true;
                corregido.setAllele(i,objetoActual);
            } else 
                malos.add(new Integer(i));
        }
        for (Integer e : malos) {
            objetoActual = encuentraNoColocado(objetos);
            objetos[objetoActual.intValue()] = true;
            corregido.setAllele(e.intValue(),objetoActual);
        }
        boolean bien = true;
        for (int k = 1; k < numObjetos; k++)
            bien = bien && objetos[k];
        if (bien) {
            return codif.encode(corregido);
        } else {
            System.out.println("No lo corrigio :(");
            return g;
        }
    }



        /**
         * Dada un arreglo que representa los objetos en sus lugares y un objeto como entero
         * verifica si el objeto ha sido colocadi o no
         * @param objeto El objeto a verificar
         * @param objetos El arreglo de booleanos de los objetos que han sido 
         * colocados y las que no han sido colocados
         * @return true si el objeto ha sido colocado y false en otro caso
         **/
        private boolean fueColocado(Integer objeto, boolean[] objetos) {
            int valorObjeto = objeto.intValue();
            if (valorObjeto >= objetos.length || valorObjeto <= 0)
                return true;
            else
                return objetos[valorObjeto];
        }



        /**
         * Dada un arreglo de los objetos colocados y no colocados busca el primer
         * objeto (de izquierda a derecha) que no ha sido colocado
         * @param objetos El arreglo con los objetos
         * @return Un Integer con el objeto no colocado o -1 si todos han sido 
         * colocados
         **/
        private Integer encuentraNoColocado(boolean[] objetos) {
            int i = 1;
            while (i < objetos.length && objetos[i])
                i++;
            if (i == objetos.length)
                return new Integer(-1);
            else
                return new Integer(i);
        }
        
}