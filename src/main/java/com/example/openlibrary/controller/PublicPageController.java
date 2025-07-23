package com.example.openlibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PublicPageController {
	@GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping("/bookdetail")
    public String bookDetail() {
        return "bookdetail";
    }

}
