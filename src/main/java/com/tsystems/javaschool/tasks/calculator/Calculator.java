package com.tsystems.javaschool.tasks.calculator;



import java.util.*;

public class Calculator {


    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {   //алгоритм находит обратную польскую нотацию и отправляет ее на вычисление

        if (!strCheck(statement)) {      //проверяем сразу строку
            return null;
        }
        StringBuilder string = new StringBuilder(statement);
        ArrayDeque<String> que = new ArrayDeque<>();    //output que
        Stack<String> st = new Stack<>();               //operator stack

        for(int i = 0; i < string.length(); i ++) {
                List<String> precedence = Arrays.asList("-", "+", "","*", "/");   // лист приоритетов
                String token = String.valueOf(string.charAt(i));

                if(token.equals("/")|| token.equals("-")|| token.equals("+")|| token.equals("*")) {
                    while (!st.empty() && (precedence.indexOf(token) - precedence.indexOf(st.peek()) <= 1) && !st.peek().equals("(")) {
                        // делаем перенос стека в очередь если оператор выше по приоритету либо равен
                        que.add(st.pop());
                    }
                    st.push(token);   // отправлеяем новый оператор в стек
                }
                else if(token.equals("(")){
                    st.push(token);
                }
                else if(token.equals(")")) {
                    while(!st.peek().equals("(")) {
                        que.add(st.pop());      // как только встретили закрывающую скобку отправляем все что было до после открывающей
                    }
                    st.pop();
                }
                else
                {
                    if(i > 0 && (Character.isDigit(string.charAt(i-1)) || string.charAt(i-1) == '.')) {
                        // особеность: добавляем в очередь число(не цифру) либо число с точкой
                        que.add(que.removeLast() + token);
                    }else{
                        que.add(token);
                    }


                }

            }
        while(!st.isEmpty()&&(st.peek().equals("/")||st.peek().equals("*")||st.peek().equals("+")||st.peek().equals("-")))
            // отправляем в очередь остаток стека
            que.add(st.pop());

        double eval = evalRPN(que);
        if(eval == (int)eval){
            return String.valueOf((int)eval);
        } else{
            return String.valueOf(eval);
        }
    }

    public static double evalRPN(ArrayDeque<String> tokens) {    // функция для подсчета нотации

        Double returnValue = 0.0;

        String operators = "+-*/";

        Stack<String> stack = new Stack();

        for(String t : tokens){
            if(!operators.contains(t)){
                stack.push(t);
            }else{
                double a = Double.valueOf(stack.pop());
                double b = Double.valueOf(stack.pop());
                int index = operators.indexOf(t);
                switch(index){
                    case 0:
                        stack.push(String.valueOf(a+b));
                        break;
                    case 1:
                        stack.push(String.valueOf(b-a));
                        break;
                    case 2:
                        stack.push(String.valueOf(a*b));
                        break;
                    case 3:
                        stack.push(String.valueOf(b/a));
                        break;
                }
            }
        }

        returnValue = Double.valueOf(stack.pop());

        return returnValue;

    }
    public static boolean strCheck(String str) {     //банальная посимольная проверка
        int open = 0;
        int close = 0;
        String operators = "+-*/.";

        if(str == null || str.isEmpty() ||  operators.contains(String.valueOf(str.charAt(0))) ||   //блок если  пустая строка или ноль или начинается с опертора
                operators.contains(String.valueOf(str.charAt(str.length()-1)))){
            return false;
        }

        for(int i = 1; i < str.length()-1; i ++) {
            char token = str.charAt(i);
            if(operators.contains(String.valueOf(token)) && operators.contains(String.valueOf(str.charAt(i-1)))){ //блок если операторы пошли подряд
                return false;
            }
            if(!Character.isDigit(token) && !operators.contains(String.valueOf(token)) && !(token == '(') && !(token == ')')) {
                return false;
            }
            if(token == '(') {
                open++;
            }
            if(token == ')') {
                close++;
            }
        }

        if(open != close){
            return false;
        }
        return true;
    }

}
