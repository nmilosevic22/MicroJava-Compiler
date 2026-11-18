package rs.ac.bg.etf.pp1;

import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPc;
	
	private int tempAddr;
	
	private Stack endJumps = new Stack<> ();
	private Stack falseJumps = new Stack<> ();
	
	public int getMainPc() {
		return this.mainPc;
	}
	
	@Override
	public void visit (Method method) {
		if("main".equals(method.obj.getName())) {
			mainPc = Code.pc;
		}
		
		tempAddr = method.obj.getLocalSymbols().size();
		
		Code.put(Code.enter);
		Code.put(method.obj.getLevel());
		Code.put(method.obj.getLocalSymbols().size() + 8);
	}
	
	@Override
	public void visit (MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit (DesignatorStatement designatorStatement) {
		
		if (designatorStatement.getDesignatorList() instanceof DesignatorList_assign) {
			Code.store(designatorStatement.getDesignator().obj);
		}
		
		else if (designatorStatement.getDesignatorList() instanceof DesignatorList_inc) {
			
			if (designatorStatement.getDesignator().obj.getKind() == Obj.Elem) {
				Code.put(Code.dup2);
			}
			
			Code.load(designatorStatement.getDesignator().obj);
			Code.loadConst(1);
			Code.put(Code.add);
			Code.store(designatorStatement.getDesignator().obj);
		}
		
		else if (designatorStatement.getDesignatorList() instanceof DesignatorList_dec) {
			
			if (designatorStatement.getDesignator().obj.getKind() == Obj.Elem) {
				Code.put(Code.dup2);
			}
			
			Code.load(designatorStatement.getDesignator().obj);
			Code.loadConst(-1);
			Code.put(Code.add);
			Code.store(designatorStatement.getDesignator().obj);
		}
	}
	
	@Override
	public void visit (Designator_dotLength designator_dotLength) {
		Code.load(designator_dotLength.getDesignator().obj);
		Code.put(Code.arraylength);
	}
	
	@Override
	public void visit (CommaNumber_single commaNumber_single) {
		Code.loadConst(commaNumber_single.getN1());
	}
	
	@Override
	public void visit (CommaNumber_e commaNumber_e) {
		//default print width
		Code.loadConst(5);				
	}
	
	@Override
	public void visit (Statement_print statement_print) {
		Struct exprType = statement_print.getExpr().struct;
		
		if (exprType.equals(Tab.charType)) {
			Code.put(Code.bprint);
		}
		else {
			Code.put(Code.print);
		}
	}
	
	@Override 
	public void visit (Statement_read statement_read) {
		if (statement_read.getDesignator().obj.getType() == Tab.charType) {
			Code.put(Code.bread);
		}
		
		else {
			Code.put(Code.read);
		}
		
		Code.store(statement_read.getDesignator().obj);
	}
	
	@Override
	public void visit (Statement_findAny statement_findAny) {
		Obj leftDes = statement_findAny.getDesignatorDot().getDesignator().obj;
		Obj rightDes = statement_findAny.getDesignatorDot().getDesignator1().obj;
		
		Struct arrayType = rightDes.getType();
		Struct elemType = rightDes.getType().getElemType();
		
		Obj findAnyArray = new Obj(Obj.Var, "findAnyArray", arrayType, tempAddr, 1);
		Obj findAnySearch = new Obj (Obj.Var, "findAnyValue", elemType, tempAddr + 1, 1);
		Obj findAnyIndex = new Obj (Obj.Var, "findAnyIndex", Tab.intType, tempAddr + 2, 1);
		
		Code.store(findAnySearch);
		Code.store(findAnyArray);
		
		Code.loadConst(0);
		Code.store(findAnyIndex);
		
		//setpoint for while loop
		int setPoint = Code.pc;
		
		//if (index < array.length)
		Code.load(findAnyIndex);
		Code.load(findAnyArray);
		Code.put(Code.arraylength);
		
		Code.putFalseJump(Code.lt, 0);
		int notFoundAddr = Code.pc - 2;
		
		//index < array.length, loading array[index]
		Code.load(findAnyArray);
		Code.load(findAnyIndex);
		if (elemType == Tab.charType) {
			Code.put(Code.baload);
		}
		else {
			Code.put(Code.aload);
		}
		
		//searchedValue == array[index] ?
		Code.load(findAnySearch);
		Code.putFalseJump(Code.eq, 0);
		int nextElem = Code.pc - 2;
		
		//elem found, result is true (1), jumping out of loop
		Code.loadConst(1);
		
		Code.putJump(0);
		int foundAddr = Code.pc - 2;
		
		
		//NEXT ELEM:
		//elem not found, incrementing index, unconditional jump to loop
		Code.fixup(nextElem);
		
		Code.load(findAnyIndex);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(findAnyIndex);
		
		Code.putJump(setPoint);
		
		
		//NOT_FOUND_ADDR:
		//elem is not found, ending loop, result is false (0)
		
		Code.fixup(notFoundAddr);
		Code.loadConst(0);
		
		
		//FOUND_ADDR
		Code.fixup(foundAddr);
		
		
		//storing result in left designator
		Code.store(leftDes);
	}
	
	@Override
	public void visit (FindAnyFlag findAnyFlag) {
		Statement_findAny parent = (Statement_findAny) findAnyFlag.getParent();
		
		Code.load(parent.getDesignatorDot().getDesignator1().obj);
	}
	
	@Override
	public void visit (Statement_max statement_max) {
		Obj desDest = statement_max.getDesignatorDot().getDesignator().obj;
		Obj desBase = statement_max.getDesignatorDot().getDesignator1().obj;
		
		Obj indexCount = 	new Obj(Obj.Var, "indexCount", Tab.intType, tempAddr + 6, 1);
		Obj currMax = 	new Obj(Obj.Var, "currMax", Tab.intType, tempAddr + 7, 1);
		
		//initialize max value
		Code.loadConst(0);
		Code.store(currMax);
		
		//initialize index counter
		Code.loadConst(0);
		Code.store(indexCount);
		
		//loop start
		int setPoint = Code.pc;
		
		Code.load(indexCount);
		Code.load(desBase);
		Code.put(Code.arraylength);
		
		Code.putFalseJump(Code.lt, 0);
		int indexOutOfRange = Code.pc - 2;
		
		Code.load(desBase);
		Code.load(indexCount);
		Code.put(Code.aload);
		
		Code.load(currMax);
		
		Code.putFalseJump(Code.gt, 0);
		int nextElem = Code.pc - 2;
		
		Code.load(desBase);
		Code.load(indexCount);
		Code.put(Code.aload);
		Code.store(currMax);
		
		//incrementing index counter
		Code.fixup(nextElem);
		
		Code.load(indexCount);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(indexCount);
		
		Code.putJump(setPoint);
		
		//jumping out of the loop
		Code.fixup(indexOutOfRange);
		
		Code.load(currMax);
		Code.store(desDest);
	}
	
	@Override
	public void visit (Statement_count statement_count) {
		Obj desDest = statement_count.getDesignatorDot().getDesignator().obj;
		Obj desBase = statement_count.getDesignatorDot().getDesignator1().obj;
		
		Struct exprType = statement_count.getExpr().struct;
		
		Obj exprObj = 		new Obj(Obj.Var, "exprObj", exprType, tempAddr + 3, 1);
		Obj countedElems = 	new Obj(Obj.Var, "countedElems", Tab.intType, tempAddr + 4, 1);
		Obj indexCounter = 	new Obj(Obj.Var, "indexCounter", Tab.intType, tempAddr + 5, 1);
		
		Code.load(indexCounter);
		
		int setPoint = Code.pc;
		
		Code.load(desBase);
		Code.put(Code.arraylength);
		
		Code.putFalseJump(Code.lt, 0);
		int indexOutOfRange = Code.pc - 2;
		
		Code.load(desBase);
		Code.load(indexCounter);
		Code.load(exprObj);
		
		Code.putFalseJump(Code.eq, 0);
		int nextElem = Code.pc - 2;
		
		Code.load(countedElems);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(countedElems);
		
		Code.fixup(nextElem);
		
		Code.load(indexCounter);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(indexCounter);
		Code.load(indexCounter);
		Code.putJump(setPoint);
		
		Code.fixup(indexOutOfRange);
		
		Code.load(countedElems);
		Code.store(desDest);
	}
	
	@Override
	public void visit (Factor_num factor_num) {
		Code.loadConst(factor_num.getN1());
	}
	
	@Override
	public void visit (Factor_char Factor_char) {
		Code.loadConst(Factor_char.getC1());
	}
	
	@Override
	public void visit (Factor_bool factor_bool) {
		Code.loadConst(factor_bool.getB1());
	}
	
	@Override
	public void visit (Factor_des factor_des) {
		if(!(factor_des.getDesignator() instanceof Designator_dotLength)) {
			Code.load(factor_des.getDesignator().obj);
		}
	}
	
	@Override
	public void visit (Factor_exprBrackets factor_exprBrackets) {
		Code.put(Code.newarray);
		
		if (factor_exprBrackets.getType().struct == Tab.charType) {
			Code.put(0);
		}
		else {
			Code.put(1);
		}
	}
	
	@Override 
	public void visit (Factor_meth factor_meth) {
		Obj des = factor_meth.getDesignator().obj;
		
		switch (des.getName()) {
			case "ord" :
			case "chr" :
				//argument is on stack, function will return casted return value
				break;
				
			case "len" :
				Code.put(Code.arraylength);
				break;
		}
	}
	
	@Override
	public void visit (ArrayBase arrayBase) {
		Designator_index parent = (Designator_index) arrayBase.getParent();
		
		Code.load(parent.getDesignator().obj);
	}
	
	@Override
	public void visit (CondFact_relopExpr condFact_relopExpr) {
		int relopCode = -1;
		
		if (condFact_relopExpr.getRelop() instanceof Relop_eq) {
			relopCode = Code.eq;
		}
		else if (condFact_relopExpr.getRelop() instanceof Relop_neq) {
			relopCode = Code.ne;
		}
		else if (condFact_relopExpr.getRelop() instanceof Relop_gt) {
			relopCode = Code.gt;
		}
		else if (condFact_relopExpr.getRelop() instanceof Relop_gteq) {
			relopCode = Code.ge;
		}
		else if (condFact_relopExpr.getRelop() instanceof Relop_ls) {
			relopCode = Code.lt;
		}
		else if (condFact_relopExpr.getRelop() instanceof Relop_lseq) {
			relopCode = Code.le;
		}
		
		Code.putFalseJump(relopCode, 0);
		int falseAddr = Code.pc - 2;
		
		//true
		Code.loadConst(1);
		
		Code.putJump(0);
		int endAddr = Code.pc - 2;
		
		//false
		Code.fixup(falseAddr);
		
		Code.loadConst(0);
		
		//end
		Code.fixup(endAddr);
		
	}
	
	@Override
	public void visit (CondQuestion condQuestion) {
		Code.loadConst(1);
		
		Code.putFalseJump(Code.eq, 0);
		falseJumps.push(Code.pc - 2);	
	}
	
	@Override
	public void visit (Decider decider) {
		//condition is true, skip false jump
		
		Code.putJump(0);
		endJumps.push(Code.pc - 2);
		
		//falseJumpAddr 
		Code.fixup((int) falseJumps.pop());
	}
	
	@Override
	public void visit (Expr_cond expr_cond) {
		Code.fixup((int) endJumps.pop());
	}
	
	@Override
	public void visit (MulFactor_mul mulFactor_mul) {
		if (mulFactor_mul.getMulop() instanceof Mulop_mul) {
			Code.put(Code.mul);
		}
		else if(mulFactor_mul.getMulop() instanceof Mulop_div) {
			Code.put(Code.div);
		}
		else if (mulFactor_mul.getMulop() instanceof Mulop_mod) {
			Code.put(Code.rem);
		}
	}
	
	@Override
	public void visit (AddTerm_add addTerm_add) {
		if (addTerm_add.getAddop() instanceof Addop_plus) {
			Code.put(Code.add);
		}
		else if (addTerm_add.getAddop() instanceof Addop_minus) {
			Code.put(Code.sub);
		}
	}
	
	//used for term-negation when ExprMinus production is recognized 
	@Override
	public void visit (MinusFlag minusFlag) {
		Code.put(Code.neg);
	}

}
