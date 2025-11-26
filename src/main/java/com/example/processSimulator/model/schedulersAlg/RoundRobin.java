package com.example.processSimulator.model.schedulersAlg;

import com.example.processSimulator.exceptions.FilaVaziaException;
import com.example.processSimulator.model.PCB;
import com.example.processSimulator.model.StatusProcess;

import java.util.*;

public class RoundRobin implements IScheduler {

    private String name;

    private Queue<PCB> roundReadyQueue;
    private List<PCB> roundFinishedList;
    private PCB runningProcess;

    public RoundRobin() {
        this.name = "Round Robin";
        this.roundReadyQueue = new LinkedList<>();
        this.roundFinishedList = new ArrayList<>();
        this.runningProcess = null;
    }

    @Override
    public void addProcess(PCB pcb) {
        pcb.setStatus(StatusProcess.READY);
        roundReadyQueue.add(pcb);
    }

    @Override
    public PCB nextPCB() {
        if(roundReadyQueue.isEmpty()) throw new FilaVaziaException();
        setRunningProcess(roundReadyQueue.remove());
        this.runningProcess.setStatus(StatusProcess.RUNNING);
        return getRunningProcess();
    }

    public boolean needsPreempting() {
        if(runningProcess == null) return false;

        return (runningProcess.getQuantum() <= 0);
    }

    public PCB preemptRunningProcess() {
        if (runningProcess == null) return null;

        PCB preempted = runningProcess;
        preempted.setStatus(StatusProcess.READY);

        preempted.setQuantum(preempted.getQuantumConst());

        roundReadyQueue.add(preempted);
        this.runningProcess = null;

        return preempted;
    }

    @Override
    public List<PCB> getReadyList() {
        return new ArrayList<>(roundReadyQueue);
    }


    @Override
    public List<PCB> getFinishedList() {
        return roundFinishedList;
    }

    @Override
    public boolean isEmpty() {
        return roundReadyQueue.isEmpty();
    }

    @Override
    public PCB finishProcess() {
        if(runningProcess == null) throw new FilaVaziaException();
        PCB pcb = getRunningProcess();
        pcb.setStatus(StatusProcess.TERMINATED);
        roundFinishedList.add(pcb);
        return pcb;
    }

    @Override
    public PCB getRunningProcess() {
        return runningProcess;
    }

    public void setRunningProcess(PCB runningProcess) {
        this.runningProcess = runningProcess;
    }

    @Override
    public String toString() {
        return name;
    }
}