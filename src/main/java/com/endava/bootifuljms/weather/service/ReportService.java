package com.endava.bootifuljms.weather.service;

import com.endava.bootifuljms.weather.report.Report;
import com.endava.bootifuljms.weather.report.ReportGenerationRequest;
import com.endava.bootifuljms.weather.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;

    private final ReportGenerationService reportGenerationService;

    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    public Report getById(long reportId) {
        return reportRepository.findById(reportId).orElse(null);
    }

    public void deleteById(long reportId) {
        reportRepository.deleteById(reportId);
    }

    public Report generateReport(ReportGenerationRequest request) {
        Report report = reportGenerationService.generateReportFor(LocalDate.parse(request.getDate()), request.getCity());
        reportRepository.save(report);
        return report;
    }
}
