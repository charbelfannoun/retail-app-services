package com.retail.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchasingAmountDTO
{

    BigDecimal totalPurchaseAmount;
    BigDecimal totalDiscountablePurchaseAmount;
    Integer    discountPercentage;
    BigDecimal discountPercentageAmount;
    BigDecimal discountHundredAmount;
    BigDecimal totalNetPurchaseAmount;


}
