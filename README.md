# 백엔드 과제

### 실행 방법
- 서버 시작 전 선실행
  - src/main/resources/docker 안에 docker-compose.yml 실행
    - redis, kafka 도커 이미지로 실행
### API 문서
- Swagger 사용
- API 문서 보기
  1. 접속 url (서버 실행 후)
  - http://localhost:8080/swagger-ui/index.html#/
  2. Swagger Editor 사용
  - resources의 openapi.yaml 를 https://editor.swagger.io/ 에서 import
info:
  title: Chat App Project Springdoc
  description: Chat App Project Swagger UI
  version: 1.0.0
servers:
  - url: http://localhost:8080
    description: Generated server url
paths:
  /chats/rooms:
    post:
      tags:
        - chat-room-controller
      summary: 채팅방 생성 API
      description: 요청된 채팅방 이름으로 채팅방 생성
      operationId: createChatRoom
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/ChatRoomRequest'
        required: true
      responses:
        '201':
          description: 성공 시 Created Response 반환
  /member/recently-active/count:
    get:
      tags:
        - member-controller
      operationId: getRecentLoginMemberCount
      responses:
        '200':
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/DataResponseInteger'
  /chats/rooms/{roomId}/past-messages:
    get:
      tags:
        - chat-room-member-controller
      summary: 과거 채팅 내역 반환 API
      description: 과거 채팅 내역을 무한 스크롤 형식으로 반환 - Slice 사용
      operationId: getPastChatRoomMessages
      parameters:
        - name: pageableRequest
          in: query
          required: true
          schema:
            $ref: '#/components/schemas/PageableRequest'
        - name: roomId
          in: path
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '200':
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/DataResponseSliceChatMessageDto'
  /chats/rooms/recently-active:
    get:
      tags:
        - chat-room-controller
      summary: 채팅 목록 반환 API
      description: 30분 내에 접속한 사용자 수의 내림차순으로 채팅 목록 정렬
      operationId: getChatRoomByRecentLoginMember
      responses:
        '200':
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/DataResponseListChatRoomDto'
  /chats/rooms/{roomId}:
    delete:
      tags:
        - chat-room-member-controller
      summary: 채팅방 삭제 API
      description: 채팅방 ID로 채팅방 및 참가자 정보 삭제
      operationId: removeChatRoom
      parameters:
        - name: roomId
          in: path
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '204':
          description: ChatRoom과 Participant 테이블에 정보 삭제
components:
  schemas:
    ChatRoomRequest:
      required:
        - memberIds
      type: object
      properties:
        roomName:
          maxLength: 100
          minLength: 1
          type: string
        memberIds:
          type: array
          items:
            type: integer
            format: int64
    DataResponseInteger:
      type: object
      properties:
        data:
          type: integer
          format: int32
        error:
          $ref: '#/components/schemas/ErrorResponse'
    ErrorResponse:
      type: object
      properties:
        code:
          type: string
        message:
          type: string
    PageableRequest:
      required:
        - page
        - size
      type: object
      properties:
        page:
          type: integer
          format: int32
        size:
          type: integer
          format: int32
    ChatMessageDto:
      type: object
      properties:
        content:
          type: string
        senderId:
          type: integer
          format: int64
        senderName:
          type: string
        chatRoomId:
          type: integer
          format: int64
        chatRoomName:
          type: string
        createdAt:
          type: string
          format: date-time
    DataResponseSliceChatMessageDto:
      type: object
      properties:
        data:
          $ref: '#/components/schemas/SliceChatMessageDto'
        error:
          $ref: '#/components/schemas/ErrorResponse'
    PageableObject:
      type: object
      properties:
        offset:
          type: integer
          format: int64
        sort:
          $ref: '#/components/schemas/SortObject'
        pageNumber:
          type: integer
          format: int32
        pageSize:
          type: integer
          format: int32
        paged:
          type: boolean
        unpaged:
          type: boolean
    SliceChatMessageDto:
      type: object
      properties:
        size:
          type: integer
          format: int32
        content:
          type: array
          items:
            $ref: '#/components/schemas/ChatMessageDto'
        number:
          type: integer
          format: int32
        sort:
          $ref: '#/components/schemas/SortObject'
        first:
          type: boolean
        last:
          type: boolean
        numberOfElements:
          type: integer
          format: int32
        pageable:
          $ref: '#/components/schemas/PageableObject'
        empty:
          type: boolean
    SortObject:
      type: object
      properties:
        empty:
          type: boolean
        sorted:
          type: boolean
        unsorted:
          type: boolean
    ChatRoomDto:
      type: object
      properties:
        roomId:
          type: integer
          format: int64
        roomName:
          type: string
    DataResponseListChatRoomDto:
      type: object
      properties:
        data:
          type: array
          items:
            $ref: '#/components/schemas/ChatRoomDto'
        error:
          $ref: '#/components/schemas/ErrorResponse'



### 가정
- 과거 메시지는 조회는 무한 스크롤 형식으로 진행
  - 가장 최근 메시지를 10개씩 보여주는 방법으로 채팅방에 스크롤을 올리면 보여주는 걸 가정 (카카오톡과 동일)

![image](https://github.com/user-attachments/assets/d4f71322-53c8-4f6f-b8fc-860a9ce781f6)

