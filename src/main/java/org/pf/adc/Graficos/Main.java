package org.pf.adc.Graficos;

import javafx.application.Application;
import javafx.stage.Stage;
import org.pf.adc.Graficos.Controlador.ImplementacionControlador;
import org.pf.adc.Graficos.Modelo.ImplementacionModelo;
import org.pf.adc.Graficos.Vista.ImplementacionVista;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        ImplementacionVista vista=new ImplementacionVista(stage);
        ImplementacionControlador controlador= new ImplementacionControlador();
        ImplementacionModelo modelo= new ImplementacionModelo();
        vista.setControlador(controlador);
        vista.setModelo(modelo);
        modelo.setVista(vista);
        controlador.setModelo(modelo);
        controlador.setVista(vista);
        vista.crearStage();
    }
}
