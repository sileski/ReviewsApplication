package com.project.reviews.controller;

import com.project.reviews.model.Review;
import com.project.reviews.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController()
@RequestMapping("/reviews")
public class ReviewsController {
    private final ReviewsService reviewsService;

    @Autowired
    public ReviewsController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    @GetMapping("")
    ModelAndView getReviews(
            @RequestParam(value = "min_rating") int minimumRating,
            @RequestParam(value = "rating_order", defaultValue = "lowest_first") String ratingOrder,
            @RequestParam(value = "date_order", defaultValue = "oldest_first") String dateOrder,
            @RequestParam(value = "prioritize_text", required = false) boolean prioritizeText
    ) {
        List<Review> reviewsList = reviewsService.getReviews(minimumRating, ratingOrder, dateOrder, prioritizeText);
        ModelAndView modalView = new ModelAndView("reviews-table");
        modalView.addObject("reviews", reviewsList);
        return modalView;
    }
}
