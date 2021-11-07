package ga;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import contrastFunc.FuncContrastInfo;
import solution.Result;

public abstract class GA
{
	public Initializer initializer = null;
	public SelectionOperator selectionOperator = null;
	public List<MutationOperator> mutationOperators = new ArrayList<MutationOperator>();
	public List<CrossoverOperator> crossoverOperators = new ArrayList<CrossoverOperator>();
	public Decoder decoder;
	public Evaluator evaluator;
	public Stopper stopper;
	public Fitness fitnessFunction;

	public int generation = 0;
	public Population population;

	public Chromosome bestChromosome;
	public Protein bestProtein;
	public double bestFitness = -200;
	public double support = -200.0;
	public int bestGeneration;

	public double lastBestFitness = -300;
	
	public double bestConstant = 0.0;
	public int aritySum = 0;

	public long startTime;
	public long stopTime;
	public long bestTime;

	public double threshold = 0.6;

	public List<Result> result = new ArrayList<>();

	public List<Double> averageSupportHistory = new ArrayList<>();
	public List<Double> averageRatioHistory = new ArrayList<>();
	public List<Double> averageFitnessHistory = new ArrayList<Double>();
	public List<Double> bestFitnessHistory = new ArrayList<Double>();

	public void initialize()
	{
		
	}

	public void setInitializer(Initializer initializer)
	{
		this.initializer = initializer;
	}

	public void setSelectionOperator(SelectionOperator selectionOperator)
	{
		this.selectionOperator = selectionOperator;
	}

	public void addMutationOperator(MutationOperator mutationOperator)
	{
		this.mutationOperators.add(mutationOperator);
	}

	public void addCrossoverOperator(CrossoverOperator crossoverOperator)
	{
		this.crossoverOperators.add(crossoverOperator);
	}

	public void setDecoder(Decoder decoder)
	{
		this.decoder = decoder;
	}

	public void setFitnessFunction(Fitness fitnessFunction)
	{
		this.fitnessFunction = fitnessFunction;
	}

	public void setEvaluator(Evaluator evaluator)
	{
		this.evaluator = evaluator;
	}

	public void setStopper(Stopper stopper)
	{
		this.stopper = stopper;
	}

	public void run()
	{
		startTime = System.currentTimeMillis();
		population = initializer.generateInitialPopulation();
		double[] fitnesses = evaluate(population);

		for (; !stopper.canStop(); generation++)
		{
			verbose(fitnesses, 0);

			population = select(population, fitnesses);
			population = mutation(population);
			population = crossover(population);
			fitnesses = evaluate(population);
		}
		stopTime = System.currentTimeMillis();
	}

	public void runWithError() 
	{
		startTime = System.currentTimeMillis();
		population = initializer.generateInitialPopulation();
		double[] fitnesses = evaluateWithError(population);

		for (; !stopper.canStop(); generation++)
		{
			verbose(fitnesses, 0);

			population = select(population, fitnesses);
			population = mutation(population);
			population = crossover(population);
			fitnesses = evaluateWithError(population);
		}
		stopTime = System.currentTimeMillis();
	}

	public void runWithErrorGetConstant() 
	{
		startTime = System.currentTimeMillis();

		population = initializer.generateInitialPopulation();
		double[] fitnesses = evaluateWithErrorGetConstant(population);
		for (; !stopper.canStop(); generation++)
		{
			verbose(fitnesses, 0);

			population = select(population, fitnesses);
			population = mutation(population);
			population = crossover(population);
			fitnesses = evaluateWithErrorGetConstant(population);
		}
		stopTime = System.currentTimeMillis();
	}

	private Population select(Population population, double fitnesses[])
	{
		int[] index = selectionOperator.select(fitnesses);

		Population pop = new Population();

		pop.add(bestChromosome);

		int size = population.size();
		for (int i = 1; i < size; i++)
		{
			pop.add(population.get(index[i]));
		}

		return pop;
	}

	private Population mutation(Population population)
	{
		int size = population.size();
		for (Iterator<MutationOperator> iterator = mutationOperators.iterator(); iterator.hasNext();)
		{
			MutationOperator mutationOperator = (MutationOperator) iterator.next();
			Population pop = new Population();
			for (int i = 0; i < size; i++)
			{
				Chromosome chromosome = population.get(i);
				chromosome = mutationOperator.mutate(chromosome);
				pop.add(chromosome);
			}
			population = pop;
		}

		return population;
	}

