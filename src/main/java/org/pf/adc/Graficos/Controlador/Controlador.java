package org.pf.adc.Graficos.Controlador;

import org.pf.adc.Excepciones.ClusterException;
import org.pf.adc.Graficos.Modelo.Modelo;
import org.pf.adc.Graficos.Vista.Vista;
import org.pf.adc.Interfaces.Distance;

import java.io.IOException;

public interface Controlador {
    void setVista(Vista vista);
    void setModelo(Modelo modelo);
    void abrirSegundaVentana(String prueba,String algoritmo, Distance distancia) throws IOException, ClusterException;
    void setCancionesRecomendadas(String metodo, Distance distancia) throws ClusterException, IOException;

}
