package com.blockchain.commune.model;

public class ApiAuth {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column api_auth.url
     *
     * @mbg.generated
     */
    private String url;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column api_auth.role_level
     *
     * @mbg.generated
     */
    private Integer roleLevel;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column api_auth.url
     *
     * @return the value of api_auth.url
     *
     * @mbg.generated
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column api_auth.url
     *
     * @param url the value for api_auth.url
     *
     * @mbg.generated
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column api_auth.role_level
     *
     * @return the value of api_auth.role_level
     *
     * @mbg.generated
     */
    public Integer getRoleLevel() {
        return roleLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column api_auth.role_level
     *
     * @param roleLevel the value for api_auth.role_level
     *
     * @mbg.generated
     */
    public void setRoleLevel(Integer roleLevel) {
        this.roleLevel = roleLevel;
    }
}