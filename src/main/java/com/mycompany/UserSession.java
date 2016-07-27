package com.mycompany;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class UserSession extends WebSession {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1800911970905016411L;

	private UserModel uSerModel;
	
	public UserSession(Request request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	
	public static UserSession getInstance(){
		return (UserSession)Session.get();
	}

	public UserModel getuSerModel() {
		return uSerModel;
	}

	public void setuSerModel(UserModel uSerModel) {
		this.uSerModel = uSerModel;
	}

}
