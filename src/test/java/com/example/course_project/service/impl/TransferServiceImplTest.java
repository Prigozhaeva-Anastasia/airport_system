package com.example.course_project.service.impl;

import com.example.course_project.entity.Transfer;
import com.example.course_project.entity.TransitFlight;
import com.example.course_project.repository.TransferRepository;
import com.example.course_project.repository.TransitFlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {
    @Mock
    private TransferRepository transferRepository;
    @Mock
    private TransitFlightRepository transitFlightRepository;
    @InjectMocks
    private TransferServiceImpl transferService;
    @Test
    void testCreateTransfer() {
        TransitFlight transitFlight = new TransitFlight();
        transitFlight.setId(1L);
        when(transitFlightRepository.findById(any())).thenReturn(Optional.of(transitFlight));
        transferService.createTransfer("Minsk", LocalTime.now(), 1L);
        verify(transferRepository).save(any());
    }

    @Test
    void testFetchTransfersForTransitFlight() {
        transferService.fetchTransfersForTransitFlight("QW8547");
        verify(transferRepository).getTransfersByTransitFlightNumber("QW8547");
    }

    @Test
    void testFindTransferById() {
        Transfer transfer = new Transfer();
        transfer.setId(1L);
        when(transferRepository.findById(any())).thenReturn(Optional.of(transfer));
        Transfer actualTransfer= transferService.findTransferById(1L);
        assertEquals(transfer, actualTransfer);
    }

    @Test
    void testRemoveTransfer() {
        transferService.removeTransfer(1L);
        verify(transferRepository).deleteById(1L);
    }
}