package org.pf.adc.Clases;


import org.pf.adc.Constructores.Table;
import org.pf.adc.Interfaces.Algorithm;
import org.pf.adc.Patrones.EuclideanDistance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SongRecSys {
    private RecSys recsys;
    private static final String ACTION_1 = "train";
    private static final String ACTION_2 = "kmeans";

    SongRecSys(String method) throws Exception {
        String sep = System.getProperty("file.separator");
        String ruta = "src\\main\\resources\\files";

        // File names (could be provided as arguments to the constructor)
        Map<String,String> filenames = new HashMap<>();
        filenames.put("knn"+ ACTION_1,ruta+sep+"songs_train.csv");
        filenames.put("knn"+"test",ruta+sep+"songs_test.csv");
        filenames.put(ACTION_2 + ACTION_1,ruta+sep+"songs_train_withoutnames.csv");
        filenames.put(ACTION_2+"test",ruta+sep+"songs_test_withoutnames.csv");

        // Algorithms
        Map<String, Algorithm> algorithms = new HashMap<>();
        algorithms.put("knn",new KNN(new EuclideanDistance()));
        algorithms.put(ACTION_2,new Kmeans(15, 200, 4321, new EuclideanDistance()));

        // Tables
        Map<String, Table> tables = new HashMap<>();
        String [] stages = {ACTION_1, "test"};
        CSV csv = new CSV();
        for (String stage : stages) {
            tables.put("knn" + stage, csv.readTableWithLabels(filenames.get("knn" + stage)));
            tables.put(ACTION_2 + stage, csv.readTable(filenames.get(ACTION_2 + stage)));
        }

        // Names of items
        List<String> names = readNames(ruta+sep+"songs_test_names.csv");

        // Start the RecSys
        this.recsys = new RecSys(algorithms.get(method));
        this.recsys.train(tables.get(method+ACTION_1));
        this.recsys.run(tables.get(method+"test"), names);

        // Given a liked item, ask for a number of recomendations
        String likedName = "Lootkemia";
        List<String> recommendedItems = this.recsys.recommend(likedName,5);

        // Display the recommendation text (to be replaced with graphical display)
        reportRecommendation(likedName,recommendedItems);
    }

    private List<String> readNames(String fileOfItemNames) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileOfItemNames))) {
            String line;
            List<String> names = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                names.add(line);
            }
            return names;
        }
    }

    private void reportRecommendation(String likedName, List<String> recommendedItems) {
        System.out.println("If you liked \""+likedName+"\" then you might like:");
        for (String name : recommendedItems)
        {
            System.out.println("\t * "+name);
        }
    }

    public static void main(String[] args) throws Exception {
        new SongRecSys("knn");
        new SongRecSys(ACTION_2);
    }
}