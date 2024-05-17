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
import org.pf.adc.Patrones.EuclideanDistance;
import org.pf.adc.Patrones.ManhattanDistance;
import java.io.IOException;

public class ImplementacionVista implements Vista{
    private VBox recRadio, distRadio, listaCanciones, botAceptar;
    private StackPane root;
    private Stage stage;
    private Button boton;
    private ListView<String> lista;
    private RadioButton recSongFeature, recGuessGenre, distEuclid, distManh;
    private Controlador controlador;
    private Modelo modelo;

    private static final String ACTION_1 = "-fx-font-size: 20px;";
    private static final String ACTION_2 = "-fx-font-weight: bold;";
    public ImplementacionVista(Stage stage){
        this.stage=stage;
        root = new StackPane();
    }

    @Override
    public void prepararStage(){
        stage.setTitle("Song Recommender");
        stage.setScene(new Scene(root, 400, 660));
    }
    public void crearRecType(){
        HBox hlabelRecType = new HBox(new Label("Recommendation Type"));
        hlabelRecType.setAlignment(Pos.BASELINE_LEFT);
        hlabelRecType.setPadding(new Insets(2,0,0,4));
        hlabelRecType.setStyle(ACTION_1 + ACTION_2);

        ToggleGroup grupo = new ToggleGroup();
        recSongFeature = new RadioButton("Recommended based on song features");
        recSongFeature.setStyle("-fx-font-size: 13px;");
        recGuessGenre = new RadioButton("Recommended based on guessed genre");
        recGuessGenre.setStyle("-fx-font-size: 13px;");

        recSongFeature.setToggleGroup(grupo);
        recGuessGenre.setToggleGroup(grupo);

        VBox vRadio= new VBox(recSongFeature, recGuessGenre);
        vRadio.setSpacing(5);
        vRadio.setPadding(new Insets(7,0,0,4));
        recRadio = new VBox(hlabelRecType, vRadio);
    }

    public void crearDistType(){
        HBox hlabelDistType = new HBox(new Label("Distance Type"));
        hlabelDistType.setAlignment(Pos.BASELINE_LEFT);
        hlabelDistType.setPadding(new Insets(9,0,0,4));
        hlabelDistType.setStyle(ACTION_1+ ACTION_2);

        ToggleGroup grupo = new ToggleGroup();
        distEuclid = new RadioButton("Euclidean");
        distManh = new RadioButton("Manhattan");

        distEuclid.setToggleGroup(grupo);
        distManh.setToggleGroup(grupo);

        VBox vRadio= new VBox(distEuclid, distManh);
        vRadio.setSpacing(3);
        vRadio.setPadding(new Insets(7,0,0,4));
        distRadio = new VBox(hlabelDistType, vRadio);
    }

    public void crearListaCanciones() throws IOException {
        HBox hlabelTitle = new HBox(new Label("Song Titles"));
        hlabelTitle.setAlignment(Pos.BASELINE_LEFT);
        hlabelTitle.setPadding(new Insets(9,0,0,4));
        hlabelTitle.setStyle(ACTION_1+ ACTION_2);


        String sep = System.getProperty("file.separator");
        String ruta = "src/main/resources/files";
        lista = new ListView<>(FXCollections.observableArrayList(modelo.anadirCanciones(ruta+sep+"songs_test_names.csv")));
        lista.getSelectionModel().selectedItemProperty().addListener((item, valorInicial, valorActual) -> boton.setText("Recommended on "+ valorActual));

        Tooltip tip=new Tooltip("Double click for recommendations based on this song");
        Tooltip.install(lista,tip);
        listaCanciones =new VBox(hlabelTitle,lista);
    }

    public void dobleClickLista(){
        lista.setOnMouseClicked(mouseEvent -> {
            if(comprobaciones() && mouseEvent.getClickCount()==2){
                try {
                    controlador.abrirSegundaVentana(lista.getSelectionModel().getSelectedItem(),recomendacionElegida(),distanciaElegida());
                } catch (IOException | ClusterException e) {
                    throw new RuntimeException(e);
                }
            }else if(mouseEvent.getClickCount() == 2){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setHeaderText("Missing Information");
                alert.setContentText("Some info is not selected. Please try again");

                alert.showAndWait();
            }
        });
    }

    public void crearBotonAceptar(){
        boton = new Button("Recommended");

        boton.setOnAction(actionEvent -> {
            if(comprobaciones()){
                try {
                    controlador.abrirSegundaVentana(lista.getSelectionModel().getSelectedItem(),recomendacionElegida(),distanciaElegida());
                } catch (IOException | ClusterException e) {
                    throw new RuntimeException(e);
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setHeaderText("Missing Information");
                alert.setContentText("Some info is not selected. Please try again");

                alert.showAndWait();
            }
        });
        boton.setStyle("-fx-text-fill: rgb(49, 89, 23);" + "-fx-border-color: rgb(49, 89, 23);" + "-fx-border-radius: 5;\n" + "-fx-padding: 3 6 6 6;");
        botAceptar =new VBox(boton);
        botAceptar.setPadding(new Insets(15,0,0,0));
        botAceptar.setAlignment(Pos.CENTER);
    }
    private String recomendacionElegida(){
        if (recSongFeature.isSelected()){
            return "knn";
        }else{
            return "kmeans";
        }
    }

    private Distance distanciaElegida(){
        if (distEuclid.isSelected()){
            return new EuclideanDistance();
        }else{
            return new ManhattanDistance();
        }
    }

    private boolean comprobaciones(){
        return (distEuclid.isSelected() || distManh.isSelected()) && (recGuessGenre.isSelected() || recSongFeature.isSelected()) && lista.getSelectionModel().getSelectedIndex() != -1;
    }

    @Override
    public void montarStage(){
        VBox estrucGlobal = new VBox(recRadio, distRadio, listaCanciones, botAceptar);
        root.getChildren().add(estrucGlobal);
    }

    @Override
    public void crearStage() throws IOException {
        prepararStage();
        crearRecType();
        crearDistType();
        crearListaCanciones();
        dobleClickLista();
        crearBotonAceptar();
        montarStage();
        stage.show();
    }

    @Override
    public void setModelo(Modelo modelo){
        this.modelo=modelo;
    }

    @Override
    public void setControlador(Controlador controlador){
        this.controlador=controlador;
    }
}
