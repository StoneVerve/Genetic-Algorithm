import qap.*;
import qap.ags.*;
import qap.fga.*;

import gaframework.*;

import java.util.*; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File; 

/**
 * Clase principal para la ejecucion de los algoritmos geneticos para
 * resolver una instancia del QAP
 **/
public class Main {

    private static final String uso = "\nPor favor ejecute de la "
	+"siguiente manera:\n"
	+"\nAlgoritmo Genetico Simple\njava -jar [Problema] [Archivo Parametros] -a"
	+"\nAlgoritmo Genetico Fluido\njava -jar [Problema] [Archivo Parametros] -f";

    
    
    public static void main(String[] args) {
		if (args.length != 3){
	    	System.out.println("Numero de argumentos erroneo."+uso);
	    	return;
		}
		String problema = args[0];
		String opcion = args[2];
		String parametros = args[1];

		if (!(opcion.equals("-f")) && !(opcion.equals("-a"))){
		    System.out.println(opcion+": opción invalida."+uso);
		    return;
		}

		QuadraticAP pro = new QuadraticAP (new File(problema.trim()));
		if (pro.getDimension() == 1)
			return;
		if (opcion.equals("-f")){
		    String linea = "";
			double probCruza = 0.0;
			double gLR = 0.0;
			double iLR = 0.0;
			double dR = 0.0;
			int iter = 1;
			try {
				BufferedReader lector = new BufferedReader(new FileReader(new File(parametros)));
				while((linea =  lector.readLine()) != null) {
					String[] vals = linea.split(":");
					if (vals[0].equals("Probabilidad de Cruza")) 
						probCruza = Double.parseDouble(vals[1].trim());
					 else if (vals[0].equals("Global Learning Rate"))
						gLR = Double.parseDouble(vals[1].trim());
					 else if (vals[0].equals("Individual Learning Rate"))
						iLR = Double.parseDouble(vals[1].trim());
					 else if (vals[0].equals("Diversity Rate"))
						dR = Double.parseDouble(vals[1].trim());
					else if (vals[0].equals("Iteraciones"))
						iter = Integer.parseInt(vals[1].trim());
				}
				lector.close();
				if (probCruza == 0.0 || gLR == 0.0 || iLR == 0.0 || dR == 0.0 ||
					iter == 0.0) {
					throw new IllegalArgumentException();
				}
			} catch (NumberFormatException e4) {
				System.out.println("Alguno de los parametro no es un numero");
				return;
			} catch (IllegalArgumentException e) {
				System.out.println("No se incluyo algun parametro en el archivo o alguno " +
															" tiene el valor 0");
				return;
			} catch (FileNotFoundException e5) {
   	    		System.out.println("El archivo de parametros no existe");
				return;
			} catch (Exception e3) {
				return;
			}
		    SelectionRuletaFGA<Integer> sely = new SelectionRuletaFGA<>(2);
		    CrossoverFGA<Integer> crusy = new CrossoverFGA<>(probCruza, iLR); // 0.9, 0.01
		    TerminationConditionFGA<Integer> termy = new TerminationConditionFGA<>(iter);//10000
		    BornAnIndividualQAP bornToBeAlive = new BornAnIndividualQAP(gLR,dR); //0.1, 0.05
		    FluidGeneticAlgorithm fluid = new FluidGeneticAlgorithm(problema,
		    															 null,
		    															 sely,
		    															 crusy,
		    															 bornToBeAlive,
		    															 termy);
		    fluid.run();
		} else {
			String linea = "";
			double probCruza = 0.0;
			double probMut = 0.0;
			int iter = 1;
			try {
				BufferedReader lector = new BufferedReader(new FileReader(new File(parametros)));
				while((linea =  lector.readLine()) != null) {
					String[] vals = linea.split(":");
					if (vals[0].equals("Probabilidad de Cruza")) 
						probCruza = Double.parseDouble(vals[1].trim());
					 else if (vals[0].equals("Probabilidad de Mutacion"))
						probMut = Double.parseDouble(vals[1].trim());
					else if (vals[0].equals("Iteraciones"))
						iter = Integer.parseInt(vals[1].trim());
				}
				lector.close();
				if (probCruza == 0.0 || probMut == 0.0 || iter == 0) {
					throw new IllegalArgumentException();
				}
			} catch (NumberFormatException e4) {
				System.out.println("Alguno de los parametro no es un numero");
				return;
			} catch (IllegalArgumentException e) {
				System.out.println("No se incluyo algun parametro en el archivo o alguno " +
															" tiene el valor 0");
				return;
			} catch (FileNotFoundException e5) {
   	    		System.out.println("El archivo de parametros no existe");
				return;
			} catch (Exception e3) {
				return;
			}
			System.out.println("Prob Cruza" + Double.toString(probCruza));
			System.out.println("Prob Mutac" + Double.toString(probMut));
			SelectionRuleta<ArrayList<Boolean>,Integer> sely = new SelectionRuleta<>(2);
		    CruzamientoUnPunto<ArrayList<Boolean>> crusy = new CruzamientoUnPunto<>(probCruza);//0.8
		    MutadorSwap<ArrayList<Boolean>> muty = new MutadorSwap<>(probMut);//
		    TerminationConditionQAP<ArrayList<Boolean>,Integer> termy = new TerminationConditionQAP<>(iter);
		    
		    GeneticAlgorithmQAP simple = new GeneticAlgorithmQAP(problema, null, sely,
		    													crusy, muty, termy);
		    simple.run();
		}
	}
}



