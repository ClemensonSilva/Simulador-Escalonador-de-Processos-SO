package com.example.processSimulator.model.process;
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

public class PCB {
    private long pid;
    private StatusProcess status;
    private long burstTime;
    private long arrivalTime;
    private long waitingTime;
    private long completionTime;
    private long turnAround;
    private long priority;

    public PCB(long pid, long burstTime) {
        this.pid = pid;
        this.status = StatusProcess.NEW;
        this.burstTime = burstTime;
        this.arrivalTime = System.currentTimeMillis();
        this.waitingTime = 0;
        this.completionTime = 0;
        this.turnAround = 0;
        this.priority = 0;
    }


    public PCB(long pid, long burstTime, long priority) {
        this.pid = pid;
        this.status = StatusProcess.NEW;
        this.burstTime = burstTime;
        this.arrivalTime = System.currentTimeMillis();
        this.priority = priority;
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

    /**
     * The time spended in the queue with the ready state
     * @return
     */
    public long getWaitingTime() {
        return this.turnAround - this.burstTime;
    }

    public long getTurnAround() {
        return this.completionTime - this.arrivalTime;
    }

    /**
     * used to calculate the  efficiency of a algorithm. It is the time used by the model.process to complete its task
     * @return
     */
    public long getCompletionTime() {
        return this.arrivalTime+ this.waitingTime + this.burstTime;
    }


}

