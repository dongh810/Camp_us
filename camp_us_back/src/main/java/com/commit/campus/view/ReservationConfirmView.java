package com.commit.campus.view;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReservationConfirmView {

    private Long userId;
    private Long campId;
    private Long campFacsId;
    private LocalDateTime reservationDate;
    private LocalDate entryDate;
    private LocalDate leavingDate;
    private String reservationStatus;

}
