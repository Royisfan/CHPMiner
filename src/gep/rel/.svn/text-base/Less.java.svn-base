package gep.rel;

import gep.Expression;
import gep.bool.BExpression;
import gep.num.NExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * 小于等于函数表达式
 */
public class Less implements BExpression
{
	public static final char code = '<';
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

	public boolean evaluate()
	{
		return left.evaluate() <= right.evaluate();
	}

	public String toString()
	{
		return "(" + left + ") <= (" + right + ")";
	}

	public List<Expression> getChildren()
	{
		List<Expression> children = new ArrayList<Expression>();
		children.add(left);
		children.add(right);

		return children;
	}
}
