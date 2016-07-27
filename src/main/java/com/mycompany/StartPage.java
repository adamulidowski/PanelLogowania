/*
 * Copyright 2016 Adam.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mycompany;



import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;




/**
 *
 * Strona startowa aplikacji. 
 *
 * @author Adam
 */
public class StartPage extends WebPage {


	 private static final long serialVersionUID = 1L;

	private UserDao userDao;
	private ActivityDao actDao;
	
	private UserModel userModel = new UserModel();

	public StartPage() {
		final TextField<String> login = new TextField<String>("login", new PropertyModel<String>(userModel, "login"));

		PasswordTextField passwd = new PasswordTextField("password", new PropertyModel<String>(userModel, "pass"));
		
		final Label badInfo=new Label("message", "Bad Login or Password!");
		
		badInfo.setVisible(Boolean.FALSE);
		Form<Object> form = new Form<Object>("form");
		
			form.add(new Button("submit"){
			
				
				private static final long serialVersionUID = -1800911970905016411L;
				@Override
				public void onSubmit(){
				super.onSubmit();
				userDao = new UserDao(); 	
				actDao=new ActivityDao();
				try {
					if((userDao.findUserByLoginAndPassword(userModel.getLogin(), userModel.getPass()).equals("none"))){
						badInfo.setVisible(Boolean.TRUE);
					}
					else{
					UserSession.getInstance().setuSerModel(userModel);
					UserSession.getInstance().setAttribute("nick", userModel.getLogin());
					UserSession.getInstance().setAttribute("admin", userDao.ifAdmin(userModel.getLogin()));
					actDao.addActivity(userDao.findUserId(userModel.getLogin()),userModel.getLogin());
					setResponsePage(Panel.class); 
					}
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				}
			});

		
		form.add(login); 
		form.add(passwd);
		add(form); 
		add(badInfo);
		

		
		
		

	}
}

