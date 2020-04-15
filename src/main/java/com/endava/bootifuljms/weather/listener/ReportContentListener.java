package com.endava.bootifuljms.weather.listener;

import com.endava.bootifuljms.weather.report.ReportGenerationRequest;
import com.endava.bootifuljms.weather.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportContentListener {

    private final ReportService reportService;

//    @Async
//    @EventListener
    @JmsListener(destination = "REPORTS.Q")
    public void onReportContentRequest(ReportGenerationRequest request) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Generating content for report {}", request.getCity());
        reportService.generateReport(request);
        log.info("Content for report {} was generated", request.getCity());
    }
}
