package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.models.TransferAccount;
import com.blobus.apiExterneBlobus.services.implementations.TransferAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ewallet/v1")
public class TransferAccountController {
    @Autowired
    private TransferAccountServiceImpl transferAccountService;
    @GetMapping("/accounts")
    public List<TransferAccount> get()
    {
        return transferAccountService.getAllTransfertAccount();
    }
    @GetMapping("/accounts/{id}")
    public Optional<TransferAccount> getAccount(@PathVariable Long id){
        return transferAccountService.getTransfertAccountById(id);
    }

    @PostMapping("/accounts")
    public TransferAccount save(@RequestBody TransferAccount transferAccount){
        return transferAccountService.createTransfertAccount(transferAccount);
    }
    @RequestMapping(value = "/accounts/{id}",method = RequestMethod.PUT)
    public TransferAccount update(@RequestBody TransferAccount transferAccount,@PathVariable Long id){
        return transferAccountService.updateTranfertAccount(transferAccount,id);
    }
    @RequestMapping("/accounts/{id}")
    public boolean delete(@PathVariable Long id)
    {
        return transferAccountService.deleteTransfertAccountById(id);
    }


}
