import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.expression.functions.FastFunction;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.FormatFunction;
import cn.origincraft.magic.function.FunctionRegister;
import cn.origincraft.magic.utils.FunctionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class SimpleIDE extends JFrame {

    private JTextArea textArea;
    private JTextArea suggestionTextArea;

    public SimpleIDE() {
        setTitle("简单IDE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("文件");
        JMenuItem newMenuItem = new JMenuItem("新建");
        JMenuItem openMenuItem = new JMenuItem("打开");
        JMenuItem saveMenuItem = new JMenuItem("保存");
        JMenuItem exitMenuItem = new JMenuItem("退出");

        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });

        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // 这里你可以实现文件读取逻辑
                }
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // 这里你可以实现文件保存逻辑
                }
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        getContentPane().add(splitPane, BorderLayout.CENTER);

        textArea = new JTextArea();
        JScrollPane textScrollPane = new JScrollPane(textArea);
        splitPane.setLeftComponent(textScrollPane);

        suggestionTextArea = new JTextArea();
        suggestionTextArea.setEditable(false);
        suggestionTextArea.setBackground(UIManager.getColor("Panel.background"));
        JScrollPane suggestionScrollPane = new JScrollPane(suggestionTextArea);
        splitPane.setRightComponent(suggestionScrollPane);

        splitPane.setResizeWeight(0.7);
        splitPane.setContinuousLayout(true);

        textArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                onTextChanged();
            }
        });
    }

    private void onTextChanged() {
        MagicManager magicManager = new MagicManager();
        FunctionRegister.regDefault(magicManager);
        String currentText = textArea.getText();
        if (currentText.isEmpty()){
            StringBuilder sb=new StringBuilder();
            for (String functionsRealName : magicManager.getFunctionsRealNames()) {
                sb.append(functionsRealName).append("\n");
            }
            suggestionTextArea.setText(sb.toString());
            return;
        }
        String[] words = currentText.split("\n");

        if (words.length==0){
            return;
        }
        String word=words[words.length-1];
        java.util.List<String> now =  FunctionUtils.parseCode(word);
        if (now.isEmpty()){
            return;
        }
        String last=now.get(now.size()-1);
        StringBuilder stringBuilder=new StringBuilder();
        for (Map.Entry<String, List<String>> entry : magicManager.getFastExpression().getAliasesManager().getAliases().entrySet()) {
            String key = entry.getKey();
            List<String> value=entry.getValue();
            boolean flag= key.startsWith(last);
            for (String s : value) {
                if (s.startsWith(last)){
                    flag=true;
                    break;
                }
            }
            if (flag){
                stringBuilder.append("real:").append("\n  ").append(key).append("\n");
                stringBuilder.append("aliases:").append("\n");
                for (String s : value) {
                    stringBuilder.append("  ").append(s).append("\n");
                }
                FastFunction fastFunction= magicManager.getFastExpression().getFunctionManager().get(key);
                FormatFunction function= (FormatFunction) fastFunction;
                stringBuilder.append("usage:").append("\n");
                for (String s : function.getUsage()) {
                    stringBuilder.append("  ").append(s).append("\n");
                }
            }
        }
        suggestionTextArea.setText(stringBuilder.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SimpleIDE ide = new SimpleIDE();
                ide.setVisible(true);
            }
        });
    }
}
