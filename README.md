# Linked-House
*Backend Repository*

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fweble4%2Flinked-house-backend&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

### 프로젝트 개요
Weble4 팀의 프로젝트 linked-house backend는 숙박 공유 사이트의 백엔드 만드는 것을 목표로 합니다.
Rest API를 통해 안정적으로 데이터를 주고받는 서버를 구축하여 클라이언트와의 통신을 지원합니다.
향후의 확장성을 염두해 Rest API 형식으로 설계하였습니다.
* * *

### **📅** 프로젝트 기간

백엔드 구현 기간 
2023.08.04 ~ 2023.08.27

* * *

## 팀원 소개

```java
이찬희 = programmer(Team weble4, Project linkedhouse)
```

```java
민세기 = programmer(Team weble4, Project linkedhouse)
```

```java
김지민 = programmer(Team weble4, Project linkedhouse)
```

```java
김승용 = programmer(Team weble4, Project linkedhouse)
```

* * *

### 기술 스택
- Backend

<img alt="Springboot" src ="https://img.shields.io/badge/Spring boot-6DB33F.svg?&style=for-the-badge&logo=Springboot&logoColor=white"/>
<img alt="Springsecurity" src ="https://img.shields.io/badge/Spring security-6DB33F.svg?&style=for-the-badge&logo=Springsecurity&logoColor=white"/>

- DB

<img alt="mysql" src ="https://img.shields.io/badge/mysql-61DAFB.svg?&style=for-the-badge&logo=mysql&logoColor=black"/>
<img alt="redis" src ="https://img.shields.io/badge/redis-DC382D.svg?&style=for-the-badge&logo=redis&logoColor=black"/>

- CI/CD, CLOUD

<img alt="git" src ="https://img.shields.io/badge/git-F05032.svg?&style=for-the-badge&logo=git&logoColor=black"/>
<img alt="github" src ="https://img.shields.io/badge/github-181717.svg?&style=for-the-badge&logo=github&logoColor=white"/>
<img alt="jenkins" src ="https://img.shields.io/badge/jenkins-D24939.svg?&style=for-the-badge&logo=jenkins&logoColor=white"/>
<img alt="docker" src ="https://img.shields.io/badge/docker-2496ED.svg?&style=for-the-badge&logo=docker&logoColor=white"/>
<img alt="naver" src ="https://img.shields.io/badge/naver cloud-03C75A.svg?&style=for-the-badge&logo=naver&logoColor=white"/>

* * *
### ERD

<image src="images/erd.png"></image>

* * *
### 아키텍쳐

<image src="images/architecture.png"></image>

* * *

### Git Commit 전략 & 워크플로우 및 컨벤션

[워크플로우 및 깃 커밋 전략 링크](https://drive.google.com/file/d/1TUeQ6C006s3wgy6Rkkuq26g0kODSsln_/view?usp=sharing)  

[컨벤션 정리 링크](https://drive.google.com/file/d/1eOo6uOa01F0ZvcmgLMiGfxb4PGKxLh0l/view?usp=sharing)

* * *

### 백엔드 기능

[API 설계 보러 가기](https://drive.google.com/file/d/1F39qFjpXvOeruN8FjxDehIKulmPVRdpy/view?usp=sharing)

[주요 구현 기술](https://drive.google.com/file/d/13ewvn-RyL1EciMuZyDEOJe0mmOHd9IAo/view?usp=sharing)

- CRUD 기능 구현
  - 리뷰 게시판을 통한 CRUD 기능 구현
  - House 등록 CRUD 기능 구현
  - Image 업로드 기능 구현


- ADMIN 기능 구현
  - 유저 정지 기능 구현 
  - 전체 알람 보내기 기능
  - 신고 글 확인 및 유저 글 삭제 기능


- 알람 기능
  - RDBMS를 이용하여 CRUD 기능을 통한 알람 생성


-  로그인 기능
   - Spring security + JWT를 활용한 자체 로그인기능 및 OAuth 이용한 소셜 로그인 기능 구현
   - Redis를 활용한 RefreshToken 활용
   - JavaMailSender를 활용한 Email 인증 작업 기능 구현


- WebSocket을 이용한 대화기능 구현
  - Stomp를 사용해 대화기능 구현


- JDBC 활용한 동적 SQL을 활용한 테이블 생성
  - 유저가 숙소 북마크를 할 시, 개인 테이블이 생성되어 저장되게 구현
  - 유저 탈퇴시 개인 유저 탈퇴 테이블이 생성되고, Scheduler를 활용해 일정 기간후 유저 정보 삭제 및 테이블 삭제


- AOP를 이용한 로그 API 타임 측정
  - Thread Local을 활용하여 요청에 동일성을 주려고 함.
  - 레이어 계층의 이동을 표현한 Log로 각 레이어별 요청 응답시간 측정


- Spring data JPA + Querydsl 활용
  - Spring 에서 제공하는 Page 기능 적극 활용
  - Querydsl을 활용하여 동적 SQL을 통한 검색 기능 작성

* * *
### Trouble Shooting

팀원 개개인이 기능 구현을 하며 겪은 Trouble Shooting 정리

[보러가기](https://www.notion.so/Trouble-Shooting-eb252aeeb0fa4498993ce97d22a4a34b?pvs=4)

* * *

### API doc

<image src="images/actu.png"></image>
<image src="images/customer0.png"></image>
<image src="images/customer1.png"></image>
<image src="images/reser0.png"></image>
<image src="images/reser1.png"></image>
<image src="images/reser2.png"></image>
<image src="images/review.png"></image>
<image src="images/admin0.png"/></image>
