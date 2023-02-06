package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.exception.ResourceNotFoundException;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.models.enums.WalletType;
import com.blobus.apiExterneBlobus.repositories.CustomerRepository;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import com.blobus.apiExterneBlobus.services.interfaces.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

        User retailer = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Account> comptes = retailer.getAccounts();
        int i = 0;
        /* verifier si le retailer à deja un compte de tranfert
         si la collection compts est vide
         on ajoute le compte directement
        */
        if (comptes.isEmpty()) {
            // verifie si l'utlisateur est un retailer
            if (retailer.getRoles().contains(Role.RETAILER)) {
                transferAccount.setRetailer(retailer);
                transferAccount.set_active(true);
                Account compte = transferAccountRepository.save(transferAccount);
                retailer.addTransferAccounts(compte);
                return compte;
            } else throw new EntityNotFoundException("Retailer with id" + ": " + id + " don't exist");

        }
        // Si le retailer a deja au moins un compte :
        else {

            for (Account cpt : comptes ) {
                WalletType type = cpt.getWalletType();
                if (type == transferAccount.getWalletType())
                    i++;
            }
            if (i != 0) {
                //System.out.println("Ce customer possede deja un compte de ce type");
                throw  new IllegalStateException("Ce retailer possede deja un compte de ce type");
            } else {
                if (retailer.getRoles().contains(Role.RETAILER)) {
                    transferAccount.setRetailer(retailer);
                    transferAccount.set_active(true);
                    Account compte = transferAccountRepository.save(transferAccount);
                    retailer.addTransferAccounts(compte);
                    return compte;
                } else throw new EntityNotFoundException("Retailer with id" + ": " + id + " don't exist");
            }
        }

    }

    @Override
    public Account createCustomerTransfertAccount(Account transferAccount,Long id) {

        //List<Account> comptes=new ArrayList<>();
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        List<Account> comptes = customer.getTransferAccounts();
        int i = 0;
        for (Account cpt : comptes
        ) {
            WalletType type=cpt.getWalletType();
            if(type==transferAccount.getWalletType())
                i++;
        }
        if (i != 0) {
            throw new IllegalStateException("Ce customer : "  +id + " Possede deja un compte de ce type");
            //System.out.println("Ce customer possede deja un compte de ce type");
            //return null;
        } else {

            transferAccount.setCustomer(customer);
            transferAccount.set_active(true);
            Account compte = transferAccountRepository.save(transferAccount);
            customer.addTransferAccounts(compte);
            return compte;
        }
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

    /**
     * Cette methode permet a un administrateur de mettre à jour le compte de tranfert d'un retailer
     * @param id
     * @param account
     * @param role
     * @return
     */
    @Override
    public Account modifyTransferAccountRetailer(Long id, Account account, Role role) {
        Account account1 = transferAccountRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Account not found"));
        if(account1.getRetailer().getRoles().contains(Role.RETAILER)){
            account1.setBalance(account.getBalance());
            account1.setPhoneNumber(account.getPhoneNumber());
            account1.setWalletType(account1.getWalletType());
            account1.setEncryptedPinCode(account.getEncryptedPinCode());
            return transferAccountRepository.saveAndFlush(account1);
        }
        return null;
    }
}
