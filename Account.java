import java.util.*;
class Account {
  int no;
  String type;
  int balance;
  ArrayList<Transaction> transactions=new ArrayList<Transaction>();
  //These function can also be put into the
  //Customer class , but made more sense to me
  //to put them here , since it wasn't specified
  public Transaction depositMoney(int money){
    Transaction t = new Transaction();
    t.senderId=null;
    t.receiverId=this.no;
    t.amount=money;
    t.type="DEPOSIT";
    return t;
  }
  public Transaction withdrawMoney(int money){
    Transaction t = new Transaction();
    t.senderId=this.no;
    t.receiverId=null;
    t.amount=money;
    t.type="WITHDRAW";
    return t;
  }
}
