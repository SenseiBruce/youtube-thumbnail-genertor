package com.thumbnailgen.controller;

import com.thumbnailgen.dto.OutputFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/output-format")
public class OutputFormatController {

    @GetMapping
    public OutputFormat get() {
        return new OutputFormat("png", MediaType.IMAGE_PNG_VALUE);
    }
}
