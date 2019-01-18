package ru.sber.kvit001.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.sber.kvit001.model.db.BaseEntity;
import ru.sber.kvit001.servise.BaseService;

public class BaseEntityController <Entity extends BaseEntity> {

    @Autowired
    private BaseService<Entity> service;

    @GetMapping
    public Entity get() throws InterruptedException {
            return service.get();
    }

    @PutMapping
    public Entity set(Entity entity) throws Exception {
        return service.set(entity);
    }
}