package ru.sber.kvit001.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sber.kvit001.model.db.Setting;

@RestController
@CrossOrigin
@RequestMapping("/setting")
public class SettingController extends BaseEntityController<Setting> {

}
