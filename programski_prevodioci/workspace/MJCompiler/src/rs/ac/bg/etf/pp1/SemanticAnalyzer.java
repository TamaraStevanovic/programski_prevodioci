package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.Obj;

public class SemanticAnalyzer extends VisitorAdaptor {

	Obj currentProgram = null;
	
    public void visit(ProgName ProgName) {
    	currentProgram = Tab.insert(Obj.Prog, ProgName.getProgName(), Tab.noType);
    	Tab.openScope();
    }
    
    public void visit(Program Program) {
    	Tab.chainLocalSymbols(currentProgram);
    	Tab.closeScope();
    }

	
}
