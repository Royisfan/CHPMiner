package logical;

import ga.Chromosome;
import ga.MutationOperator;

public class LogicaMutationOperator extends MutationOperator
{
	private LogicalGep gep;

	public LogicaMutationOperator(LogicalGep gep, double probability)
	{
		super(probability);
		this.gep = gep;
	}

	public Chromosome mutate(Chromosome chromosome)
	{
		LogicalChromosome c = (LogicalChromosome) chromosome;

		char[][] ps = c.getGenes();
		char[][] ss = new char[2][];

		int geneHead = gep.getGeneHead();
		int geneTail = gep.getGeneTail();
		int geneNumber = gep.getGeneNumber();

		for (int x = 0; x < 2; x++)
		{
			char[] s = ps[x].clone();
			ss[x] = s;
			
			int index = 0;
			for (int k = 0; k < geneNumber; k++)
			{
				for (int i = 0; i < geneHead; i++, index++)
				{
					if (!checkProbability()) continue;

					s[index] = gep.getHeadCode();
				}
				for (int i = 0; i < geneTail; i++, index++)
				{
					if (!checkProbability()) continue;

					s[index] = gep.getTailCode();
				}
			}
		}

		return new LogicalChromosome(c.getRelation(), ss);
	}

}
