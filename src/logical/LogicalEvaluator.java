package logical;

import ga.Evaluator;
import ga.Protein;

import java.util.List;

import contrastFunc.FuncContrastInfo;

public class LogicalEvaluator implements Evaluator
{
	private LogicalDataSet train0;
	private LogicalDataSet train1;
	public static int count0 = 0;
	public static int count1 = 0;
	public static double sup0 = 0.0;
	public static double sup1 = 0.0;

	public LogicalEvaluator(LogicalDataSet train0, LogicalDataSet train1)
	{
		this.train0 = train0;
		this.train1 = train1;
	}

	public double evaluate(Protein protein)
	{
		LogicalFormula formula = (LogicalFormula) protein;

		int count0 = evaluate0(formula, train0.getData());
		int count1 = evaluate0(formula, train1.getData());

		if ((count0==-1)||(count1==-1)) {
			LogicalEvaluator.sup0 = 0.0;
			LogicalEvaluator.sup1 = 0.0;
			return 0.0;
		}

		double sup0 =  (double)count0/(double)train0.getData().size();
		double sup1 =  (double)count1/(double)train1.getData().size();

		LogicalEvaluator.count0 = count0;
		LogicalEvaluator.count1 = count1;
		LogicalEvaluator.sup0 = sup0;
		LogicalEvaluator.sup1 = sup1;
		return (count0 * (train1.getData().size() - count1));
	}

	private static int evaluate0(LogicalFormula formula, List<double[]> train)
	{
		int count = 0;
		for (double[] sample : train)
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

	@Override
	public double getSup0() {
		return sup0;
	}

	@Override
	public double getSup1() {
		return sup1;
	}
}
