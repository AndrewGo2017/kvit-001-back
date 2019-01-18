package ru.sber.kvit001.model.misc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MainColEntity {
    public String fio;
    public String adr;
    public String sum;
    public String contract;
    public String purpose;
    public String kbk;
    public String oktmo;

    public String adrName;
    public String fioName;
    public String sumName;
    public String contractName;
    public String purposeName;
    public String kbkName;
    public String oktmoName;
}
