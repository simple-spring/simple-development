package ${packagePath}.service.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.annotation.base.swagger.Api;
import com.spring.simple.development.core.annotation.base.swagger.ApiImplicitParam;
import com.spring.simple.development.core.annotation.base.swagger.ApiOperation;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import ${packagePath}.mapper.DemoDoMapperExt;
import ${packagePath}.model.DemoDo;
import ${packagePath}.model.DemoDoExample;
import ${packagePath}.service.TestDemoBo;
import ${packagePath}.vo.DemoVo;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@IsApiService
@Api(tags = "用户相关")
public class TestDemoBoImpl extends AbstractLavaBoImpl<DemoDo, DemoDoMapperExt, DemoDoExample> implements TestDemoBo {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(DemoDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    @ApiOperation(value = "插入", notes = "插入一亿个用户")
    @ApiImplicitParam(name = "demoVo", description = "用户vo")
    public void insertData(DemoVo demoVo) {
        DemoDo demoDo = baseSupport.objectCopy(demoVo, DemoDo.class);
        insert(demoDo);
    }


    @Override
    @ApiOperation(value = "删除", notes = "删除一亿个订单")
    @ValidHandler(key = "demoVo", value = DemoVo.class, isReqBody = false)
    public void deleteData(Long id) {
        DemoDo dbDemoDo = selectByPrimaryKey(id);
        if (dbDemoDo == null) {
            throw new GlobalException(ResponseCode.RES_DATA_NOT_EXIST, "数据不存在");
        }
        delete(id);
    }

    @Override
    @ApiOperation(value = "更新", notes = "更新一亿个用户")
    @ValidHandler(key = "demoVo", value = DemoVo.class, isReqBody = false)
    public void modifyData(DemoVo demoVo) {
        DemoDo dbDemoDo = selectByPrimaryKey(demoVo.getId());
        if (dbDemoDo == null) {
            throw new GlobalException(ResponseCode.RES_DATA_NOT_EXIST, "数据不存在");
        }
        DemoDo demoDo = baseSupport.objectCopy(demoVo, DemoDo.class);
        update(demoDo);
    }

    @Override
    @ApiOperation(value = "查询", notes = "查询一亿个用户")
    @ApiImplicitParam(name = "demoVo", description = "用户vo", resultDataType = DemoVo.class)
    public DemoVo getData(Long id) {
        DemoDo dbDemoDo = selectByPrimaryKey(id);
        if (dbDemoDo == null) {
            throw new GlobalException(ResponseCode.RES_DATA_NOT_EXIST, "数据不存在");
        }
        return baseSupport.objectCopy(dbDemoDo, DemoVo.class);
    }
}