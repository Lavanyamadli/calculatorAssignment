package com;
import java.util.Scanner;
public class Calculator {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Welcome to Simple-Calculator");
        System.out.println("Enter a Mathematical expression");
        while(true){
            System.out.print(">");
            String input=scanner.nextLine().trim();
            // this is exit condition
            if(input.equalsIgnoreCase("exit")){
                System.out.println("Exiting the Calculator.");
                break;
            }
            try{
                double result=evaluate(input);
                System.out.println("Output:"+result);
            }
            catch(Exception e){
                System.out.println("Error:Invalid expression. Please try again");
            }
        }
        scanner.close();

    }
    //evaluate simple mathematical expressions
    private static double evaluate(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if(ch==charToEat){
                    nextChar();
                    return true;
                }
                return false;
            }
            double parse(){
                nextChar();
                double x=parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }
            //Expression Parsing (Addition/Subtraction)
            double parseExpression() {
                double x = parseTerm();
                while (true) {
                    if (eat('+')) x += parseTerm();//Addition
                    else if (eat('-')) x -= parseTerm();//Substraction
                    else return x;
                }

            }
            //Expression Parsing(Addition/Subtraction)
            double parseTerm() {

                double x = parseFactor();
                while (true) {
                    if (eat('*')) x *= parseFactor();//Multiplication
                    else if (eat('/')) x /= parseFactor();//Division
                    else return x;
                }
            }
            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                if (eat('(')) {
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing closing parenthesis");
                } else {
                    StringBuilder sb = new StringBuilder();
                    while ((ch >= '0' && ch <= '9') || ch == '.') {
                        sb.append((char) ch);
                        nextChar();
                    }
                    x = Double.parseDouble(sb.toString());
                }

                return x;
            }
        }.parse();
    }
}


