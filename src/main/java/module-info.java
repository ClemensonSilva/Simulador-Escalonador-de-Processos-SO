module com.example.processSimulator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.processSimulator to javafx.fxml;
    exports com.example.processSimulator;


    opens com.example.processSimulator.controller to javafx.fxml;
    exports com.example.processSimulator.controller;
    exports com.example.processSimulator.structures;
    opens com.example.processSimulator.structures to javafx.fxml;

}