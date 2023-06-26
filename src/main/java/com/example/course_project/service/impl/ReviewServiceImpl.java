package com.example.course_project.service.impl;
import com.example.course_project.entity.Airline;
import com.example.course_project.entity.Passenger;
import com.example.course_project.entity.Review;
import com.example.course_project.repository.AirlineRepository;
import com.example.course_project.repository.PassengerRepository;
import com.example.course_project.repository.ReviewRepository;
import com.example.course_project.service.ReviewService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private AirlineRepository airlineRepository;
    private PassengerRepository passengerRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, AirlineRepository airlineRepository, PassengerRepository passengerRepository) {
        this.reviewRepository = reviewRepository;
        this.airlineRepository = airlineRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Review createReview(Long airlineId, Long passengerId, double checkInSpeed, double state_of_salon, double crew_work, double quality_of_flight_meals, double overall_score, String remark) {
        Airline airline= airlineRepository.findById(airlineId).orElseThrow(() -> new EntityNotFoundException("Airline with id " + airlineId + " Not Found"));
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(() -> new EntityNotFoundException("Passenger with id " + passengerId + " Not Found"));
        return reviewRepository.save(Review.builder().airline(airline).passenger(passenger).checkInSpeed(checkInSpeed).state_of_salon(state_of_salon).crewWork(crew_work).qualityOfFlightMeals(quality_of_flight_meals). overallScore(overall_score).remark(remark).build());
    }

    @Override
    public Review findReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Review with id " + id + " Not Found"));
    }

    @Override
    public List<Review> fetchAll() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> fetchReviewsForAirline(Long airlineId) {
        return reviewRepository.getReviewsByAirlineId(airlineId);
    }

    @Override
    public Review createOrUpdateReview(Review review, Long airlineId, Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(()->new EntityNotFoundException("Passenger with id " + passengerId + " Not Found"));
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(()->new EntityNotFoundException("Airline with id " + airlineId + " Not Found"));
        review.setOverallScore((review.getCheckInSpeed() + review.getState_of_salon() + review.getCrewWork() + review.getQualityOfFlightMeals()) / 4);
        review.setPassenger(passenger);
        review.setAirline(airline);
        if (review.getRemark() == null) review.setRemark("");
        double rating = 0;
        List<Review> reviews = reviewRepository.findAll().stream().filter(review1 -> review1.getAirline().getId() == airlineId).collect(Collectors.toList());
        for (Review reviewDB : reviews) rating += reviewDB.getOverallScore();
        rating += review.getOverallScore();
        rating /= reviews.size() + 1;
        airline.setRating(Double.parseDouble(String.format("%.2f", rating).replace(",", ".")));
        airlineRepository.save(airline);
        return reviewRepository.save(review);
    }

    @Override
    public void removeReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
