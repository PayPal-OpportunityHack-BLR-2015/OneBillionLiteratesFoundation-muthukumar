package com.project.jpolling.to.user;

import java.io.Serializable;

/**
 * 
 * @author P.Ayyasamy
 * 
 * @since 1.0
 */
public class UserTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long userId;
	private String password;
	private String mailId;
	private String userName;
	private String userType;
	private boolean verified;
	private String address1;
	private String address2;
	private String state;
	private String city;

	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}


	public UserTO() {
		userName = "";
		password = "";
		mailId = "";
	}

    /**
     *  @AUTHOR Muthukumar and Prabhu
     *  @since 1.0
     *  
     * @return
     */
	public long getUserId() {
		return userId;
	}

	/**
	 * 
	 * @AUTHOR Muthukumar and Prabhu
     *  @since 1.0
     *  
	 * @param userId
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}



	/**
	 * 
	 * @AUTHOR Muthukumar and Prabhu
     *  @since 1.0
     *  
	 * @param userName
	 */

	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @AUTHOR Muthukumar and Prabhu
     *  @since 1.0
     *  
	 * @param userName
	 */

	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	 * @return the password
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the mailId
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public String getMailId() {
		return mailId;
	}

	/**
	 * @param mailId
	 *            the mailId to set
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}


	public boolean isVerified() {
		return verified;
	}


	public void setVerified(boolean verified) {
		this.verified = verified;
	}


	public String getAddress1() {
		return address1;
	}


	public void setAddress1(String address1) {
		this.address1 = address1;
	}


	public String getAddress2() {
		return address2;
	}


	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}

	
}
