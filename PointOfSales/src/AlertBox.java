
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * AlertBox.java - Used to display a message if an error has occurred or to display if a product has been found
 *
 * in the search() method in PointOfSales.java.
 *
 * @author Rohan
 * @version 1.0
 */
public class AlertBox extends PointOfSales {

    /**
     * Displays the AlertBox window.
     *
     * @param title A variable of type String
     * @param message A variable of type String
     */
    public static void display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);
        window.setMinHeight(150);

        Label label1 = new Label();
        label1.setText(message);
        label1.setFont(new Font(16.0));
        label1.setAlignment(Pos.CENTER);
        Button exitButton = new Button("OK");
        exitButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.setSpacing(15);
        layout.getChildren().addAll(label1, exitButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}
