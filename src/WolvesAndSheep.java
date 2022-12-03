import de.ur.mi.oop.app.GraphicsApp;
import de.ur.mi.oop.colors.Color;
import de.ur.mi.oop.colors.Colors;
import de.ur.mi.oop.launcher.GraphicsAppLauncher;


public class WolvesAndSheep extends GraphicsApp {
    private static final Color BACKGROUND_COLOR = Colors.WHITE;
    public static final int CANVAS_HEIGHT = 800;
    public static final int CANVAS_WIDTH = 800;
    public static final int FRAME_RATE = 60;

    @Override
    public void initialize() {
        setupCanvas();
    }

    @Override
    public void draw() {
        drawBackground(BACKGROUND_COLOR);
    }

    private void setupCanvas() {
        setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        setFrameRate(FRAME_RATE);
    }

    public static void main(String[] args) {
        GraphicsAppLauncher.launch();
    }
}
