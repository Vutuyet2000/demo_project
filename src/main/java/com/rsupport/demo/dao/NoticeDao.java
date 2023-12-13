package com.rsupport.demo.dao;

import com.rsupport.demo.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface NoticeDao extends JpaRepository<Notice, UUID> {
    public List<Notice> findByStartDateBeforeAndEndDateAfter(Date startDate, Date endDate);

    default List<Notice> findByStartDateBeforeAndEndDateAfter(Date givenDate) {
        return findByStartDateBeforeAndEndDateAfter(givenDate, givenDate);
    }

    public List<Notice> findByActiveTrue();

    public Notice findByIdAndActiveTrue(UUID id);
}
