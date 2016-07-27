package com.mycompany;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/*
 *  Klasa odpowiadajaca plikowi addUser.html.
 */
public class Panel extends WebPage{
	

	
	public Panel(){
		
		try{
		String result = ""; 
			result = String.valueOf(UserSession.getInstance().getAttribute("nick"));
		String result2= String.valueOf(UserSession.getInstance().getAttribute("admin"));
		add(new Label("SendedParam",result));
		if(UserSession.getInstance().getAttribute("admin").equals(true)){
			add(new Label("SendedParam2", "Admin Panel"));
		}
		else{
			add(new Label("SendedParam2", "User Panel"));
		}
		
		final Button addUser=new Button("addUser"){
			@Override
			public void onSubmit() {
				super.onSubmit();
				setResponsePage(addUser.class); 
			}
		};
		Button logOut=new Button("logOut"){
		
			private static final long serialVersionUID = -1800911970905016411L;
			@Override
			public void onSubmit() {
				super.onSubmit();
				UserSession.getInstance().invalidate();
				setResponsePage(StartPage.class); 
			}
		};

		ActivityDao act=new ActivityDao();
		add(act.activityList(result, result2));
		
	
		if(UserSession.getInstance().getAttribute("admin").equals(true)){
			addUser.setVisible(Boolean.TRUE);
			
		}else{
			addUser.setVisible(Boolean.FALSE);
		}
		
		
		Form<?> form = new Form<Void>("form") ;
			System.out.println(UserSession.getInstance().getuSerModel().getLogin());
	form.add(addUser);
	form.add(logOut);
	add(form);
		} catch(RuntimeException e){
			UserSession.getInstance();
			setResponsePage(StartPage.class);
		}
}
}