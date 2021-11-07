package bool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import util.Closer;

/**
 * ���ݼ�
 * 
 * @author duanlei
 *
 */
public class BoolDataSet
{
	private List<boolean[]> data = new ArrayList<boolean[]>();
	private List<boolean[]> lineData = new ArrayList<boolean[]>();

	public BoolDataSet(List<boolean[]> data){
		this.data = data;

		for(int i = 0; i < this.data.get(0).length; i ++)//���������Ը�����
		{
			boolean[] linedd = new boolean[this.data.size()];
			for (int j = 0; j < this.data.size(); j ++)//data.size()=6,����
			{
				linedd[j] = this.data.get(j)[i];
			}
			lineData.add(linedd);
		}
	}
	public BoolDataSet(String file) throws Exception
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
				boolean[] dd = new boolean[ss.length];
				for (int i = 0; i < ss.length; i++)
				{
					if(Integer.valueOf(ss[i])== 1)//��Ҫ�� �ж������Ƿ�Ϊ1��1��true����֮false
						dd[i] = Boolean.valueOf("true");
					else
						dd[i] = Boolean.valueOf("false");
					
					
//					if(i<=10)//��һ�����ԣ����䣬����ȡֵ��Χ{0,1,2,3,4,5,6,7,8,9,10},��Ӧ������index
//					{
//						if(Integer.valueOf(ss[i])== i)
//							dd[i] = Boolean.valueOf("true");
//						else
//							dd[i] = Boolean.valueOf("false");
//					}
//					else if(i>=11 && i<=12)////��2�����ԣ��Ա�ȡֵ��Χ{0��1}
//					{
//						int aa = (i-11)%5;
//						if(Integer.valueOf(ss[i])== aa)//��Ҫ�� �ж������Ƿ�Ϊ1��1��true����֮false
//							dd[i] = Boolean.valueOf("true");
//						else
//							dd[i] = Boolean.valueOf("false");
//					}
//					else//�ӵ��������Կ�ʼΪ�걾ָ�ꡣÿ5������Ϊһ��ָ���5��ȡֵ��һ��ָ���ȡֵ��Χ{0,1,2,3,4}
//					{
//						int cc = (i-13)%5;//ȡֵΪ0��1��2��3��4
//						if(Integer.valueOf(ss[i])== cc)//��Ҫ�� �ж������Ƿ�Ϊ1��1��true����֮false
//							dd[i] = Boolean.valueOf("true");
//						else
//							dd[i] = Boolean.valueOf("false");
//					}
					
				}
				
				data.add(dd);
			}
			
			for(int i = 0; i < data.get(0).length; i ++)//���������Ը�����
			{
				boolean[] linedd = new boolean[data.size()];
				for (int j = 0; j < data.size(); j ++)//data.size()=6,����
				{
					linedd[j] = data.get(j)[i];
				}
				lineData.add(linedd);
			}
		}
		finally
		{
			Closer.close(reader);
		}
	}
	
	public List<boolean[]> getData()
	{
//		return lineData;
		return data;
	}
}