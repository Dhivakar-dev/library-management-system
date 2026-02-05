package com.dhivakar.Library_Management_System.payload.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistDTO {

    private Long id;
    private Long userId;
    private String userFullName;
    private BookDTO book;
    private LocalDateTime addedAt;
    private String notes;
}
