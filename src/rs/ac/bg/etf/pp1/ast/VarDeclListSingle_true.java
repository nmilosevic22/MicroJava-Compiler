// generated with ast extension for cup
// version 0.8
// 3/8/2026 17:29:6


package rs.ac.bg.etf.pp1.ast;

public class VarDeclListSingle_true extends VarDeclListSingle {

    private VarDeclList VarDeclList;
    private VarDeclListSingle VarDeclListSingle;

    public VarDeclListSingle_true (VarDeclList VarDeclList, VarDeclListSingle VarDeclListSingle) {
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.VarDeclListSingle=VarDeclListSingle;
        if(VarDeclListSingle!=null) VarDeclListSingle.setParent(this);
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public VarDeclListSingle getVarDeclListSingle() {
        return VarDeclListSingle;
    }

    public void setVarDeclListSingle(VarDeclListSingle VarDeclListSingle) {
        this.VarDeclListSingle=VarDeclListSingle;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(VarDeclListSingle!=null) VarDeclListSingle.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(VarDeclListSingle!=null) VarDeclListSingle.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(VarDeclListSingle!=null) VarDeclListSingle.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclListSingle_true(\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclListSingle!=null)
            buffer.append(VarDeclListSingle.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclListSingle_true]");
        return buffer.toString();
    }
}
