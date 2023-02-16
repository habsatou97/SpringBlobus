package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.dto.*;
import com.blobus.apiExterneBlobus.models.enums.CustomerType;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.models.enums.WalletType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ewallet/v1/accounts/")
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
    public ResponseEntity<Optional<CreateOrEditAccountDto>> getOne(@PathVariable Long id)
            throws NoSuchPaddingException, IllegalBlockSizeException,
            IOException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeyException {

        Optional<CreateOrEditAccountDto> accountDto=transferAccountService.getTransfertAccountById(id);
        DecryptDto decryptDto=new DecryptDto();
        decryptDto.setEncryptedPinCode(accountDto.get().getEncryptedPinCode());
        accountDto.get().setEncryptedPinCode(keyGenerator.decrypt(decryptDto));

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

        transferAccount.setEncryptedPinCode(
                keyGenerator.encrypt(new DecryptDto(transferAccount.getEncryptedPinCode())));
        DecryptDto decryptDto=new DecryptDto();
        CreateOrEditAccountDto accountDto=
                transferAccountService.createRetailerTransfertAccount(transferAccount,id);
        decryptDto.setEncryptedPinCode(accountDto.getEncryptedPinCode());
        accountDto.setEncryptedPinCode(keyGenerator.decrypt(decryptDto));

        return ResponseEntity.ok().body(accountDto);
    }


    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public ResponseEntity<CreateOrEditAccountDto> update(@RequestBody EditAccountDto transferAccount,
                                                         @PathVariable Long id)
            throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, IOException, BadPaddingException,
            InvalidKeySpecException, InvalidKeyException {

        transferAccount.setEncryptedPinCode(
                keyGenerator.encrypt(new DecryptDto(transferAccount.getEncryptedPinCode())));
        DecryptDto decryptDto=new DecryptDto();
        CreateOrEditAccountDto accountDto=transferAccountService.updateTranfertAccount(transferAccount,id);
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
    public void delete(@PathVariable Long id)
    {
        transferAccountService.deleteTransfertAccountById(id);
    }

    @DeleteMapping("account/deleteByPhoneNumber/{phoneNumber}")
    public void deleteByPhoneNumber(@PathVariable String phoneNumber){
        transferAccountService.deleteByPhoneNumber(phoneNumber);
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
     * @param role
     * @return
     */
    @PutMapping("/edit/retailer/{id}")
    public ResponseEntity<CreateOrEditAccountDto> updatedRetailerTransferAccount(
            @PathVariable("id") Long id,
            @RequestBody EditAccountDto account) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException,
            IOException, BadPaddingException,
            InvalidKeySpecException, InvalidKeyException {

        account.setEncryptedPinCode(
                keyGenerator.encrypt(new DecryptDto(account.getEncryptedPinCode())));
        DecryptDto decryptDto=new DecryptDto();
        CreateOrEditAccountDto accountDto =
                transferAccountService.modifyTransferAccountRetailer(id,account);
        decryptDto.setEncryptedPinCode(accountDto.getEncryptedPinCode());
        accountDto.setEncryptedPinCode(keyGenerator.decrypt(decryptDto));
       return   ResponseEntity.ok(accountDto);
    }

    @PostMapping("changePinCode")
    ResponseEntity<ResponseChangePinCodeDto> changePinCode(
            @RequestBody RequestBodyChangePinCodeDto requestBodyChangePinCodeDto,
            @RequestParam String msisdn, @RequestParam CustomerType customerType,
            @RequestParam WalletType walletType)
            throws NoSuchPaddingException, IllegalBlockSizeException,
            IOException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeySpecException, InvalidKeyException {

        return ResponseEntity.ok(transferAccountService
                .changePinCode(requestBodyChangePinCodeDto,msisdn,customerType,walletType));
    }


    /**
     * @param phoneNumber
     * @param walletType
     * @return
     * cette methode affiche le profile d'un utilisateur via son msisdn
     * autrement dit via le numero de telephone de son compte tranfert
     */
    @GetMapping("find/{phoneNumber}")
    public  ResponseEntity<RequestBodyUserProfileDto> getUserProfileByMsisdn(
            @PathVariable("phoneNumber") String phoneNumber, @RequestBody WalletTypeDto walletType){

        return ResponseEntity.ok(transferAccountService.getUserProfileByMsisdn(phoneNumber,walletType));
    }

}
