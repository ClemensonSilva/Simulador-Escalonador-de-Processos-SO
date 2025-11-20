package com.example.processSimulator.exceptions;

public class FilaVaziaException extends RuntimeException {
  public FilaVaziaException() {
    super("Fila está vazia");
  }
}
