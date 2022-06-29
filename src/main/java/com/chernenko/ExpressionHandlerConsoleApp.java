package com.chernenko;

import com.chernenko.database.DBConnector;
import com.chernenko.database.dao.ExpressionDAO;
import com.chernenko.database.dao.ExpressionDAOImpl;
import com.chernenko.ui.FrontController;
import com.chernenko.validator.Validator;
import com.chernenko.validator.ValidatorImpl;
import javax.script.ScriptException;

public class ExpressionHandlerConsoleApp {
    public static void main(String[] args) throws ScriptException {

        DBConnector connector = new DBConnector("database");
        ExpressionDAO expressionDAO = new ExpressionDAOImpl(connector);
        Validator validator = new ValidatorImpl();
        FrontController frontController = new FrontController(expressionDAO,validator);

        while(true){
            frontController.printMenu();
        }
    }
}
