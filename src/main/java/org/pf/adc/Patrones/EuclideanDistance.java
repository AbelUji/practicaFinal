package org.pf.adc.Patrones;

import org.pf.adc.Interfaces.Distance;

import java.util.List;

public class EuclideanDistance implements Distance {
    public EuclideanDistance(){

    }
    @Override
    public double calculateDistance(List<Double> p, List<Double> q) {
        double amount=-1;
        for(int i=0;i<p.size();i++){
            amount+=Math.pow((p.get(i)-q.get(i)),2);
        }
        return Math.sqrt(amount);
    }
}
