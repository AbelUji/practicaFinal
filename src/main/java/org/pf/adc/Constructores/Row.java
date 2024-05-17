package org.pf.adc.Constructores;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private List<Double> data;
    public Row(){
        super();
        data=new ArrayList<>();
    }

    public List<Double> getData() {
        return data;
    }

    public void addFila(Double fila){
        data.add(fila);
    }

    public void addListaFila(List<Double> data){
        this.data=data;
    }



}
