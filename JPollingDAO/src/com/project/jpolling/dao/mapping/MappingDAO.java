/**
 * Project development
 **/
package com.project.jpolling.dao.mapping;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.project.jpolling.dao.answer.QADAO;
import com.project.jpolling.dao.util.HibernateUtil;
import com.project.jpolling.to.answer.AnswerTO;
import com.project.jpolling.to.answer.QATO;
import com.project.jpolling.to.mapping.MappingTO;
import com.project.jpolling.to.question.QuestionTO;
import com.project.jpolling.to.user.UserTO;

/**
 * @author P.Ayyasamy
 * @since 1.0
 */
public class MappingDAO {

	/**
	 * 
	 * @param mapping
	 * @return
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public MappingTO saveMapping(MappingTO mapping) {

		Session session = HibernateUtil.sessionFactory.openSession();
		try {
			session.beginTransaction().begin();
			if (mapping.getMapId() == 0) {
				session.save(mapping);
				System.out.println("User mapped with the question successfull");
			} else{
				session.update(mapping);
				System.out.println("answer posted successfull");
			}
			session.beginTransaction().commit();
		} catch (Exception e) {
			System.out.println("Exception in Saving Mapping");
			e.printStackTrace();
		} finally {
			session.close();
		}
		return mapping;
	}

	/**
	 * 
	 * @param mapping
	 * @return
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public MappingTO updateMapping(MappingTO mapping) {

		Session session = HibernateUtil.sessionFactory.openSession();
		try {
			session.beginTransaction().begin();
			session.update(mapping);
			System.out.println("update mapping success");
			session.beginTransaction().commit();
		} catch (Exception e) {
			System.out.println("Exception in Saving Mapping");
			e.printStackTrace();
		} finally {
			session.close();
		}
		return mapping;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public long getMaxUsersCount() throws Exception {
		long count = 0;
		Session session = HibernateUtil.sessionFactory.openSession();

		try {
			Criteria criteria = session.createCriteria(MappingTO.class);
			criteria.createAlias("question", "question");
			ProjectionList prj = Projections.projectionList();
			prj.add(Projections.count("question.id"), "question.id");
			criteria.setProjection(prj);
			criteria.setProjection(Projections.groupProperty("question.id"));
			List<Object[]> obj =criteria.list();
				System.out.println(obj);
		} catch (Exception e) {
			System.out.println("Exception in getting Max Users count");
			e.printStackTrace();
		} finally {
			session.close();
		}
		return count;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public long getMinUsersCount() throws Exception {
		long count = 0;
		Session session = HibernateUtil.sessionFactory.openSession();

		try {
			Criteria criteria = session.createCriteria(MappingTO.class);

			count = criteria.list().size();
		} catch (Exception e) {
			System.out.println("Exception in getting Min Users count");
			e.printStackTrace();
		} finally {
			session.close();
		}
		return count;
	}

	/**
	 * Method to get the Percentage of votes for the answer  
	 * 
	 * @param answerTO
	 * @return
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public double getPercentageForAnswerId(AnswerTO answerTO) {
		Session session = HibernateUtil.sessionFactory.openSession();
		double percentage = 0;
		try {

			Criteria criteria = session.createCriteria(MappingTO.class);
			criteria.createAlias("question", "question");
			criteria.add(Restrictions.eq("question", answerTO.getQuestion()));
			double totalCount = criteria.list().size();

			criteria.add(Restrictions.eq("answer", answerTO));

			double count = criteria.list().size();

			percentage = (count / totalCount) * 100;
		} catch (Exception e) {
			System.out.println("Exception in getting percentage");
			e.printStackTrace();
		} finally {
			session.close();
		}
		return percentage;

	}

	/**
	 * 
	 * @param user
	 * @param question
	 * @return
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public MappingTO getMapping(UserTO user, QuestionTO question) {
		MappingTO mapping = new MappingTO();
		Session session = HibernateUtil.sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(MappingTO.class);
			criteria.createAlias("user", "user");
			criteria.createAlias("question", "question");
			criteria.add(Restrictions.eq("user", user));
			criteria.add(Restrictions.eq("question", question));
			mapping = (MappingTO) criteria.list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return mapping;
	}

	public int getMappingForUser(UserTO user) {
		MappingTO mapping = new MappingTO();
		List<MappingTO> mappingList ;
		int answerdQuestions = 0;
		Session session = HibernateUtil.sessionFactory.openSession();
		QADAO qa = new QADAO();
		try {
			Criteria criteria = session.createCriteria(MappingTO.class);
			criteria.createAlias("user", "user");
			criteria.createAlias("question", "question");
			criteria.add(Restrictions.eq("user", user));
			mappingList = criteria.list();
			for(MappingTO mappingTO : mappingList){
				List<QATO> list = qa.qaList(mappingTO.getQuestion().getId());
				for(QATO qaTO : list){
					if(mappingTO.getAnswer().getId()==qaTO.getAnswer().getId()){
						answerdQuestions++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return answerdQuestions;
	}
	
	public boolean deleteMapping(int questionId){
		Session session = HibernateUtil.sessionFactory.openSession();
		session.beginTransaction();
		try{
			 String hql = "delete from "+MappingTO.class.getName()+" where q_id="+questionId;
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
	/**
	 * 
	 * @return
	 * @throws Exception
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public long getUserQuestionsCount(UserTO userTO) throws Exception{
		long count=0;
		Session session = HibernateUtil.sessionFactory.openSession();	
		try{
			Criteria criteria = session.createCriteria(MappingTO.class);
			criteria.createAlias("user", "user");
			criteria.add(Restrictions.eq("user", userTO));
			criteria.add(Restrictions.isNull("answer"));
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
	 * 
	 * @param userTO
	 * @return
	 * @throws Exception
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public List<QuestionTO> getUserQuestions(UserTO userTO, int start) throws Exception {
		List<MappingTO> mappingTOList = new ArrayList<MappingTO>();
		List<QuestionTO> questionTOList= new ArrayList<QuestionTO>();
		Session session = HibernateUtil.sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(MappingTO.class);
			criteria.addOrder(Order.desc("id"));
			criteria.createAlias("user", "user");
			criteria.createAlias("question", "question");
			criteria.add(Restrictions.eq("user", userTO));
			criteria.add(Restrictions.isNull("answer"));
			criteria.setFirstResult(start);
			criteria.setMaxResults(5);
			mappingTOList = criteria.list();
			
			for(MappingTO mappingTO : mappingTOList){
				questionTOList.add(mappingTO.getQuestion());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return questionTOList;
	}
	
	/**
	 * @param args
	 * @throws Exception
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public static void main(String[] args) throws Exception {
		MappingDAO mappingDAO = new MappingDAO();
		UserTO userTO = new UserTO();
		userTO.setUserId(2);
		mappingDAO.getUserQuestions(userTO,0);
		//System.out.println(mappingDAO.getMaxUsersCount());
		/*
		 * MappingTO mapping =new MappingTO();
		 * 
		 * System.out.println(mappingDAO.saveMapping(mapping));
		 */

	}

}
