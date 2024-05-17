package org.pf.adc.Excepciones;

public class ClusterException extends Exception{
    static final long serialVersionUID = 8129437039424566964L;
    public ClusterException(){
        super("Numero de K diferente a N");
    }

}
