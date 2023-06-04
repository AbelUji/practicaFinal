package org.pf.adc.Plantilla;

import org.pf.adc.Constructores.RowWithLabel;
import org.pf.adc.Constructores.TableWithLabels;

public class CSVLabeledFileReader extends CSVUnlabeledFileReader{
    private TableWithLabels tabla;
    public CSVLabeledFileReader(String file) {
        super(file);
        tabla=new TableWithLabels();
    }

    @Override
    void processData(String data) {
        RowWithLabel row = new RowWithLabel();
        String[] datos = data.split(",");
        for(int i=0;i<datos.length-1;i++){
            row.addFila(Double.parseDouble(datos[i]));
        }
        tabla.addEtiquetas(datos[datos.length - 1],row);
        tabla.addFila(row);
    }

    @Override
    public TableWithLabels getTable() {
        return tabla;
    }
}
