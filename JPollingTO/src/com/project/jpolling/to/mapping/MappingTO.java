package com.project.jpolling.to.mapping;

import java.io.Serializable;

import com.project.jpolling.to.answer.AnswerTO;
import com.project.jpolling.to.question.QuestionTO;
import com.project.jpolling.to.user.UserTO;

/**
 * @author P.Ayyasamy
 * 
 * @since 1.0
 */
public class MappingTO implements Serializable
{

	private long mapId;

	private UserTO user;

	private QuestionTO question;

	private AnswerTO answer;

	private long cnt;
	/**
	 * @return the map_id
	 * @author siva
	 * @since 1.0
	 */
	public long getMapId() {
		return mapId;
	}

	/**
	 * @param mapId
	 *            the map_id to set
	 * @author siva
	 * @since 1.0
	 */
	public void setMapId(long mapId) {
		this.mapId = mapId;
	}
	
	/**
	 * @return the user
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public UserTO getUser() {
		return user;
	}

	

	/**
	 * @param user
	 *            the user to set
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public void setUser(UserTO user) {
		this.user = user;
	}

	/**
	 * @return the question
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public QuestionTO getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            the question to set
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public void setQuestion(QuestionTO question) {
		this.question = question;
	}

	/**
	 * @return the answer
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public AnswerTO getAnswer() {
		return answer;
	}

	/**
	 * @param answer
	 *            the answer to set
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public void setAnswer(AnswerTO answer) {
		this.answer = answer;
	}

	public long getCnt() {
		return cnt;
	}

	public void setCnt(long cnt) {
		this.cnt = cnt;
	}

	
}
