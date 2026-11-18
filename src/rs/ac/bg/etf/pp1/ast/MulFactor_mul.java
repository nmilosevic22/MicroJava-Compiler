// generated with ast extension for cup
// version 0.8
// 3/8/2026 17:29:6


package rs.ac.bg.etf.pp1.ast;

public class MulFactor_mul extends MulFactor {

    private MulFactor MulFactor;
    private Mulop Mulop;
    private Factor Factor;

    public MulFactor_mul (MulFactor MulFactor, Mulop Mulop, Factor Factor) {
        this.MulFactor=MulFactor;
        if(MulFactor!=null) MulFactor.setParent(this);
        this.Mulop=Mulop;
        if(Mulop!=null) Mulop.setParent(this);
        this.Factor=Factor;
        if(Factor!=null) Factor.setParent(this);
    }

    public MulFactor getMulFactor() {
        return MulFactor;
    }

    public void setMulFactor(MulFactor MulFactor) {
        this.MulFactor=MulFactor;
    }

    public Mulop getMulop() {
        return Mulop;
    }

    public void setMulop(Mulop Mulop) {
        this.Mulop=Mulop;
    }

    public Factor getFactor() {
        return Factor;
    }

    public void setFactor(Factor Factor) {
        this.Factor=Factor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MulFactor!=null) MulFactor.accept(visitor);
        if(Mulop!=null) Mulop.accept(visitor);
        if(Factor!=null) Factor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MulFactor!=null) MulFactor.traverseTopDown(visitor);
        if(Mulop!=null) Mulop.traverseTopDown(visitor);
        if(Factor!=null) Factor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MulFactor!=null) MulFactor.traverseBottomUp(visitor);
        if(Mulop!=null) Mulop.traverseBottomUp(visitor);
        if(Factor!=null) Factor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MulFactor_mul(\n");

        if(MulFactor!=null)
            buffer.append(MulFactor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Mulop!=null)
            buffer.append(Mulop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Factor!=null)
            buffer.append(Factor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MulFactor_mul]");
        return buffer.toString();
    }
}
