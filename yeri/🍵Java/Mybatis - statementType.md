> JDBC가 SQL을 실행할 때 사용하는 방식을 지정하는 속성


 
- 프로시저 호출시 : statementType - CALLAVLE
- 일반 쿼리 실행시 : statementType - PREPARED


### **`PREPARED`** (디폴트)
- JDBC `PreparedStatement` 사용
- ? 기호로 파라미터를 바인딩
- 사전 컴파일된 SQL을 실행하며 매개변수를 전달할 수 있음
- 반복 실행 시 성능이 뛰어나고 SQL 인젝션을 예방
- 일반적인 SELECT, INSERT, UPDATE, DELETE와 같은 동적 쿼리에 적합

### `STATEMENT`
- JDBC `Statement` 사용
- 파라미터 바인딩 없이 문자열 그대로 쿼리를 실행할 때 사용
- 일반적인 SQL 쿼리를 실행하며, 매개변수를 전달할 수 없음
- 실행 시마다 컴파일되므로 성능이 떨어질 수 있지만, DDL 문(create, alter, drop 등)에서 주로 사용됨

### **`CALLABLE`**
- JDBC `CallableStatement` 사용
- -. 저장 프로시저를 호출하는 데 사용된다. 프로시저의 입력 매개변수와 출력 값을 설정할 수 있으며, 미리 컴파일되어 있어 성능상 이점이 있다. Oracle이나 SQL Server에서 저장 프로시저를 활용할 때 적합하다.


