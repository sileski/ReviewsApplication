package com.project.reviews.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reviews.model.Review;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Repository
public class ReviewsRepository {
    public List<Review> getReviews() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Review> reviews = Arrays.asList(mapper.readValue(new File("src/main/resources/reviews.json"), Review[].class));
        return reviews;
    }
}
