package com.endava.bootifuljms.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportGenerationController {

    private final ReportGenerationService reportGenerationService;

    @PostMapping("/reports/{reportType}")
    public ResponseEntity generateReports(@PathVariable ReportType reportType) {
        reportGenerationService.generateReportsByTriggerType(reportType);
        return ResponseEntity.accepted()
                .build();
    }
}
