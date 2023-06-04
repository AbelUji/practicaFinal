package org.pf.adc.Interfaces;

import org.pf.adc.Constructores.Table;
import org.pf.adc.Excepciones.ClusterException;

import java.util.List;

public interface Algorithm<T extends Table> {
    void train(T datos) throws ClusterException;
    Integer estimate(List<Double> dato);
}
