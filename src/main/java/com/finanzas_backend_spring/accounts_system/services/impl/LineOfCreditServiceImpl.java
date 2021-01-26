package com.finanzas_backend_spring.accounts_system.services.impl;

import com.finanzas_backend_spring.accounts_system.models.LineOfCredit;
import com.finanzas_backend_spring.accounts_system.repositories.LineOfCreditRepository;
import com.finanzas_backend_spring.accounts_system.services.LineOfCreditService;
import com.finanzas_backend_spring.user_system.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LineOfCreditServiceImpl implements LineOfCreditService {
    private final LineOfCreditRepository lineOfCreditRepository;
    private final LocalDate today = LocalDate.now();

    @Autowired
    public LineOfCreditServiceImpl(LineOfCreditRepository lineOfCreditRepository) {
        this.lineOfCreditRepository = lineOfCreditRepository;
    }

    @Override
    public List<LineOfCredit> getAllLineOfCredit() {
        return lineOfCreditRepository.findAll();
    }

    @Override
    public LineOfCredit getLineOfCreditById(Long id) {
        return lineOfCreditRepository.findById(id).orElseThrow(()-> new NotFoundException("lineOfCredit","id",id));
    }

    @Override
    public LineOfCredit save(LineOfCredit lineOfCredit) {
        lineOfCredit.setRegisterDate(today);
        return lineOfCreditRepository.save(lineOfCredit);
    }

    @Override
    public LineOfCredit update(Long id, LineOfCredit lineOfCredit) {
        LineOfCredit exited = lineOfCreditRepository.findById(id).orElseThrow(()-> new NotFoundException("lineOfCredit","id",id));
        exited.setRateType(lineOfCredit.getRateType());
        exited.setCredit(lineOfCredit.getCredit());
        exited.setTypeOfCurrency(lineOfCredit.getTypeOfCurrency());
        exited.setRegisterDate(today);
        exited.setPeriod(lineOfCredit.getPeriod());
        exited.setRateValue(lineOfCredit.getRateValue());
        return lineOfCreditRepository.save(exited);
    }

    @Override
    public void delete(Long id) {
        LineOfCredit lineOfCredit = lineOfCreditRepository.findById(id).orElseThrow(()-> new NotFoundException("lineOfCredit","id",id));
        lineOfCreditRepository.delete(lineOfCredit);
    }
}
