package hello.proxy;

import hello.proxy.config.ConcreteProxyConfig;
import hello.proxy.config.InterfaceProxyConfig;
import hello.proxy.config.v2_dynamicproxy.DynamicProxyBasicConfig;
import hello.proxy.config.v2_dynamicproxy.DynamicProxyFiterConfig;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


//@Import({AppV1Config.class, AppV2Config.class}) // 빈을 수동등록할때 이렇게 사용해도 된다.
@SpringBootApplication(scanBasePackages = "hello.proxy.app") // 주의 - 컴포넌트 스캔의 범위는 , main이 위치한 패키지에 있는 애들이랑 하위애들만 컴포넌트 스캔 대상이 되는데,
 															// 안에 저렇게 설정해주면 hello.proxy.app과 그 하위만 컴포넌트스캔 대상이 된다.
//@Import(InterfaceProxyConfig.class)
//@Import(ConcreteProxyConfig.class)
//@Import(DynamicProxyBasicConfig.class)
@Import(DynamicProxyFiterConfig.class)
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

	@Bean
	public LogTrace logTrace(){
		return new ThreadLocalLogTrace();
	}
}
