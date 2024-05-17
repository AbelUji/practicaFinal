package org.pf.adc.Graficos.Controlador;

import javafx.stage.Stage;
import org.pf.adc.Excepciones.ClusterException;
import org.pf.adc.Graficos.Modelo.Modelo;
import org.pf.adc.Graficos.Vista.ImplementacionVista2;
import org.pf.adc.Graficos.Vista.Vista;
import org.pf.adc.Interfaces.Distance;
import java.io.IOException;

public class ImplementacionControlador implements Controlador{
    private Vista vista;
    private Modelo modelo;

    public ImplementacionControlador(){

    }
    @Override
    public void setVista(Vista vista) {
        this.vista=vista;
    }

    @Override
    public void setModelo(Modelo modelo) {
        this.modelo=modelo;
    }

    @Override
    public void setCancionesRecomendadas(String metodo, Distance distancia) throws ClusterException, IOException {
        modelo.recSys(metodo,distancia);
    }

    @Override
    public void abrirSegundaVentana(String cancion,String algoritmo, Distance distancia) throws IOException, ClusterException {
        vista=new ImplementacionVista2(new Stage(),cancion,algoritmo,distancia);
        vista.setModelo(modelo);
        vista.setControlador(this);
        modelo.setVista(vista);
        this.setModelo(modelo);
        modelo.gestionarStage();
    }
}
