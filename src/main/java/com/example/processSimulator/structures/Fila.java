package com.example.processSimulator.structures;

import com.example.processSimulator.exceptions.FilaCheiaException;
import com.example.processSimulator.exceptions.FilaVaziaException;

import java.util.List;

public interface Fila<E> {
  public int size();

  public boolean isEmpty();

  public E front() throws FilaVaziaException;

    public E next() throws FilaVaziaException;

    public void enqueue(E element) throws FilaCheiaException;

  public E dequeue() throws FilaVaziaException;
  public void show();
  public List<E> getAll();
}
