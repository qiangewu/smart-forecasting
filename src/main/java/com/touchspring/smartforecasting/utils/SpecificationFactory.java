package com.touchspring.smartforecasting.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Data
class SearchFilter {
    private String operator;
    private String key;
    private List<String> values;
    private List<SearchFilter> filters;
}

enum Operator {
    EQ, LIKE, GT, LT, GTE, LTE, IN, BT, OR, AND
}

public class SpecificationFactory<T> {

    public Predicate getPredicateByJson(Root<T> root, CriteriaBuilder builder, String json) {
        return getPredicate(root, builder, JSONObject.parseObject(json, SearchFilter.class));
    }


    private Predicate getPredicate(Root<T> root, CriteriaBuilder builder, SearchFilter searchFilter) {
        List<Predicate> predicates = new ArrayList<>();
        Operator operator = Operator.valueOf(searchFilter.getOperator());
        switch (operator) {
            case OR:
                List<Predicate> orChildPredicates = new ArrayList<>();
                for (SearchFilter filter : searchFilter.getFilters()) {
                    Predicate predicate = getPredicate(root, builder, filter);
                    orChildPredicates.add(predicate);
                }
                builder.or(orChildPredicates.toArray(new Predicate[0]));
                break;
            case AND:
                List<Predicate> andChildPredicates = new ArrayList<>();
                for (SearchFilter filter : searchFilter.getFilters()) {
                    Predicate predicate = getPredicate(root, builder, filter);
                    andChildPredicates.add(predicate);
                }
                builder.and(andChildPredicates.toArray(new Predicate[0]));
                break;
            case EQ:
                predicates.add(builder.equal(root.get(searchFilter.getKey()), searchFilter.getValues().get(0)));
                break;
            case LIKE:
                predicates.add(builder.like(root.get(searchFilter.getKey()), searchFilter.getValues().get(0)));
                break;
            case GT:
                predicates.add(builder.greaterThan(root.get(searchFilter.getKey()), searchFilter.getValues().get(0)));
                break;
            case LT:
                predicates.add(builder.lessThan(root.get(searchFilter.getKey()), searchFilter.getValues().get(0)));
                break;
            case GTE:
                predicates.add(builder.greaterThanOrEqualTo(root.get(searchFilter.getKey()), searchFilter.getValues().get(0)));
                break;
            case LTE:
                predicates.add(builder.lessThanOrEqualTo(root.get(searchFilter.getKey()), searchFilter.getValues().get(0)));
                break;
            case BT:
                predicates.add(builder.between(root.get(searchFilter.getKey()), searchFilter.getValues().get(0), searchFilter.getValues().get(1)));
                break;
            case IN:
                CriteriaBuilder.In<Object> in = builder.in(root.get(searchFilter.getKey()));
                for (String value : searchFilter.getValues()) {
                    in.value(value);
                }
                predicates.add(in);
                break;
        }
        return builder.or(predicates.toArray(new Predicate[0]));
    }
}
