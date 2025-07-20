package com.example.openlibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PageController {
  @GetMapping("/")
  public String index() {
    return "index";
  }
  
  @GetMapping("/home")
  public String home() {
    return "home";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/signup")
  public String signup() {
    return "signup";
  }
  @GetMapping("/bookdetail")
  public String bookdetail() {
    return "bookdetail";
  }
  @GetMapping("/category")
  public String category() {
    return "category";
  }
  @GetMapping("/category/fiction")
  public String fictionCategory() {
      return "fiction";
  }

  @GetMapping("/category/non-fiction")
  public String nonFictionCategory() {
      return "non-fiction";
  }

  @GetMapping("/category/children")
  public String childrenCategory() {
      return "children";
  }
  @GetMapping("/search")
  public String search(@RequestParam(name = "query", required = false) String query, Model model) {
      model.addAttribute("query", query);
      return "search";
  }

}
