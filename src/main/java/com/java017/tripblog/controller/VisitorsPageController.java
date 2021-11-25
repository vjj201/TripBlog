package com.java017.tripblog.controller;

import com.java017.tripblog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VisitorsPageController {
     private final UserService userService;

    public VisitorsPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/visitorsSpace/{userName}")
    public String visitorsSpace(@PathVariable String userName, Model model){
        model.addAttribute("userShow",userService.findUserByUsername(userName));
        return "Visitors/Visitors_space";
    }

    @GetMapping("/visitorsArticle/{userName}")
    public String visitorsArticle(@PathVariable String userName, Model model){
        model.addAttribute("userShow",userService.findUserByUsername(userName));
        return "Visitors/Visitors_article";
    }

    @GetMapping("/visitorsCollect/{userName}")
    public String visitorsCollect(@PathVariable String userName, Model model){
        model.addAttribute("userShow",userService.findUserByUsername(userName));
        return "Visitors/Visitors_collection";
    }
}
