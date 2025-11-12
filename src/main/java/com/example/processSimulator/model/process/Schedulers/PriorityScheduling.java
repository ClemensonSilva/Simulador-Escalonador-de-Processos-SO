package com.example.processSimulator.model.process.Schedulers;

import com.example.processSimulator.model.process.PCB;

import java.util.*;

public class PriorityScheduling implements IScheduler {

    public Queue<PCB> priorityReadyQueue;
    public List<PCB> priorityFinishedList;

    public PriorityScheduling() {
        this.priorityReadyQueue = new PriorityQueue<>(Comparator.comparingLong(PCB::getPriority));
        this.priorityFinishedList = new ArrayList<>();
    }

    @Override
    public void addProcess(PCB pcb) {
        priorityReadyQueue.add(pcb);
    }

    @Override
    public PCB nextPCB() {
        return null;
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
        PCB pcb = priorityReadyQueue.remove();
        priorityFinishedList.add(pcb);
        return pcb;
    }

}
