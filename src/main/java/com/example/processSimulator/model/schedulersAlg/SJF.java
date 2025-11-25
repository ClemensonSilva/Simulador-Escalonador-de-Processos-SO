package com.example.processSimulator.model.schedulersAlg;

import com.example.processSimulator.exceptions.FilaVaziaException;
import com.example.processSimulator.model.PCB;
import com.example.processSimulator.model.StatusProcess;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SJF  implements IScheduler{
    private String name;
    private final PriorityQueue<PCB> SJFReadyQueue;
    private  final List<PCB> SJFFinishedQueue;
    private PCB runningProcess;

    public SJF() {
        this.SJFReadyQueue = new PriorityQueue<>(Comparator.comparingLong(PCB::getBurstTime).thenComparing(PCB::getArrivalTime));
        this.SJFFinishedQueue = new ArrayList<>();
        this.name = "Shortest Job First";
        this.runningProcess = null;
    }

    public void addProcess(PCB pcb) {
        pcb.setStatus(StatusProcess.READY);
        SJFReadyQueue.add(pcb);
    }


    @Override
    public PCB nextPCB() {
        if(SJFReadyQueue.isEmpty()) throw new FilaVaziaException();;
        setRunningProcess(SJFReadyQueue.remove());
        this.runningProcess.setStatus(StatusProcess.RUNNING);
        return  getRunningProcess();
    }

    @Override
    public List<PCB> getReadyList() {
        return SJFReadyQueue.stream().toList();
    }

    @Override
    public List<PCB> getFinishedList() {
        return SJFFinishedQueue;
    }

    @Override
    public boolean isEmpty() {
        return (SJFReadyQueue.isEmpty());
    }

    @Override
    public PCB finishProcess() {
        if(runningProcess == null) throw new FilaVaziaException();
        PCB pcb = getRunningProcess();
        pcb.setStatus(StatusProcess.TERMINATED);
        SJFFinishedQueue.add(pcb);
        return pcb ;
    }

    @Override
    public String toString() {
        return name  ;
    }

    public PCB getRunningProcess() {
        return runningProcess;
    }

    public void setRunningProcess(PCB runningProcess) {
        this.runningProcess = runningProcess;
    }
}
