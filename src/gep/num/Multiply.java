package gep.num;

import gep.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * �˷�����ʽ
 */
public class Multiply implements NExpression
{
	public static final char code = '*';
	public static final int arity = 2;

	private NExpression left;
	private NExpression right;

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

		if (right == null)
		{
			right = (NExpression) child;
			return;
		}

		throw new IllegalStateException("ADD_CHILD_ERROR");
	}

	public double evaluate()
	{
		return left.evaluate() * right.evaluate();
	}

	public String toString()
	{
		return "(" + left + ")*(" + right + ")";
	}

	public List<Expression> getChildren()
	{
		List<Expression> children = new ArrayList<Expression>();
		children.add(left);
		children.add(right);

		return children;
	}
}
