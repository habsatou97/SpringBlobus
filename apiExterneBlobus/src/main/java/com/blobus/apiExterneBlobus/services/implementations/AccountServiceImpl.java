package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.exception.ResourceNotFoundException;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.repositories.CustomerRepository;
import com.blobus.apiExterneBlobus.repositories.TransferAccountRepository;
import com.blobus.apiExterneBlobus.services.interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private TransferAccountRepository transferAccountRepository;

    @Autowired
  private  final   CustomerRepository customerRepository;

    @Override
    public Account createTransfertAccount(Account transferAccount) {
        return transferAccountRepository.save(transferAccount);
    }

    @Override
    public List<Account> getAllTransfertAccount() {

        return transferAccountRepository.findAll();
    }

    @Override
    public Optional<Account> getTransfertAccountById(Long id) {
        return transferAccountRepository.findById(id);
    }

    @Override
    public Account EnableTransfertAccount(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        if (existingAccount.isPresent()) {
            existingAccount.get().set_active(TRUE);
            return transferAccountRepository.save(existingAccount.get());
        }
        return null;
    }

    @Override
    public Account DiseableTranfertAccount(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        if (existingAccount.isPresent()) {
            existingAccount.get().set_active(FALSE);
            return transferAccountRepository.save(existingAccount.get());
        }
        return  null;
    }

    @Override
    public String GetAccountPhoneNumber(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        if (existingAccount.isPresent())
            return existingAccount.get().getPhoneNumber();
        return null;
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
        return null;

    }

    @Override
    public Boolean deleteTransfertAccountById(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        if (existingAccount.isPresent()) {
            transferAccountRepository.deleteById(id);
            return TRUE;
        }
        return FALSE;
    }

    @Override
    public Account addCustomerAccount(Account account, Long id) {

        Customer customer = customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("customer not found"));
        account.setCustomer(customer);
         Account account1 = transferAccountRepository.save(account);
         customer.addTransferAccounts(account1);
        return account1;
    }
}
