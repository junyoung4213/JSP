package spms.controls;

import java.util.Map;

/* pageController 객체를 동일한 방식으로 호출하기 위해
 * 정의한 인터페이스
 * 모든 pageController 클래스는 이 interface를 상속받는다
 * */
public interface Controller {
	public String execute(Map<String, Object> model) throws Exception;
}
