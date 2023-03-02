package com.example.bhatti.Services.Impl;

import com.example.bhatti.Services.UserService;
import com.example.bhatti.UserPojo.*;
import com.example.bhatti.config.PasswordEncoderUtil;
import com.example.bhatti.entity.*;
import com.example.bhatti.exception.AppException;
import com.example.bhatti.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public final UserRepo userRepo;
    public final BookingRepo bookingRepo;
    public final FeedbackRepo feedbackRepo;
    @Override
    public String save(UserPojo userPojo) {
        User user = new User();
        if(userPojo.getId()!=null){
            user.setId(userPojo.getId());
        }
        user.setEmail(userPojo.getEmail());
        user.setFullname(userPojo.getFullname());
        user.setMobileNo(userPojo.getMobile_no());
        user.setPassword(PasswordEncoderUtil.getInstance().encode(userPojo.getPassword()));
        userRepo.save(user);
        return "created";
    }

    @Override
    public String update(UserPojo userPojo) {
        User user = new User();
        if(userPojo.getId()!=null){
            user.setId(userPojo.getId());
        }
        user.setEmail(userPojo.getEmail());
        user.setFullname(userPojo.getFullname());
        user.setMobileNo(userPojo.getMobile_no());
        userRepo.save(user);
        return "created";
    }


    @Override
    public String save(BookingPojo bookingPojo) {
        User relateduser = userRepo.findById(bookingPojo.getUser_id())
                .orElseThrow(() -> new AppException("Invalid id for user type", HttpStatus.BAD_REQUEST));

        Booking booking=new Booking();
        if(bookingPojo.getId()!=null){
            booking.setId(bookingPojo.getId());
        }
        booking.setFullname(bookingPojo.getFullname());
        booking.setNumber_of_people(bookingPojo.getNumber_of_people());
        booking.setMobileNo(bookingPojo.getMobile_no());
        booking.setCheckin(bookingPojo.getCheckin());
        booking.setCheckout(bookingPojo.getCheckout());
        booking.setDate(bookingPojo.getDate());
        booking.setTotal(bookingPojo.getAmount());
        booking.setRooms(bookingPojo.getRoom());
        booking.setUser_id(relateduser);
        bookingRepo.save(booking);
        return null;
    }
    @Override
    public String saveAdmin(AdminBooking adminBooking) {
//        User relateduser = userRepo.findById(bookingPojo.getUser_id())
//                .orElseThrow(() -> new AppException("Invalid id for user type", HttpStatus.BAD_REQUEST));

        Booking booking=new Booking();
        if(adminBooking.getId()!=null){
            booking.setId(adminBooking.getId());
        }
        booking.setFullname(adminBooking.getFullname());
        booking.setNumber_of_people(adminBooking.getNumber_of_people());
        booking.setMobileNo(adminBooking.getMobile_no());
        booking.setCheckin(adminBooking.getCheckin());
        booking.setCheckout(adminBooking.getCheckout());
        booking.setDate(adminBooking.getDate());
        booking.setTotal(adminBooking.getAmount());
        booking.setRooms(adminBooking.getRoom());
//        booking.setUser_id(relateduser);
        bookingRepo.save(booking);
        return null;
    }


    @Override
    public String submitFeedback(FeedbackPojo feedbackPojo){
        Feedback feedback = new Feedback();
        feedback.setFullname(feedbackPojo.getFullname());
        feedback.setEmail(feedbackPojo.getEmail());
        feedback.setSubject(feedbackPojo.getSubject());
        feedback.setMessage(feedbackPojo.getMessage());
        feedbackRepo.save(feedback);
        return "sent";
    }

    public List<Feedback> fetchAllFeedback() {
        return this.feedbackRepo.findAll();
    }

    @Override
    public List<Booking> findApplicationById(Integer id) {
        return bookingRepo.findApplicationById(id);
    }

    @Override
    public Booking fetchById(Integer id) {
        return bookingRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));
    }

    @Override
    public User getById(Integer id) {
        return userRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));
    }


    public List<Booking> fetchAll(){
        return this.bookingRepo.findAll();
    }


    @Override
    public void deleteById(Integer id) {
        bookingRepo.deleteById(id);
    }

    @Override
    public void deleteFeedback(Integer id) {
        feedbackRepo.deleteById(id);
    }

    @Override
    public void deletecomment(Integer id) {

    }


    @Override
    public User findByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new AppException("Invalid User email", HttpStatus.BAD_REQUEST));
        return user;
    }
}
