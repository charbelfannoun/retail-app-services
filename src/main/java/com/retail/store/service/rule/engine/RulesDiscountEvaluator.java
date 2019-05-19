package com.retail.store.service.rule.engine;

import com.retail.store.dtos.CustomerDTO;
import com.retail.store.service.rule.AfiliateDiscountRule;
import com.retail.store.service.rule.DiscountRule;
import com.retail.store.service.rule.EmployeeDiscountRule;
import com.retail.store.service.rule.TwoYearsDiscountRule;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>
 *     RulesDiscountCalculator is the Evaluator Following <b>The Rules Design Pattern Michael Whelan</b>.<br>
 *     It holds a collection of rules that calculate discounts and loops through them to find the greatest discount.
 * </p>
 * The Rules Pattern works by separating out the rules from the rules processing logic
 *
 * @author Charbel.f
 */
@Service
public class RulesDiscountEvaluator
{
    private Set<DiscountRule> rules;

    @PostConstruct
    public void RulesDiscountEvaluator()
    {
        // Rules programmatic configuration
        this.rules = new LinkedHashSet<>();
        this.rules.add(new EmployeeDiscountRule());
        this.rules.add(new AfiliateDiscountRule());
        this.rules.add(new TwoYearsDiscountRule());
    }
    public Integer calculateDiscountPercentage(CustomerDTO customer)
    {
      Integer percentage = 0;
      for(DiscountRule rule : this.rules)
      {
          percentage = Math.max(rule.calculateCustomerDiscount(customer),percentage);
      }
      return percentage;
    }
}
