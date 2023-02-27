package com.blobus.apiexterneblobus.controllers;

import com.blobus.apiexterneblobus.dto.*;
import com.blobus.apiexterneblobus.exception.ChangePinCodeException;
import com.blobus.apiexterneblobus.exception.ResourceNotFoundException;
import com.blobus.apiexterneblobus.models.enums.CustomerType;
import com.blobus.apiexterneblobus.models.enums.WalletType;
import com.blobus.apiexterneblobus.services.implementations.AccountServiceImpl;
import com.blobus.apiexterneblobus.services.implementations.KeyGeneratorImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ewallet/v1/admin/accounts/")
public class AccountController {
    @Autowired
    private final AccountServiceImpl transferAccountService;
    @Autowired
    private final KeyGeneratorImpl keyGenerator;

    @PostMapping("balance")
    public ResponseEntity<AmountDto> getBalance(@RequestBody GetRetailerBalanceDto getRetailerBalanceDto)
            throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        transferAccountService.getBalance(getRetailerBalanceDto);
        return ResponseEntity.ok().body(transferAccountService.getBalance(getRetailerBalanceDto));
    }

    @GetMapping
    public ResponseEntity<List<CreateOrEditAccountDto>>getAll()
            throws NoSuchPaddingException, IllegalBlockSizeException,
            IOException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeyException {

        DecryptDto decryptDto=new DecryptDto();
        List<CreateOrEditAccountDto> createOrEditAccountDtos=
                new ArrayList<>(transferAccountService.getAllTransfertAccount());
        for ( CreateOrEditAccountDto account:createOrEditAccountDtos)
             {
                 decryptDto.setEncryptedPinCode(account.getEncryptedPinCode());
                 account.setEncryptedPinCode(keyGenerator.decrypt(decryptDto));
             }
        return ResponseEntity
                .ok(createOrEditAccountDtos);

    }
    @GetMapping("{id}")
    public ResponseEntity<CreateOrEditAccountDto> getOne(@PathVariable Long id)
            throws NoSuchPaddingException, IllegalBlockSizeException,
            IOException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeyException {

        CreateOrEditAccountDto accountDto=transferAccountService.getTransfertAccountById(id).orElseThrow(()-> new ResourceNotFoundException("Account with id "+id+" don't exits."));
        DecryptDto decryptDto=new DecryptDto();
        decryptDto.setEncryptedPinCode(accountDto.getEncryptedPinCode());
        accountDto.setEncryptedPinCode(keyGenerator.decrypt(decryptDto));

        return ResponseEntity.ok().body(accountDto);
    }
    @RequestMapping(value = "phoneNumber/{id}",method = RequestMethod.GET)
    public String getPhoneNumber(@PathVariable Long id)
    {
        return transferAccountService.getAccountPhoneNumber(id);
    }

    @PostMapping("customer/{id}")
    public ResponseEntity<CreateOrEditAccountDto>saveCustomer(
            @RequestBody CreateAccountDto transferAccount,
            @PathVariable Long id)
            throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, IOException, BadPaddingException,
            InvalidKeySpecException, InvalidKeyException {

        transferAccount.setEncryptedPinCode(keyGenerator.encrypt(
                new DecryptDto(transferAccount.getEncryptedPinCode())));
        DecryptDto decryptDto=new DecryptDto();
        CreateOrEditAccountDto accountDto=
                transferAccountService.createCustomerTransfertAccount(transferAccount,id);
        decryptDto.setEncryptedPinCode(accountDto.getEncryptedPinCode());
        accountDto.setEncryptedPinCode(keyGenerator.decrypt(decryptDto));
        return ResponseEntity.ok().body(accountDto);
    }
    @PostMapping("/retailer/{id}")
    public ResponseEntity<CreateOrEditAccountDto>saveRetailer(@RequestBody CreateAccountDto transferAccount,
                                                              @PathVariable Long id)
            throws NoSuchPaddingException, IllegalBlockSizeException,
            IOException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeySpecException, InvalidKeyException {

//        transferAccount.setEncryptedPinCode(
//                keyGenerator.encrypt(new DecryptDto(transferAccount.getEncryptedPinCode())));

        DecryptDto decryptDto=new DecryptDto();
        CreateOrEditAccountDto accountDto=
                transferAccountService.createRetailerTransfertAccount(transferAccount,id);
        decryptDto.setEncryptedPinCode(accountDto.getEncryptedPinCode());
        accountDto.setEncryptedPinCode(transferAccount.getEncryptedPinCode());

        return ResponseEntity.ok().body(accountDto);
    }

    /**
     * Ce endPoint permet de modifier les infos d'un compte de transfert
     * @param transferAccount
     * @param id
     * @return
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws BadPaddingException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     */
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public ResponseEntity<CreateOrEditAccountDto> update(@RequestBody EditAccountDto transferAccount, @PathVariable Long id) throws
            NoSuchPaddingException,
            IllegalBlockSizeException,
            NoSuchAlgorithmException,
            IOException,
            BadPaddingException,
            InvalidKeySpecException,
            InvalidKeyException {
         if(transferAccount.getEncryptedPinCode()!=null
                 && transferAccount.getEncryptedPinCode().length()>0)
         {
        transferAccount.setEncryptedPinCode(
                keyGenerator.encrypt(new DecryptDto(transferAccount.getEncryptedPinCode())));
         }
        DecryptDto decryptDto=new DecryptDto();
        CreateOrEditAccountDto accountDto=
                transferAccountService.updateTranfertAccount(transferAccount,id);
        decryptDto.setEncryptedPinCode(accountDto.getEncryptedPinCode());
        accountDto.setEncryptedPinCode(keyGenerator.decrypt(decryptDto));

        return ResponseEntity.ok().body(accountDto);
       }
    @RequestMapping(value = "enable/{id}",method = RequestMethod.PUT)
    public ResponseEntity<CreateOrEditAccountDto> enable(@PathVariable Long id) throws
            NoSuchPaddingException,
            IllegalBlockSizeException,
            IOException,
            NoSuchAlgorithmException,
            BadPaddingException,
            InvalidKeyException {
       DecryptDto decryptDto=new DecryptDto();
       CreateOrEditAccountDto accountDto=transferAccountService.enableTransfertAccount(id);
       decryptDto.setEncryptedPinCode(accountDto.getEncryptedPinCode());
       accountDto.setEncryptedPinCode(keyGenerator.decrypt(decryptDto));
    return ResponseEntity.ok().body(accountDto);
   }

    @RequestMapping( value = "disable/{id}",method = RequestMethod.PUT)
     public ResponseEntity<CreateOrEditAccountDto> disable(@PathVariable Long id)
            throws NoSuchPaddingException,
            IllegalBlockSizeException,
            IOException,
            NoSuchAlgorithmException,
            BadPaddingException,
            InvalidKeyException {

        DecryptDto decryptDto=new DecryptDto();
        CreateOrEditAccountDto accountDto=transferAccountService.diseableTranfertAccount(id);
        decryptDto.setEncryptedPinCode(accountDto.getEncryptedPinCode());
        accountDto.setEncryptedPinCode(keyGenerator.decrypt(decryptDto));

       return ResponseEntity.ok().body(accountDto);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Boolean>> delete(@PathVariable Long id)
    {
        Map<String,Boolean> response = new HashMap<>();
        transferAccountService.deleteTransfertAccountById(id);
        response.put("deleted",true);
        return ResponseEntity.ok(response);
    }



    /**
     * ce endpoint permet de modifier le solde d'un compte d'un transfert
     * @param balanceDto
     * @param id
     * @return
     */
    @PutMapping("/edit/balance/{id}")
    public ResponseEntity<BalanceDto> updatedBalance(@RequestBody BalanceDto balanceDto,
                                                     @PathVariable("id") Long id){
        return ResponseEntity.ok(transferAccountService.updatedBalance(balanceDto,id));
    }

    /**
     * Ce endpoint permet à l'administrateur de mettre à jour le compte de transfert d'un retailer
     * @param id
     * @param account
     * @return
     */
    @PutMapping("/edit/retailer/{id}")
    public ResponseEntity<CreateOrEditAccountDto> updatedRetailerTransferAccount(
            @PathVariable("id") Long id,
            @RequestBody EditAccountDto account) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException,
            IOException, BadPaddingException,
            InvalidKeySpecException, InvalidKeyException {
        if(account.getEncryptedPinCode()!=null && account.getEncryptedPinCode().length()>0)
        {
            account.setEncryptedPinCode(
                    keyGenerator.encrypt(new DecryptDto(account.getEncryptedPinCode())));
        }
        DecryptDto decryptDto=new DecryptDto();
        CreateOrEditAccountDto accountDto =
                transferAccountService.modifyTransferAccountRetailer(id,account);
        decryptDto.setEncryptedPinCode(accountDto.getEncryptedPinCode());
        accountDto.setEncryptedPinCode(keyGenerator.decrypt(decryptDto));
       return   ResponseEntity.ok(accountDto);
    }





}
