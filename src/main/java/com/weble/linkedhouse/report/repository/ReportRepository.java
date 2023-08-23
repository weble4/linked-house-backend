package com.weble.linkedhouse.report.repository;

import com.weble.linkedhouse.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
