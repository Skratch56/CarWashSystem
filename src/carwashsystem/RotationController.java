/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carwashsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author danml
 */
public class RotationController implements Initializable {

    @FXML
    private Rectangle rect1;
    @FXML
    private Rectangle rect2;
    @FXML
    private Rectangle rect3;
    @FXML
    private Pane menu1;
    @FXML
    private Pane menu2;
    @FXML
    private Pane menu3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Instantiating rotate animations
        RotateTransition firstTransition = new RotateTransition(Duration.seconds(2), rect1);
        RotateTransition secondTransition = new RotateTransition(Duration.seconds(2), rect2);
        RotateTransition thirdTransition = new RotateTransition(Duration.seconds(2), rect3);
        //Instantiating fade animations
        FadeTransition fade1 = new FadeTransition(Duration.seconds(2), menu1);
        FadeTransition fade2 = new FadeTransition(Duration.seconds(2), menu2);
        FadeTransition fade3 = new FadeTransition(Duration.seconds(2), menu3);
        //Setting properties to fade transitions
        FadeTransition[] fadeTransitions = {fade1, fade2, fade3};
        for (FadeTransition fadeTransition : fadeTransitions) {
            fadeTransition.setAutoReverse(false);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.setCycleCount(1);
        }
        //Setting properties to rotate transitions
        RotateTransition[] rotateTransitions = {firstTransition, secondTransition, thirdTransition};
        for (RotateTransition rotateTransition : rotateTransitions) {
            rotateTransition.setAutoReverse(false);
            rotateTransition.setCycleCount(1);
            rotateTransition.setFromAngle(45);
            rotateTransition.setToAngle(135);
        }

        // Starting sequential animations
        firstTransition.setOnFinished((event) -> {
            fade1.play();
        });
        firstTransition.play();
        fade1.setOnFinished((e) -> {
            secondTransition.play();
        });
        secondTransition.setOnFinished((event2) -> {
            fade2.play();
        });
        fade2.setOnFinished((e) -> {
            thirdTransition.play();
        });
        thirdTransition.setOnFinished((event3) -> {
            fade3.play();
        });

    }

}
