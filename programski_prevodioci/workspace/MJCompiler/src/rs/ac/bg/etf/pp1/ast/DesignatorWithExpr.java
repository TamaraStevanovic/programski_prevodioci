// generated with ast extension for cup
// version 0.8
// 26/7/2019 19:57:48


package rs.ac.bg.etf.pp1.ast;

public class DesignatorWithExpr extends Designator {

    private DesignatorIdentity DesignatorIdentity;
    private Expr Expr;

    public DesignatorWithExpr (DesignatorIdentity DesignatorIdentity, Expr Expr) {
        this.DesignatorIdentity=DesignatorIdentity;
        if(DesignatorIdentity!=null) DesignatorIdentity.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public DesignatorIdentity getDesignatorIdentity() {
        return DesignatorIdentity;
    }

    public void setDesignatorIdentity(DesignatorIdentity DesignatorIdentity) {
        this.DesignatorIdentity=DesignatorIdentity;
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
        if(DesignatorIdentity!=null) DesignatorIdentity.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorIdentity!=null) DesignatorIdentity.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorIdentity!=null) DesignatorIdentity.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorWithExpr(\n");

        if(DesignatorIdentity!=null)
            buffer.append(DesignatorIdentity.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorWithExpr]");
        return buffer.toString();
    }
}
