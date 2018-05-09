package com.emag;

import com.emag.config.EmagApplication;
import com.emag.config.SpringJdbcConfig;
import com.emag.exception.AddressException;
import com.emag.model.Address;
import com.emag.service.AddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmagApplication.class, SpringJdbcConfig.class})
public class AddressServiceTests {

    @Autowired
    private AddressService addressService;

    @Test(expected = AddressException.class)
    public void addAddressToUserAccount() throws AddressException {
        Long id = new Long(2);
        addressService.addAddress(id, null);
    }
}
