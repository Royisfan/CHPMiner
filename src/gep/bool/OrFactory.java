package gep.bool;

import gep.Expression;
import gep.FunctionFactory;

public class OrFactory implements FunctionFactory
{

	public char getCode()
	{
		return Or.code;
	}

	public int getArity()
	{
		return Or.arity;
	}

	public Expression get(char code)
	{
		return new Or();
	}
}
