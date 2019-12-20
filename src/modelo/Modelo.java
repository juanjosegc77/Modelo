package modelo;

import java.io.IOException;
import java.util.*;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

/**
 * @author I.B. Juan José Guzmán Cruz
 * 06 mayo 2019
 */

public class Modelo {
    
    public void testCorrelation(){
        double x[] = {3,5,9,10,20,21,24,24,27,35};
        double y[] = {100,90,80,45,50,50,60,40,25,20};

        System.out.println("testCorrelation \n");
        System.out.println("x: " + Arrays.toString(x));
        System.out.println("y: " + Arrays.toString(y));
        System.out.println("Covariance: " + new Covariance().covariance(x,y));
        System.out.println("Pearson's Correlation: " + new PearsonsCorrelation().correlation(x,y));
        System.out.println("Spearman's Correlation: " + new SpearmansCorrelation().correlation(x,y));
        System.out.println("Kendall's Correlation: " + new KendallsCorrelation().correlation(x,y));
    }
    
    public void testMatrixCorrelation(){
    double x[] = {10, 8, 13, 9, 11, 14, 6, 4, 12, 7, 5};
    double y[][] = {{10, 8, 13},{8.04, 6.95, 7.58},{9.14, 8.14, 8.74}};
    
    System.out.println("\n===========================\n");
    System.out.println("testMatrixCorrelation \n");
    System.out.println("x : " + Arrays.toString(x));
    System.out.println("y : ");
    for (int i = 0; i < y.length; i++) {
        for (int j = 0; j < y[i].length; j++) {
            System.out.print(y[i][j] + " ");
        }
        System.out.println();
    }
    System.out.println("PearsonsCorrelation : ");
    RealMatrix matrix = new PearsonsCorrelation().computeCorrelationMatrix(y);
    
    double m[][] = matrix.getData();
    int f = m.length;
    int c = m.length;
    for (int i=0;i<f;i++){
        System.out.print ("[");
        for (int j=0;j<c; j++)
            System.out.print (" " + m[i][j] + " ");/* Elementos de Fila*/
        System.out.println("]"); /* Salto al finalizar los Elementos de la Fila i */
}
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        Modelo modelo = new Modelo();
        modelo.testCorrelation();
        modelo.testMatrixCorrelation();

        Funciones f = new Funciones();
        f.crearFunciones();
    }
    
}