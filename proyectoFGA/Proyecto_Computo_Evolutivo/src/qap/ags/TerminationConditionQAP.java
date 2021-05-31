package qap.ags;

import gaframework.*;
import java.util.*;


/**
 * Implentacion de una condicion de para para un algoritmo genetico donde se
 * verifica el numero de iteraciones que se ha realizado el agoritmos (generaciones)
 */
public class TerminationConditionQAP<G,P> implements TerminationCondition<G,P> {

    private int numIteraciones;

    /**
     * Genera una nueva condicion de terminacion dado un numero de iteraciones objetivo
     * @param numIte El numero de iteraciones objetivo
     **/
    public TerminationConditionQAP(int numIte) {
        numIteraciones = numIte;
    }

    /**
     * Verifica si la condicion de para ha sido alcanzada o no para una poblacion
     * @param opoblacion La poblacion donde se verifica la condicion de paro
     * @return true si la condicion ha sido alcanzada y false en cualquier otro caso
     **/
    public boolean conditionReached(Population<G,P> poblacion) {
        return poblacion.getGeneration() >= numIteraciones;
    
    }

}