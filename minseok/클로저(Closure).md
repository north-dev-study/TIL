클로저란?
- 함수가 선언되던 시점의 외부 변수와 환경을 기억하고 함수가 나중에 실행되어도 그 변수에 접근할 수 있는 현상
```
function outer() {
  const message = "안녕하세요";

  function inner() {
    console.log(message);
  }

  return inner;
}

const greeting = outer();

greeting(); // 안녕하세요
```
- outer 함수는 inner 함수를 return 한다.
- greeting을 선언하고 outer 함수를 실행해서 inner 함수를 담는다.
- inner 함수는 console.log(message)인데 message는 outer 내부에 선언된 변수이기 때문에 greeting을 실행하면 null이나 undefined가 찍힐 것 같은데 그게 아니라 outer 내부에 있던 "안녕하세요" 라는 변수를 찍어낸다.

이는 함수가 실행될 때가 아닌 함수가 선언될 때의 외부 변수와 환경을 기억하기 때문이다. 
