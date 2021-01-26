package com.finanzas_backend_spring.accounts_system.controllers;

import com.finanzas_backend_spring.accounts_system.models.LineOfCredit;
import com.finanzas_backend_spring.accounts_system.resources.LineOfCreditResource;
import com.finanzas_backend_spring.accounts_system.resources.SaveLineOfCreditResource;
import com.finanzas_backend_spring.accounts_system.services.LineOfCreditService;
import com.finanzas_backend_spring.auth.resources.Message;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/")
public class LineOfCreditController {
    private final LineOfCreditService lineOfCreditService;
    private final ModelMapper mapper;

    @Autowired
    public LineOfCreditController(LineOfCreditService lineOfCreditService, ModelMapper mapper) {
        this.lineOfCreditService = lineOfCreditService;
        this.mapper = mapper;
    }

    @GetMapping("lineaOfCredits/")
    public ResponseEntity<List<LineOfCreditResource>> getAllLineaOfCredits(){
        try{
            List<LineOfCredit> lineOfCredits = lineOfCreditService.getAllLineOfCredit();
            if (lineOfCredits.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<LineOfCreditResource> lineOfCreditResources = lineOfCredits.stream().map(this::convertToResource).collect(Collectors.toList());
            return new ResponseEntity<>(lineOfCreditResources,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("lineaOfCredits/{id}/")
    public ResponseEntity<LineOfCreditResource> getLineOfCreditById(@PathVariable Long id){
        LineOfCredit lineOfCredit = lineOfCreditService.getLineOfCreditById(id);
        return new ResponseEntity<>(convertToResource(lineOfCredit),HttpStatus.OK);
    }

    @PostMapping("lineaOfCredit/")
    public ResponseEntity<LineOfCreditResource> create(@RequestBody SaveLineOfCreditResource saveLineOfCreditResource)
    {
        LineOfCredit lineOfCredit = lineOfCreditService.save(convertToEntity(saveLineOfCreditResource));
        return new ResponseEntity<>(convertToResource(lineOfCredit), HttpStatus.OK);
    }

    @PutMapping("lineaOfCredits/{id}/")
    public ResponseEntity<LineOfCreditResource> UpdateUserById (@PathVariable Long id, @RequestBody SaveLineOfCreditResource saveLineOfCreditResource){
        LineOfCredit lineOfCredit = lineOfCreditService.update(id,convertToEntity(saveLineOfCreditResource));
        return new ResponseEntity<>(convertToResource(lineOfCredit),HttpStatus.OK);
    }

    @DeleteMapping("lineaOfCredits/{id}/")
    public  ResponseEntity<Message> delete (@PathVariable Long id){
        lineOfCreditService.delete(id);
        Message message = new Message("Delete successful");
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    public LineOfCreditResource convertToResource(LineOfCredit entity) {
        return mapper.map(entity, LineOfCreditResource.class);
    }

    public LineOfCredit convertToEntity(SaveLineOfCreditResource resource) {
        return mapper.map(resource, LineOfCredit.class);
    }
}
