package com.simple.code.generate.dto;

/**
 * @author luke
 */
public class SimpleConfigDto {

    /**
     * gitlab分支名
     */
    private String branchName;
    /**
     * 项目Code
     */
    private String projectCode;
    /**
     * 项目配置地址
     */
    private String fastGoServer;

    /**
     * 项目配置地址
     */
    private Boolean mybatisIsAutoGenerate;

    /**
     * mysqlIp
     */
    private String mysqlIp;
    /**
     * mysqlPort
     */
    private String mysqlPort;
    /**
     * dataBaseName
     */
    private String dataBaseName;
    /**
     * mysqlUser
     */
    private String mysqlUser;
    /**
     * mysqlPassword
     */
    private String mysqlPassword;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getFastGoServer() {
        return fastGoServer;
    }

    public void setFastGoServer(String fastGoServer) {
        this.fastGoServer = fastGoServer;
    }

    public Boolean getMybatisIsAutoGenerate() {
        return mybatisIsAutoGenerate;
    }

    public void setMybatisIsAutoGenerate(Boolean mybatisIsAutoGenerate) {
        this.mybatisIsAutoGenerate = mybatisIsAutoGenerate;
    }

    public String getMysqlIp() {
        return mysqlIp;
    }

    public void setMysqlIp(String mysqlIp) {
        this.mysqlIp = mysqlIp;
    }

    public String getMysqlPort() {
        return mysqlPort;
    }

    public void setMysqlPort(String mysqlPort) {
        this.mysqlPort = mysqlPort;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getMysqlUser() {
        return mysqlUser;
    }

    public void setMysqlUser(String mysqlUser) {
        this.mysqlUser = mysqlUser;
    }

    public String getMysqlPassword() {
        return mysqlPassword;
    }

    public void setMysqlPassword(String mysqlPassword) {
        this.mysqlPassword = mysqlPassword;
    }
}
