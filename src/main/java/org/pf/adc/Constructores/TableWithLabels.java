package org.pf.adc.Constructores;

import java.util.HashMap;
import java.util.Map;

public class TableWithLabels extends Table {
    private Map<String,Integer> labelsToIndex;
    private int numberClass;

    public TableWithLabels(){
        super();
        this.labelsToIndex=new HashMap<>();
        this.numberClass=0;
    }

    @Override
    public RowWithLabel getRowAt(int n) {
        return (RowWithLabel) super.getRowAt(n);
    }

    public Map<String, Integer> getEtiquetas() {
        return labelsToIndex;
    }

    public void addEtiquetas(String etiqueta, RowWithLabel row){
        if (!this.getEtiquetas().containsKey(etiqueta)) {
            numberClass++;
            row.addNumberClass(numberClass);
            labelsToIndex.put(etiqueta,numberClass);
        } else {
            row.addNumberClass(this.getEtiquetas().get(etiqueta));
        }
    }

}