package com.endava.bootifuljms.weather.repository;

import com.endava.bootifuljms.weather.report.ReportType;
import com.endava.bootifuljms.weather.report.ReportConfig;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportConfigRepository extends CrudRepository<ReportConfig, Long> {

    List<ReportConfig> findByTriggerType(ReportType triggerType);
}