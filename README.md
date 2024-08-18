 # 백엔드 과제

### 실행 방법
- 서버 시작 전 선실행
  - src/main/resources/docker 안에 docker-compose.yml 실행
    - redis, kafka
### API 문서
- Swagger 사용
- API 문서 보기
  1. 접속 url (서버 실행 후)
   - http://localhost:8080/swagger-ui/index.html#/
  2. Swagger Editor 사용
   - src/main/resources/swagger 안에 openapi.yaml 를 https://editor.swagger.io/ 에서 import
    
### 가정
- 채팅 목록의 해당 채팅방의 가장 최근 메시지를 조회하는 API의 경우 무한 스크롤 형식으로 진행 (Slice 사용)
  - 가장 최근 메시지를 10개씩 보여주는 방법으로 채팅방에 스크롤을 올리면 보여주는 걸 가정 (카카오톡과 동일)
- seeding sql (data.sql)이 자동으로 실행됨으로 서버 실행 시 데이터가 들어감 (src/main/resources data.sql 참고)
- 논리적 외래키 사용

### 구현
- 채팅 구현으로 websocket 사용
- 사용자가 특정 채팅방에 접속했을 때 최근 메시지 10개씩 redis에 캐싱
  - 모든 과거 메시지를 캐싱하지 않음 (사용자가 스크롤링하면서 10개씩 보여질 때마다 각 10개 세트를 캐싱)
  - TTL를 1시간으로 설정 (1시간 뒤에 캐시가 사라짐)
  - 카카오톡 처럼 채팅방을 껐다가 다시 키는 행동을 짧은 시간 내에 반복했을 때 지속적으로 DB에 연결해 과거 메시지를 가져오는 것 보다는 캐시에서 빠르게 가져오게 함
- 채팅방 생성, 삭제 및 메시지 저장 같은 경우 비동기로 실행
- Kafka를 사용해 메시지 분산처리
- Exception 코드화 (common/error/exception 패키지)
- github action으로 develop에 pr를 올릴 때마다 모든 테스트 자동으로 실행
- Test : e2e, service, repository 테스트 구현
- WebSocketListener를 통해 connetion이 맺혀지거나 끊겼을 때 event 로깅
- WebSocketInterceptor를 통해 구독 시 채팅방의 사용자들을 캐싱 또는 구독 해제 시 캐시 삭제를 통해 채팅방의 사용자가 메시지 송신 시 채팅방의 속한 사용자만이 특정 구독 URL를 통해 메시지 송신 가능하게 함
- Websocket 테스트 (WebScoketChatIntegrationTest.java)로 실제 메시지 송신 테스트 가능
  - Apic 웹사이트(https://apic.app/online/#/tester) 로 테스트 시 WebSocketConfig.java의 ".withSocket()" 제거 필요

### 사용 기술 스택
- Springboot 3.3.2
- Java 17
- Gradle
- JPA
- QueryDsl
- H2 Inmemory DB
- Redis
- Kafka
- Websocket
- Docker Compose
- Github Action
- Swagger
  
### 구성도
![image](https://github.com/user-attachments/assets/d4f71322-53c8-4f6f-b8fc-860a9ce781f6)

### 테이블 구성도
- 논리적 외래키 사용

![image](https://github.com/user-attachments/assets/7a4d9b25-6356-46f6-b200-0c431635c7bc)

