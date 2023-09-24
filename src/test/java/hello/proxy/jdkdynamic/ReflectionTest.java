package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello tartget = new Hello();

        // 공통 로직1 시작
        log.info("start");
        String result1 = tartget.callA(); //호출하는 메서드만 다름
        log.info("result={}", result1);
        // 공통 로직1 종료

        // 공통 로직2 시작
        log.info("start");
        String result2 = tartget.callB(); //호출하는 메서드만 다름
        log.info("result={}", result2);
        // 공통 로직2 종료

    }

    static class Hello {

        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }

    @Test
    void reflection1() throws Exception {
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        //call A의 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);
        log.info("result={}", result1);

        //call B의 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result={}", result2);

        // 문자로 바꿨다는 말은 파라미터로 바꿀수 있다는 의미!
    }


    @Test
    void reflection2() throws Exception {
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        //call A의 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        //call B의 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);

        // 문자로 바꿨다는 말은 파라미터로 바꿀수 있다는 의미!
    }

    private void dynamicCall(Method method, Object target) throws Exception{
        // 공통 로직1 시작
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}", result);
        // 공통 로직1 종료
    }

    // 주의
    // 어쩔 수 없는 경우에는 사용해야 하지만 런타임에 동작하기 때문에, 컴파일 시점에 오류를 잡을 수 없다.
    // 실수로 classHello.getMethod("callAaaaa"); 이렇게 하면 배포 다 하고 운영 시점에 에러가 터진다.
}
