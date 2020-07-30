package ${packagePath}.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import ${packagePath}.mapper.DemoMapper;
import ${packagePath}.model.DemoDo;
import ${packagePath}.service.DemoService;
import ${packagePath}.vo.DemoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * demo 服务实现类
 * </p>
 *
 * @author luke
 * @since 2020-07-27
 */
@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, DemoDo> implements DemoService {
    @Autowired
    private BaseSupport baseSupport;

    @Override
    @ValidHandler(key = "demoVo", value = DemoVo.class)
    public void addDemoDo(DemoVo demoVo) {
        DemoDo demoDo = baseSupport.objectCopy(demoVo, DemoDo.class);
        save(demoDo);
    }

    @Override
    public void delDemoDo(Long id) {
        removeById(id);
    }

    @Override
    public void modifyDemoDo(DemoVo demoVo) {
        DemoDo demoDo = baseSupport.objectCopy(demoVo, DemoDo.class);
        updateById(demoDo);
    }

    @Override
    public DemoVo getDemoDo(Long id) {
        DemoDo demoDo = getById(id);
        DemoVo demoVo = baseSupport.objectCopy(demoDo, DemoVo.class);
        return demoVo;
    }

    @Override
    public ResPageDTO getPageDemoDo() {
        IPage<DemoDo> page = new Page<>(1, 10);
        IPage<DemoDo> demoDoIPage = this.baseMapper.selectPage(page, null);
        ResPageDTO resPageDTO = new ResPageDTO();
        resPageDTO.setList(demoDoIPage.getRecords());
        resPageDTO.setPageSize((int) demoDoIPage.getSize());
        resPageDTO.setPageNum((int) demoDoIPage.getCurrent());
        resPageDTO.setTotalCount(demoDoIPage.getPages());
        return resPageDTO;
    }
}
