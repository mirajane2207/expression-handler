package com.chernenko.database.domain;

import java.util.Objects;

public class Expression {

    private Long id;
    private String expression;
    private double result;

    public Long getId() {
        return id;
    }

    public Expression(Long id, String expression, double result) {
        this.id = id;
        this.expression = expression;
        this.result = result;
    }

    public Expression(String expression, double result) {
        this.expression = expression;
        this.result = result;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expression that = (Expression) o;
        return result == that.result && Objects.equals(id, that.id) && Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expression, result);
    }
}
