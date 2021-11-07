package logical;

import gep.Expression;
import gep.FunctionFactory;
import gep.num.NGEP;
import gep.rel.GreaterEqualsFactory;
import gep.rel.GreaterFactory;
import gep.rel.LessEqualsFactory;
import gep.rel.LessFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class LogicalGep extends NGEP
{
	private Random random = new Random();

	private char[] relations;

	private Map<Character, FunctionFactory> relationFactories = new HashMap<Character, FunctionFactory>(); // πÿœµ‘ÀÀ„

	public LogicalGep()
	{
		addRelationFactory(new GreaterFactory());
		addRelationFactory(new GreaterEqualsFactory());
		addRelationFactory(new LessFactory());
		addRelationFactory(new LessEqualsFactory());
	}

	public void initialize()
	{
		super.initialize();
	}

	public Expression getRelation(char code)
	{
		FunctionFactory factory = (FunctionFactory) relationFactories.get(new Character(code));
		return factory.get(code);
	}

	public void addRelationFactory(FunctionFactory factory)
	{
		relationFactories.put(new Character(factory.getCode()), factory);
	}

	public char getRelationCode()
	{
		int index = random.nextInt(relations.length);
		return relations[index];
	}

	public void setRelationSet(String relations)
	{
		this.relations = relations.toCharArray();
	}
}
