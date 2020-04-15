package com.endava.bootifuljms.weather.service;

import com.endava.bootifuljms.weather.report.ReportConfig;
import com.endava.bootifuljms.weather.report.ReportGenerationRequest;
import com.endava.bootifuljms.weather.report.ReportType;
import com.endava.bootifuljms.weather.repository.ReportConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ReportGenerationEventBroadcaster {

    private final ReportConfigRepository reportConfigRepository;
//    private final ApplicationEventPublisher eventPublisher;
    private final JmsTemplate jmsTemplate;

    public void generateReportsByTriggerType(ReportType triggerType) {
        reportConfigRepository.findByTriggerType(triggerType)
                .forEach(this::generateReport);
    }

    private void generateReport(ReportConfig config) {
        ReportGenerationRequest request = ReportGenerationRequest.builder()
                .city(config.getCity().getName())
                .date(LocalDate.now().toString())
                .build();
//        eventPublisher.publishEvent(request);
        jmsTemplate.convertAndSend("REPORTS.Q", request);
    }
}
