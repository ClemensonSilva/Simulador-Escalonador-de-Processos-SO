package com.example.processSimulator.structures;

import com.example.processSimulator.model.OrdenationCriteria;
import com.example.processSimulator.model.PCB;

import java.util.function.Function;

public class QuickSort {
    private  PCB [] pcbs;
    private Function<PCB, Long> extrator;

    public PCB[] sort( ){
        quick_sort(pcbs, 0, pcbs.length -1);

        return pcbs;
    }

    private PCB[] quick_sort(PCB[] array, int left, int rigth){
        if(left < rigth) {
            int pivot = partition(array, left, rigth);
            quick_sort(array, pivot+1, rigth);
            quick_sort(array, left, pivot-1);
        }

        return array;
    }

    private int partition(PCB[] array, int left, int rigth ){
        int i;
        long atualVal;
        i = left -1;


        long pivot = extrator.apply(array[rigth]); // escolhe elemento do fim

        for (int j = left; j < rigth; j++) {
            atualVal = extrator.apply(array[j]);
            if(atualVal <= pivot){
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i+1,  rigth);
        return i+1;
    }

    private static void swap(PCB[] arr, int i, int j) {
        PCB temp = (arr[i]);
        arr[i] = arr[j];
        arr[j] = temp;
    }


    public QuickSort(PCB[] pcbs, Function<PCB, Long> extrator) {
        this.pcbs = pcbs;
        this.extrator = extrator;
    }

    public PCB[] getPcbs() {
        return pcbs;
    }

    public void setPcbs(PCB[] pcbs) {
        this.pcbs = pcbs;
    }
}
