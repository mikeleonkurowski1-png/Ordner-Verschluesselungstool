# Ordner-Verschluesselungstool

- Ordner-Verschlüsselungstool für Windows, geschrieben in Java (mit Gradle)
- Das Tool durchläuft den gewünschten Ordner rekursiv und verschlüsselt alle enthaltene Dateien

# Roadmap:

- [X] Projekt aufsetzen und Github Repository erstellen
- [X] Das Grundlegende GUI bauen mit JavaFX
- [X] Verschlüsselungslogik schreiben
- [X] Rekursives durchsuchen der Ordner implementieren
- [X] Logik in das GUI einbinden
- [ ] Später: Option zum sicheren Löschen des Originalordners

# Wichtige genutzte Bibliotheken: 

- JavaFX: Für die grafische Oberfläche und die Ordnerauswahl
- Java Cryptography Extension (JCE): Liefert unter anderem die "Cipher"-Klassen, mit denen  die eigentliche Verschlüsselung stattfindet
- Java Standartbibliotheken (java.io, java.util, ...): Für das Lesen und Schreiben von Dateien und der Verarbeitung des Keys zu Text