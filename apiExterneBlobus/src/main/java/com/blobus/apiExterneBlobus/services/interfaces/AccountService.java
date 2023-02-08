package com.blobus.apiExterneBlobus.services.interfaces;


import com.blobus.apiExterneBlobus.dto.GetRetailerBalanceDto;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.enums.Role;

import java.util.List;

public interface AccountService {
  public Account createCustomerTransfertAccount(Account transferAccount,Long id);
  public Account createRetailerTransfertAccount(Account transferAccount,Long id);
  public List<Account> getAllTransfertAccount();
  public Account getTransfertAccountById(Long id);
  public Account EnableTransfertAccount(Long id);
  public Account DiseableTranfertAccount(Long id);
  public String GetAccountPhoneNumber(Long id);
  public Account updateTranfertAccount(Account transferAccount, Long id);
  public void deleteTransfertAccountById(Long id);
  public double getBalance(GetRetailerBalanceDto getRetailerBalanceDto);

    Account modifyTransferAccountRetailer(Long id, Account account, Role role);

    public void deleteByPhoneNumber(String phoneNumber);
}
