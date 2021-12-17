# Multi-chatting-program
- Multi-chatting-program-server
- 1:1 채팅 https://github.com/skarltjr/chatting_program
- 1:1 채팅 프로그램 확장해보기 위한 N:N 채팅 ( 단톡 ) 구현해보기
- client : https://github.com/skarltjr/Multi-chatting-program-client


### 개요
- 단톡을 구성하기 위해서 서버는 while을 돌면서 계속 accept를 받는다
- accept로 생성된 소캣은 새로운 스레드를 할당하여 담당
- 각각의 유저가 보낸 메세지는 모든 다른 유저에게 전달된다.


### 주의 사항
- 서버는 while을 통해 계속 listen하면서 요청이 오면 accept를 수행
- accept 소캣은 새로운 스레드를 할당하여 담당한다
- 접속자 list가 존재하고 해당 list는 static 개념으로 전역적으로 공유하는 companion object로 만든다.
- ★가장 중요점 / 여러 스레드에서 list를 공유한다
    - 즉 멀티 스레드 환경에서 공유변수가 문제될 수 있다
    - 이를 막기 위해 해당 list는 synchronizedList로 사용


### 정리
1. 서버는 새로운 스레드로 accept 소캣 담당
2. 클라이언트는 sendThread를 통해 서버로 메세지를 보내고 / receiveThread를 통해 서버로부터 메세지를 받는다
    - 사실 서버로 보내는게 아니라 단톡방 멤버들에게 보낸다는 개념 / 받는것도 마찬가지
3. 단톡방 멤버 리스트는 모든 서버 스레드가 공유하기에 sync
4. 정리하자면 서버에서는 각 accept소캣을 각각의 스레드로 담당하고 /  각각의 클라이언트는 send / receive를 각 thread를 할당받아 수행
![Untitled Diagram drawio](https://user-images.githubusercontent.com/62214428/146584070-c87b3ef5-7484-4333-bd17-b9188f75e967.png)
