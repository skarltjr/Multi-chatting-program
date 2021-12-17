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