package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.dto.GetRetailerBalanceDto;
import com.blobus.apiExterneBlobus.dto.BalanceDto;
import com.blobus.apiExterneBlobus.dto.CreateOrEditAccountDto;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.services.implementations.AccountServiceImpl;
import com.blobus.apiExterneBlobus.services.implementations.KeyGeneratorImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ewallet/v1/accounts/")
public class AccountController {
    @Autowired
    private final AccountServiceImpl transferAccountService;

    @Autowired
    private final KeyGeneratorImpl keyGenerator;

    @GetMapping("retailer/balance")
    public double getBalance(@RequestBody GetRetailerBalanceDto getRetailerBalanceDto){
        return transferAccountService.getBalance(getRetailerBalanceDto);
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
    public ResponseEntity<CreateOrEditAccountDto>saveRetailer(@RequestBody CreateOrEditAccountDto transferAccount,@PathVariable Long id) throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
         transferAccount.setEncryptedPinCode(keyGenerator.encrypt(transferAccount.getEncryptedPinCode()));
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

    @PostMapping("crypt/")
    String crypt(@RequestBody String chaine) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        return keyGenerator.encrypt(chaine);
    }

    @PostMapping("decrypt/")
    String decrypt(@RequestBody String chaine) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        String dec=keyGenerator.encrypt(chaine);
        System.out.println(dec);
        // System.out.println(keyGenerator.decrypt(keyGenerator.encrypt(chaine)));
        return keyGenerator.decrypt(dec);
    }
}
