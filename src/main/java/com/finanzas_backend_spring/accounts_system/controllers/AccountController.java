package com.finanzas_backend_spring.accounts_system.controllers;

import com.finanzas_backend_spring.accounts_system.models.Account;
import com.finanzas_backend_spring.accounts_system.resources.AccountResource;
import com.finanzas_backend_spring.accounts_system.services.AccountService;
import com.finanzas_backend_spring.auth.resources.Message;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/")
public class AccountController {
    private final AccountService accountService;
    private final ModelMapper mapper;

    public AccountController(AccountService accountService, ModelMapper mapper) {
        this.accountService = accountService;
        this.mapper = mapper;
    }

    @GetMapping("accounts/")
    public ResponseEntity<List<AccountResource>> getAllAccounts(){
        try
        {
            List<Account> accounts = accountService.getAllAccounts();
            if(accounts.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<AccountResource> accountResources = accounts.stream().map(this::convertToResource).collect(Collectors.toList());
            return new ResponseEntity<>(accountResources, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("clients/{id}/accounts/")
    public ResponseEntity<List<AccountResource>> getAllAccountsByClient(@PathVariable Long id){
        try {
            List<Account> accounts = accountService.getAllAccountsByClient(id);
            if (accounts.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<AccountResource> accountResources = accounts.stream().map(this::convertToResource).collect(Collectors.toList());
            return new ResponseEntity<>(accountResources, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("clients/{clientId}/maintenances/{maintenanceId}/lineOfCredits/{lineOfCreditId}/accounts/")
    public ResponseEntity<AccountResource> create (@PathVariable Long clientId, @PathVariable Long maintenanceId, @PathVariable Long lineOfCreditId)
    {
        Account account = accountService.save(clientId,maintenanceId,lineOfCreditId);
        return new ResponseEntity<>(convertToResource(account),HttpStatus.OK);
    }

    @GetMapping("accounts/{id}/")
    public ResponseEntity<AccountResource> getAccountById (@PathVariable Long id){
        Account account = accountService.getAccountById(id);
        return new ResponseEntity<>(convertToResource(account),HttpStatus.OK);
    }

    @DeleteMapping("accounts/{id}/")
    public ResponseEntity<Message> delete (@PathVariable Long id){
        accountService.delete(id);
        Message message = new Message("Delete successful");
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PutMapping("maintenances/{maintenanceId}/accounts/{id}/")
    public ResponseEntity<AccountResource> changeMaintenance (@PathVariable Long maintenanceId, @PathVariable Long id){
        Account account = accountService.changeMaintenance(id, maintenanceId);
        return new ResponseEntity<>(convertToResource(account), HttpStatus.OK);
    }

    @PostConstruct
    public void init(){
        mapper.addMappings(new PropertyMap<Account, AccountResource>() {
            @Override
            protected void configure() {
                map().setClientName(source.getClient().getFirstName());
                map().setMaintenanceDescription(source.getMaintenance().getDescription());
            }
        });
    }

    public AccountResource convertToResource(Account entity) {
        return mapper.map(entity, AccountResource.class);
    }
}
