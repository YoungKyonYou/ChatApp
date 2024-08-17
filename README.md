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
-채팅 목록의 해당 채팅방의 가장 최근 메시지를 조회하는 API의 경우 무한 스크롤 형식으로 진행 (Slice 사용)
  - 가장 최근 메시지를 10개씩 보여주는 방법으로 채팅방에 스크롤을 올리면 보여주는 걸 가정 (카카오톡과 동일)

### 구현
- 사용자가 특정 채팅방에 접속했을 때 최근 메시지 10개씩 redis에 캐싱
  - 모든 과거 메시지를 캐싱하지는 않음
  - TTL를 1시간으로 설정 (1시간 뒤에 캐시가 사라짐)
  - 카카오톡 처럼 채팅방을 껐다가 다시 키는 행동을 짧은 시간 내에 반복했을 때 지속적으로 DB에 연결해 과거 메시지를 가져오는 것 보다는 캐시에서 빠르게 가져오게 함
- 채팅방 생성, 삭제 및 메시지 저장 같은 경우 비동기로 실행
- Kafka를 사용해 메시지 분산처리
### 구성도
![image](https://github.com/user-attachments/assets/d4f71322-53c8-4f6f-b8fc-860a9ce781f6)

