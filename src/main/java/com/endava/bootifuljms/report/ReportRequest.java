package com.endava.bootifuljms.report;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ReportRequest {
    private String reportCode;

    private ReportType triggerType;

    private String format;
}
