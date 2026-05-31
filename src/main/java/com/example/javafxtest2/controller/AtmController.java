package com.example.javafxtest2.controller;

import com.example.javafxtest2.model.AtmSimulation;

import java.util.ArrayList;
import java.util.List;

public class AtmController {

    private final List<AtmSimulation> activeAtms;
    private final List<Thread> atmThreads;

    public AtmController() {
        this.activeAtms = new ArrayList<>();
        this.atmThreads = new ArrayList<>();
    }

    public void startAtms(int count) {
        for (int i = 1; i <= count; i++) {
            AtmSimulation atm = new AtmSimulation("ATM-" + i);
            Thread thread = new Thread(atm);

            activeAtms.add(atm);
            atmThreads.add(thread);

            thread.start();
        }
    }

    public void stopAllAtms() {
        for (AtmSimulation atm : activeAtms) {
            atm.stopSimulation();
        }
        for (Thread thread : atmThreads) {
            thread.interrupt();
        }
        activeAtms.clear();
        atmThreads.clear();
    }
}