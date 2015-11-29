/**
 * Project development
 **/
package com.project.jpolling.dao.answer;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.project.jpolling.dao.util.HibernateUtil;
import com.project.jpolling.to.answer.AnswerTO;
import com.project.jpolling.to.question.QuestionTO;

/**
 * @author P.Ayyasamy
 * @since 1.0
 */
public class AnswersDAO
{

	/**
	 * Method to save or update Answer
	 * 
	 * @param answer
	 * @return
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public AnswerTO saveAnswer(AnswerTO answer) {

		Session session = HibernateUtil.sessionFactory.openSession();
			
		try {
			session.getTransaction().begin();
			if(answer.getId()==0){				
				session.save(answer);
			} else{
				session.update(answer);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Exception in Saving Answer");
		} finally {
			session.close();
		}

		return answer;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public long getAnswerCount() throws Exception {
		long count = 0;
		Session session = HibernateUtil.sessionFactory.openSession();
		
		try {
			Criteria criteria = session.createCriteria(AnswerTO.class);

			count = criteria.list().size();
		} catch (Exception e) {
			System.out.println("Exception in getting Users count");
		} finally {
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
		AnswersDAO ans =new AnswersDAO();
		QuestionTO questionTO = new QuestionTO();
		questionTO.setId(1);
		AnswerTO answerTO =new AnswerTO();
		answerTO.setQuestion(questionTO);
		answerTO.setAnswer("a1");
		ans.saveAnswer(answerTO);
		
		System.out.println(ans.getAnswerList(1));

	}

	/**
	 * view answer
	 * 
	 * @return
	 * @throws Exception
	 * @author siva
	 * @param questionId 
	 * @since 1.0
	 */
	public List<AnswerTO> getAnswerList(long questionId) throws Exception {
		Session session = HibernateUtil.sessionFactory.openSession();
		List<AnswerTO> list =new ArrayList<AnswerTO>();
		try {
			Criteria criteria = session.createCriteria(AnswerTO.class);
			criteria.createAlias("question", "question");
			criteria.add(Restrictions.eq("question.id", questionId));
			list = criteria.list();
		} catch (Exception e) {
			System.out.println("Exception in getting answers");
			e.printStackTrace();
 		} finally {
			session.close();
		}
		return list;
	}
	
	public boolean deleteAnswer(int questionId){
		Session session = HibernateUtil.sessionFactory.openSession();
		session.beginTransaction();
		try{
			String hql = "delete from "+AnswerTO.class.getName()+" where q_id="+questionId;
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
