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
import com.project.jpolling.to.answer.QATO;
import com.project.jpolling.to.question.QuestionTO;

/**
 * @since 1.0
 */
public class QADAO
{

	/**
	 * Method to save or update Answer
	 * 
	 * @param answer
	 * @return
	 * 
	 * @since 1.0
	 */
	public QATO saveQA(QATO answer) {

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
			e.printStackTrace();
		} finally {
			session.close();
		}

		return answer;
	}

	/**
	 * @param args
	 * @throws Exception 
	 * 
	 * @since 1.0
	 */
	public static void main(String[] args) throws Exception {
		QADAO ans =new QADAO();
		QuestionTO questionTO = new QuestionTO();
		questionTO.setId(1);
		QATO QATO =new QATO();
		AnswerTO answerTO =new AnswerTO();
		answerTO.setId(1);
		QATO.setQuestion(questionTO);
		QATO.setAnswer(answerTO);
		ans.saveQA(QATO);
		System.out.println(ans.qaList(1));

	}

	/**
	 * view answer
	 * 
	 * @return
	 * @throws Exception
	 * @param questionId 
	 * @since 1.0
	 */
	public List<QATO> qaList(long questionId) throws Exception {
		Session session = HibernateUtil.sessionFactory.openSession();
		List<QATO> list =new ArrayList<QATO>();
		try {
			Criteria criteria = session.createCriteria(QATO.class);
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
	
	
	public boolean deleteQA(int qaId){
		Session session = HibernateUtil.sessionFactory.openSession();
		session.beginTransaction();
		try{
			String hql = "delete from "+QATO.class.getName()+" where id="+qaId;
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
