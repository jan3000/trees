package de.treestudio;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        VBox vBox = new VBox();

        Pane pane = new Pane();

        Path path = drawPath();
        pane.getChildren().add(path);
        vBox.getChildren().add(pane);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.setTitle("magic trees 1.0");
        stage.show();
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
        moveTo2.setX(20.0f);
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
