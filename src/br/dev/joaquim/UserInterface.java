package br.dev.joaquim;

import java.util.Random;
import java.util.Scanner;

import br.dev.joaquim.bank.BankAccount;

public class UserInterface {
    private Scanner input = new Scanner(System.in);
    private BankAccount account;

    private void welcome() {
        System.out.println("Bem-vindo ao sistema bancário");
        System.out.print("Vamos criar usa conta, informe seu nome: ");
        String holderName = input.nextLine();
        int accountNumber = 1000 + (new Random()).nextInt(8999);
        System.out.println("Criamos uma conta com o número: " + accountNumber + ", com saldo igual a 0 (zero).");
        this.account = new BankAccount(accountNumber, 0, holderName);
    }

    private void showMenu() {
        System.out.println("\n\n-----------------------");
        System.out.println("Escolha uma das opções:");
        System.out.println("\t1. Verificar dados da conta.");
        System.out.println("\t2. Depositar.");
        System.out.println("\t3. Sacar.");
        System.out.println("\t4. Sair.");
        System.out.print("opção > ");
    }

    public void start() {
        welcome();
        if (account == null)
            return;

        while (true) {
            showMenu();
            try {
                int choice = readOption();
                switch (choice) {
                    case 1:
                        System.out.println("\n" + this.account);
                        break;
                    case 2:
                        deposit();
                        break;
                    case 3:
                        withdraw(); // pode dar problema
                        break;
                    case 4:
                        System.out.println("Até a próxima.");
                        return;
                    default:
                        System.out.println("Opção inválida");
                        break;
                }
                waitUser();
            } catch (NumberFormatException ex) {
                System.out.println("Valor informado não é um número");
            }
        }
    }

    /**
        * Realiza um deposito no saldo ({@link #balance}) da conta, a partir de um valor informado ao usuário.
        * 
        * Este método solicita ao usuário o valor a ser depositado e verifica se o valor
        * é negativo. Caso seja, uma exceção {@link IllegalArgumentException} será lançada.
        * Caso o valor seja válido, o depósito será realizado na conta.
        *
        * @var value Variável que armazena o valor que o usuário quer depositar.
        * @var balance Variável que armazena o saldo atual da conta.
        *
        * @throws IllegalArgumentException Se o valor a ser depositado for negativo.
    */
    private void deposit() {
        System.out.print("\nInforme o valor a ser depositado: ");
        double value = readValue();

        // Criando um try com catch para gerar a exceção caso o valor a depositar seja negativo
        try{
            if (value<0){
                throw new IllegalArgumentException("Não é possível depositar um valor negativo!");
            }
            else{
                account.deposit(value);
                System.out.println("Desposito realizado com sucesso.");
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }      
    }

    /**
        * Realiza uma retirada no saldo ({@link #balance}) da conta, a partir de um valor informado ao usuário.
        * 
        * Este método solicita ao usuário o valor a ser sacado e verifica se o valor
        * é negativo ou insuficiente. Caso seja negativo, uma exceção {@link IllegalArgumentException} 
        * será lançada, de outra forma, caso seja um valor insuficiente para sacar, uma exceção {@link InsufficientFundsException} será lançada.
        * Caso o valor seja válido, o saque será realizado na conta.
        *
        * @var value Variável que armazena o valor que o usuário quer sacar.
        * @var balance Variável que armazena o saldo atual da conta, utilizado para verificar se o saque pode ser realizado.
        *
        * @throws IllegalArgumentException Se o valor a ser sacado for negativo.
        * @throws InsufficientFundsException Se o valor a ser sacado for maior do que o valor no saldo do usuário.
    */
    private void withdraw() {
        System.out.print("\nInforme o valor a ser sacado: ");

        // Valor a sacar
        double value = readValue();

        // Valor atual do saldo da conta
        double balance = account.getBalance();

        // Criado um try com duas verificações (IF) que caso não atenda a tal requisito, gera uma exceção que acaba não encerrando o programa e gerando uma mensagem
        try{
            if (value<0){
                throw new IllegalArgumentException("Não é possível sacar um valor negativo!");
            }
            // Aqui foi citado uma Exception ESPECÍFICA criada por mim
            else if(value>balance){
                throw new InsufficientFundsException ("\nSaldo atual =  "+ balance + "\nSaldo que você deseja sacar = " + value + "\nVocê não tem saldo disponível");
            }
            else{
            account.withdraw(value); 
            System.out.println("Saque realizado com sucesso");
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        catch(InsufficientFundsException e){
            System.out.println(e.getMessage());
        }
    }

    private int readOption() {
        String choiceString = input.nextLine();
        return Integer.parseInt(choiceString);
    }

    private double readValue() {
        String line = input.nextLine();
        return Double.parseDouble(line);
    }

    private void waitUser() {
        System.out.println("pressione ENTER para continuar...");
        input.nextLine();
    }
}
