
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.util.*;

/**
 * @Author MT
 * @Date 2021/11/22 14:45
 * @Description: 发送微信消息
 */


public class MsgSend {
    //执行次数
    private static int count = 0;

    public static void main(String[] args) {

            System.out.println("开始执行----------");
            Scanner scanner = new Scanner(System.in);
            System.out.println("请设置需发送的好友昵称：");
            String friendNickName = scanner.next();
            System.out.println("请设置需发送的内容：");
            String content = scanner.next();
            System.out.println("请输入发送时间的小时：");
            Integer hour = scanner.nextInt();
            System.out.println("请输入发送时间的分钟：");
            Integer minute = scanner.nextInt();
            System.out.println("请输入发送时间的秒：");
            Integer second = scanner.nextInt();
            System.out.println("您设定的发送时间为" + hour + "时" + minute + "分" + second + "秒！！！");
            //执行时间
            Date date = getDate(hour, minute, second);
            //Timer定时器
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    searchPerson(friendNickName, content);
                    count++;
                    System.out.println("共发送了" + count + "天！！！");
                }
            };
            //延迟date毫秒执行一次发送方法
            timer.schedule(task, date);
        }


    //获取执行时间
    public static Date getDate(Integer hour, Integer minute, Integer second) {
        //设置执行时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);//每天
        calendar.set(year, month, day, hour, minute, second);
        Date date = calendar.getTime();
        //如果启动时间超过执行时间，则明天执行
        if (date.before(new Date())) {
            date = addDay(date, 1);
        }
        return date;
    }

    // 增加或减少天数
    public static Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

    private static void searchPerson(String friendNickName, String content) {
        // 创建Robot对象
        Robot robot = getRobot();
        //打开微信 Ctrl+Alt+W
        assert robot != null;
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_W);
        //释放Ctrl按键，像Ctrl，退格键，删除键这样的功能性按键，在按下后一定要释放
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_ALT);
        // 该延迟不能少，否则无法搜索
        robot.delay(1000);
        // Ctrl + F 搜索指定好友
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_F);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        // 将好友昵称发送到剪切板
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(friendNickName);
        clip.setContents(tText, null);
        // 以下两行按下了ctrl+v，完成粘贴功能
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(1000);
        // 发送消息
        try {
            sendMsg(content);
            //关闭微信
            assert robot != null;
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_W);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sendMsg(String content) throws InterruptedException {
        if (!content.equals("发送默认的信息")) {
            sendOneMsg(content);
        } else {
            String[] msgArr = {
                    "测试消息",

            };
            ArrayList<String> msgList = new ArrayList<>();
            int i = 1;
            while (i <= 10) {
                msgList.add("测试消息");
                i++;
            }
            for (String item : msgList) {
                sendOneMsg(item);
            }
            Thread.sleep(2000);

            sendOneMsg("测试消息");

        }

    }

    private static Robot getRobot() {
        // 创建Robot对象
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return robot;
    }

    private static void sendOneMsg(String msg) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText;
        //创建Robot对象
        Robot robot = getRobot();
        //延迟十秒，主要是为了预留出打开窗口的时间，括号内的单位为毫秒


            if (null != robot) {
                robot.delay(500);
                tText = new StringSelection(msg);
                clip.setContents(tText, null);
                // 按下了ctrl+v，完成粘贴功能
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.delay(500);
                //回车发送消息
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.delay(500);
            }


    }
}
