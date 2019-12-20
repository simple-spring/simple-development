package com.chexin.simple.development.demo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CollectionRecordDoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CollectionRecordDoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNull() {
            addCriterion("creator_id is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNotNull() {
            addCriterion("creator_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdEqualTo(Integer value) {
            addCriterion("creator_id =", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotEqualTo(Integer value) {
            addCriterion("creator_id <>", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThan(Integer value) {
            addCriterion("creator_id >", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("creator_id >=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThan(Integer value) {
            addCriterion("creator_id <", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThanOrEqualTo(Integer value) {
            addCriterion("creator_id <=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIn(List<Integer> values) {
            addCriterion("creator_id in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotIn(List<Integer> values) {
            addCriterion("creator_id not in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdBetween(Integer value1, Integer value2) {
            addCriterion("creator_id between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("creator_id not between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifierIsNull() {
            addCriterion("modifier is null");
            return (Criteria) this;
        }

        public Criteria andModifierIsNotNull() {
            addCriterion("modifier is not null");
            return (Criteria) this;
        }

        public Criteria andModifierEqualTo(String value) {
            addCriterion("modifier =", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotEqualTo(String value) {
            addCriterion("modifier <>", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThan(String value) {
            addCriterion("modifier >", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThanOrEqualTo(String value) {
            addCriterion("modifier >=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThan(String value) {
            addCriterion("modifier <", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThanOrEqualTo(String value) {
            addCriterion("modifier <=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLike(String value) {
            addCriterion("modifier like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotLike(String value) {
            addCriterion("modifier not like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierIn(List<String> values) {
            addCriterion("modifier in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotIn(List<String> values) {
            addCriterion("modifier not in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierBetween(String value1, String value2) {
            addCriterion("modifier between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotBetween(String value1, String value2) {
            addCriterion("modifier not between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierIdIsNull() {
            addCriterion("modifier_id is null");
            return (Criteria) this;
        }

        public Criteria andModifierIdIsNotNull() {
            addCriterion("modifier_id is not null");
            return (Criteria) this;
        }

        public Criteria andModifierIdEqualTo(Integer value) {
            addCriterion("modifier_id =", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdNotEqualTo(Integer value) {
            addCriterion("modifier_id <>", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdGreaterThan(Integer value) {
            addCriterion("modifier_id >", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("modifier_id >=", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdLessThan(Integer value) {
            addCriterion("modifier_id <", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdLessThanOrEqualTo(Integer value) {
            addCriterion("modifier_id <=", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdIn(List<Integer> values) {
            addCriterion("modifier_id in", values, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdNotIn(List<Integer> values) {
            addCriterion("modifier_id not in", values, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdBetween(Integer value1, Integer value2) {
            addCriterion("modifier_id between", value1, value2, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdNotBetween(Integer value1, Integer value2) {
            addCriterion("modifier_id not between", value1, value2, "modifierId");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIsNull() {
            addCriterion("customer_name is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIsNotNull() {
            addCriterion("customer_name is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNameEqualTo(String value) {
            addCriterion("customer_name =", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotEqualTo(String value) {
            addCriterion("customer_name <>", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameGreaterThan(String value) {
            addCriterion("customer_name >", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameGreaterThanOrEqualTo(String value) {
            addCriterion("customer_name >=", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLessThan(String value) {
            addCriterion("customer_name <", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLessThanOrEqualTo(String value) {
            addCriterion("customer_name <=", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLike(String value) {
            addCriterion("customer_name like", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotLike(String value) {
            addCriterion("customer_name not like", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIn(List<String> values) {
            addCriterion("customer_name in", values, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotIn(List<String> values) {
            addCriterion("customer_name not in", values, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameBetween(String value1, String value2) {
            addCriterion("customer_name between", value1, value2, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotBetween(String value1, String value2) {
            addCriterion("customer_name not between", value1, value2, "customerName");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("`status` like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("`status` not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNameIsNull() {
            addCriterion("status_name is null");
            return (Criteria) this;
        }

        public Criteria andStatusNameIsNotNull() {
            addCriterion("status_name is not null");
            return (Criteria) this;
        }

        public Criteria andStatusNameEqualTo(String value) {
            addCriterion("status_name =", value, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameNotEqualTo(String value) {
            addCriterion("status_name <>", value, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameGreaterThan(String value) {
            addCriterion("status_name >", value, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameGreaterThanOrEqualTo(String value) {
            addCriterion("status_name >=", value, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameLessThan(String value) {
            addCriterion("status_name <", value, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameLessThanOrEqualTo(String value) {
            addCriterion("status_name <=", value, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameLike(String value) {
            addCriterion("status_name like", value, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameNotLike(String value) {
            addCriterion("status_name not like", value, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameIn(List<String> values) {
            addCriterion("status_name in", values, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameNotIn(List<String> values) {
            addCriterion("status_name not in", values, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameBetween(String value1, String value2) {
            addCriterion("status_name between", value1, value2, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameNotBetween(String value1, String value2) {
            addCriterion("status_name not between", value1, value2, "statusName");
            return (Criteria) this;
        }

        public Criteria andRecordIsNull() {
            addCriterion("record is null");
            return (Criteria) this;
        }

        public Criteria andRecordIsNotNull() {
            addCriterion("record is not null");
            return (Criteria) this;
        }

        public Criteria andRecordEqualTo(String value) {
            addCriterion("record =", value, "record");
            return (Criteria) this;
        }

        public Criteria andRecordNotEqualTo(String value) {
            addCriterion("record <>", value, "record");
            return (Criteria) this;
        }

        public Criteria andRecordGreaterThan(String value) {
            addCriterion("record >", value, "record");
            return (Criteria) this;
        }

        public Criteria andRecordGreaterThanOrEqualTo(String value) {
            addCriterion("record >=", value, "record");
            return (Criteria) this;
        }

        public Criteria andRecordLessThan(String value) {
            addCriterion("record <", value, "record");
            return (Criteria) this;
        }

        public Criteria andRecordLessThanOrEqualTo(String value) {
            addCriterion("record <=", value, "record");
            return (Criteria) this;
        }

        public Criteria andRecordLike(String value) {
            addCriterion("record like", value, "record");
            return (Criteria) this;
        }

        public Criteria andRecordNotLike(String value) {
            addCriterion("record not like", value, "record");
            return (Criteria) this;
        }

        public Criteria andRecordIn(List<String> values) {
            addCriterion("record in", values, "record");
            return (Criteria) this;
        }

        public Criteria andRecordNotIn(List<String> values) {
            addCriterion("record not in", values, "record");
            return (Criteria) this;
        }

        public Criteria andRecordBetween(String value1, String value2) {
            addCriterion("record between", value1, value2, "record");
            return (Criteria) this;
        }

        public Criteria andRecordNotBetween(String value1, String value2) {
            addCriterion("record not between", value1, value2, "record");
            return (Criteria) this;
        }

        public Criteria andRepaymentDateIsNull() {
            addCriterion("repayment_date is null");
            return (Criteria) this;
        }

        public Criteria andRepaymentDateIsNotNull() {
            addCriterion("repayment_date is not null");
            return (Criteria) this;
        }

        public Criteria andRepaymentDateEqualTo(String value) {
            addCriterion("repayment_date =", value, "repaymentDate");
            return (Criteria) this;
        }

        public Criteria andRepaymentDateNotEqualTo(String value) {
            addCriterion("repayment_date <>", value, "repaymentDate");
            return (Criteria) this;
        }

        public Criteria andRepaymentDateGreaterThan(String value) {
            addCriterion("repayment_date >", value, "repaymentDate");
            return (Criteria) this;
        }

        public Criteria andRepaymentDateGreaterThanOrEqualTo(String value) {
            addCriterion("repayment_date >=", value, "repaymentDate");
            return (Criteria) this;
        }

        public Criteria andRepaymentDateLessThan(String value) {
            addCriterion("repayment_date <", value, "repaymentDate");
            return (Criteria) this;
        }

        public Criteria andRepaymentDateLessThanOrEqualTo(String value) {
            addCriterion("repayment_date <=", value, "repaymentDate");
            return (Criteria) this;
        }

        public Criteria andRepaymentDateLike(String value) {
            addCriterion("repayment_date like", value, "repaymentDate");
            return (Criteria) this;
        }

        public Criteria andRepaymentDateNotLike(String value) {
            addCriterion("repayment_date not like", value, "repaymentDate");
            return (Criteria) this;
        }

        public Criteria andRepaymentDateIn(List<String> values) {
            addCriterion("repayment_date in", values, "repaymentDate");
            return (Criteria) this;
        }

        public Criteria andRepaymentDateNotIn(List<String> values) {
            addCriterion("repayment_date not in", values, "repaymentDate");
            return (Criteria) this;
        }

        public Criteria andRepaymentDateBetween(String value1, String value2) {
            addCriterion("repayment_date between", value1, value2, "repaymentDate");
            return (Criteria) this;
        }

        public Criteria andRepaymentDateNotBetween(String value1, String value2) {
            addCriterion("repayment_date not between", value1, value2, "repaymentDate");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodIsNull() {
            addCriterion("repayment_period is null");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodIsNotNull() {
            addCriterion("repayment_period is not null");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodEqualTo(String value) {
            addCriterion("repayment_period =", value, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodNotEqualTo(String value) {
            addCriterion("repayment_period <>", value, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodGreaterThan(String value) {
            addCriterion("repayment_period >", value, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodGreaterThanOrEqualTo(String value) {
            addCriterion("repayment_period >=", value, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodLessThan(String value) {
            addCriterion("repayment_period <", value, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodLessThanOrEqualTo(String value) {
            addCriterion("repayment_period <=", value, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodLike(String value) {
            addCriterion("repayment_period like", value, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodNotLike(String value) {
            addCriterion("repayment_period not like", value, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodIn(List<String> values) {
            addCriterion("repayment_period in", values, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodNotIn(List<String> values) {
            addCriterion("repayment_period not in", values, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodBetween(String value1, String value2) {
            addCriterion("repayment_period between", value1, value2, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodNotBetween(String value1, String value2) {
            addCriterion("repayment_period not between", value1, value2, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRecordNameIsNull() {
            addCriterion("record_name is null");
            return (Criteria) this;
        }

        public Criteria andRecordNameIsNotNull() {
            addCriterion("record_name is not null");
            return (Criteria) this;
        }

        public Criteria andRecordNameEqualTo(String value) {
            addCriterion("record_name =", value, "recordName");
            return (Criteria) this;
        }

        public Criteria andRecordNameNotEqualTo(String value) {
            addCriterion("record_name <>", value, "recordName");
            return (Criteria) this;
        }

        public Criteria andRecordNameGreaterThan(String value) {
            addCriterion("record_name >", value, "recordName");
            return (Criteria) this;
        }

        public Criteria andRecordNameGreaterThanOrEqualTo(String value) {
            addCriterion("record_name >=", value, "recordName");
            return (Criteria) this;
        }

        public Criteria andRecordNameLessThan(String value) {
            addCriterion("record_name <", value, "recordName");
            return (Criteria) this;
        }

        public Criteria andRecordNameLessThanOrEqualTo(String value) {
            addCriterion("record_name <=", value, "recordName");
            return (Criteria) this;
        }

        public Criteria andRecordNameLike(String value) {
            addCriterion("record_name like", value, "recordName");
            return (Criteria) this;
        }

        public Criteria andRecordNameNotLike(String value) {
            addCriterion("record_name not like", value, "recordName");
            return (Criteria) this;
        }

        public Criteria andRecordNameIn(List<String> values) {
            addCriterion("record_name in", values, "recordName");
            return (Criteria) this;
        }

        public Criteria andRecordNameNotIn(List<String> values) {
            addCriterion("record_name not in", values, "recordName");
            return (Criteria) this;
        }

        public Criteria andRecordNameBetween(String value1, String value2) {
            addCriterion("record_name between", value1, value2, "recordName");
            return (Criteria) this;
        }

        public Criteria andRecordNameNotBetween(String value1, String value2) {
            addCriterion("record_name not between", value1, value2, "recordName");
            return (Criteria) this;
        }

        public Criteria andRecordPhoneIsNull() {
            addCriterion("record_phone is null");
            return (Criteria) this;
        }

        public Criteria andRecordPhoneIsNotNull() {
            addCriterion("record_phone is not null");
            return (Criteria) this;
        }

        public Criteria andRecordPhoneEqualTo(String value) {
            addCriterion("record_phone =", value, "recordPhone");
            return (Criteria) this;
        }

        public Criteria andRecordPhoneNotEqualTo(String value) {
            addCriterion("record_phone <>", value, "recordPhone");
            return (Criteria) this;
        }

        public Criteria andRecordPhoneGreaterThan(String value) {
            addCriterion("record_phone >", value, "recordPhone");
            return (Criteria) this;
        }

        public Criteria andRecordPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("record_phone >=", value, "recordPhone");
            return (Criteria) this;
        }

        public Criteria andRecordPhoneLessThan(String value) {
            addCriterion("record_phone <", value, "recordPhone");
            return (Criteria) this;
        }

        public Criteria andRecordPhoneLessThanOrEqualTo(String value) {
            addCriterion("record_phone <=", value, "recordPhone");
            return (Criteria) this;
        }

        public Criteria andRecordPhoneLike(String value) {
            addCriterion("record_phone like", value, "recordPhone");
            return (Criteria) this;
        }

        public Criteria andRecordPhoneNotLike(String value) {
            addCriterion("record_phone not like", value, "recordPhone");
            return (Criteria) this;
        }

        public Criteria andRecordPhoneIn(List<String> values) {
            addCriterion("record_phone in", values, "recordPhone");
            return (Criteria) this;
        }

        public Criteria andRecordPhoneNotIn(List<String> values) {
            addCriterion("record_phone not in", values, "recordPhone");
            return (Criteria) this;
        }

        public Criteria andRecordPhoneBetween(String value1, String value2) {
            addCriterion("record_phone between", value1, value2, "recordPhone");
            return (Criteria) this;
        }

        public Criteria andRecordPhoneNotBetween(String value1, String value2) {
            addCriterion("record_phone not between", value1, value2, "recordPhone");
            return (Criteria) this;
        }

        public Criteria andRecordRelationIsNull() {
            addCriterion("record_relation is null");
            return (Criteria) this;
        }

        public Criteria andRecordRelationIsNotNull() {
            addCriterion("record_relation is not null");
            return (Criteria) this;
        }

        public Criteria andRecordRelationEqualTo(String value) {
            addCriterion("record_relation =", value, "recordRelation");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNotEqualTo(String value) {
            addCriterion("record_relation <>", value, "recordRelation");
            return (Criteria) this;
        }

        public Criteria andRecordRelationGreaterThan(String value) {
            addCriterion("record_relation >", value, "recordRelation");
            return (Criteria) this;
        }

        public Criteria andRecordRelationGreaterThanOrEqualTo(String value) {
            addCriterion("record_relation >=", value, "recordRelation");
            return (Criteria) this;
        }

        public Criteria andRecordRelationLessThan(String value) {
            addCriterion("record_relation <", value, "recordRelation");
            return (Criteria) this;
        }

        public Criteria andRecordRelationLessThanOrEqualTo(String value) {
            addCriterion("record_relation <=", value, "recordRelation");
            return (Criteria) this;
        }

        public Criteria andRecordRelationLike(String value) {
            addCriterion("record_relation like", value, "recordRelation");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNotLike(String value) {
            addCriterion("record_relation not like", value, "recordRelation");
            return (Criteria) this;
        }

        public Criteria andRecordRelationIn(List<String> values) {
            addCriterion("record_relation in", values, "recordRelation");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNotIn(List<String> values) {
            addCriterion("record_relation not in", values, "recordRelation");
            return (Criteria) this;
        }

        public Criteria andRecordRelationBetween(String value1, String value2) {
            addCriterion("record_relation between", value1, value2, "recordRelation");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNotBetween(String value1, String value2) {
            addCriterion("record_relation not between", value1, value2, "recordRelation");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNameIsNull() {
            addCriterion("record_relation_name is null");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNameIsNotNull() {
            addCriterion("record_relation_name is not null");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNameEqualTo(String value) {
            addCriterion("record_relation_name =", value, "recordRelationName");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNameNotEqualTo(String value) {
            addCriterion("record_relation_name <>", value, "recordRelationName");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNameGreaterThan(String value) {
            addCriterion("record_relation_name >", value, "recordRelationName");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNameGreaterThanOrEqualTo(String value) {
            addCriterion("record_relation_name >=", value, "recordRelationName");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNameLessThan(String value) {
            addCriterion("record_relation_name <", value, "recordRelationName");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNameLessThanOrEqualTo(String value) {
            addCriterion("record_relation_name <=", value, "recordRelationName");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNameLike(String value) {
            addCriterion("record_relation_name like", value, "recordRelationName");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNameNotLike(String value) {
            addCriterion("record_relation_name not like", value, "recordRelationName");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNameIn(List<String> values) {
            addCriterion("record_relation_name in", values, "recordRelationName");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNameNotIn(List<String> values) {
            addCriterion("record_relation_name not in", values, "recordRelationName");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNameBetween(String value1, String value2) {
            addCriterion("record_relation_name between", value1, value2, "recordRelationName");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNameNotBetween(String value1, String value2) {
            addCriterion("record_relation_name not between", value1, value2, "recordRelationName");
            return (Criteria) this;
        }

        public Criteria andOrderNumberIsNull() {
            addCriterion("order_number is null");
            return (Criteria) this;
        }

        public Criteria andOrderNumberIsNotNull() {
            addCriterion("order_number is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNumberEqualTo(String value) {
            addCriterion("order_number =", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotEqualTo(String value) {
            addCriterion("order_number <>", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberGreaterThan(String value) {
            addCriterion("order_number >", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberGreaterThanOrEqualTo(String value) {
            addCriterion("order_number >=", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberLessThan(String value) {
            addCriterion("order_number <", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberLessThanOrEqualTo(String value) {
            addCriterion("order_number <=", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberLike(String value) {
            addCriterion("order_number like", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotLike(String value) {
            addCriterion("order_number not like", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberIn(List<String> values) {
            addCriterion("order_number in", values, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotIn(List<String> values) {
            addCriterion("order_number not in", values, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberBetween(String value1, String value2) {
            addCriterion("order_number between", value1, value2, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotBetween(String value1, String value2) {
            addCriterion("order_number not between", value1, value2, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andElectricManIdIsNull() {
            addCriterion("electric_man_id is null");
            return (Criteria) this;
        }

        public Criteria andElectricManIdIsNotNull() {
            addCriterion("electric_man_id is not null");
            return (Criteria) this;
        }

        public Criteria andElectricManIdEqualTo(Long value) {
            addCriterion("electric_man_id =", value, "electricManId");
            return (Criteria) this;
        }

        public Criteria andElectricManIdNotEqualTo(Long value) {
            addCriterion("electric_man_id <>", value, "electricManId");
            return (Criteria) this;
        }

        public Criteria andElectricManIdGreaterThan(Long value) {
            addCriterion("electric_man_id >", value, "electricManId");
            return (Criteria) this;
        }

        public Criteria andElectricManIdGreaterThanOrEqualTo(Long value) {
            addCriterion("electric_man_id >=", value, "electricManId");
            return (Criteria) this;
        }

        public Criteria andElectricManIdLessThan(Long value) {
            addCriterion("electric_man_id <", value, "electricManId");
            return (Criteria) this;
        }

        public Criteria andElectricManIdLessThanOrEqualTo(Long value) {
            addCriterion("electric_man_id <=", value, "electricManId");
            return (Criteria) this;
        }

        public Criteria andElectricManIdIn(List<Long> values) {
            addCriterion("electric_man_id in", values, "electricManId");
            return (Criteria) this;
        }

        public Criteria andElectricManIdNotIn(List<Long> values) {
            addCriterion("electric_man_id not in", values, "electricManId");
            return (Criteria) this;
        }

        public Criteria andElectricManIdBetween(Long value1, Long value2) {
            addCriterion("electric_man_id between", value1, value2, "electricManId");
            return (Criteria) this;
        }

        public Criteria andElectricManIdNotBetween(Long value1, Long value2) {
            addCriterion("electric_man_id not between", value1, value2, "electricManId");
            return (Criteria) this;
        }

        public Criteria andElectricManNameIsNull() {
            addCriterion("electric_man_name is null");
            return (Criteria) this;
        }

        public Criteria andElectricManNameIsNotNull() {
            addCriterion("electric_man_name is not null");
            return (Criteria) this;
        }

        public Criteria andElectricManNameEqualTo(String value) {
            addCriterion("electric_man_name =", value, "electricManName");
            return (Criteria) this;
        }

        public Criteria andElectricManNameNotEqualTo(String value) {
            addCriterion("electric_man_name <>", value, "electricManName");
            return (Criteria) this;
        }

        public Criteria andElectricManNameGreaterThan(String value) {
            addCriterion("electric_man_name >", value, "electricManName");
            return (Criteria) this;
        }

        public Criteria andElectricManNameGreaterThanOrEqualTo(String value) {
            addCriterion("electric_man_name >=", value, "electricManName");
            return (Criteria) this;
        }

        public Criteria andElectricManNameLessThan(String value) {
            addCriterion("electric_man_name <", value, "electricManName");
            return (Criteria) this;
        }

        public Criteria andElectricManNameLessThanOrEqualTo(String value) {
            addCriterion("electric_man_name <=", value, "electricManName");
            return (Criteria) this;
        }

        public Criteria andElectricManNameLike(String value) {
            addCriterion("electric_man_name like", value, "electricManName");
            return (Criteria) this;
        }

        public Criteria andElectricManNameNotLike(String value) {
            addCriterion("electric_man_name not like", value, "electricManName");
            return (Criteria) this;
        }

        public Criteria andElectricManNameIn(List<String> values) {
            addCriterion("electric_man_name in", values, "electricManName");
            return (Criteria) this;
        }

        public Criteria andElectricManNameNotIn(List<String> values) {
            addCriterion("electric_man_name not in", values, "electricManName");
            return (Criteria) this;
        }

        public Criteria andElectricManNameBetween(String value1, String value2) {
            addCriterion("electric_man_name between", value1, value2, "electricManName");
            return (Criteria) this;
        }

        public Criteria andElectricManNameNotBetween(String value1, String value2) {
            addCriterion("electric_man_name not between", value1, value2, "electricManName");
            return (Criteria) this;
        }

        public Criteria andCreatorLikeInsensitive(String value) {
            addCriterion("upper(creator) like", value.toUpperCase(), "creator");
            return (Criteria) this;
        }

        public Criteria andModifierLikeInsensitive(String value) {
            addCriterion("upper(modifier) like", value.toUpperCase(), "modifier");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLikeInsensitive(String value) {
            addCriterion("upper(customer_name) like", value.toUpperCase(), "customerName");
            return (Criteria) this;
        }

        public Criteria andPhoneLikeInsensitive(String value) {
            addCriterion("upper(phone) like", value.toUpperCase(), "phone");
            return (Criteria) this;
        }

        public Criteria andStatusLikeInsensitive(String value) {
            addCriterion("upper(`status`) like", value.toUpperCase(), "status");
            return (Criteria) this;
        }

        public Criteria andStatusNameLikeInsensitive(String value) {
            addCriterion("upper(status_name) like", value.toUpperCase(), "statusName");
            return (Criteria) this;
        }

        public Criteria andRecordLikeInsensitive(String value) {
            addCriterion("upper(record) like", value.toUpperCase(), "record");
            return (Criteria) this;
        }

        public Criteria andRepaymentDateLikeInsensitive(String value) {
            addCriterion("upper(repayment_date) like", value.toUpperCase(), "repaymentDate");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodLikeInsensitive(String value) {
            addCriterion("upper(repayment_period) like", value.toUpperCase(), "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRecordNameLikeInsensitive(String value) {
            addCriterion("upper(record_name) like", value.toUpperCase(), "recordName");
            return (Criteria) this;
        }

        public Criteria andRecordPhoneLikeInsensitive(String value) {
            addCriterion("upper(record_phone) like", value.toUpperCase(), "recordPhone");
            return (Criteria) this;
        }

        public Criteria andRecordRelationLikeInsensitive(String value) {
            addCriterion("upper(record_relation) like", value.toUpperCase(), "recordRelation");
            return (Criteria) this;
        }

        public Criteria andRecordRelationNameLikeInsensitive(String value) {
            addCriterion("upper(record_relation_name) like", value.toUpperCase(), "recordRelationName");
            return (Criteria) this;
        }

        public Criteria andOrderNumberLikeInsensitive(String value) {
            addCriterion("upper(order_number) like", value.toUpperCase(), "orderNumber");
            return (Criteria) this;
        }

        public Criteria andElectricManNameLikeInsensitive(String value) {
            addCriterion("upper(electric_man_name) like", value.toUpperCase(), "electricManName");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}