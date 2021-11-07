package gep.bool;

import gep.Expression;
import gep.FunctionFactory;

public class AndFactory implements FunctionFactory
{

	public char getCode()
	{
		return And.code;
	}

	public int getArity()
	{
		return And.arity;
	}

	public Expression get(char code)
	{
		return new And();
	}
}
