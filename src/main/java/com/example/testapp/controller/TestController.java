package com.example.testapp.controller;

import com.example.testapp.model.HumanData;
import com.example.testapp.model.RequestData;
import com.example.testapp.service.ExcelConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final ExcelConversionService conversionService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong!");
    }

    @PostMapping(path = "/calc", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> calculate(@ModelAttribute RequestData data) {
        return ResponseEntity.ok(data.stringify());
    }

    @PostMapping(path = "/exel")
    public ResponseEntity<FileSystemResource> excelSheet(@RequestBody Map<String, HumanData> data) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + new Date() + ".xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Access-Control-Allow-Origin", "*");

        File convert = conversionService.convert(data.values());
        FileSystemResource resource = new FileSystemResource(convert);
        return ResponseEntity.ok()
                       .headers(headers)
                       .contentLength(convert.length())
                       .contentType(MediaType.APPLICATION_OCTET_STREAM)
                       .body(resource);
    }
}
