package com.example.processSimulator.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import com.example.processSimulator.model.PCB;

/**
 * Classe usada para apresentar os resultados obtidos na simulação. Nela tambem será implementado a funcionalidade de
 * ordenar os processos na tabela por completionTime para verificar os que foram execultads mais rapidamente.
 */
public class ResultadosController {
    // TODO verificar uma forma de incluir um algoritmo de busca no projeto.
    @FXML
    private TableView<PCB> processosTerminadosTable;

    // Método usado ao se concluir a simulação para coleta de informações
    public void setResultados(ObservableList<PCB> listaDeProcessos) {
        processosTerminadosTable.setItems(listaDeProcessos);
    }
}