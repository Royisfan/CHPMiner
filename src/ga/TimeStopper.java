package ga;

public class TimeStopper implements Stopper
{
	private long start = 0;
	private long time;

	public TimeStopper(long time)
	{
		this.time = time;
	}

	public boolean canStop()
	{
		if (start == 0)
		{
			start = System.currentTimeMillis();
			return false;
		}

		if (System.currentTimeMillis() - start >= time) { return true; }

		return false;
	}

	public String toString()
	{
		return getClass().getName();
	}
}
