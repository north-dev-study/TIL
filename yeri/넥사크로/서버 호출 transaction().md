```
this.btnSearch_onclick = function(obj, e)
{
    this.transaction(
        "searchStudent",                         // 트랜잭션 ID
        "/backend/student/selectStudentList.do", // 호출 URL
        "dsSearch=dsSearch",                     // 서버로 보낼 Dataset
        "dsStudent=dsStudent",                   // 서버에서 받을 Dataset
        "",
        "fnCallback"                             // 응답 후 실행 함수
    );
};
```


- dsSearch : 검색조건을 서버로 보냄 
- dsStudent : 조회 결과를 서버에서 받아옴

넥사크로의 `transaction()`은 내부적으로 HTTP 요청을 만들어 Tomcat으로 보냄