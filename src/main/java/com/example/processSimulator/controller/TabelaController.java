package com.example.processSimulator.controller;

import com.example.processSimulator.model.PCB;
import com.example.processSimulator.model.StatusProcess;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TabelaController implements Initializable {
    @FXML
    private TableView<PCB> processosTable;
    @FXML
    private TableColumn<PCB, Long> pidColumn;

    @FXML
    private TableColumn<PCB, StatusProcess> statusColumn;

    @FXML
    private TableColumn<PCB, Long> burstTimeColumn;

    @FXML
    private TableColumn<PCB, Long> arrivalTimeColumn;

    @FXML
    private TableColumn<PCB, Long> waitingTimeColumn;

    @FXML
    private TableColumn<PCB, Long> completionTimeColumn;

    @FXML
    private TableColumn<PCB, Long> turnAroundColumn;



    public TableView<PCB> getProcessosTable(){
        return  processosTable;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        burstTimeColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        waitingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
        completionTimeColumn.setCellValueFactory(new PropertyValueFactory<>("completionTime"));
        turnAroundColumn.setCellValueFactory(new PropertyValueFactory<>("turnAround"));
    }
    public void setProcessos(ObservableList<PCB> lista) {
        processosTable.setItems(lista);
    }
}
