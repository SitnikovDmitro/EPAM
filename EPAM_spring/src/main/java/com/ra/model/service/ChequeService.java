package com.ra.model.service;

import com.ra.model.entity.Cheque;
import com.ra.model.entity.ChequeLine;
import com.ra.model.exceptions.DBException;
import com.ra.model.repository.ChequeLineRepository;
import com.ra.model.repository.ChequeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ChequeLineService chequeLineService;

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

    public void completeCheque(ArrayList<ChequeLine> chequeLines) throws DBException {
        if (chequeLines.isEmpty()) return;

        Cheque cheque = new Cheque(0, 0, 0, Date.valueOf(LocalDate.now()));
        cheque.setPrice(chequeLineService.computePrice(chequeLines));

        try {
            chequeRepository.save(cheque);
            for (ChequeLine chequeLine : chequeLines) {
                chequeLine.setCheque(cheque);
                chequeLineRepository.save(chequeLine);
            }
        } catch (Exception exception) {
            throw new DBException(exception);
        }
    }
}
