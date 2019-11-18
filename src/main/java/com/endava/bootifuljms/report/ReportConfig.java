package com.endava.bootifuljms.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report_config")
class ReportConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "report_code")
    private String reportCode;

    @Column(name = "trigger_type")
    @Enumerated(EnumType.STRING)
    private ReportType triggerType;

    @Column(name = "report_format")
    private String reportFormat;
}
