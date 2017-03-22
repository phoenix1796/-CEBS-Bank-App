class Transaction implements Cloneable{
  //Make it cloneable so that transactions can have different
  //types in different accounts
  //Since for one account it maybe debit ,
  //But for other it maybe credit at the same time
  //refer to Bank :: applyTransaction for more details


  //Transaction id not set right now ,
  //Used Integers instead of int ,
  //To make these ids comparable to null
  public Object clone()throws CloneNotSupportedException{
    return super.clone();
    }
  Integer id,senderId,receiverId;
  String type;
  int amount;
}
