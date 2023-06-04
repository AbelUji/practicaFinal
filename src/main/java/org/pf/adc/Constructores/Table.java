package org.pf.adc.Constructores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {
    private List<String> headers;
    private List<Row> rows;
    public Table(){
        super();
        headers=new ArrayList<>();
        rows=new ArrayList<>();
    }

    public Row getRowAt(int n){
        return rows.get(n);
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void addEtiquetas(String[] cabeceras){
        headers.addAll(Arrays.asList(cabeceras));
    }
    public void addFila(Row newLine){
        rows.add(newLine);
    }

}
