package org.pf.adc.Constructores;

public class RowWithLabel extends Row {
    private int numberClass;

    public RowWithLabel(){
        super();
        numberClass=-1;
    }
    public int getNumberClass() {
        return numberClass;
    }
    public void addNumberClass(int n){
        numberClass=n;
    }

}