package org.pf.adc.Clases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pf.adc.Constructores.Table;
import org.pf.adc.Excepciones.ClusterException;
import org.pf.adc.Patrones.EuclideanDistance;


import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KmeansTest {
    private Kmeans miKmeans, miKmeans2;
    private Table table ;
    private List<Double> list0, list1, list2,list3,list4,list5;

    @BeforeEach
    void setUp() throws FileNotFoundException, ClusterException {
        CSV myCSV = new CSV();
        table = myCSV.readTable("src\\main\\resources\\files\\iris_without_labels.csv");
        miKmeans=new Kmeans(3,3,23456, new EuclideanDistance());
        miKmeans.train(table);

        list0= Arrays.asList(4.1, 2.0, 1.5, 1.2);
        list1=Arrays.asList(2.8, 5.7, 3.9, 1.2);
        list2=Arrays.asList(1.9,1.0,1.1,1.8);
        list3=Arrays.asList(5.5,2.5,4.0,1.3);
        list4=Arrays.asList(5.8,2.6,4.0,1.2);
        list5=Arrays.asList(0.0,0.0,0.0,0.0);

    }

    @Test
    void train() {
        assertEquals(3, miKmeans.getGrupos().size());
        assertEquals(3, miKmeans.getCentroides().size());
        assertEquals(150, miKmeans.getGrupos().get(0).size()+miKmeans.getGrupos().get(1).size()+miKmeans.getGrupos().get(2).size());

    }

    @Test
    void estimate() {
        assertEquals(2,miKmeans.estimate(list0));
        assertEquals(0,miKmeans.estimate(list1));
        assertEquals(2,miKmeans.estimate(list2));
        assertEquals(2,miKmeans.estimate(list3));
        assertEquals(0,miKmeans.estimate(list4));
        assertEquals(1,miKmeans.estimate(list5));
    }
    @Test
    void exceptionTest(){
        miKmeans2=new Kmeans(4,3,23456, new EuclideanDistance());
        assertThrows(ClusterException.class,() -> miKmeans2.train(table));
    }
}