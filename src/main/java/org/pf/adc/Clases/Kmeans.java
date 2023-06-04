package org.pf.adc.Clases;

import org.pf.adc.Constructores.Row;
import org.pf.adc.Constructores.Table;
import org.pf.adc.Excepciones.ClusterException;
import org.pf.adc.Interfaces.Algorithm;
import org.pf.adc.Interfaces.Distance;
import org.pf.adc.Interfaces.DistanceClient;


import java.util.*;

public class Kmeans implements Algorithm<Table>, DistanceClient {
    private int numClusters, numIterations;
    private long seed;
    private Map<Integer,List<Row>> grupos;
    private List<Row> centroides;
    private Table tablaK;
    private Distance distance;

    public Kmeans(int numClusters,int numIterations, long seed, Distance distancia){
        this.numClusters=numClusters;
        this.numIterations=numIterations;
        this.seed=seed;
        this.grupos=new HashMap<>();
        this.centroides=new ArrayList<>();
        this.distance = distancia;
    }
    public List<Row> getCentroides() {
        return centroides;
    }

    public Map<Integer, List<Row>> getGrupos() {
        return grupos;
    }


    @Override
    public void train(Table datos) throws ClusterException {
        if(numClusters>numIterations){
            throw new ClusterException();
        }
        tablaK=datos;
        randomCentroides();
        for(int j=0;j<numIterations;j++) {
            asignarPuntos();
            centroides.clear();
            for (int i = 0; i < numClusters; i++) {
                centroides.add(meanCentroide(grupos.get(i)));
                if(j!=numIterations-1){
                    grupos.get(i).clear();
                }
            }
        }
    }

    public void asignarPuntos(){
        for (int i = 0; i < tablaK.getRows().size(); i++) {
            int numberClass = estimate(tablaK.getRowAt(i).getData());
            if(!grupos.get(numberClass).contains(tablaK.getRowAt(i)))
                grupos.get(numberClass).add(tablaK.getRowAt(i));
        }
    }
    @Override
    public Integer estimate(List<Double> dato){
        int id=-1,contador=0;
        double distMin=Double.MAX_VALUE;
        for(Row element:centroides){
            double distActual=calcularDistancia(dato,element.getData());
            if(distMin > distActual){
                distMin=distActual;
                id=contador;
            }
            contador++;
        }
        return id;
    }

    public double calcularDistancia(List<Double> data_source, List<Double> data){
        return distance.calculateDistance(data_source,data);
    }
    private void randomCentroides(){
        Random random=new Random(seed);
        for(int i=0;i<numClusters;i++){
            int numeroRandom=random.nextInt(tablaK.getRows().size()-1);
            if(!centroides.contains(tablaK.getRowAt(numeroRandom))){
                centroides.add(tablaK.getRowAt(numeroRandom));
                grupos.put(i,new ArrayList<>());
            }else{
                i--;
            }
        }
    }
    private Row meanCentroide(List<Row> rows) {
        Row devolver=new Row();
        List<Double> aux=new ArrayList<>();
        for(int i=0;i<tablaK.getHeaders().size();i++){
            aux.add(0.0);
        }
        for(Row element:rows){
            List<Double> aux2=element.getData();
            for(int i=0;i<aux2.size();i++){
                aux.set(i,aux2.get(i)+aux.get(i)/ rows.size()-1);
            }
        }

        devolver.addListaFila(aux);
        return devolver;
    }

    @Override
    public void setDistance(Distance distance) {
        this.distance=distance;
    }
}
