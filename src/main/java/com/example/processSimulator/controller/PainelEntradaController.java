package com.example.processSimulator.controller;
import com.example.processSimulator.model.process.Schedulers.IScheduler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class PainelEntradaController {

    @FXML
    private TextField chegadaTextField;

    @FXML
    private TextField execucaoTextField;

    @FXML
    private Button adicionarButton;

    @FXML
    private ComboBox<IScheduler> algoritmoComboBox;
    // TODO como salvar o algoritmo escolihod como variavel global
    /**
     * Este método é chamado quando o botão "Adicionar Processo" é clicado,
     * conforme definido pelo onAction="#handleAdicionarProcesso" no FXML.
     */
    @FXML
    protected void handleAdicionarProcesso(ActionEvent event) {
        String tempoChegada = chegadaTextField.getText();
        String tempoExecucao = execucaoTextField.getText();
        String algoritmoSelecionado = algoritmoComboBox.getValue().toString();

        // instancia um obj PCB

        // metodo responsavel por adicionar o pcb criado na tabela que será criada dinamicamente cada vez q um novo processo é adicionado

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
    @FXML
    public void initialize() {
    }

    /**
     * Método responsavel por disparar evento que criará segunda tela onde toda a animação do escalonamento será execultada.
     * @param event
     */
    public void handleIniciarSimulacao(ActionEvent event) {
    }
}
