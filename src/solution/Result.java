package solution;

import ga.Protein;

import java.util.Objects;

public class Result {

    private Protein protein;
    private double support;
    private double fitness;
    private double ratio;

    public Protein getProtein() {
        return protein;
    }

    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    public double getSupport() {
        return support;
    }

    public void setSupport(double support) {
        this.support = support;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public String toString(){
        return "pattern : " + this.protein.toString() + "\tsupport :" +
                this.support + "\tfitness : " + this.fitness + "\tratio : "
                + this.ratio;
    }

    public String toString(Protein protein){
        return "pattern : " + protein.toString() + " ^ " + this.protein.toString() +
                "\tsupport :" + this.support + "\tfitness : " + this.fitness + "\tratio : "
                + this.ratio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(protein, result.protein);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protein);
    }
}

