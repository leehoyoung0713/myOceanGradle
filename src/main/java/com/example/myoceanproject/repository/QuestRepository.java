package com.example.myoceanproject.repository;

import com.example.myoceanproject.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestRepository extends JpaRepository<Quest, Long> {
}
