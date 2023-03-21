public class SplitNode implements Node{
    String attName;
    Node left;
    Node right;
    public void report(String indent){
        System.out.printf("%s%s = True:%n", indent, attName);
        left.report(indent+"\t");
        System.out.printf("%s%s = False:%n", indent, attName);
        right.report(indent+"\t");
        }
}
