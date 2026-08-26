package thread.sync;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * [       t2] 거래 시작: BankAccountV1
 * [       t1] 거래 시작: BankAccountV1
 * [       t2] [검증 시작] 출금액: 800, 잔액: 1000
 * [       t1] [검증 시작] 출금액: 800, 잔액: 1000
 * [       t2] [검증 완료] 출금액: 800, 잔액: 1000
 * [       t1] [검증 완료] 출금액: 800, 잔액: 1000
 * [     main] t1 state: TIMED_WAITING
 * [     main] t2 state: TIMED_WAITING
 * [       t2] [출금 완료] 출금액: 800, 잔액: -600
 * [       t1] [출금 완료] 출금액: 800, 잔액: -600
 * [       t1] 거래 종료
 * [       t2] 거래 종료
 * [     main] 최종 잔액: -600
 * 
 * > 동시성 문제 발생
 * why? 여러 스레드가 함께 사용하는 공유 자원을 여러 단계로 나누어 사용하기 때문
 * 
 * 1. 검증 단계 : 잔액이 출금액보다 많은지 확인한다.
 * 2. 출금 단계 : 잔액을 출금액만큼 줄인다.
 * 
 * 이 로직에는 하나의 큰 가정이 있다
 * : 스레드 하나의 관점에서 출금을 보면 검증 단계에서 확인한 잔액 1000원은 출금 단계에서
 *   계산을 끝마칠 때까지 같은 1000원으로 유지되어야 한다. 
 * 
 * 잔액은 여러 스레드가 함께 사용하는 공유자원이다. 따라서 출금 로직을 수행하는 중간에 
 * 다른 스레드에서 이 값을 얼마든지 변경할 수 있다. 
 * 
 * 한 번에 하나의 스레드만 실행한다면 ? 중간에 다른 스레드가 잔액을 변경하지 않아 안전하게 수행 가능
 * => 검증과 계산 두 단계는 한 번에 하나의 스레드만 실행해야 한다.
 * 
 * 임계 영역 (critical section)
 *  - 여러 스레드가 동시에 접근하면 데이터 불일치나 예상치 못한 동작이 발생할 수 있는 위험하고 중요한 코드 부분을 의미
 *  - 여러 스레드가 동시에 접근해서는 안되는 공유 자원을 접근하거나 수정하는 부분을 의미
 */
public class BankMain {
	
	public static void main(String[] args) throws InterruptedException {
		BankAccount account = new BankAccountV1(1000);
		
		Thread t1 = new Thread(new WithdrawTask(account, 800), "t1");
		Thread t2 = new Thread(new WithdrawTask(account, 800), "t2");
		
		t1.start();
		t2.start();
		
		sleep(500);
		log("t1 state: " + t1.getState());
		log("t2 state: " + t2.getState());
		
		t1.join();
		t2.join();
		log("최종 잔액: " + account.getBalance());
	}
}
