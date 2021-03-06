package net.orangepeels.utils;

import java.io.*;

public class MarkDownTools {

    private MarkDownTools() {
        // 私有构造方法，防止创建工具类实例
    }

    /**
     * 工具类唯一的public入口方法
     *
     * @param file markdown文件
     * @return html代码
     * @throws IOException
     */
    public static String getHTML(File file) throws IOException {
        String strHTML;
        InputStream input = new FileInputStream(file);
        strHTML = getFistLab(input);
        return strHTML;
    }

    public static String getHTML(InputStream input) throws IOException {
        String strHTML;
        strHTML = getFistLab(input);
        return strHTML;
    }

    public static String getHTMl(String[] textList) throws IOException {
        String strHTML;
        strHTML = getFistLab(textList);
        return strHTML;
    }

    /**
     * 先把markdown转换成HTML标签
     *
     * @param input 输入流
     * @return
     * @throws IOException
     */
    private static String getFistLab(InputStream input) throws IOException {
        StringBuilder toLab = new StringBuilder();
        String tag;
        boolean ulFlag = false;
        boolean olFlag = false;
        boolean codeFlag = false;
        InputStreamReader reader = new InputStreamReader(input);
        BufferedReader br = new BufferedReader(reader); // markdown读取器
        String temp;
        while ((temp = br.readLine()) != null) {
            if (temp.length() < 1)
                continue;
            tag = temp.substring(0, 1);
            //解决ul标签的问题
            if ("-".equals(tag) && !ulFlag) {
                toLab.append("<ul>\r\n");
                ulFlag = true;
            }
            if (!"-".equals(tag) && ulFlag) {
                toLab.append("</ul>\r\n");
                ulFlag = false;
            }
            //解决<ol><li>问题
            if (MathTools.isNumber(tag.toCharArray()[0]) && !olFlag) {
                toLab.append("<ol>\r\n");
                olFlag = true;
            }
            if (!MathTools.isNumber(tag.toCharArray()[0]) && olFlag) {
                toLab.append("</ol>\r\n");
                olFlag = false;
            }
            //解决<pre><code>标签问题
            if ("`".equals(tag) && !codeFlag) {
                if ("```".equals(temp.substring(0, 3))) {
                    toLab.append("<pre>\r\n<code>\r\n");
                    codeFlag = true;
                    continue;
                }
            }
            if ("`".equals(tag) && codeFlag) {
                if ("```".equals(temp.substring(0, 3))) {
                    toLab.append("</code>\r\n</pre>\r\n");
                    codeFlag = false;
                    continue;
                }
            }
            //添加对应的标签
            toLab.append(getHtmlTag(temp, tag));
        }
        if (ulFlag) { //如果到末尾了标签未结束，加上结束标签
            toLab.append("</ul>\r\n");
        }
        if (codeFlag) { //如果到末尾了标签未结束，加上结束标签
            toLab.append("</code>\r\n</pre>\r\n");
        }
        return toLab.toString();
    }

    private static String getFistLab(String[] input) throws IOException {
        StringBuilder toLab = new StringBuilder();
        String tag;
        boolean ulFlag = false;
        boolean olFlag = false;
        boolean codeFlag = false;
        for (String temp : input) {
            if (temp.length() < 1)
                continue;
            tag = temp.substring(0, 1);
            //解决ul标签的问题
            if ("-".equals(tag) && !ulFlag) {
                toLab.append("<ul>\r\n");
                ulFlag = true;
            }
            if (!"-".equals(tag) && ulFlag) {
                toLab.append("</ul>\r\n");
                ulFlag = false;
            }
            //解决<ol><li>问题
            if (MathTools.isNumber(tag.toCharArray()[0]) && !olFlag) {
                toLab.append("<ol>\r\n");
                olFlag = true;
            }
            if (!MathTools.isNumber(tag.toCharArray()[0]) && olFlag) {
                toLab.append("</ol>\r\n");
                olFlag = false;
            }
            //解决<pre><code>标签问题
            if ("`".equals(tag) && !codeFlag) {
                if ("```".equals(temp.substring(0, 3))) {
                    toLab.append("<pre>\r\n<code>\r\n");
                    codeFlag = true;
                    continue;
                }
            }
            if ("`".equals(tag) && codeFlag) {
                if ("```".equals(temp.substring(0, 3))) {
                    toLab.append("</code>\r\n</pre>\r\n");
                    codeFlag = false;
                    continue;
                }
            }
            //添加对应的标签
            toLab.append(getHtmlTag(temp, tag));
        }
        if (ulFlag) { //如果到末尾了标签未结束，加上结束标签
            toLab.append("</ul>\r\n");
        }
        if (codeFlag) { //如果到末尾了标签未结束，加上结束标签
            toLab.append("</code>\r\n</pre>\r\n");
        }
        return toLab.toString();
    }

    /**
     * 对最基本的单元标签进行操作
     *
     * @param temp 行本文
     * @param tag  标签
     * @return
     */
    private static String getHtmlTag(String temp, String tag) {
        StringBuilder toLab = new StringBuilder();
        switch (tag) {
            case "#":
                toLab.append(hTag(temp)); // 转换 h 标签
                break;
            case "-":
                toLab.append(liTag(temp)); // 转换 ul->li 标签
                break;
            default:
                if (MathTools.isNumber(tag.toCharArray()[0])) { // 转换 ol->li 标签
                    toLab.append(liTag(temp));
                } else {
                    toLab.append(codeTag(temp));
                }

        }
        return toLab.toString();
    }

    //处理code标签中的特殊字符比如"<"  ">"
    private static String codeTag(String temp) {
        StringBuilder toLab = new StringBuilder();
        char[] charArray = temp.toCharArray();
        for (char item :
                charArray) {
            if ('<' == item) {
                toLab.append("&lt;");
                continue;
            }
            if ('>' == item) {
                toLab.append("&gt;");
                continue;
            }
            toLab.append(item);
        }
        toLab.append("\r\n");
        return toLab.toString();
    }

    //对应li列表
    private static String liTag(String temp) {
        String tab = "";  // 组合的标签
        String start, end; // 记录标签头与标签尾
        start = "<li>";
        end = "</li>";
        tab += start;
        tab += "\r\n";
        tab += temp.substring(temp.indexOf(" "), temp.length());
        tab += "\r\n";
        tab += end;
        tab += "\r\n";
        return tab;
    }

    //生成对应的h1~h6标题
    private static String hTag(String temp) {
        String tab = "";  // 组合的标签
        String start, end; // 记录标签头与标签尾
        int hNum = temp.indexOf(" "); //看有几个井号
        start = "<h" + hNum + ">";
        end = "</h" + hNum + ">";
        tab += start;
        tab += "\r\n";
        tab += temp.substring(temp.indexOf(" "), temp.length());
        tab += "\r\n";
        tab += end;
        tab += "\r\n";
        return tab;
    }
}
