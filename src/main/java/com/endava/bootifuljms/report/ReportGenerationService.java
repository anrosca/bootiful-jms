package com.endava.bootifuljms.report;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportGenerationService {

    private final ReportConfigRepository reportConfigRepository;

    private final ApplicationEventPublisher eventPublisher;

    private final JmsTemplate jmsTemplate;

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
//        eventPublisher.publishEvent(reportRequest);

        jmsTemplate.convertAndSend("REPORT_CONTENT.Q", reportRequest);
    }
}
