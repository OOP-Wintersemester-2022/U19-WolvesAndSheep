import de.ur.mi.oop.colors.Color;
import de.ur.mi.oop.colors.Colors;

/*
    Diese Klasse soll einen Wolf darstellen. Dafür erbt sie von der Animal-Klasse.
    Durch die Vererbung übernimmt sie alle Eigenschaften und Methoden der Animal-Klasse.
 */
public class Wolf extends Animal {

    /* Private Konstanten */
    private static final Color WOLF_COLOR = new Color(100, 70, 36);
    private static final float STARVATION_SIZE = 10;
    private static final float SIZE_DECAY = 3;
    private static final float MIN_SIZE_DECAY_PERCENTAGE = 0.1f;
    private static final int HUNGER_TIMER = 90;

    // In dieser Variable wird gezählt, wie lange ein Wolf nichts gegessen hat
    private int timeSinceLastEatenOrSlept;

    /*
        Im Konstruktor muss der Super-Konstruktor der Animal-Klasse aufgerufen werden.
     */
    public Wolf(float x, float y, float size, float movementX, float movementY) {
        super(x, y, size, WOLF_COLOR, movementX, movementY);
        timeSinceLastEatenOrSlept = 0;
    }

    /*
        In der update-Methode wird über den Aufruf der update-Methode der Animal-Klasse durch "super.update()"
        die Position des Wolfes aktualisiert.
        Zusätzlich wird die timeSinceLastEaten-Variable inkrementiert. Falls ein Wolf zu lange nichts gegessen hat,
        muss er die Nacht hungrig verbringen. Dies wird über eine if-Abfrage geprüft.
     */
    @Override
    public void update() {
        if (isAlive) {
            super.update();
            timeSinceLastEatenOrSlept++;
            if (timeSinceLastEatenOrSlept >= HUNGER_TIMER) {
                sleepHungry();
            }
        }
    }

    /*
        In dieser Methode wird die Logik implementiert, wenn ein Wolf hungrig einschläft.
        Dann magert er ab, also wird sein Körper kleiner. Dabei verliert der Kreis der seinen Körper darstellen soll
        10% seines Radius, mindestens aber drei Einheiten. Dies wird durch den Math.max(...)-Aufruf implementiert.
        Wenn ein Wolf zu sehr abmagert, also der Radius seines Körpers einen unteren Schwellwert erreicht, dann stirbt er.
     */
    private void sleepHungry() {
        float shrinkage = Math.max(SIZE_DECAY, body.getRadius() * MIN_SIZE_DECAY_PERCENTAGE);
        body.setRadius(body.getRadius() - shrinkage);
        timeSinceLastEatenOrSlept = 0;
        if (body.getRadius() <= STARVATION_SIZE) {
            die();
        }
    }

    /*
        In dieser Methode soll ein Wolf ein Sheep fressen.
        Dann wird der Körper des Wolfes größer, d.h. der Radius des Kreises nimmt zu.
        Das Sheep stirbt dabei.
     */
    private void devour(Sheep prey) {
        body.setRadius(body.getRadius() + prey.getSize() / 2);
        timeSinceLastEatenOrSlept = 0;
        prey.die();
    }

    /*
        Hier wird die die-Methode der Animal-Klasse überschrieben.
        Der Körper des Wolfes soll liegen bleiben, wenn er stirbt.
        Dafür wird, zusätzlich zum Aufruf der die-Methode der Animal-Klasse,
        die Farbe des Kreises und seines Rahmens angepasst.
     */
    @Override
    public void die() {
        super.die();
        body.setColor(Colors.WHITE);
        body.setBorder(Colors.RED, 1);
    }

    /*
       Das Überschreiben der handleConfrontation-Methode der Animal-Klasse ist durch das "abstract"-Schlüsselwort in der Super-Klasse erforderlich.
       Hier muss zwischen den Typen des Tiers unterschieden werden, mit denen der Wolf zusammenstößt.
       Dies wird durch eine if-Abfrage gelöst, in der mit "instance of" geprüft wird, zu welcher Klasse ein Objekt gehört.

       1. Handelt es sich um ein Schaf, frisst der Wolf dieses.
       2. Handelt es sich um einen anderen Wolf, hängt das Verhalten vom Größenverhältnis der beiden Wölfe ab.
            2.1 Ist der aktuelle Wolf (this) größer als der als Parameter übergebene, stirbt der Übergebene
            2.2 Ist der aktuelle Wolf (this) kleiner, stirbt er selbst
            2.3 Sind beide gleich groß, gehen sie jeweils in die umgekehrte Richtung weiter
     */
    @Override
    public void handleConfrontation(Animal animal) {
        if (animal instanceof Sheep) {
            devour((Sheep) animal);
        } else if (animal instanceof  Wolf) {
            if (this.getSize() > animal.getSize()) {
                animal.die();
            } else if (animal.getSize() > this.getSize()) {
                this.die();
            } else {
                movementX *= -1;
                movementY *= -1;
            }
        }
    }
}
