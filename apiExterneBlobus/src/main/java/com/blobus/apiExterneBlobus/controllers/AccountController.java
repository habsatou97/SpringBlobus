package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.services.implementations.AccountServiceImpl;
//import com.blobus.apiExterneBlobus.services.implementations.KeyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ewallet/v1/accounts/")
public class AccountController {
    @Autowired
    private AccountServiceImpl transferAccountService;
   // @Autowired
    //private KeyServiceImpl keyService;
    //public static Map<String,Object> map=new HashMap<>();

     /*@GetMapping("publicKey/")
     public byte[] getkey() throws IOException {
         return keyService.getPublicKey();
     }*/
    @GetMapping("balanceRetailer/{encryptedPinCode}/{phoneNumber}/{idUser}")
    public Double getBalance(@PathVariable String encryptedPinCode,@PathVariable String phoneNumber,@PathVariable Long idUser){
        return transferAccountService.getBalance(encryptedPinCode, phoneNumber, idUser).getBalance();
    }

    @GetMapping
    public ResponseEntity<List<Account>>get()
    {

        return ResponseEntity
                .ok(transferAccountService.getAllTransfertAccount());

    }
    @GetMapping("{id}")
    public ResponseEntity<Account> get(@PathVariable Long id){
        return ResponseEntity.ok().body(transferAccountService.getTransfertAccountById(id));
    }
    @RequestMapping(value = "phoneNumber/{id}",method = RequestMethod.GET)
    public String getPhoneNumber(@PathVariable Long id)
    {
        return transferAccountService.GetAccountPhoneNumber(id);
    }

    @PostMapping("customer/{id}")
    public ResponseEntity<Account>saveCustomer(@RequestBody Account transferAccount,@PathVariable Long id){
        //encryptDecryptPinCode.createKeys();
       // encryptDecryptPinCode.encryptPinCode(transferAccount.getEncryptedPinCode());
        return ResponseEntity.ok(transferAccountService.createCustomerTransfertAccount(transferAccount,id));
    }
    @PostMapping("/retailer/{id}")
    public ResponseEntity<Account>saveRetailer(@RequestBody Account transferAccount,@PathVariable Long id){
        //encryptDecryptPinCode.encryptPinCode(transferAccount.getEncryptedPinCode());
        return ResponseEntity.ok(transferAccountService.createRetailerTransfertAccount(transferAccount,id));
    }
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public ResponseEntity<Account> update(@RequestBody Account transferAccount, @PathVariable Long id){
        return ResponseEntity.ok(transferAccountService.updateTranfertAccount(transferAccount,id));
    }
    @RequestMapping(value = "enable/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Account> enable(@PathVariable Long id)
   {
    return ResponseEntity.ok(transferAccountService.EnableTransfertAccount(id));
   }
    @RequestMapping( value = "disable/{id}",method = RequestMethod.PUT)
     public ResponseEntity<Account> disable(@PathVariable Long id)
    {
       return ResponseEntity.ok(transferAccountService.DiseableTranfertAccount(id));
    }
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id)
    {
        transferAccountService.deleteTransfertAccountById(id);
    }


}
