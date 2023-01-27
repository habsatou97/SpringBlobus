package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.models.TransferAccount;
import com.blobus.apiExterneBlobus.repositories.TransferAccountRepository;
import com.blobus.apiExterneBlobus.services.interfaces.TransferAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.TRUE;
@Service

public class TransferAccountServiceImpl implements TransferAccountService {
    @Autowired
    private TransferAccountRepository transferAccountRepository;
    @Override
    public TransferAccount createTransfertAccount(TransferAccount transferAccount) {
        return transferAccountRepository.save(transferAccount);
    }

    @Override
    public List<TransferAccount> getAllTransfertAccount() {

        return transferAccountRepository.findAll();
    }

    @Override
    public Optional<TransferAccount> getTransfertAccountById(Long id) {
        return transferAccountRepository.findById(id);
    }
/*
    @Override
    public TransferAccount EnableTransfertAccount(TransferAccount transferAccount) {
        return null;
    }*/

    @Override
    public TransferAccount updateTranfertAccount(TransferAccount transferAccount, Long id) {
        Optional<TransferAccount> existingAccount= transferAccountRepository.findById(id);
        if(existingAccount.isPresent())
        {
           existingAccount.get().setBalance(transferAccount.getBalance());
           existingAccount.get().setWalletType(transferAccount.getWalletType());
           existingAccount.get().set_active(transferAccount.is_active());
           existingAccount.get().setEncryptedPinCode(transferAccount.getEncryptedPinCode());
        }
        return transferAccountRepository.save(existingAccount.get());
    }

    @Override
    public Boolean deleteTransfertAccountById(Long id) {
        Optional<TransferAccount> existingAccount=transferAccountRepository.findById(id);
        if(existingAccount.isPresent())
            transferAccountRepository.deleteById(id);
        return TRUE;
    }
}
