package app.service;

import app.dao.ChequeDAO;
import app.dao.ChequeLineDAO;
import app.dao.ProductDAO;
import app.entity.Cheque;
import app.entity.ChequeLine;
import app.exceptions.DBException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ChequeService {
    private final ChequeDAO chequeDAO;
    private final ChequeLineDAO chequeLineDAO;
    private final ProductDAO productDAO;

    public ChequeService(ChequeDAO chequeDAO, ChequeLineDAO chequeLineDAO, ProductDAO productDAO) {
        this.chequeDAO = chequeDAO;
        this.chequeLineDAO = chequeLineDAO;
        this.productDAO = productDAO;
    }

    /**
     * Extract cheques from database
     * @param fromPrice from price
     * @param toPrice to price
     * @param sortCriteria sort criteria (by price or by creation time)
     * @param cheques cheques
     * @param chequeLines cheque lines
     */
    public void findCheques(String fromPrice, String toPrice, String sortCriteria, ArrayList<Cheque> cheques, ArrayList<ArrayList<ChequeLine>> chequeLines) throws SQLException {
        ArrayList<Cheque> list1;
        int from = 0, to = Integer.MAX_VALUE;

        try {
            from = (int)(Double.parseDouble(fromPrice)*100.0);
        } catch (NumberFormatException | NullPointerException ignored) { }

        try {
            to = (int)(Double.parseDouble(toPrice)*100.0);
        } catch (NumberFormatException | NullPointerException ignored) { }

        if (sortCriteria != null && sortCriteria.equals("price")) {
            list1 = chequeDAO.findChequesSortedByPrice(from, to);
        } else {
            list1 = chequeDAO.findChequesSortedByCreationTime(from, to);
        }

        cheques.clear();
        chequeLines.clear();
        for (Cheque cheque : list1) {
            cheques.add(cheque);
            chequeLines.add(chequeLineDAO.findChequeLinesByChequeId(cheque.getId()));
        }
    }

    /**
     * Paginate cheques
     * @param page number of current page
     * @param source1 cheques extracted from database
     * @param source2 cheque lines extracted from database
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

        Cheque cheque = new Cheque(0, 0, 0, Date.valueOf(LocalDate.now()));
        cheque.setPrice(computePrice(chequeLines));

        try {
            int id = chequeDAO.addCheque(cheque);
            cheque.setId(id);
            for (ChequeLine chequeLine : chequeLines) {
                chequeLine.getProduct().setTotalAmount(chequeLine.getProduct().getTotalAmount() - chequeLine.getAmount());
                if (chequeLine.getProduct().getTotalAmount() < 0) chequeLine.getProduct().setTotalAmount(0);
                chequeLine.setCheque(cheque);
                productDAO.updateProduct(chequeLine.getProduct());
                chequeLineDAO.addChequeLine(chequeLine);
            }
        } catch (SQLException exception) {
            throw new DBException(exception);
        }
    }

    private int computePrice(ArrayList<ChequeLine> chequeLines) {
        int total = 0;
        for (ChequeLine chequeLine : chequeLines) {
            if (chequeLine.getProduct().isCountable()) {
                total += chequeLine.getAmount()*chequeLine.getProduct().getPrice();
            } else {
                total += chequeLine.getAmount()*chequeLine.getProduct().getPrice()/1000;
            }
        }
        return total;
    }

    /**
     * Creates report
     * @param reportType type of report (X or Z)
     * @param path path to reports folder
     * @return number of created report
     */
    public int createReport(String reportType, String path) throws DBException {
        try {
            int free = 1;
            for (String filename : new File(path+"\\reports").list()) {
                try {
                    int curr = Integer.parseInt(filename.substring(0, filename.length()-5));
                    if (free <= curr) free = curr + 1;
                } catch (IndexOutOfBoundsException | NumberFormatException ignored) { }
            }

            int total = 0;
            ArrayList<Cheque> cheques = chequeDAO.findChequesSortedByPrice(0, Integer.MAX_VALUE);
            for (Cheque cheque : cheques) {
                total += cheque.getPrice();
            }

            if ("Z".equals(reportType)) {
                chequeDAO.removeAll();
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

            FileOutputStream outputStream = new FileOutputStream(path+"\\reports\\"+free+".docx");
            docxModel.write(outputStream);
            outputStream.close();

            return free;
        } catch (NullPointerException | IOException | SQLException e) {
            throw new DBException(e);
        }
    }
}
