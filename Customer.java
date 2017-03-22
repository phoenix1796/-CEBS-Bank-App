class Customer {
  String name,contact;
  Account account=new Account();

  void viewStatement(){
    System.out.println("Transaction Details:");
    System.out.println("----------------------------------");
    for(Transaction t:account.transactions){
      //Transaction Id NULL right now , since this is for testing
      // and no proper method for assigning Transaction Ids required
      System.out.print("Transaction Id: "+t.id);
      System.out.print("\tReceiver: "+t.receiverId);
      System.out.print("\tSender: "+t.senderId);
      System.out.print("\tType: "+t.type);
      System.out.print("\tAmount: "+t.amount);
      System.out.println("");
    }
    System.out.println("----------------------------------");
    System.out.println("Current Balance:"+account.balance+"\n\n");
  }
}
