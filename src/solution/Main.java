package solution;

import logical.LogicalDataSet;
import logical.LogicalEvaluator;
import bool.BoolDataSet;
import bool.BoolEvaluator;
import ga.Evaluator;
import ga.MaxGenerationStopper;
import ga.Stopper;
import gep.GEP;
import gep.bool.BFormula;

import java.util.ArrayList;
import java.util.List;

public class Main implements Runnable{

    private GEP gep;

    public long time = 0;
    public BFormula bFormula;
    public List<Double> averageSupportHistory = new ArrayList<>();
    public List<Double> averageRatioHistory = new ArrayList<>();
    public List<Result> finalResult = new ArrayList<>();
    @Override
    public void run() {
        try {

            List<String[]> posData = DatasetProcessor.getDataset(Parameter.posDataPath);
            List<String[]> negData = DatasetProcessor.getDataset(Parameter.negDataPath);

            List<boolean[]> boolPosData = DatasetProcessor.getBoolDataset(posData);
            List<boolean[]> boolNegData = DatasetProcessor.getBoolDataset(negData);
            BoolDataSet trainPosBool = new BoolDataSet(boolPosData);
            BoolDataSet trainNegBool = new BoolDataSet(boolNegData);

            gep = GepConstructor.ConstructBoolGep();
            gep.threshold = Parameter.threshold;
            Evaluator boolEvaluator = new BoolEvaluator(trainPosBool, trainNegBool);
            gep.setEvaluator(boolEvaluator);

            Stopper boolStopper = new MaxGenerationStopper(gep, Parameter.boolMaxGenerationStopper);
            gep.setStopper(boolStopper);

            gep.run();
            time += gep.bestTime;

            BFormula boolFormula = (BFormula)gep.getBestProtein();
            bFormula = boolFormula;

            List<String[]> posData1 = Utils.filterDatasetByBoolFormula(boolFormula, trainPosBool.getData(), posData);
            List<String[]> negData1 = negData;
            List<double[]> logicalPosData = DatasetProcessor.getLogicalDataset(posData1);
            List<double[]> logicalNegData = DatasetProcessor.getLogicalDataset(negData1);
            LogicalDataSet trainPosLogical = new LogicalDataSet(logicalPosData);
            LogicalDataSet trainNegLogical = new LogicalDataSet(logicalNegData);

            gep = GepConstructor.ConstructLogicalGep();
            gep.threshold = Parameter.threshold;
            Evaluator logicalEvaluator = new LogicalEvaluator(trainPosLogical, trainNegLogical);
            gep.setEvaluator(logicalEvaluator);

            Stopper logicalStopper = new MaxGenerationStopper(gep, Parameter.logicalMaxGenerationStopper);
            gep.setStopper(logicalStopper);

            gep.run();
            time += gep.bestTime;
            averageRatioHistory = gep.averageRatioHistory;
            averageSupportHistory = gep.averageSupportHistory;

            List<Result> results2 = gep.getResult();
            Utils.resultSort(results2);
            List<Result> results3 = Utils.duplicateRemove(results2);
            Utils.resultSort(results3);
            finalResult = results3;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int runTimes = 1;
        for (int i = 0; i < runTimes; i++) {
            Main main = new Main();
            Thread worker = new Thread(main);
            worker.setPriority(Thread.MIN_PRIORITY);
            worker.start();
            while(worker.isAlive())
            {
                ;
            }
        }
    }
}
