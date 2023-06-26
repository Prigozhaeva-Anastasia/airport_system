package com.example.course_project.service.impl;

import com.example.course_project.entity.Transfer;
import com.example.course_project.entity.TransitFlight;
import com.example.course_project.repository.TransferRepository;
import com.example.course_project.repository.TransitFlightRepository;
import com.example.course_project.service.TransferService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {
    private TransferRepository transferRepository;
    private TransitFlightRepository transitFlightRepository;

    public TransferServiceImpl(TransferRepository transferRepository, TransitFlightRepository transitFlightRepository) {
        this.transferRepository = transferRepository;
        this.transitFlightRepository = transitFlightRepository;
    }

    @Override
    public Transfer createTransfer(String nameOfCity, LocalTime arrivalTime, Long transitFlightId) {
        TransitFlight transitFlight = transitFlightRepository.findById(transitFlightId).orElseThrow(()->new EntityNotFoundException("TransitFlight with id " + transitFlightId + " Not Found"));
        return transferRepository.save(new Transfer(nameOfCity, arrivalTime, transitFlight));
    }

    @Override
    public Transfer createOrUpdateTransfer(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @Override
    public List<Transfer> fetchTransfersForTransitFlight(String flightNumber) {
        return transferRepository.getTransfersByTransitFlightNumber(flightNumber);
    }

    @Override
    public Transfer findTransferById(Long id) {
        return transferRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Transfer with id " + id + " Not Found"));
    }

    @Override
    public void removeTransfer(Long transferId) { transferRepository.deleteById(transferId); }
}
