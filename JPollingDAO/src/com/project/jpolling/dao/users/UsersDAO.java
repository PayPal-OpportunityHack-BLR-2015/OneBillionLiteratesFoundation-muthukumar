package com.project.jpolling.dao.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.project.jpolling.dao.util.HibernateUtil;
import com.project.jpolling.to.user.UserTO;

/**
 * @author P.Ayyasamy
 * 
 * @since 1.0
 */
public class UsersDAO {

	/**
	 * Method to save or update User
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */

	public UserTO saveUser(UserTO user) throws Exception {
		Session session = HibernateUtil.sessionFactory.openSession();
		 try {
		 session.getTransaction().begin();
		 session.saveOrUpdate(user);
		 session.getTransaction().commit();
			 } catch (RuntimeException e) {
		 session.getTransaction().rollback();
		 System.out.println("Exception in User DAO's Save User");
		 throw new Exception(e);
		 } finally {
		 session.close();
		 }
		return user;
	}

	public int saveMultipleUser(String commonName, int startRange,
			int endRange, String password, String mailId) {
		String userName = "";
		int count = 0, i = 0;
		UserTO userTO = null;
		for (i = startRange, count = 0; i < endRange; i++) {
			userName = commonName + i;
			userTO = new UserTO();
			userTO.setUserName(userName);
			userTO.setPassword(password);
			userTO.setMailId(mailId);
			userTO.setUserType("user");
			userTO = saveSingleUser(userTO);
			if (userTO != null) {
				count++;
			}
		}
		return count;
	}

	public UserTO saveSingleUser(UserTO userTO) {
		//UserTO userTO = null;
		try {
			
			UsersDAO userDAO = new UsersDAO();
			UserTO userTO2 = new UserTO();
			userTO2 = getUserByUserName(userTO.getUserName());
			if(userTO2 == null)
			{
				userTO = userDAO.saveUser(userTO);	
				System.out.println("User "+userTO.getUserName()+" Saved Successfully");
			} else {
				userTO.setUserId(userTO2.getUserId());
				userTO = userDAO.saveUser(userTO);	
				System.out.println("User "+userTO.getUserName()+" Updated Successfully");
			}
		} catch (Exception e) {
			System.out.println("Exception in User DAO's Save Single User");
		}
		return userTO;
	}

	public int saveUserByMailId(String userId, String password,
			List<String> mailIds) {
		int count = 0,range;
		UserTO userTO = null;
		Random random=new Random();
		for (String mailId : mailIds) {
			range=random.nextInt(999);
			userId = userId + range;
			//userTO = saveSingleUser(userId, password, mailId);
			if (userTO != null) {
				count++;
			}
		}
		return count;
	}

	public UserTO getUserByUserName(String userName)
	{
		UserTO userTO=null;
		Session session = HibernateUtil.sessionFactory.openSession();

		try {
			Criteria criteria = session.createCriteria(UserTO.class);
			criteria.add(Restrictions.eq("userName", userName));
			userTO= (UserTO)criteria.uniqueResult();
		} catch (Exception e) {
			System.out.println("Exception in getting Users count");
		} finally {
			session.close();
		}		
		return userTO;
	}
	
	public UserTO getUserByUserNameAndPassword(String userName,String password)
	{
		UserTO userTO=null;
		Session session = HibernateUtil.sessionFactory.openSession();

		try {
			Criteria criteria = session.createCriteria(UserTO.class);
			criteria.add(Restrictions.eq("userName", userName));
			criteria.add(Restrictions.eq("password", password));
			userTO= (UserTO)criteria.uniqueResult();
		} catch (Exception e) {
			System.out.println("Exception in User DAO's get User By username and password");
			e.printStackTrace();
		} finally {
			session.close();
		}		
		return userTO;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public long getUsercount(String question) throws Exception {
		long count = 0;
		Session session = HibernateUtil.sessionFactory.openSession();

		try {
			Criteria criteria = session.createCriteria(UserTO.class);
			count = criteria.list().size();
			if(question != null && !question.equalsIgnoreCase("")){
				count = count - 1; // For Detecting ADMIN
			}
		} catch (Exception e) {
			System.out.println("Exception in getting Users count");
			e.printStackTrace();
		} finally {
			session.close();
		}
		return count;
	}

	
	public List<UserTO> getUsers(int start, String question) {
		List<UserTO> users = new ArrayList<UserTO>();
		Session session = HibernateUtil.sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(UserTO.class);
			criteria.add(Restrictions.eq("userType", "user"));
			criteria.setFirstResult(start);
			criteria.setMaxResults(5);
			users = criteria.list();
		} catch (Exception e) {
			System.out.println("Exception in getting all the Users in User DAO");
			e.printStackTrace();
		} finally {
			session.close();
		}
		return users;
	}

	/**
	 * @author MuthuKumar
	 * @since 1.0
	 * @param args
	 */
	public static void main(String args[]) {
		//UserTO userTO = new UsersDAO().getUserByUserNameAndPassword("muthu", "hasdbg");
		//System.out.println(userTO.getUserName());
		//System.out.println(getUsers());
	}
	
	public UserTO getUserById(UserTO userTO){
		Session session = HibernateUtil.sessionFactory.openSession();

		try {
			Criteria criteria = session.createCriteria(UserTO.class);
			criteria.add(Restrictions.eq("userId", userTO.getUserId()));
			userTO= (UserTO)criteria.uniqueResult();
		} catch (Exception e) {
			System.out.println("Exception in getting Users By ID");
		} finally {
			session.close();
		}		
		return userTO;
	}
}
