package com.example.course_project.service.impl;

import com.example.course_project.entity.Airline;
import com.example.course_project.entity.Passenger;
import com.example.course_project.entity.Review;
import com.example.course_project.repository.AirlineRepository;
import com.example.course_project.repository.PassengerRepository;
import com.example.course_project.repository.ReviewRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private AirlineRepository airlineRepository;
    @Mock
    private PassengerRepository passengerRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void testCreateReview() {
        Airline airline = new Airline();
        airline.setId(1L);
        when(airlineRepository.findById(any())).thenReturn(Optional.of(airline));
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        when(passengerRepository.findById(any())).thenReturn(Optional.of(passenger));
        reviewService.createReview(1L, 1L, 4, 5, 4, 5,4, "good");
        verify(reviewRepository).save(any());
    }

    @Test
    void testFindReviewById() {
        Review review = new Review();
        review.setId(1L);
        when(reviewRepository.findById(any())).thenReturn(Optional.of(review));
        Review actualReview = reviewService.findReviewById(1L);
        assertEquals(review, actualReview);
    }

    @Test
    void testFetchAll() {
        reviewService.fetchAll();
        verify(reviewRepository).findAll();
    }

    @Test
    void testFetchReviewsForAirline() {
        reviewService.fetchReviewsForAirline(1L);
        verify(reviewRepository).getReviewsByAirlineId(1L);
    }

    @Test
    void testCreateOrUpdateReview() {
        Review review = new Review();
        review.setId(1L);
        Airline airline = new Airline();
        airline.setId(1L);
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        when(airlineRepository.findById(any())).thenReturn(Optional.of(airline));
        when(passengerRepository.findById(any())).thenReturn(Optional.of(passenger));
        reviewService.createOrUpdateReview(review, 1L, 2L);
        ArgumentCaptor<Review> argumentCaptor = ArgumentCaptor.forClass(Review.class);
        verify(reviewRepository).save(argumentCaptor.capture());
        Review capturedValue = argumentCaptor.getValue();
        assertEquals(review, capturedValue);
    }

    @Test
    void testRemoveReview() {
        reviewService.removeReview(1L);
        verify(reviewRepository).deleteById(1L);
    }
}