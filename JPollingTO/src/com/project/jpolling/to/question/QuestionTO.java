package com.project.jpolling.to.question;

import java.io.Serializable;
import java.util.Date;

/**
 * @author P.Ayyasamy
 *
 * @since 1.0
 */
public class QuestionTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String question;
	private Date startDate;
	private Date endDate;
		
	
	/**
	 * 
	 */
	public QuestionTO() {
		super();
	}

	/**
	 * @param id
	 * @param question
	 * @param startDate
	 * @param endDate
	 * @param answerToList
	 */
	public QuestionTO(Long id, String question, Date startDate,
			Date endDate) {
		super();
		this.id = id;
		this.question = question;
		this.startDate = startDate;
		this.endDate = endDate;
		
	}

		
	/**
	 * @return the id
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the question
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public String getQuestion() {
		return question;
	}
	
	/**
	 * @param question the question to set
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the startDate
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
