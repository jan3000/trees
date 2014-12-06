package de.treestudio;

import de.treestudio.domain.*;
import de.treestudio.service.TreeGenerationService;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Iterator;

public class Main extends Application {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 800;
    private TreeGenerationService treeGenerationService = new TreeGenerationService();
    public static final int PANE_HEIGTH = HEIGHT - 100;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = initScene();
        initStage(stage, scene);
        stage.show();
    }

    private Scene initScene() {
        final Pane pane = new Pane();
        pane.setMinHeight(PANE_HEIGTH);
        addBranchesToPane(treeGenerationService, pane);


        Button button = new Button("Again please");
        button.setAlignment(Pos.CENTER);
        button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent actionEvent) {
                removeBranchesFromPane(pane);
                addBranchesToPane(treeGenerationService, pane);
            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(PANE_HEIGTH, PANE_HEIGTH);
        borderPane.setCenter(pane);

        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: lightblue");
        vBox.getChildren().add(button);
        vBox.setMinHeight(50);

        vBox.setAlignment(Pos.CENTER);
        borderPane.setBottom(vBox);

        Text headerText = new Text("Trees");
        headerText.setFontSmoothingType(FontSmoothingType.GRAY);
        headerText.setFont(Font.font("Monospaced", 18));
        VBox headerVBox = new VBox();
        headerVBox.setStyle("-fx-background-color: lightblue");
        headerVBox.setMinHeight(50);
        headerVBox.setAlignment(Pos.CENTER);
        headerVBox.getChildren().add(headerText);
        borderPane.setTop(headerVBox);

        VBox leftBox = new VBox();
        leftBox.setMinWidth(50);
        leftBox.setStyle("-fx-background-color: lightblue");
        borderPane.setLeft(leftBox);


        VBox rightBox = new VBox();
        rightBox.setMinWidth(50);
        rightBox.setStyle("-fx-background-color: lightblue");
        borderPane.setRight(rightBox);
        Scene scene = new Scene(borderPane, HEIGHT, HEIGHT, Color.WHITESMOKE);
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
        return new Line(line.getXStart(), PANE_HEIGTH - line.getYStart(), line.getXEnd(),
                PANE_HEIGTH - line.getYEnd());
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
