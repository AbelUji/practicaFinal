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
    private VBox estruc_global, rec_radio, dist_radio, lista_canciones,bot_aceptar;
    private StackPane root;
    private Stage stage;
    private Button boton;
    private ListView<String> lista;
    private RadioButton rec_song_feature, rec_guess_genre, dist_euclid, dist_manh;
    private Controlador controlador;
    private Modelo modelo;

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
        HBox hlabel_rec_type = new HBox(new Label("Recommendation Type"));
        hlabel_rec_type.setAlignment(Pos.BASELINE_LEFT);
        hlabel_rec_type.setPadding(new Insets(2,0,0,4));
        hlabel_rec_type.setStyle("-fx-font-size: 20px;" + "-fx-font-weight: bold;");

        ToggleGroup grupo = new ToggleGroup();
        rec_song_feature = new RadioButton("Recommended based on song features");
        rec_song_feature.setStyle("-fx-font-size: 13px;");
        rec_guess_genre = new RadioButton("Recommended based on guessed genre");
        rec_guess_genre.setStyle("-fx-font-size: 13px;");

        rec_song_feature.setToggleGroup(grupo);
        rec_guess_genre.setToggleGroup(grupo);

        VBox vRadio= new VBox(rec_song_feature, rec_guess_genre);
        vRadio.setSpacing(5);
        vRadio.setPadding(new Insets(7,0,0,4));
        rec_radio = new VBox(hlabel_rec_type, vRadio);
    }

    public void crearDistType(){
        HBox hlabel_dist_type = new HBox(new Label("Distance Type"));
        hlabel_dist_type.setAlignment(Pos.BASELINE_LEFT);
        hlabel_dist_type.setPadding(new Insets(9,0,0,4));
        hlabel_dist_type.setStyle("-fx-font-size: 20px;"+ "-fx-font-weight: bold;");

        ToggleGroup grupo = new ToggleGroup();
        dist_euclid = new RadioButton("Euclidean");
        dist_manh = new RadioButton("Manhattan");

        dist_euclid.setToggleGroup(grupo);
        dist_manh.setToggleGroup(grupo);

        VBox vRadio= new VBox(dist_euclid, dist_manh);
        vRadio.setSpacing(3);
        vRadio.setPadding(new Insets(7,0,0,4));
        dist_radio = new VBox(hlabel_dist_type, vRadio);
    }

    public void crearListaCanciones() throws IOException {
        HBox hlabel_title = new HBox(new Label("Song Titles"));
        hlabel_title.setAlignment(Pos.BASELINE_LEFT);
        hlabel_title.setPadding(new Insets(9,0,0,4));
        hlabel_title.setStyle("-fx-font-size: 20px;"+ "-fx-font-weight: bold;");


        String sep = System.getProperty("file.separator");
        String ruta = "src/main/resources/files";
        lista = new ListView<>(FXCollections.observableArrayList(modelo.anadirCanciones(ruta+sep+"songs_test_names.csv")));
        lista.getSelectionModel().selectedItemProperty().addListener((item, valorInicial, valorActual) -> {
            boton.setText("Recommended on "+ valorActual);
        });

        Tooltip tip=new Tooltip("Double click for recommendations based on this song");
        Tooltip.install(lista,tip);
        lista_canciones=new VBox(hlabel_title,lista);
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
        bot_aceptar=new VBox(boton);
        bot_aceptar.setPadding(new Insets(15,0,0,0));
        bot_aceptar.setAlignment(Pos.CENTER);
    }
    private String recomendacionElegida(){
        if (rec_song_feature.isSelected()){
            return "knn";
        }else{
            return "kmeans";
        }
    }

    private Distance distanciaElegida(){
        if (dist_euclid.isSelected()){
            return new EuclideanDistance();
        }else{
            return new ManhattanDistance();
        }
    }

    private boolean comprobaciones(){
        return (dist_euclid.isSelected() || dist_manh.isSelected()) && (rec_guess_genre.isSelected() || rec_song_feature.isSelected()) && lista.getSelectionModel().getSelectedIndex() != -1;
    }

    @Override
    public void montarStage(){
        estruc_global = new VBox(rec_radio,dist_radio,lista_canciones,bot_aceptar);
        root.getChildren().add(estruc_global);
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
