package gep.bool;

import gep.Expression;
import gep.FunctionFactory;

public class BFactory implements FunctionFactory
{

	public char getCode()
	{
		return B.code;
	}

	public int getArity()
	{
		return B.arity;
	}

	public Expression get(char code)
	{
		return new B();
	}
}
