package View;

import Controller.Controller;
import Model.ADT.MyDictionary;
import Model.ADT.MyList;
import Model.ADT.MyStack;
import Model.Command.ExitCommand;
import Model.Command.RunExample;
import Model.Expression.ArithmeticExpression;
import Model.Expression.ValueExpression;
import Model.Expression.VariableExpression;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.Repository;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args){
        /*
         * Example1:
         *  int v; v=2;Print(v)  is represented as:
         * IStmt ex1= new CompoundStmt(new VariableDeclarationStmt("v",new IntType()),
         * new CompoundStmt(new AssignStmt("v",new ValueExpression(new IntValue(2))), new PrintStmt(new VariableExpression("v"))));
         * */
        IStmt ex1 = new CompoundStmt(new VariableDeclarationStmt("v", new IntType()),
                new CompoundStmt(new AssignStmt("v", new ValueExpression(new IntValue(2))), new PrintStmt(new VariableExpression("v"))));
        ProgramState State1 = new ProgramState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),new MyDictionary<StringValue, BufferedReader>(), new MyList<Value>(), ex1);
        Repository Repo1 = new Repository(State1, "log1.txt");
        Controller Controller1 = new Controller(Repo1);
        /*
         * Example2:
         * int a;int b; a=2+3*5;b=a+1;Print(b) is represented as:
         * IStmt ex2 = new CompoundStmt( new VariableDeclarationStmt("a",new IntType()), new CompoundStmt(new VariableDeclarationStmt("b",new IntType()),
         * new CompoundStmt(new AssignStmt("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),
         * new ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
         * new CompoundStmt(new AssignStmt("b",new ArithmeticExpression('+',new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
         * new PrintStmt(new VariableExpression("b"))))) ;
         * */

        IStmt ex2 = new CompoundStmt( new VariableDeclarationStmt("a",new IntType()), new CompoundStmt(new VariableDeclarationStmt("b",new IntType()),
                new CompoundStmt(new AssignStmt("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),
                new ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                new CompoundStmt(new AssignStmt("b",new ArithmeticExpression('+',new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                new PrintStmt(new VariableExpression("b"))))));
        ProgramState State2 = new ProgramState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),new MyDictionary<StringValue, BufferedReader>(), new MyList<Value>(), ex2);
        Repository Repo2 = new Repository(State2, "log2.txt");
        Controller Controller2 = new Controller(Repo2);
        /*
         * Example3:
         * bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)   is represented as:
         * Stmt ex3 = new CompoundStmt(new VariableDeclarationStmt("a",new BoolType()),
         * new CompoundStmt(new VariableDeclarationStmt("v", new IntType()),new CompoundStmt(new AssignStmt("a", new ValueExpression(new BoolValue(true))),
         * new CompoundStmt(new IfStmt(new VariableExpression("a"),new AssignStmt("v",new ValueExpression(new IntValue(2))),
         * new AssignStmt("v", new ValueExpression(new IntValue(3)))), new PrintStmt(new VariableExpression("v")))))));
         * */

        IStmt ex3 = new CompoundStmt(new VariableDeclarationStmt("a",new BoolType()),
                new CompoundStmt(new VariableDeclarationStmt("v", new IntType()),new CompoundStmt(new AssignStmt("a", new ValueExpression(new BoolValue(true))),
                new CompoundStmt(new IfStmt(new VariableExpression("a"),new AssignStmt("v",new ValueExpression(new IntValue(2))),
                new AssignStmt("v", new ValueExpression(new IntValue(3)))), new PrintStmt(new VariableExpression("v"))))));
        ProgramState State3 = new ProgramState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),new MyDictionary<StringValue, BufferedReader>(), new MyList<Value>(), ex3);
        Repository Repo3 = new Repository(State3, "log3.txt");
        Controller Controller3 = new Controller(Repo3);
        /*
        Example 4:
        string varf;
        varf="test.in";
        openRFile(varf);
        int varc;
        readFile(varf,varc);
        print(varc);
        readFile(varf,varc);
        print(varc);
        closeRFile(varf);
         */

        IStmt ex4 = new CompoundStmt(new VariableDeclarationStmt("varf", new StringType()),
                new CompoundStmt(new AssignStmt("varf", new ValueExpression(new StringValue("test.in"))),
                new CompoundStmt(new OpenReadFileStmt(new VariableExpression("varf")),
                new CompoundStmt(new VariableDeclarationStmt("varc", new IntType()),
                new CompoundStmt(new ReadFileStmt(new VariableExpression("varf"), new StringValue("varc")),
                new CompoundStmt(new PrintStmt(new VariableExpression("varc")),
                new CompoundStmt(new ReadFileStmt(new VariableExpression("varf"), new StringValue("varc")),
                new CompoundStmt(new PrintStmt(new VariableExpression("varc")),
                new CloseReadFileStmt(new VariableExpression("varf"))))))))));
        ProgramState State4 = new ProgramState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),new MyDictionary<StringValue, BufferedReader>(), new MyList<Value>(), ex4);
        Repository Repo4 = new Repository(State4, "log4.txt");
        Controller Controller4 = new Controller(Repo4);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",ex1.toString(),Controller1));
        menu.addCommand(new RunExample("2",ex2.toString(),Controller2));
        menu.addCommand(new RunExample("3",ex3.toString(),Controller3));
        menu.addCommand(new RunExample("4",ex4.toString(),Controller4));
        menu.show();
    }
}
