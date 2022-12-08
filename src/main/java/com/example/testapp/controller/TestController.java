package com.example.testapp.controller;

import com.example.testapp.model.RequestData;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        return ResponseEntity.ok("pong!");
    }

    @PostMapping(path = "/calc", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE} )
    public ResponseEntity<String> calculate(@ModelAttribute RequestData data){
        return ResponseEntity.ok(data.stringify());
    }
}
