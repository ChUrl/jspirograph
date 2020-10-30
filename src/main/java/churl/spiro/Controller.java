package churl.spiro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import churl.spiro.objects.Gear;
import churl.spiro.objects.GearBuilder;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

// TODO: Modus, in welchem die Kreise nur die Höhe bestimmen und die X-Koordinate immer weiter wächst
public class Controller {

    @FXML
    Pane mainPane;

    @FXML
    Canvas mainCanvas;
    GraphicsContext canvasGC;

    Gear root;

    private final int SPEED = 5;

    public void initialize() {
        initGearChain();
        initGraphics();
        initLoop();
    }

    private void initLoop() {

        new AnimationTimer() {
            // long lastUpdate;

            @Override
            public void handle(long now) {
                // if (now - lastUpdate >= 25000000) {
                root.update((Math.PI / 60) * SPEED, canvasGC);
                // lastUpdate = now;
                // }
            }
        }.start();

    }

    private void initGraphics() {
        mainPane.setBackground(Background.EMPTY);
        canvasGC = mainCanvas.getGraphicsContext2D();
        canvasGC.setStroke(Color.RED);
    }

    private void initGearChain() {
        Path inputPath = Paths.get("src/main/resources/churl/spiro/config").toAbsolutePath();

        List<Integer> gearConfig = readConfig(inputPath);
        root = buildGearChain(gearConfig);

        root.printGearConfig();

        mainPane.getChildren().addAll(root.printAsList());
    }

    // config-Datei enthält einen Radius pro Zeile
    private static List<Integer> readConfig(Path inputPath) {
        List<Integer> gearConfig = null;

        try {
            gearConfig = Files.readAllLines(inputPath).stream().map(Integer::valueOf).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gearConfig;
    }

    private static Gear buildGearChain(List<Integer> gearConfig) {
        if (gearConfig.isEmpty()) {
            return null;
        }

        GearBuilder gearBuilder = new GearBuilder();

        gearConfig.stream().forEach(radius -> gearBuilder.extend(radius));

        return gearBuilder.build();
    }

}
