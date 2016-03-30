package org.heng.jutils.log.loghelper;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * 0【核心】. 自动关联所调用的上一层方法，这经常是一个业务方法，而且，按照命令约定原则，（类名+方法名）经常 就能代表所操作的事情了。
 * 0【核心】。  让日志调用时的msg，只需要传有意义的消息，而不需要传上下文信息。
 * 1。 （省了）不想再在每个类中定义一个final static logger
 * 2. 这是一个全局的日志
 * 3. 编译时、运行时，字节码修改了的情况。未知。
 * 4. 如果内部类、匿名类，就没问题，因为这是针对java文件粒度的，
 * 5. 但是如果有因为lambda进入新的栈帧，那么输出的方法名就会很难理解。暂时没办法解决。（但是类名、行号还是正确的，还是对应java文件）
 * 6. 内部是用的slf4j接口
 */
public class LogHelper {

	
	private static final AtomicReference<Function<String, String>> classOp = new AtomicReference<>();
	//默认只取简单的类名就好，不取类的全名
	private static final Function<String, String> DEFAULT_CLASS_OP = className -> className.substring(className.lastIndexOf(".") + 1, className.length()); 
	static {
		classOp.set(DEFAULT_CLASS_OP);
	}
	
	public static void setClassOp(final Function<String, String> op){
		if(op != null){
			classOp.set(op);
		}
	}
	
	private static final Logger DEFAULT_LOGGER= LoggerFactory.getLogger("LogHelper:all");
	
	
	
	private static final int DIRECT_STACK_TRACE_INDEX = 4;
	private static final int LOG_STACK_TRACE_INDEX = DIRECT_STACK_TRACE_INDEX;
	
	private static StackTraceElement getTrace(final int index){
		return Thread.currentThread().getStackTrace()[index];//数字与这里的方法调用结构层次有关
	}
	
	
	private static String _log(final int stackTraceIndex){
		final StackTraceElement trace = getTrace(stackTraceIndex);
		return String.format("[%s].[%s] line=[%s]", classOp.get().apply(trace.getClassName()), trace.getMethodName(), trace.getLineNumber());
	}
	
	
	public static String log(){
		return _log(DIRECT_STACK_TRACE_INDEX);
	}
	
	
	private static String _log(final String msg, final int stackTraceIndex){
		final StackTraceElement trace = getTrace(stackTraceIndex);
		//一定长度内
		if(Strings.isNullOrEmpty(msg) || msg.length() < 30){
			return  String.format("[%s].[%s] line=[%s] msg=[%s]", classOp.get().apply(trace.getClassName()), trace.getMethodName(), trace.getLineNumber(), msg);
		}else{
			return  String.format("[%s].[%s] line=[%s] msg=\r\n%s\r\n", classOp.get().apply(trace.getClassName()), trace.getMethodName(), trace.getLineNumber(), msg);
		}
	}
	public static String log(final String msg){
		return  _log(msg, DIRECT_STACK_TRACE_INDEX);
	}
	
	
	private static Logger getLogger() {
		return DEFAULT_LOGGER;
	}
	
	
	
	
	//////info
	public static void info(final String msg){
		getLogger().info(_log(msg, LOG_STACK_TRACE_INDEX));
	}
	
	public static void info(final Throwable t){
		getLogger().info(_log(LOG_STACK_TRACE_INDEX), t);
	}
	
	public static void info(final String msg, final Throwable t){
		getLogger().info(_log(msg, LOG_STACK_TRACE_INDEX), t);
	}
	
	public static void info(final String msg, final Throwable t, final Logger logger){
		logger.info(_log(msg, LOG_STACK_TRACE_INDEX), t);
	}
	

	
	
	/////debug
	public static void debug(final String msg){
		getLogger().debug(_log(msg, LOG_STACK_TRACE_INDEX));
	}
	public static void debug(final Throwable t){
		getLogger().debug(_log(LOG_STACK_TRACE_INDEX), t);
	}
	public static void debug(final String msg, final Throwable t){
		getLogger().debug(_log(msg, LOG_STACK_TRACE_INDEX), t);
	}
	
	public static void debug(final String msg, final Throwable t, final Logger logger){
		logger.debug(_log(msg, LOG_STACK_TRACE_INDEX), t);
	}
	
	
	
	//////warn
	public static void warn(final String msg){
		getLogger().warn(_log(msg, LOG_STACK_TRACE_INDEX));
	}
	public static void warn(final Throwable t){
		getLogger().warn(_log(LOG_STACK_TRACE_INDEX), t);
	}
	public static void warn(final String msg, final Throwable t){
		getLogger().warn(_log(msg, LOG_STACK_TRACE_INDEX), t);
	}
	
	public static void warn(final String msg, final Throwable t, final Logger logger){
		logger.warn(_log(msg, LOG_STACK_TRACE_INDEX), t);
	}
	
	
	
	
	////error
	public static void error(final String msg){
		getLogger().error(_log(msg, LOG_STACK_TRACE_INDEX));
	}
	public static void error(final Throwable t){
		getLogger().error(_log(LOG_STACK_TRACE_INDEX), t);
	}
	public static void error(final String msg, final Throwable t){
		getLogger().error(_log(msg, LOG_STACK_TRACE_INDEX), t);
	}
	
	public static void error(final String msg, final Throwable t, final Logger logger){
		logger.error(_log(msg, LOG_STACK_TRACE_INDEX), t);
	}
	

	
//	public static String logRich(){
//		final StackTraceElement trace = Thread.currentThread().getStackTrace()[2];
//		final String r = String.format("[%s].[%s] line=[%s]", trace.getClassName(), trace.getMethodName(), trace.getLineNumber());
//		System.out.println(r);
//		return r;
//	}
}
