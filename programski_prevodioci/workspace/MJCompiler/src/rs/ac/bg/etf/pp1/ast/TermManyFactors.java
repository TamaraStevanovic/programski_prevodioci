// generated with ast extension for cup
// version 0.8
// 12/5/2019 23:8:3


package rs.ac.bg.etf.pp1.ast;

public class TermManyFactors extends Term {

    private Term Term;
    private Mullop Mullop;
    private Factor Factor;

    public TermManyFactors (Term Term, Mullop Mullop, Factor Factor) {
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
        this.Mullop=Mullop;
        if(Mullop!=null) Mullop.setParent(this);
        this.Factor=Factor;
        if(Factor!=null) Factor.setParent(this);
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public Mullop getMullop() {
        return Mullop;
    }

    public void setMullop(Mullop Mullop) {
        this.Mullop=Mullop;
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
        if(Term!=null) Term.accept(visitor);
        if(Mullop!=null) Mullop.accept(visitor);
        if(Factor!=null) Factor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
        if(Mullop!=null) Mullop.traverseTopDown(visitor);
        if(Factor!=null) Factor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Term!=null) Term.traverseBottomUp(visitor);
        if(Mullop!=null) Mullop.traverseBottomUp(visitor);
        if(Factor!=null) Factor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TermManyFactors(\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Mullop!=null)
            buffer.append(Mullop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Factor!=null)
            buffer.append(Factor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TermManyFactors]");
        return buffer.toString();
    }
}
