- 논리적으로 절대 쪼개질 수 없는 하나 이상의 데이터베이스 작업 묶음을 의미한다.

### ACID 원칙
- Atomicity (원자성)
	- All or Nothing, 더 이상 쪼갤 수 없는 논리적 단위, 트랙잭션 안에 있는 작업을 분리할 수 없다.
- Consistency (일관성)
	- 항상 일관된 상태를 유지해야 한다.
- Isolation (격리성)
	- 하나의 트랜잭션이 실행 중일때 다른 트랜잭션이 중간 결과에 끼어들 수 없다.
- Durability (지속성)
	- Commit 된 결과는 영구적으로 보존된다. (장애가 발생되어도)
### 동시성 문제
1. 더티 리드 (Dirty-Read)
	1. 아직 Commit 하지 않은, 수정 중인 데이터를 다른 트랜잭션이 읽는 것
2. 반복 불가능 읽기 (Non-Repeatable Read)
	1. 한 트랜잭션 내에서 똑같은 Select 쿼리를 두 번 실행했는데 서로 결과가 다를 경우
3. 유령 읽기 (Phatom Read)
	1. 특정 범위의 데이터를 두 번 읽었는데 첫 번째 조회에서는 없었던 데이터가 나타나는 현상


|                  | Dirty-Read | Non-Repeatable Read | Phatom Read |
| ---------------- | ---------- | ------------------- | ----------- |
| Read Uncommitted | O          | O                   | O           |
| Read Committed   | X          | O                   | O           |
| Repeatable Read  | X          | X                   | O,X         |
| Serializable     | X          | X                   | X           |

### 4가지 격리 수준
1. Read Uncommitted
	1. 아무것도 막을 수 없다, 정합성 이슈가 많아서 사용되지 않는다.
	2. Read Committed 와 성능차이가 거의 나지 않는다 (0~3%)
	3. 커밋되지 않은, 즉 언제든 사라질 수 있는 더러운 데이터를 허용하기 때문에 데이터 정합성에 심각한 문제를 일으킬 수 있다
	4. 예외로 대용량 데이터에 대한 실시간 집계, 통계 작업 같은 곳에 사용할 수 있다. (경향성을 파악하기 위한 정도라면)
2. Read Committed
	1. Commit 된 데이터만 읽을 수 있다. 대부분의 DB의 기본 격리 수준이다. 더티 리드를 방지한다.
	2. 성능, 동시성 확보가 중요하고 약간의 데이터 비 일관성을 감수할 수 있는 상황에 쓰인다.
	3. 사실 Non-Repeatable Read, Phantom Read 가 발생될 상황이 거의 없다. 대부분의 트랜잭션에선 조회를 두 번 하고 그러진 않는다.
3. Repeatable Read 
	1. Non-Repeatable Read를 방지한다.
	2. MySQL 의 InnoDB의 기본 격리 수준이다.
4. Serializable
	1. 가장 엄격하게 데이터 정합성을 보장한다.
	2. 동시성 문제가 심각해서 (성능이 떨어짐) 거의 안 쓰인다.
	3. Select ... For Update 같은 비관적 락 (Pessimistic Lock), 버전번호를 두는 낙관적 락 (Optimistic Lock)을 구현하는게 낫다.