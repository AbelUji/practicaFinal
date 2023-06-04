package org.pf.adc.Plantilla;

import org.pf.adc.Constructores.Table;
import java.io.FileNotFoundException;

abstract class ReaderTemplate{
    private String fileG;

    public ReaderTemplate(String file){
        this.fileG=file;
    }
    public final Table readTableFromSource() throws FileNotFoundException {
        openSource(fileG);
        int contador=0;
        while(hasMoreData()){
            if(contador==0){
                processHeaders(getNextData());
                contador++;
            }
            else{
                processData(getNextData());
            }
        }
        closeSource();
        return getTable();
    }
    abstract void openSource(String source) throws FileNotFoundException;
    abstract void processHeaders(String headers);
    abstract void processData(String data);
    abstract void closeSource();
    abstract boolean hasMoreData();
    abstract String getNextData();
    public abstract Table getTable();

}


