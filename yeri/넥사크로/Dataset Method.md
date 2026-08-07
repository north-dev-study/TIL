## row 추가
```
this.dsName.addRow(); // 가장 마지막에 row 추가

this.dsName.addRow(i); // 원하는 위치에 row 추가
```


## 셀 값 가져오기 및 저장
```
// ds 값 검색 및 입력
trace("   " + this.dsName.getColumn(0,"columnName"));

// i: grd의 경우 몇번째 row 의 데이터인지 위치 설정
this.dsName.setColumn(i,"columnName","set할 값");
```


## 값이 몇개 있는지 확인

- 주로 데이터가 대상 데이터셋에 잘 들어갔는지 확인할 때 사용
```
this.dsName.rowcount
```




