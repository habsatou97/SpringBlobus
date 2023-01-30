package com.blobus.apiExterneBlobus.services.interfaces;


import com.blobus.apiExterneBlobus.models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
  public Account createTransfertAccount(Account transferAccount);
  public List<Account> getAllTransfertAccount();
  public Optional<Account> getTransfertAccountById(Long id);
  public Account EnableTransfertAccount(Long id);
  public Account DiseableTranfertAccount(Long id);
  public String GetAccountPhoneNumber(Long id);
  public Account updateTranfertAccount(Account transferAccount, Long id);
  public Boolean deleteTransfertAccountById(Long id);

}
