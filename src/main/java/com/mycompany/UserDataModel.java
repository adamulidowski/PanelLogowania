package com.mycompany;

import java.io.Serializable;
import java.util.Set;


/**
 *  Klasa dostępu do danych odpowiadająca tabeli "Users" w bazie danych.
 *  
 * @author adam
 *
 */
public class UserDataModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	int id;
	String login;
	String password;
	boolean admin;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return id + "  login=" + login;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean getAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

}