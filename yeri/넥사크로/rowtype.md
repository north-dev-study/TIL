- Nexacro Dataset 행의 변경 상태

- 사용자가 화면에서 행을 추가·수정·삭제하면 각 행에 상태값이 붙고, 서버의 Service가 이를 보고 SQL을 분기처리 할 수 있음

```
Nexacro Dataset 행
 ├─ 신규(INSERT) → INSERT SQL
 ├─ 수정(UPDATE) → UPDATE SQL
 └─ 삭제(DELETE) → DELETE SQL
```


일반적인 java 예시
```
for (SomeVO vo : dataList) {
    if (vo.getRowType() == RowType.INSERT) {
        dao.insert("SomeQry.insertSomeQ01", vo);
    } else if (vo.getRowType() == RowType.UPDATE) {
        dao.update("SomeQry.updateSomeQ01", vo);
    } else if (vo.getRowType() == RowType.DELETE) {
        dao.delete("SomeQry.deleteSomeQ01", vo);
    }
}
```