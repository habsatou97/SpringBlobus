package com.blobus.apiexterneblobus.controllers;

import com.blobus.apiexterneblobus.dto.RequestBodyChangePinCodeDto;
import com.blobus.apiexterneblobus.dto.RequestBodyUserProfileDto;
import com.blobus.apiexterneblobus.dto.ResponseChangePinCodeDto;
import com.blobus.apiexterneblobus.exception.ChangePinCodeException;
import com.blobus.apiexterneblobus.models.enums.CustomerType;
import com.blobus.apiexterneblobus.models.enums.WalletType;
import com.blobus.apiexterneblobus.services.implementations.AccountServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "This operation allows a user profil by the msisdn (PhoneNumber) of his account ")
    @ApiResponse(responseCode = "201" ,description = "Sucessfully getted user")
    @ApiResponse(responseCode = "400",description = "Failed to get the user")
    @GetMapping("/getuserprofilebymsisdn")
    public ResponseEntity<RequestBodyUserProfileDto> getUserProfileByMsisdn(
            @Parameter(description = "The phoneNumber is required")@RequestParam String phoneNumber,
            @Parameter(description = "The walletType is required")@RequestParam WalletType walletType,
            @RequestHeader(required = true,name = "Authorization")String token){

        return ResponseEntity.ok(accountService.getUserProfileByMsisdn(phoneNumber,walletType));
    }

    @Operation(summary = "This operation allows a retailer to change its account's PinCode ")
    @ApiResponse(responseCode = "201" ,description = "The PinCode account is  sucessfully changed")
    @ApiResponse(responseCode = "400",description = "Failed to change the account's PinCode")
    @PostMapping("/changePinCode")
    ResponseEntity<ResponseChangePinCodeDto> changePinCode(

            @RequestBody RequestBodyChangePinCodeDto requestBodyChangePinCodeDto,
            @Parameter(description = "The msisdn is required") @RequestParam String msisdn,
            @Parameter(description = "The CustomerType is required but only a Retailer is allow to do this Operation") @RequestParam CustomerType customerType,
            @Parameter(description = "The WalletType is required") @RequestParam WalletType walletType,
            @RequestHeader(required = true,name = "Content_type") String content_type,@RequestHeader(required = true,name = "Authorization")String token)
            throws NoSuchPaddingException, IllegalBlockSizeException,
            IOException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeySpecException, InvalidKeyException, ChangePinCodeException {
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        return ResponseEntity.ok(transferAccountService
                .changePinCode(requestBodyChangePinCodeDto,msisdn,customerType,walletType, content_type));
    }
}

