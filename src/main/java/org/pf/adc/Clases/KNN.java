package org.pf.adc.Clases;

import org.pf.adc.Constructores.RowWithLabel;
import org.pf.adc.Constructores.TableWithLabels;
import org.pf.adc.Interfaces.Algorithm;
import org.pf.adc.Interfaces.Distance;
import org.pf.adc.Interfaces.DistanceClient;

import java.util.List;

public class KNN implements Algorithm<TableWithLabels>, DistanceClient {
    private TableWithLabels table;
    private Distance distance;

    public KNN(Distance distancia){
        distance = distancia;
    }
    public void train(TableWithLabels data){
        this.table=data;
    }
    public Integer estimate(List<Double> data){
        double euclidea;
        double distMin=-1;
        int numberClass=-1;
        for(int i=0;i<table.getRows().size();i++){
            RowWithLabel row=(RowWithLabel) table.getRows().get(i);
             euclidea=calcularDistancia(row.getData(),data);
             if(euclidea<distMin || distMin==-1){
                 distMin=euclidea;
                 numberClass=table.getRowAt(i).getNumberClass();
             }
        }
        return numberClass;
    }
    public double calcularDistancia(List<Double> data_source, List<Double> data){
        return distance.calculateDistance(data_source,data);
    }

    @Override
    public void setDistance(Distance distance) {
        this.distance=distance;
    }
}
