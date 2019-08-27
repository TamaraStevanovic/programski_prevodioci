// generated with ast extension for cup
// version 0.8
// 27/7/2019 19:26:53


package rs.ac.bg.etf.pp1.ast;

public class ManyGlobVarElements extends GlobVarElem {

    private GlobVarElem GlobVarElem;
    private GlobSingleVarElem GlobSingleVarElem;

    public ManyGlobVarElements (GlobVarElem GlobVarElem, GlobSingleVarElem GlobSingleVarElem) {
        this.GlobVarElem=GlobVarElem;
        if(GlobVarElem!=null) GlobVarElem.setParent(this);
        this.GlobSingleVarElem=GlobSingleVarElem;
        if(GlobSingleVarElem!=null) GlobSingleVarElem.setParent(this);
    }

    public GlobVarElem getGlobVarElem() {
        return GlobVarElem;
    }

    public void setGlobVarElem(GlobVarElem GlobVarElem) {
        this.GlobVarElem=GlobVarElem;
    }

    public GlobSingleVarElem getGlobSingleVarElem() {
        return GlobSingleVarElem;
    }

    public void setGlobSingleVarElem(GlobSingleVarElem GlobSingleVarElem) {
        this.GlobSingleVarElem=GlobSingleVarElem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(GlobVarElem!=null) GlobVarElem.accept(visitor);
        if(GlobSingleVarElem!=null) GlobSingleVarElem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobVarElem!=null) GlobVarElem.traverseTopDown(visitor);
        if(GlobSingleVarElem!=null) GlobSingleVarElem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobVarElem!=null) GlobVarElem.traverseBottomUp(visitor);
        if(GlobSingleVarElem!=null) GlobSingleVarElem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ManyGlobVarElements(\n");

        if(GlobVarElem!=null)
            buffer.append(GlobVarElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobSingleVarElem!=null)
            buffer.append(GlobSingleVarElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ManyGlobVarElements]");
        return buffer.toString();
    }
}
