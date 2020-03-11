package com.example.shirodemo.entity;


public class User {

  private String userid;
  private String username;
  private String userphone;
  private String password;
  private Integer usersex;
  private String usersalt;
  private String usertype;

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUserphone() {
    return userphone;
  }

  public void setUserphone(String userphone) {
    this.userphone = userphone;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getUsersex() {
    return usersex;
  }

  public void setUsersex(Integer usersex) {
    this.usersex = usersex;
  }

  public String getUsersalt() {
    return usersalt;
  }

  public void setUsersalt(String usersalt) {
    this.usersalt = usersalt;
  }

  public String getUsertype() {
    return usertype;
  }

  public void setUsertype(String usertype) {
    this.usertype = usertype;
  }
}
