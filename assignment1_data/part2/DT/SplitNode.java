public class SplitNode implements Node{
    String attName;
    Node left;
    Node right;
    public SplitNode(String attName, Node l, Node r){
        this.attName=attName;
        left=l;
        right=r;
    }
    /**
     * taken from helper code given in assignment
     */
    public void report(String indent){
        System.out.printf("%s%s = True:%n", indent, attName);
        left.report(indent+"\t");
        System.out.printf("%s%s = False:%n", indent, attName);
        right.report(indent+"\t");
        }
}
