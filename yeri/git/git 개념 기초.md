
참조 : [테코톡 git 기초 명령어](https://www.youtube.com/watch?v=kbAvBcLmgwE)

git : 분산 **버전관리** 시스템

![255](../../assets/Pasted%20image%2020260721213644.png)

이렇게 여러 버전이 생기는 것을 하나의 파일만으로 관리한다.

## commit
- 파일의 각각의 버전. 수정2, 완성본, 찐최종 ...

## HEAD
- 현재 작업하고 있는 위치

## 브랜치
- 파일의 복사본을 만드는 행위

## .git
`git init` 시 생성되는 폴더.
지금까지 진행한 모든 작업 변경내용이 들어있는 저장소.
만약 이 .git 파일을 날린다면? 최종버전 파일만 남아있게됨

## commit
Staging Area에 있는 내용을 바탕으로 새로운 버전을 생성
commit시 특정한 버전명이 생성된다.

**commit 과정**
1. HEAD가 가리키고 있던 곳이 새로운 버전의 부모가 됨
2. 커밋된 정보를 반영해 새 버전의 커밋 ID를 결정함
3. HEAD가 새로운 버전을 가리킴


## tracked / untracked
- tracked : 이전에 한번이라도 add가 된 파일
- untracked : 한 번도 add가 되지 않은 파일


## 명령어들
- git add 로 stage에 올라간 file 취소
`git reset [file_name]`


- main 브랜치로 돌아가기
`git checkout main`
`git checkout [@main의 commit_id]`



