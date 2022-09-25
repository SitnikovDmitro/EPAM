package com.ra.service;

import com.ra.model.entity.Cheque;
import com.ra.model.entity.ChequeLine;
import com.ra.exceptions.DBException;
import com.ra.repository.ChequeLineRepository;
import com.ra.repository.ChequeRepository;
import com.ra.repository.ProductRepository;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChequeService {
    @Autowired
    private ChequeRepository chequeRepository;
    @Autowired
    private ChequeLineRepository chequeLineRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ChequeLineService chequeLineService;

    /**
     * Extract cheques from database
     * @param fromPrice from price
     * @param toPrice to price
     * @param sortCriteria sort criteria (by price or by creation time)
     * @param cheques cheques
     * @param chequeLines cheque lines
     */
    public void findCheques(String fromPrice, String toPrice, String sortCriteria, ArrayList<Cheque> cheques, ArrayList<ArrayList<ChequeLine>> chequeLines) throws SQLException {
        List<Cheque> list1;
        int from = 0, to = Integer.MAX_VALUE;

        try {
            from = (int)(Double.parseDouble(fromPrice)*100.0);
        } catch (NumberFormatException | NullPointerException ignored) { }

        try {
            to = (int)(Double.parseDouble(toPrice)*100.0);
        } catch (NumberFormatException | NullPointerException ignored) { }

        if (sortCriteria != null && sortCriteria.equals("price")) {
            list1 = chequeRepository.findChequesSortedByPrice(from, to);
        } else {
            list1 = chequeRepository.findChequesSortedByCreationTime(from, to);
        }

        cheques.clear();
        chequeLines.clear();
        for (Cheque cheque : list1) {
            cheques.add(cheque);
            chequeLines.add(new ArrayList<>(chequeLineRepository.findChequeLinesByChequeId(cheque.getId())));
        }
    }

    /**
     * Paginate cheques
     * @param page number of current page
     * @param source1 cheques extracted from database
     * @param source1 cheque lines extracted from database
     * @param dest1 cheques to be shown on page
     * @param dest2 cheque lines to be shown on page
     * @param pages references to other pages
     */
    public void findCheques(String page, ArrayList<Cheque> source1, ArrayList<ArrayList<ChequeLine>> source2,  ArrayList<Cheque> dest1, ArrayList<ArrayList<ChequeLine>> dest2, ArrayList<Integer> pages) {
        dest1.clear();
        dest2.clear();
        if (source1.isEmpty()) return;

        final int amount = 4;
        int pagesCount = source1.size()/amount;
        if (source1.size()%amount != 0) {
            pagesCount++;
        }

        int p = 1;
        try {
            p = Integer.parseInt(page);
            if (p < 1) p = 1;
            if (p > pagesCount) p = pagesCount;
        } catch (NumberFormatException ignored) {}


        for (int i = Math.max((p-1)*amount, 0); i < p*amount && i < source1.size(); i++) {
            dest1.add(source1.get(i));
            dest2.add(source2.get(i));
        }

        if (p != 1) pages.add(1);
        if (p > 2) pages.add(p-1);
        pages.add(p);
        if (pagesCount > 1 && p < pagesCount-1) pages.add(p+1);
        if (pagesCount > 0 && p != pagesCount) pages.add(pagesCount);
    }

    /**
     * Creates cheque in database
     * @param chequeLines list of cheque lines
     */
    public void completeCheque(ArrayList<ChequeLine> chequeLines) throws DBException {
        if (chequeLines.isEmpty()) return;

        Cheque cheque = new Cheque(null, 0, 0, Date.valueOf(LocalDate.now()));
        cheque.setPrice(chequeLineService.computePrice(chequeLines));

        try {
            chequeRepository.save(cheque);
            for (ChequeLine chequeLine : chequeLines) {
                chequeLine.getProduct().setTotalAmount(chequeLine.getProduct().getTotalAmount() - chequeLine.getAmount());
                if (chequeLine.getProduct().getTotalAmount() < 0) chequeLine.getProduct().setTotalAmount(0);
                chequeLine.setCheque(cheque);
                productRepository.save(chequeLine.getProduct());
                chequeLineRepository.save(chequeLine);
            }
            chequeLines.clear();
        } catch (Exception exception) {
            throw new DBException(exception);
        }
    }

    /**
     * Creates report
     * @param reportType type of report (X or Z)
     * @return number of created report
     */
    public int createReport(String reportType) throws DBException {
        try {
            final String path = "src/main/webapp/files/reports/";

            int free = 1;
            for (String filename : new File(path).list()) {
                try {
                    int curr = Integer.parseInt(filename.substring(0, filename.length()-5));
                    if (free <= curr) free = curr + 1;
                } catch (IndexOutOfBoundsException | NumberFormatException ignored) { }
            }

            int total = 0;
            List<Cheque> cheques = chequeRepository.findChequesSortedByPrice(0, Integer.MAX_VALUE);
            for (Cheque cheque : cheques) {
                total += cheque.getPrice();
            }

            if ("Z".equals(reportType)) {
                chequeLineRepository.deleteAll();
                chequeRepository.deleteAll();
            }


            XWPFDocument docxModel = new XWPFDocument();

            XWPFParagraph bodyParagraph = docxModel.createParagraph();
            bodyParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun paragraphConfig = bodyParagraph.createRun();
            paragraphConfig.setFontSize(25);
            paragraphConfig.setText("Магазин \"Фора\"");

            bodyParagraph = docxModel.createParagraph();
            bodyParagraph.setAlignment(ParagraphAlignment.CENTER);
            paragraphConfig = bodyParagraph.createRun();
            paragraphConfig.setFontSize(25);
            paragraphConfig.setText("Номер "+free);

            bodyParagraph = docxModel.createParagraph();
            bodyParagraph.setAlignment(ParagraphAlignment.CENTER);
            paragraphConfig = bodyParagraph.createRun();
            paragraphConfig.setFontSize(25);
            paragraphConfig.setText(LocalDate.now().toString());

            bodyParagraph = docxModel.createParagraph();
            bodyParagraph.setAlignment(ParagraphAlignment.CENTER);
            paragraphConfig = bodyParagraph.createRun();
            paragraphConfig.setBold(true);
            paragraphConfig.setFontSize(40);
            paragraphConfig.setText("Z".equals(reportType)?"Z - звіт":"X - звіт");

            bodyParagraph = docxModel.createParagraph();
            bodyParagraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphConfig = bodyParagraph.createRun();
            paragraphConfig.setFontSize(25);
            paragraphConfig.setText("Кількість чеків - "+cheques.size()+" шт");

            bodyParagraph = docxModel.createParagraph();
            bodyParagraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphConfig = bodyParagraph.createRun();
            paragraphConfig.setFontSize(25);
            paragraphConfig.setText("Загальна вартість - "+TextService.getInstance().formatPrice(total)+" $");

            FileOutputStream outputStream = new FileOutputStream(path+free+".docx");
            docxModel.write(outputStream);
            outputStream.close();

            return free;
        } catch (NullPointerException | IOException e) {
            throw new DBException(e);
        }
    }
}
