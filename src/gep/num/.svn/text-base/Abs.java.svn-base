package gep.num;

import gep.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * 绝对值函数表达式
 */
public class Abs implements NExpression
{
	public static final char code = 'A';
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
		return Math.abs(left.evaluate());
	}

	public String toString()
	{
		return "Abs[" + left + "]";
	}

	public List<Expression> getChildren()
	{
		List<Expression> children = new ArrayList<Expression>();
		children.add(left);

		return children;
	}
}
