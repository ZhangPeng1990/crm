package uk.co.quidos.common.util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author peng.shi
 */
public final class RandomStringUtil
{

	private static final String[] randomLowerLetter = new String[]
	{ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

	private static final String[] randomUpperLetter = new String[]
	{ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public static final String[] randomNumber = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	/**
	 * 获取随即字符串
	 * @param length
	 * @return
	 */
	public static final String getString(int length)
	{
		if (length <= 0)
		{
			length = 6;
		}
		// merge array
		List<String> randomLowerLetterList = Arrays.asList(randomLowerLetter);
		List<String> randomUpperLetterList = Arrays.asList(randomUpperLetter);
		List<String> randomNumberList = Arrays.asList(randomNumber);
		List<String> randomList = new ArrayList<String>();
		randomList.addAll(randomLowerLetterList);
		randomList.addAll(randomUpperLetterList);
		randomList.addAll(randomNumberList);
		// generate random length number
		int listLength = randomList.size();
		List<Integer> indexList = new ArrayList<Integer>();
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < length; i++)
		{
			int n = random.nextInt(listLength - 1);
			indexList.add(n);
		}
		String randomPassword = "";
		for (Integer indexNumber : indexList)
		{
			randomPassword = randomPassword + randomList.get(indexNumber);
		}
		return randomPassword;
	}

	public static void main(String[] args)
	{
		System.out.println(getString(8));
	}

}
