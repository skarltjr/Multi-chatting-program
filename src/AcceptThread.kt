import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

import java.util.Collections




class AcceptThread(
        var socket: Socket,
): Thread() {
        companion object{
                // static의 개념
                // 모든 스레드에서 해당 list를 공유하도록 목적
                // 그런데 여기서 모든 스레드가 해당 list에 접근 가능하다는것은 큰 문제발생의 원인이 될 수 있다
                // 그래서 ★ 해당 리스트는 한 번에 한 명씩 접근 가능하도록 sync
                var list: MutableList<PrintWriter> = Collections.synchronizedList(mutableListOf<PrintWriter>())
        }
        var printWriter: PrintWriter
        init {
                this.printWriter = PrintWriter(socket.getOutputStream())
                list.add(printWriter)
        }

        override fun run() {
                var name: String? = null
                try {
                        println("닉네임을 입력해주세요!")
                        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                        name = reader.readLine()
                        sendAll(name + "님이 입장했습니다")
                        while (true) {
                                val context = reader.readLine()
                                if (context == null) {
                                        break
                                }
                                sendAll(name + " : " + context)
                        }
                } catch (e: Exception) {

                }finally {
                        list.remove(this.printWriter)
                        sendAll("알림 : " + name + "님이 퇴장했습니다")
                        socket.close()
                }
        }

        fun sendAll(message:String) {
                for (printWriter in list) {
                        printWriter.println(message)
                        printWriter.flush()
                }
        }
}