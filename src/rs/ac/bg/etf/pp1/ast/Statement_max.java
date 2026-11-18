// generated with ast extension for cup
// version 0.8
// 3/8/2026 17:29:6


package rs.ac.bg.etf.pp1.ast;

public class Statement_max extends Statement {

    private DesignatorDot DesignatorDot;

    public Statement_max (DesignatorDot DesignatorDot) {
        this.DesignatorDot=DesignatorDot;
        if(DesignatorDot!=null) DesignatorDot.setParent(this);
    }

    public DesignatorDot getDesignatorDot() {
        return DesignatorDot;
    }

    public void setDesignatorDot(DesignatorDot DesignatorDot) {
        this.DesignatorDot=DesignatorDot;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorDot!=null) DesignatorDot.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorDot!=null) DesignatorDot.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorDot!=null) DesignatorDot.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_max(\n");

        if(DesignatorDot!=null)
            buffer.append(DesignatorDot.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_max]");
        return buffer.toString();
    }
}
