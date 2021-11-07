package ga;

public abstract class MutationOperator extends GeneticOperator
{
	public MutationOperator(double probability)
	{
		super(probability);
	}

	abstract public Chromosome mutate(Chromosome chromosome);
}
