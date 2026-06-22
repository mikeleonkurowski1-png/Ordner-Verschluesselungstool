package org.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;


// Eigene Datei, die nur dem Erstellen des GUI dient


public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();
        root.setPadding(new javafx.geometry.Insets(20));

        // Überschrift
        Label Ueberschrift = new Label("Ordner-Verschlüsselungs-Tool");
        Ueberschrift.setStyle("-fx-font-size: 20");
        //Ueberschrift.setLayoutX(600);
        //Ueberschrift.setLayoutY(400);  Beide Zeilen werden von der Positionierung innerhalb des BorderPane überschrieben.
        root.setTop(Ueberschrift);
        BorderPane.setAlignment(Ueberschrift, Pos.CENTER);

        //Mittlerer Teil
        HBox Mitte = new HBox(100);
        Mitte.setAlignment(Pos.CENTER);

        Button OrdnerAuswahl = new Button("Ordner-Auswahl");
        Label Fortschrit = new Label("..% (In Arbeit)");
        Button OrdnerAusgabe = new Button("Herunterladen");
        OrdnerAusgabe.setPrefSize(140, 35);
        OrdnerAuswahl.setPrefSize(140, 35);
        Fortschrit.setStyle("-fx-font-size: 20");

        VBox Zentrum = new VBox(10);
        Zentrum.setAlignment(Pos.CENTER);
        Label aktOrdner = new Label("Aktueller Ordner: ... ");

        Zentrum.getChildren().addAll(Fortschrit, aktOrdner);

        Mitte.getChildren().addAll(OrdnerAuswahl, Zentrum, OrdnerAusgabe);
        root.setCenter(Mitte);

        //Auswählen des Ordners implementieren
        OrdnerAuswahl.setOnAction(event -> {
                    javafx.stage.DirectoryChooser directoryChooser = new javafx.stage.DirectoryChooser();
                    directoryChooser.setTitle("Wähle einen Ordner zum Verschlüsseln aus!");
                    File selectedDirectory = directoryChooser.showDialog(primaryStage);

                    //ZUm Anzeigen des Ordnernamens
                    if (selectedDirectory != null) {
                        aktOrdner.setText("Aktueller Ordner: " +  selectedDirectory.getName());
                    }
                });


        //Unterer Teil
        HBox Unten = new HBox(100);
        Unten.setAlignment(Pos.BOTTOM_CENTER);

        Button Verschluesseln = new Button("Verschlüsseln");
        Button Entschluesseln = new Button("Entschlüsseln");
        Verschluesseln.setPrefSize(120, 30);
        Entschluesseln.setPrefSize(120, 30);
        Unten.getChildren().addAll(Verschluesseln, Entschluesseln);
        root.setBottom(Unten);


        Scene scene = new Scene(root, 600, 300);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}