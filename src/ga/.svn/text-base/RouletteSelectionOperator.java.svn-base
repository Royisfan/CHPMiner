package ga;

import java.util.Random;

/**
 * 轮盘赌选择算子
 */
public class RouletteSelectionOperator implements SelectionOperator
{
	private Random random = new Random();

	public RouletteSelectionOperator(GA ga)
	{
	}

	public int[] select(double[] fitnesses)
	{
		// 计算每一个个体的选择概率
		int size = fitnesses.length;
		double sum = 0.0;
		for (int i = 0; i < size; i++)
		{
			sum += fitnesses[i];
		}

		double[] probabilities = new double[size + 1];
		double s = 0.0;
		for (int i = 0; i < size; i++)
		{
			probabilities[i] = s / sum;
			s += fitnesses[i];
		}
		probabilities[size] = 1.0;

		// 选择个体
		int[] index = new int[size];
		for (int i = 1; i < size; i++) // 第一个位置留给最优个体
		{
			double p = random.nextDouble();

			// TODO 可以改进为二分查找
			// 找到满足 probabilities[j] <= p < probabilities[j+1] 的 j
			int j = 0;
			while (j < size)
			{
				if (p < probabilities[j + 1]) break;

				j++;
			}
			index[i] = j;
		}

		return index;
	}

	public String toString()
	{
		return getClass().getName();
	}
}
