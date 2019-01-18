package ru.sber.kvit001.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "main_field")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class MainField extends BaseEntity {
    private String fio;

    private String adr;

    private String sum;

    private String contract;

    private String purpose;

    private String kbk;

    private String oktmo;


    @Column(name = "fio_name")
    private String fioName;

    @Column(name = "adr_name")
    private String adrName;

    @Column(name = "sum_name")
    private String sumName;

    @Column(name = "contract_name")
    private String contractName;

    @Column(name = "purpose_name")
    private String purposeName;

    @Column(name = "kbk_name")
    private String kbkName;

    @Column(name = "oktmo_name")
    private String oktmoName;

    public MainField(Integer id, String fio, String adr, String sum, String contract, String purpose, String kbk, String oktmo, String fioName, String adrName, String sumName, String contractName, String purposeName, String kbkName, String oktmoName) {
        super(id);
        this.fio = fio;
        this.adr = adr;
        this.sum = sum;
        this.contract = contract;
        this.purpose = purpose;
        this.kbk = kbk;
        this.oktmo = oktmo;
        this.fioName = fioName;
        this.adrName = adrName;
        this.sumName = sumName;
        this.contractName = contractName;
        this.purposeName = purposeName;
        this.kbkName = kbkName;
        this.oktmoName = oktmoName;
    }
}
