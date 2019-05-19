package com.retail.store.service;

import com.retail.store.domain.Customer;
import com.retail.store.domain.PurchaseOrder;
import com.retail.store.dtos.CustomerDTO;
import com.retail.store.dtos.PurchasingAmountDTO;
import com.retail.store.exceptions.BaseException;
import com.retail.store.exceptions.NotFoundException;
import com.retail.store.repository.CustomerRepository;
import com.retail.store.repository.PurchaseOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService
{

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
    private static final String CUSTOMER_DOES_NOT_EXIST = "Customer with id %s does not exist.";
    private static final String PURCHASE_ORDER_DOES_NOT_EXIST = "Purchase order with id %s does not exist.";

    private final PurchaseAmountService purchaseAmountService;
    private final CustomerRepository customerRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    public CustomerService(PurchaseAmountService purchaseAmountService, CustomerRepository customerRepository,PurchaseOrderRepository purchaseOrderRepository)
    {
        this.purchaseAmountService = purchaseAmountService;
        this.customerRepository = customerRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Autowired


    /**
     * Get a list of all Customers
     *
     */
    public List<CustomerDTO> findAll()
    {
        return this.customerRepository .findAll()
                .stream()
                .map(customer -> new CustomerDTO(customer.getId()
                        , customer.getFirstName()
                        , customer.getLastName()
                        , customer.getCreatedDate(),
                        customer.getCustomerType()))
                .collect(Collectors.toList());
    }

    /**
     * Get the {@link CustomerDTO}, by Id
     *
     * @param id
     * @return
     */
    public CustomerDTO findById(Long id) throws NotFoundException
    {
        log.debug("Find customer by id {}", id);
        Customer customer = customerRepository.findById(id).
                orElseThrow(() -> new NotFoundException(String.format(CUSTOMER_DOES_NOT_EXIST, id)));
       return  new CustomerDTO(customer.getId()
                                ,customer.getFirstName()
                                ,customer.getLastName()
                                ,customer.getCreatedDate()
                                ,customer.getCustomerType());
    }


    public PurchasingAmountDTO getPurhasedAmount(long customerId, long purchaseOrderId)
    throws BaseException
    {
        CustomerDTO customer = this.findById(customerId);
        PurchaseOrder purchaseOrder = this.purchaseOrderRepository.findById(purchaseOrderId).
                 orElseThrow(() -> new NotFoundException(String.format(PURCHASE_ORDER_DOES_NOT_EXIST, purchaseOrderId)));

        log.info("Customer with Id {} was found, Starting Amount discounst calculation.", customerId);
        PurchasingAmountDTO purchasingAmountDTO = this.purchaseAmountService.calculateNetTotalAmount(customer,purchaseOrder.getItems());
        log.info("Customer with Id {} have a detailed purchase amount of ", customerId, purchasingAmountDTO);
        return purchasingAmountDTO;
    }
}
