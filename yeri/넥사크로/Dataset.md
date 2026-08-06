- 데이터를 다루는 객체 개념
- DB 자원에 트래픽이 몰려 생기는 오버헤드를 줄이는 것
-> 테이블 전체의 데이터를 넥사크로 특정 데이터셋에 담아두고 목적에 맞게 가공해 사용 == a.k.a. 비연결방식

- 그리드(테이블), 콤보박스, 셀렉트 박스 등 넥사크로의 엘리먼트들과 연동해서 데이터를 화면에 쉽게 보여줄 수 있음 == **데이터 바인딩**


### 구조
- 이름을 정해야함. 이름 == id 로 사용됨
- 컬럼별 이름, 타입 필수 기입 (반드시 영어로!)
- xml 구조로 되어있음

```
<Dataset id="ds_member"> // id : ds name
	<ColumnInfo> // 생성한 컬럼 메타데이터
	   <Column id="name" size="5" type="STRING"/>
	   <Column id="age" size="10" type="STRING"/>
	</ColumnInfo>
	<Rows> // 행 데이터
	   <Row>
			<Col id="name">길동</Col> // 컬럼 데이터
			<Col id="age">12</Col>
	   </Row>
	   <Row>
			<Col id="name">흥민</Col>
			<Col id="age">23</Col>
	   </Row>
	</Rows>
 </Dataset>
```


- spring과 연동할떄, 하나의 row는 java의 Map과 매핑 가능
map.key : 'name'
map.value : '길동'

map.key : 'age'
map.value : 12

이걸 List<Map<String, Object>> 로 넘기면됨


## 메서드

### 필터 기능
```
this.ds_my_dataset.filter(" name =='" + name + "' || age == 10 "");
```
-> 나이가 10인 사람중에 이름이 변수 name 에 들어있는 값과 비교해서 있으면 dataset에 한 건이 필터링 됨


### 데이터셋 확인 : saveXML()
```
this.ds_my_dadtaset.saveXML();
```


