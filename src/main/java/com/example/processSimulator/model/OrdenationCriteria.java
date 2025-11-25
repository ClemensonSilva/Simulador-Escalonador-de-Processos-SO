package com.example.processSimulator.model;

import java.util.function.Function;

public enum OrdenationCriteria {
    PID("PID", PCB::getPid),
    BURST_TIME("Burst Time", PCB::getBurstTime),
    ARRIVAL_TIME("Arrival Time", PCB::getArrivalTime);

    private final String label;
    private final Function<PCB, Long> extrator;


    OrdenationCriteria(String label, Function<PCB, Long> extrator) {
        this.label = label;
        this.extrator = extrator;
    }

    public String getLabel() {
        return label;
    }

    public Function<PCB, Long> getExtrator() {
        return extrator;
    }
}
