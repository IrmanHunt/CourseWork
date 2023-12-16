package com.example.coursework;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Класс создание анимации
 */
public class AnimationPageController {

    static final Logger logger = LogManager.getLogger(AnimationPageController.class.getName());

    /**
     * Создание анимации
     */
    public static void animation() {

        Circle circle = new Circle();

        Group root = new Group(circle);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Курсовая работа.");
        stage.setWidth(800);
        stage.setHeight(540);
        stage.setResizable(false);
        stage.setScene(scene);

        circle.setCenterX(400 - 15.0f);
        circle.setCenterY(250.0f);
        circle.setRadius(55.0f);
        circle.setFill(Color.BROWN);
        circle.setStrokeWidth(20);

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(400));
        scaleTransition.setNode(circle);
        scaleTransition.setByY(1.5);
        scaleTransition.setByX(1.5);

        ScaleTransition scaleTransition2 = new ScaleTransition();
        scaleTransition2.setDuration(Duration.millis(250));
        scaleTransition2.setNode(circle);
        scaleTransition2.setByY(-2);
        scaleTransition2.setByX(-2);

        ScaleTransition scaleTransition3 = new ScaleTransition();
        scaleTransition3.setDuration(Duration.millis(400));
        scaleTransition3.setNode(circle);
        scaleTransition3.setByY(10);
        scaleTransition3.setByX(10);

        scaleTransition.play();

        scaleTransition.setOnFinished((finish) -> {
            scaleTransition2.play();
        });

        scaleTransition2.setOnFinished((finish) -> {
            scaleTransition3.play();
        });

        scaleTransition3.setOnFinished((finish) -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainPage.fxml"));
            Scene scene2 = null;
            try {
                scene2 = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                logger.error("Ошибка в анимации");
                throw new RuntimeException(e);
            }
            stage.setScene(scene2);
            stage.show();
        });

        stage.show();
    }
}