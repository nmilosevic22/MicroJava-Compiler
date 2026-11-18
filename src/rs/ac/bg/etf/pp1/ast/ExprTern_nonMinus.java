// generated with ast extension for cup
// version 0.8
// 3/8/2026 17:29:6


package rs.ac.bg.etf.pp1.ast;

public class ExprTern_nonMinus extends ExprTern {

    private Term Term;
    private AddTerm AddTerm;

    public ExprTern_nonMinus (Term Term, AddTerm AddTerm) {
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
        this.AddTerm=AddTerm;
        if(AddTerm!=null) AddTerm.setParent(this);
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public AddTerm getAddTerm() {
        return AddTerm;
    }

    public void setAddTerm(AddTerm AddTerm) {
        this.AddTerm=AddTerm;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Term!=null) Term.accept(visitor);
        if(AddTerm!=null) AddTerm.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
        if(AddTerm!=null) AddTerm.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Term!=null) Term.traverseBottomUp(visitor);
        if(AddTerm!=null) AddTerm.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprTern_nonMinus(\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AddTerm!=null)
            buffer.append(AddTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprTern_nonMinus]");
        return buffer.toString();
    }
}
