package com.example.processSimulator.model.SchedulersAlg;

import com.example.processSimulator.exceptions.FilaVaziaException;
import com.example.processSimulator.model.PCB;
import com.example.processSimulator.model.StatusProcess;

import java.util.*;

public class PriorityScheduling implements IScheduler {
    private String name;

    private Queue<PCB> priorityReadyQueue;
    private List<PCB> priorityFinishedList;
    private PCB runningProcess ;
    public PriorityScheduling() {
        this.priorityReadyQueue = new PriorityQueue<>(Comparator.comparingLong(PCB::getPriority));
        this.priorityFinishedList = new ArrayList<>();
        this.name = "Priority Schedulling";
    }

    @Override
    public void addProcess(PCB pcb) {
        pcb.setStatus(StatusProcess.READY);
        priorityReadyQueue.add(pcb);
    }

    @Override
    public PCB nextPCB() {
        if(priorityReadyQueue.isEmpty()) throw new FilaVaziaException();;
        setRunningProcess(priorityReadyQueue.remove());
        this.runningProcess.setStatus(StatusProcess.READY);
        return  getRunningProcess();
    }

    @Override
    public List<PCB> getReadyList() {
        return priorityReadyQueue.stream().toList();
    }

    @Override
    public List<PCB> getFinishedList() {
        return priorityFinishedList;
    }

    @Override
    public boolean isEmpty() {
        return (priorityReadyQueue.isEmpty());
    }

    @Override
    public PCB finishProcess() {
        if(runningProcess == null) throw new FilaVaziaException();
        PCB pcb = getRunningProcess();
        pcb.setStatus(StatusProcess.TERMINATED);
        priorityFinishedList.add(pcb);
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
