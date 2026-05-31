package com.example.javafxtest2.model;

import java.time.LocalDate;

public interface TimeObserver {
    void onDateChanged(LocalDate newDate, boolean monthChanged);
}