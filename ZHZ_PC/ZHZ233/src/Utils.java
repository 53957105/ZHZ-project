import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	
	/**
	 * �Ƿ���ַ���
	 * @param str
	 * @return
	 */
	public static boolean isNullString(String str) {
		if(str == null || str.isEmpty() || str=="" || str.length()==0) {
			return true;
		}
		return false;
	}
	
	/**
	 * �Ƿ�Ϊ����
	 * @param str
	 * @return
	 */
	public static boolean IsNumber(String str) {
	       String regex = "^[0-9]*$";
	       return match(regex, str);
	}
	
	/**
     * 6-12λ �˺� �ֻ��� ���� ��֤
     * @param str
     * @return
     */
    public static boolean isLogonInfo(String str) {
        String regex = "^[a-zA-Z0-9_]{6,12}$";
        return match(regex, str);
    }
	 
	private static boolean match(String regex, String str) {
	       // ����������ʽ
	       Pattern ptn = Pattern.compile(regex);
	       Matcher mthr = ptn.matcher(str);
	       // �ַ����Ƿ���������ʽ��ƥ��
	       return mthr.matches();//���ؽ��
	}
}
