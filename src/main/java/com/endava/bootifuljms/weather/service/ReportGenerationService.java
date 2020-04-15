package com.endava.bootifuljms.weather.service;

import com.endava.bootifuljms.weather.report.Report;
import com.endava.bootifuljms.weather.report.WeatherStation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Validated
@RequiredArgsConstructor
public class ReportGenerationService {
    private static final String REPORT_FILE_NAME_SUFFIX = "_temperature.pdf";

    private final JasperReportGenerator reportGenerator;
    private final WeatherStation weatherStation;

    public Report generateReportFor(@NotNull LocalDate date, @NotNull @Size(min = 1) String cityName) {
        try {
            byte[] reportContent = reportGenerator.generate(weatherStation, cityName, date);
            return Report.builder()
                    .content(reportContent)
                    .fileName(generateFileName(date, cityName))
                    .creationTimestamp(LocalDateTime.now())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generateFileName(LocalDate date, String cityName) {
        return cityName + "_" + date + REPORT_FILE_NAME_SUFFIX;
    }
}
