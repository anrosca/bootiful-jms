package com.endava.bootifuljms.report;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportGenerationService {

    private final ReportConfigRepository reportConfigRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void generateReportsByTriggerType(ReportType triggerType) {
        reportConfigRepository.findByTriggerType(triggerType)
                .forEach(this::generateReport);
    }

    private void generateReport(ReportConfig config) {
        ReportRequest reportRequest = ReportRequest.builder()
                .reportCode(config.getReportCode())
                .triggerType(config.getTriggerType())
                .format(config.getReportFormat())
                .build();
        eventPublisher.publishEvent(reportRequest);
    }
}
