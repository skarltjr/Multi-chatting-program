import java.net.ServerSocket

class Main {
    companion object {
        const val PORT = 8080
    }
}
fun main(args: Array<String>) {
    var serverSocket: ServerSocket? = null
    try {
        //socket & bind
        serverSocket = ServerSocket(Main.PORT)
        // 1:1과의 차이점 : while을 통해 여러 클라이언트의 요청을 accept
        while (true) {
            val accept = serverSocket.accept()
            val acceptThread = AcceptThread(accept)
            acceptThread.start()
        }
    } catch (e: Exception) {

    }
}