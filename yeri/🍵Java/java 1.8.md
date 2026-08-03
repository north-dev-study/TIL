## 람다식

```
list.forEach(item -> System.out.println(item));
```

## Stream API

```
List<String> names = users.stream()
        .map(User::getName)
        .collect(Collectors.toList());
```

## 새로운 날짜 API

```
LocalDate today = LocalDate.now();
LocalDateTime now = LocalDateTime.now();
```

## 인터페이스의 default 메서드

```
public interface UserService {

    default void print() {
        System.out.println("default");
    }
}
```


