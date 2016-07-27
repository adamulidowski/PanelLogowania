package com.mycompany;

import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * 
 * Klasa odpowiadajaca plikowi addUser.html.
 * 
 * @author adam
 * 
 */
public class addUser extends WebPage {

	public addUser(){

		try{
		final Label userExist=new Label("exist", "User already exist!");
		userExist.setVisible(false);
		
		UserModel userModel=new UserModel();
		final Label userAdded=new Label("message", "User Added!");
		userAdded.setVisible(false);
		
		final Label length=new Label("length", "Username and password must have 4-15 letters!");
		length.setVisible(false);
		
		
		
		final TextField<String> login = new TextField<String>("name", new PropertyModel<String>(userModel, "login"));
		
		final PasswordTextField  passwd = new PasswordTextField("pass", new PropertyModel<String>(userModel, "pass"));
		Form <?> form=new Form("form"){
			@Override
			public void onSubmit(){
				super.onSubmit();
				UserDao user=new UserDao();
				String name2 = login.getInput();
				String pass2 = passwd.getInput();
				if(name2.length()<4 || pass2.length()<5 || name2.length()>15 || pass2.length()>15 || name2.isEmpty()){
					userExist.setVisible(false);
					userAdded.setVisible(false);
					length.setVisible(true);
					
				}
				else if(user.userExist(name2)){
					length.setVisible(false);
					userAdded.setVisible(false);
					userExist.setVisible(true);
				}
				else{
					String pass3="";
					try {
						pass3=user.PasswordHash(pass2);
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				user.addUser(name2, pass3);
				
				userExist.setVisible(false);
				length.setVisible(false);
				userAdded.setVisible(true);
				}
			}
		};
		
		Form <?> form2=new Form("form2"){
			@Override
			public void onSubmit(){
				super.onSubmit();

			setResponsePage(Panel.class);
			}
		};
		PageParameters pageParameters = new PageParameters();
		System.out.println(pageParameters.get("number"));
		System.out.println(UserSession.getInstance().getAttribute("nick"));
		add(form2);
		add(userExist);
		add(length);
		add(userAdded);
		add(form);
		form.add(login);
		form.add(passwd);
		
		
		} catch(RuntimeException e){
			UserSession.getInstance();
			setResponsePage(StartPage.class);
		}
		
	}
}
