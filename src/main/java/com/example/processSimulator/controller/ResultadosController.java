package com.example.processSimulator.controller;

import com.example.processSimulator.HelloApplication;
import com.example.processSimulator.model.OrdenationCriteria;
import com.example.processSimulator.model.schedulersAlg.IScheduler;
import com.example.processSimulator.structures.BuscaLinear;
import com.example.processSimulator.structures.QuickSort;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import com.example.processSimulator.model.PCB;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe usada para apresentar os resultados obtidos na simulação. Nela tambem será implementado a funcionalidade de
 * ordenar os processos na tabela por completionTime para verificar os que foram execultads mais rapidamente.
 */
public class ResultadosController implements Initializable {
    // TODO verificar uma forma de incluir um algoritmo de busca no projeto.
    @FXML
    private TabelaController tabelaController;
    @FXML private ComboBox<OrdenationCriteria> ordenacaoComboBox;
    @FXML private TextField campoBuscaPid;
    @FXML private Label averageWaitingLabel;

    private ObservableList<PCB> processList = FXCollections.observableArrayList();
    private IScheduler algorithm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOrdenacaoComboBox();
        tabelaController.setProcessos(processList);
        campoBuscaPid.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                processList.clear();
                setProcessList();
            }else {
                handleBuscar();
            }
        });

    }

    public void setAverageWaitingLabel() {
        double average = 0;
        for (PCB pcb: algorithm.getFinishedList()){
            average+= pcb.getWaitingTime();
        }
       average =  average/algorithm.getFinishedList().size();
        this.averageWaitingLabel.setText("Tempo médio de Espera: " + average);
    }

    public void setResultsController(IScheduler algorithm) {
        this.algorithm = algorithm;
        this.setProcessList();
        setAverageWaitingLabel();

    }

    public void setProcessList(){
        processList.setAll(algorithm.getFinishedList());
    }
    public void setOrdenacaoComboBox(){
        ordenacaoComboBox.getItems().addAll(OrdenationCriteria.values());
    }


    public void handleOrdenar(ActionEvent event) {
        QuickSort qs = new QuickSort(algorithm.getFinishedList().toArray(new PCB[0]), ordenacaoComboBox.getValue().getExtrator());
        processList.clear();
        processList.addAll(qs.sort());

    }

    public void handleRetornarInicio(ActionEvent event) {
        HelloApplication.painelEntrada();
    }

    public void handleBuscar()  {
        try {
            long pid = Long.parseLong(campoBuscaPid.getText());
            BuscaLinear blinear = new BuscaLinear(algorithm.getFinishedList().toArray(new PCB[0]));
            PCB pcb = blinear.findByPID(pid);

            if (pcb != null) {
                processList.setAll(pcb);
            } else {
                HelloApplication.showAlert("Erro", "Nenhum resultado encontrado", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            HelloApplication.showAlert("Erro", "PID inválido. Digite apenas números.", Alert.AlertType.ERROR);
        }
    }

    public void handleResetarTabela(ActionEvent event) {
        processList.clear();
        setProcessList();
    }
}