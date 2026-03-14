package com.klef.fsad.hibernate;

import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class HQLApp {

 public static void main(String[] args) {

  Session session = HibernateUtil.getSessionFactory().openSession();

  session.beginTransaction();

  // Insert additional records
  session.persist(new Product("Keyboard","Electronics",1500,20));
  session.persist(new Product("Monitor","Electronics",12000,5));
  session.persist(new Product("Headset","Electronics",2000,15));
  session.persist(new Product("Tablet","Gadgets",25000,7));
  session.persist(new Product("Charger","Accessories",500,40));
  session.persist(new Product("Speaker","Electronics",3500,8));

  session.getTransaction().commit();

  // Sorting by price ASC
  Query<Product> q1 = session.createQuery("from Product order by price asc", Product.class);
  List<Product> list1 = q1.getResultList();

  System.out.println("Price Ascending:");
  for(Product p : list1) {
   System.out.println(p.getName() + " " + p.getPrice());
  }

  // Sorting by price DESC
  Query<Product> q2 = session.createQuery("from Product order by price desc", Product.class);

  System.out.println("Price Descending:");
  List<Product> list2 = q2.getResultList();
  for(Product p : list2) {
   System.out.println(p.getName() + " " + p.getPrice());
  }

  // Sort by quantity highest first
  Query<Product> q3 = session.createQuery("from Product order by quantity desc", Product.class);

  System.out.println("Quantity High to Low:");
  List<Product> list3 = q3.getResultList();
  for(Product p : list3) {
   System.out.println(p.getName() + " " + p.getQuantity());
  }

  // Pagination first 3
  Query<Product> q4 = session.createQuery("from Product", Product.class);
  q4.setFirstResult(0);
  q4.setMaxResults(3);

  System.out.println("First 3 Products:");
  List<Product> list4 = q4.getResultList();
  for(Product p : list4) {
   System.out.println(p.getName());
  }

  // Pagination next 3
  Query<Product> q5 = session.createQuery("from Product", Product.class);
  q5.setFirstResult(3);
  q5.setMaxResults(3);

  System.out.println("Next 3 Products:");
  List<Product> list5 = q5.getResultList();
  for(Product p : list5) {
   System.out.println(p.getName());
  }

  // Aggregate queries
  Long total = (Long) session.createQuery("select count(*) from Product").uniqueResult();
  System.out.println("Total Products: " + total);

  Long available = (Long) session.createQuery("select count(*) from Product where quantity>0").uniqueResult();
  System.out.println("Products with quantity>0: " + available);

  Object[] minmax = (Object[]) session.createQuery("select min(price), max(price) from Product").uniqueResult();
  System.out.println("Min Price: " + minmax[0] + " Max Price: " + minmax[1]);

  // Group by description
  List<Object[]> grp = session.createQuery(
   "select description,count(*) from Product group by description").list();

  System.out.println("Group By Description:");
  for(Object[] r : grp) {
   System.out.println(r[0] + " " + r[1]);
  }

  // Price range filter
  Query<Product> range = session.createQuery(
   "from Product where price between 1000 and 10000", Product.class);

  System.out.println("Price between 1000 and 10000:");
  List<Product> rangeList = range.getResultList();
  for(Product p : rangeList) {
   System.out.println(p.getName());
  }

  // LIKE queries

  Query<Product> like1 = session.createQuery(
   "from Product where name like 'M%'", Product.class);

  System.out.println("Names starting with M:");
  for(Product p : like1.getResultList()) {
   System.out.println(p.getName());
  }

  Query<Product> like2 = session.createQuery(
   "from Product where name like '%r'", Product.class);

  System.out.println("Names ending with r:");
  for(Product p : like2.getResultList()) {
   System.out.println(p.getName());
  }

  Query<Product> like3 = session.createQuery(
   "from Product where name like '%ea%'", Product.class);

  System.out.println("Names containing 'ea':");
  for(Product p : like3.getResultList()) {
   System.out.println(p.getName());
  }

  Query<Product> like4 = session.createQuery(
   "from Product where length(name)=5", Product.class);

  System.out.println("Names with length 5:");
  for(Product p : like4.getResultList()) {
   System.out.println(p.getName());
  }

  session.close();
 }
}