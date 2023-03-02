package com.example.bhatti.Services;
import com.example.bhatti.UserPojo.*;
import com.example.bhatti.entity.Booking;
import com.example.bhatti.entity.Feedback;
import com.example.bhatti.entity.User;

import java.util.List;

public interface UserService {

    List<Booking> fetchAll();



    String save(UserPojo userPojo);
    String update(UserPojo userPojo);

    Booking fetchById(Integer id);
    User getById(Integer id);
    String save(BookingPojo bookingPojo);
    String saveAdmin(AdminBooking adminBooking);

    String submitFeedback(FeedbackPojo feedbackPojo);
    List<Feedback> fetchAllFeedback();
    List<Booking> findApplicationById(Integer id);



    //    Contact fetchById(Integer id);
    void deleteById(Integer id);
    void deleteFeedback(Integer id);
    void deletecomment(Integer id);

    User findByEmail(String email);
//    String save(BlogPojo blogPojo);
//    UserPojo findByEmail(String email);

//    List<Booking> getByKeyword( String keyword);
//    Page<Booking> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);


}
