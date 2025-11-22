package com.example.processSimulator.structures;

import com.example.processSimulator.exceptions.FilaCheiaException;
import com.example.processSimulator.exceptions.FilaVaziaException;
import java.util.ArrayList;
import java.util.List;

public class FilaVector<E> implements Fila<E> {
    private int capacity; // Renomeado de 'size' para evitar confusão
    private int head;
    private int tail;
    private E[] array;

    @SuppressWarnings("unchecked")
    public FilaVector(int capacity) {
        this.capacity = capacity;
        this.array = (E[]) new Object[capacity];
        this.tail = 0;
        this.head = 0;
    }

    @Override
    public int size() {
        return tail - head;
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    public boolean isFull() {
        return size() == capacity;
    }

    @Override
    public void enqueue(E element) throws FilaCheiaException {
        if (isFull()) {
            throw new FilaCheiaException("A fila está cheia");
        }
        this.array[tail % capacity] = element;
        this.tail++;
    }

    @Override
    public E dequeue() throws FilaVaziaException {
        if (isEmpty()) {
            throw new FilaVaziaException();
        }
        E element = array[head % capacity];
        array[head % capacity] = null;
        this.head++;
        return element;
    }

    @Override
    public E front() throws FilaVaziaException {
        if (isEmpty()) {
            throw new FilaVaziaException();
        }
        return array[head % capacity];
    }

    @Override
    public E next() throws FilaVaziaException {
        // Implementação do next depende do que você quer.
        // Se for o "próximo após o head" (segundo da fila):
        if (size() < 2) {
            throw new FilaVaziaException();
        }
        return array[(head + 1) % capacity];
    }

    @Override
    public void show() {
        System.out.print("Fila: [");
        for (int i = head; i < tail; i++) {
            System.out.print(array[i % capacity] + (i < tail - 1 ? ", " : ""));
        }
        System.out.println("]");
    }

    @Override
    public List<E> getAll() {
        List<E> list = new ArrayList<>();
        for (int i = head; i < tail; i++) {
            E element = array[i % capacity];
            if (element != null) {
                list.add(element);
            }
        }
        return list;
    }
}