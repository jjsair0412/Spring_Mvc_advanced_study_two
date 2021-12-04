package hello.proxy.purproxy.proxy.code;

public class ProxyPatternClient {
    private Subject subject; // 클라이언트는 subject라는애를 DI.

    public ProxyPatternClient(Subject subject){
        this.subject = subject;
    }

    public void execute(){
        subject.operation(); // DI한 subject의 operation 메서드를 호출
    }
}
