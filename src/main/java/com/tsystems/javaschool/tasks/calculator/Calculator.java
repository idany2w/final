package com.tsystems.javaschool.tasks.calculator;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    private final TreeMap<String, Integer> priority = new TreeMap<>();
    {
        priority.put("+", 1);
        priority.put("-", 1);
        priority.put("*", 2);
        priority.put("/", 2);
    }

    private LinkedList<String> getStatements(String statement){
        LinkedList<String> statements = new LinkedList<>();
        Pattern p  = Pattern.compile("\\.\\.|[^\\d\\+\\-\\*\\/\\.()]");
        Matcher m = p.matcher(statement);

        if(m.find()) return null;

        p = Pattern.compile("(\\d+\\.\\d+|\\d+|[()+\\-\\*\\/])");
        m = p.matcher(statement.replace(" ", ""));

        while (m.find()){
            statements.addLast(m.group(1));
        }
        return statements;
    }

    private void calcOperation(LinkedList<String> numbers, LinkedList<String> operations, String operation){
        double num1 = Double.parseDouble(numbers.getLast());
        numbers.removeLast();
        double num2 = Double.parseDouble(numbers.getLast());
        numbers.removeLast();
        switch (operation){
            case "+": num2 += num1;break;
            case "-": num2 -= num1;break;
            case "/": if(num1 != 0) {
                num2 /= num1;
                break;
            }else {
                throw new ArithmeticException();
            }
            case "*": num2 *= num1;break;
        }
        numbers.addLast(String.valueOf(num2));
        operations.removeLast();
    }

    public String evaluate(String statement) {
        try {
            LinkedList<String> statements  = getStatements(statement);
            LinkedList<String> operations = new LinkedList<>();
            LinkedList<String> numbers = new LinkedList<>();

            for (String state: statements) {

                // state is Digit
                if(state.matches("-?\\d+(\\.\\d+)?")){
                    numbers.addLast(state);
                    continue;
                }

                // state is Operation
                if(priority.containsKey(state)){
                    if(operations.isEmpty()){
                        operations.addLast(state);
                        continue;
                    }


                    String prevOperation = operations.getLast();

                    if (prevOperation.equals("(")){
                        operations.addLast(state);
                        continue;
                    }


                    int prevOperationPriority = priority.get(prevOperation);
                    int currOperationPriority = priority.get(state);

                    if (currOperationPriority > prevOperationPriority){
                        operations.addLast(state);
                        continue;
                    }

                    while (true){
                        calcOperation(numbers, operations, prevOperation);

                        if(operations.isEmpty()){
                            operations.addLast(state);
                            break;
                        }
                        prevOperation = operations.getLast();
                        if(prevOperation.equals("(")){
                            operations.addLast(state);
                            break;
                        }
                        prevOperationPriority = priority.get(prevOperation);
                        if (currOperationPriority > prevOperationPriority){
                            operations.addLast(state);
                            break;
                        }
                    }
                    continue;
                }

                if (state.equals("(")){
                    operations.addLast(state);
                    continue;
                }

                if (state.equals(")")){
                    String prevOperation = operations.getLast();

                    while (!prevOperation.equals("(")){
                        calcOperation(numbers, operations, prevOperation);

                        if(operations.isEmpty()) break;
                        prevOperation = operations.getLast();
                    }
                    operations.removeLast();
                }
            }

            while (!operations.isEmpty()){
                String prevOperation = operations.getLast();
                calcOperation(numbers, operations, prevOperation);
            }

            //String result = numbers.getLast().replace("\\.0", ""); //it's don't correct work in CalculatorTest.java
            //return result;

            double a = Double.parseDouble(numbers.getLast());
            int b = (int)a;
            if( a - b == 0){
                return numbers.getLast().replace(".0", "");
            } else {
                return numbers.getLast();
            }
        } catch (Exception e){
            return null;
        }
    }

}
