package bool;

import java.util.List;

import contrastFunc.FuncContrastInfo;

import ga.Evaluator;
import ga.Protein;
import gep.bool.BFormula;

/**
 * 评价器
 */
public class BoolEvaluator implements Evaluator
{
	private BoolDataSet train0; // 训练数据0
	private BoolDataSet train1; // 训练数据1
	public static int count0 = 0;
	public static int count1 = 0;
	public static double sup0 = 0;
	public static double sup1 = 0;

	public BoolEvaluator(BoolDataSet train0, BoolDataSet train1)
	{
		this.train0 = train0;
		this.train1 = train1;
	}

	/**
	 * 评价
	 * 
	 * @param protein
	 * @return
	 */
	public double evaluate(Protein protein)
	{
		BFormula formula = (BFormula) protein;

		int count0 = evaluate0(formula, train0.getData());//表达式formula在每个正例lineData中出现的次数
		int count1 = evaluate0(formula, train1.getData());

		if ((count0==-1)||(count1==-1)) {
			BoolEvaluator.sup0 = 0.0;
			BoolEvaluator.sup1 = 0.0;
			return 0.0;
		}

		double sup0 = (double)count0/(double)train0.getData().size();
		double sup1 = (double)count1/(double)train1.getData().size();

		BoolEvaluator.count0 = count0;
		BoolEvaluator.count1 = count1;
		BoolEvaluator.sup0 = sup0;
		BoolEvaluator.sup1 = sup1;

//		return (double)(count0 + 1) / (count1 + 1);
		return (count0 * (train1.getData().size() - count1));
	}

	private static int evaluate0(BFormula formula, List<boolean[]> train)
	{
		int count = 0; // 模型正确判断的数量
		for (boolean[] sample : train)
		{
			boolean b = false;
			try
			{
				formula.setValues(sample);
				b = formula.evaluate();
			}
			catch (Exception e)
			{
				return -1;
			}

			if (b) count++;
		}

		return count;
	}

	public FuncContrastInfo evaluateInfo(Protein protein) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public FuncContrastInfo evaluateInfoGetConstant(Protein protein) {
		// TODO Auto-generated method stub
		return null;
	}

	public double getSup0(){ return sup0;}
	public double getSup1(){ return sup1;}
}

