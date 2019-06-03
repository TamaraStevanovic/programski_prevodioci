// generated with ast extension for cup
// version 0.8
// 29/4/2019 19:31:16


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Designator Designator);
    public void visit(EnumSingleElem EnumSingleElem);
    public void visit(MethodDecl MethodDecl);
    public void visit(Factor Factor);
    public void visit(DesignatorIdentity DesignatorIdentity);
    public void visit(Stmt Stmt);
    public void visit(ConstDecl ConstDecl);
    public void visit(EnumIdent EnumIdent);
    public void visit(FormParam FormParam);
    public void visit(GlobVarElem GlobVarElem);
    public void visit(GlobSingleVarElem GlobSingleVarElem);
    public void visit(Declarations Declarations);
    public void visit(Expr Expr);
    public void visit(FormPars FormPars);
    public void visit(VarDeclList VarDeclList);
    public void visit(DeclarationList DeclarationList);
    public void visit(EnumElem EnumElem);
    public void visit(DesignatorStmt DesignatorStmt);
    public void visit(Mullop Mullop);
    public void visit(Addop Addop);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(TypeConst TypeConst);
    public void visit(ConstElem ConstElem);
    public void visit(Term Term);
    public void visit(StmtList StmtList);
    public void visit(MultiplyOpMod MultiplyOpMod);
    public void visit(MultiplyOpDivide MultiplyOpDivide);
    public void visit(MultiplyOpTimes MultiplyOpTimes);
    public void visit(AddOperationMinus AddOperationMinus);
    public void visit(AddOperationPlus AddOperationPlus);
    public void visit(FactorRegularExpr FactorRegularExpr);
    public void visit(FactorNew FactorNew);
    public void visit(FactorNewArray FactorNewArray);
    public void visit(FactorConst FactorConst);
    public void visit(TermManyFactors TermManyFactors);
    public void visit(TermSingleFactor TermSingleFactor);
    public void visit(ExprList ExprList);
    public void visit(ExprMinusTerm ExprMinusTerm);
    public void visit(ExprTerm ExprTerm);
    public void visit(EnumValue EnumValue);
    public void visit(EnumIdentity EnumIdentity);
    public void visit(DesignatorName DesignatorName);
    public void visit(DesignatorWithExpr DesignatorWithExpr);
    public void visit(DesinatorWithDOT DesinatorWithDOT);
    public void visit(DesignatorSingle DesignatorSingle);
    public void visit(DesignatorStmtDEc DesignatorStmtDEc);
    public void visit(DesignatorStmtINC DesignatorStmtINC);
    public void visit(DesignatorStmtAssign DesignatorStmtAssign);
    public void visit(PrintStatementOneArg PrintStatementOneArg);
    public void visit(PrintStatementTwoArg PrintStatementTwoArg);
    public void visit(ReadStatement ReadStatement);
    public void visit(Assignment Assignment);
    public void visit(NoStatements NoStatements);
    public void visit(Statements Statements);
    public void visit(NoVarDeclarationList NoVarDeclarationList);
    public void visit(VarDeclarationList VarDeclarationList);
    public void visit(FormalParamArrayDef FormalParamArrayDef);
    public void visit(FormalParamDef FormalParamDef);
    public void visit(FormalParametersMany FormalParametersMany);
    public void visit(FormalParameterOne FormalParameterOne);
    public void visit(MethodDeclVoidWithoutFormPars MethodDeclVoidWithoutFormPars);
    public void visit(MethodDeclWithoutFormPars MethodDeclWithoutFormPars);
    public void visit(MethodDeclVoidWithFormPars MethodDeclVoidWithFormPars);
    public void visit(MethodDeclWithFormPars MethodDeclWithFormPars);
    public void visit(NoMethodDecl NoMethodDecl);
    public void visit(MethodDeclList1 MethodDeclList1);
    public void visit(EnumSingleDef EnumSingleDef);
    public void visit(EnumSingleDefDefault EnumSingleDefDefault);
    public void visit(EnumManyElements EnumManyElements);
    public void visit(EnumSingleElement EnumSingleElement);
    public void visit(EnumDecl EnumDecl);
    public void visit(GlobVarSingleDefWithBracket GlobVarSingleDefWithBracket);
    public void visit(GlobVarSingleDefWithoutBracket GlobVarSingleDefWithoutBracket);
    public void visit(ManyGlobVarElements ManyGlobVarElements);
    public void visit(GlobSingleVarElement GlobSingleVarElement);
    public void visit(GlobVarDecl GlobVarDecl);
    public void visit(CharacterConst CharacterConst);
    public void visit(BooleanConst BooleanConst);
    public void visit(NumberConst NumberConst);
    public void visit(SingleConstElem SingleConstElem);
    public void visit(ManyConstElemDecl ManyConstElemDecl);
    public void visit(SingleConstElemDecl SingleConstElemDecl);
    public void visit(ConstDeclaration ConstDeclaration);
    public void visit(DeclarationsEnumDecl DeclarationsEnumDecl);
    public void visit(DeclarationsGlobVarDecl DeclarationsGlobVarDecl);
    public void visit(DeclarationsConstDecl DeclarationsConstDecl);
    public void visit(NoDeclarations NoDeclarations);
    public void visit(Declarations1 Declarations1);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
