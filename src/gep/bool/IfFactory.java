package gep.bool;

import gep.Expression;
import gep.FunctionFactory;

public class IfFactory implements FunctionFactory
{

	public char getCode()
	{
		return If.code;
	}

	public int getArity()
	{
		return If.arity;
	}

	public Expression get(char code)
	{
		return new If();
	}
}
