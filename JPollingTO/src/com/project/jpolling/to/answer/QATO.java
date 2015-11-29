package com.project.jpolling.to.answer;

import java.io.Serializable;

import com.project.jpolling.to.question.QuestionTO;

/**
 * 
 * @since 1.0
 */
public class QATO implements Serializable
{

	private long id;

	private QuestionTO question;
	
	private AnswerTO answer;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public QuestionTO getQuestion() {
		return question;
	}

	public void setQuestion(QuestionTO question) {
		this.question = question;
	}

	public AnswerTO getAnswer() {
		return answer;
	}

	public void setAnswer(AnswerTO answer) {
		this.answer = answer;
	}
	
	
}
