package ru.sber.kvit001.handler.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ru.sber.kvit001.handler.qr.QrHandler;
import ru.sber.kvit001.handler.qr.QrStructure;
import ru.sber.kvit001.model.db.Setting;
import ru.sber.kvit001.model.misc.CommonReqs;
import ru.sber.kvit001.model.misc.MainColEntity;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfHandler extends BasePdfHandler {
    private List<CommonReqs> commonReqsList;
    private Setting payReqs;
    private MainColEntity mainColumns;

    public PdfHandler(List<CommonReqs> commonReqsList, Setting payReqs, MainColEntity mainColumns) {
        super(payReqs, mainColumns);
        this.commonReqsList = commonReqsList;
        this.payReqs = payReqs;
        this.mainColumns = mainColumns;
    }

    public ByteArrayOutputStream handle() throws Exception {
        Document document = new Document();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        int billQuantity = 1;
        if (payReqs.getBillAmountOnPage() != null && payReqs.getBillAmountOnPage() > 0) {
            billQuantity = payReqs.getBillAmountOnPage();
        }

        int rowCount = 0;

        try {
            for (CommonReqs row : commonReqsList) {
                rowCount++;

                PdfPTable tableL0 = getTableL0(2.5f, 7.5f);

                PdfPTable tableL1 = getTableL1(4.5f, 1, 4.5f);

                if (!mainColumns.getKbk().isEmpty() && !mainColumns.getOktmo().isEmpty()) {

                    tableL1.addCell(new PdfCellBuilder(row.getKbk(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
                    tableL1.addCell(new PdfCellBuilder(" ", font10).borderWidth(0).build());
                    tableL1.addCell(new PdfCellBuilder(row.getOktmo(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());

                    tableL1.addCell(new PdfCellBuilder("("+mainColumns.getKbkName().toLowerCase()+")", font7).border(0).borderWidth(0).horizontalAlignment(1).build());
                    tableL1.addCell(new PdfCellBuilder(" ", font7).border(0).borderWidth(0).horizontalAlignment(0).build());
                    tableL1.addCell(new PdfCellBuilder("("+mainColumns.getOktmoName().toLowerCase()+")", font7).border(0).borderWidth(0).horizontalAlignment(1).build());
                }

                //main reqs
                PdfPTable tableL2 = getTableL2(row, 3, 7);


                //QR
                Image imgQR = Image.getInstance(QrHandler.handle(
                        new QrStructure(payReqs.getName(), payReqs.getPayAcc(), payReqs.getBank(), payReqs.getBic(), payReqs.getCorAcc(), payReqs.getInn(), payReqs.getKpp(), row.getSum(), row.getFio(), row.getAdr(), payReqs.getQrAddInfo(), row.getKbk(), row.getOktmo(), row.getContract(), row.getPurpose())
                ));
                imgQR.setWidthPercentage(70);
                imgQR.setAlignment(1);
                PdfPCell splitter3 = new PdfCellBuilder(" ").border(Rectangle.RIGHT).padding(0).build();

                tableL0.setWidthPercentage(97);

                tableL0.addCell(new PdfCellBuilder(imgQR).border(Rectangle.RIGHT).rowSpan(1).horizontalAlignment(1).verticalAlignment(1).padding(0).build());
                tableL0.addCell(tableL1);

                tableL0.addCell(splitter3);
                tableL0.addCell(tableL2);

                PdfPTable borderTable = new PdfPTable(1);
                borderTable.setWidthPercentage(100);
                borderTable.addCell(new PdfCellBuilder(tableL0).borderWidth(1).build());

                document.add(borderTable);

                PdfPTable emptyTable = new PdfPTable(1);
                emptyTable.addCell(new PdfCellBuilder(" ", font3).border(0).build());
                document.add(emptyTable);

                if (rowCount % billQuantity == 0)
                    document.newPage();

            }
        } catch (Exception e) {
            throw new Exception("ряд  " + rowCount + " ; " + e.toString());
        }

        document.close();

        return baos;
    }

}
