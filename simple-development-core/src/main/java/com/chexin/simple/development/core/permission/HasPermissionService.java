package com.chexin.simple.development.core.permission;


import com.chexin.simple.development.core.commonenum.Logical;
import org.springframework.beans.factory.annotation.Configurable;


/**
 * @author liko
 * @Date 2019-09-22 10:36
 * @DESCRIPTION 权限校验接口
 */
@Configurable
public interface HasPermissionService {
    /**
     * 校验权限返回子权限集合
     *
     * @param permissionKeys
     * @return
     */
    String checkPermission(String[] permissionKeys, Logical logical, Boolean isPage);

}