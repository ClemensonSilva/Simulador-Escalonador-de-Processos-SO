package com.example.processSimulator.model.Schedulers;

import com.example.processSimulator.model.PCB;

import java.util.List;

public interface IScheduler {
    public void addProcess(PCB pcb);
    public PCB nextPCB();
    public List<PCB> getReadyList();
    public List<PCB> getFinishedList();
    public boolean isEmpty();
    public PCB finishProcess();
}
