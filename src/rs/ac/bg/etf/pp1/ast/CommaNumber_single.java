// generated with ast extension for cup
// version 0.8
// 3/8/2026 17:29:6


package rs.ac.bg.etf.pp1.ast;

public class CommaNumber_single extends CommaNumber {

    private Integer N1;

    public CommaNumber_single (Integer N1) {
        this.N1=N1;
    }

    public Integer getN1() {
        return N1;
    }

    public void setN1(Integer N1) {
        this.N1=N1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CommaNumber_single(\n");

        buffer.append(" "+tab+N1);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CommaNumber_single]");
        return buffer.toString();
    }
}
