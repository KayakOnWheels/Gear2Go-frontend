package com.gear2go_frontend.dto;

import java.time.LocalDate;

public record ProductAvailabilityRequest(Long productId, LocalDate rentDate, LocalDate returnDate) {
}
