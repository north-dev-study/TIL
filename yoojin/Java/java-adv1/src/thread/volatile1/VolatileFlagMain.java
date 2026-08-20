package thread.volatile1;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * volatile, 메모리 가시성1
 * 
 * runFlag를 사용해서 스레드의 작업을 종료하려고 한다.
 * main 스레드, work 스레드 모두 MyTask 인스턴스에 있는 runFlag를 사용한다.
 * main 스레드에서는 sleep()을 통해 1초 뒤 runFlag를 false로 설정한다.
 * work 스레드는 run() 메서드를 실행하면서 while(runFlag)를 체크하는데, 
 * 이제 runFlag가 false가 되었으므로 "task 종료"를 출력하고 작업을 종료해야 한다.
 * 하지만 실행결과는 while문에서 빠져나오지 못하고 계속 돈다.
 * 
 * [     main] runFlag = true
 * [     work] task 시작
 * [     main] runFlag를 false로 변경 시도
 * [     main] runFlag = false
 * [     main] main 종료
 */
public class VolatileFlagMain {
	
	public static void main(String[] args) {
		MyTask task = new MyTask();
		Thread t = new Thread(task, "work");
		log("runFlag = " + task.runFlag);
		t.start();
		
		sleep(1000);
		log("runFlag를 false로 변경 시도");
		task.runFlag = false;
		log("runFlag = " + task.runFlag);
		log("main 종료");
	}
	
	static class MyTask implements Runnable {
		boolean runFlag = true;
		
		public void run() {
			log("task 시작");
			while(runFlag) {
				// runFlag가 false로 변하면 탈출
			}
			log("task  종료");
		}
	}
}
