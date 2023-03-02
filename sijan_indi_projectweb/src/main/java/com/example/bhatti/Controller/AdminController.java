package com.example.bhatti.Controller;

import com.example.bhatti.Services.*;

import com.example.bhatti.UserPojo.*;
import com.example.bhatti.entity.*;
import com.example.bhatti.repo.BookingRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private  final BookingRepo bookingRepo;
    private  final CabService cabService;


    @GetMapping("/list")
    public String getUserList( Model model) {
        List<Booking> bookings = userService.fetchAll();
        model.addAttribute("bookinglist", bookings);
        return "viewCustomerlist";
    }



    @GetMapping("/contactfetch")
    public String getContactAdmin(Model model) {
        List<Feedback> feedbacks = userService.fetchAllFeedback();
        model.addAttribute("feedback", feedbacks);
        return "Admin/ViewContactandFeedback";
    }

    @GetMapping("/deletefeed/{id}")
    public String deleteFeedback(@PathVariable("id") Integer id) {
        userService.deleteFeedback(id);
        return "redirect:/admin/contactfetch";
    }



    @GetMapping("/newbooking")
    public String BookHotel(Model model) {
        model.addAttribute("newBooking", new AdminBooking());
        return "newbookings";
    }



    @PostMapping("/savenewbook")
    public String saveBooking(@Valid AdminBooking adminBooking) {
        userService.saveAdmin(adminBooking);
        return "redirect:/admin/list";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        Booking booking = userService.fetchById(id);
        model.addAttribute("newBooking", new AdminBooking(booking));
        return "newbookings";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return "redirect:/admin/list";
    }


    @GetMapping("/bill/{id}")
    public String getBill(@PathVariable("id") Integer id, Model model) {
        Booking booking = userService.fetchById(id);
        model.addAttribute("bill", new AdminBooking(booking));
        return "PrintBill";
    }




    @GetMapping("/cabBookingByCustomer")
    public String getCabBooking(Model model) {
        List<Cab> cabs = cabService.findAll();
        model.addAttribute("cab", cabs);
        return "Admin/ViewCabBooking";
    }


    @GetMapping("/deletecab/{id}")
    public String detCab(@PathVariable("id") Integer id) {
        cabService.deleteById(id);
        return "redirect:/admin/cabBookingByCustomer";
    }


    public Map<String, String> validateRequest(BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return null;
        }
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;

    }

//    ---------------------------------------
//    -------  Admin Profile ----------------
//    ---------------------------------------

    @GetMapping("/admin_profile")
    public String getAdminProfile(Model model){
        return "adminProfile";
    }
}
