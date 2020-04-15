package com.endava.bootifuljms.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.endava.bootifuljms.weather.report.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
