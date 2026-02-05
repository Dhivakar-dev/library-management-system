package com.dhivakar.Library_Management_System.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {

    @NotNull(message = "Book ID is mandatory")
    private Long bookId;

    private String notes;
}
