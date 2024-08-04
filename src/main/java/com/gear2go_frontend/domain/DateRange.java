package com.gear2go_frontend.domain;

import org.springframework.lang.Nullable;

import java.time.LocalDate;

public record DateRange(LocalDate rentDate, LocalDate returnDate, @Nullable String mail) {
}
