package ga;

abstract public class CrossoverOperator extends GeneticOperator
{
	protected CrossoverOperator(double probability)
	{
		super(probability);
	}

	abstract public Chromosome[] operate(Chromosome parents[]);
}
