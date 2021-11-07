package gep.num;

import gep.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * 负号表达式
 */
public class Negative implements NExpression
{
	public static final char code = '~';
	public static final int arity = 1;

	private NExpression left;

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
			left = (NExpression) child;
			return;
		}

		throw new IllegalStateException("ADD_CHILD_ERROR");
	}

	public double evaluate()
	{
		return -left.evaluate();
	}

	public String toString()
	{
		return "-(" + left + ")";
	}

	public List<Expression> getChildren()
	{
		List<Expression> children = new ArrayList<Expression>();
		children.add(left);

		return children;
	}
}
