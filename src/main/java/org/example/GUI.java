package org.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.util.Optional;

import org.example.*;


// Eigene Datei, die nur dem Erstellen des GUI dient


public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        final File[] ausgewählterOrdner = new File[1];
        final String[] generierterKeySicherer = new String[1];
        final String[] Modus = new  String[1];

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
        Label Fortschrit = new Label("");
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
                        ausgewählterOrdner[0] = selectedDirectory;
                    }
                });

        OrdnerAusgabe.setOnAction(event -> {
            //Check ob überhaupt bereits ein Key vorliegt (Ob überhaupt bereits Ver oder Entschlüsselt wurde
            if (generierterKeySicherer[0] == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Achtung");
                alert.setContentText("Bitte klicke zuerst auf Verschlüsseln um einen Schlüssel zu generieren!");
                alert.showAndWait();
                return;
            }

            javafx.stage.DirectoryChooser directoryChooser = new javafx.stage.DirectoryChooser();
            directoryChooser.setTitle("Wähle einen Speicherort aus.");
            File speicherziel =  directoryChooser.showDialog(primaryStage);

            if ("Verschlüsseln".equals(Modus[0])) {

                if (speicherziel != null) {
                    try {
                        File VerschlüsselterOrdner = new File(speicherziel, ausgewählterOrdner[0].getName() + ".enc");

                        VerschlüsselLogik logik = new VerschlüsselLogik();
                        logik.OrdnerVerschlüsseln(ausgewählterOrdner[0], VerschlüsselterOrdner, generierterKeySicherer[0]);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Herunterladen erfolgreich!");
                        alert.setHeaderText(null);
                        alert.setContentText("Der Verschlüsselte Ordner wurde erfolgreich heruntergeladen unter; " + VerschlüsselterOrdner.getPath());
                        alert.showAndWait();
                        return;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if ("Entschlüsseln".equals(Modus[0])) {

                if (speicherziel != null) {
                    try {
                        String name = ausgewählterOrdner[0].getName();
                        name = name.substring(0, name.length() - 4);
                        File EntschlüsselterOrdner = new File(speicherziel, name + ".dec");

                        VerschlüsselLogik logik = new VerschlüsselLogik();
                        logik.OrdnerEntschlüsseln(ausgewählterOrdner[0], EntschlüsselterOrdner, generierterKeySicherer[0]);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Herunterladen erfolgreich!");
                        alert.setHeaderText(null);
                        alert.setContentText("Der Entschlüsselte Ordner wurde erfolgreich heruntergeladen unter; " + EntschlüsselterOrdner.getPath());
                        alert.showAndWait();
                        return;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

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

        //Action des Verschlüsseln-Buttons implementieren
        Verschluesseln.setOnAction(event -> {
            if (ausgewählterOrdner[0] == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Achtung");
                alert.setContentText("Wähle zuerst einen Ordner aus!");
                alert.showAndWait();
                return;

            } else if (ausgewählterOrdner[0] != null) {
                try {
                    String key = ZufälligerSchlüssel.getKeyFromKeyGenerator();
                    generierterKeySicherer[0] = key;

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Erfolg!");
                    alert.setHeaderText("Ordner wurde erfolgreich verschlüsselt!");

                    javafx.scene.control.TextArea textArea = new javafx.scene.control.TextArea("Hier der Key zum Entschlüsseln: " + key);
                    textArea.setEditable(false);
                    textArea.setWrapText(true);

                    alert.getDialogPane().setContent(textArea);
                    alert.showAndWait();

                    Fortschrit.setText("Fertig!");
                    Modus[0] = "Verschlüsseln";

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        Entschluesseln.setOnAction(event -> {
            if (ausgewählterOrdner[0] == null || !(ausgewählterOrdner[0].getName().endsWith(".enc"))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Achtung");
                alert.setContentText("Wähle zuerst einen verschlüsselten Ordner aus! (.enc Endung)");
                alert.showAndWait();
                return;
            }

            TextInputDialog Eingabe = new TextInputDialog();
            Eingabe.setTitle("Schlüsssel eingeben!");
            Eingabe.setContentText("Bitte geben sie den Schlüssel ein: ");

            Optional<String> eingabe = Eingabe.showAndWait();

            if (eingabe.isPresent()) {
                generierterKeySicherer[0] = eingabe.get();
                Modus[0] = "Entschlüsseln";

            }
        });



        Scene scene = new Scene(root, 600, 300);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}