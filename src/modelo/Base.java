package modelo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author I.B. Juan José Guzmán Cruz
 * 06 mayo 2019
 */

public class Base {
    
    /**
     * Define la base de conocimientos a utilizar.
     * @return Devuelve la base de conocimientos en un Map.
     */
    public Map<String, String[][]> CrearBase(){
        Map<String, String[][]> hmap = new HashMap<String, String[][]>();
        
        String p1 = "CT";
        String[][] v1 = {{"recomendado", "0.0", "200.0", "1.0", "1.0" },
                        { "limitrofe", "200.0", "240.0", "1.0", "0.0" },
                        { "alto_riesgo", "240.0", "400.0", "0.0", "0.0" }};
        hmap.put(p1, v1);
        
        String p2 = "C-LDL";
        String[][] v2 = {{"recomendado", "0.0", "130.0", "1.0", "1.0" },
                        { "limitrofe", "130.0", "160.0", "1.0", "0.5" },
                        { "alto_riesgo", "160.0", "190.0", "0.5", "0.0" },
                        { "muy_alto_riesgo", "190.0", "400.0", "0.0", "0.0" }};
        hmap.put(p2, v2);
        
        String p3 = "TG";
        String[][] v3 = {{"recomendado", "0.0", "150.0", "1.0", "1.0" },
                        { "limitrofe", "150.0", "200.0", "1.0", "0.5" },
                        { "alto_riesgo", "200.0", "1000.0", "0.5", "0.0" },
                        { "muy_alto_riesgo", "1000.0", "1100.0", "0.0", "0.0" }};
        hmap.put(p3, v3);
        
        String p4 = "C-HDL";
        String[][] v4 = {{"alto_riesgo", "0.0", "35.0", "0.0", "1.0" },
                        {"recomendado", "35.0", "70.0", "1.0", "1.0" }};
        hmap.put(p4, v4);
        
        String p5 = "PCR";
        String[][] v5 = {{"bajo_riesgo", "0.0", "1.0", "1.0", "1.0" },
                        { "riesgo_promedio", "1.0", "3.0", "1.0", "0.5" },
                        { "alto_riesgo", "3.0", "5.0", "0.5", "0.0" }};
        hmap.put(p5, v5);
        
        String p6 = "TSH";
        String[][] v6 = {{"hipotiroidismo_secundario", "0.0", "1.0", "0.0", "1.0" },
                        { "normal", "1.0", "4.5", "1.0", "1.0" },
                        { "Hipotiroidismo subclínico", "4.5", "10.0", "1.0", "0.5" },
                        { "Hipotiroidismo primario", "10.0", "15.5", "0.5", "0.0" }};
        hmap.put(p6, v6);
        
        String p7 = "Vitamina D";
        String[][] v7 = {{"deficiencia", "0.0", "20.0", "0.0", "1.0"},
                        {"normal", "20.0", "50.0", "1.0", "1.0" }};
        hmap.put(p7, v7);
            
        return hmap;
    }
}
