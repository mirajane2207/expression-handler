package com.chernenko.validator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;

public class ValidatorImpl implements Validator{

    @Override
    public boolean isExpressionValid(String expression) {

        if(isBracketsValid(expression) && isFormatValid(expression) && isOperandsValid(expression)) {
            return true;
        }

        return false;
    }

    private boolean isFormatValid(String expression) {
        if (expression.matches("^[0-9+-/*()]+$")){
            return true;
        }

        return false;
    }

    private boolean isBracketsValid(String expression) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : expression.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }

        return (stack.isEmpty());
    }

    private static boolean isOperandsValid(String expression) {

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if(c == '+' || c == '-' || c == '*' || c == '/'){
                switch (expression.charAt(i+1)) {
                    case '*':
                    case '/':
                        return false;

                }
            }
        }

        return true;
    }
}
