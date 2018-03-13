package com.cpssoft.dev.zweb.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StringUtil {


	public static final int PAD_LEFT = -1;

	public static final int PAD_BOTH = 0;

	public static final int PAD_RIGHT = 1;

	private static final String INVALID_EMAIl_CHAR = "[,;!#$%&@\"'*+-/=?^_`{|}~.\\\\]+";

	/**
	 * Generates unique ID based on timestamp, thread ID, random seed and random
	 * double. Each part is converted to hexadecimal, padded left with zeros to
	 * a length of 8 chars and then concatentated to each other.
	 * 
	 * @return Unique 32-char hexadecimal string.
	 */
	public static synchronized String getUniqueID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * Pad the given string with the given pad value to the given length in the
	 * given direction. Valid directions are StringX.PAD_LEFT, StringX.PAD_BOTH
	 * and StringX.PAD_RIGHT. When using StringX.PAD_BOTH, padding left has
	 * precedence over padding right when difference between string's length and
	 * the given length is odd.
	 * 
	 * @param string
	 *            The string to be padded.
	 * @param pad
	 *            The value to pad the given string with.
	 * @param length
	 *            The length to pad the given string to.
	 * @param direction
	 *            The direction to pad the given string to.
	 * @return The padded string.
	 * @throws IllegalArgumentException
	 *             When invalid direction is given.
	 */
	public static String pad(String string, String pad, int length, int direction) throws IllegalArgumentException {
		switch (direction) {
		case PAD_LEFT:
			while (string.length() < length) {
				string = pad + string;
			}
			break;

		case PAD_RIGHT:
			while (string.length() < length) {
				string += pad;
			}
			break;

		case PAD_BOTH:
			int right = (length - string.length()) / 2 + string.length();
			string = pad(string, pad, right, PAD_RIGHT);
			string = pad(string, pad, length, PAD_LEFT);
			break;

		default:
			throw new IllegalArgumentException(
					"Invalid direction, must be one of " + "StringX.PAD_LEFT, StringX.PAD_BOTH or StringX.PAD_RIGHT.");
		}

		return string;
	}

	public static String getStringBetween(String string, String begin, String end) {
		int startIndex = string.indexOf(begin) + begin.length();
		if (startIndex <= 0) {
			startIndex = 0;
		}

		int endIndex = string.indexOf(end);
		if (endIndex < startIndex) {
			endIndex = startIndex;
		}

		String result = string.substring(startIndex, endIndex);
		return result;
	}

	public static String getStringBetween(String string, String begin, String end, int fromIndex) {
		int startIndex = string.indexOf(begin, fromIndex) + begin.length();
		if (startIndex <= 0) {
			startIndex = 0;
		}

		int endIndex = string.indexOf(end);
		if (endIndex < startIndex) {
			endIndex = startIndex;
		}

		String result = string.substring(startIndex, endIndex);
		return result;
	}

	public static List<String> getListStringBetween(String string, String begin, String end) {
		List<String> result = new ArrayList<String>();
		int index = 0;
		while ((index = string.indexOf(begin, index)) != -1) {
			result.add(getStringBetween(string, begin, end));
			index++;
		}
		return result;
	}

	public static String getStringBefore(String string, String before) {
		int pos = string.indexOf(before);
		if (pos == -1) {
			return string;
		} else {
			return string.substring(0, pos);
		}
	}

	public static String getStringAfter(String string, String after) {
		int startPos = string.indexOf(after);
		if (startPos > -1) {
			return string.substring(startPos + after.length());
		} else {
			return "";
		}
	}

	public static String getFirstWord(String string) {
		return getStringBefore(string, " ");
	}

	public static int indexOfStringIC(String string, String arg) {
		return string.toLowerCase().indexOf(arg.toLowerCase());
	}

	public static boolean stringContainsIC(String string, String arg) {
		return indexOfStringIC(string, arg) > -1;
	}

	public static String getStringBeforeIC(String string, String before) {
		int pos = indexOfStringIC(string, before);
		if (pos == -1) {
			return string;
		} else {
			return string.substring(0, pos);
		}
	}

	public static String getStringAfterIC(String string, String after) {
		int startPos = indexOfStringIC(string, after);
		if (startPos > -1) {
			return string.substring(startPos + after.length());
		} else {
			return "";
		}
	}

	public static String getStringIC(String string, String arg) {
		int pos = indexOfStringIC(string, arg);
		if (pos > -1) {
			return string.substring(pos, pos + arg.length());
		} else {
			return "";
		}
	}

	public static String removeNonNumericCharacter(String string) {
		if (string == null) {
			return "";
		}

		return string.replaceAll("[^0-9]", "");
	}

	public static String substringNumeric(String string, int beginIndex, int endIndex) {
		if (string == null) {
			return "";
		}

		String numeric = StringUtil.removeNonNumericCharacter(string);
		if (numeric.length() > beginIndex) {
			int end = Math.min(endIndex, numeric.length());
			return numeric.substring(beginIndex, end);
		} else {
			return "";
		}
	}

	public static String camelCaseToUnderscoreSeparated(String s) {
		String result = "";
		for (int index = 0; index < s.length(); index++) {
			char c = s.charAt(index);
			if (index == 0) {
				result = result + Character.toLowerCase(c);
			} else if (Character.isUpperCase(c)) {
				result = result + "_" + Character.toLowerCase(c);
			} else {
				result = result + c;
			}
		}
		return result;
	}

	public static String dashSeparatedToCamelCase(String s) {
		StringBuilder result = new StringBuilder();
		int offset = 0;
		int length = s.length();
		while (offset < length) {
			int pos = s.indexOf('-', offset);
			if (pos > -1) {
				result.append(s.substring(offset, pos));
				char c = s.charAt(pos + 1);
				result.append(Character.toUpperCase(c));
				offset = pos + 2;
			} else {
				result.append(s.substring(offset));
				break;
			}
		}
		return result.toString();
	}

	public static String underscoreSeparatedToCamelCase(String s) {
		StringBuilder result = new StringBuilder();
		s = s.toLowerCase();
		int offset = 0;
		int length = s.length();
		while (offset < length) {
			int pos = s.indexOf('_', offset);
			if (pos > -1) {
				result.append(s.substring(offset, pos));
				char c = s.charAt(pos + 1);
				result.append(Character.toUpperCase(c));
				offset = pos + 2;
			} else {
				result.append(s.substring(offset));
				break;
			}
		}
		return result.toString();
	}

	public static int countChar(String string, char charToCount) {
		int result = 0;

		int offset = 0;
		int charIndex = string.indexOf(charToCount, offset);
		while (charIndex >= 0) {
			result++;
			offset = charIndex + 1;
			charIndex = string.indexOf(charToCount, offset);
		}
		return result;
	}

	public static String quoteString(String source, char quote) {
		if (source == null)
			source = "";

		// Normally, the quoted string is two characters longer than the source
		// string (because of start quote and end quote).
		StringBuffer quoted = new StringBuffer(source.length() + 2);
		quoted.append(quote);
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			// if the character is a quote, escape it with an extra quote
			if (c == quote)
				quoted.append(quote);
			quoted.append(c);
		}
		quoted.append(quote);
		return quoted.toString();
	}

	public static String quoteString(String source) {
		return quoteString(source, '\"');
	}
	
	public static boolean isBlank(String value) {
		if (value == null || value.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static String joinAsMultiLine(String... source) {
		StringBuilder result = new StringBuilder();
		for (String s : source) {
			if (!isBlank(s)) {
				if (result.length() > 0) {
					result.append('\n');
				}
				result.append(s);
			}
		}
		return result.toString();
	}

	public static String joinAsMultiLine(Iterable<String> source) {
		StringBuilder result = new StringBuilder();

		for (String s : source) {
			if (!isBlank(s)) {
				if (result.length() > 0) {
					result.append('\n');
				}
				result.append(s);
			}
		}
		return result.toString();
	}

	public static String removeUncompatibleEmailCharacter(String recipient) {
		return recipient.replaceAll(INVALID_EMAIl_CHAR, "");
	}

	/**
	 * Trim string into contain only one space(remove tab, newline and double space)
	 * e.g "Saya   suka  \n
	 * lagi lagi \t	lagi" will be trimmed into
	 *  "Saya suka lagi lagi lagi"
	 */
	public static String changeToOneSpacePerWord(String word) {
		return word.replaceAll("\\s{1,}", " ");
	}
	
	public static String getFirstNumericPart(String string) {
		if (string == null) {
			return "";
		}

		String s = string.trim();
		int len = s.length();
		int start = -1;
		int end = -1;
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (Character.isDigit(c)) {
				if (start == -1) {
					start = i;
				}
			} else {
				if (start > -1) {
					end = i;
					break;
				}
			}
		}
		
		if (start > -1) {
			if (end == -1) {
				end = s.length();
			}
			return s.substring(start, end) ;
		} else {
			return "";
		}
	}
	
	public static String maxLength(String string, int max) {
		if (string == null) {
			return null;
		}
		
		if (string.length() > max) {
			return string.substring(0, 20);
		} else {
			return string;
		}
	}
}