	private Population crossover(Population population)
	{
		int size = population.size();
		for (Iterator<CrossoverOperator> iterator = crossoverOperators.iterator(); iterator.hasNext();)
		{
			CrossoverOperator crossoverOperator = (CrossoverOperator) iterator.next();
			Population pop = new Population();
			for (int i = 0; i < size; i += 2)
			{
				Chromosome[] parents = new Chromosome[2];
				parents[0] = population.get(i);
				parents[1] = population.get(i + 1);

				Chromosome[] sons = crossoverOperator.operate(parents);
				pop.add(sons[0]);
				pop.add(sons[1]);
			}
			population = pop;
		}

		return population;
	}

	private double[] evaluate(Population population)
	{
		int size = population.size();
		double averageSupport = 0.0;
		double averageRatio = 0.0;
		double maxL = 15.0;
		double[] fitnesses = new double[size];
		double averageFitness = 0.0;
		for (int i = 0; i < size; i++)
		{
			Chromosome chromosome = population.get(i);
			Result result1 = new Result();
			Protein protein = decoder.decode(chromosome);

			String regex = "a";
			Matcher matcher = Pattern.compile(regex).matcher(protein.toString());
			double count = 0;
			while(matcher.find()) {
				count++;
			}
			double fitness = evaluator.evaluate(protein);
			double support = evaluator.getSup0();
			this.support = support;
			double negSupport = evaluator.getSup1();
			double ratio;
			if(negSupport == 0.0){
				ratio =  support / 0.01;
			}else {
				ratio = support / negSupport;
			}
			DecimalFormat df = new DecimalFormat("0.00");

			double delta = 0;
			if(count < maxL){
				delta= 1 - count/maxL;
				delta = Double.parseDouble(df.format(delta));
				fitness = fitness + delta;
			}
			if(this.support >= this.threshold){
				result1.setProtein(protein);
				result1.setSupport(this.support);
				result1.setFitness(fitness);
				result1.setRatio(ratio);
				result.add(result1);
			}


			fitnesses[i] = fitness;
			averageFitness += fitness;
			averageSupport += support;
			averageRatio += ratio;

			if (fitness > bestFitness)
			{
				bestChromosome = chromosome;
				bestProtein = protein;
				bestFitness = fitness;
				bestGeneration = generation;
				bestTime = System.currentTimeMillis()-startTime;
			}
		}
		averageFitness /= size;
		averageSupport /= size;
		averageRatio /= size;

		averageFitnessHistory.add(averageFitness);
		averageSupportHistory.add(averageSupport);
		averageRatioHistory.add(averageRatio);
		bestFitnessHistory.add(bestFitness);

		return fitnesses;
	}

	private double[] evaluateWithError(Population population) 
	{
		int size = population.size();
		double[] fitnesses = new double[size];
		double averageFitness = 0.0;
		Chromosome[] chromosomes = new Chromosome[size];
		Protein[] proteins = new Protein[size];
		FuncContrastInfo [] infos  = new FuncContrastInfo[size];
		boolean[] visits = new boolean[size];
		
		for (int i = 0; i < size; i++)
		{
			chromosomes[i] = population.get(i);
			proteins[i] = decoder.decode(chromosomes[i]);
			infos[i] = evaluator.evaluateInfo(proteins[i]);			
		}
		
		for (int i = 0; i < size; i++)
		{
			Vector<Double> errorRates = new Vector<Double>();
			Vector<Integer> indexSet = new Vector<Integer>();
			if(infos[i].count <= 0)
			{
				fitnesses[i] = 0.0;		
				visits[i] = true;
				continue;
			}
			for (int j = i; j < size; j++)
			{
				boolean insertSuccess = false;
				if(visits[j] == true)
					continue;
				if(infos[i].count == infos[j].count)
				{
					for(double err:errorRates)
					{
						if(err > infos[j].error)
						{
							int id = errorRates.indexOf(err);
							errorRates.insertElementAt(infos[j].error,id);
							indexSet.insertElementAt(j, id);
							insertSuccess = true;
							break;
						}
					}
					if(insertSuccess == false)
					{
						errorRates.add(infos[j].error);
						indexSet.add(j);
					}
				}
			}
			if(errorRates.size()>1)
			{
				for(int j=0; j<indexSet.size(); j++)
				{
					fitnesses[indexSet.elementAt(j)] = infos[indexSet.elementAt(j)].count - j*(1.0/(double)indexSet.size());
					visits[i] = true;
				}
			}
			else
			{
				fitnesses[i] = infos[i].count;
				visits[i] = true;
			}	
		}
		
		for (int i = 0; i < size; i++)
		{
			averageFitness += fitnesses[i];

			if (fitnesses[i] > bestFitness)
			{
				bestChromosome = chromosomes[i];
				bestProtein = proteins[i];
				bestFitness = fitnesses[i];
				bestGeneration = generation;
				bestTime = System.currentTimeMillis();
			}
		}
		averageFitness /= size;
		averageFitnessHistory.add(new Double(averageFitness));
		bestFitnessHistory.add(new Double(bestFitness));

		return fitnesses;
	}

