package com.retail.store.service.rule;

import com.retail.store.domain.support.CustomerType;
import com.retail.store.dtos.CustomerDTO;
import com.retail.store.service.rule.engine.DiscountType;

public class AfiliateDiscountRule implements DiscountRule
{
    @Override
    public Integer calculateCustomerDiscount(CustomerDTO customer)
    {
        return CustomerType.AFFILIATE.equals(customer.getCustomerType()) ?
                DiscountType.AFFILIATE_DISCOUNT.getPercentage() : 0;
    }
}