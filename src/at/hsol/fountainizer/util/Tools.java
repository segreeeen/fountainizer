package at.hsol.fountainizer.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class Tools {
    
    public static String bbToString(ByteBuffer buffer, Charset CHARSET) {
	CharsetDecoder decoder = CHARSET.newDecoder();
	CharBuffer charBuffer;
	try {
	    charBuffer = decoder.decode(buffer);
	    return charBuffer.toString();
	} catch (CharacterCodingException e) {
	    return null;
	}
    }

    public static ByteBuffer stringToBuffer(String s, Charset CHARSET) {
	CharBuffer c = CharBuffer.wrap(s);
	ByteBuffer b = CHARSET.encode(c);
	return b;
    }
}
