package spms.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(
		urlPatterns="/*",
		initParams= {
			@WebInitParam(name="encoding", value="UTF-8")
		})
public class CharacterEncodingFilter implements Filter{
	
	FilterConfig config;

	@Override
	public void destroy() {	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(
				config.getInitParameter("encoding"));
		
		System.out.println("CharacterEncodingFilter - " + 
				config.getInitParameter("encoding"));
		
		// 다음 필터로 전송
		// 다음 필터가 없으면 서블릿의 service() 호출
		FilterChain nextFilter = chain;
		nextFilter.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;		
	}

}
