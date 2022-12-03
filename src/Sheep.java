import de.ur.mi.oop.colors.Color;

/*
    Diese Klasse soll ein Sheep darstellen. Dafür erbt sie von der Animal-Klasse.
    Durch die Vererbung übernimmt sie alle Eigenschaften und Methoden der Animal-Klasse.
 */
public class Sheep extends Animal {

    /* Private Konstanten */
    private static final Color SHEEP_COLOR = new Color(150, 150, 150);

    /*
        Im Konstruktor muss der Super-Konstruktor der Animal-Klasse aufgerufen werden.
    */
    public Sheep(float x, float y, float size, float movementX, float movementY) {
        super(x, y, size, SHEEP_COLOR, movementX, movementY);
    }

    /*
        Hier wird die draw-Methode der Animal-Klasse aufgerufen.
        Da ein Sheep dann tot ist, wenn es gefressen wurde, soll es in diesem Fall nicht mehr auf dem Canvas auftauchen.
        Der Aufruf der draw-Methode der Super-Klasse Animal findet also nur unter der Bedingung statt, dass das Sheep noch lebt.
     */
    @Override
    public void draw() {
        if (isAlive) {
            super.draw();
        }
    }

    /*
       Da ein Sheep nicht mehr agieren kann, wenn es auf einen Wolf trifft, weil es vorher gefressen wird,
       muss hier nur der Aufprall mit einem anderen Sheep implementiert werden.
       Dann soll das Sheep umdrehen.

       Hinweis: Warum muss hier nicht die() aufgerufen werden, wenn das Sheep auf einen Wolf trifft?
       Durch die Struktur des Programms trifft der Wolf automatisch auf das Sheep, wenn das Sheep auf den Wolf trifft.
       Das liegt daran, dass in der updateAnimals()-Methode der WolvesAndSheep-Klasse eine Kollision jeden Animals mit jedem anderen geprüft wird.
       Die Kollision wird also auf beiden Seiten (Wolf kollidiert mit Sheep, Sheep kollidiert mit Wolf) nacheinander erkannt.
       Wenn der Wolf entsprechend das Sheep verspeist, muss das Sheep sich nicht aktiv selbst verspeisen lassen.
    */
    @Override
    public void handleConfrontation(Animal animal) {
        if (animal instanceof Sheep) {
            movementX *= -1;
            movementY *= -1;
        }
    }
}
