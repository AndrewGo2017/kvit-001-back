package ru.sber.kvit001.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.kvit001.model.db.Setting;

public interface SettingRepository extends JpaRepository<Setting, Integer> {
}
