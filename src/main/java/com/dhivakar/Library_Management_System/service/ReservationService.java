package com.dhivakar.Library_Management_System.service;

import com.dhivakar.Library_Management_System.payload.dto.ReservationDTO;
import com.dhivakar.Library_Management_System.payload.request.ReservationRequest;
import com.dhivakar.Library_Management_System.payload.request.ReservationSearchRequest;
import com.dhivakar.Library_Management_System.payload.response.PageResponse;

public interface ReservationService {

    ReservationDTO createReservation(ReservationRequest reservationRequest) throws Exception;

    ReservationDTO createReservationForUser(ReservationRequest reservationRequest, Long userId) throws Exception;

    ReservationDTO cancelReservation(Long reservationId) throws Exception;

    ReservationDTO fulfillReservation(Long reservationId) throws Exception;

    /**
     * Get my reservations (current user) with filters
     * @param searchRequest Search criteria
     * @return Paginated reservations
     */
    PageResponse<ReservationDTO> getMyReservations(ReservationSearchRequest searchRequest) throws Exception;

    PageResponse<ReservationDTO> searchReservations(ReservationSearchRequest searchRequest);
}
