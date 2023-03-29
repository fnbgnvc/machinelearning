
public class PInstance {
    double[] vars;
    boolean good;
    PInstance(){}
    void setVars(double[] vars){this.vars=vars;}
    void setClass(boolean good){this.good=good;}
    public boolean gitGud(){return good;}
    public double getVar(int i){return vars[i];}
}