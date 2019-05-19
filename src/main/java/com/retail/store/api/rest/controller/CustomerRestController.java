package com.retail.store.api.rest.controller;

import com.retail.store.dtos.CustomerDTO;
import com.retail.store.dtos.PurchasingAmountDTO;
import com.retail.store.exceptions.BaseException;
import com.retail.store.exceptions.NotFoundException;
import com.retail.store.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author charbel.f
 * @since 19/05/2019
 */
@RestController
@RequestMapping(value = "customers")
public class CustomerRestController
{
    private static final Logger log = LoggerFactory.getLogger(CustomerRestController.class);
    private CustomerService customerService;

    public CustomerRestController(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    /**
     * Rest Resource that get the list of All store customers
     *
     * @return list of all {}
     */
    @ApiOperation(
            notes = "Returns  list of all store customers.",
            value = "Get a list of all store customers.",
            nickname = "listAll",
            tags = {"customers"})
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    public ResponseEntity<List<CustomerDTO>> listAll()
    {
        return new ResponseEntity<>(this.customerService.findAll(),HttpStatus.OK);
    }

    /**
     * Rest Resource that return single Customer by Id
     *
     * @param customerId
     * @return customer
     * @throws NotFoundException
     */
    @ApiOperation(notes = "Returns a Customer.",
            value = "Get Customer",
            nickname = "getById",
            tags = {"customers"})
    @GetMapping(value = "/{customer-id}")
    @Transactional(readOnly = true)
    public ResponseEntity<CustomerDTO> getById(@PathVariable("customer-id") Long customerId)
    throws BaseException
    {
        CustomerDTO customerDTO = this.customerService.findById(customerId);
        log.info("GET CUSTOMER- customer found with id {}: {}", customerId, customerDTO);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    /**
     * Rest Resource that return single Customer by Id
     *
     * @param customerId
     * @return customer
     * @throws NotFoundException
     */
    @ApiOperation(notes = "Returns a Customer.",
            value = "Get Customer",
            nickname = "getById",
            tags = {"customers"})
    @GetMapping(value = "{customer-id}/purchasing-order/{order-id}")
    @Transactional(readOnly = true)
    public ResponseEntity<PurchasingAmountDTO> getAmountDetail(@PathVariable("customer-id") Long customerId,@PathVariable("order-id") Long orderId)
            throws BaseException
    {
        PurchasingAmountDTO purchasingAmountDTO = this.customerService.getPurhasedAmount(customerId, orderId);
        log.info("GET PURCHASE AMOUNT DETAIL-  detail found {}", customerId, purchasingAmountDTO);
        return new ResponseEntity<>(purchasingAmountDTO, HttpStatus.OK);
    }
}
