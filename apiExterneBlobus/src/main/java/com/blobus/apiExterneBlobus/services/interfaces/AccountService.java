package com.blobus.apiExterneBlobus.services.interfaces;


import com.blobus.apiExterneBlobus.dto.*;
import com.blobus.apiExterneBlobus.models.enums.CustomerType;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.models.enums.WalletType;

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
  public CreateOrEditAccountDto createCustomerTransfertAccount(CreateOrEditAccountDto transferAccount, Long id);
  public CreateOrEditAccountDto createRetailerTransfertAccount(CreateOrEditAccountDto transferAccount,Long id);
  public List<CreateOrEditAccountDto> getAllTransfertAccount();
  public Optional<CreateOrEditAccountDto> getTransfertAccountById(Long id);
  public Optional<CreateOrEditAccountDto>  geTransferAccountByMsisdn(String msisdn);
  public CreateOrEditAccountDto enableTransfertAccount(Long id);
  public CreateOrEditAccountDto diseableTranfertAccount(Long id);
  public String getAccountPhoneNumber(Long id);
  public CreateOrEditAccountDto updateTranfertAccount(CreateOrEditAccountDto transferAccount, Long id);
  public void deleteTransfertAccountById(Long id);
  public double getBalance(GetRetailerBalanceDto getRetailerBalanceDto) throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
  public CreateOrEditAccountDto modifyTransferAccountRetailer(Long id, CreateOrEditAccountDto account, Role role);
  public void deleteByPhoneNumber(String phoneNumber);

    public BalanceDto updatedBalance(BalanceDto balance,Long id);
  public ResponseChangePinCodeDto changePinCode(RequestBodyChangePinCodeDto requestBodyChangePinCodeDto, String msisdn, CustomerType customerType, WalletType walletType) throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException;
}
