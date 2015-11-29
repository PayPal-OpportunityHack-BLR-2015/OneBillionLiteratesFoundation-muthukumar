package com.project.jpolling.to.answer;

import java.io.Serializable;

import com.project.jpolling.to.question.QuestionTO;

/**
 * @author P.Ayyasamy
 * 
 * @since 1.0
 */
public class AnswerTO implements Serializable
{


	private long id;

	private QuestionTO question;

	private String answer;

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
	 * @param id
	 *            the id to set
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the answer
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer
	 *            the answer to set
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @return the question
	 * @author siva
	 * @since 1.0
	 */
	public QuestionTO getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 * @author siva
	 * @since 1.0
	 */
	public void setQuestion(QuestionTO question) {
		this.question = question;
	}

	
}
