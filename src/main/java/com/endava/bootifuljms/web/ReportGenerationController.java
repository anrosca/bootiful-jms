package com.endava.bootifuljms.web;

import com.endava.bootifuljms.weather.report.Report;
import com.endava.bootifuljms.weather.report.ReportType;
import com.endava.bootifuljms.weather.service.ReportGenerationEventBroadcaster;
import com.endava.bootifuljms.weather.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportGenerationController {

    private final ReportService reportService;
    private final ReportGenerationEventBroadcaster eventBroadcaster;

    @PostMapping("/{reportType}")
    public ResponseEntity<?> generateReports(@PathVariable ReportType reportType) {
        eventBroadcaster.generateReportsByTriggerType(reportType);
        return ResponseEntity.accepted()
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Report> getAll() {
        return reportService.getAll();
    }

    @GetMapping("{reportId}")
    public ResponseEntity<Report> getById(@PathVariable long reportId) {
        Report report = reportService.getById(reportId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("{reportId}/download")
    public HttpEntity<InputStreamResource> downloadReport(@PathVariable long reportId) {
        Report report = reportService.getById(reportId);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(report.getContent()));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + report.getFileName());
        return new HttpEntity<>(resource, headers);
    }

    @DeleteMapping("{reportId}")
    public ResponseEntity<?> deleteById(@PathVariable long reportId) {
        reportService.deleteById(reportId);
        return ResponseEntity.noContent().build();
    }
}
