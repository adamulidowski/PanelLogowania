package com.mycompany;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 * Klasa zawierajaca metody sluzace do zarzadzania tabela aktywnosci w bazie danych. 
 * 
 * @author adam
 *
 */
public class ActivityDao implements Serializable {

	private static final long serialVersionUID = 1L;
	Configuration con;
	SessionFactory sf;
	Session session;

	public ActivityDao() {
	}
/**
 * 
 * Dodaje nowy rekord do tabeli zawierajacej aktywnosci uzytkownikow. Metoda wywolywana po poprawnym zalogowaniu uzytkownika.
 * 
 * @param userId Id uzytkownika, ktory sie zalogowal.
 * @param userName Nazwa uzytkownika, ktory sie zalogowal.
 */
	public void addActivity(int userId, String userName) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
		String sdt = df.format(new Date(System.currentTimeMillis()));
		System.out.println(sdt);

		con = new Configuration();
		con.configure("hibernate.cfg.xml");
		SessionFactory sf = con.buildSessionFactory();
		Session session = sf.openSession();

		session.beginTransaction();

		ActivityDataModel newAct = new ActivityDataModel();

		newAct.setDate(sdt);
		newAct.setId_user(userId);
		newAct.setUserName(userName);
		Integer actId = (Integer) session.save(newAct);
		session.getTransaction().commit();
		sf.close();
	}

	/**
	 * 
	 * Generuje liste aktywnosci uzytkownikow. W zaleznosci od tego, jaki status funkcja otrzyma w paramnetrze, wygeneruje inna liste.
	 * 
	 * @param uName Nazwa zalogowanego uzytkownika.
	 * @param uAdmin Status zalogowanego uzytkownika.
	 * @return Lista aktywnosci uzytkownikow.
	 */
	public ListView activityList(String uName, String uAdmin) {
		Configuration con;
		con = new Configuration();
		con.configure("hibernate.cfg.xml");
		SessionFactory sf = con.buildSessionFactory();
		Session session = sf.openSession();
		List<ActivityDataModel> dataList;
		dataList = session.createCriteria(ActivityDataModel.class).list();

		session.close();
		sf.close();

		for (int i = 0; i < dataList.size(); i++) {
			if (uAdmin.equals("false") && !(dataList.get(i).getUserName().equals(uName))) {
				dataList.remove(i);
			}
		}

		if(dataList.size() > 10 && uAdmin.equals("false")){
			for (int j = dataList.size()-1; j > 0; j--) {
				if (!(dataList.get(j).getUserName().equals(uName))) {
					dataList.remove(j);
				}
			}
		}
		int x=dataList.size();
		if(dataList.size() > 10 && uAdmin.equals("false")){
			for (int j = x-10 ; j > 0; j--) {
				dataList.remove(j);
			}
		}
		
		ListView listview = new ListView("listview", dataList) {
			protected void populateItem(ListItem item) {
				item.add(new Label("label", item.getModel()));
			}
		};

		return listview;

	}

}
