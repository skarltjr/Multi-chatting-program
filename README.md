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


### 스레드 사용 이유
#### 클라이언트
- 클라이언트에서 send와 receive역할을 각각 thread를 따로 할당하여 담당
    - 내가 채팅을 입력할 때도 다른 사람이 보낸 메세지를 볼 수 있어야 한다고 생각했기 때문
- 서버에서 각 클라이언트 connect를 accept한 socket을 새로운 스레드를 할당하여 처리한 이유
    - 여러 클라이언트의 connect를 대응하기 위해
    - 왜 프로세스 대신 스레드? => 새로운 프로세스 생성은 스레드보다 많은 리소스가 낭비된다고 생각. 


### 정리
0. 가장 중요한 핵심은 서버는 말 그대로 서버의 역할만 수행. 이전처럼 서버와 클라이언트 1:1채팅이 아니라 서버는 여러 클라이언트가 다대다로 소통할 수 있도록 중개
1. 서버는 새로운 스레드로 accept 소캣 담당
2. 클라이언트는 sendThread를 통해 서버로 메세지를 보내고 / receiveThread를 통해 서버로부터 메세지를 받는다
    - 클라이언트가 서버로 메세지를 보낸다
    - 서버는 해당 메세지를 읽고 클라이언트들(단톡방 멤버)에게 해당 메세지를 모두 전송
    - 클라이언트는 receiveThread를 통해 이 메세지를 서버로부터 전달받아 읽는다.
3. 단톡방 멤버 리스트는 모든 서버 스레드가 공유하기에 `synchronizedList로`
4. 정리하자면 서버에서는 각 accept소캣을 각각의 스레드로 담당하고 /  각각의 클라이언트는 send / receive를 각 thread를 할당받아 수행


![Untitled Diagram drawio](https://user-images.githubusercontent.com/62214428/146584070-c87b3ef5-7484-4333-bd17-b9188f75e967.png)
