package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.exception.ResourceNotFoundException;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.repositories.CustomerRepository;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
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
    private AccountRepository transferAccountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Account createRetailerTransfertAccount(Account transferAccount,Long id) {

            User retailer=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
            if(retailer.getRoles().contains(Role.RETAILER)) {
                transferAccount.setRetailer(retailer);
                transferAccount.set_active(true);
                Account compte = transferAccountRepository.save(transferAccount);
                retailer.addTransferAccounts(compte);
                return compte;
            }
            else throw new EntityNotFoundException("Retailer with id"+": "+id+ " don't exist");
    }

    @Override
    public Account createCustomerTransfertAccount(Account transferAccount,Long id) {
        Customer customer=customerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer not found"));
        transferAccount.setCustomer(customer);
        transferAccount.set_active(true);
        Account compte=transferAccountRepository.save(transferAccount);
        customer.addTransferAccounts(compte);
        return compte;
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

    @Override
    public Account getBalance(String encryptedPinCode, String phoneNumber, Long idUser) {
        boolean userExiste = userRepository.existsById(idUser);
        if(!userExiste) throw new IllegalStateException("This user don't existe");
        User user = userRepository.findById(idUser).orElseThrow();
        if (user.getRoles().contains(Role.RETAILER)){
            return transferAccountRepository.findByEncryptedPinCodeAndPhoneNumberAndRetailer(encryptedPinCode,phoneNumber,user).orElseThrow();
        }
        throw new IllegalStateException("This user don't have a retailer role");
    }


}
