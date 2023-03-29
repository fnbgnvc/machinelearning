package assignment1_data.part3;

public class Instance {
    double[] vars;
    boolean good;
    Instance(){}
    void setVars(double[] vars){this.vars=vars;}
    void setClass(boolean good){this.good=good;}
    public boolean gitGud(){return good;}
    public double getVar(int i){return vars[i];}
}
