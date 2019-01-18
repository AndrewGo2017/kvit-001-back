package ru.sber.kvit001.servise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.kvit001.model.db.MainField;
import ru.sber.kvit001.repository.MainFieldRepository;

@Service
public class MainFieldService implements BaseService<MainField> {

    @Autowired
    private MainFieldRepository repository;

    @Override
    public MainField get() {
        return repository.findById(1).orElse(null);
    }

    @Override
    public MainField set(MainField entity) {
        transformEntity(entity);
        return repository.save(entity);
    }
}
