package com.example.bhatti.Controller;

import com.example.bhatti.Services.UserService;
import com.example.bhatti.UserPojo.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collection;

@Controller
@RequiredArgsConstructor
@RequestMapping("/homepage")
public class HomepageController {
    private  final UserService userService;


    @GetMapping("")
    public String geHomepage(Model model, Principal principal, Authentication authentication) {

        if (authentication!=null){
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                if (grantedAuthority.getAuthority().equals("Admin")) {
                    return "redirect:/admin/list";
                }
            }
        }
        model.addAttribute("feedback", new FeedbackPojo());

//        model.addAttribute("info",userService.findByEmail(principal.getName()));
        return ("homepage");
    }
    @GetMapping("/services")
    public String getServicePage(Model model , Principal principal) {

//        model.addAttribute("info",userService.findByEmail(principal.getName()));
        return "services";
    }

    @GetMapping("/about")
    public String getaboutPage(){
        return "about";
    }

    @GetMapping("/feeds")
    public String getFeedbackPage(){
        return "feeds";
    }





    @GetMapping("/rooms")
    public String getRooms(Model model, Principal principal) {
//        model.addAttribute("info",userService.findByEmail(principal.getName()));
        return "rooms";
    }

    @GetMapping("/cabbook")
    public String getCabBook() {
        return "redirect:/cab/book";
    }





    @GetMapping("/profile")
    public String getUserProfile (Integer id,Model model, Principal principal) {
//        User user= userService.getById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        model.addAttribute("update", new UserPojo());
        model.addAttribute("info",userService.findByEmail(principal.getName()));
        return "user_profile";
    }
    @PostMapping("/updateUser")
    public String updateUser(@Valid UserPojo userpojo) {
        userService.save(userpojo);
        return "redirect:/homepage/profile";
    }
    @GetMapping("/deletebooking/{id}")
    public String deleteUserBooking(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return "redirect:/homepage/profile";
    }

}
