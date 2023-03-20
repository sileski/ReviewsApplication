package com.project.reviews.service;

import com.project.reviews.model.Review;
import com.project.reviews.repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
public class ReviewsService {
    private final ReviewsRepository reviewsRepository;

    @Autowired
    public ReviewsService(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    public List<Review> getReviews(int minimumRating, String ratingOrder, String dateOrder, boolean prioritizeText) {
        try {
            List<Review> reviews = reviewsRepository.getReviews();
            reviews = reviews.stream().filter(review -> review.getRating() >= minimumRating).toList();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            Comparator<Review> comparator = (o1, o2) -> 0;

            if (prioritizeText) {
                comparator = comparator.thenComparing(review -> review.getReviewText().isEmpty());
            }

            if (ratingOrder.equals("highest_first")) {
                comparator = comparator.thenComparing(Review::getRating, Comparator.reverseOrder());
            } else if (ratingOrder.equals("lowest_first")) {
                comparator = comparator.thenComparing(Review::getRating);
            }

            if (dateOrder.equals("highest_first")) {
                comparator = comparator.thenComparing(review -> ZonedDateTime.parse(review.getReviewCreatedOnDate(), dateTimeFormatter), Comparator.reverseOrder());
            } else if (dateOrder.equals("lowest_first")) {
                comparator = comparator.thenComparing(review -> ZonedDateTime.parse(review.getReviewCreatedOnDate(), dateTimeFormatter));
            }

            return reviews.stream().sorted(comparator).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
