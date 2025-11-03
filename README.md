# Linked-House
*Backend Repository*

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

모든 작업은 feature 브랜치에서 시작되며, 충분한 논의와 코드 리뷰를 거친 후에만 main 브랜치에 병합하는 것을 원칙으로 삼았습니다.

1. 브랜치 전략 (Branch Strategy)
main:
항상 배포 가능한 상태를 유지하는 프로덕션 브랜치입니다.
직접적인 Commit은 금지되며, 오직 Pull Request(PR)를 통해서만 병합됩니다.
팀원 전체의 코드 리뷰와 테스트를 통과한 기능만이 main 브랜치에 반영됩니다.
feature/{기능-이슈번호}:
새로운 기능을 개발하거나 버그를 수정하는 등 모든 작업은 feature 브랜치에서 진행됩니다.
브랜치 이름은 어떤 기능인지 명확히 알 수 있도록 feature/login-12와 같이 직관적으로 명명했습니다.
기능 개발이 완료되면, main 브랜치를 대상으로 Pull Request(PR)를 생성합니다.

3. 작업 프로세스 (Workflow)
Issue 생성: 새로운 기능 개발이나 버그 수정이 필요할 경우, GitHub Issue를 생성하여 작업을 할당합니다.
Feature 브랜치 생성: 로컬에서 최신 main 브랜치를 pull 받은 후, 담당한 이슈에 해당하는 feature 브랜치를 생성하고 해당 브랜치로 이동(checkout)합니다.
code
Bash
git checkout -b feature/jwt-auth main
기능 개발 및 Commit: 기능을 개발하며, 의미 있는 단위로 작업을 나누어 Commit합니다.
Commit 메시지는 Conventional Commits 양식을 따라 타입(스코프): 제목 형식으로 작성하여 Commit의 목적을 명확히 했습니다. (e.g., feat(auth): JWT 로그인 기능 추가)
Pull Request (PR) 생성: 기능 개발이 완료되면, 원격 저장소에 feature 브랜치를 push하고 main 브랜치를 대상으로 PR을 생성합니다.
PR 템플릿을 활용하여 **"어떤 작업을 했는지", "어떻게 테스트했는지", "리뷰어가 중점적으로 봐야 할 부분은 무엇인지"**를 상세히 기술했습니다.

코드 리뷰 및 테스트:
PR이 생성되면 최소 1명 이상의 팀원에게 코드 리뷰를 요청합니다.
리뷰어는 코드의 개선 방향에 대해 피드백을 남기고, 모든 피드백이 반영되면 'Approve' 합니다.
필요 시 CI/CD 파이프라인을 통해 자동화된 테스트를 통과해야 합니다.
main 브랜치에 병합: 코드 리뷰와 테스트가 모두 완료되면, PR을 main 브랜치에 병합(Squash and Merge)하고, 원격 및 로컬의 feature 브랜치를 삭제하여 브랜치를 정리합니다.

* * *

### 백엔드 기능

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

### API doc

<image src="images/actu.png"></image>
<image src="images/customer0.png"></image>
<image src="images/customer1.png"></image>
<image src="images/reser0.png"></image>
<image src="images/reser1.png"></image>
<image src="images/reser2.png"></image>
<image src="images/review.png"></image>
<image src="images/admin0.png"/></image>
