package logical;

import ga.Chromosome;

public class LogicalChromosome implements Chromosome
{
	private char relation;
	private char[][] genes;

	public LogicalChromosome(char relation, char[][] genes)
	{
		this.relation = relation;
		this.genes = genes;
	}

	public char getRelation()
	{
		return relation;
	}

	public void setRelation(char relation)
	{
		this.relation = relation;
	}

	public char[][] getGenes()
	{
		return genes;
	}

	public void setGenes(char[][] genes)
	{
		this.genes = genes;
	}

}
