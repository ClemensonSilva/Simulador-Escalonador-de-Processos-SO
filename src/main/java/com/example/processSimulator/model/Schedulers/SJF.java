package com.example.processSimulator.model.Schedulers;

import com.example.processSimulator.model.PCB;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SJF  implements IScheduler{

    private final PriorityQueue<PCB> SJFReadyQueue;
    private  final List<PCB> SJFFinishedQueue;

    public SJF() {
        this.SJFReadyQueue = new PriorityQueue<>(Comparator.comparingLong(PCB::getBurstTime));
        this.SJFFinishedQueue = new ArrayList<>();
    }


    @Override
    public void addProcess(PCB pcb) {
        SJFReadyQueue.add(pcb);
    }

    @Override
    public PCB nextPCB() {
        return null;
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
        PCB pcb = SJFReadyQueue.remove();
        SJFFinishedQueue.add(pcb);
        return pcb ;
    }
}
