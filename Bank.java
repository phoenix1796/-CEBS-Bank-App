import java.util.*;
class Bank {
  public static void stdout(String str){
    System.out.print(str);
  }
  Map<Integer,Customer> customers=new HashMap<Integer,Customer>();
  public void addCustomer(){
    Customer cust = new Customer();
    Scanner sc = new Scanner(System.in);
    System.out.println("\nOpening New Account\n----------------------");
    System.out.println("Enter name:");
    cust.name=sc.nextLine();
    System.out.println("\nEnter contact:");
    cust.contact=sc.nextLine();
    System.out.println("\nEnter Account Number:");
    cust.account.no = sc.nextInt();
    sc.nextLine();
    System.out.println("\nEnter Account Type:");
    cust.account.type = sc.nextLine();
    this.customers.put(cust.account.no,cust);
    System.out.println("Added New Customer!\n----------------------");
  }


  //This function acts as an appplicator of transactions for the Bank
  //No other function directly touches the Balance or Transaction entries in an account
  //Except depositMoney and withdrawMoney
  //All transactions are "applied" using the following
  //This ensures uniformity and lessens redundancy considerably
  //Also makes Transaction only mutable through here and thus the error recovery
  //is easier (if needed to be added)
  //Acts somewhat like reducers in REDUX
  //http://redux.js.org/docs/basics/Reducers.html
  public void applyTransaction(Transaction t){
    Customer sender=customers.get(t.senderId);
    Customer receiver=customers.get(t.receiverId);

    //The condition below has been shifted inside the switch module
    //This is done to not have aborted transactions
    //in account transactions list.
    //Here we traded code size for better recoverability
    //Since if the system crashed after the below if..else..
    //Then an aborted transaction might have been added to the accounts

        //Check if either is null , i.e. WITHDRAW or DEPOSIT

    /*    if(sender!=null)
          sender.account.transactions.add(t);
        if(receiver!=null)
          receiver.account.transactions.add(t);
    */
    switch(t.type){
      case "DEBIT"://Transaction Type :DEBIT
                    if(sender.account.balance>=t.amount){
                      sender.account.transactions.add(t);
                      //Switched type , since for the receiver it will be
                      //CREDIT transaction
                      try {
                        Transaction temp = (Transaction)t.clone();
                        temp.type="CREDIT";
                        receiver.account.transactions.add(temp);
                        sender.account.balance -= t.amount;
                        receiver.account.balance += t.amount;
                      }
                      catch(CloneNotSupportedException e){
                        System.out.println(e);
                      }
                    }
                    else{
                      System.out.println("Cannot proceed with transaction , not enough amount");
                    }
                    break;
      case "CREDIT"://Transaction Type :CREDIT
                    if(receiver.account.balance>=t.amount){
                      receiver.account.transactions.add(t);
                      //Switched type , since for the receiver it will be
                      //DEBIT transaction
                      try{
                        Transaction temp =(Transaction)t.clone();
                        temp.type="DEBIT";
                        sender.account.transactions.add(temp);
                        sender.account.balance += t.amount;
                        receiver.account.balance -= t.amount;
                      }
                      catch(CloneNotSupportedException e){
                        System.out.println(e);
                      }
                    }
                    else {
                      System.out.println("Cannot proceed with transaction , not enough amount");
                    }
                    break;
      case "WITHDRAW"://Transaction Type :WITHDRAW , receiver = NULL
                    if(sender.account.balance>=t.amount){
                      sender.account.transactions.add(t);
                      sender.account.balance -= t.amount;
                    }
                    else{
                      System.out.println("Cannot proceed with transaction , not enough amount");
                    }
                    break;
      case "DEPOSIT"://Transaction Type :DEPOSIT , sender = NULL
                    receiver.account.transactions.add(t);
                    receiver.account.balance += t.amount;
                    break;
      default:System.out.println("Transaction aborted! ,Unknown Transaction type");
                    break;
    }
  }
  Transaction createTransaction(){
    Scanner sc = new Scanner(System.in);
    Transaction t = new Transaction();
    stdout("\n-----------------\nEnter Sender Id :");
    t.senderId=sc.nextInt();
    stdout("Enter Receiver Id :");
    t.receiverId=sc.nextInt();
    stdout("Enter Amount to be transferred:");
    t.amount=sc.nextInt();
    t.type="DEBIT";
    return t;
  }
  public static void main(String ...args){
    Scanner sc = new Scanner(System.in);
    int choice;
    Customer cust;
    Bank B = new Bank();
    do {
      stdout("\nWhat do you want to do?\n");
      stdout("1.Open Account\n");
      stdout("2.Deposit Money\n");
      stdout("3.Withdraw Money\n");
      stdout("4.View Statement\n");
      stdout("5.Do a Transaction\n");
      stdout("Your choice:");
      choice=sc.nextInt();
      //Assuming the Account Ids are correct ,
      //No error checks implemented right now ,
      //Can check for "NULL" returned by B.customers.get(XYZ)
      //In future and act accordingly
      switch(choice){
        case 1:B.addCustomer();
                break;
        case 2:stdout("Enter Account Id for depositing money:");
                cust=B.customers.get(sc.nextInt());
                stdout("Enter amount to be deposited:");
                B.applyTransaction(cust.account.depositMoney(sc.nextInt()));
                break;
        case 3:stdout("Enter Account Id for Withdrawing money:");
                cust=B.customers.get(sc.nextInt());
                stdout("Enter amount to be withdrawn:");
                B.applyTransaction(cust.account.withdrawMoney(sc.nextInt()));
                break;
        case 4:stdout("Enter Account Id :");
                B.customers.get(sc.nextInt()).viewStatement();
                break;
        case 5:B.applyTransaction(B.createTransaction());
                break;
        default:stdout("Wrong choice amigos , do you mind trying again ?\n");
      }
      stdout("\n Restart? (0/1)");
    }while(sc.nextInt()==1);
  }
}
