package com.blobus.apiexterneblobus.controllers;

import com.blobus.apiexterneblobus.dto.RequestBodyChangePinCodeDto;
import com.blobus.apiexterneblobus.dto.RequestBodyUserProfileDto;
import com.blobus.apiexterneblobus.dto.ResponseChangePinCodeDto;
import com.blobus.apiexterneblobus.exception.ChangePinCodeException;
import com.blobus.apiexterneblobus.models.enums.CustomerType;
import com.blobus.apiexterneblobus.models.enums.WalletType;
import com.blobus.apiexterneblobus.services.implementations.AccountServiceImpl;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ewallet/v1/retailer/accounts")
public class GetProfileByMsisdnController {
    @Autowired
    private final AccountServiceImpl accountService;
    @Autowired
    private final AccountServiceImpl transferAccountService;


    /**
     *  cette methode affiche le profile d'un utilisateur via son msisdn
     *   autrement dit via le numero de telephone de son compte tranfert
     * @param phoneNumber
     * @param walletType
     * @return  ResponseEntity<RequestBodyUserProfileDto>
     *
     */
    @GetMapping("/getuserprofilebymsisdn")
    public ResponseEntity<RequestBodyUserProfileDto> getUserProfileByMsisdn(
            @RequestParam String phoneNumber, @RequestParam WalletType walletType){

        return ResponseEntity.ok(accountService.getUserProfileByMsisdn(phoneNumber,walletType));
    }


    @PostMapping("/changePinCode")
    ResponseEntity<ResponseChangePinCodeDto> changePinCode(
            @RequestBody RequestBodyChangePinCodeDto requestBodyChangePinCodeDto,
            @RequestParam String msisdn, @RequestParam CustomerType customerType,
            @RequestParam WalletType walletType,
            @RequestHeader String content_type)
            throws NoSuchPaddingException, IllegalBlockSizeException,
            IOException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeySpecException, InvalidKeyException, ChangePinCodeException {

        return ResponseEntity.ok(transferAccountService
                .changePinCode(requestBodyChangePinCodeDto,msisdn,customerType,walletType, content_type));
    }
}

