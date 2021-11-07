package ga;

public interface Decoder
{
	public void reset();
	public Protein decode(Chromosome chromosome);

	int getORFSum(Chromosome bestChromosome);
}
