package com.example.processSimulator.structures;

import com.example.processSimulator.model.PCB;

public class BuscaLinear {

    private PCB [] pcbs;

    public PCB findByPID(long pid)  {
        PCB pcb = null;
        for (int i = 0; i < pcbs.length; i++) {
            if(pcbs[i].getPid() == pid) pcb= pcbs[i];
        }
        
        return pcb;
    }

    public BuscaLinear(PCB[] pcbs) {
        this.pcbs = pcbs;
    }

    public PCB[] getPcbs() {
        return pcbs;
    }

    public void setPcbs(PCB[] pcbs) {
        this.pcbs = pcbs;
    }
}
