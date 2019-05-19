package com.retail.store.service;

import com.retail.store.domain.Item;
import com.retail.store.domain.support.ItemType;
import com.retail.store.dtos.CustomerDTO;
import com.retail.store.dtos.PurchasingAmountDTO;
import com.retail.store.service.rule.engine.RulesDiscountEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

@Service
public class PurchaseAmountService
{
    private Logger log = LoggerFactory.getLogger(PurchaseAmountService.class);

    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    private static final BigDecimal SPENDING_OVER_100 = BigDecimal.valueOf(5);
    private RulesDiscountEvaluator rulesEvaluator;

    @Autowired
    public PurchaseAmountService(RulesDiscountEvaluator rulesEvaluator)
    {
        this.rulesEvaluator = rulesEvaluator;
    }

    public PurchasingAmountDTO calculateNetTotalAmount(CustomerDTO customer, Set<Item> purchasedItems)
    {
       BigDecimal totalPurchaseAmount = this.calculateTotalAmount(purchasedItems);
       log.debug("calculate Net Total Amount, totalPurchaseAmount {}", totalPurchaseAmount);
       BigDecimal totalDiscountablePurchaseAmount = this.calculateTotalDiscountableAmount(purchasedItems);
       log.debug("calculate Net Total Amount, totalDiscountablePurchaseAmount {}", totalDiscountablePurchaseAmount);
       Integer    discountPercentage = Integer.valueOf(0);
       BigDecimal discountPercentageAmount = BigDecimal.ZERO;
       BigDecimal discountHundredAmount =  BigDecimal.ZERO;

        if(!BigDecimal.ZERO.equals(totalDiscountablePurchaseAmount))
        {
           discountPercentage = this.rulesEvaluator.calculateDiscountPercentage(customer);
           log.debug("calculate Net Total Amount, discountPercentage {}", discountPercentage);
           discountPercentageAmount = this.calculatePercentage(discountPercentage,totalDiscountablePurchaseAmount);
           log.debug("calculate Net Total Amount, discountPercentageAmount {}", discountPercentageAmount);
           discountHundredAmount  = totalPurchaseAmount.compareTo(HUNDRED) > 0
                   ?totalPurchaseAmount.divide(HUNDRED, 0, RoundingMode.DOWN).multiply(SPENDING_OVER_100)
                   :BigDecimal.ZERO;
            log.debug("calculate Net Total Amount, discountHundredAmount {}", discountHundredAmount);
        }
        else
        {
            log.info("Total discountable items amount is 0");
        }

        BigDecimal totalNetPurchaseAmount = totalPurchaseAmount.subtract(discountPercentageAmount).subtract(discountHundredAmount);
        log.debug("calculate Net Total Amount, totalNetPurchaseAmount {}", totalNetPurchaseAmount);
        return new PurchasingAmountDTO( totalPurchaseAmount,
                                        totalDiscountablePurchaseAmount,
                                        discountPercentage,
                                        discountPercentageAmount,
                                        discountHundredAmount,
                                        totalNetPurchaseAmount
                                       );

    }

    /**
     * Calculate Total items Amount
     * @param purchasedItems
     * @return
     */
    private BigDecimal calculateTotalAmount(Set<Item> purchasedItems)
    {
        return  purchasedItems.stream().map(Item::getItemPrice).
                reduce(BigDecimal::add).orElse(BigDecimal.valueOf(0));

    }

    /**
     * Calculate total items amount that discount apply as a product
     * @param purchasedItems
     * @return
     */
    private BigDecimal calculateTotalDiscountableAmount(Set<Item> purchasedItems)
    {
        return purchasedItems.stream()
                .filter(item -> !item.getItemType().equals(ItemType.GROCERY))
                .map(Item::getItemPrice)
                .reduce(BigDecimal::add).orElse(BigDecimal.valueOf(0));
    }

    /**
     * Calculate percentage
     * @param percent
     * @param total
     * @return
     */
    private BigDecimal calculatePercentage(Integer percent, BigDecimal total)
    {
        return total.multiply(BigDecimal.valueOf(percent)).divide(HUNDRED, 10, RoundingMode.FLOOR);
    }
}
