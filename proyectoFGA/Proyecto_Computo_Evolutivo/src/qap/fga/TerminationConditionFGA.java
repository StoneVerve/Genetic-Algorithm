package qap.fga;


/**
 * Implentacion de una condicion de para para un algoritmo genetico FGA donde se
 * verifica el numero de iteraciones que se ha realizado el agoritmos (generaciones)
 */
public class TerminationConditionFGA<F> {

    private int numIteraciones;

    /**
     * Construye una nueva condicion que verifca el numero de generaciones
     * indicado
     * @param numIte El numero de iteraciones con las que la condicion sera cumplida
     **/
    public TerminationConditionFGA(int numIte) {
        numIteraciones = numIte;
    }

    /**
     * Verifica si la condicion de para ha sido alcanzada o no para una poblacion
     * @param poblacion La poblacion donde se verifica la condicion de paro
     * @return true si la condicion ha sido alcanzada y false en cualquier otro caso
     **/
    public boolean conditionReached(PopulationFGA<F> poblacion) {
        return poblacion.getGeneracion() >= numIteraciones;
    
    }

}