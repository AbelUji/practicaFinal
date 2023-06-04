package org.pf.adc.Clases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.pf.adc.Constructores.Table;
import org.pf.adc.Constructores.TableWithLabels;
import org.pf.adc.Plantilla.CSVLabeledFileReader;
import org.pf.adc.Plantilla.CSVUnlabeledFileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CSVTemplateTest {
    private CSVUnlabeledFileReader fichero1;
    private CSVLabeledFileReader fichero2;
    private Table tabla1;
    private TableWithLabels tabla2;

    @org.junit.jupiter.api.Test
    @BeforeEach
    void antesDe() throws IOException {
        //Tabla sin etiquetas
        fichero1=new CSVUnlabeledFileReader("src\\main\\resources\\files\\miles_dollars.csv");
        tabla1=fichero1.readTableFromSource();

        //Tabla con etiquetas
        fichero2=new CSVLabeledFileReader("src\\main\\resources\\files\\iris.csv");
        fichero2.readTableFromSource();
        tabla2=fichero2.getTable();

    }
    @org.junit.jupiter.api.Test
    @DisplayName("Test numero de filas")
    void numeroFilas(){

        //Segunda prueba
        assertEquals(25,tabla1.getRows().size());

        //Tercera prueba

        assertEquals(150,tabla2.getRows().size());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test numero de columnas")
    void numeroColumnas() {


        //Segunda prueba
        assertEquals(2,tabla1.getHeaders().size());

        //Tercera prueba
        assertEquals(5,tabla2.getHeaders().size());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test nombre de etiquetas")
    void nombreEtiquetas() {

        List<String> etiquetas= Arrays.asList("Miles","Dollars");


        //Segunda prueba
        assertEquals(etiquetas,tabla1.getHeaders());

        //Tercera prueba

        List<String> etiquetas2=Arrays.asList("sepal length","sepal width","petal length","petal width","class");

        assertEquals(etiquetas2,tabla2.getHeaders());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test numero asignado")
    void numeroAsignado() {

        //Primera prueba
        assertEquals(3,tabla2.getRowAt(130).getNumberClass());

        //Segunda prueba
        assertEquals(1,tabla2.getRowAt(0).getNumberClass());

        //Tercera prueba
        assertEquals(1,tabla2.getRowAt(33).getNumberClass());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test recuperar datos")
    void recuperarDatos() {
        List<Double> ej1= Arrays.asList(4.8,3.4,1.6,0.2); //12
        List<Double> ej2= Arrays.asList(6.2,2.2,4.5,1.5); //69
        List<Double> ej3= Arrays.asList(5.8,2.7,5.1,1.9); //102

        //Primera prueba
        assertEquals(ej1,tabla2.getRowAt(11).getData());

        //Segunda prueba
        assertEquals(ej2,tabla2.getRowAt(68).getData());

        //Tercera prueba
        assertEquals(ej3,tabla2.getRowAt(101).getData());
    }
}