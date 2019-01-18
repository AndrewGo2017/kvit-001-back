package ru.sber.kvit001.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.sber.kvit001.handler.excel.ExcelDataUtil;
import ru.sber.kvit001.handler.excel.ExcelStandardHandler;
import ru.sber.kvit001.handler.pdf.PdfHandler;
import ru.sber.kvit001.model.db.MainField;
import ru.sber.kvit001.model.db.Setting;
import ru.sber.kvit001.model.db.Type;
import ru.sber.kvit001.model.misc.CommonReqs;
import ru.sber.kvit001.model.misc.FileRow;
import ru.sber.kvit001.model.misc.MainColEntity;
import ru.sber.kvit001.servise.MainFieldService;
import ru.sber.kvit001.servise.SettingService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class MainHandler {
    @Autowired
    private MainFieldService mainFieldService;

    @Autowired
    private SettingService settingService;

    private MainColEntity mainColumns;

    private Setting payReqs;

    private List<CommonReqs> handle(MultipartFile file) throws Exception {

        this.mainColumns = new MainColEntity();
        this.payReqs = settingService.get();

        List<String> columnNameListFromSettings = new ArrayList<>();
        MainField mainField = mainFieldService.get();

        String fio = mainField.getFio() == null ? "" : mainField.getFio();
        String adr = mainField.getAdr() == null ? "" : mainField.getAdr();
        String sum = mainField.getSum() == null ? "" : mainField.getSum();
        String kbk = mainField.getKbk() == null ? "" : mainField.getKbk();
        String oktmo = mainField.getOktmo() == null ? "" : mainField.getOktmo();
        String contract = mainField.getContract() == null ? "" : mainField.getContract();
        String purpose = mainField.getPurpose() == null ? "" : mainField.getPurpose();

        String fioName = mainField.getFioName() == null ? "" : mainField.getFioName();
        String adrName = mainField.getAdrName() == null ? "" : mainField.getAdrName();
        String sumName = mainField.getSumName() == null ? "" : mainField.getSumName();
        String kbkName = mainField.getKbkName() == null ? "" : mainField.getKbkName();
        String oktmoName = mainField.getOktmoName() == null ? "" : mainField.getOktmoName();
        String contractName = mainField.getContractName() == null ? "" : mainField.getContractName();
        String purposeName = mainField.getPurposeName() == null ? "" : mainField.getPurposeName();

        columnNameListFromSettings.add(fio);
        columnNameListFromSettings.add(adr);
        columnNameListFromSettings.add(sum);
        columnNameListFromSettings.add(kbk);
        columnNameListFromSettings.add(oktmo);
        columnNameListFromSettings.add(contract);
        columnNameListFromSettings.add(purpose);

        mainColumns.setAdr(adr);
        mainColumns.setFio(fio);
        mainColumns.setSum(sum);
        mainColumns.setKbk(kbk);
        mainColumns.setOktmo(oktmo);
        mainColumns.setContract(contract);
        mainColumns.setPurpose(purpose);

        mainColumns.setAdrName(adrName);
        mainColumns.setFioName(fioName);
        mainColumns.setSumName(sumName);
        mainColumns.setKbkName(kbkName);
        mainColumns.setOktmoName(oktmoName);
        mainColumns.setContractName(contractName);
        mainColumns.setPurposeName(purposeName);

        List<String> columnNameListFromSettingsWithNoEmpty = columnNameListFromSettings.stream().filter(e -> !e.isEmpty()).collect(Collectors.toList());
        InputStream inputStream = file.getInputStream();

        if (payReqs.getType() == Type.STANDARD){
            ExcelStandardHandler excelStandardHandler = new ExcelStandardHandler(inputStream, columnNameListFromSettingsWithNoEmpty);
            List<FileRow> fileRowList = excelStandardHandler.handle();
            List<String> columnNameListFromFile = excelStandardHandler.getColumnNameListFromFile();
            return ExcelDataUtil.getCommonReqsList(mainColumns, fileRowList, columnNameListFromFile);
        } else {
            return ExcelDataUtil.getCommonReqsList(inputStream);
        }

    }

    public ByteArrayOutputStream getPdf(MultipartFile file) throws Exception {
        ByteArrayOutputStream baos = new PdfHandler(getTable(file), payReqs, mainColumns).handle();
        return baos;
    }

    public List<CommonReqs> getTable(MultipartFile file) throws Exception {
        return handle(file);
    }
}
