// generated with ast extension for cup
// version 0.8
// 3/8/2026 17:29:6


package rs.ac.bg.etf.pp1.ast;

public class Statement_findAny extends Statement {

    private DesignatorDot DesignatorDot;
    private FindAnyFlag FindAnyFlag;
    private Expr Expr;

    public Statement_findAny (DesignatorDot DesignatorDot, FindAnyFlag FindAnyFlag, Expr Expr) {
        this.DesignatorDot=DesignatorDot;
        if(DesignatorDot!=null) DesignatorDot.setParent(this);
        this.FindAnyFlag=FindAnyFlag;
        if(FindAnyFlag!=null) FindAnyFlag.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public DesignatorDot getDesignatorDot() {
        return DesignatorDot;
    }

    public void setDesignatorDot(DesignatorDot DesignatorDot) {
        this.DesignatorDot=DesignatorDot;
    }

    public FindAnyFlag getFindAnyFlag() {
        return FindAnyFlag;
    }

    public void setFindAnyFlag(FindAnyFlag FindAnyFlag) {
        this.FindAnyFlag=FindAnyFlag;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorDot!=null) DesignatorDot.accept(visitor);
        if(FindAnyFlag!=null) FindAnyFlag.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorDot!=null) DesignatorDot.traverseTopDown(visitor);
        if(FindAnyFlag!=null) FindAnyFlag.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorDot!=null) DesignatorDot.traverseBottomUp(visitor);
        if(FindAnyFlag!=null) FindAnyFlag.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_findAny(\n");

        if(DesignatorDot!=null)
            buffer.append(DesignatorDot.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FindAnyFlag!=null)
            buffer.append(FindAnyFlag.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_findAny]");
        return buffer.toString();
    }
}
