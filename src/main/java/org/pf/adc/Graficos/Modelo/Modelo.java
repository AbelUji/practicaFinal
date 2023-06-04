package org.pf.adc.Graficos.Modelo;

import org.pf.adc.Clases.RecSys;
import org.pf.adc.Excepciones.ClusterException;
import org.pf.adc.Graficos.Vista.Vista;
import org.pf.adc.Interfaces.Distance;

import java.io.IOException;
import java.util.List;

public interface Modelo {
    void setVista(Vista vista);
    void recSys(String method, Distance distancia) throws IOException, ClusterException;
    RecSys getRecsys();
    List<String> anadirCanciones(String fichero) throws IOException;
    void gestionarStage() throws ClusterException, IOException;
}
