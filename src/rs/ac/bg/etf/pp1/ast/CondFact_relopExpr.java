// generated with ast extension for cup
// version 0.8
// 3/8/2026 17:29:6


package rs.ac.bg.etf.pp1.ast;

public class CondFact_relopExpr extends CondFact {

    private ExprTern ExprTern;
    private Relop Relop;
    private ExprTern ExprTern1;

    public CondFact_relopExpr (ExprTern ExprTern, Relop Relop, ExprTern ExprTern1) {
        this.ExprTern=ExprTern;
        if(ExprTern!=null) ExprTern.setParent(this);
        this.Relop=Relop;
        if(Relop!=null) Relop.setParent(this);
        this.ExprTern1=ExprTern1;
        if(ExprTern1!=null) ExprTern1.setParent(this);
    }

    public ExprTern getExprTern() {
        return ExprTern;
    }

    public void setExprTern(ExprTern ExprTern) {
        this.ExprTern=ExprTern;
    }

    public Relop getRelop() {
        return Relop;
    }

    public void setRelop(Relop Relop) {
        this.Relop=Relop;
    }

    public ExprTern getExprTern1() {
        return ExprTern1;
    }

    public void setExprTern1(ExprTern ExprTern1) {
        this.ExprTern1=ExprTern1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprTern!=null) ExprTern.accept(visitor);
        if(Relop!=null) Relop.accept(visitor);
        if(ExprTern1!=null) ExprTern1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprTern!=null) ExprTern.traverseTopDown(visitor);
        if(Relop!=null) Relop.traverseTopDown(visitor);
        if(ExprTern1!=null) ExprTern1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprTern!=null) ExprTern.traverseBottomUp(visitor);
        if(Relop!=null) Relop.traverseBottomUp(visitor);
        if(ExprTern1!=null) ExprTern1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondFact_relopExpr(\n");

        if(ExprTern!=null)
            buffer.append(ExprTern.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Relop!=null)
            buffer.append(Relop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExprTern1!=null)
            buffer.append(ExprTern1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondFact_relopExpr]");
        return buffer.toString();
    }
}
