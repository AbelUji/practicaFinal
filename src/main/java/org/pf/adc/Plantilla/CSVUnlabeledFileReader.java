package org.pf.adc.Plantilla;

import org.pf.adc.Constructores.Row;
import org.pf.adc.Constructores.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVUnlabeledFileReader extends ReaderTemplate{
    private Scanner sc;
    private Table tabla;
    public CSVUnlabeledFileReader(String file) {
        super(file);
        tabla=new Table();
    }

    @Override
    void openSource(String source) throws FileNotFoundException {
        sc=new Scanner(new File(source));
    }

    @Override
    void processHeaders(String headers) {
        getTable().addEtiquetas(headers.split(","));
    }

    @Override
    void processData(String data) {
        Row row=new Row();
        String[] datos = data.split(",");
        for(String element:datos){
            row.addFila(Double.valueOf(element));
        }
        tabla.addFila(row);
    }

    @Override
    void closeSource() {
        sc.close();
    }

    @Override
    boolean hasMoreData() {
        return sc.hasNext();
    }

    @Override
    String getNextData() {
        return sc.nextLine();
    }

    @Override
    public Table getTable() {
        return tabla;
    }


}
