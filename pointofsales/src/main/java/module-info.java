module com.pointofsales {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.pointofsales to javafx.fxml;
    exports com.pointofsales;
}
