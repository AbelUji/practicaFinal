package org.pf.adc.Patrones;

import org.pf.adc.Interfaces.Distance;

import java.util.List;

public class ManhattanDistance implements Distance {
    @Override
    public double calculateDistance(List<Double> p, List<Double> q) {
        double amount=-1;
        for(int i=0;i<p.size();i++){
            amount+=Math.abs((p.get(i)-q.get(i)));
        }
        return amount;
    }
}
