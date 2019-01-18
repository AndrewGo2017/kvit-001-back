package ru.sber.kvit001.servise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.kvit001.model.db.Setting;
import ru.sber.kvit001.repository.SettingRepository;

@Service
public class SettingService implements BaseService<Setting> {

    @Autowired
    private SettingRepository repository;

    @Override
    public Setting get() {
        return repository.findById(1).orElse(null);
    }

    @Override
    public Setting set(Setting entity) {
        transformEntity(entity);
        return repository.save(entity);
    }
}
