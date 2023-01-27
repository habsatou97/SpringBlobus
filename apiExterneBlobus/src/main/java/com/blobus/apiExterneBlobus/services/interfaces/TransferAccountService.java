package com.blobus.apiExterneBlobus.services.interfaces;


import com.blobus.apiExterneBlobus.models.TransferAccount;

import java.util.List;
import java.util.Optional;

public interface TransferAccountService {
  public TransferAccount createTransfertAccount(TransferAccount transferAccount);
  public List<TransferAccount> getAllTransfertAccount();
  public Optional<TransferAccount> getTransfertAccountById(Long id);
  //public TransferAccount EnableTransfertAccount(TransferAccount transferAccount);
  public TransferAccount updateTranfertAccount(TransferAccount transferAccount,Long id);
  public Boolean deleteTransfertAccountById(Long id);

}
