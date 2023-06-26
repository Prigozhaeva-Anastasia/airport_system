package com.example.course_project.service;

import com.example.course_project.entity.Card;
import com.example.course_project.entity.Role;
import com.example.course_project.entity.Transfer;
import com.example.course_project.entity.TransitFlight;

import java.time.LocalTime;
import java.util.List;

public interface TransferService {
    Transfer createTransfer(String nameOfCity, LocalTime arrivalTime, Long transitFlightId);
    Transfer createOrUpdateTransfer(Transfer transfer);
    List<Transfer> fetchTransfersForTransitFlight(String flightNumber);
    Transfer findTransferById(Long id);
    void removeTransfer(Long transferId);
}
