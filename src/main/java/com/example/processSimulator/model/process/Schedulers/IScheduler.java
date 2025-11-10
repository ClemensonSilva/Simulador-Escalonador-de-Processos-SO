package com.example.processSimulator.model.process.Schedulers;

import java.util.List;

public interface IScheduler {
    public void addProcess(Process process);
    public Process nextProcess();
    public List<Process> getReadyList();
    public boolean isEmpty();

}
