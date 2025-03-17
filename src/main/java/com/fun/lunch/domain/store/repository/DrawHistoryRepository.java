package com.fun.lunch.domain.store.repository;

import com.fun.lunch.domain.store.entity.DrawHistory;
import com.fun.lunch.domain.store.entity.Personal;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrawHistoryRepository extends JpaRepository<DrawHistory, Long> {

    List<DrawHistory> findTop30ByPersonal(Personal personal, Sort sort);
}
