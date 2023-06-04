package org.pf.adc.Graficos.Vista;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.pf.adc.Excepciones.ClusterException;
import org.pf.adc.Graficos.Controlador.Controlador;
import org.pf.adc.Graficos.Modelo.Modelo;
import org.pf.adc.Interfaces.Distance;

import java.io.IOException;

public class ImplementacionVista2 implements Vista{
    private StackPane root;
    private Stage stage;
    private VBox estruc_global, vLista, bot_aceptar;
    private HBox vNumRec;
    private Controlador controlador;
    private Modelo modelo;
    private Spinner<Integer> spinner;
    private String cancionElegida, algoritmoElegido;
    private Distance distanciaElegida;
    private ListView<String> lista;
    private Button boton;

    public ImplementacionVista2(Stage stage, String recomendada, String algor, Distance distancia){
        this.stage=stage;
        root = new StackPane();
        this.cancionElegida=recomendada;
        this.algoritmoElegido=algor;
        this.distanciaElegida=distancia;
    }
    @Override
    public void prepararStage() {
        stage.setTitle("Recommended songs based on "+cancionElegida);
        stage.setScene(new Scene(root, 430, 440));
    }

    public void crearNumRec() throws ClusterException, IOException {
        controlador.setCancionesRecomendadas(algoritmoElegido,distanciaElegida);
        Label label_rec_type = new Label("Number of recommendations (Maximo: "+modelo.getRecsys().getGrupo(cancionElegida)+"):");
        label_rec_type.setPadding(new Insets(2,6,0,4));

        spinner = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, modelo.getRecsys().getGrupo(cancionElegida),14);
        spinner.setValueFactory(valueFactory);

        lista=new ListView<>(FXCollections.observableArrayList(modelo.getRecsys().recommend(cancionElegida, spinner.getValue())));
        lista.setPadding(new Insets(2,0,0,4));

        spinner.valueProperty().addListener(((observableValue, integer, t1) -> {lista.setItems(FXCollections.observableArrayList(modelo.getRecsys().recommend(cancionElegida, spinner.getValue())));}));
        vNumRec= new HBox(label_rec_type, spinner);
        vNumRec.setPadding(new Insets(6,0,0,0));
    }

    public void recomendadas(){
        HBox hlabel_rec = new HBox(new Label("If you liked '"+cancionElegida+"' you might like"));
        hlabel_rec.setPadding(new Insets(15,0,0,4));

        vLista=new VBox(hlabel_rec,lista);
    }

    public void buttonClose(){
        boton = new Button("Close recommendation");

        boton.setOnAction(actionEvent -> stage.close());

        boton.setStyle("-fx-text-fill: rgb(49, 89, 23);" + "-fx-border-color: rgb(49, 89, 23);" + "-fx-border-radius: 5;\n" + "-fx-padding: 3 6 6 6;");
        bot_aceptar=new VBox(boton);
        bot_aceptar.setPadding(new Insets(15,0,5,0));
        bot_aceptar.setAlignment(Pos.CENTER);
    }

    @Override
    public void montarStage() {
        estruc_global=new VBox(vNumRec,vLista, bot_aceptar);
        root.getChildren().add(estruc_global);
    }

    @Override
    public void crearStage() throws IOException, ClusterException {
        prepararStage();
        crearNumRec();
        recomendadas();
        buttonClose();
        montarStage();
        stage.show();
    }

    @Override
    public void setControlador(Controlador controlador) {
        this.controlador=controlador;
    }

    @Override
    public void setModelo(Modelo modelo) {
        this.modelo=modelo;
    }
}
