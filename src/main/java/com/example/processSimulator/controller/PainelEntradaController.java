package com.example.processSimulator.controller;
import com.example.processSimulator.model.PCB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class PainelEntradaController {

    @FXML
    private TextField arrivalTimeTextField;

    @FXML
    private TextField execucaoTextField;
    @FXML
    private TextField pidTextField;

    @FXML
    private Button adicionarButton;

    @FXML
    private TabelaController tabelaController;

    private ObservableList<PCB> listaDeProcessos = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> algoritmoComboBox; // Elemento nao será mais necessário aqui, mas sera criado em outra tela ao clicar em iniciar simulacao

    @FXML
    private void initialize() {
        if (tabelaController != null)
            tabelaController.setProcessos(listaDeProcessos);
    }


    // TODO como salvar o algoritmo escolihod como variavel global
    /**
     * Este método é chamado quando o botão "Adicionar Processo" é clicado,
     * conforme definido pelo onAction="#handleAdicionarProcesso" no FXML.
     */

    @FXML
    protected void handleAdicionarProcesso(ActionEvent event) {
        String pid = pidTextField.getText();
        String arrivalTime = arrivalTimeTextField.getText();
        String tempoExecucao = execucaoTextField.getText();

        PCB pcb = new PCB(Long.parseLong(pid), Long.parseLong(tempoExecucao), Long.parseLong(arrivalTime));

        listaDeProcessos.add(pcb);

    }

    /**
     * Método usado para criar processos através da leitura de arquivo
     * @param event
     */
    @FXML
    protected void handleCarregarArquivo(ActionEvent event){

    }

    /**
     * Opcional: Este método é chamado automaticamente após o FXML ser carregado.
     * Útil para configurações iniciais.
     */

    /**
     * Método responsavel por disparar evento que criará segunda tela onde toda a animação do escalonamento será execultada.
     * @param event
     */
    public void handleIniciarSimulacao(ActionEvent event) {
    }
}
