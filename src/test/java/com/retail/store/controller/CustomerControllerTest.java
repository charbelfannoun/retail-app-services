package com.retail.store.controller;

import com.retail.store.api.rest.controller.CustomerRestController;
import com.retail.store.dtos.CustomerDTO;
import com.retail.store.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest
{
    private MockMvc mvc;

    @Mock
    private CustomerService customerService;

    private List<CustomerDTO> customers = new ArrayList<>();

    @Before
    public void setUp() {

        this.mvc = standaloneSetup(new CustomerRestController(customerService))
                .alwaysExpect(status().isOk()).build();

        CustomerDTO c1 = new CustomerDTO();
        c1.setFirstName("Charbel");
        CustomerDTO c2 = new CustomerDTO();
        c1.setFirstName("Ahmad");
        CustomerDTO c3 = new CustomerDTO();
        c1.setFirstName("Bassam");
        this.customers = Arrays.asList(c1, c2, c3);

        Mockito.when(this.customerService.findAll()).thenReturn(this.customers);
    }

    @Test
    public void whenGetAllCustomersEqualSize() throws Exception
    {
        this.mvc.perform(get("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(this.customers.size()));
    }

    @Test
    public void whenGetAllCustomersReturnResponse() throws Exception
    {
        this.mvc.perform(get("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(this.customers.size()))
                .andExpect(jsonPath("$.[0].firstName").value(this.customers.get(0).getFirstName()))
                .andExpect(jsonPath("$.[1].firstName").value(this.customers.get(1).getFirstName()))
                .andExpect(jsonPath("$.[2].firstName").value(this.customers.get(2).getFirstName()));
    }
}
