package gep.bool;

import gep.Expression;
import gep.FunctionFactory;

public class NotFactory implements FunctionFactory
{

	public char getCode()
	{
		return Not.code;
	}

	public int getArity()
	{
		return Not.arity;
	}

	public Expression get(char code)
	{
		return new Not();
	}
}
