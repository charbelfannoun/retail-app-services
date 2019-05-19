package com.retail.store.dtos;


import com.retail.store.domain.support.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO
{
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime createdDate;
    private CustomerType customerType;
}
