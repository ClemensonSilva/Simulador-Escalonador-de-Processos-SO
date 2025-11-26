package com.example.processSimulator.model.schedulersAlg;

import com.example.processSimulator.model.PCB;

import java.util.List;

public class RoundRobin implements IScheduler{
    @Override
    public void addProcess(PCB pcb) {

    }

    @Override
    public PCB nextPCB() {
        return null;
    }

    @Override
    public List<PCB> getReadyList() {
        return List.of();
    }

    @Override
    public List<PCB> getFinishedList() {
        return List.of();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public PCB finishProcess() {
        return null;
    }

    @Override
    public PCB getRunningProcess() {
        return null;
    }

    @Override
    public void setRunningProcess(PCB pcb) {

    }
}
