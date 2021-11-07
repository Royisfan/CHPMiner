package gep.num;

import gep.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * ±‰¡ø
 */
public class NVariable implements NExpression
{
	public static final int arity = 0;

	private char code;
	private double value;

	public NVariable(char code)
	{
		this.code = code;
	}

	public char getCode()
	{
		return code;
	}

	public int getArity()
	{
		return arity;
	}

	public void addChild(Expression child)
	{
		throw new IllegalStateException("ADD_CHILD_ERROR");
	}

	public void setValue(double value)
	{
		this.value = value;
	}

	public double evaluate()
	{
		return value;
	}

	public String toString()
	{
//		if(Character.isLowerCase(code) == true)
//			return code + "1";
//		if(Character.isUpperCase(code) == true)
//			return "A" + code + "1";
//		return null;
		
//		char a=(char)(code/10);
//		char b=(char)(code%10 + '0');
		return "a"+String.valueOf(code-970);
	}

	public List<Expression> getChildren()
	{
		List<Expression> children = new ArrayList<Expression>();

		return children;
	}
}
