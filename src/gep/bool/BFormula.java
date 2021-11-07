package gep.bool;

import gep.Formula;

public class BFormula extends Formula implements BEvaluable
{
	private BVariable[] variables;

	public BFormula(BExpression tree, BVariable[] variables)
	{
		super(tree);
		this.variables = variables;
	}

	public void setValues(boolean[] values)
	{
		for (int index = 0; (index < values.length) && (index < variables.length); index++)
		{
			BVariable variable = variables[index];
			if (variable == null) continue;

			variable.setValue(values[index]);
		}
	}

	public boolean evaluate()
	{
		return ((BExpression) tree).evaluate();
	}
}
