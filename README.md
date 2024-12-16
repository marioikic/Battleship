READ ME:
Battleship (Schiffe Versenken)
Übersicht
Battleship ist ein Konsolen-basiertes Schiffe-Versenken-Spiel, das in Java implementiert wurde. Spieler können gegen den Computer antreten und Schiffe auf einem Spielfeld platzieren, um sie zu schützen, während sie gleichzeitig versuchen, die Schiffe ihres Gegners zu versenken.
Features:
Spieler gegen Computer: Der Spieler tritt gegen einen Computer an, der seine Schiffe zufällig platziert und eigenständig agiert.
- Schiff-Platzierung:
    - Benutzerdefinierte Platzierung durch Eingabe.
    - Zufällige Platzierung der Schiffe durch den Computer.
      Spielablauf:
    - Benutzer und Computer schießen abwechselnd auf das gegnerische Spielfeld.
    - Intelligente Zielmechanik für den Computer.
      Visualisierung:
    - Spielfeld des Spielers vollständig sichtbar.
    - Verdecktes Spielfeld für den Gegner (Schiffe nicht sichtbar).
      Tests: Unit-Tests zur Überprüfung der Spiellogik.
      Voraussetzungen:
- Java: Version 23
- Maven: Für Abhängigkeitsmanagement und Build-Prozesse.
  Projektstruktur:

Battleship_GIT
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── battleship
│   │   │   │   ├── Controller.java
│   │   │   │   ├── LoggerConfig.java
│   │   │   │   ├── MCP.java
│   │   │   │   ├── Spieler.java
│   │   │   │   ├── Spielfeld.java
│   ├── test
│       ├── java
│       │   ├── battleship
│       │       ├── SpielfeldTest.java
├── pom.xml
```
Tests
Unit-Tests wurden mit JUnit 5 implementiert und decken die Kernfunktionen des Spiels ab:

Beispiel-Tests:
- Spielfeld anzeigen: Überprüft, ob das Spielfeld initial korrekt angezeigt wird.
- Schiff platzieren: Testet die Platzierung von Schiffen, einschließlich Überlappung und Grenzen.
- Schießen: Validiert Treffer, Fehlschüsse und wiederholte Schüsse.
- Spielende: Überprüft, ob das Spiel korrekt beendet wird, wenn alle Schiffe versenkt sind.


Nutzung
Spiel starten
1. Starten Sie das Spiel über die Klasse MCP
  
2. Folgen Sie den Anweisungen auf der Konsole, um Ihre Schiffe zu platzieren und zu spielen.

Eingabeformat
- **Schiff platzieren**: `A5H` (Horizontale Platzierung an Zeile A, Spalte 5).
- **Schuss abgeben**: `B3` (Schuss auf Zeile B, Spalte 3).
- **Spiel beenden**: `exit`
Entwicklungsdetails
Pom.xml
Die Projektkonfiguration verwendet Maven mit den folgenden Abhängigkeiten:
- **JUnit**: Für Unit-Tests (Version 5.8.2).

```xml
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>${junit.version}</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>${junit.version}</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```
Verbesserungsvorschläge
- Benutzeroberfläche: Integration einer grafischen Oberfläche (z.B. JavaFX).
- Erweiterte KI: Intelligenteres Verhalten für den Computergegner.
- Mehrspieler-Modus: Möglichkeit, gegen einen anderen Spieler zu spielen.
- Erweiterte Tests: Zusätzliche Testfälle für umfangreichere Validierung.

Autor
Dieses Projekt wurde im Rahmen eines Lernprojekts von Stefan L., Mario I., Lukas R., Teresa J., Arnold R.  erstellt und dient als Beispiel für ein einfaches Java-Entwicklungsprojekt.
