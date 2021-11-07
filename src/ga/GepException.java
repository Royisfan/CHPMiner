package ga;

public class GepException extends Exception
{
	private static final long serialVersionUID = 1L;

	public GepException(String message)
	{
		super(message);
	}

	public GepException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
