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
 * 1. 以类似json的方式来读取Map、List的内嵌元素
 * 【.a.b[9].c[1].d】
 * 【[0].a.b.c[7]】
 * 
 * 2. 线程安全不要在这些节点上搞，中间会生成缺少的节点（hashMap、arrayList）
 */
public final class JsonPath {

	
	/**
	 * {@link #getByPath(Map, String)}
	 */
	public static Integer getIntegerByPath(final Map<String, Object> map, String path){
		final Object r = getByPath(map, path);
		if(r instanceof Number){
			return Integer.valueOf(r.toString());
		}
		throw new IllegalArgumentException("不是number类型");
	}
	
	
	/**
	 * {@link #getByPath(Map, String)}
	 */
	public static String getStringByPath(final Map<String, Object> map, String path){
		final Object r = getByPath(map, path);
		return r != null? r.toString(): null;
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
		if(map == null || path == null || path.length() == 0){
			throw new IllegalArgumentException();
		}
		return getByPath0(map, path);
	}
	
	
	/**
	 * {@link #getByPath(List, String)}
	 */
	public static Integer getIntegerByPath(final List<Object> map, String path){
		final Object r = getByPath(map, path);
		if(r instanceof Number){
			return Integer.valueOf(r.toString());
		}
		throw new IllegalArgumentException("不是number类型");
	}
	
	
	
	/**
	 * {@link #getByPath(List, String)}
	 */
	public static String getStringByPath(final List<Object> map, String path){
		final Object r = getByPath(map, path);
		return r != null? r.toString(): null;
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
		return getByPath0(map, path);
	}
	
	
	
	
	/**
	 * 目前只能解析下面几种情况的js属性：
	 * 1. '.' [-a-zA-Z0-9_]+
	 * 2. '[' [0-9]+ ']'
	 * @param map
	 * @param path
	 * @return
	 */
	static Object getByPath0(final Object map, final String path){
		final List<IAttribute> attList = AttributeChainParser.parse(path);
		if(attList.size() <=0){
			throw new IllegalArgumentException();
		}
		Object currentObj = map;
		final String err = String.format("path=[%s]与 数据类型不匹配", path);
		for(final IAttribute attribute: attList){
			//如果中间环节为null，就直接返回null
			if(currentObj == null){
				return null;
			}
			if(attribute instanceof Attribute){
				final Attribute att = (Attribute)attribute;
				if(currentObj instanceof Map){
					currentObj = ((Map)currentObj).get(att.name);
					continue;
				}
				throw new IllegalArgumentException(err);
			}else if(attribute instanceof ArrayAttribute){
				final ArrayAttribute att = (ArrayAttribute)attribute;
				if(currentObj instanceof List){
					final List l = (List)currentObj;
					if(l.size() > att.index){
						currentObj = l.get(att.index);
						continue;
					}else{
						throw new IndexOutOfBoundsException();
					}
				}
				throw new IllegalArgumentException(err);
			}else{
				throw new IllegalStateException();//unreachable
			}
		}
		return currentObj;
	}
	
	
	
	
	
	///////////////setter
	public static void setByPath(final Object map, final Object val ,final String path){
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
	
	
	
}
