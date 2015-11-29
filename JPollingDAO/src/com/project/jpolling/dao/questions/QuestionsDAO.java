/**
 * Project development
 **/
package com.project.jpolling.dao.questions;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.project.jpolling.dao.util.HibernateUtil;
import com.project.jpolling.to.question.QuestionTO;
import com.project.jpolling.to.user.UserTO;

/**
 * @author P.Ayyasamy
 * @since 1.0
 */
public class QuestionsDAO {

	/**
	 * Method to save or update Question
	 * 
	 * @param quesion
	 * @return
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public QuestionTO saveQuestion(QuestionTO quesion) {

		Session session = HibernateUtil.sessionFactory.openSession();
		try {
			session.getTransaction().begin();
			if (quesion.getId() == 0) {
				session.save(quesion);
			} else {
				session.update(quesion);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Exception in Saving Question");
		} finally {
			session.close();
		}

		return quesion;

	}
	
	public List<QuestionTO> getQuestionForId(long id){
		Session session = HibernateUtil.sessionFactory.openSession();
		List<QuestionTO> questionList= new ArrayList<QuestionTO>();
		try{
			Criteria criteria = session.createCriteria(QuestionTO.class);
			if(id!=0){
				criteria.add(Restrictions .eq("id", id));
			}			
			questionList = criteria .list();
			
		}catch (Exception e) {
			System.out.println("Exception in getQuestionsForId" + e);
		}
		return questionList;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public long getAdminQuestionsCount(UserTO userTO) throws Exception{
		long count=0;
		Session session = HibernateUtil.sessionFactory.openSession();	
		
		try{
			Criteria criteria = session.createCriteria(QuestionTO.class);
			count=criteria.list().size();
		}catch (Exception e) {
			System.out.println("Exception in getting Users count");
			e.printStackTrace();
		}finally{
			session.close();
		}
		return count;
	}
	/**
	 * @param args
	 * @throws Exception 
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public static void main(String[] args) throws Exception {

		//QuestionsDAO questionsDAO = new QuestionsDAO();
		/*List<QuestionTO> qlist;
		qlist= questionsDAO.getQuestionForId(6);

		for (QuestionTO question : qlist){
			System.out.println(question.getId());
			System.out.println(question.getQuestion());
		}*/
		/*QuestionTO question = new QuestionTO();
		question.setId(0);
		question.setQuestion("Question details");
		// question.setStartDate();
		// question.setEndDate("15.2.2012");
		questionsDAO.saveQuestion(question);*/
  
		//System.out.println(questionsDAO.getQuestioncount());
		
	}
	
	/**
	 * Get all Questions
	 * 
	 * @return
	 * @throws Exception
	 * @author siva
	 * @since 1.0
	 */
	public List<QuestionTO> getAdminQuestions(int start) throws Exception {
		List<QuestionTO> questionList = null;
		Session session = HibernateUtil.sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(QuestionTO.class);
			criteria.addOrder(Order.desc("id"));
			criteria.setFirstResult(start);
			criteria.setMaxResults(5);
			questionList = criteria.list();			
		} catch (RuntimeException e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return questionList;
	}

	
	/**
	 * Return last posted question
	 * 
	 * @return
	 * @author siva
	 * @since 1.0
	 */
	public QuestionTO getLastQuestion() throws Exception {
		Session session = HibernateUtil.sessionFactory.openSession();
		List<QuestionTO> questionTO = new ArrayList<QuestionTO>();
		try {
			Criteria criteria = session.createCriteria(QuestionTO.class);
			criteria.addOrder(Order.desc("id"));
			criteria.setFirstResult(0);
			criteria.setMaxResults(1);
			questionTO = criteria.list();
		} catch (RuntimeException e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		for (QuestionTO qto : questionTO) {
			return qto;
		}
		return null;
	}

	public boolean deleteQuestion(int questionId){
		Session session = HibernateUtil.sessionFactory.openSession();
		session.beginTransaction();
		try{
			String hql = "delete from "+QuestionTO.class.getName()+" where q_id="+questionId;
			 Query query = session.createQuery(hql);
			 int row = query.executeUpdate();
			 if (row == 0) {
			 System.out.println("Doesn't deleted any row!");
			 }
			 else {
			 System.out.println("Deleted Row: " + row);
			 }
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			session.beginTransaction().commit();
			session.close();
		}
		return true;
	}
}
