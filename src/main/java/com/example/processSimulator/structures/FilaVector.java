package com.example.processSimulator.structures;
import com.example.processSimulator.exceptions.FilaCheiaException;
import com.example.processSimulator.exceptions.FilaVaziaException;

import java.util.Arrays;
import java.util.List;

/**
 * Classe que implementa uma fila utilizando vetores.
 * @param <E>
 */
public class FilaVector<E>  implements Fila<E> {
    private int size;
    private int head;
    private int tail;
    private E[] array;

    public FilaVector(int size) {
        this.size = size;
        this.array = (E[]) new Object[size];
        this.tail = this.head = 0;

    }


    public int size() {
        return this.tail - this.size;
    }


    public void show() {
        System.out.println("FilaVectorImpl{" +
                "array=" + Arrays.toString(array) +
                '}');
    }

    @Override
    public List<E> getAll() {
        List<E> list = List.of();
        for (E element : array){
            list.add(element);
        }
        return  list;
    }


    public boolean isEmpty() {
        return head == tail;
    }


    public E front() throws FilaVaziaException {
        if (this.isEmpty())
            throw new FilaVaziaException("A fila está vazia");
        return array[head];
    }

    public E next() throws FilaVaziaException {
        if (this.size == 1)
            throw new FilaVaziaException("Não há proximo elemento");
        return array[head-1];
    }


    public void enqueue(E element) throws FilaCheiaException {
        if (size == this.size())
            throw new FilaCheiaException("A fila esta cheia");
        this.array[(tail % this.size)] = element;
        this.tail++;
    }


    public E dequeue() throws FilaVaziaException {
        if (this.isEmpty())
            throw new FilaVaziaException("A fila está vazia");
        E element = array[head % this.size];
        array[head % this.size] = null;
        this.head++;
        return element;
    }

}
