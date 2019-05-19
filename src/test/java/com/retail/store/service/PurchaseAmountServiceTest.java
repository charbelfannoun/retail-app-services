package com.retail.store.service;

import com.retail.store.domain.Item;
import com.retail.store.domain.support.CustomerType;
import com.retail.store.domain.support.ItemType;
import com.retail.store.dtos.CustomerDTO;
import com.retail.store.dtos.PurchasingAmountDTO;
import com.retail.store.service.rule.engine.RulesDiscountEvaluator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseAmountServiceTest
{
    @Mock
    CustomerDTO mockCustomer;

    @Mock
    private RulesDiscountEvaluator rulesDiscountEvaluator;

    @InjectMocks
    private PurchaseAmountService purchaseAmountService;

    private Set<Item> purchasedItems;

    @Before
    public void setUp()
    {
        this.purchasedItems = new LinkedHashSet<>();
        purchasedItems.add(new Item("G-1", ItemType.GROCERY, BigDecimal.valueOf(100.23)));
        purchasedItems.add(new Item("G-2",ItemType.MEAT ,BigDecimal.valueOf(33.28)));
        purchasedItems.add(new Item("G-3",ItemType.GROCERY, BigDecimal.valueOf(50.23)));
        purchasedItems.add(new Item("G-4",ItemType.OTHER, BigDecimal.valueOf(200.45)));
    }

    @Test
    public void calculateDiscountForEmployee() {
        when(mockCustomer.getCustomerType()).thenReturn(CustomerType.EMPLOYEE);
        PurchasingAmountDTO purchasingAmountDTO = this.purchaseAmountService.calculateNetTotalAmount(this.mockCustomer, this.purchasedItems);
        long discountPerc = new Long(purchasingAmountDTO.getDiscountPercentage()) ;
        Assert.assertEquals(discountPerc, 30L);
        Assert.assertEquals(purchasingAmountDTO.getDiscountPercentageAmount().stripTrailingZeros(), BigDecimal.valueOf(70).stripTrailingZeros());

    }

}
