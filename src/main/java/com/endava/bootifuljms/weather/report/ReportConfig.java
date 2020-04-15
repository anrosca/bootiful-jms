package com.endava.bootifuljms.weather.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report_config")
public class ReportConfig {

    @Id
    @SequenceGenerator(name = "report_config_sequence_generator",
            sequenceName = "report_config_sequence", allocationSize = 1)
    @GeneratedValue(generator = "report_config_sequence_generator")
    private Long id;

    @OneToOne
    private City city;

    @Column(name = "trigger_type")
    @Enumerated(EnumType.STRING)
    private ReportType triggerType;
}
