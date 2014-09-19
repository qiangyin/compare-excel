package com.compare.excel;


import com.compare.bvo.UserBvo;
import com.compare.util.ClassRefUtil;
import com.compare.util.JxlTool;
import com.compare.xls.XlsReadAndWrite;
import com.compare.xls.XlsxReadAndWrite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.List;

public class CompareExcel extends JFrame implements ActionListener,
        PropertyChangeListener {
    private static final String[] USER_INFO = new String[]{"序号", "姓名", "身份证号码", "所属驾校", "待考时间", "参考次数", "最近考试时间", "联系电话", "备注"};
    private static final String ZIP_FILE_PATH = "/var/tmp/%s";
    //定义面板，并设置为网格布局，4行4列，组件水平、垂直间距均为3
    static JFrame f = new JFrame();
    private ProgressMonitor progressMonitor;
    //让窗体居中显示
    final Map<String, List<UserBvo>> zMap = new HashMap<String, List<UserBvo>>();
    final Map<String, List<UserBvo>> fMap = new HashMap<String, List<UserBvo>>();
    private JButton submit;
    private Task task;

    class Task extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
            setProgress(0);
            try {
//                    compareMap(zMap, fMap);
                int mapSize = zMap.size();
                int count = 1;
                Iterator iterator = zMap.entrySet().iterator();
                while (iterator.hasNext() && !isCancelled()) {
                    java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
                    String key = (String) entry.getKey();
                    List<UserBvo> value = (List<UserBvo>) entry.getValue();
                    Iterator fIterator = fMap.entrySet().iterator();
                    while (fIterator.hasNext()) {
                        java.util.Map.Entry fEntry = (java.util.Map.Entry) fIterator.next();
                        String fKey = (String) fEntry.getKey();
                        if (key.equals(fKey)) {
                            List<UserBvo> fValue = (List<UserBvo>) fEntry.getValue();
                            compareList(value, fValue, fKey);
                        }
                    }
                    setProgress(Math.min((count * 100 / mapSize), 100));
                    count += 1;
                    Thread.sleep(500);
                }
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            f.setEnabled(true);
            JOptionPane.showMessageDialog(f,
                    "比较成功 ！生成excel文件！", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public CompareExcel(String s) throws IOException {
        f.setBounds(600, 600, 600, 500);
        f.setLayout(null);     //定义窗体布局为边界布局
        JLabel title = new JLabel("交通驾校");
        JLabel label1 = new JLabel("总校名单：");
        final JLabel name1 = new JLabel();
        final JButton submit1 = new JButton("上传");
        JLabel label2 = new JLabel("我校名单：");
        final JLabel name2 = new JLabel();
        final JButton submit2 = new JButton("上传");
        submit = new JButton("比较");
        final JButton reset = new JButton("重置");
        f.setTitle(s);
        f.getContentPane().add(label1);
        f.getContentPane().add(label2);
        f.getContentPane().add(submit1);
        f.getContentPane().add(submit2);
        f.getContentPane().add(submit);
        f.getContentPane().add(reset);
        f.getContentPane().add(name1);
        f.getContentPane().add(name2);
        f.getContentPane().setBackground(new Color(255, 255, 255));
        title.setBounds(230, 0, 200, 60);
        title.setFont(new Font("宋体", Font.BOLD, 30));
//        File file = new File(this.getClass().getResource("/config/logo.jpg").getPath());
//        Image image = ImageIO.read(file);
//        f.setIconImage(image);
//        title.setIcon(new ImageIcon(this.getClass().getResource("/config/logo.jpg").getPath()));
        f.getContentPane().add(title);
        label1.setBounds(0, 200, 120, 60);
        label1.setFont(new Font("宋体", Font.BOLD, 20));
        name1.setBounds(120, 200, 240, 60);
        name1.setFont(new Font("宋体", Font.BOLD, 15));
        submit1.setBounds(480, 200, 120, 60);
        submit1.setFont(new Font("宋体", Font.BOLD, 20));
        label2.setBounds(0, 300, 320, 60);
        label2.setFont(new Font("宋体", Font.BOLD, 20));
        name2.setBounds(120, 300, 240, 60);
        name2.setFont(new Font("宋体", Font.BOLD, 15));
        submit2.setBounds(480, 300, 120, 60);
        submit2.setFont(new Font("宋体", Font.BOLD, 20));
        submit.setBounds(200, 400, 100, 60);
        submit.setFont(new Font("宋体", Font.BOLD, 20));
        reset.setBounds(300, 400, 100, 60);
        reset.setFont(new Font("宋体", Font.BOLD, 20));
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        submit.setActionCommand("比较");
        submit.addActionListener(this);

//        submit.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                if (zMap.isEmpty()) {
//                    JOptionPane.showMessageDialog(f,
//                            "请选择总校名单！", "Message", JOptionPane.ERROR_MESSAGE);
//                } else {
//                    if (fMap.isEmpty()) {
//                        JOptionPane.showMessageDialog(f,
//                                "请选择我校名单！", "Message", JOptionPane.ERROR_MESSAGE);
//                    }
//                    if (!zMap.isEmpty() && !fMap.isEmpty() && JOptionPane.showConfirmDialog(f, "确认比较吗") == 0) {
//                        compareMap(zMap, fMap);
//                        JOptionPane.showMessageDialog(f,
//                                "比较成功 ！生成excel文件！", "Message", JOptionPane.INFORMATION_MESSAGE);
//                    }
//                }
//            }
//        });
        submit1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                if (jfc.showOpenDialog(f) == JFileChooser.APPROVE_OPTION) {
                    zMap.clear();
                    try {
                        if (jfc.getSelectedFile().getName().contains(".xls")) {
                            if (jfc.getSelectedFile().getName().contains(".xlsx")) {
                                mapToMap(zMap, XlsxReadAndWrite.readXlsx(jfc.getSelectedFile().getAbsolutePath()));
                            }
                            if (jfc.getSelectedFile().getName().contains(".xls") && !jfc.getSelectedFile().getName().contains(".xlsx")) {
                                mapToMap(zMap, XlsReadAndWrite.readXls(jfc.getSelectedFile().getAbsolutePath()));
                            }
                            name1.setText(jfc.getSelectedFile().getName());
                        } else {
                            JOptionPane.showMessageDialog(f,
                                    "请选择正确文件格式！", "Message", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        submit2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                if (jfc.showOpenDialog(f) == JFileChooser.APPROVE_OPTION) {
                    fMap.clear();
                    try {
                        if (jfc.getSelectedFile().getName().contains(".xls")) {
                            if (jfc.getSelectedFile().getName().contains(".xlsx")) {
                                mapToMap(fMap, XlsxReadAndWrite.readXlsx(jfc.getSelectedFile().getAbsolutePath()));
                            }
                            if (jfc.getSelectedFile().getName().contains(".xls") && !jfc.getSelectedFile().getName().contains(".xlsx")) {
                                mapToMap(fMap, XlsReadAndWrite.readXls(jfc.getSelectedFile().getAbsolutePath()));
                            }
                            name2.setText(jfc.getSelectedFile().getName());
                        } else {
                            JOptionPane.showMessageDialog(f,
                                    "请选择正确文件格式！", "Message", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(f, "确认重置吗") == 0) {
                    name1.setText("");
                    name2.setText("");
                    zMap.clear();
                    fMap.clear();
                    JOptionPane.showMessageDialog(f,
                            "重置成功 ！", "Message", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public void compareList(java.util.List<UserBvo> list1, List<UserBvo> list2, String name) {
        List<UserBvo> existList = new ArrayList<UserBvo>();
        for (UserBvo userBvo1 : list1) {
            for (UserBvo userBvo2 : list2) {
                if (userBvo1.getIdCard().equals(userBvo2.getIdCard())) {
                    existList.add(userBvo2);
                }
            }
        }
        list2.removeAll(existList);
        makeExcel(existList, USER_INFO, name + "_存在", 0, name + "_存在");
        makeExcel(list2, USER_INFO, name + "_不存在", 0, "" + name + "_不存在");
    }

    public void compareMap(Map<String, List<UserBvo>> zMap, Map<String, List<UserBvo>> fMap) {
        Iterator iterator = zMap.entrySet().iterator();
        while (iterator.hasNext()) {
            java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            List<UserBvo> value = (List<UserBvo>) entry.getValue();
            Iterator fIterator = fMap.entrySet().iterator();
            while (fIterator.hasNext()) {
                java.util.Map.Entry fEntry = (java.util.Map.Entry) fIterator.next();
                String fKey = (String) fEntry.getKey();
                if (key.equals(fKey)) {
                    List<UserBvo> fValue = (List<UserBvo>) fEntry.getValue();
                    compareList(value, fValue, fKey);
                }
            }
        }
    }

    public void mapToMap(Map<String, List<UserBvo>> destMap, Map<String, List<UserBvo>> oriMap) {
        Iterator iterator = oriMap.entrySet().iterator();
        while (iterator.hasNext()) {
            java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            List<UserBvo> value = (List<UserBvo>) entry.getValue();
            destMap.put(key, value);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (zMap.isEmpty()) {
            JOptionPane.showMessageDialog(f,
                    "请选择总校名单！", "Message", JOptionPane.ERROR_MESSAGE);
        } else {
            if (fMap.isEmpty()) {
                JOptionPane.showMessageDialog(f,
                        "请选择我校名单！", "Message", JOptionPane.ERROR_MESSAGE);
            }
            if (!zMap.isEmpty() && !fMap.isEmpty() && JOptionPane.showConfirmDialog(f, "确认比较吗") == 0) {
                progressMonitor = new ProgressMonitor(CompareExcel.this,
                        "正在生成 excel 请稍后",
                        "", 0, 100);
                progressMonitor.setProgress(0);
                task = new Task();
                task.addPropertyChangeListener(this);
                task.execute();
                f.setEnabled(false);
            }
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressMonitor.setProgress(progress);
            String message =
                    String.format("完成 %d%%.\n", progress);
            progressMonitor.setNote(message);
            if (progressMonitor.isCanceled() || task.isDone()) {
                Toolkit.getDefaultToolkit().beep();
                if (progressMonitor.isCanceled()) {
                    task.cancel(true);
                } else {
                }
                f.setEnabled(true);
            }
        }
    }

    private void makeExcel(List<?> bvos, String[] fieldNameArray, String filename, int start, String date) {
        OutputStream os = null;
        try {
            List<String[]> data = new ArrayList<String[]>();
            for (Object bvo : bvos) {
                String[] tmp = new String[fieldNameArray.length];
                List<String> values = ClassRefUtil.getFieldValueList(bvo, start);
                for (int i = 0; i < fieldNameArray.length; i++) {
                    tmp[i] = values.get(i);
                }
                data.add(tmp);
            }

            String folderPath = String.format(ZIP_FILE_PATH, date);
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            filename = folderPath + File.separator + filename + ".xls";

            File excelFile = new File(filename);

            os = new FileOutputStream(excelFile);
            JxlTool.makeExcelWorkBook(os, filename, fieldNameArray, data);
        } catch (Exception e) {
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new CompareExcel("比较附件内容仅支持xls和xlsx文件");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}