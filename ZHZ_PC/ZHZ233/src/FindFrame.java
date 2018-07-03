import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinDef.HWND;

public class FindFrame {
	
	public static boolean judgeWeGameFrame() {
		String windowName = "WeGame";  
        HWND hwnd = User32.INSTANCE.FindWindow(null,windowName);  
        if (hwnd==null) {
        	return false;  
        }
        else {  
            System.out.println("Hit!");  
            boolean showed = User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_RESTORE );  
            System.out.println(windowName+(showed?"窗口之前可见.":"窗口之前不可见.")); 
            return true;
        }
	}
	
	public static boolean judgeSteamFrame() {
		String windowName = "Steam 登录";  
        HWND hwnd = User32.INSTANCE.FindWindow("vguiPopupWindow",windowName);  
        if (hwnd==null) {
        	System.out.println("Miss!"); 
        	JOptionPane.showMessageDialog(new JPanel(), "请打开Steam", "警告",JOptionPane.WARNING_MESSAGE); 
        	return false;
        } else {  
            //System.out.println("Hit!");  
            boolean showed = User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_RESTORE );  
            System.out.println(windowName+(showed?"窗口之前可见.":"窗口之前不可见."));  
        } 
        int foreThreadId=User32.INSTANCE.GetWindowThreadProcessId(hwnd,null);//获得Steam的句柄
        System.out.println("句柄ID:"+foreThreadId);
        //获取当前活跃窗口
        HWND wnd;
        wnd=User32.INSTANCE.GetForegroundWindow();
        int ForeThreadId=User32.INSTANCE.GetWindowThreadProcessId(wnd,null);
        System.out.println("句柄ID:"+ForeThreadId);
        //判断输入点是否为Steam
        if(foreThreadId==ForeThreadId) {
        	return true;
        }else {
        	return false;
        }
	}
}
