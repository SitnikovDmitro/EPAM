package com.ra.service;


import com.ra.repository.ChequeLineRepository;
import com.ra.repository.ChequeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommonService {
    @Autowired
    private ChequeRepository chequeRepository;
    @Autowired
    private ChequeLineRepository chequeLineRepository;
    @Autowired
    private ChequeLineService chequeLineService;



}
