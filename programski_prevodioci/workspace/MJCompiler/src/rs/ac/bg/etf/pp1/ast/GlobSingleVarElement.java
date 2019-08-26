// generated with ast extension for cup
// version 0.8
// 26/7/2019 19:57:48


package rs.ac.bg.etf.pp1.ast;

public class GlobSingleVarElement extends GlobVarElem {

    private GlobSingleVarElem GlobSingleVarElem;

    public GlobSingleVarElement (GlobSingleVarElem GlobSingleVarElem) {
        this.GlobSingleVarElem=GlobSingleVarElem;
        if(GlobSingleVarElem!=null) GlobSingleVarElem.setParent(this);
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
        if(GlobSingleVarElem!=null) GlobSingleVarElem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobSingleVarElem!=null) GlobSingleVarElem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobSingleVarElem!=null) GlobSingleVarElem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobSingleVarElement(\n");

        if(GlobSingleVarElem!=null)
            buffer.append(GlobSingleVarElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobSingleVarElement]");
        return buffer.toString();
    }
}
