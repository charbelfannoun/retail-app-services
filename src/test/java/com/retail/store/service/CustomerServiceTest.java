package com.retail.store.service;


import com.retail.store.domain.Customer;
import com.retail.store.domain.support.CustomerType;
import com.retail.store.dtos.CustomerDTO;
import com.retail.store.exceptions.NotFoundException;
import com.retail.store.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest
{

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void whenFindAllCustomers() {
        List<Customer> customers = new ArrayList<>(3);
        customers.add(new Customer("John", "Employee", CustomerType.EMPLOYEE));
        customers.add(new Customer("Tom", "Affiliate", CustomerType.AFFILIATE));
        customers.add(new Customer("Georges", "Customer With More Than 2 years", CustomerType.CUSTOMER));

        when(this.customerRepository.findAll()).thenReturn(customers);
        List<CustomerDTO> listItems = this.customerService.findAll();
        Assert.assertEquals(3, listItems.size());
        Assert.assertEquals("John", listItems.get(0).getFirstName());
        Assert.assertEquals("Employee", listItems.get(0).getLastName());
        Assert.assertEquals(CustomerType.CUSTOMER, listItems.get(2).getCustomerType());
    }

    @Test
    public void whenFindCustomerByIdReturnResponse() throws NotFoundException
    {
        Long id = 1L;
        Customer customer = new Customer("John", "Employee", CustomerType.EMPLOYEE);
        when(this.customerRepository.findById(id)).thenReturn(Optional.of(customer));

        CustomerDTO response = this.customerService.findById(id);
        Assert.assertEquals(customer.getId(), response.getId());
        Assert.assertEquals(customer.getFirstName(), response.getFirstName());
        Assert.assertEquals(customer.getCustomerType(), response.getCustomerType());
    }

    @Test(expected = NotFoundException.class)
    public void whenFindCustomerByIddWillNotReturnResponse() throws NotFoundException
    {
        Long id = 1L;
        when(this.customerRepository.findById(id)).thenReturn(Optional.empty());
        this.customerService.findById(id);
    }
}
