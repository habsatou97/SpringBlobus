package com.blobus.apiexterneblobus.services.interfaces;


import com.blobus.apiexterneblobus.dto.*;
import com.blobus.apiexterneblobus.models.enums.CustomerType;
import com.blobus.apiexterneblobus.models.enums.WalletType;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

public interface AccountService {
  public CreateOrEditAccountDto createCustomerTransfertAccount(CreateAccountDto transferAccount, Long id);
  public CreateOrEditAccountDto createRetailerTransfertAccount(CreateAccountDto transferAccount,Long id);
  public List<CreateOrEditAccountDto> getAllTransfertAccount();
  public Optional<CreateOrEditAccountDto> getTransfertAccountById(Long id);
  public CreateOrEditAccountDto enableTransfertAccount(Long id);
  public CreateOrEditAccountDto diseableTranfertAccount(Long id);
  public String getAccountPhoneNumber(Long id);
  public CreateOrEditAccountDto updateTranfertAccount(EditAccountDto transferAccount, Long id);
  public void deleteTransfertAccountById(Long id);
  public AmountDto getBalance(GetRetailerBalanceDto getRetailerBalanceDto)
          throws NoSuchPaddingException, IllegalBlockSizeException, IOException,
          NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
  public CreateOrEditAccountDto modifyTransferAccountRetailer(Long id, EditAccountDto account);

    public BalanceDto updatedBalance(BalanceDto balance,Long id);
  public ResponseChangePinCodeDto changePinCode(RequestBodyChangePinCodeDto requestBodyChangePinCodeDto, String msisdn, CustomerType customerType, WalletType walletType,  String content_type) throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException;

  public RequestBodyUserProfileDto getUserProfileByMsisdn(String phoneNumber, WalletType walletType);



}
