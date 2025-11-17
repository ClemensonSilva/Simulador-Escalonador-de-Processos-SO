package com.example.processSimulator.model.SchedulersAlg;

import com.example.processSimulator.exceptions.FilaVaziaException;
import com.example.processSimulator.model.PCB;
import com.example.processSimulator.model.StatusProcess;
import com.example.processSimulator.structures.Fila;
import com.example.processSimulator.structures.FilaVector;

import java.util.List;

public class FCFS implements IScheduler {
    private String name;
    private Fila<PCB> readyQueue;
    private Fila<PCB> finishedQueue;
    private PCB runningProcess;

    public FCFS() {
        this.readyQueue = new FilaVector<>(25);
        this.finishedQueue = new FilaVector<>(25);
        this.name = "FCFS";
        this.runningProcess = null;
    }

    @Override
    public void addProcess(PCB pcb) {
        pcb.setStatus(StatusProcess.READY);
        readyQueue.enqueue(pcb);
    }

    @Override
    public PCB nextPCB() {
        if(readyQueue.isEmpty()) throw new FilaVaziaException();;
        setRunningProcess(readyQueue.dequeue());
        this.runningProcess.setStatus(StatusProcess.READY);
        return  getRunningProcess();
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
        if(runningProcess == null) throw new FilaVaziaException();
        PCB pcb = getRunningProcess();
        pcb.setStatus(StatusProcess.TERMINATED);
        readyQueue.enqueue(pcb);
        return pcb;
    }

    @Override
    public String toString() {
        return name  ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public PCB getRunningProcess() {
        return runningProcess;
    }

    public void setRunningProcess(PCB runningProcess) {
        this.runningProcess = runningProcess;
    }
}
