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
- 과거 메시지는 조회는 무한 스크롤 형식으로 진행
  - 가장 최근 메시지를 10개씩 보여주는 방법으로 채팅방에 스크롤을 올리면 보여주는 걸 가정 (카카오톡과 동일)

### 구성도
![image](https://github.com/user-attachments/assets/d4f71322-53c8-4f6f-b8fc-860a9ce781f6)

