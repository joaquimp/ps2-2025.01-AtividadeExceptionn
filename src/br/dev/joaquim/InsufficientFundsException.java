package br.dev.joaquim;

// Toda exception específica herda da classe mãe (Exception) por isso o uso do extends
public class InsufficientFundsException extends Exception{
    // Construtor que herda o atributo message
    public InsufficientFundsException(String message){
        super(message);
    }
}
