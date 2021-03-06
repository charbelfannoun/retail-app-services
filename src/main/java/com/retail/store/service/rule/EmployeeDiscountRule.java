package com.retail.store.service.rule;

import com.retail.store.domain.support.CustomerType;
import com.retail.store.dtos.CustomerDTO;
import com.retail.store.service.rule.engine.DiscountType;

public class EmployeeDiscountRule implements  DiscountRule
{
    @Override
    public Integer calculateCustomerDiscount(CustomerDTO customer)
    {
        return CustomerType.EMPLOYEE.equals(customer.getCustomerType())
                ? DiscountType.EMPLOYEE_DISCOUNT.getPercentage():0;
    }
}
