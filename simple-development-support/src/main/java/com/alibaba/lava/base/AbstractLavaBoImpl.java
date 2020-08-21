//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.lava.base;

import com.alibaba.lava.base.stateaction.DataResult;
import com.alibaba.lava.dal.common.Page;
import com.alibaba.lava.privilege.PrivilegeInfo;
import com.spring.simple.development.support.LavaThreadLocal;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractLavaBoImpl<D extends LavaDo, K extends LavaMapper<D, E>, E extends LavaExample> {
    protected K mapper;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected static final String SOURCE_TYPE_INSERT = "insert";
    protected static final String SOURCE_TYPE_UPDATE = "update";

    public AbstractLavaBoImpl() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public void setMapper(K mapper) {
        this.mapper = mapper;
    }

    protected void beforeSave(D dataObject, String type) {
    }

    protected void afterSave(D dataObject, String type) {
    }

    public int insert(D dataObject) {
        this.beforeSave(dataObject, "insert");
        if (this.isValidDo(dataObject)) {
            dataObject.setCreator(this.getOperator(LavaThreadLocal.getLava()));
            dataObject.setModifier(this.getOperator(LavaThreadLocal.getLava()));
            dataObject.setGmtCreate(new Date(System.currentTimeMillis()));
            dataObject.setGmtModified(new Date(System.currentTimeMillis()));
            int v = this.mapper.insertSelective(dataObject);
            this.afterSave(dataObject, "insert");
            return v;
        } else {
            throw new InvalidDoException("Invalid do:" + dataObject.toString());
        }
    }

    public int delete(Long id) {
        D record = this.mapper.selectByPrimaryKey(id);
        record.setGmtModified(new Date(System.currentTimeMillis()));
        record.setModifier(this.getOperator(LavaThreadLocal.getLava()));
        return this.mapper.deleteByPrimaryKey(record);
    }

    protected int deleteByExample(E example) {
        Map<String, Object> map = new HashMap();
        map.put("example", example);
        Map<String, Object> record = new HashMap();
        record.put("gmtModified", new Date(System.currentTimeMillis()));
        record.put("modifier", this.getOperator(LavaThreadLocal.getLava()));
        record.put("isDeleted", "y");
        map.put("record", record);
        return this.mapper.updateByExampleSelective(map);
    }

    protected List<D> selectByExample(E example) {
        return this.mapper.selectByExample(example);
    }

    protected int countByExample(E example) {
        return this.mapper.countByExample(example);
    }

    protected DataResult<D> getPageByExample(E example) {
        DataResult<D> dr = new DataResult();
        dr.setData(this.selectByExample(example));
        example.setPage((Page) null);
        dr.setCount(this.countByExample(example));
        return dr;
    }

    public D selectByPrimaryKey(Long id) {
        return (D) this.mapper.selectByPrimaryKey(id);
    }

    public int update(D dataObject) {
        this.beforeSave(dataObject, "update");
        if (this.isValidDo(dataObject)) {
            dataObject.setGmtModified(new Date(System.currentTimeMillis()));
            dataObject.setModifier(this.getOperator(LavaThreadLocal.getLava()));
            int v = this.mapper.updateByPrimaryKeySelective(dataObject);
            this.afterSave(dataObject, "update");
            return v;
        } else {
            throw new InvalidDoException("Invalid do:" + dataObject.toString());
        }
    }

    protected int updateByExample(D record, E example) {
        return this.mapper.updateByExampleSelective(record, example);
    }

    protected int updateByExample(Map<String, Object> map) {
        return this.mapper.updateByExampleSelective(map);
    }

    public boolean isValidDo(D dataObject) {
        return true;
    }

    private String getOperator(PrivilegeInfo pvgInfo) {
        return pvgInfo != null && !StringUtils.isEmpty(pvgInfo.getUserId()) ? pvgInfo.getUserId() : "SYSTEM";
    }
}
