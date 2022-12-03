import de.ur.mi.oop.colors.Color;
import de.ur.mi.oop.graphics.Circle;

/*
    Diese Klasse dient als gemeinsamer Grundbaustein für die Wolf- und Sheep-Klassen.
    Sie gibt alle Eigenschaften und Methoden vor, die sich diese beiden teilen.
    Da es metaphorisch betrachtet kein "Tier" gibt, dass nicht spezifiziert werden kann -
    also in dieser Welt Wolf oder Schaf ist - wird die Klasse als "abstract" deklariert.
    Damit wird verhindert, dass z.B. mit "new Animal(...)" eine Instanz von Animal erstellt werden kann.
    Instanzen können nur von Sub-Klassen, also Wolf oder Schaf, erstellt werden.
 */
public abstract class Animal {

    /*
        Eigenschaften, die sich beide Klassen teilen.
     */

    // Der Körper wird durch eine Kreis-Form dargestellt.
    protected Circle body;
    // Die Bewegung bei jedem Update wird durch einen 2d-Vektor dargestellt.
    protected Vector movementVector;
    // Attribut zur Darstellung, ob ein Animal bereits gestorben ist.
    protected boolean isAlive;

    /*
        Allgemeiner Konstruktor für alle Sub-Klassen von Animal.
        In den Konstruktoren der Sub-Klassen wird dieser Konstruktor mit dem Aufruf super(...) referenziert.
     */
    public Animal(float x, float y, float size, Color color, Vector movementVector) {
        this.body = new Circle(x, y, size, color);
        this.movementVector = movementVector;
        isAlive = true;
    }

    /*
        Um ein Animal auf dem Canvas darzustellen, wird dessen Körper, dargestellt durch einen Kreis, gezeichnet.
     */
    public void draw() {
        body.draw();
    }

    /*
        In der update-Methode wird das Animal um seinen Bewegungsvektor verschoben.
        Nach jeder Bewegung wird eine Kollision mit dem Canvas-Rand geprüft.
     */
    public void update() {
        body.move(movementVector.x, movementVector.y);
        handleCanvasBorderCollision();
    }

    /*
        In dieser Methode wird geprüft, ob das Body des Animal sich aus dem Canvas heraus bewegt hat.
        Dafür muss eine mögliche Kollision mit jeder Kante ermittelt werden.
        Wenn das Animal mit einer Kante zusammenstößt, bewegt es sich stattdessen in eine neue Richtung weiter.
        Zusätzlich wird die Position auf den Punkt der Kante +- den Radius des Kreises gesetzt, damit der Kreis nicht
        aus dem Bild läuft.
     */
    private void handleCanvasBorderCollision() {
        if (body.getXPos() - body.getRadius() < 0) {
            body.setXPos(body.getRadius());
            movementVector.mirrorX();
        } else if (body.getXPos() + body.getRadius() > WolvesAndSheep.CANVAS_WIDTH ){
            body.setXPos(WolvesAndSheep.CANVAS_WIDTH - body.getRadius());
            movementVector.mirrorX();
        }
        if (body.getYPos() - body.getRadius() < 0) {
            body.setYPos(body.getRadius());
            movementVector.mirrorY();
        } else if (body.getYPos() + body.getRadius() > WolvesAndSheep.CANVAS_HEIGHT) {
            body.setYPos(WolvesAndSheep.CANVAS_HEIGHT - body.getRadius());
            movementVector.mirrorY();
        }
    }

    /*
        In dieser Methode wird ein möglicher Zusammenprall mit einem anderen Animal geprüft.
        Dieser Zusammenprall ist nur dann relevant, wenn beide Animals leben, also isAlive = true ist.
        Zur Überprüfung wird die hitTest-Methode der Circle-Instanz genutzt.
     */
    public void checkCollision(Animal animal) {
        if (!animal.isAlive || !this.isAlive) return;
        if (body.hitTest(animal.getX(), animal.getY())) {
            handleConfrontation(animal);
        }
    }

    /*
        In dieser Methode soll auf einen Zusammenprall mit einem anderen Animal reagiert werden.
        Da sich das Verhalten je nachdem ob es sich um Schaf oder Wolf handelt, unterscheidet,
        wird hier kein Rumpf vorgegeben.
        Durch das "abstract"-Schlüsselwort kann stattdessen erzwungen werden, dass jede Klasse die von Animal erbt,
        die handleConfrontation-Methode überschreiben muss.
     */
    public abstract void handleConfrontation(Animal animal);

    public void die() {
        isAlive = false;
    }

    public float getX() {
        return body.getXPos();
    }

    public float getY() {
        return body.getYPos();
    }

    public float getSize() {
        return body.getRadius();
    }

}
