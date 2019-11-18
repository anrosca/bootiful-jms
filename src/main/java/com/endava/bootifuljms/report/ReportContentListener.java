package com.endava.bootifuljms.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ReportContentListener {

//    @Async("reportContentTaskExecutor")
//    @EventListener
    @JmsListener(destination = "REPORT_CONTENT.Q", concurrency = "1-2")
    public void onReportContentRequest(ReportRequest reportRequest) {
        log.info("Generating {} content for report {}", reportRequest.getFormat(), reportRequest.getReportCode());
        byte[] reportContent = generateReport(reportRequest);
        log.info("{} content for report {} was generated", reportRequest.getFormat(), reportRequest.getReportCode());
    }

    private byte[] generateReport(ReportRequest reportRequest) {
        sleep();
        return new byte[]{1, 2, 3};
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
