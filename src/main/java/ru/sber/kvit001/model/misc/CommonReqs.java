package ru.sber.kvit001.model.misc;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommonReqs {
    String contract;
    String fio;
    String adr;
    String purpose;
    Double sum;
    String kbk;
    String oktmo;
}