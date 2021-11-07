package logical;

import ga.Chromosome;
import ga.CrossoverOperator;

public class LogicalOnePointCrossoverOperator extends CrossoverOperator
{
	public LogicalOnePointCrossoverOperator(LogicalGep gep, double probability)
	{
		super(probability);
	}

	public Chromosome[] operate(Chromosome[] chromosomes)
	{
		if (!checkProbability()) return chromosomes;

		LogicalChromosome c0 = (LogicalChromosome) chromosomes[0];
		LogicalChromosome c1 = (LogicalChromosome) chromosomes[1];

		char[][] pg0 = c0.getGenes();
		char[][] pg1 = c1.getGenes();

		char[][] sg0 = new char[2][];
		char[][] sg1 = new char[2][];

		for (int i = 0; i < 2; i++)
		{
			char[] p0 = pg0[i];
			char[] p1 = pg1[i];
			char[] s0 = p0.clone();
			char[] s1 = p1.clone();

			crossover(p0, p1, s0, s1);
			
			sg0[i] = s0;
			sg1[i] = s1;
		}

		Chromosome[] sons = new Chromosome[2];
		sons[0] = new LogicalChromosome(c0.getRelation(), sg0);
		sons[1] = new LogicalChromosome(c1.getRelation(), sg1);

		return sons;
	}

	private void crossover(char[] p0, char[] p1, char[] s0, char[] s1)
	{
		int size = p0.length;
		int start = (int) (Math.random() * size);

		for (int i = start; i < size; i++)
		{
			s0[i] = p1[i];
			s1[i] = p0[i];
		}
	}
}
