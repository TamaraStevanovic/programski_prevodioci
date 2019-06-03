// generated with ast extension for cup
// version 0.8
// 29/4/2019 19:31:16


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationList extends VarDeclList {

    private GlobVarDecl GlobVarDecl;

    public VarDeclarationList (GlobVarDecl GlobVarDecl) {
        this.GlobVarDecl=GlobVarDecl;
        if(GlobVarDecl!=null) GlobVarDecl.setParent(this);
    }

    public GlobVarDecl getGlobVarDecl() {
        return GlobVarDecl;
    }

    public void setGlobVarDecl(GlobVarDecl GlobVarDecl) {
        this.GlobVarDecl=GlobVarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(GlobVarDecl!=null) GlobVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobVarDecl!=null) GlobVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobVarDecl!=null) GlobVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclarationList(\n");

        if(GlobVarDecl!=null)
            buffer.append(GlobVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationList]");
        return buffer.toString();
    }
}
