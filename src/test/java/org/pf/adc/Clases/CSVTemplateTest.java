package org.pf.adc.Clases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pf.adc.Constructores.Table;
import org.pf.adc.Constructores.TableWithLabels;
import org.pf.adc.Plantilla.CSVLabeledFileReader;
import org.pf.adc.Plantilla.CSVUnlabeledFileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CSVTemplateTest {
    private Table tabla1;
    private TableWithLabels tabla2;

    @BeforeEach
    void antesDe() throws IOException {
        //Tabla sin etiquetas
        CSVUnlabeledFileReader fichero1 =new CSVUnlabeledFileReader("src\\main\\resources\\files\\miles_dollars.csv");
        tabla1=fichero1.readTableFromSource();

        //Tabla con etiquetas
        CSVLabeledFileReader fichero2=new CSVLabeledFileReader("src\\main\\resources\\files\\iris.csv");
        fichero2.readTableFromSource();
        tabla2=fichero2.getTable();

    }
    @Test
    @DisplayName("Test numero de filas")
    void numeroFilas(){

        //Segunda prueba
        assertEquals(25,tabla1.getRows().size(), "Error en numero de filas");

        //Tercera prueba

        assertEquals(150,tabla2.getRows().size(), "Error en numero de filas");
    }

    @Test
    @DisplayName("Test numero de columnas")
    void numeroColumnas() {

        //Segunda prueba
        assertEquals(2,tabla1.getHeaders().size(),"Error en numero de columnas");

        //Tercera prueba
        assertEquals(5,tabla2.getHeaders().size(),"Error en numero de columnas");
    }

    @Test
    @DisplayName("Test nombre de etiquetas")
    void nombreEtiquetas() {

        List<String> etiquetas= Arrays.asList("Miles","Dollars");


        //Segunda prueba
        assertEquals(etiquetas,tabla1.getHeaders(),"Error en nombre de etiquetas");

        //Tercera prueba

        List<String> etiquetas2=Arrays.asList("sepal length","sepal width","petal length","petal width","class");

        assertEquals(etiquetas2,tabla2.getHeaders(),"Error en nombre de etiquetas");
    }

    @Test
    @DisplayName("Test numero asignado")
    void numeroAsignado() {

        //Primera prueba
        assertEquals(3,tabla2.getRowAt(130).getNumberClass());

        //Segunda prueba
        assertEquals(1,tabla2.getRowAt(0).getNumberClass());

        //Tercera prueba
        assertEquals(1,tabla2.getRowAt(33).getNumberClass());
    }

    @Test
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