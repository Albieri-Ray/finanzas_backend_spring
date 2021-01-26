package com.finanzas_backend_spring.accounts_system.services;

import com.finanzas_backend_spring.accounts_system.models.LineOfCredit;

import java.util.List;

public interface LineOfCreditService {
    List<LineOfCredit> getAllLineOfCredit();
    LineOfCredit getLineOfCreditById(Long id);
    LineOfCredit save (LineOfCredit lineOfCredit);
    LineOfCredit update (Long id, LineOfCredit lineOfCredit);
    void delete(Long id);
}
