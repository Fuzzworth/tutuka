package com.tutuka.project.Tutuka.controller;

import com.tutuka.project.Tutuka.service.MatchingService;
import com.tutuka.project.Tutuka.service.MatchingServiceImpl;
import com.tutuka.project.Tutuka.service.StorageService;
import com.tutuka.project.Tutuka.service.StorageServiceImpl;
import com.tutuka.project.Tutuka.util.ParseAndCompare;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class MainController {

    private StorageService storageService;
    private MatchingService matchingService;


    public MainController(StorageServiceImpl storageService, MatchingServiceImpl matchingService) {
        this.storageService = storageService;
        this.matchingService = matchingService;
    }

    @GetMapping("/")
    public String home() {

        return "index";
    }

    @PostMapping("/csv")
    @ResponseBody
    public ParseAndCompare uploadFile(@RequestParam("file_one") MultipartFile fileOne, @RequestParam("file_two") MultipartFile fileTwo) throws IOException {
        String nameOne = storageService.store(fileOne);
        String nameTwo = storageService.store(fileTwo);

        ParseAndCompare compareClass = matchingService.match(nameOne,nameTwo);

        return compareClass;
    }

}
