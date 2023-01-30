package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.services.implementations.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ewallet/v1")
public class AccountController {
    @Autowired
    private AccountServiceImpl transferAccountService;
    @GetMapping("/accounts")
    public List<Account> get()
    {
        return transferAccountService.getAllTransfertAccount();
    }
    @GetMapping("/accounts/{id}")
    public Optional<Account> getAccount(@PathVariable Long id){
        return transferAccountService.getTransfertAccountById(id);
    }
    @RequestMapping(value = "accounts/phoneNumber/{id}",method = RequestMethod.GET)
    public String getPhoneNumber(@PathVariable Long id)
    {
        return transferAccountService.GetAccountPhoneNumber(id);
    }
    @PostMapping("/accounts")
    public Account save(@RequestBody Account transferAccount){
        return transferAccountService.createTransfertAccount(transferAccount);
    }
    @RequestMapping(value = "/accounts/{id}",method = RequestMethod.PUT)
    public Account update(@RequestBody Account transferAccount, @PathVariable Long id){
        return transferAccountService.updateTranfertAccount(transferAccount,id);
    }
    @RequestMapping(value = "/accounts/enable/{id}",method = RequestMethod.PUT)
    public Account enable(@PathVariable Long id)
   {
    return transferAccountService.EnableTransfertAccount(id);
   }
    @RequestMapping( value = "/accounts/disable/{id}",method = RequestMethod.PUT)
     public Account disable(@PathVariable Long id)
    {
       return transferAccountService.DiseableTranfertAccount(id);
    }
    @RequestMapping(value = "/accounts/{id}",method = RequestMethod.DELETE)
    public boolean delete(@PathVariable Long id)
    {
        return transferAccountService.deleteTransfertAccountById(id);
    }


}
