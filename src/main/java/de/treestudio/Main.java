package de.treestudio;

import de.treestudio.domain.*;
import de.treestudio.service.TreeGenerationService;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Iterator;

public class Main extends Application {

    public static final int WIDTH = 600;
    public static final int HEIGHT = TreeGenerationService.MAX_BRANCH_HEIGHT_END + 50;
    private TreeGenerationService treeGenerationService = new TreeGenerationService();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final Pane pane = new Pane();
        addBranchesToPane(treeGenerationService, pane);
        Button button = new Button("Again please");
        button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent actionEvent) {
                removeBranchesFromPane(pane);
                addBranchesToPane(treeGenerationService, pane);
            }
        });

        VBox vBox = new VBox();
        vBox.getChildren().add(pane);
        vBox.getChildren().add(button);

        Scene scene = initScene(vBox);
        initStage(stage, scene);
        stage.show();
    }

    private Scene initScene(VBox vBox) {
        Scene scene = new Scene(vBox, 1000, 1000);
        scene.widthProperty();
        return scene;
    }

    private void initStage(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.setTitle("magic trees 1.0");
        stage.setMinHeight(HEIGHT);
        stage.setMinWidth(WIDTH);
    }

    private Line drawLine(de.treestudio.domain.Line line) {
        return new Line(line.getXStart(), TreeGenerationService.TRUNK_HEIGHT_END - line.getYStart(), line.getXEnd(),
                TreeGenerationService.TRUNK_HEIGHT_END - line.getYEnd());
    }

    private void addBranchesToPane(TreeGenerationService treeGenerationService, Pane pane) {
        Tree tree = treeGenerationService.generateTree();
        pane.getChildren().add(drawLine(tree.getTrunk().getTrunkLine()));
        for (Branch branch : tree.getTrunk().getBranches()) {
            for (de.treestudio.domain.Line line : branch.getBranchLines()) {
                pane.getChildren().add(drawLine(line));
            }
        }
    }

    private void removeBranchesFromPane(Pane pane) {
        Iterator<Node> iterator = pane.getChildren().iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node instanceof javafx.scene.shape.Line) {
                iterator.remove();
            }
        }
    }
}
