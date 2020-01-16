package com.spring.simple.development.core.component.swagger;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liko.wang
 * @Date 2020/1/15/015 19:37
 * @Description 构造代码模板
 **/
public class CodeGenerationHandler {


    private static String baseCodeTemplate = "package com.spring.simple.development.core.component.mvc.controller;\n" +
            "\n" +
            "import com.spring.simple.development.core.baseconfig.isapiservice.ServiceInvoke;\n" +
            "import com.spring.simple.development.core.component.mvc.req.ReqBody;\n" +
            "import com.spring.simple.development.core.component.mvc.req.RpcRequest;\n" +
            "import com.spring.simple.development.core.component.mvc.res.ResBody;\n" +
            "import com.spring.simple.development.support.utils.DateUtils;\n" +
            "import com.spring.simple.development.support.utils.HttpRequestUtil;\n" +
            "import org.apache.logging.log4j.LogManager;\n" +
            "import org.apache.logging.log4j.Logger;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.web.bind.annotation.RequestMapping;\n" +
            "import org.springframework.web.bind.annotation.RequestMethod;\n" +
            "import org.springframework.web.bind.annotation.RestController;\n" +
            "\n" +
            "import javax.servlet.http.HttpServletRequest;"+
            "\n" +
            "\n" +
            "/**\n" +
            " * swagger接口\n" +
            " *\n" +
            " * @author liko\n" +
            " */\n" +
            "@RestController\n" +
            "public class %sController {\n" +
            "    private static final Logger logger = LogManager.getLogger(%sController.class);\n" +
            "\n" +
            "    @Autowired\n" +
            "    private ServiceInvoke serviceInvoke;\n" +
            "\n";
    private static String methodTemplate = "@RequestMapping(value = \"%s\", method = RequestMethod.POST)\n" +
            "    public ResBody %s(HttpServletRequest request, ReqBody reqBody) throws Throwable {\n" +
            "        RpcRequest rpcRequest = new RpcRequest();\n" +
            "        rpcRequest.setServiceName(\"%s\");\n" +
            "        rpcRequest.setMethodName(\"%s\");\n" +
            "        rpcRequest.setReqBody(reqBody);\n" +
            "        String paramJson = HttpRequestUtil.readStringFromRequestBody(request);\n" +
            "        logger.info(\"requestJson:\" + paramJson + \" date:\" + DateUtils.getCurrentTime());\n" +
            "        return serviceInvoke.%s(rpcRequest, request);\n" +
            "    }";
    private static String endCode = "}";

    /**
     * @param className
     * @param methodListParams
     * @return java.lang.String
     * @author liko.wang
     * @Date 2020/1/15/015 20:35
     * @Description 获取模板代码
     **/
    public static String getBaseCode(String className, List<String> methodListParams) {
        List<String> baseCodeParams = new ArrayList<>();
        baseCodeParams.add(className);
        baseCodeParams.add(className);
        String[] baseCodeParamsStr = baseCodeParams.toArray(new String[baseCodeParams.size()]);
        String baseCode = String.format(baseCodeTemplate, baseCodeParamsStr);

        String methodCodes = "";
        for (String methodParams : methodListParams) {
            String[] params = methodParams.split(",");

            String[] methodTemplateParams = new String[5];
            methodTemplateParams[0] = params[0];
            methodTemplateParams[1] = params[2];
            methodTemplateParams[2] = params[1];
            methodTemplateParams[3] = params[2];
            methodTemplateParams[4] = params[3];

            methodCodes += String.format(methodTemplate, methodTemplateParams);
        }
        return baseCode + "\n" + methodCodes + "\n" + endCode;

    }
}