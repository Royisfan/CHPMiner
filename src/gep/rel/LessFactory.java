package gep.rel;

import gep.Expression;
import gep.FunctionFactory;

public class LessFactory implements FunctionFactory
{

	public char getCode()
	{
		return Less.code;
	}

	public int getArity()
	{
		return Less.arity;
	}

	public Expression get(char code)
	{
		return new Less();
	}
}
