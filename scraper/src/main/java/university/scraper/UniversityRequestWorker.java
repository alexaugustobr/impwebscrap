package university.scraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.Map;

public class UniversityRequestWorker {
	
	private String url;
	private Map<String, String> cookies = new HashMap<>();
	private Map<String, String> authCookies = new HashMap<>();
	private Map<String, String> data = new HashMap<>();
	
	public UniversityRequestWorker() {
	}
	
	UniversityRequestWorker withUrl(String url) {
		this.url = url;
		return this;
	}
	
	UniversityRequestWorker withUser(UserSession userSession) {
		this.authCookies.putAll(userSession.getAuthCookies());
		return this;
	}
	
	UniversityRequestWorker withCookies(Map<String, String> cookies) {
		this.cookies.putAll(cookies);
		return this;
	}
	
	UniversityRequestWorker withData(Map<String, String> data) {
		this.data.putAll(data);
		return this;
	}
	
	UniversityRequestWorker withData(String key, String value) {
		this.data.put(key, value);
		return this;
	}
	
	UniversityRequestWorker cleanCookies() {
		this.cookies = new HashMap<>();
		return this;
	}
	
	UniversityRequestWorker cleanData() {
		this.data = new HashMap<>();
		return this;
	}
	
	UniversityRequestWorker cleanUser() {
		this.authCookies = new HashMap<>();
		return this;
	}
	
	Connection.Response doGet() {
		return doRequest(Connection.Method.GET);
	}
	
	Connection.Response doPost(Connection.Method method) {
		return doRequest(Connection.Method.POST);
	}
	
	Connection.Response doRequest(Connection.Method method) {
		try {
			Connection connection = Jsoup.connect(url)
					.cookies(cookies)
					.cookies(authCookies)
					.data(data)
					.method(method);
			return connection.execute();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
