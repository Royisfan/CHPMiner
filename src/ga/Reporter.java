package ga;

import java.util.Date;

public interface Reporter
{
	public abstract void report(GA ga, Date now) throws GepException;
}
