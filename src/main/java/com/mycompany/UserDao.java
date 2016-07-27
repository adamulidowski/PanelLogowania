package com.mycompany;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;






/**
 * 
 * Klasa zawierajaca metody potrzebne do zarzadzania uzytkownikami znajdujacymi sie w bazie danych.
 * 
 * @author adam
 *
 */
public class UserDao implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Configuration con;
	SessionFactory sf;
	Session session;
	
	public UserDao(){
	}
/**
 * Wyszukuje podanego w parametrze uzytkownika w  bazie danych.
 * 
 * @param Login Nazwa szukanego uzytkownika.
 * @param Passw Haslo szukanego uzytkownika.
 * @return Login uzytkownika, lub "none" jezeli uzytkownik nie istnieje.
 * @throws NoSuchAlgorithmException Wjatek jest generowany, gdy dany algorytm kryptograficzny jest wymagany, lecz nie jest dostepny w srodowisku.
 */
	public String findUserByLoginAndPassword(String Login,String Passw) throws NoSuchAlgorithmException{
		con = new Configuration();
		con.configure("hibernate.cfg.xml"); 
 		SessionFactory sf = con.buildSessionFactory(); 
		Session session = sf.openSession(); 
		List<UserDataModel> usersList; 
		usersList = session.createCriteria(UserDataModel.class).list(); 
		
		
		session.close();
		sf.close();
		for(int i=1; i < usersList.size(); i++){ 
 			if(usersList.get(i).getLogin().equals(Login) && usersList.get(i).getPassword().equals(PasswordHash(Passw))){
				return usersList.get(i).getLogin();
			}
		}
		return "none"; 
	}
	/**
	 * 
	 * Sprawdza, czy podany w parametrze uzytkownik ma status administratora.
	 * 
	 * @param Login Zmienna zawierajaca login uzytkownika.
	 * @return True, jezeli uzytkownik jest administratorem, w przeciwnym wypadku zwraca false.
	 */
	public boolean ifAdmin(String Login){
		
		con = new Configuration();
		con.configure("hibernate.cfg.xml"); 
 		SessionFactory sf = con.buildSessionFactory(); 
		Session session = sf.openSession(); 
		List<UserDataModel> usersList; 
		usersList = session.createCriteria(UserDataModel.class).list(); 
		
		session.close();
		sf.close();
		for(int i=1; i < usersList.size(); i++){ 
 			if( usersList.get(i).getLogin().equals(Login) && usersList.get(i).getAdmin()==true){
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * Zapisuje nowego uzytkownika do odpowiedniej tabeli w bazie danych.
	 * 
	 * @param name Nazwa nowego uzytkownika.
	 * @param pass Haslo nowego uzytkownika.
	 */
	public void addUser( String name, String pass){
		con = new Configuration();
		con.configure("hibernate.cfg.xml"); 
 		SessionFactory sf = con.buildSessionFactory(); 
		Session session = sf.openSession(); 
		
		
        session.beginTransaction();
		
		UserDataModel newUser=new UserDataModel();
		
		newUser.setLogin(name);
		newUser.setPassword(pass);
		newUser.setAdmin(false);
		System.out.println("dodawanie");
		Integer userId =(Integer) session.save(newUser);
        session.getTransaction().commit();
		
		sf.close();
	}
	
	/**
	 * 
	 * Sprawdza, czy istnieje uzytkownik o podanycm loginie.
	 * 
	 * @param Login Login szukanego uzytkownika.
	 * @return True, gdy uzytkownik znajduje sie w bazie danych, flase gdy uzytkownik o podanym loginie nie istnieje.
	 */
	public boolean userExist(String Login){
		
		con = new Configuration();
		con.configure("hibernate.cfg.xml"); 
 		SessionFactory sf = con.buildSessionFactory(); 
		Session session = sf.openSession(); 
		List<UserDataModel> usersList; 
		usersList = session.createCriteria(UserDataModel.class).list(); 
		
		session.close();
		sf.close();
		for(int i=1; i < usersList.size(); i++){ 
 			if(usersList.get(i).getLogin().equals(Login)){
				return true;
			}
		}
		
		return false;
		
	}
	/**
	 * 
	 * Haszuje algorytmem "SHA-256" podane w parametrze haslo.
	 * 
	 * @param password Haslo do zahaszowania.
	 * @return Zahaszowane haslo.
	 * @throws NoSuchAlgorithmException Wjątek jest generowany, gdy dany algorytm kryptograficzny jest wymagany, lecz nie jest dostepny w środowisku.
	 */
	public String PasswordHash(String password) throws NoSuchAlgorithmException{
		 
		MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(password.getBytes());
	        
	        byte byteData[] = md.digest();
	 
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        return sb.toString();
	}
	
	
	/**
	 * 
	 * Wyszukanie id uzytkownika, o podanym w parametrze loginie.
	 * 
	 * @param Login Login uzytkownika.
	 * @return Id uzytkownika.
	 */
	public int findUserId(String Login) {
		con = new Configuration();
		con.configure("hibernate.cfg.xml"); 
 		SessionFactory sf = con.buildSessionFactory(); 
		Session session = sf.openSession(); 
		List<UserDataModel> usersList; 
		usersList = session.createCriteria(UserDataModel.class).list(); 

		session.close();
		sf.close();
		for(int i=1; i < usersList.size(); i++){ 
 			if(usersList.get(i).getLogin().equals(Login)){
				return usersList.get(i).getId();
			}
		}
		return 0;
	}
	
	
}