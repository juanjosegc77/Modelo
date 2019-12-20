package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries.PieSeriesRenderStyle;
import org.knowm.xchart.style.PieStyler.AnnotationType;

/**
 * @author I.B. Juan José Guzmán Cruz
 * 06 mayo 2019
 */

public class Funciones {
    List<XYChart> charts = new ArrayList<XYChart>();
    
    /**
     * Calcular la función de membresía, evaluar los valores originales es esta
     * y mostrar la representación gráfica.
     * @throws IOException 
     */
    public void crearFunciones() throws IOException{
        
        // Se obtiene las condiciones indicadas en la base de conocimientos y
        // se almacenan en un HashMap que se utiliza como diccionario.
        Base base = new Base();
        Map<String, String[][]> hmap = new HashMap();
        hmap = base.CrearBase();
        
        // Recorre sobre el contenido del HashMap utilizando un iterador.
        Set set = hmap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            //System.out.println("key is: "+ mentry.getKey());
            
            // Variables iniciales.
            String [][] v = (String[][]) mentry.getValue();
            ArrayList<String> etiqueta = new ArrayList();
            ArrayList<Double> mem_x = new ArrayList();
            ArrayList<Double> mem_y = new ArrayList();
            
            // Se obtienen los datos del archivo de texto que tenga por nombre
            // el valor del parámetro en turno. El contenido del archivo se
            // almacena temporalmente en un vector de tipo double.
            String parametro = mentry.getKey().toString();
            double[] y = this.leerArchivo(parametro);
            double[] eval = new double[y.length];
            
            // Se evalua cada uno de los valores del archivo de texto de acuerdo
            // a las condiciones expecificadas en la base de conocimiento.
            // La evaluación se lleva a cabo con la ecuación de la recta.
            for(int i=0; i<v.length; i++){
                double x1 = Double.parseDouble(v[i][1]);
                double x2 = Double.parseDouble(v[i][2]);
                double y1 = Double.parseDouble(v[i][3]);
                double y2 = Double.parseDouble(v[i][4]);
                
                double m = (y2 - y1) / (x2 - x1);
                double b = y1 - m * x1;
                
                etiqueta.add(v[i][0]);
                mem_x.add(x1);
                mem_x.add(x2);
                mem_y.add(y1);
                mem_y.add(y2);
                
                int count = 0;
                for(int n=0; n<y.length; n++){
                    if(y[n] >= x1 && y[n] <= x2){
                        double val = m*y[n] + b;
                        eval[n] = val;
                        count++; 
                    }
                }
            } 
            
            double[] xData = new double[mem_x.size()];
            double[] yData = new double[mem_y.size()];
            for(int i = 0; i < xData.length; i++){
                xData[i] = mem_x.get(i);
                yData[i] = mem_y.get(i);
            }
            
            graficarValoresIniciales(parametro);
            graficarFuncion(parametro, xData, yData);
            graficarEvaluacion(parametro, y, eval);
        }
        
        new SwingWrapper<XYChart>(charts).displayChartMatrix();
    }
    
    /**
     * Lee un archivo de texto y devuelve los datos en un vector de tipo double.
     * @param parametro Nombre del archivo a buscar.
     * @return Devuelve los valores en un vector de tipo double.
     */
    public double[] leerArchivo(String parametro){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        
        String class_path = Funciones.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String class_package = modelo.Funciones.class.getPackage().getName();
        
        // Nota: es necesario colocar los archivos de texto con los datos de la 
        // serie de tiempo en una carpeta llamada "valores", el directorio raíz
        // del proyecto cuando se está desarrollando o en la carpeta donde se
        // ejecute el archivo .jar, según sea el caso.
        String path = System.getProperty("user.dir") + "/valores/" + parametro + ".txt";
        
        double[] valores = null;
        try {
           archivo = new File(path);
           fr = new FileReader (archivo);
           br = new BufferedReader(fr);
           
           String linea = br.readLine();
           String[] l = linea.split(",");
           
           valores = new double[l.length];
           for(int i=0; i<l.length; i++){
               valores[i] = Double.valueOf(l[i]);
           } 
        }
        catch(Exception e){
           e.printStackTrace();
        }finally{
           try{                    
              if( null != fr ){   
                 fr.close();     
              }                  
           }catch (Exception e2){ 
              e2.printStackTrace();
           }
        }
      
      return valores;
    }
    
    /**
     * Crea un gráfico de los valores originales almacenados en un archivo de texto.
     * @param parametro Nombre del marcador utilizado que corresponde con el
     * archivo de texto cargado.
     */
    private void graficarValoresIniciales(String parametro){
        
        double[] valores = this.leerArchivo(parametro);
        XYChart chart0 = new XYChartBuilder().xAxisTitle("Días observados")
                .yAxisTitle("Valores obtenido").width(600).height(400).build();
        XYSeries series0 = chart0.addSeries(parametro, valores);
        series0.setMarker(SeriesMarkers.NONE);
        chart0.getStyler().setLegendPosition(LegendPosition.InsideNE);
        charts.add(chart0);
    }
    
    /**
     * Crea un gráfico de la función de membresía asociada a un parámetro determinado.
     * @param parametro Nombre del marcador utilizado que corresponde con el
     * archivo de texto cargado.
     * @param xData Vector con los valores correspondientes a los puntos del eje
     * coordenado de las abscisas.
     * @param yData Vector con los valores correspondientes a los puntos del eje
     * coordenado de las ordenadas.
     */
    private void graficarFuncion(String parametro, double[] xData, double[] yData){
        
        XYChart chart = new XYChartBuilder().xAxisTitle("Rango permitido")
                .yAxisTitle("Valor de pertenencia").width(600).height(400).build();
        chart.getStyler().setYAxisMin(0.0);
        chart.getStyler().setYAxisMax(1.0);
        XYSeries series = chart.addSeries(parametro, xData, yData);
        //series.setMarker(SeriesMarkers.NONE);
        chart.getStyler().setLegendPosition(LegendPosition.InsideNE);
        charts.add(chart);
        //org.knowm.xchart.XYChart chart1 = QuickChart.getChart(parametro, "Rango permitido", "Valor de pertenencia", "p(t)", xData, yData);
        //new SwingWrapper(chart1).displayChart();
        //BitmapEncoder.saveBitmap(chart1, parametro, BitmapFormat.PNG);
    }
    
    /**
     * Crea un gráfico de los valores originales evaluados en su correspondiente
     * función de membresía.
     * @param parametro Nombre del marcador utilizado que corresponde con el
     * archivo de texto cargado.
     * @param y Valores originales cargados desde al archivo de texto.
     * @param eval Valores originales evaluados en la función de membresía correspondiente.
     */
    private void graficarEvaluacion(String parametro, double[] y, double[] eval){
        
        XYChart chart0 = new XYChartBuilder().xAxisTitle("Días observados").yAxisTitle("Valores obtenido").width(600).height(400).build();
        chart0.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        chart0.getStyler().setChartTitleVisible(false);
        chart0.getStyler().setLegendPosition(LegendPosition.InsideSW);
        chart0.getStyler().setMarkerSize(8);
        chart0.addSeries("Valores evaluados", y, eval);
        charts.add(chart0);        
    }   
}