package tw.kane.ken;

public class Util {
    public static String makeSpace(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < length; i++)
            stringBuilder.append(" ");
        return stringBuilder.toString();
    }

    public static String repeatString(String string, int time) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < time; i++)
            stringBuilder.append(string);
        return stringBuilder.toString();
    }
}
