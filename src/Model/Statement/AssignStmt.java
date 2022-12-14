package Model.Statement;

import Model.ADT.MyDictionaryInterface;
import Model.ADT.MyHeapInterface;
import Model.Expression.Expression;
import Model.ProgramState;
import Exception.MyException;
import Model.Type.Type;
import Model.Value.Value;

public class AssignStmt implements IStmt{
    String id;
    Expression expression;

    public AssignStmt(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    public String toString(){
        return id + "=" + expression.toString();
    }

    public ProgramState execute(ProgramState state) throws MyException{
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyHeapInterface<Integer, Value> heap = state.getHeap();

        if (!(symbolTable.isDefined(id))) {
            throw new MyException("undefined variable");
        }
        Value value = expression.evaluate(symbolTable, heap);
        Type typeId = (symbolTable.lookup(id)).getType();
        if (!(value.getType().equals(typeId))){
            throw new MyException("type does not match");
        }
        symbolTable.update(id,value);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id.toString(), expression.deepCopy());
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnv)
    throws MyException {
        Type typeVar = typeEnv.lookup(id);
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeVar.equals(typeExp)) {
            return typeEnv;
        } else {
            throw new MyException("Assignment: right hand side and left hand side have different types");
        }
    }
}
