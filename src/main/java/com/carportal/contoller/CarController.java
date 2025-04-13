package com.carportal.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/car")
public class CarController {
@GetMapping
    public String carController(){
        // TODO: Implement car-related logic here
        return "Car controller called";
    }
}
