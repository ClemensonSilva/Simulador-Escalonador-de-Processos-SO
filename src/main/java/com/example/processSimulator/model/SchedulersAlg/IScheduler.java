package com.example.processSimulator.model.SchedulersAlg;

import com.example.processSimulator.model.PCB;

import java.util.List;

public interface IScheduler {
    /**
     * Add a process to the READY queue of the algorithm
     * @param pcb the pcb to be set to the READY queue
     */
    public void addProcess(PCB pcb);

    /**
     * Used in the simulation to setthe pcb that is current running;
     * Remove the head of the queue and set the Running attribute to be it.
     * @return The next pcb inside the ready queue to be put on the Running attribute or FilaVazia Exception if the queue is empty
     */
    public PCB nextPCB();
    public List<PCB> getReadyList();
    public List<PCB> getFinishedList();
    public boolean isEmpty();

    /**
     * Send the running attribute to the finished Queue
     * @return  the set pcb or FilaVazia Exception if the queue is empty.
     */
    public PCB finishProcess();
    public PCB getRunningProcess();
    public void setRunningProcess(PCB pcb);
}
