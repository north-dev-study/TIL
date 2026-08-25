package thread.sync;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BankMain {
	
	public static void main(String[] args) throws InterruptedException {
		BankAccount account = new BankAccountV1(1000);
		
		Thread t1 = new Thread(new WithdrawTask(account, 800), "t1");
		t1.start();
		t1.join();
		
		log("최종 잔액: " + account.getBalance());
	}
}
