package com.example.bhatti.Controller;


import com.example.bhatti.Services.UserService;
import com.example.bhatti.UserPojo.*;

import com.example.bhatti.UserPojo.BookingPojo;
import com.example.bhatti.UserPojo.UserPojo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("user", new UserPojo());
        return "register";
    }

    @PostMapping("/save")
    public String saveUser(@Valid UserPojo userpojo) {
        userService.save(userpojo);
        return "login";
    }


    @GetMapping("/booking")
    public String BookHotel(Integer id, Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
//        User user= userService.findByEmail(principal.getName());
        model.addAttribute("booking", new BookingPojo());
//        model.addAttribute("userdata",userService.getById(id));
        model.addAttribute("info",userService.findByEmail(principal.getName()));

//        model.addAttribute("data",user);
//        model.addAttribute("join",new UserPojo(user));
        return "booking";
    }


    @PostMapping("/savebook")
    public String saveBooking(@Valid BookingPojo bookingPojo) {
        userService.save(bookingPojo);
        return "redirect:/homepage";
    }
//
//-------
//    rating save
//
//
    @PostMapping("/savefeedback")
    public String getFeedback(@Valid FeedbackPojo feedbackPojo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        userService.submitFeedback(feedbackPojo);
        return "redirect:/homepage";
    }


    @GetMapping("/pickup_request")
    public String getPickupPage() {
        return ("picked_up");
    }


}
