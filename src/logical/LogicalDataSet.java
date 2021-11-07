package logical;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import util.Closer;

public class LogicalDataSet
{
	private List<double[]> data = new ArrayList<double[]>();

	public LogicalDataSet(List<double[]> data){
		this.data = data;
	}
	public LogicalDataSet(String file) throws Exception
	{
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			
			for (; ; )
			{
				String line = reader.readLine();
				if (line == null) break;
				
				line = line.trim();
				if (line.equals("")) continue;
				
				String[] ss = line.split("[ \t]+");
				double[] dd = new double[ss.length];
				for (int i = 0; i < ss.length; i++)
				{
					dd[i] = Double.parseDouble(ss[i]);
				}
				
				data.add(dd);
			}
		}
		finally
		{
			Closer.close(reader);
		}
	}
	
	public List<double[]> getData()
	{
		return data;
	}
}
