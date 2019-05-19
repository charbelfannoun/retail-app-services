package com.retail.store.service.rule;

import com.retail.store.domain.support.CustomerType;
import com.retail.store.dtos.CustomerDTO;

public class AfiliateDiscountRule implements DiscountRule
{
    @Override
    public Integer calculateCustomerDiscount(CustomerDTO customer)
    {
        return CustomerType.AFFILIATE.equals(customer.getCustomerType()) ?
                DiscountRule.DiscountType.AFFILIATE_DISCOUNT.getPercentage() : ZERO_DISCOUNT;
    }
}