package com.retail.store;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RetailDiscountApplicationTest
{
    @Test
    public void bootContextTest() {
        RetailDiscountApplication.main(new String[] {});
        assertTrue("I am uo, Means application started successfully", true);
    }
}
