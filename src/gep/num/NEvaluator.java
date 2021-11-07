package gep.num;

import contrastFunc.FuncContrastInfo;
import ga.Evaluator;
import ga.Fitness;
import ga.Protein;

/**
 * ��ֵ������
 */
public class NEvaluator implements Evaluator
{
	private NFitness fitnessFunction; // ��Ӧ�Ⱥ���
	private double[][] parameters; // ѵ�����ݵĲ���

	public NEvaluator(Fitness fitnessFunction, NDataSet dataSet)
	{
		this.fitnessFunction = (NFitness) fitnessFunction;
		this.parameters = dataSet.getParameters();
	}

	/**
	 * ���ۣ�������Ӧ��
	 * 
	 * @param protein
	 *            �����۵�"������"
	 * @return ��Ӧ��ֵ
	 */
	public double evaluate(Protein protein)
	{
		NEvaluable evaluable = (NEvaluable) protein;

		int size = parameters.length;

		// ����ÿһ��������ֵ
		double[] model = new double[size];
		for (int i = 0; i < size; i++)
		{
			double[] sample = parameters[i];

			// ģ�ͼ���ֵ
			try
			{
				model[i] = evaluable.evaluate(sample);
			}
			catch (Exception e)
			{
				return fitnessFunction.getMinFitness();
			}
		}

		// ������Ӧ��
		double f = fitnessFunction.calculate(model);

		// TODO ����"�������ԭ��"
		//		if (false) f += 1/((Formula) protein).getComplex();

		return f;
	}

	public String toString()
	{
		return getClass().getName();
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
		return 0;
	}

	@Override
	public double getSup1() {
		return 0;
	}
}
