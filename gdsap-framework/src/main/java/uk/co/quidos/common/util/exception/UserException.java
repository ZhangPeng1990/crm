package uk.co.quidos.common.util.exception;

import uk.co.quidos.common.util.exception.ASException;

/**
 * 
 */


/**
 * @author peng.shi
 */
public class UserException extends ASException
{
	private static final long serialVersionUID = -4979136580153646840L;
	
	/**
	 * 用户名重复
	 */
	public static final int USER_NAME_DUPLICATE_ERROR_CODE = 1;
	
	/**
	 * 用户非可用状态
	 */
	public static final int USER_INACTIVE_ERROR_CODE = 2;
	
	/**
	 * 用户名密码不匹配
	 */
	public static final int USER_PASSWORD_NOT_MATCH_ERROR_CODE = 3;
	
	private int errorCode;

	public int getErrorCode()
	{
		return errorCode;
	}
	
	public UserException(int errorCode)
	{
		this.errorCode = errorCode;
	}
	
}
