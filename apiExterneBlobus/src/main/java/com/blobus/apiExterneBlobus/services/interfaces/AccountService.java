package com.blobus.apiExterneBlobus.services.interfaces;


import com.blobus.apiExterneBlobus.models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
  public Account createTransfertAccount(Account transferAccount);
  public List<Account> getAllTransfertAccount();
  public Optional<Account> getTransfertAccountById(Long id);
  //public TransferAccount EnableTransfertAccount(TransferAccount transferAccount);
  public Account updateTranfertAccount(Account transferAccount, Long id);
  public Boolean deleteTransfertAccountById(Long id);

}
