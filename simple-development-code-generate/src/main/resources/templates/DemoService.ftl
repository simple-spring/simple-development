package ${packagePath}.service;

import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import ${packagePath}.model.DemoDo;
import com.baomidou.mybatisplus.extension.service.IService;
import ${packagePath}.vo.DemoVo;

/**
* <p>
    * demo 服务类
    * </p>
*
* @author luke
* @since 2020-07-27
*/
public interface DemoService extends IService<DemoDo> {
    /**
    * 新增数据
    *
    * @param demoVo
    */
    void addDemoDo(DemoVo demoVo);

    /**
    * 删除数据
    *
    * @param id
    */
    void delDemoDo(Long id);

    /**
    * 修改数据
    *
    * @param demoVo
    */
    void modifyDemoDo(DemoVo demoVo);

    /**
    * 获取一个数据
    *
    * @param id
    * @return
    */
    DemoVo getDemoDo(Long id);

    /**
    * 获取分页数据
    *
    * @return
    */
    ResPageDTO getPageDemoDo();
    }
