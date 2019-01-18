package ru.sber.kvit001.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name = "setting")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Setting extends BaseEntity {

    private String name;

    private String inn;

    private String kpp;

    @Column(name = "pay_acc")
    private String payAcc;

    private String bank;

    private String bic;

    @Column(name = "cor_acc")
    private String corAcc;

    @Column(name = "bill_amount_on_page")
    private Integer billAmountOnPage;

    @Column(name = "font_size")
    private Integer fontSize;

    @Column(name = "add_info")
    private String addInfo;

    @Column(name = "qr_add_info")
    private String qrAddInfo;

    @Enumerated(EnumType.STRING)
    private Type type;

    public Setting(Integer id, String name, String inn, String kpp, String payAcc, String bank, String bic, String corAcc, Integer billAmountOnPage, Integer fontSize, String addInfo, String qrAddInfo, Type type) {
        super(id);
        this.name = name;
        this.inn = inn;
        this.kpp = kpp;
        this.payAcc = payAcc;
        this.bank = bank;
        this.bic = bic;
        this.corAcc = corAcc;
        this.billAmountOnPage = billAmountOnPage;
        this.fontSize = fontSize;
        this.addInfo = addInfo;
        this.qrAddInfo = qrAddInfo;
        this.type = type;
    }
}
