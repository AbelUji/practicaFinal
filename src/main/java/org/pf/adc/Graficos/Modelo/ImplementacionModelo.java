package org.pf.adc.Graficos.Modelo;

import org.pf.adc.Clases.CSV;
import org.pf.adc.Clases.KNN;
import org.pf.adc.Clases.Kmeans;
import org.pf.adc.Clases.RecSys;
import org.pf.adc.Constructores.Table;
import org.pf.adc.Excepciones.ClusterException;
import org.pf.adc.Graficos.Vista.Vista;
import org.pf.adc.Interfaces.Algorithm;
import org.pf.adc.Interfaces.Distance;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImplementacionModelo implements Modelo{
    private Vista vista;
    private RecSys recsys;
    @Override
    public void setVista(Vista vista) {
        this.vista=vista;
    }

    @Override
    public void recSys(String method, Distance distancia) throws IOException, ClusterException {
        String sep = System.getProperty("file.separator");
        String ruta = "src\\main\\resources\\files";

        // File names (could be provided as arguments to the constructor to be more general)
        Map<String,String> filenames = new HashMap<>();
        filenames.put("knn"+"train",ruta+sep+"songs_train.csv");
        filenames.put("knn"+"test",ruta+sep+"songs_test.csv");
        filenames.put("kmeans"+"train",ruta+sep+"songs_train_withoutnames.csv");
        filenames.put("kmeans"+"test",ruta+sep+"songs_test_withoutnames.csv");

        // Algorithms
        Map<String, Algorithm> algorithms = new HashMap<>();
        algorithms.put("knn",new KNN(distancia));
        algorithms.put("kmeans",new Kmeans(15, 200, 4321, distancia));

        // Tables
        Map<String, Table> tables = new HashMap<>();
        String [] stages = {"train", "test"};
        CSV csv = new CSV();
        for (String stage : stages) {
            tables.put("knn" + stage, csv.readTableWithLabels(filenames.get("knn" + stage)));
            tables.put("kmeans" + stage, csv.readTable(filenames.get("kmeans" + stage)));
        }

        // Names of items
        List<String> names = anadirCanciones(ruta+sep+"songs_test_names.csv");

        // Start the RecSys
        this.recsys = new RecSys(algorithms.get(method));
        this.recsys.train(tables.get(method+"train"));
        this.recsys.run(tables.get(method+"test"), names);
    }

    public List<String> anadirCanciones(String fichero) throws IOException {
        String line;
        List<String> names = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fichero));

        while ((line = br.readLine()) != null) {
            names.add(line);
        }
        br.close();
        return names;
    }

    @Override
    public RecSys getRecsys(){
        return this.recsys;
    }

    public void gestionarStage() throws ClusterException, IOException {
        vista.crearStage();
    }

}
