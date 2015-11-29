
package com.project.jpolling.dao.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author P.Ayyasamy
 * @since 1.0
 */
public class HibernateUtil {

	public static SessionFactory sessionFactory;
	static {
		System.out.println("i am creating Session ");
		sessionFactory = new Configuration().configure().buildSessionFactory();
		System.out.println("i am done Session ");
	}

	
	public static void main(String[] args) {
		sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		session.getTransaction().commit();
		session.close();
	}
}
