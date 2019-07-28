package rs.ac.bg.etf.pp1;
import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticPass extends VisitorAdaptor {

	boolean errorDetected = false;
	int printCallCount = 0;
	Obj currentMethod = null;
	boolean returnFound = false;
	int nVars;
	
	Struct currentType;

	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	/////////////////////////////////////////////////////////////////////
	
    public void visit(DesignatorSingle DesignatorSingle) {
    	String name = DesignatorSingle.getName();
    	Obj o = Tab.find(name);
    	if (o != Tab.noObj) {
    		// uspeh
    		DesignatorSingle.obj = o;
    	}
    	else {
    		report_error("Nije definisan simbol " + name, DesignatorSingle);
    	}
    }
		
	public void visit(Program program) {
		//obj je dva nivoa dole od neterminala Program;
		//na dole uvek mozemo da dohvatamo sta nam treba
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}

	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();     	
	}
	
    public void visit(Type Type) {
    	currentType = Tab.noType;
    	String typeName = Type.getTypeName();
    	Obj typeObj = Tab.find(typeName);
    	if (typeObj != Tab.noObj) {
    		if (typeObj.getKind() == Obj.Type) {
    			currentType = typeObj.getType();
    		}
    		else {
    			report_error(typeObj.getName() + "nije tip", Type);
    		}
    	}else {
    		report_error("Nedefinisan tip " + typeName, Type);
    	}
    	
    	Type.struct = currentType;
    }
	
    public void visit(GlobVarSingleDefWithoutBracket GlobVarSingleDefWithoutBracket) {
    	String name = GlobVarSingleDefWithoutBracket.getVarName();
    	if (Tab.find(name) == Tab.noObj) {
        	Tab.insert(Obj.Var, name, currentType);
    	}
    	else {
    		report_error("Visestruko definisanje simbola " + name, GlobVarSingleDefWithoutBracket);
    	}
    }
    
    public void visit(SingleConstElem SingleConstElem) {
    	String name = SingleConstElem.getConstName();
    	Obj o = Tab.insert(Obj.Con, name, currentType);
    	int address = SingleConstElem.getTypeConst().struct.getKind();
    	o.setAdr(address);
    }
    
    public void visit(CharacterConst CharacterConst) { visit(); }
    public void visit(BooleanConst BooleanConst) { visit(); }
    
    public void visit(NumberConst NumberConst) {
    	//kada posecujemo ovaj cvor napravimo struct cvor sa njegovom vrednoscu
    	//a posle ce TypeConst biti bas taj cvor ako je u pitanju NUMBER
    	//i onda ce se sa SingleConstElem.getTypeConst().struct.getKind();
    	//dohvatiti ta vrednost
    	
    	int value = NumberConst.getValue();
    	Struct s = new Struct(value);
    	NumberConst.struct = s;
    }
    
    public void visit (MethodTypeAndName methodTypeAndName) {
    	Struct type = methodTypeAndName.getType().struct;
    	String name = methodTypeAndName.getMethodName();
    	methodTypeAndName.obj = Tab.insert(Obj.Meth, name, type);
    	Tab.openScope();
    }

    public void visit(MethodVoidAndName methodVoidAndName) {
    	Struct type = Tab.noType;
    	String name = methodVoidAndName.getMethodName();
    	methodVoidAndName.obj = Tab.insert(Obj.Meth, name, type);
    	Tab.openScope();
    }
    
    public void visit(MethodDeclWithoutFormPars methodDeclWithoutFormPars) {
    	Tab.chainLocalSymbols(methodDeclWithoutFormPars.getMethodTypeName().obj);
    	Tab.closeScope();
    }
	
	/*
	public void visit(VarDecl varDecl) {
		report_info("Deklarisana promenljiva "+ varDecl.getVarName(), varDecl);
		Obj varNode = Tab.insert(Obj.Var, varDecl.getVarName(), varDecl.getType().struct);
	}

	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola", null);
			type.struct = Tab.noType;
		} 
		else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} 
			else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip ", type);
				type.struct = Tab.noType;
			}
		}  
	}

	public void visit(MethodDecl methodDecl) {
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funcija " + currentMethod.getName() + " nema return iskaz!", null);
		}
		
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		
		returnFound = false;
		currentMethod = null;
	}

	public void visit(MethodTypeName methodTypeName) {
		currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), methodTypeName.getType().struct);
		methodTypeName.obj = currentMethod;
		Tab.openScope();
		report_info("Obradjuje se funkcija " + methodTypeName.getMethName(), methodTypeName);
	}

	public void visit(Assignment assignment) {
		if (!assignment.getExpr().struct.assignableTo(assignment.getDesignator().obj.getType()))
			report_error("Greska na liniji " + assignment.getLine() + " : " + " nekompatibilni tipovi u dodeli vrednosti ", null);
	}

	public void visit(PrintStmt printStmt){
		printCallCount++;    	
	}

	public void visit(ReturnExpr returnExpr){
		returnFound = true;
		Struct currMethType = currentMethod.getType();
		if (!currMethType.compatibleWith(returnExpr.getExpr().struct)) {
			report_error("Greska na liniji " + returnExpr.getLine() + " : " + "tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije " + currentMethod.getName(), null);
		}			  	     	
	}

	public void visit(ProcCall procCall){
		Obj func = procCall.getDesignator().obj;
		if (Obj.Meth == func.getKind()) { 
			report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + procCall.getLine(), null);
			//RESULT = func.getType();
		} 
		else {
			report_error("Greska na liniji " + procCall.getLine()+" : ime " + func.getName() + " nije funkcija!", null);
			//RESULT = Tab.noType;
		}     	
	}    

	public void visit(AddExpr addExpr) {
		Struct te = addExpr.getExpr().struct;
		Struct t = addExpr.getTerm().struct;
		if (te.equals(t) && te == Tab.intType)
			addExpr.struct = te;
		else {
			report_error("Greska na liniji "+ addExpr.getLine()+" : nekompatibilni tipovi u izrazu za sabiranje.", null);
			addExpr.struct = Tab.noType;
		} 
	}

	public void visit(TermExpr termExpr) {
		termExpr.struct = termExpr.getTerm().struct;
	}

	public void visit(Term term) {
		term.struct = term.getFactor().struct;    	
	}

	public void visit(Const cnst){
		cnst.struct = Tab.intType;    	
	}
	
	public void visit(Var var) {
		var.struct = var.getDesignator().obj.getType();
	}

	public void visit(FuncCall funcCall){
		Obj func = funcCall.getDesignator().obj;
		if (Obj.Meth == func.getKind()) { 
			report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + funcCall.getLine(), null);
			funcCall.struct = func.getType();
		} 
		else {
			report_error("Greska na liniji " + funcCall.getLine()+" : ime " + func.getName() + " nije funkcija!", null);
			funcCall.struct = Tab.noType;
		}

	}

	public void visit(Designator designator){
		Obj obj = Tab.find(designator.getName());
		if (obj == Tab.noObj) { 
			report_error("Greska na liniji " + designator.getLine()+ " : ime "+designator.getName()+" nije deklarisano! ", null);
		}
		designator.obj = obj;
	}

	*/
	
	public boolean passed() {
		return !errorDetected;
	}
	
}

