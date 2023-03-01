package com.blobus.apiexterneblobus.controllers;

import com.blobus.apiexterneblobus.dto.*;
import com.blobus.apiexterneblobus.exception.ChangePinCodeException;
import com.blobus.apiexterneblobus.exception.ResourceNotFoundException;
import com.blobus.apiexterneblobus.models.enums.CustomerType;
import com.blobus.apiexterneblobus.models.enums.WalletType;
import com.blobus.apiexterneblobus.services.implementations.AccountServiceImpl;
import com.blobus.apiexterneblobus.services.implementations.KeyGeneratorImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Set the account balance")
    public ResponseEntity<AmountDto> getBalance( @RequestBody(required = true) GetRetailerBalanceDto getRetailerBalanceDto, @RequestHeader("Authorization") String token)
            throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        transferAccountService.getBalance(getRetailerBalanceDto);
        return ResponseEntity.ok().body(transferAccountService.getBalance(getRetailerBalanceDto));
    }

    @GetMapping
    @Operation(summary = "Get all the existing accounts")
    public ResponseEntity<List<CreateOrEditAccountDto>>getAll(@RequestHeader("Authorization") String token)
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
    @Operation(summary = "Get the account by its ID")
    public ResponseEntity<CreateOrEditAccountDto> getOne(@Parameter(description = "The id is required",required = true)@PathVariable Long id,@RequestHeader("Authorization") String token)
            throws NoSuchPaddingException, IllegalBlockSizeException,
            IOException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeyException {

        CreateOrEditAccountDto accountDto=transferAccountService.getTransfertAccountById(id).orElseThrow(()-> new ResourceNotFoundException("Account with id "+id+" don't exits."));
        DecryptDto decryptDto=new DecryptDto();
        decryptDto.setEncryptedPinCode(accountDto.getEncryptedPinCode());
        accountDto.setEncryptedPinCode(keyGenerator.decrypt(decryptDto));

        return ResponseEntity.ok().body(accountDto);
    }
    @Operation(summary = "Get the account msisdn(PhoneNumber) by its ID")
    @RequestMapping(value = "phoneNumber/{id}",method = RequestMethod.GET)
    public String getPhoneNumber(@Parameter(description = "The id is reuired for this opertaion")@PathVariable Long id,@RequestHeader("Authorization") String token)
    {
        return transferAccountService.getAccountPhoneNumber(id);
    }

    @Operation(summary = "This operation allows to create a transfert account for a customer that its ID is given in parameter ")
    @PostMapping("customer/{id}")
    public ResponseEntity<CreateOrEditAccountDto>saveCustomer(
            @Parameter(description = "A request body is required")
            @RequestBody(required = true) CreateAccountDto transferAccount,
            @PathVariable Long id,@RequestHeader("Authorization") String token)
            throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, IOException, BadPaddingException,
            InvalidKeySpecException, InvalidKeyException {
        return ResponseEntity.ok().body(transferAccountService.createCustomerTransfertAccount(transferAccount,id));
    }
    @Operation(summary = "This operation allows to create a transfert account for a retailer that its ID is given in parameter ")
    @PostMapping("/retailer/{id}")
    public ResponseEntity<CreateOrEditAccountDto>saveRetailer(@Parameter(description = "A request body is required")@RequestBody CreateAccountDto transferAccount,
                                                              @PathVariable Long id,@RequestHeader("Authorization") String token)
            throws NoSuchPaddingException, IllegalBlockSizeException,
            IOException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeySpecException, InvalidKeyException {
        return ResponseEntity.ok().body(transferAccountService.createRetailerTransfertAccount(transferAccount,id));
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


    @Operation(summary = "This operation allows to update a transfert account by its ID ")
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public ResponseEntity<CreateOrEditAccountDto> update(@RequestBody EditAccountDto transferAccount, @Parameter(name = "id",description = "To update an account, the id is required ")
    @PathVariable Long id,@RequestHeader("Authorization") String token) throws
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
    @Operation(summary = "This operation allows to enable  a transfert account by its ID ")
    @RequestMapping(value = "enable/{id}",method = RequestMethod.PUT)
    public ResponseEntity<CreateOrEditAccountDto> enable(@Parameter(name = "id",description = "To enable an account, the id is required ")

                                                            @PathVariable Long id,@RequestHeader("Authorization") String token) throws
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

    @Operation(summary = "This operation allows to disable  a transfert account by its ID ")
    @RequestMapping( value = "disable/{id}",method = RequestMethod.PUT)
     public ResponseEntity<CreateOrEditAccountDto> disable(@Parameter(name = "id",description = "To disable an account, the id is required ")

                                                               @PathVariable Long id,@RequestHeader("Authorization") String token)
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
    @Operation(summary = "This operation allows to delete a transfert account by its ID ")
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Boolean>> delete(@Parameter(name = "id",description = "To delete an account, the id is required ")

                                                          @PathVariable Long id,@RequestHeader("Authorization") String token)
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
    @Operation(summary = "This operation allows to edit an account's balance by its ID ")
    @PutMapping("/edit/balance/{id}")
    public ResponseEntity<BalanceDto> updatedBalance(@RequestBody(required = true) BalanceDto balanceDto,
                                                     @Parameter(name = "id",description = "To edit an account's balance, the id is required ")

                                                     @PathVariable(value = "id",required = true) Long id,@RequestHeader("Authorization") String token)

    {
        return ResponseEntity.ok(transferAccountService.updatedBalance(balanceDto,id));
    }

    /**
     * Ce endpoint permet à l'administrateur de mettre à jour le compte de transfert d'un retailer
     * @param id
     * @param account
     * @return
     */
    @Operation(summary = "This operation allows to update a retailer account balance by its ID ")
    @PutMapping("/edit/retailer/{id}")
    public ResponseEntity<CreateOrEditAccountDto> updatedRetailerTransferAccount(
            @Parameter(name = "id",description = "To update a retailer account, the id is required ")@PathVariable("id") Long id,
            @RequestBody(required = true) EditAccountDto account,
            @Parameter(name = "Authorization",description = "Access Token")
            @RequestHeader("Authorization") String token) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException,
            IOException, BadPaddingException,
            InvalidKeySpecException, InvalidKeyException {
//        if(account.getEncryptedPinCode()!=null && account.getEncryptedPinCode().length()>0)
//        {
//            account.setEncryptedPinCode(
//                    keyGenerator.encrypt(new DecryptDto(account.getEncryptedPinCode())));
//        }
        DecryptDto decryptDto=new DecryptDto();
        CreateOrEditAccountDto accountDto =
                transferAccountService.modifyTransferAccountRetailer(id,account);
        decryptDto.setEncryptedPinCode(accountDto.getEncryptedPinCode());
        accountDto.setEncryptedPinCode(keyGenerator.decrypt(decryptDto));
       return   ResponseEntity.ok(accountDto);
    }





}
