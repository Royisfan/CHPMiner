package ga;

import java.util.Random;

public class RouletteSelectionOperator implements SelectionOperator
{
	private Random random = new Random();

	public RouletteSelectionOperator(GA ga)
	{
	}

	public int[] select(double[] fitnesses)
	{
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

		int[] index = new int[size];
		for (int i = 1; i < size; i++)
		{
			double p = random.nextDouble();

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
