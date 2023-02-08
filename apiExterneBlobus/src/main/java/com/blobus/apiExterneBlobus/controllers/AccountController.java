package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.dto.BalanceDto;
import com.blobus.apiExterneBlobus.dto.CreateOrEditAccountDto;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.services.implementations.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ewallet/v1/accounts/")
public class AccountController {
    @Autowired
    private final AccountServiceImpl transferAccountService;

    @GetMapping("balanceRetailer/{encryptedPinCode}/{phoneNumber}/{idUser}")
    public Double getBalance(@PathVariable String encryptedPinCode,@PathVariable String phoneNumber,@PathVariable Long idUser){
        return transferAccountService.getBalance(encryptedPinCode, phoneNumber, idUser).getBalance();
    }

    @GetMapping
    public ResponseEntity<List<Account>>getAll()
    {

        return ResponseEntity
                .ok(transferAccountService.getAllTransfertAccount());

    }
    @GetMapping("{id}")
    public ResponseEntity<Account> getOne(@PathVariable Long id){
        return ResponseEntity.ok().body(transferAccountService.getTransfertAccountById(id));
    }
    @RequestMapping(value = "phoneNumber/{id}",method = RequestMethod.GET)
    public String getPhoneNumber(@PathVariable Long id)
    {
        return transferAccountService.GetAccountPhoneNumber(id);
    }

    @PostMapping("customer/{id}")
    public ResponseEntity<CreateOrEditAccountDto>saveCustomer(@RequestBody CreateOrEditAccountDto transferAccount, @PathVariable Long id){
        return ResponseEntity.ok().body(transferAccountService.createCustomerTransfertAccount(transferAccount,id));
    }
    @PostMapping("/retailer/{id}")
    public ResponseEntity<CreateOrEditAccountDto>saveRetailer(@RequestBody CreateOrEditAccountDto transferAccount,@PathVariable Long id){
        return ResponseEntity.ok().body(transferAccountService.createRetailerTransfertAccount(transferAccount,id));
    }


    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public ResponseEntity<CreateOrEditAccountDto> update(@RequestBody CreateOrEditAccountDto transferAccount, @PathVariable Long id){
        return ResponseEntity.ok().body(transferAccountService.updateTranfertAccount(transferAccount,id));
    }
    @RequestMapping(value = "enable/{id}",method = RequestMethod.PUT)
    public ResponseEntity<CreateOrEditAccountDto> enable(@PathVariable Long id)
   {
    return ResponseEntity.ok().body(transferAccountService.EnableTransfertAccount(id));
   }

    @RequestMapping( value = "disable/{id}",method = RequestMethod.PUT)
     public ResponseEntity<CreateOrEditAccountDto> disable(@PathVariable Long id)
    {
       return ResponseEntity.ok().body(transferAccountService.DiseableTranfertAccount(id));
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id)
    {
        transferAccountService.deleteTransfertAccountById(id);
    }

    @DeleteMapping("account/deleteByPhoneNumber/{phoneNumber}")
    public void deleteByPhoneNumber(@PathVariable String phoneNumber){
        transferAccountService.deleteByPhoneNumber(phoneNumber);
    }

    @PutMapping("/edit/balance/{id}")
    public BalanceDto updatedBalance(@RequestBody BalanceDto balanceDto,@PathVariable("id") Long id){
        return transferAccountService.updatedBalance(balanceDto,id);
    }

}
