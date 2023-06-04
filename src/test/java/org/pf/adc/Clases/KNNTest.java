package org.pf.adc.Clases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pf.adc.Constructores.TableWithLabels;
import org.pf.adc.Patrones.EuclideanDistance;


import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KNNTest {
    CSV miCSV;
    KNN miKNN;
    TableWithLabels table;
    private List<Double> listaDistancia1, listaDistancia2, lista0,lista1,lista2,lista3,lista4;

    @BeforeEach
    void antesDe() throws FileNotFoundException {
        miCSV=new CSV();
        miKNN=new KNN(new EuclideanDistance());

        listaDistancia1=Arrays.asList(5.4,3.9,1.3,0.4);
        listaDistancia2=Arrays.asList(3.7,3.0,5.5,1.9);
        lista0=Arrays.asList(5.0,0.0,7.0,1.0);
        lista1=Arrays.asList(5.0,5.0,5.0,5.0);
        lista2=Arrays.asList(3.6,3.9,1.9,6.4);
        lista3=Arrays.asList(-1.0,-1.0,-1.0,-1.0);
        lista4=Arrays.asList(0.0,0.0,0.0,0.0);

        table=miCSV.readTableWithLabels("src\\main\\resources\\files\\iris.csv");
        miKNN.train(table);
    }
    @Test
    void estimate() {
        assertEquals(3,miKNN.estimate(lista0));
        assertEquals(3,miKNN.estimate(lista1));
        assertEquals(3,miKNN.estimate(lista2));
        assertEquals(1,miKNN.estimate(lista3));
        assertEquals(1,miKNN.estimate(lista4));
    }

    @Test
    void distance() {
        assertEquals(6.871,miKNN.calcularDistancia(listaDistancia1,lista0),0.001);
        assertEquals(3.570,miKNN.calcularDistancia(listaDistancia2,lista0),0.001);
        assertEquals(5.934,miKNN.calcularDistancia(listaDistancia1,lista1),0.001);
        assertEquals(3.814,miKNN.calcularDistancia(listaDistancia2,lista1),0.001);
    }
}