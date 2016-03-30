package org.heng.jutils.http.httppostparam;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;
import org.apache.commons.io.IOUtils;
import org.heng.jutils.http.httppostparam.antlr.HttpFormParamLexer;
import org.heng.jutils.http.httppostparam.kv.antlr.KVLexer;
import org.heng.jutils.jsonpath.JsonPath;

import com.google.common.base.CharMatcher;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * 解析表单（application/x-www-form-urlencoded）格式的字符串，解析成map层次。
 */
public class FormParamParser {
	
	
	private static final String SUPPORT_HTTP_METHOD = "POST";
	private static final String SUPPORT_CONTENT_TYPE = "application/x-www-form-urlencoded";
	
	private static final int DEFAULT_REQUEST_CONTENT_LENGTH = 10*1024; //10k
	
	/**
	 * 这不适合在servlet filter之中做，而是应该在应用端做，因为流默认不可读多次，而且是有状态的，在复杂的环境中，会让事情不可控。
	 * @param req
	 * @return
	 */
	public static Map<String, Object> parse(final HttpServletRequest req) {
		if(!Objects.equal(SUPPORT_HTTP_METHOD, req.getMethod()) 
				//|| !Objects.equal(SUPPORT_CONTENT_TYPE, CharMatcher.whitespace().removeFrom(req.getContentType().toLowerCase()))/*content-type这个不行，因为有时候client会不带这个参数*/
				){
			throw new IllegalStateException("http request类型不适合解析输入流中的参数");
		}
		final byte[] buffer = new byte[req.getContentLength() >=0 ? req.getContentLength() : DEFAULT_REQUEST_CONTENT_LENGTH];
		try {
			final int length = IOUtils.read(req.getInputStream(), buffer);
			return parse(new String(buffer, 0, length, Charset.forName("utf-8")));
		} catch (IOException e) {
			throw new RuntimeException("尝试从http request的输入 流中解析参数时出错", e);
		} 
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * （只做token解析）
	 * @param s： 【jjww=&   	orderby=1&myname=东方& show =title%2Cbname&myorder=1&keyboard=abccc&button=%CB%D1%CB%F7%C2%FE%BB%AD】
	 * @return
	 */
	public static Map<String, Object> parse(final String s){
		final String err = String.format("解析=[%s]不对，无法正常解析", s);
		try {
			
			final HttpFormParamLexer lexer = new HttpFormParamLexer(new ANTLRInputStream( s));
			final TokenSource ts = new CommonTokenStream(lexer).getTokenSource();
			
			final Map<String, Object> r = Maps.newHashMap();
			
			for(Token t = ts.nextToken(); !Objects.equal(t.getType(), Token.EOF);t = ts.nextToken()){
				switch(t.getType()){
					case HttpFormParamLexer.Pair: {
						final String[] tmp = parseKV(t.getText());
						final String k = tmp[0];
						final String v = tmp[1];
						if(!Strings.isNullOrEmpty(k)){
							JsonPath.setByPath(r, "."+ k, v);
						}
						break;
					}
					default: break;
				}
				
			}
			return r;
		} catch (Exception e) {
			throw new IllegalArgumentException(err, e);
		}
	}
	
	
	
	private static String[] parseKV(final String raw){
		final KVLexer kvLexer = new KVLexer(new ANTLRInputStream( raw ));
		final TokenSource kvTk = new CommonTokenStream(kvLexer).getTokenSource();
		
		String k = null;
		String v = null;
		
		int index = 0;
		
		
		for(Token t = kvTk.nextToken(); !Objects.equal(t.getType(), Token.EOF);t = kvTk.nextToken()){
			//k因为两边空白没处理好，会误解析成为v
			//v因为格式太正了，会误被解析成k
			//只能依赖于上下文先后顺序来解决
			if(t.getType() == KVLexer.PName || t.getType() == KVLexer.PVal){
				if(index == 0){
					k = CharMatcher.WHITESPACE.removeFrom(t.getText());
					index++;
					continue;
				}
				
				if(index == 1){
					v = t.getText();
					continue;
				}
			}
		}
		return new String[]{Strings.nullToEmpty(k), Strings.nullToEmpty(v)};
	}

}
