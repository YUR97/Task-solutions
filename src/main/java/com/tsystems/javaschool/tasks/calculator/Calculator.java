package com.tsystems.javaschool.tasks.calculator;

import java.util.ArrayList;
import java.util.Stack;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */

    public String evaluate(String statement) {
        // TODO: Implement the logic here
        if(statementIsCorrect(statement)){
            Stack<String> stack = parseToStack(statement);
            String result = calculateReversePolishRecord(reversePN(stack));
            if(Double.isInfinite(Double.parseDouble(result))){
                return null;
            }
            else {
                int resInt = (int) Double.parseDouble(result);
                double resDouble = Double.parseDouble(result);
                if(resInt == resDouble){
                    Integer res = resInt;
                    return res.toString();
                }
                else{
                    return result;
                }
            }
        }
        else {
            return null;
        }
    }
    public static boolean statementIsCorrect(String statement){
        if(statement == null|statement == ""){
            return false;
        }
        else{
            int bracketPairsTester = 0;
            Character character[] = new Character[statement.length()];
            for(int i = 0; i < statement.length(); i++){
                character[i] = statement.charAt(i);
                if(isOpenBracket(character[i])){
                    bracketPairsTester++;
                }
                else if(isCloseBracket(character[i])) {
                    bracketPairsTester--;
                    if(bracketPairsTester < 0){
                        return false;
                    }
                }
                else if(character[i] == ','){
                    return false;
                }
                else{
                    if(i == 0){
                        if(!(character[i] == '+'|character[i] == '-'|isDigit(character[i])|isOpenBracket(character[i]))){
                            return false;
                        }
                    }
                    else{
                        if((!isDigit(character[i])) && (character[i] == character[i-1])){
                            return false;
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            if(bracketPairsTester == 0){
                if(false){
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                return false;
            }
        }
    }
    public static boolean isDigit(char charOfStatement){
        return charOfStatement == '0'|charOfStatement == '1'|charOfStatement == '2'|charOfStatement == '3'|
                charOfStatement == '4'|charOfStatement == '5'|charOfStatement == '6'|charOfStatement == '7'|
                charOfStatement == '8'|charOfStatement == '9';
    }
    public static boolean isOperation(char charOfStatement) {
        return charOfStatement == '+' | charOfStatement == '-' | charOfStatement == '*' | charOfStatement == '/';
    }
    public static boolean isOpenBracket(char charOfStatement) {
        return charOfStatement == '(';
    }
    public static boolean isCloseBracket(char charOfStatement) {
        return charOfStatement == ')';
    }
    public static boolean isPoint(char charOfStatement) {
        return charOfStatement == '.';
    }
    public static boolean isBracket(char charOfStatement) {
        return isOpenBracket(charOfStatement)|isCloseBracket(charOfStatement);
    }
    public static Stack<String> parseToStack(String statement){
        Stack<String> stack = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < statement.length(); i++){
            if(i == 0){
                stringBuilder.append(statement.charAt(i));
            }
            else{
                if(i == 1){
                    if(statement.charAt(i-1) == '-'){
                        if(isDigit(statement.charAt(i))){
                            stringBuilder.append(statement.charAt(i));
                        }
                        else {
                            stack.push(stringBuilder.toString());
                            stringBuilder.delete(0,stringBuilder.length());
                            stringBuilder.append(statement.charAt(i));
                        }
                    }
                    else if(statement.charAt(i-1) == '('){
                        stack.push(stringBuilder.toString());
                        stringBuilder.delete(0,stringBuilder.length());
                        stringBuilder.append(statement.charAt(i));
                    }
                    else if(isDigit(statement.charAt(i-1))){
                        if(isDigit(statement.charAt(i))|isPoint(statement.charAt(i))){
                            stringBuilder.append(statement.charAt(i));
                        }
                        else {
                            stack.push(stringBuilder.toString());
                            stringBuilder.delete(0,stringBuilder.length());
                            stringBuilder.append(statement.charAt(i));
                        }
                    }
                }
                if(i > 1){
                    if(isDigit(statement.charAt(i))){
                        if(isDigit(statement.charAt(i-1))|isPoint(statement.charAt(i-1))|(statement.charAt(i-1) == '-' && statement.charAt(i-2) == '(')){
                            stringBuilder.append(statement.charAt(i));
                        }
                        else{
                            stack.push(stringBuilder.toString());
                            stringBuilder.delete(0,stringBuilder.length());
                            stringBuilder.append(statement.charAt(i));
                        }
                    }
                    else if(statement.charAt(i) == '-'){
                        stack.push(stringBuilder.toString());
                        stringBuilder.delete(0,stringBuilder.length());
                        stringBuilder.append(statement.charAt(i));
                    }
                    else if(statement.charAt(i) == '+'){
                        stack.push(stringBuilder.toString());
                        stringBuilder.delete(0,stringBuilder.length());
                        stringBuilder.append(statement.charAt(i));
                    }
                    else if(statement.charAt(i) == '*'|statement.charAt(i) == '/'){
                        stack.push(stringBuilder.toString());
                        stringBuilder.delete(0,stringBuilder.length());
                        stringBuilder.append(statement.charAt(i));
                    }
                    else if(isPoint(statement.charAt(i))){
                        stringBuilder.append(statement.charAt(i));
                    }
                    else if(isBracket(statement.charAt(i))){
                        stack.push(stringBuilder.toString());
                        stringBuilder.delete(0,stringBuilder.length());
                        stringBuilder.append(statement.charAt(i));
                    }
                }
            }
            if(i == statement.length()-1){
                stack.push(stringBuilder.toString());
                stringBuilder.delete(0,stringBuilder.length());
            }
        }
        return stack;
    }
    public static ArrayList<String> reversePN(Stack<String> stack){
        Stack <String> input = new Stack<>();
        ArrayList<String> output = new ArrayList<>();
        Stack<String> operation = new Stack<>();
        int stackSize = stack.size();
        for(int i = 0; i < stackSize; i++){
            input.push(stack.pop());
        }
        int stackPriority = 0;
        int currentPriority = 0;
        int bracketPriority = 1;
        int plusMinusPriority = 2;
        int mulDivPriority = 3;
        for(int i = 0; i < stackSize; i++){
            if(!input.isEmpty()){
                boolean elementIsDigit = input.peek().contains("0")|input.peek().contains("1")|
                        input.peek().contains("2")| input.peek().contains("3")|
                        input.peek().contains("4")|input.peek().contains("5")|
                        input.peek().contains("6")|input.peek().contains("7")|
                        input.peek().contains("8")|input.peek().contains("9");
                if(elementIsDigit){
                    output.add(input.pop());
                }
                else {
                    if(input.peek().contains("-")|input.peek().contains("+")){
                        currentPriority = plusMinusPriority;
                    }
                    else if(input.peek().contains("*")|input.peek().contains("/")){
                        currentPriority = mulDivPriority;
                    }
                    else if(input.peek().contains("(")){
                        operation.add(input.pop());
                        currentPriority = bracketPriority;
                        stackPriority = currentPriority;
                        continue;
                    }
                    else if(input.peek().contains(")")){
                        input.pop();
                        do{
                            output.add(operation.pop());
                        }
                        while (operation.contains("("));
                        output.remove(output.size()-1);
                    }
                    if(stackPriority < currentPriority){
                        operation.add(input.pop());
                        stackPriority = currentPriority;
                    }
                    else{
                        if(!operation.isEmpty()){
                            output.add(operation.pop());
                        }
                        if(!input.isEmpty()){
                            operation.add(input.pop());
                            stackPriority = currentPriority;
                        }
                    }
                }
            }
            if(i == stackSize-1 && (!operation.isEmpty())){
                do{
                    output.add(operation.pop());
                }
                while (!operation.isEmpty());
            }
        }
        return output;
    }
    public static String calculateReversePolishRecord(ArrayList<String> input){
        Stack <Double> result = new Stack<>();
        for (String s: input) {
            boolean elementIsDigit = s.contains("0")|s.contains("1")|s.contains("2")|
                    s.contains("3")|s.contains("4")|s.contains("5")|s.contains("6")|
                    s.contains("7")|s.contains("8")|s.contains("9");
            if(elementIsDigit){
                result.push(Double.parseDouble(s));
            }
            else{
                if(s.contains("+")){
                    result.push(result.pop()+result.pop());
                }
                else if(s.contains("-")){
                    Double from,other;
                    other = result.pop();
                    from = result.pop();
                    result.push(from-other);
                }
                else if(s.contains("*")){
                    result.push(result.pop()*result.pop());
                }
                else if(s.contains("/")){
                    Double numerator,denominator;
                    denominator = result.pop();
                    numerator = result.pop();
                    result.push(numerator/denominator);
                }
            }
        }
        return result.peek().toString();
    }
}
