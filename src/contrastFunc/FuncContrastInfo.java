package contrastFunc;

public class FuncContrastInfo 
{
	public int count;
	public double error;
	public double constant;
	
	public FuncContrastInfo(int count, double error)
	{
		this.count = count;
		this.error = error;
		this.constant = 0;
	}
	
	public FuncContrastInfo(int count, double error, double constant)
	{
		this.count = count;
		this.error = error;
		this.constant = constant;
	}
}
