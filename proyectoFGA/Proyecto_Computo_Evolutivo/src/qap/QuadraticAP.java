package qap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File; 


/**
 * Modelacion de una instancia del problema de asignacion cuadratico
 **/
public class QuadraticAP {

    private int[][] distancias;
    private int[][] flujos;


    /**
     * Construye un instancia del problema de QAP dada una matriz de distancias 
     * u otra de flujos
     **/
    public QuadraticAP(int[][] dis, int[][] flu) {
        distancias = dis;
        flujos = flu;
    }



    /**
     * Construye una instancia de QAP dada un archivo de la biblioteca 
     **/
    public QuadraticAP(File problema) {
        try {
            BufferedReader lector = new BufferedReader(new FileReader(problema));
            //Obtenemos la dimension del problema
            String linea = lector.readLine();
            int dimension = Integer.parseInt(linea);
            flujos = new int[dimension][dimension];
            distancias = new int[dimension][dimension];
            // Espacio blanco
            lector.readLine();
            // Llenamos el arreglo de flujos
            String[] renglon;
            String valor = "";
            for (int i = 0; i < dimension && linea != null;i++) {
                linea = lector.readLine();
                linea = linea.trim();
                int t = 0;
                int j = 0;
                while (j < linea.length() && t < dimension) {
                    while (linea.charAt(j) == ' ') 
                        j++;
                    while (j < linea.length() && Character.isDigit(linea.charAt(j))) {
                        valor = valor + linea.charAt(j);
                        j++;
                    }
                    flujos[i][t] = Integer.parseInt(valor);
                    valor = "";
                    t++;
                }
            }
            //Llenamos el arreglo de distancias
            linea = lector.readLine();
            for (int i = 0; i < dimension && linea != null;i++) {
                linea = lector.readLine();
                linea = linea.trim();
                int t = 0;
                int j = 0;
                while (j < linea.length() && t < dimension) {
                    while (linea.charAt(j) == ' ') 
                        j++;
                    while (j < linea.length() && Character.isDigit(linea.charAt(j))) {
                        valor = valor + linea.charAt(j);
                        j++;
                    }
                    distancias[i][t] = Integer.parseInt(valor);
                    valor = "";
                    t++;
                }
            }
            lector.close();
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe");
            distancias = new int[1][1];
            flujos = distancias;
        } catch (IOException f) {
            System.out.println("Ocurrio un error en la lectura del archivo");
            distancias = new int[1][1];
            flujos = distancias;
        }
    }


    /**
     * Regresa un arreglo con los valores de flujos del objeto indicado al resto
     * de los objetos
     * @param id El objeto cuyos flujos se quieren obtener
     * @return Un arreglo con los flujos a las otros objetos
     **/
    public int[] getFlujos(int id) {
        if (id < flujos.length)
            throw new IllegalArgumentException("El objeto no existe");
        else {
            int[] copia = new int[flujos.length];
            for (int i = 0; i < flujos.length; i++)
                copia[i] = flujos[id][i];
            return copia;
        }
    }



    /**
     * Regresa un arreglo con los valores de distancias del objeto indicado al resto
     * de los objetos
     * @param id El objeto cuyos distancias se quieren obtener
     * @return Un arreglo con los distancias a las otros objetos
     **/
    public int[] getDistancias(int id) {
        if (id > distancias.length)
            throw new IllegalArgumentException("El objeto no existe");
        else {
            int[] copia = new int[distancias.length];
            for (int i = 0; i < distancias.length; i++)
                copia[i] = distancias[id][i];
            return copia;
        }
    }



    /**
     * Regresa la dimension del problema (numero de objetos y localizaciones)
     * @return La dimension del problema
     **/
    public int getDimension() {
        return distancias.length;
    }


    /**
     * Regresa el flujo entre dos objetos
     * @param id1 El primer objeto
     * @param id2 El segundo objeto
     * @return El flujo entre los dos objetos
     **/
    public int getFlujoEntre(int id1, int id2) {
        if (id1 >= flujos.length || id2 >= flujos.length ||  id1 < 0 || id2 < 0)
            throw new IllegalArgumentException("El objeto no existe");
        else
            return flujos[id1][id2];
    }


    /**
     * Regresa la distancia entre dos localizaciones
     * @param id1 La primer localizacion
     * @param id2 La segunda localizacion
     * @return La distancia entre las dos localizaciones
     **/
    public int getDistanciaEntre(int id1, int id2) {
        if (id1 >= distancias.length || id2 >= distancias.length || id1 < 0 || id2 < 0)
            throw new IllegalArgumentException("El objeto no existe");
        else
            return distancias[id1][id2];
    }


    /**
     * Genera una representacion en String del problema 
     * @return El string que representa al problema
     **/
    public String toString() {
        // Convertimos la matriz de Distancias
        String salida = "Distancias \n";
        for (int i = 0; i < distancias.length; i++) {
            for (int j = 0; j < distancias.length; j++)
                salida = salida + "|" + distancias[i][j] + "|";
            salida = salida + '\n';
        }
        // Convertimos la matriz de Flujos
        salida = salida + "Flujos \n";
        for (int i = 0; i < flujos.length; i++) {
            for (int j = 0; j < flujos.length; j++)
                salida = salida + "|" + flujos[i][j] + "|";
            salida = salida + '\n';
        }
        return salida;
    }

}



