package com.pointofsales;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * ConfirmBox.java - Used for confirmation. Ex: For closing windows, or before removing a product.
 *
 * @author Rohan
 * @version 1.0
 */
public class ConfirmBox extends PointOfSales {

    static boolean answer;

    /**
     * display() method is used to display the confirmation window.
     *
     * @param title A variable of type String
     * @param message A variable of type String
     * @return A boolean data type.
     */
    public static boolean display(String title, String message) {
        Stage window = new Stage();
        /* window must be closed to performed other actions in the program */
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label text = new Label();
        text.setText(message);

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        Button noButton = new Button("No");
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(text, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
