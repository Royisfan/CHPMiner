package gep.bool;

import ga.Chromosome;
import ga.Protein;
import gep.Expression;
import gep.GEP;
import gep.GepChromosome;
import gep.GepDecoder;

public class BDecoder extends GepDecoder
{
	protected BConstant[] constants = new BConstant[2];

	protected BVariable[] variables = new BVariable[343];

	
	public BDecoder(GEP gep)
	{
		super(gep);
	}

	public void reset()
	{
		variables = new BVariable[343];
	}

	public Expression getVariable(char code)
	{
		int index = code - 970;
		if (variables[index] == null)
		{
			variables[index] = new BVariable(code);
		}
		return variables[index];
	}

	public Expression getConstant(char code)
	{
		int index = code - 'a';
		if (constants[index] == null)
		{
			constants[index] = new BConstant(code, index != 0);
		}
		return constants[index];
	}

	public Protein decode(Chromosome chromosome)
	{
		reset();

		String genes = ((GepChromosome) chromosome).getGenes();
		char[] p = genes.toCharArray();

		BExpression root = (BExpression) decode0(p);

		return new BFormula(root, variables);
	}

	public int getORFSum(Chromosome bestChromosome) {
		// TODO Auto-generated method stub
		return 0;
	}

}
