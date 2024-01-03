package com.ezen.guru.controller.export;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("export")
public class ExportController {

    @Autowired
    private ExportService exportService;
}
