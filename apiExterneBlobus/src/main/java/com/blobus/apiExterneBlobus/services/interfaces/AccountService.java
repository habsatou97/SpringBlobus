package com.blobus.apiExterneBlobus.services.interfaces;


import com.blobus.apiExterneBlobus.dto.BalanceDto;
import com.blobus.apiExterneBlobus.dto.CreateOrEditAccountDto;
import com.blobus.apiExterneBlobus.dto.GetRetailerBalanceDto;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.enums.Role;

import java.util.List;
import java.util.Optional;

public interface AccountService {
  public CreateOrEditAccountDto createCustomerTransfertAccount(CreateOrEditAccountDto transferAccount, Long id);
  public CreateOrEditAccountDto createRetailerTransfertAccount(CreateOrEditAccountDto transferAccount,Long id);
  public List<CreateOrEditAccountDto> getAllTransfertAccount();
  public Optional<CreateOrEditAccountDto> getTransfertAccountById(Long id);
  public CreateOrEditAccountDto EnableTransfertAccount(Long id);
  public CreateOrEditAccountDto DiseableTranfertAccount(Long id);
  public String GetAccountPhoneNumber(Long id);
  public CreateOrEditAccountDto updateTranfertAccount(CreateOrEditAccountDto transferAccount, Long id);
  public void deleteTransfertAccountById(Long id);
  public double getBalance(GetRetailerBalanceDto getRetailerBalanceDto);
  public CreateOrEditAccountDto modifyTransferAccountRetailer(Long id, CreateOrEditAccountDto account, Role role);
  public void deleteByPhoneNumber(String phoneNumber);

    public BalanceDto updatedBalance(BalanceDto balance,Long id);
}
