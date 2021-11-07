package gep.bool;

import gep.Expression;

import java.util.ArrayList;
import java.util.List;

public class Not implements BExpression
{
	public static final char code = '!';
	public static final int arity = 1;

	private BExpression left;

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
		if (left == null)
		{
			left = (BExpression) child;
			return;
		}

		throw new IllegalStateException("ADD_CHILD_ERROR");
	}

	public boolean evaluate()
	{
		return !left.evaluate();
	}

	public String toString()
	{
		return "(!" + left + ")";
	}

	public List<Expression> getChildren()
	{
		List<Expression> children = new ArrayList<Expression>();
		children.add(left);

		return children;
	}
}
