package org.heng.jutils.jsonpath;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.heng.jutils.jsonpath.attribute.ArrayAttribute;
import org.heng.jutils.jsonpath.attribute.Attribute;
import org.heng.jutils.jsonpath.attribute.AttributeChainParser;
import org.heng.jutils.jsonpath.attribute.IAttribute;


/**
 * 详见{@link JsonPath}
 * 这个提供了一个实例化的方法
 *## TODO
 */
public class JsonPathBean {

	
	/**
	 * {@link #getByPath(Map, String)}
	 */
	public Integer getIntegerByPath(final Map<String, Object> map, String path){
		return JsonPath.getIntegerByPath(map, path);
	}
	
	
	/**
	 * {@link #getByPath(Map, String)}
	 */
	public static String getStringByPath(final Map<String, Object> map, String path){
		return JsonPath.getStringByPath(map, path);
	}
	
	
	/**
	 * 目前只能解析下面几种情况的js属性：
	 * 1. '.' [-a-zA-Z0-9_]+
	 * 2. '[' [0-9]+ ']'
	 * @param map
	 * @param path
	 * @return
	 */
	public static Object getByPath(final Map<String, Object> map, String path){
		return JsonPath.getByPath(map, path);
	}
	
	
	/**
	 * {@link #getByPath(List, String)}
	 */
	public static Integer getIntegerByPath(final List<Object> map, String path){
		return JsonPath.getIntegerByPath(map, path);
	}
	
	
	
	/**
	 * {@link #getByPath(List, String)}
	 */
	public static String getStringByPath(final List<Object> map, String path){
		return JsonPath.getStringByPath(map, path);
	}
	
	/**
	 * 目前只能解析下面几种情况的js属性：
	 * 1. '.' [-a-zA-Z0-9_]+
	 * 2. '[' [0-9]+ ']'
	 * @param map
	 * @param path
	 * @return
	 */
	public static Object getByPath(final List<Object> map, String path){
		return JsonPath.getByPath(map, path);
	}
	
	
	
	
	///////////////setter
	public void setByPath(final Object map, final Object val ,final String path){
		if(path == null || path.length() == 0){
			throw new IllegalArgumentException();
		}
		setByPath0(map, val, path);
	}
	
	static void setByPath0(final Object map, final Object val ,final String path){
		final List<IAttribute> attList = AttributeChainParser.parse(path);
		if(attList.size() <=0){
			throw new IllegalArgumentException();
		}
		Object currentObj = map;
		IAttribute currentAtt = null;
		final String err = String.format("path=[%s]与 数据类型不匹配", path);
		
		for(final ListIterator<IAttribute> it = attList.listIterator();;){
			if(!it.hasNext()){
				throw new IllegalStateException();
			}else{
				//currentObj往右移一位
				currentAtt = it.next();
				//预支一位
				final IAttribute nextAtt = it.hasNext()? it.next(): null;
				//回退一位
				it.previous();
				
				if (currentAtt instanceof Attribute) {//.xxx
					//currentObj现在为止还是parent
					//parent绝对不会为null
					if(currentObj instanceof Map){
						final Attribute att = (Attribute) currentAtt;
						
						if(nextAtt == null){
							((Map)currentObj).put(att.name, val);
							return ;
						}else{
							if(((Map)currentObj).get(att.name) == null){
								((Map)currentObj).put(att.name, nextAtt instanceof Attribute? new HashMap<>(): new ArrayList<>());
							}
							//下一轮
							currentObj = ((Map)currentObj).get(att.name);
							continue;
						}
					}else{
						throw new IllegalStateException(err);
					}
				}
				
				if (currentAtt instanceof ArrayAttribute) {//[123]
					//currentObj现在为止还是parent
					//parent绝对不会为null
					if(currentObj instanceof List){
						final ArrayAttribute att = (ArrayAttribute) currentAtt;
						
						if(nextAtt == null){
							safeSetList((List)currentObj, att.index, val);
							return ;
						}else{
							if(((List)currentObj).size() <= att.index || ((List)currentObj).get(att.index) == null){
								safeSetList((List)currentObj, att.index, nextAtt instanceof Attribute? new HashMap<>(): new ArrayList<>());
							}
							//下一轮
							currentObj = ((List)currentObj).get(att.index);
							continue;
						}
					}else{
						throw new IllegalStateException(err);
					}
				}
				
				throw new IllegalStateException();
			}
		}
	
		
	}
	
	
	/**
	 * 前置元素设置为null
	 * @param list
	 * @param index
	 * @param val
	 */
	static void safeSetList(final List list, final int index, final Object val){
		if(list.size() > index){
			list.set(index, val);
			return ;
		}
		
		//前置元素设置为null
		while(list.size() < index){
			list.add(null);
		}
		list.add(val);
		
	}
	
	
	
	public static void main(String...args){
		
		Map m = new HashMap();
		setByPath(m, "7796", ".a.b.c.d[3][1].e[2].f[0]");
		System.out.println(getByPath(m, ".a.b.c.d[3][1].e[2]"));
		System.out.println(m);
		
	}
}
