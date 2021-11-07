package gep.bool;

import gep.Expression;
import gep.FunctionFactory;

public class NOTXORFactory implements FunctionFactory
{

	public char getCode()
	{
		return NOTXOR.code;
	}

	public int getArity()
	{
		return NOTXOR.arity;
	}

	public Expression get(char code)
	{
		return new NOTXOR();
	}
}
