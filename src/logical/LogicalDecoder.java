package logical;

import ga.Chromosome;
import ga.Protein;
import gep.Expression;
import gep.GEP;
import gep.GepDecoder;
import gep.bool.BExpression;
import gep.num.NExpression;
import gep.num.NVariable;

public class LogicalDecoder extends GepDecoder
{
	protected NVariable[] variables = new NVariable[23];

	public LogicalDecoder(GEP gep)
	{
		super(gep);
	}

	public void setConstantValues(double[] values)
	{
	}

	public void reset()
	{
//		variables = new NVariable[26];
		variables = new NVariable[23];
	}

	public Expression getVariable(char code)
	{
		int index = code - 970;
		if (variables[index] == null)
		{
			variables[index] = new NVariable(code);
		}
		return variables[index];
	}

	public Protein decode(Chromosome chromosome)
	{

		reset();

		LogicalChromosome dc = (LogicalChromosome) chromosome;

		BExpression root = (BExpression) ((LogicalGep) gep).getRelation(dc.getRelation());
		
		char[][] genes = dc.getGenes();
		for (int i = 0; i < 2; i++)
		{
			NExpression e = (NExpression) decode0(genes[i]);
			root.addChild(e);
		}
		return (Protein) new LogicalFormula(root, variables);
	}

	protected Expression getConstant(char code)
	{
		return null;
	}

	public int getORFSum(Chromosome chromosome) {
		int ORFSum = 0;
		
		LogicalChromosome dc = (LogicalChromosome) chromosome;
		char[][] genes = dc.getGenes();
		for (int i = 0; i < 2; i++)
		{
			char [] p = genes[i];
			int geneLength = gep.geneHead + gep.geneHead * (gep.maxArity - 1) + 1;
		
			for(int j = 0; j < gep.geneNumber; j++)
			{
				int start = j * geneLength;
				int ORFLength = 1; 
				for(int k = start; k < (start + geneLength); k++)
				{
					if(gep.isFunction(p[k]) == true)
						ORFLength = ORFLength + gep.getFunctionArity(p[k]) - 1;
					else
						ORFLength -= 1;
					ORFSum++;
					if(ORFLength == 0)
						break;
				}
			}
		}
		
		return ORFSum;
	}

}
