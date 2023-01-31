package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.services.implementations.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ewallet/v1")
public class AccountController {
    @Autowired
    private AccountServiceImpl transferAccountService;
    @GetMapping("/accounts")
    public ResponseEntity<List<Account>>get()
    {

        return ResponseEntity.ok().body(transferAccountService.getAllTransfertAccount());
    }
    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> get(@PathVariable Long id){
        return ResponseEntity.ok().body(transferAccountService.getTransfertAccountById(id));
    }
    @RequestMapping(value = "accounts/phoneNumber/{id}",method = RequestMethod.GET)
    public String getPhoneNumber(@PathVariable Long id)
    {
        return transferAccountService.GetAccountPhoneNumber(id);
    }
    @PostMapping("/accounts")
    public ResponseEntity<Account>save(@RequestBody Account transferAccount){
        return ResponseEntity.ok().body(transferAccountService.createTransfertAccount(transferAccount));
    }
    @RequestMapping(value = "/accounts/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Account> update(@RequestBody Account transferAccount, @PathVariable Long id){
        return ResponseEntity.ok().body(transferAccountService.updateTranfertAccount(transferAccount,id));
    }
    @RequestMapping(value = "/accounts/enable/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Account> enable(@PathVariable Long id)
   {
    return ResponseEntity.ok().body(transferAccountService.EnableTransfertAccount(id));
   }
    @RequestMapping( value = "/accounts/disable/{id}",method = RequestMethod.PUT)
     public ResponseEntity<Account> disable(@PathVariable Long id)
    {
       return ResponseEntity.ok().body(transferAccountService.DiseableTranfertAccount(id));
    }
    @RequestMapping(value = "/accounts/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id)
    {
        transferAccountService.deleteTransfertAccountById(id);
    }


}
