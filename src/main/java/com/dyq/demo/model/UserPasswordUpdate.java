package com.dyq.demo.model;

/**
 * @author dengyouquan
 **/
public class UserPasswordUpdate {
    private String oldPassword;
    private String updatePassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getUpdatePassword() {
        return updatePassword;
    }

    public void setUpdatePassword(String updatePassword) {
        this.updatePassword = updatePassword;
    }
}
