package de.treestudio;

import de.treestudio.domain.*;
import de.treestudio.domain.Line;
import de.treestudio.service.TreeGenerationService;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Main extends Application {

    private TreeGenerationService treeGenerationService = new TreeGenerationService();

    @Override
    public void start(Stage stage) throws Exception {
        final Pane pane = new Pane();
        addBranchesToPane(treeGenerationService, pane);
        Button button = new Button("Nochmal");
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
        Scene scene = new Scene(vBox, 800, 600);
        scene.widthProperty();
        return scene;
    }

    private void initStage(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.setTitle("magic trees 1.0");
        stage.setMinHeight(800);
        stage.setMinWidth(600);
    }

    private void addBranchesToPane(TreeGenerationService treeGenerationService, Pane pane) {
        Branch branch = treeGenerationService.generateBranch();
        pane.getChildren().addAll(branch.getBranchLines());
    }

    private void removeBranchesFromPane(Pane pane) {
        Iterator<Node> iterator = pane.getChildren().iterator();
        while(iterator.hasNext()) {
            Node node = iterator.next();
            if (node instanceof javafx.scene.shape.Line) {
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
