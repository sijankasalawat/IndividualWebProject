package com.example.hotel_indi_project;

import com.example.hotel_indi_project.entity.Feedback;
import com.example.hotel_indi_project.repo.FeedbackRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FeedbackRepositoryTest {

    @Autowired
    private FeedbackRepo feedbackRepo;


    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveContactTest() {


        Feedback feedback = Feedback.builder()
                .email("sijankasalawat@gmail.com")
                .message("great ")
                .build();

        feedbackRepo.save(feedback);

        Assertions.assertThat(feedback.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getContactTest() {

        Feedback feedback = Feedback.builder()
                .email("joshiroshan052@gmail.com")
                .message("wow")
                .build();

        feedbackRepo.save(feedback);


        Feedback feedbackCreated = feedbackRepo.findById(feedback.getId()).get();
        Assertions.assertThat(feedbackCreated.getId()).isEqualTo(feedback.getId());

    }

    @Test
    @Order(3)
    public void getListOfContactTest(){
        Feedback feedback = Feedback.builder()
                .email("joshiroshan052@gmail.com")
                .message("wow")
                .build();
        feedbackRepo.save(feedback);
        List<Feedback> User = feedbackRepo.findAll();
        Assertions.assertThat(User.size()).isGreaterThan(0);
    }


    @Test
    @Order(4)
    public void updateContactTest(){

        Feedback feedback = Feedback.builder()
                .email("joshiroshan052@gmail.com")
                .message("wow")
                .build();

        feedbackRepo.save(feedback);

        Feedback feedback1  = feedbackRepo.findById(feedback.getId()).get();

        feedback1.setEmail("joshiroshan052@gmail.com");

        Feedback feedbackUpdated  = feedbackRepo.save(feedback);

        Assertions.assertThat(feedbackUpdated.getEmail()).isEqualTo("joshiroshan052@gmail.com");

    }

    @Test
    @Order(5)
    public void deleteContactTest(){

        Feedback feedback = Feedback.builder()
                .email("joshiroshan052@gmail.com")
                .message("wow")
                .build();

        feedbackRepo.save(feedback);

//        @Query(value = "SELECT * from")

        Feedback feedback1 = feedbackRepo.findById(feedback.getId()).get();
        feedbackRepo.delete(feedback1);

        Feedback feedback2 = null;
        Optional<Feedback> optionalFeedback = feedbackRepo.findFeedbackByEmail("joshiroshan052@gmail.com");
        if(optionalFeedback.isPresent()){
            feedback2 = optionalFeedback.get();
        }

        Assertions.assertThat(feedback2).isNull();
//        Assertions.assertThat(User1.getId()).isNull();
    }


}
