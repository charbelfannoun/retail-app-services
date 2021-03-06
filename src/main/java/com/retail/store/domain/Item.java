package com.retail.store.domain;

import com.retail.store.domain.support.Identity;
import com.retail.store.domain.support.ItemType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ITEM")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Item extends Identity
{

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_type")
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(name = "item_price")
    private BigDecimal itemPrice;

    @ManyToOne
    @JoinColumn
    private PurchaseOrder purchaseOrder;

    public Item(String itemName, ItemType itemType, BigDecimal itemPrice)
    {
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
    }
}
