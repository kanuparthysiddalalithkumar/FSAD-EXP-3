
package com.klef.fsad.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

 private static SessionFactory sessionFactory;

 static{
  try{
   sessionFactory = new Configuration()
     .configure("hibernate.cfg.xml")
     .addAnnotatedClass(Product.class)
     .buildSessionFactory();
  }
  catch(Exception e){
   e.printStackTrace();
  }
 }

 public static SessionFactory getSessionFactory(){
  return sessionFactory;
 }
}
