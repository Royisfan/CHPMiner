package gep.bool;

import ga.Evaluator;
import ga.Protein;
import gep.GEP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import contrastFunc.FuncContrastInfo;

public class BEvaluator implements Evaluator
{
	protected GEP gep;
	protected double maxFitness;
	protected List<boolean[]> trainData = new ArrayList<boolean[]>(); // ÑµÁ·Êý¾Ý

	protected BEvaluator(GEP gep)
	{
		this.gep = gep;
	}

	public double evaluate(Protein protein)
	{
		BFormula formula = (BFormula) protein;

		int ok = 0;
		for (Iterator<boolean[]> iterator = trainData.iterator(); iterator.hasNext();)
		{
			boolean[] sample = (boolean[]) iterator.next();
			boolean target = sample[0];

			boolean model;
			try
			{
				formula.setValues(sample);
				model = formula.evaluate();
			}
			catch (Exception e)
			{
				return 1.0;
			}

			if (target == model) ok++;
		}

		return ok;
	}

	public FuncContrastInfo evaluateInfo(Protein protein) {
		// TODO Auto-generated method stub
		return null;
	}

	public FuncContrastInfo evaluateInfoGetConstant(Protein protein) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getSup0() {
		return 0;
	}

	@Override
	public double getSup1() {
		return 0;
	}
}
