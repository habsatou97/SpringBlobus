package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.repositories.TransferAccountRepository;
import com.blobus.apiExterneBlobus.services.interfaces.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
@Service


public class AccountServiceImpl implements AccountService {
    @Autowired
    private TransferAccountRepository transferAccountRepository;


    @Override
    public Account createTransfertAccount(Account transferAccount) {
        return transferAccountRepository.save(transferAccount);
    }

    @Override
    public List <Account> getAllTransfertAccount() {

        return transferAccountRepository.findAll();
    }

    @Override
    public Account getTransfertAccountById(Long id) {
        return transferAccountRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Account with id"+": "+id+ " don't exist"));
    }

    @Override
    public Account EnableTransfertAccount(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        if (existingAccount.isPresent()) {
            existingAccount.get().set_active(TRUE);
            return transferAccountRepository.save(existingAccount.get());
        }
        else throw new EntityNotFoundException("Account with id"+": "+id+ " don't exist");
    }

    @Override
    public Account DiseableTranfertAccount(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        if (existingAccount.isPresent()) {
            existingAccount.get().set_active(FALSE);
            return transferAccountRepository.save(existingAccount.get());
        }
        else throw new EntityNotFoundException("Account with id"+": "+id+ " don't exist");
    }

    @Override
    public String GetAccountPhoneNumber(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        if (existingAccount.isPresent())
            return existingAccount.get().getPhoneNumber();
        else throw new EntityNotFoundException("Account with id"+": "+id+ " don't exist");
    }

    @Override
    public Account updateTranfertAccount(Account transferAccount, Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        if (existingAccount.isPresent()) {
            existingAccount.get().setBalance(transferAccount.getBalance());
            existingAccount.get().setWalletType(transferAccount.getWalletType());
            existingAccount.get().set_active(transferAccount.is_active());
            existingAccount.get().setEncryptedPinCode(transferAccount.getEncryptedPinCode());
            return transferAccountRepository.save(existingAccount.get());
        }
        else throw new EntityNotFoundException("Account with id"+": "+id+ " don't exist");

        //return null;

    }

    @Override
    public void deleteTransfertAccountById(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        if (existingAccount.isPresent()) {
            transferAccountRepository.deleteById(id);
        }
        else throw new EntityNotFoundException("Account with id"+": "+id+ " don't exist");

    }

    public Account addCustomerAccount(Account account, Long id) {
 return null;
    }
}
