package com.endava.bootifuljms.report;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface ReportConfigRepository extends CrudRepository<ReportConfig, Long> {

    List<ReportConfig> findByTriggerType(ReportType triggerType);
}