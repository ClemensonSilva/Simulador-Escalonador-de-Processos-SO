package com.example.processSimulator.model;
import com.example.processSimulator.model.SchedulersAlg.IScheduler;

import java.util.Objects;

/**
 * This class is responsible for represent the PCB of a model.process which is where some important
 * information about the model.process used by scheduler can be found. For this purpose, some information
 * about memory allocation,  the status of the model.process before switching will not be saved here.
 *
 * Those information will be used by the scheduler algorithm to choose the right model.process.
 *
 * Definitions of others attributes can be found here: https://www.baeldung.com/cs/cpu-scheduling
 *
 */

public class PCB implements Comparable<PCB> {
    private long pid;
    private StatusProcess status;
    private long burstTime;
    private long arrivalTime;
    private long waitingTime;
    private long completionTime;
    private long remaingTime;
    private long turnAround;
    private long priority;
    private IScheduler schedulerAlg;

    public PCB(long pid, long burstTime, long arrivalTime) {
        this.pid = pid;
        this.status = StatusProcess.NEW;
        this.burstTime = burstTime;
        this.remaingTime = burstTime;
        this.waitingTime = 0;
        this.completionTime = 0;
        this.turnAround = 0;
        this.priority = 0;
        this.arrivalTime = arrivalTime;
    }


    public PCB(long pid, long burstTime, long priority, long arrivalTime ) {
        this.pid = pid;
        this.status = StatusProcess.NEW;
        this.burstTime = burstTime;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PCB pcb = (PCB) o;
        return pid == pcb.pid;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pid);
    }

    @Override
    public String toString() {
        return "PCB{" +
                "pid=" + pid +
                ", status=" + status +
                ", burstTime=" + burstTime +
                ", arrivalTime=" + arrivalTime +
                ", waitingTime=" + waitingTime +
                ", completionTime=" + completionTime +
                ", turnAround=" + turnAround +
                ", priority=" + priority +
                '}';
    }

    public IScheduler getSchedulerAlg() {
        return schedulerAlg;
    }

    public void setSchedulerAlg(IScheduler schedulerAlg) {
        this.schedulerAlg = schedulerAlg;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public StatusProcess getStatus() {
        return status;
    }

    public void setStatus(StatusProcess status) {
        this.status = status;
    }

    public long getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(long burstTime) {
        this.burstTime = burstTime;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public long getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(long completionTime) {
        this.completionTime = completionTime;
    }

    /**
     * The time spended in the queue with the ready state
     * @return
     */
    public long ProcWaitingTime() {
        return this.turnAround - this.burstTime;
    }

    public long ProcgetTurnAround() {
        return this.completionTime - this.arrivalTime;
    }

    /**
     * used to calculate the  efficiency of a algorithm. It is the time used by the model.process to complete its task
     * @return
     */
    public long ProcgetCompletionTime() {
        return this.arrivalTime+ this.waitingTime + this.burstTime;
    }


    @Override
    public int compareTo(PCB o) {
        return Long.compare(this.priority, o.priority);
    }
}

