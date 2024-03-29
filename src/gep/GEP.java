package gep;

import ga.GA;
import ga.GepException;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Gene Expression Programming
 */
public class GEP extends GA
{
	private char[] functions = new char[0]; // GEP中可以使用的所有函数代码
	private char[] terminals = new char[0]; // GEP中可以使用的所有终结符代码

	private Map<Character, FunctionFactory> functionFactories = new HashMap<Character, FunctionFactory>(); // GEP中可以使用的所有函数(从名字到工厂的映射)

	private char linkFunction = '无'; // 连接函数(代码)
	private FunctionFactory linkFunctionFactory; // 连接函数工厂 

	public int maxArity = 1; // 函数的最大目数
	public int geneHead = 0; // 基因头部长度
	public int geneTail = 0; // 基因尾部长度
	public int geneNumber = 1; // 基因数量
	 
	public int maxFuncLimit = 0;	// 基因头部中函数个数的最大数

	public void initialize()
	{
		super.initialize();
	}
	
	/**
	 * 添加候选表达式的工厂
	 * 
	 * @param factory
	 */
	public void addFunctionFactory(FunctionFactory factory)
	{
		functionFactories.put(new Character(factory.getCode()), factory);
	}

	/**
	 * 设置可以使用的函数集
	 * 
	 * @param set
	 * @throws GepExpression
	 */
	public void setFunctionSet(String set) throws GepException
	{
		functions = set.toCharArray();
		for (int i = 0; i < functions.length; i++)
		{
			char function = functions[i];
			FunctionFactory factory = (FunctionFactory) functionFactories.get(new Character(functions[i]));

			if (factory == null) throw new GepException("Unknown function: '" + function + "'");

			int arity = factory.getArity();
			if (arity > maxArity)
			{
				maxArity = arity;
				geneTail = geneHead * (maxArity - 1) + 1;
			}
		}
	}

	/**
	 * 设置变量集合 (其名字按 a,b,...,z,A,B,...,Z 依次命名) 如果希望加大某个变量的选择概率，可以在集合中重复声明
	 * 
	 * @param set
	 * @throws GepExpression
	 */
	public void setVariableSet(String set) throws GepException
	{
		for (int i = 0; i < set.length(); i++)
		{
			char code = set.charAt(i);
/*zuo:		if (!Character.isLowerCase(code)) throw new GepException("Unknown variable: '" + code + "'"); 
/*logical:		if (!Character.isLowerCase(code)&&!Character.isUpperCase(code)) throw new GepException("Unknown variable: '" + code + "'");*/
///*wang:*/	if (!Character.isLowerCase(code/10));
		    if (code<970) throw new GepException("Unknown variable: '" + code + "'");//solution 改
		}

		terminals = (set + new String(terminals)).toCharArray();
	}

	/**
	 * 设置常量量集合 (其名字按 1, 2, ... 依次命名) 如果希望加大某个常量的选择概率，可以在集合中重复声明
	 * 
	 * @param set
	 * @throws GepExpression
	 */
	public void setConstantSet(String set) throws GepException
	{
		for (int i = 0; i < set.length(); i++)
		{
			char code = set.charAt(i);
			if (!Character.isDigit(code)) throw new GepException("Unknown constant: '" + code + "'");
		}

		terminals = (set + new String(terminals)).toCharArray();
	}

	/**
	 * 设置连接函数
	 * 
	 * @param c
	 * @throws GepException
	 */
	public void setLinkFunction(char c) throws GepException
	{
		linkFunction = c;
		linkFunctionFactory = (FunctionFactory) functionFactories.get(new Character(c));

		if (linkFunctionFactory == null) throw new GepException("Unknown function: '" + c + "'");
	}

	/**
	 * 返回名字对应的表达式(可能为函数或者终结符)
	 * 
	 * @param name
	 * @return
	 */
	public Expression getFunction(char code)
	{
		FunctionFactory factory = (FunctionFactory) functionFactories.get(new Character(code));
		return factory.get(code);
	}

	/**
	 * 返回连接函数表达式
	 * 
	 * @return
	 */
	public Expression getLinkFunction()
	{
		return linkFunctionFactory.get(linkFunction);
	}

	/**
	 * 返回连接函数的目数
	 * 
	 * @return
	 */
	public int getLinkFunctionArity()
	{
		return linkFunctionFactory.getArity();
	}

	//----------------------------------
	public int getGeneHead()
	{
		return geneHead;
	}

	public int getGeneTail()
	{
		return geneTail;
	}

	public int getGeneNumber()
	{
		return geneNumber;
	}
	
	public int getMaxFunc()
	{
		return maxFuncLimit;
	}

	public void setGeneHead(int i)
	{
		geneHead = i;
		geneTail = geneHead * (maxArity - 1) + 1;
	}

	public void setGeneTail(int i)
	{
		geneTail = i;
	}

	public void setGeneNumber(int i)
	{
		geneNumber = i;
	}
	
	public void setMaxFunc(int i)
	{
		maxFuncLimit = i;
	}

	/**
	 * 返回基因长度
	 * 
	 * @return
	 */
	public int getGeneLength()
	{
		return geneHead + geneTail;
	}

	/**
	 * 返回随机的用于头部的代码
	 * 
	 * @return
	 */
	public char getHeadCode()
	{
		int index = (int) (Math.random() * (functions.length + terminals.length));//返回0-functions.length + terminals.length 的随机数
		if (index < functions.length) { return functions[index]; }
		return terminals[index - functions.length];
	}

	/**
	 * 返回随机的只用于尾部代码 (终结符代码)
	 * 
	 * @return
	 */
	public char getTailCode()
	{
		return terminals[(int) (Math.random() * terminals.length)];
	}

	public boolean isFunction(char code)
	{
		for (int i = 0; i < functions.length; i++)
		{
			if (functions[i] == code) { return true; }
		}

		return false;
	}

	/**
	 * 报告GEP的运行状况
	 */
	public void report(PrintWriter writer) throws GepException
	{
		writer.println("Functions          : " + new String(functions));
		writer.println("Terminals          : " + new String(terminals));
		writer.println("Link Function      : " + linkFunction);

		writer.println("Max Arity          : " + maxArity);
		writer.println("Head Length        : " + geneHead);
		writer.println("Tail Length        : " + geneTail);
		writer.println("Gene Number        : " + geneNumber);

		super.report(writer);
	}
	
	/**返回指定函数的目数  2011-06-27
	 * @return
	 */
	public int getFunctionArity(char code)
	{
		FunctionFactory factory = (FunctionFactory) functionFactories.get(new Character(code));
		return factory.getArity() ;
	}

}
