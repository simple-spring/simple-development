package com.spring.simple.development.core.component.swagger;


import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liko.wang
 * @Date 2020/1/15/015 19:37
 * @Description 构造代码模板
 **/
public class CodeGenerationHandler {

    /**
     * 兼容参数类型转换成小写与原型数据重合
     */
    public static Map<String, String> baseType = new HashMap<>();

    static {
        baseType.put("byte", "simpleByte");
        baseType.put("short", "simpleShort");
        baseType.put("int", "simpleInt");
        baseType.put("long", "simpleLong");
        baseType.put("float", "simpleFloat");
        baseType.put("double", "simpleDouble");
        baseType.put("boolean", "simpleBoolean");
        baseType.put("char", "simpleChar");
        baseType.put("character", "simpleCharacter");
        baseType.put("integer", "simpleInteger");

    }


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
            "import org.springframework.web.bind.annotation.RequestBody;\n" +
            "import org.springframework.web.bind.annotation.RestController;\n" +
            "import com.alibaba.fastjson.JSONObject;\n" +
            "import io.swagger.annotations.*;\n" +
            "import com.spring.simple.development.core.annotation.base.NoLogin;\n" +
            "\n" +
            "import javax.servlet.http.HttpServletRequest;" +
            "import java.util.HashMap;\n" +
            "import java.util.Map;\n" +
            "%s" +
            "\n" +
            "\n" +
            "/**\n" +
            " * swagger接口\n" +
            " *\n" +
            " * @author liko\n" +
            " */\n" +
            "@RestController\n" +
            "@Api(tags = \"%s\")\n" +
            "public class %sController {\n" +
            "    private static final Logger logger = LogManager.getLogger(%sController.class);\n" +
            "\n" +
            "    @Autowired\n" +
            "    private ServiceInvoke serviceInvoke;\n" +
            "\n";
    private static String methodTemplate = "" +
            "    %s\n" +
            "    @RequestMapping(value = \"%s\", method = RequestMethod.POST)\n" +
            "    @ApiOperation(value=\"%s\", notes=\"%s\")\n" +
            //"    @ApiImplicitParam(name = \"%s\", value = \"%s\", paramType = \"body\", allowMultiple = true, dataType = \"%s\")\n" +
            "    public ResBody%s %s( %s) throws Throwable {\n" +
            "        logger.info(\"requestJson:\" + JSONObject.toJSONString(%s) + \" date:\" + DateUtils.getCurrentTime());\n" +
            "        RpcRequest rpcRequest = new RpcRequest();\n" +
            "        rpcRequest.setServiceName(\"%s\");\n" +
            "        rpcRequest.setMethodName(\"%s\");\n" +
            "        %s\n" +
            "        Map<String,Object> params= new HashMap<>();\n" +
            "        %s\n" +
            "        reqBody.setParamsMap(params);\n" +
            "        rpcRequest.setReqBody(reqBody);\n" +
            "        return serviceInvoke.%s(rpcRequest);\n" +
            "    }";
    private static String endCode = "}";

    /**
     * @param codeGenerationParams
     * @return java.lang.String
     * @author liko.wang
     * @Date 2020/1/15/015 20:35
     * @Description 获取模板代码
     **/
    public static String getBaseCode(CodeGenerationParams codeGenerationParams) {
        List<String> baseCodeParams = new ArrayList<>();
        baseCodeParams.add(codeGenerationParams.getParamTypePackagePath());
        baseCodeParams.add(codeGenerationParams.getClassTags());
        baseCodeParams.add(codeGenerationParams.getServiceName());
        baseCodeParams.add(codeGenerationParams.getServiceName());
        String[] baseCodeParamsStr = baseCodeParams.toArray(new String[baseCodeParams.size()]);
        String baseCode = String.format(baseCodeTemplate, baseCodeParamsStr);

        String methodCodes = "";
        for (CodeGenerationMethodParams codeGenerationMethodParams : codeGenerationParams.getCodeGenerationMethodParams()) {
            List<String> baseCodeMethodParams = new ArrayList<>();
            baseCodeMethodParams.add(codeGenerationMethodParams.getIsLogin());
            baseCodeMethodParams.add(codeGenerationMethodParams.getMappingUrl());
            baseCodeMethodParams.add(codeGenerationMethodParams.getApiOperationValue());
            baseCodeMethodParams.add(codeGenerationMethodParams.getApiOperationValueNotes());
            //baseCodeMethodParams.add(codeGenerationMethodParams.getApiImplicitParamName());
            //baseCodeMethodParams.add(codeGenerationMethodParams.getApiImplicitParamValue());
            //baseCodeMethodParams.add(codeGenerationMethodParams.getApiImplicitParamDataType());
            if (baseType.get(codeGenerationMethodParams.getRequestBodyName()) != null) {
                codeGenerationMethodParams.setRequestBodyName(baseType.get(codeGenerationMethodParams.getRequestBodyName()));
            }
            baseCodeMethodParams.add(codeGenerationMethodParams.getResultDataType() == null ? "" : "<" + codeGenerationMethodParams.getResultDataType() + ">");
            baseCodeMethodParams.add(codeGenerationMethodParams.getMethodName());
            // 空参数情况
            if (" ".equals(codeGenerationMethodParams.getRequestBodyName())) {
                baseCodeMethodParams.add(" ");
                baseCodeMethodParams.add("\"\" ");
            } else {
                baseCodeMethodParams.add("@ApiParam(name = \"" + codeGenerationMethodParams.getApiImplicitParamName() + "\",value=\"" + codeGenerationMethodParams.getApiImplicitParamValue() + "\")@RequestBody " + codeGenerationMethodParams.getRequestBodyType() + codeGenerationMethodParams.getRequestBodyName());
                baseCodeMethodParams.add(codeGenerationMethodParams.getRequestBodyName());

            }
            baseCodeMethodParams.add(codeGenerationParams.getServiceName());
            baseCodeMethodParams.add(codeGenerationMethodParams.getMethodName());
            // 兼容老版本的api方法生成swagger
            if (codeGenerationMethodParams.getRequestBodyName().equals("reqBody")) {
                baseCodeMethodParams.add("");
            } else {
                baseCodeMethodParams.add("ReqBody reqBody = new ReqBody();");
            }
            // 空参数情况
            if (" ".equals(codeGenerationMethodParams.getRequestBodyName())) {
                String code = "";
                baseCodeMethodParams.add(code);
            } else {
                String code = "params.put(\"" + codeGenerationMethodParams.getRequestBodyName() + "\"," + codeGenerationMethodParams.getRequestBodyName() + ");";
                baseCodeMethodParams.add(code);
            }
            baseCodeMethodParams.add(codeGenerationMethodParams.getInvokeMethodName());
            String[] baseCodeMethodParamsStr = baseCodeMethodParams.toArray(new String[baseCodeParams.size()]);
            methodCodes += String.format(methodTemplate, baseCodeMethodParamsStr) + "\n";
        }
        return baseCode + "\n" + methodCodes + "\n" + endCode;

    }
}