package com.blobus.apiExterneBlobus.services.implementations;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.blobus.apiExterneBlobus.exception.ResourceNotFoundException;
import com.blobus.apiExterneBlobus.models.Account;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AccountServiceImplTest {
    @Autowired
    private AccountServiceImpl accountServiceImpl;

    /**
     * Method under test: {@link AccountServiceImpl#createRetailerTransfertAccount(Account, Long)}
     */
    @Test
    void testCreateRetailerTransfertAccount() {
        assertThrows(ResourceNotFoundException.class,
                () -> accountServiceImpl.createRetailerTransfertAccount(new Account(), 123L));
    }

    /**
     * Method under test: {@link AccountServiceImpl#createCustomerTransfertAccount(Account, Long)}
     */
    @Test
    void testCreateCustomerTransfertAccount() {
        assertThrows(ResourceNotFoundException.class,
                () -> accountServiceImpl.createCustomerTransfertAccount(new Account(), 123L));
    }

    /**
     * Method under test: {@link AccountServiceImpl#getAllTransfertAccount()}
     */
    @Test
    void testGetAllTransfertAccount() {
        assertTrue(accountServiceImpl.getAllTransfertAccount().isEmpty());
    }

    /**
     * Method under test: {@link AccountServiceImpl#getTransfertAccountById(Long)}
     */
    @Test
    void testGetTransfertAccountById() {
        assertThrows(EntityNotFoundException.class, () -> accountServiceImpl.getTransfertAccountById(123L));
    }

    /**
     * Method under test: {@link AccountServiceImpl#EnableTransfertAccount(Long)}
     */
    @Test
    void testEnableTransfertAccount() {
        assertThrows(EntityNotFoundException.class, () -> accountServiceImpl.EnableTransfertAccount(123L));
    }

    /**
     * Method under test: {@link AccountServiceImpl#DiseableTranfertAccount(Long)}
     */
    @Test
    void testDiseableTranfertAccount() {
        assertThrows(EntityNotFoundException.class, () -> accountServiceImpl.DiseableTranfertAccount(123L));
        assertThrows(EntityNotFoundException.class, () -> accountServiceImpl.DiseableTranfertAccount(3L));
    }

    /**
     * Method under test: {@link AccountServiceImpl#GetAccountPhoneNumber(Long)}
     */
    @Test
    void testGetAccountPhoneNumber() {
        assertThrows(EntityNotFoundException.class, () -> accountServiceImpl.GetAccountPhoneNumber(123L));
    }

    /**
     * Method under test: {@link AccountServiceImpl#updateTranfertAccount(Account, Long)}
     */
    @Test
    void testUpdateTranfertAccount() {
        assertThrows(EntityNotFoundException.class, () -> accountServiceImpl.updateTranfertAccount(new Account(), 123L));
    }

    /**
     * Method under test: {@link AccountServiceImpl#deleteTransfertAccountById(Long)}
     */
    @Test
    void testDeleteTransfertAccountById() {
        assertThrows(EntityNotFoundException.class, () -> accountServiceImpl.deleteTransfertAccountById(123L));
    }

    /**
     * Method under test: {@link AccountServiceImpl#addCustomerAccount(Account, Long)}
     */
    @Test
    void testAddCustomerAccount() {
        assertNull(accountServiceImpl.addCustomerAccount(new Account(), 123L));
    }

    /**
     * Method under test: {@link AccountServiceImpl#getBalance(String, String, Long)}
     */
    @Test
    void testGetBalance() {
        assertThrows(IllegalStateException.class,
                () -> accountServiceImpl.getBalance("Encrypted Pin Code", "4105551212", 1L));
        assertThrows(IllegalStateException.class,
                () -> accountServiceImpl.getBalance("This user don't existe", "4105551212", 1L));
    }

    /**
     * Method under test: {@link AccountServiceImpl#deleteByPhoneNumber(String)}
     */
    @Test
    void testDeleteByPhoneNumber() {
            accountServiceImpl.deleteByPhoneNumber("4105551212");
            assertTrue(accountServiceImpl.getAllTransfertAccount().isEmpty());
    }
}

