package com.finanzas_backend_spring.payments_system.services.impl;

import com.finanzas_backend_spring.accounts_system.models.Account;
import com.finanzas_backend_spring.accounts_system.models.LineOfCredit;
import com.finanzas_backend_spring.accounts_system.models.Maintenance;
import com.finanzas_backend_spring.accounts_system.repositories.AccountRepository;
import com.finanzas_backend_spring.accounts_system.repositories.LineOfCreditRepository;
import com.finanzas_backend_spring.payments_system.models.Receipt;
import com.finanzas_backend_spring.payments_system.models.Trust;
import com.finanzas_backend_spring.payments_system.repositories.ReceiptRepository;
import com.finanzas_backend_spring.payments_system.repositories.TrustRepository;
import com.finanzas_backend_spring.payments_system.services.ReceiptService;
import com.finanzas_backend_spring.user_system.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiptServiceImpl implements ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final AccountRepository accountRepository;
    private final TrustRepository trustRepository;
    private final LineOfCreditRepository lineOfCreditRepository;
    private final LocalDate today = LocalDate.now();

    @Autowired
    public ReceiptServiceImpl(ReceiptRepository receiptRepository, AccountRepository accountRepository, TrustRepository trustRepository, LineOfCreditRepository lineOfCreditRepository) {
        this.receiptRepository = receiptRepository;
        this.accountRepository = accountRepository;
        this.trustRepository = trustRepository;
        this.lineOfCreditRepository = lineOfCreditRepository;
    }

    @Override
    public List<Receipt> getAllReceiptByClient(Long clientId) {
        return receiptRepository.findByClientId(clientId);
    }

    @Override
    public Receipt getReceiptById(Long id) {
        return receiptRepository.findById(id).orElseThrow(()->new NotFoundException("receipt","id",id));
    }

    @Override
    public Receipt save(Receipt receipt, Long accountId) {
        //receipt.setRegisterDate(today);
        receipt.setAccount(accountRepository.findById(accountId).orElseThrow(()->new NotFoundException("account","id", accountId)));
        receipt.setCreditConsumed(calculateCreditConsumed(receipt.getAccount()));
        receipt.setQuantityOfDays(calculateDays(receipt.getAccount(),receipt.getRegisterDate()));
        receipt.setMaintenanceCost(calculateMaintenanceCost(receipt.getAccount(),receipt.getQuantityOfDays()));
        String typeOfRate = receipt.getAccount().getLineOfCredit().getRateType();
        if (typeOfRate.equals("Simple")){
            List<BigDecimal> values = calculateSimple(receipt.getAccount(),receipt.getRegisterDate());
            receipt.setInterest(values.get(0));
            receipt.setTotal((values.get(1).add(receipt.getMaintenanceCost())));
        }
        if (typeOfRate.equals("Nominal")){
            List<BigDecimal> values = calculateNominal(receipt.getAccount(),receipt.getRegisterDate());
            receipt.setInterest(values.get(0));
            receipt.setTotal((values.get(1).add(receipt.getMaintenanceCost())));
        }
        if (typeOfRate.equals("Efectiva")){
            List<BigDecimal> values = calculateEfectiva(receipt.getAccount(),receipt.getRegisterDate());
            receipt.setInterest(values.get(0));
            receipt.setTotal((values.get(1).add(receipt.getMaintenanceCost())));
        }
        LineOfCredit lineOfCredit = receipt.getAccount().getLineOfCredit();
        lineOfCredit.setRegisterDate(today);
        lineOfCreditRepository.save(lineOfCredit);

        return receiptRepository.save(receipt);
    }

    @Override
    public void delete(Long id) {
        Receipt receipt = receiptRepository.findById(id).orElseThrow(()->new NotFoundException("receipt","id",id));
        receiptRepository.delete(receipt);
    }

    private BigDecimal calculateCreditConsumed(Account account){
        List<Trust> trusts = trustRepository.findByAccountId(account.getId());
        BigDecimal result = new BigDecimal(0);
        for (Trust trust: trusts) {
            result = result.add(trust.getPriceProduct());
        }
        return result;
    }

    private Integer calculateDays(Account account, LocalDate finalDate){
        LocalDate initialDate = account.getLineOfCredit().getRegisterDate();
        Period period = Period.between ( initialDate , finalDate );
        return period.getDays();
    }

    private BigDecimal calculateMaintenanceCost(Account account, Integer quantityOfDays ){
        Maintenance maintenance = account.getMaintenance();
        return maintenance.getPrice().multiply(new BigDecimal((quantityOfDays/getDaysByPeriodOrFrequency(maintenance.getFrequency()))));
    }

    private Integer getDaysByPeriodOrFrequency(String word){
        int frequencyDays = 0;
        if (word.equals("Semanal")){
            frequencyDays = 7;
        }
        if (word.equals("Quincenal")){
            frequencyDays = 15;
        }
        if (word.equals("Mensual")){
            frequencyDays = 30;
        }
        if (word.equals("Bimestral")){
            frequencyDays = 60;
        }
        if (word.equals("Trimestral")){
            frequencyDays = 90;
        }
        if (word.equals("Cuatrimestral")){
            frequencyDays = 120;
        }
        if (word.equals("Anual")){
            frequencyDays = 360;
        }
        return frequencyDays;
    }

    private List<BigDecimal> calculateSimple(Account account, LocalDate finalDate){
        List<BigDecimal> values = new ArrayList<>();
        List<Trust> trusts = trustRepository.findByAccountId(account.getId());
        BigDecimal interest = new BigDecimal(0);
        BigDecimal future = new BigDecimal(0);
        for (Trust trust:trusts){
            BigDecimal capitalPerTrust = trust.getPriceProduct();
            Period  daysDatePerTrust = Period.between(trust.getRegisterDate(),finalDate);
            int daysPerTrust = daysDatePerTrust.getDays();
            BigDecimal interestPerTrust = capitalPerTrust.multiply(account.getLineOfCredit().getRateValue()
                    .divide(new BigDecimal(100),2)).multiply(new BigDecimal(daysPerTrust));
//Change to seven
            BigDecimal futurePerTrust = capitalPerTrust.add(interestPerTrust);
            interest = interest.add(interestPerTrust);
            future = future.add(futurePerTrust);
        }
        values.add(interest);
        values.add(future);
        return values;
    }

    private List<BigDecimal> calculateNominal(Account account, LocalDate finalDate){
        List<BigDecimal> values = new ArrayList<>();
        List<Trust> trusts = trustRepository.findByAccountId(account.getId());
        BigDecimal interest = new BigDecimal(0);
        BigDecimal future = new BigDecimal(0);
        for (Trust trust:trusts){
            BigDecimal capitalPerTrust = trust.getPriceProduct();
            Period  daysDatePerTrust = Period.between(trust.getRegisterDate(),finalDate);
            int daysPerTrust = daysDatePerTrust.getDays();
            BigDecimal base = new BigDecimal(1).add((account.getLineOfCredit().getRateValue()
                    .divide(new BigDecimal(100),2)));
            BigDecimal baseFinal = base.divide(new BigDecimal(getDaysByPeriodOrFrequency(account.getLineOfCredit().getPeriod())),2);
//Change to seven
            BigDecimal futurePerTrust = capitalPerTrust.add(baseFinal.pow(daysPerTrust));
            BigDecimal interestPerTrust = futurePerTrust.subtract(capitalPerTrust);
            interest = interest.add(interestPerTrust);
            future = future.add(futurePerTrust);
        }
        values.add(interest);
        values.add(future);
        return values;
    }

    private List<BigDecimal> calculateEfectiva(Account account, LocalDate finalDate){
        List<BigDecimal> values = new ArrayList<>();
        List<Trust> trusts = trustRepository.findByAccountId(account.getId());
        BigDecimal interest = new BigDecimal(0);
        BigDecimal future = new BigDecimal(0);
        for (Trust trust:trusts){
            BigDecimal capitalPerTrust = trust.getPriceProduct();
            Period  daysDatePerTrust = Period.between(trust.getRegisterDate(),finalDate);
            int daysPerTrust = daysDatePerTrust.getDays();
            BigDecimal base = new BigDecimal(1).add((account.getLineOfCredit().getRateValue()
                    .divide(new BigDecimal(100),2)));
            int exponent = daysPerTrust / getDaysByPeriodOrFrequency(account.getLineOfCredit().getPeriod());
//Change to seven
            BigDecimal futurePerTrust = capitalPerTrust.add(base.pow(exponent));
            BigDecimal interestPerTrust = futurePerTrust.subtract(capitalPerTrust);
            interest = interest.add(interestPerTrust);
            future = future.add(futurePerTrust);
        }
        values.add(interest);
        values.add(future);
        return values;
    }
}
