package com.retail.store.domain;

import com.retail.store.domain.support.Identity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "PURCHASE_ORDER")
@Data
@EqualsAndHashCode(exclude = "items", callSuper = true)
@NoArgsConstructor
public class PurchaseOrder extends Identity
{
    @Column(name = "order_name")
    private String orderName;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    @OneToMany(mappedBy = "purchaseOrder")
    private Set<Item> items;

}