	private double[] evaluateWithErrorGetConstant(Population population) 
	{
		int size = population.size();
		double[] fitnesses = new double[size];
		double averageFitness = 0.0;
		Chromosome[] chromosomes = new Chromosome[size];
		Protein[] proteins = new Protein[size];
		FuncContrastInfo [] infos  = new FuncContrastInfo[size];
		boolean[] visits = new boolean[size];
		
		for (int i = 0; i < size; i++)
		{
			chromosomes[i] = population.get(i);
			proteins[i] = decoder.decode(chromosomes[i]);
			infos[i] = evaluator.evaluateInfoGetConstant(proteins[i]);			
		}
		
		for (int i = 0; i < size; i++)
		{
			Vector<Double> errorRates = new Vector<Double>();
			Vector<Integer> indexSet = new Vector<Integer>();
			if(infos[i].count <= 0)
			{
				fitnesses[i] = 0.0;		
				visits[i] = true;
				continue;
			}
			for (int j = i; j < size; j++)
			{
				boolean insertSuccess = false;
				if(visits[j] == true)
					continue;
				if(infos[i].count == infos[j].count)
				{
					for(double err:errorRates)
					{
						if(err > infos[j].error)
						{
							int id = errorRates.indexOf(err);
							errorRates.insertElementAt(infos[j].error,id);
							indexSet.insertElementAt(j, id);
							insertSuccess = true;
							break;
						}
					}
					if(insertSuccess == false)
					{
						errorRates.add(infos[j].error);
						indexSet.add(j);
					}
				}
			}
			if(errorRates.size()>1)
			{
				for(int j=0; j<indexSet.size(); j++)
				{
					fitnesses[indexSet.elementAt(j)] = infos[indexSet.elementAt(j)].count - j*(1.0/(double)indexSet.size());
					visits[i] = true;
				}
			}
			else
			{
				fitnesses[i] = infos[i].count;
				visits[i] = true;
			}	
		}
		
		for (int i = 0; i < size; i++)
		{
			averageFitness += fitnesses[i];
			if (fitnesses[i] > bestFitness)
			{
				bestChromosome = chromosomes[i];
				bestProtein = proteins[i];
				bestFitness = fitnesses[i];
				bestConstant = infos[i].constant;
				bestGeneration = generation;
				bestTime = System.currentTimeMillis();
			}
		}
		averageFitness /= size;
		averageFitnessHistory.add(new Double(averageFitness));
		bestFitnessHistory.add(new Double(bestFitness));

		return fitnesses;
	}

	public Protein getBestProtein()
	{
		return bestProtein;
	}

	public double getBestFitness() 
	{
		return bestFitness;
	}

	public double getBestTimeUsed() 
	{
		return bestTime;
	}

	public double getSupport(){ return support;}

	public List<Result> getResult(){return result;};

	private void verbose(double[] fitnesses, int level)
	{
		if (bestFitness != lastBestFitness)
		{
			lastBestFitness = bestFitness;
		}

		if (level == 0) return;

		int size = population.size();
		for (int i = 0; i < size; i++)
		{
			Chromosome chromosome = population.get(i);
		}
	}

	public void report(PrintWriter writer) throws GepException
	{
		writer.println("Selection Operator : " + selectionOperator);
		for (Iterator<MutationOperator> i = mutationOperators.iterator(); i.hasNext();)
		{
			writer.println("Mutation Operator  : " + i.next());
		}
		for (Iterator<CrossoverOperator> i = crossoverOperators.iterator(); i.hasNext();)
		{
			writer.println("Crossover Operator : " + i.next());
		}
		writer.println("Decoder            : " + decoder);
		writer.println("Evaluator          : " + evaluator);
		writer.println("Stopper            : " + stopper);
		writer.println("Fitness Function   : " + fitnessFunction);

		writer.println("Generation         : " + generation);
		writer.println("Population Size    : " + population.size());

		writer.println("Best Chromosome    : " + bestChromosome);
		writer.println("Best Fitness       : " + bestFitness);
		writer.println("Best Formula       : " + bestProtein);

		writer.println("Used Time          : " + (stopTime - startTime));
		writer.println("Used Time to Best  : " + (bestTime - startTime));
	}
}
