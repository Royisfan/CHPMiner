package ga;

import java.util.ArrayList;
import java.util.List;

public class Population
{
	private List<Chromosome> chromosomes = new ArrayList<Chromosome>();

	public void add(Chromosome chromosome)
	{
		chromosomes.add(chromosome);
	}

	public Chromosome get(int i)
	{
		return (Chromosome) chromosomes.get(i);
	}

	public int size()
	{
		return chromosomes.size();
	}
}
