// Generated from E:\antlr\httpform\HttpFormParam.g4 by ANTLR 4.1
package org.heng.jutils.http.httppostparam.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HttpFormParamParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, Pair=2, PName=3, PVal=4, WS=5;
	public static final String[] tokenNames = {
		"<INVALID>", "'&'", "Pair", "PName", "PVal", "WS"
	};
	public static final int
		RULE_r = 0;
	public static final String[] ruleNames = {
		"r"
	};

	@Override
	public String getGrammarFileName() { return "HttpFormParam.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public HttpFormParamParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class RContext extends ParserRuleContext {
		public List<TerminalNode> Pair() { return getTokens(HttpFormParamParser.Pair); }
		public TerminalNode Pair(int i) {
			return getToken(HttpFormParamParser.Pair, i);
		}
		public RContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_r; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HttpFormParamListener ) ((HttpFormParamListener)listener).enterR(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HttpFormParamListener ) ((HttpFormParamListener)listener).exitR(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HttpFormParamVisitor ) return ((HttpFormParamVisitor<? extends T>)visitor).visitR(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RContext r() throws RecognitionException {
		RContext _localctx = new RContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_r);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(2); match(Pair);
			}
			setState(7);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==1) {
				{
				{
				setState(3); match(1);
				setState(4); match(Pair);
				}
				}
				setState(9);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3\7\r\4\2\t\2\3\2\3"+
		"\2\3\2\7\2\b\n\2\f\2\16\2\13\13\2\3\2\2\3\2\2\2\f\2\4\3\2\2\2\4\t\7\4"+
		"\2\2\5\6\7\3\2\2\6\b\7\4\2\2\7\5\3\2\2\2\b\13\3\2\2\2\t\7\3\2\2\2\t\n"+
		"\3\2\2\2\n\3\3\2\2\2\13\t\3\2\2\2\3\t";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}