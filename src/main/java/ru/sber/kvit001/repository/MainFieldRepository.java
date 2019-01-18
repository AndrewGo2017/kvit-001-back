package ru.sber.kvit001.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.kvit001.model.db.MainField;

public interface MainFieldRepository extends JpaRepository<MainField, Integer> {
}
