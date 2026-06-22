package org.example;


// Datei zum Starten des Programms


import java.io.File;

public class Main {
    public static void main(String[] args) {
        GUI.main(args);


        /* Hier ein kleiner Test ob die Ver- und Entschlüsselungslogik für eine einzelne Datei funktionieren.

        File file = new File("test.txt");
        File filedec = new File("test.txt.enc");

        try{
            VerschlüsselLogik logik = new VerschlüsselLogik();

            File original = new File("test.txt");
            File verschlüssselt = new File ("test.txt.enc");

            logik.FileVerschlüsseln(original, "");

            logik.FileEntschlüsseln(verschlüssselt, "");

            System.out.println(" Test erfolgreich");
        } catch ( Exception e){
            e.printStackTrace();
        }*/
    }
}
