package gep;

import ga.Chromosome;
import ga.Decoder;
import ga.Protein;


import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用于数值优化的GEP解码器
 */
public abstract class GepDecoder implements Decoder
{
	protected GEP gep;

	public GepDecoder(GEP gep)
	{
		this.gep = gep;
	}

	/**
	 * 返回当前解码过程中 code 表示的变量
	 * 
	 * @param code
	 * @return
	 */
	protected abstract Expression getVariable(char code);

	/**
	 * 返回当前解码过程中 code 表示的常量
	 * 
	 * @param code
	 * @return
	 */
	protected abstract Expression getConstant(char code);

	/**
	 * 返回指定代码对应的表达式
	 * 
	 * @param code
	 * @return
	 */
	protected Expression getExpression(char code)
	{
		Expression expression;
//		if (Character.isLowerCase(code)||Character.isUpperCase(code)) // 是变量
//		{
//			expression = getVariable(code);
//		}
		
//		if (Character.isLowerCase(code/10)) // 是变量
//		if (Character.isLowerCase(code)) // 是变量
//		  {
//		   expression = getVariable(code);
//		  }
		
		if (code>=970) // 改 是变量
		  {
		   expression = getVariable(code);
		  }
		
		else if (Character.isDigit(code)) // 是常量
		{
			expression = getConstant(code);
		}
		else // 是普通函数
		{
			expression = gep.getFunction(code);
		}

		return expression;
	}

	/**
	 * 解码基因组为表达式树 (如果多基因，则用连接函数连起来)
	 * 
	 * @param genes
	 * @param start
	 *            开始位置
	 * @return
	 */
	protected Expression decode0(char[] genes)
	{
		int geneNumber = gep.getGeneNumber();
		int geneLength = gep.getGeneLength();
		
		Expression[] expressions = new Expression[geneNumber];
		for (int i = 0; i < geneNumber; i++)
		{
			if(gep.getMaxFunc() == 0)
				expressions[i] = decode1(genes, i * geneLength);
			else
				expressions[i] = decode2(genes, i * geneLength);
		}

		// 用连接函数将它们连接起来
		int arity = gep.getLinkFunctionArity();
		Expression root = expressions[0];
		int index = 1;
		while (index < expressions.length)
		{
			Expression expression = gep.getLinkFunction();
			expression.addChild(root);
			for (int i = 1; i < arity; i++)
			{
				expression.addChild(expressions[index++]);
			}
			root = expression;
		}

		return root;
	}

	/**
	 * 解码一个基因
	 * 
	 * @param genes
	 * @param start
	 *             开始位置
	 * @return
	 */
	protected Expression decode1(char[] genes, int start)
	{
		// 采用队列来解码 
		List<Expression> queue = new LinkedList<Expression>();

		// 将初始节点推入队列尾部
		int index = start;
		char code = genes[index++];
		Expression root = getExpression(code);
		queue.add(root);

		// 依次弹出队列头部的元素进行处理
		while (!queue.isEmpty())
		{
			Expression expression = (Expression) queue.remove(0);
			int arity = expression.getArity();

			for (int i = 0; i < arity; i++)
			{
				code = genes[index++];
				Expression child = getExpression(code);

				expression.addChild(child);
				queue.add(child);
			}
		}

		return root;
	}
	
	
	/** 改 solution
	 * 判断是否为特殊字符（在生成的基因变量中，可能出现除了函数字符之外的 特殊字符）
	 * @param str
	 * @return true为包含，false为不包含
	 */
	
	public static boolean isSpecialChar(char code) {
		
//        String regEx = "[ _@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
		String str = Character.toString(code);
		String regEx = "[!&|^]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

	
	/**
	 * 解码一个基因（限制基因头部中函数的最大数目）
	 * 
	 * @param genes
	 * @param start
	 *            开始位置
	 * @return
	 */
	protected Expression decode2(char[] genes, int start)
	{
		int numFunctions = 0;	// 记录gene中function的总数
		
		// 采用队列来解码 
		List<Expression> queue = new LinkedList<Expression>();

		// 将初始节点推入队列尾部
		int index = start;
		char code = genes[index++];
//		if (Character.isLowerCase(code)==false && Character.isUpperCase(code)==false && Character.isDigit(code)==false)// 是函数
		if (Character.isLowerCase(code)==false && Character.isUpperCase(code)==false && Character.isDigit(code)==false && code<970)// 改 是函数
			numFunctions ++;
		
		Expression root = getExpression(code);
		queue.add(root);
		
		boolean reachMax = false;

		// 依次弹出队列头部的元素进行处理
		while (!queue.isEmpty())
		{
			Expression expression = (Expression) queue.remove(0);
			int arity = expression.getArity();

			for (int i = 0; i < arity; i++)
			{
				if(numFunctions == gep.getMaxFunc() && reachMax == false)
				{
					reachMax = true;
					index = start + gep.geneHead;
				}
				code = genes[index++];
//				if (Character.isLowerCase(code)==false && Character.isUpperCase(code)==false && Character.isDigit(code)==false) // 是函数
				if (Character.isLowerCase(code)==false && Character.isUpperCase(code)==false && Character.isDigit(code)==false && code<970) //改  是函数
					numFunctions ++;
				Expression child = getExpression(code);

				expression.addChild(child);
				queue.add(child);
			}
		}

		return root;
	}
	

	public abstract void reset();

	public abstract Protein decode(Chromosome chromosome);

	public String toString()
	{
		return getClass().getName();
	}
}
