package ru.sber.kvit001.model.misc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileStructure {
    private String fio;

    private String adr;

    private String sum;

    private String contract;

    private String purpose;

    private String kbk;

    private String oktmo;
}
