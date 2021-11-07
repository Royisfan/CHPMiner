package solution;

import gep.bool.BFormula;

import java.util.*;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

class Utils {
    private static String getFileName(String fileName)
    {
        StringTokenizer st = new StringTokenizer( fileName, "." );
        if(st.hasMoreTokens())
            fileName = st.nextToken();

        return fileName;
    }

    static List<String[]> filterDatasetByBoolFormula(BFormula formula, List<boolean[]> judgeData, List<String[]> data){
        assert judgeData.size() == data.size();
        List<String[]> result = new ArrayList<>();
        for (int i = 0; i < judgeData.size(); i++) {
            boolean b = false;
            try {
                formula.setValues(judgeData.get(i));
                b = formula.evaluate();
            }catch (Exception e){
                e.printStackTrace();
                return new ArrayList<>();
            }

            if(b){
                result.add(data.get(i));
            }
        }
        return result;
    }

    static void resultSort(List<Result> results){
        results.sort((o1, o2) -> {
            if(o1.getRatio() > o2.getRatio()){
                return -1;
            }else if(o1.getRatio() == o2.getRatio()){
                if(o1.getSupport() > o2.getSupport()){
                    return -1;
                }else if(o1.getSupport() == o2.getSupport()){
                    if(o1.getFitness() > o2.getFitness()){
                        return -1;
                    }else if (o1.getFitness() == o2.getFitness()){
                        return 0;
                    }else {
                        return 1;
                    }
                }else {
                    return 1;
                }
            }else {
                return 1;
            }
        });
    }

    static List<Result> duplicateRemove(List<Result> results){
        return results.stream().collect(
                collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getProtein().toString()))), ArrayList::new));
    }
}
