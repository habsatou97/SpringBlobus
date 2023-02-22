package com.blobus.apiexterneblobus.controllers;

import com.blobus.apiexterneblobus.dto.RequestBodyUserProfileDto;
import com.blobus.apiexterneblobus.models.enums.WalletType;
import com.blobus.apiexterneblobus.services.implementations.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ewallet/v1/retailer/accounts")
public class GetProfileByMsisdnController {
    @Autowired
    private final AccountServiceImpl accountService;


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
}

