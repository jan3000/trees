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

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        final TreeGenerationService treeGenerationService = new TreeGenerationService();
        final Path path = drawPath();
        final Pane pane = new Pane();
        pane.getChildren().add(path);
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

        Scene scene = new Scene(vBox, 800, 600);
        scene.widthProperty();
        stage.setScene(scene);
        stage.setTitle("magic trees 1.0");
        stage.setMinHeight(800);
        stage.setMinWidth(600);
        stage.show();
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


    private Path drawPath() {
        Path path = new Path();

        MoveTo moveTo = new MoveTo();
        moveTo.setX(10.0f);
        moveTo.setY(10.0f);

        HLineTo hLineTo = new HLineTo();
        hLineTo.setX(70.0f);

//        QuadCurveTo quadCurveTo = new QuadCurveTo();
//        quadCurveTo.setX(120.0f);
//        quadCurveTo.setY(60.0f);
//        quadCurveTo.setControlX(100.0f);
//        quadCurveTo.setControlY(0.0f);
//
//        LineTo lineTo = new LineTo();
//        lineTo.setX(175.0f);
//        lineTo.setY(55.0f);
//
//        ArcTo arcTo = new ArcTo();
//        arcTo.setX(50.0f);
//        arcTo.setY(50.0f);
//        arcTo.setRadiusX(50.0f);
//        arcTo.setRadiusY(50.0f);

        path.getElements().add(moveTo);
        path.getElements().add(hLineTo);
        MoveTo moveTo2 = new MoveTo();
        moveTo2.setX(10.0f);
        moveTo2.setY(20.0f);

        path.getElements().add(moveTo2);
        path.getElements().add(hLineTo);
        path.getElements().add(moveTo);
        path.getElements().add(hLineTo);
//        path.getElements().add(quadCurveTo);
//        path.getElements().add(lineTo);
//        path.getElements().add(arcTo);

        return path;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
