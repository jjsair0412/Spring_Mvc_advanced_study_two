package hello.proxy.purproxy.concreteproxy.code;

public class ConcreateClient {

    private ConcreateLogic concreateLogic;

    public ConcreateClient(ConcreateLogic concreateLogic){
        this.concreateLogic = concreateLogic;
    }

    public void excute(){
        concreateLogic.operation();
    }
}
