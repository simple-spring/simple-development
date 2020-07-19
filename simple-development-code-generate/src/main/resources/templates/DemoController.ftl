package ${packagePath}.controller;

import com.spring.simple.development.core.annotation.base.NoLogin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author liko.wang
 * @Date 2020/4/10/010 19:14
 * @Description 控制入口
 **/
@RestController
public class DemoController {

    @NoLogin
    @RequestMapping("/help")
    @ResponseBody
    public String help() {
        return "https://github.com/likowong/spring-simple.git";
    }
}