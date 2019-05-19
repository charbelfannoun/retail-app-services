package com.retail.store.service.rule;

import com.retail.store.dtos.CustomerDTO;

/**
 * @author Charbel.f
 *
 * Rule interface following <b>The Rules Design Pattern Michael Whelan</b>
 */
public  interface DiscountRule
{
     public static final Integer ZERO_DISCOUNT = 0;

     Integer calculateCustomerDiscount(CustomerDTO customer);

    /**
     * Customer Dicount Enumeration
     */
     enum DiscountType
     {
         EMPLOYEE_DISCOUNT(30),
         AFFILIATE_DISCOUNT(10),
         CUSTOMER_OVER_TWO_YEARS(5);

         private int percentage;

         DiscountType(int percentage)
         {
           this.percentage = percentage;
         }

         public int getPercentage()
         {
             return percentage;
         }
     }
}
