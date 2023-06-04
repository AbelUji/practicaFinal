package org.pf.adc.Graficos.Vista;

import org.pf.adc.Excepciones.ClusterException;
import org.pf.adc.Graficos.Controlador.Controlador;
import org.pf.adc.Graficos.Modelo.Modelo;

import java.io.IOException;

public interface Vista {
    void prepararStage();
    void montarStage();
    void crearStage() throws IOException, ClusterException;
    void setControlador(Controlador controlador);
    void setModelo(Modelo modelo);
}
