import java.util.Random;

/*
    In dieser Klasse soll x- und y-Bewegungsrichtung in einem 2d-Vektor zusammengefasst werden.
    Dadurch müssen nicht wie in vorherigen Aufgaben x- und y-Geschwindigkeit als zwei Variablen bzw. Parameter verarbeitet werden.
 */
public class Vector {

    public float x;
    public float y;

    private static final Random rand = new Random();

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /*
        In dieser Methode wird eine Vektor-Instanz erzeugt,
        deren x- und y-Werte zufällig bestimmt zwischen einem oberen und unteren Grenzwert liegen.
        Durch das "static"-Schlüsselwort, handelt es sich dabei um ein Klassenmember.
        Der Aufruf erfolgt demnach mit Vector.getRandom(...).
     */
    public static Vector getRandom(float min, float max) {
        return new Vector(
                rand.nextFloat(max - min) + min,
                rand.nextFloat(max - min) + min
        );
    }

    public void mirror() {
        x *= -1;
        y *= -1;
    }

    public void mirrorX() {
        x *= -1;
    }

    public void mirrorY() {
        y *= -1;
    }
}
