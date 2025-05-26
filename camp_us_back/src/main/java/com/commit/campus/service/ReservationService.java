package com.commit.campus.service;

import com.commit.campus.dto.ReservationDTO;

import java.util.List;

public interface ReservationService {
    String createReservation(ReservationDTO reservationDTO);
    ReservationDTO confirmReservation(String reservationId);
    void cancelReservation(String reservationId);

    List<ReservationDTO> getReservation(Long userId);
}
