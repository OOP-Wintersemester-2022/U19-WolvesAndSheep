import de.ur.mi.oop.colors.Color;
import de.ur.mi.oop.graphics.Circle;

/*
    Diese Klasse dient als gemeinsamer Grundbaustein für die Wolf- und Sheep-Klassen.
    Sie gibt alle Eigenschaften und Methoden vor, die sich diese beiden teilen.
 */
public abstract class Animal {

    /*
        Eigenschaften, die sich beide Klassen teilen.
     */

    // Der Körper wird durch eine Kreis-Form dargestellt.
    protected Circle body;

    protected float movementX;
    protected float movementY;

    // Attribut zur Darstellung, ob ein Animal bereits gestorben ist.
    protected boolean isAlive;

    /*
        Allgemeiner Konstruktor für alle Sub-Klassen von Animal.
        In den Konstruktoren der Sub-Klassen wird dieser Konstruktor mit dem Aufruf super(...) referenziert.
     */
    public Animal(float x, float y, float size, Color color, float movementX, float movementY) {
        this.body = new Circle(x, y, size, color);
        this.movementX = movementX;
        this.movementY = movementY;
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
        body.move(movementX, movementY);
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
            movementX *= -1;
        } else if (body.getXPos() + body.getRadius() > WolvesAndSheep.CANVAS_WIDTH ){
            body.setXPos(WolvesAndSheep.CANVAS_WIDTH - body.getRadius());
            movementX *= -1;
        }
        if (body.getYPos() - body.getRadius() < 0) {
            body.setYPos(body.getRadius());
            movementY *= -1;
        } else if (body.getYPos() + body.getRadius() > WolvesAndSheep.CANVAS_HEIGHT) {
            body.setYPos(WolvesAndSheep.CANVAS_HEIGHT - body.getRadius());
            movementY *= -1;
        }
    }

    /*
        In dieser Methode wird ein möglicher Zusammenprall mit einem anderen Animal geprüft.
        Dieser Zusammenprall ist nur dann relevant, wenn beide Animals leben, also isAlive = true ist.
        Zur Überprüfung wird die hitTest-Methode der Circle-Instanz genutzt.
     */
    public void checkCollision(Animal animal) {
        if (!animal.isAlive || !this.isAlive)
            return;

        if (body.hitTest(animal.getX(), animal.getY())) {
            handleConfrontation(animal);
        }
    }

    /*
        In dieser Methode soll auf einen Zusammenprall mit einem anderen Animal reagiert werden.
        Da sich das Verhalten je nachdem ob es sich um Schaf oder Wolf handelt, unterscheidet,
        wird hier ein leerer Rumpf vorgegeben.
        Die handleConfrontation-Methode soll in der geerbten Klasse überschrieben werden, um für diese das gewünschte Verhalten zu implementieren.
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
