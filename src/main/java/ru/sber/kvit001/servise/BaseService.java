package ru.sber.kvit001.servise;

import ru.sber.kvit001.model.db.BaseEntity;

public interface BaseService <Entity extends BaseEntity> {
    Entity get();
    
    Entity set(Entity entity);

    default Entity transformEntity(Entity entity){
         entity.setId(1);
         return entity;
    }

}