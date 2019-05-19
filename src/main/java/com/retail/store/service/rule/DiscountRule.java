package com.retail.store.service.rule;

import com.retail.store.dtos.CustomerDTO;

/**
 * @author Charbel.f
 *
 * Rule interface following <b>The Rules Design Pattern Michael Whelan</b>
 */
public  interface DiscountRule
{
     Integer calculateCustomerDiscount(CustomerDTO customer);
}
