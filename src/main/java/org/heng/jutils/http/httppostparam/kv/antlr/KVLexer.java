// Generated from E:\antlr\kv\KV.g4 by ANTLR 4.1
package org.heng.jutils.http.httppostparam.kv.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class KVLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, PName=2, PVal=3, WS=4;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'='", "PName", "PVal", "WS"
	};
	public static final String[] ruleNames = {
		"T__0", "PName", "PVal", "WS"
	};


	public KVLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "KV.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 3: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2\6!\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\3\2\3\2\3\3\3\3\7\3\20\n\3\f\3\16\3\23\13\3\3\4"+
		"\7\4\26\n\4\f\4\16\4\31\13\4\3\5\6\5\34\n\5\r\5\16\5\35\3\5\3\5\2\6\3"+
		"\3\1\5\4\1\7\5\1\t\6\2\3\2\6\6\2/\60C\\aac|\7\2/\60\62;C\\aac|\4\2((?"+
		"?\5\2\13\f\17\17\"\"#\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2"+
		"\3\13\3\2\2\2\5\r\3\2\2\2\7\27\3\2\2\2\t\33\3\2\2\2\13\f\7?\2\2\f\4\3"+
		"\2\2\2\r\21\t\2\2\2\16\20\t\3\2\2\17\16\3\2\2\2\20\23\3\2\2\2\21\17\3"+
		"\2\2\2\21\22\3\2\2\2\22\6\3\2\2\2\23\21\3\2\2\2\24\26\n\4\2\2\25\24\3"+
		"\2\2\2\26\31\3\2\2\2\27\25\3\2\2\2\27\30\3\2\2\2\30\b\3\2\2\2\31\27\3"+
		"\2\2\2\32\34\t\5\2\2\33\32\3\2\2\2\34\35\3\2\2\2\35\33\3\2\2\2\35\36\3"+
		"\2\2\2\36\37\3\2\2\2\37 \b\5\2\2 \n\3\2\2\2\6\2\21\27\35";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}