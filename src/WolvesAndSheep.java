import de.ur.mi.oop.app.GraphicsApp;
import de.ur.mi.oop.colors.Color;
import de.ur.mi.oop.colors.Colors;
import de.ur.mi.oop.launcher.GraphicsAppLauncher;

import java.util.Random;

public class WolvesAndSheep extends GraphicsApp {

    /* Private Konstanten */
    public static final int CANVAS_HEIGHT = 800;
    public static final int CANVAS_WIDTH = 800;
    private static final int FRAME_RATE = 60;
    private static final Color BACKGROUND_COLOR = Colors.WHITE;

    private static final int WOLF_COUNT = 5;
    private static final int SHEEP_COUNT = 15;
    private static final int WOLF_SIZE = 40;
    private static final int SHEEP_SIZE = 20;

    private static final int MIN_SPEED = -3;
    private static final int MAX_SPEED = 3;

    private static final Random rand = new Random();

    private Animal[] animals;

    @Override
    public void initialize() {
        setupCanvas();
        setupAnimals();
    }

    /*
        In dieser Methode werden Instanzen der Klassen Wolf und Sheep erstellt.
        Diese werden in einem gemeinsamen Array gespeichert.
        Das funktioniert, weil beide Klassen eine gemeinsame Superklasse Animal haben.
        Das Konzept, das hier ausgenutzt wird, nennt man Polymorphismus.
        (Ein Wolf ist ein Wolf und ein Animal)
     */
    private void setupAnimals() {
        animals = new Animal[WOLF_COUNT + SHEEP_COUNT];
        for (int i = 0; i < WOLF_COUNT; i++) {
            animals[i] = new Wolf(
                    rand.nextFloat(CANVAS_WIDTH),
                    rand.nextFloat(CANVAS_HEIGHT),
                    WOLF_SIZE,
                    Vector.getRandom(MIN_SPEED, MAX_SPEED)
            );
        }
        for (int i = WOLF_COUNT; i < WOLF_COUNT + SHEEP_COUNT; i++) {
            animals[i] = new Sheep(
                    rand.nextFloat(CANVAS_WIDTH),
                    rand.nextFloat(CANVAS_HEIGHT),
                    SHEEP_SIZE,
                    Vector.getRandom(MIN_SPEED, MAX_SPEED)
            );
        }
    }

    /*
        Im draw()-Loop werden alle Instanzen innerhalb des Animal-Arrays gezeichnet
        und anschließend deren Position aktualisiert.
        Dafür wird eine for-each Schleife genutzt.
     */
    @Override
    public void draw() {
        drawBackground(BACKGROUND_COLOR);
        for (Animal animal : animals) {
            animal.draw();
        }
        updateAnimals();
    }

    /*
        In dieser Methode werden die Positionen der Animal-Instanzen aktualisiert.
        Dafür wird über das Array iteriert und die update-Methode der jeweiligen Animal-Instanz aufgerufen.
        Zusätzlich wird in einer weiteren for-Schleife für jedes Animal geprüft,
        ob dieses nach der Positionsänderung mit einem anderen zusammenstößt.
     */
    private void updateAnimals() {
        for (int i = 0; i < animals.length; i++) {
            Animal currentAnimal = animals[i];
            currentAnimal.update();
            for (int j = 0; j < animals.length; j++) {
                if (i != j) {
                    currentAnimal.checkCollision(animals[j]);
                }
            }
        }
    }

    private void setupCanvas() {
        setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        setFrameRate(FRAME_RATE);
    }

    public static void main(String[] args) {
        GraphicsAppLauncher.launch();
    }

}
