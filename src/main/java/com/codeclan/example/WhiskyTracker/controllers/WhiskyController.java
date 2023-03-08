package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Whisky;
import com.codeclan.example.WhiskyTracker.repositories.WhiskyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WhiskyController {
@Autowired
    WhiskyRepository whiskyRepository;

@GetMapping(value = "/whiskies/{id}")
    public ResponseEntity getWhisky(@PathVariable Long id) {
    return new ResponseEntity<>(whiskyRepository.findById(id), HttpStatus.OK);
}
@PostMapping(value = "/whiskies")
    public ResponseEntity<Whisky> postWhisky(@RequestBody Whisky whisky) {
    whiskyRepository.save(whisky);
    return new ResponseEntity<>(whisky, HttpStatus.CREATED);
}
@GetMapping(value = "/whiskies")
    public ResponseEntity<List<Whisky>> findWhiskiesByYear(
            @RequestParam(name="year", required = false) Integer year,
            @RequestParam(name = "distilleryName", required = false) String distilleryName,
            @RequestParam(name ="age", required = false) Integer age) {

    if (age != null && distilleryName != null){
        return new ResponseEntity<>(whiskyRepository.findWhiskiesByDistilleryNameAndAge(distilleryName, age), HttpStatus.OK);
    }
    if (year != null) {
        return new ResponseEntity<>(whiskyRepository.findWhiskiesByYear(year), HttpStatus.OK);
    }
    if (distilleryName != null) {
        return new ResponseEntity<>(whiskyRepository.findWhiskiesByDistilleryName(distilleryName), HttpStatus.OK);
    }
    if (age != null) {
        return new ResponseEntity<>(whiskyRepository.findWhiskiesByAge(age), HttpStatus.OK);
    }
    return new ResponseEntity<>(whiskyRepository.findAll(), HttpStatus.OK);
}
}
