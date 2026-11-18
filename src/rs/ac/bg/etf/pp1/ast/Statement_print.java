// generated with ast extension for cup
// version 0.8
// 3/8/2026 17:29:6


package rs.ac.bg.etf.pp1.ast;

public class Statement_print extends Statement {

    private Expr Expr;
    private CommaNumber CommaNumber;

    public Statement_print (Expr Expr, CommaNumber CommaNumber) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.CommaNumber=CommaNumber;
        if(CommaNumber!=null) CommaNumber.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public CommaNumber getCommaNumber() {
        return CommaNumber;
    }

    public void setCommaNumber(CommaNumber CommaNumber) {
        this.CommaNumber=CommaNumber;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(CommaNumber!=null) CommaNumber.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(CommaNumber!=null) CommaNumber.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(CommaNumber!=null) CommaNumber.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_print(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CommaNumber!=null)
            buffer.append(CommaNumber.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_print]");
        return buffer.toString();
    }
}
