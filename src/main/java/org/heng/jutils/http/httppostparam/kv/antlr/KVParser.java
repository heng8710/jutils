// Generated from E:\antlr\kv\KV.g4 by ANTLR 4.1
package org.heng.jutils.http.httppostparam.kv.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class KVParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, PName=2, PVal=3, WS=4;
	public static final String[] tokenNames = {
		"<INVALID>", "'='", "PName", "PVal", "WS"
	};
	public static final int
		RULE_r = 0;
	public static final String[] ruleNames = {
		"r"
	};

	@Override
	public String getGrammarFileName() { return "KV.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public KVParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class RContext extends ParserRuleContext {
		public TerminalNode PName() { return getToken(KVParser.PName, 0); }
		public TerminalNode WS(int i) {
			return getToken(KVParser.WS, i);
		}
		public TerminalNode PVal() { return getToken(KVParser.PVal, 0); }
		public List<TerminalNode> WS() { return getTokens(KVParser.WS); }
		public RContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_r; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KVListener ) ((KVListener)listener).enterR(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KVListener ) ((KVListener)listener).exitR(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KVVisitor ) return ((KVVisitor<? extends T>)visitor).visitR(this);
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
			setState(3);
			_la = _input.LA(1);
			if (_la==WS) {
				{
				setState(2); match(WS);
				}
			}

			setState(5); match(PName);
			setState(7);
			_la = _input.LA(1);
			if (_la==WS) {
				{
				setState(6); match(WS);
				}
			}

			setState(9); match(1);
			setState(10); match(PVal);
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
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3\6\17\4\2\t\2\3\2"+
		"\5\2\6\n\2\3\2\3\2\5\2\n\n\2\3\2\3\2\3\2\3\2\2\3\2\2\2\17\2\5\3\2\2\2"+
		"\4\6\7\6\2\2\5\4\3\2\2\2\5\6\3\2\2\2\6\7\3\2\2\2\7\t\7\4\2\2\b\n\7\6\2"+
		"\2\t\b\3\2\2\2\t\n\3\2\2\2\n\13\3\2\2\2\13\f\7\3\2\2\f\r\7\5\2\2\r\3\3"+
		"\2\2\2\4\5\t";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}