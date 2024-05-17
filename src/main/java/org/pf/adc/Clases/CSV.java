package org.pf.adc.Clases;

import org.pf.adc.Constructores.Row;
import org.pf.adc.Constructores.RowWithLabel;
import org.pf.adc.Constructores.Table;
import org.pf.adc.Constructores.TableWithLabels;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSV {
    public CSV(){

    }
    public Table readTable(String filename) throws FileNotFoundException {
        Table tabla=new Table();
        try(Scanner scanner=new Scanner(new File(filename))) {
            int contador = 0;
            while(scanner.hasNext())

            {
                String[] datos = scanner.nextLine().split(",");
                if (contador == 0) {
                    tabla.addEtiquetas(datos);
                    contador++;
                } else {
                    Row row = new Row();
                    for (String element : datos) {
                        row.addFila(Double.valueOf(element));
                    }
                    tabla.addFila(row);
                }
            }
        }
        return tabla;
    }

    public TableWithLabels readTableWithLabels(String filename) throws FileNotFoundException{
        TableWithLabels tabla= new TableWithLabels();
        try(Scanner scanner = new Scanner(new File(filename))) {
            int contador = 0;
            while (scanner.hasNext()) {
                String[] datos = scanner.nextLine().split(",");
                if (contador == 0) {
                    tabla.addEtiquetas(datos);
                    contador++;
                } else {
                    RowWithLabel row = new RowWithLabel();
                    for (int i = 0; i < datos.length - 1; i++) {
                        row.addFila(Double.parseDouble(datos[i]));
                    }
                    tabla.addEtiquetas(datos[datos.length - 1], row);
                    tabla.addFila(row);
                }
            }
        }
        return tabla;
    }
}