/*
Brief description of the problem:
Evaluate a mathematical expression in infix notation with min and max functions using the Shunting Yard algorithm.
The implementation must include a Stack ADT and may use a Queue ADT to process the postfix (Reverse Polish) notation.
*/

import java.util.Scanner;
 
public class Main {
    public static void main(String[] args) {
        String outputQueue = "";
        Stack<String> stack = new ArrayStack<>();
        Scanner scanner = new Scanner(System.in);
        String inputBuffer = scanner.nextLine();
        String[] arr = inputBuffer.split(" ");
        int i = 0;
        int number = 0;
        while (i < arr.length) {
            if (arr[i].matches("\\d+")) {
                number = Integer.parseInt(arr[i]);
                arr[i] = "N";
            }
            switch (arr[i]) {
                case "N":
                    outputQueue += Integer.toString(number);
                    outputQueue += " ";
                    break;
                case "max":
                case "min", "(":
                    stack.push(arr[i]);
                    break;
                case "*":
                case "/":
                case "+":
                case "-":
                    if (stack.isNotEmpty()) {
                        while (getPrecedence(stack.peek()) >= getPrecedence(arr[i]) && !stack.peek().equals("(")) {
                            outputQueue += stack.peek();
                            outputQueue += " ";
                            stack.pop();
                            if (stack.size() == 0) {
                                break;
                            }
                        }
                    }
                    stack.push(arr[i]);
                    break;
                case ",":
                    if (stack.isNotEmpty()) {
                        while (!stack.peek().equals("(")) {
                            outputQueue += stack.peek();
                            outputQueue += " ";
                            stack.pop();
                            if (stack.size() == 0) {
                                break;
                            }
                        }
                    }
                    break;
                case ")":
                    if (stack.isNotEmpty()) {
                        while (!stack.peek().equals("(")) {
                            if (stack.isNotEmpty()) {
                                outputQueue += stack.peek();
                                outputQueue += " ";
                                stack.pop();
                            }
                        }
                        if (stack.peek().equals("(")) {
                            stack.pop();
                        }
                        if (stack.peek().equals("max") || stack.peek().equals("min")) {
                            outputQueue += stack.peek();
                            outputQueue += " ";
                            stack.pop();
                        }
                        if (stack.size() == 0) {
                            break;
                        }
                    }
                    break;
            }
            i += 1;
        }
        while (stack.isNotEmpty()) {
            if (stack.peek() != "(") {
                outputQueue += stack.peek();
                outputQueue += " ";
                stack.pop();
            }
        }
        String[] arrOutput = outputQueue.split(" ");
        int ind = 0;
        Stack<String> evaluationStack = new ArrayStack<>();
        while (ind < arrOutput.length) {
            if (arrOutput[ind].matches("\\d+")) {
                evaluationStack.push(arrOutput[ind]);
            } else {
                int number2 = Integer.parseInt(evaluationStack.peek());
                evaluationStack.pop();
                int number1 = Integer.parseInt(evaluationStack.peek());
                evaluationStack.pop();
                int result = 0;
                switch (arrOutput[ind]) {
                    case "+":
                        result += number1 + number2;
                        break;
                    case "-":
                        result += number1 - number2;
                        break;
                    case "/":
                        result += number1 / number2;
                        break;
                    case "*":
                        result += number1 * number2;
                        break;
                    case "min":
                        if (number1 > number2) {
                            result += number2;
                        } else {
                            result += number1;
                        }
                        break;
                    case "max":
                        if (number1 > number2) {
                            result += number1;
                        } else {
                            result += number2;
                        }
                        break;
                }
                evaluationStack.push(String.valueOf(result));
            }
            ind += 1;
        }
        System.out.println(evaluationStack.peek());
    }
 
    private static int getPrecedence(String str1) {
        int precedence = switch (str1) {
            case "max", "min" -> 3;
            case "*", "/" -> 2;
            case "+", "-" -> 1;
            default -> 0;
        };
        return precedence;
    }
}
 
interface Stack<T> {
    void push(T item);
    T pop();
    T peek();
    int size();
    boolean isNotEmpty();
}
 
class ArrayStack<T> implements Stack<T> {
    final int INITIAL_SIZE = 123;
    T[] items;
    int stackSize;
 
    public ArrayStack() {
        this.items = (T[]) new Object[INITIAL_SIZE];
        this.stackSize = 0;
    }
 
    @Override
    public int size() {
        return this.stackSize;
    }
 
    @Override
    public boolean isNotEmpty() {
        return (this.stackSize != 0);
    }
 
    @Override
    public void push(T item) {
        if (this.stackSize >= this.items.length) {
            T[] newItems = (T[]) new Object[this.items.length * 2];
            System.arraycopy(this.items, 0, newItems, 0, this.items.length);
            this.items = newItems;
        }
        this.items[this.stackSize] = item;
        this.stackSize++;
    }
 
    @Override
    public T pop() {
        if (this.stackSize <= 0) {
            throw new RuntimeException("Cannot pop() from an empty stack");
        }
        this.stackSize--;
        T item = this.items[this.stackSize];
        this.items[this.stackSize] = null;
        return item;
    }
 
    @Override
    public T peek() {
        if (this.stackSize <= 0) {
            return (T) "n";
        }
        return this.items[this.stackSize - 1];
    }
}
