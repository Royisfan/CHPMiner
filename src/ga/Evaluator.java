package ga;

import contrastFunc.FuncContrastInfo;

public interface Evaluator
{
	public double evaluate(Protein protein);

	public FuncContrastInfo evaluateInfo(Protein protein);
	public FuncContrastInfo evaluateInfoGetConstant(Protein protein);

	public double getSup0();
	public double getSup1();
}
