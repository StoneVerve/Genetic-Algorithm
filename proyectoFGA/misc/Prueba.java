import qap.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File; 


public class Prueba {
	
	public static void main(String[] args) {
		/*int [][] flujos;
		int [][] distancias;
		try {
			File problema = new File("./Problemas_resolver/chr12a.dat");
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
				System.out.println("La linea " + linea);
				int t = 0;
				int j = 0;
				System.out.println("L " + linea.length());
				while (j < linea.length() && t < dimension) {
					System.out.println("Antes " + j);
					while (linea.charAt(j) == ' ') 
						j++;
					while (j < linea.length() && Character.isDigit(linea.charAt(j))) {
						valor = valor + linea.charAt(j);
						j++;
					}
					flujos[i][t] = Integer.parseInt(valor);
					System.out.println("Valor " + valor);
					valor = "";
					t++;
				}
				//for (int j = 0; j < dimension; j++)
				//	flujos[i][j] = Integer.parseInt(renglon[j]);
			}
			
			
			// Llenamos el arreglo de flujos
			/*String[] renglon;
				System.out.println("Dimension :" + dimension);
			for (int i = 0; i < dimension && linea != null;i++) {
				linea = lector.readLine();
				linea = linea.trim();
				System.out.println(linea);
				//renglon = linea.split(" ");
				int m = 0;
				for (int j = 0; j < linea.length(); j++)
					while (linea.charAt(j) == ' ')
						j++;
					linea.charAt(j)
					
				//for (int j = 0; j < dimension; j++)
					//System.out.print(renglon[j].replaceAll(" ", "") + ",");
					//flujos[i][j] = Integer.parseInt(renglon[j].trim());
				//System.out.print("");
			}*/
			/*linea = lector.readLine();
			for (int i = 0; i < dimension && linea != null;i++) {
				linea = lector.readLine();
				System.out.println(linea);
				renglon = linea.split(" ");
				//for (int j = 0; j < dimension; j++)
					//System.out.print(renglon[j] + ",");
					//distancias[i][j] = Integer.parseInt(renglon[j]);
				//System.out.print("");
			}
		} catch (FileNotFoundException e) {
			System.out.println("El archivo no existe");
			distancias = new int[1][1];
			flujos = distancias;
		} catch (IOException f) {
			System.out.println("Ocurrio un error en la lectura del archivo");
			distancias = new int[1][1];
			flujos = distancias;
		}
	}*/
		QuadraticAP pro = new QuadraticAP (new File("./Problemas_resolver/chr12a.dat"));
		System.out.println(pro.toString());
	}


}