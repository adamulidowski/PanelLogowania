package com.mycompany;

import java.io.Serializable;
import java.util.Date;
/**
 *  Klasa dostępu do danych odpowiadająca tabeli "Activity" w bazie danych.
 * @author adam
 *
 */
public class ActivityDataModel implements Serializable{
int id;
int id_user;
String date;
String userName;

public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getId_user() {
	return id_user;
}
public void setId_user(int id_user) {
	this.id_user = id_user;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
@Override
public String toString() {
	return userName+ ", date:" + date;
}
	
}
