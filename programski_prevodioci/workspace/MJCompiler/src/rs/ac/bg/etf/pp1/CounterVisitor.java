package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.GlobVarSingleDefWithBracket;
import rs.ac.bg.etf.pp1.ast.GlobVarSingleDefWithoutBracket;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {
	protected int count;

	public int getCount() {
		return count;
	}

	public static class VarCounter extends CounterVisitor {

		public void visit(GlobVarSingleDefWithoutBracket varDecl) {
			count++;
		}

		public void visit(GlobVarSingleDefWithBracket varDecl) {
			count++;
		}
	}
}
