package com.chernenko.ui;

import com.chernenko.database.dao.ExpressionDAO;
import com.chernenko.database.domain.Expression;
import com.chernenko.validator.Validator;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class FrontController {

    private static final String MENU = "1.Input new expression\n" +
            "2.Get all expression\n" +
            "3.Edit expression by id\n" +
            "4.Search expression\n";


    private final ExpressionDAO expressionDAO;
    private final Validator validator;

    public FrontController(ExpressionDAO expressionDAO, Validator validator) {
        this.expressionDAO = expressionDAO;
        this.validator = validator;
    }


    public void printMenu() {
        System.out.println(MENU);

        System.out.println("Select option number:");
        String optionNumber = readString();
        
        switch (optionNumber) {
            case "1":
                inputExpression();
                break; 
            case "2":
                findAll();
                break;
            case "3":
                updateExpression();
                break;
            case "4":
                searchExpression();
        }
    }

    private void inputExpression() {
        System.out.println("Input expression: ");
        String expression = readString();

        if(validator.isExpressionValid(expression)) {
            double result = new ExpressionBuilder(expression).build().evaluate();
            System.out.println("Result: " + result);
            expressionDAO.save(new Expression(expression, result));
        } else {
            System.out.println("Invalid expression argument!");
        }
    }

    private void findAll() {
        expressionDAO.findAll().forEach(e -> {
            System.out.println("id:" + e.getId() + " expr:" + e.getExpression() + " result:" + e.getResult());
        });
    }

    private void updateExpression() {
        findAll();
        System.out.println("Choose expression id:");
        long id = readLong();
        System.out.println("Input new expression");
        String expression = readString();
        double result = new ExpressionBuilder(expression).build().evaluate();
        expressionDAO.update(new Expression(id,expression,result));
    }

    private void searchExpression() {
        System.out.println("Input search parameters:");
        String parameter = readString();
        expressionDAO.findByParameter(parameter).forEach(e -> {
            System.out.println("id:" + e.getId() + " expr:" + e.getExpression() + " result:" + e.getResult());
        });
    }

    private String readString() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        return scanner.nextLine();
    }

    private long readLong() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        return scanner.nextLong();
    }

}
