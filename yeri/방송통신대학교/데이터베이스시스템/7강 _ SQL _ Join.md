JOIN : 일시적으로 하나의 레코드로 만드는 것

## 1. Cross JOIN

조인 조건 없이 두 테이블 간 조합 가능한 모든 레코드를 결합
- 그냥 두 테이블의 모든 컬럼이 합쳐져서 나옴

## 2. 내부 조인 (INNDER JOIN)

두 개 이상의 테이블에서 조인 조건을 만족하는 레코드만 결합하여 출력 결과에 포함

```ANSI 방식
SELECT [COLUMN]
FROM [TABLE1] INNER JOIN [TABLE2]
ON [TABLE1.COLUMN] = [TABLE2.COLUMN]
```

```Oracle
SELECT [COLUMN]
FROM [TABLE1], [TABLE2]
WHERE [TABLE1.COLUMN] = [TABLE2.COLUMN]
```

국제표준 방식과 오라클 방식이 다름(오라클은 WHERE 절 안에 ON 절 조건을 넣음)

