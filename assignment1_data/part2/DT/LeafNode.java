public class LeafNode implements Node{
    double probability;
    String className;
    public LeafNode(String cls, double prob){
        this.className=cls;
        this.probability=prob;
    }
    public void report(String indent){
        if (probability==0){ //Error-checking
        System.out.printf("%sUnknown%n", indent);
        }else{
        System.out.printf("%sClass %s, prob=%.2f%n", indent, className, probability);
        }}
}
