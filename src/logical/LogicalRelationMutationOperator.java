package logical;

import ga.Chromosome;
import ga.MutationOperator;

public class LogicalRelationMutationOperator extends MutationOperator
{
	private LogicalGep gep;

	public LogicalRelationMutationOperator(LogicalGep gep, double probability)
	{
		super(probability);
		this.gep = gep;
	}

	public Chromosome mutate(Chromosome chromosome)
	{
		if (!checkProbability()) return chromosome;
		
		LogicalChromosome c = (LogicalChromosome) chromosome;
		
		return new LogicalChromosome(gep.getRelationCode(), c.getGenes());
	}

}
