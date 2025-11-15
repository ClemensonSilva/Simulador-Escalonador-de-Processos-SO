package com.example.processSimulator.model.Schedulers;

import com.example.processSimulator.model.PCB;
import com.example.processSimulator.structures.Fila;
import com.example.processSimulator.structures.FilaVector;

import java.util.List;

public class FCFS implements IScheduler {
    public Fila<PCB> readyQueue;
    public Fila<PCB> finishedQueue;

    public FCFS() {
        this.readyQueue = new FilaVector<>(25);
        this.finishedQueue = new FilaVector<>(25);
    }

    @Override
    public void addProcess(PCB pcb) {
        readyQueue.enqueue(pcb);
    }

    @Override
    public PCB nextPCB() {
        return readyQueue.next();
    }

    @Override
    public List<PCB> getReadyList() {
        return readyQueue.getAll();
    }

    @Override
    public List<PCB> getFinishedList() {
        return finishedQueue.getAll();
    }

    @Override
    public boolean isEmpty() {
        return readyQueue.isEmpty();
    }

    @Override
    public PCB finishProcess() {
        PCB pcb = readyQueue.dequeue();
        finishedQueue.enqueue(pcb);
        return pcb;
    }
}
