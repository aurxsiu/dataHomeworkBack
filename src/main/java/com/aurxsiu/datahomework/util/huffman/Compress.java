package com.aurxsiu.datahomework.util.huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.*;

//文件压缩

public class Compress {

    public static void main(String[] args) {

        String[] zipfiles = {

                "D:\\123.txt"

    };

        String[] dstfiles = {

            "D:\\123.zip"

    };

        for(int i = 0; i <  zipfiles.length; i++) {

            System.out.println("压缩文本文件中...");

            zipFile(zipfiles[i], dstfiles[i]);

        }
    }

    static Map<Byte, String> huffmanCodes = new HashMap<Byte, String>();// 哈夫曼编码表

    static Node huffmantree; // 哈夫曼树的根

    static int endLen;// 记录最后一个字节的二进制串的长度

    public static void zipFile(byte[] content, File targetFile){
        ObjectOutputStream oos = null;

        FileInputStream is = null;

        try {
            // for (int i = 0; i < b.length; i++) {
            //     System.out.print(b[i]+" ");
            // }

            byte[] huffmanBytes = huffmanZip(content);

            // for (int i = 0; i < huffmanBytes.length; i++) {
            //     System.out.println(huffmanBytes[i]);
            // }

            oos = new ObjectOutputStream(new FileOutputStream(targetFile));

            // 对象序列化

            oos.writeObject(huffmanBytes);// 将编码后的字节数组存入文件

            oos.writeObject(huffmanCodes);// 将哈夫曼表也存入文件

            oos.writeObject(endLen);// 最后一个要处理的字节单位长度(可能不足八位)

            System.out.println("压缩成功!");

            System.out.println("压缩比为:");

            System.out.println((double) huffmanBytes.length / content.length); // 定义为哈夫曼编码表的长度/字符构成字节数组长度

            // System.out.println("WPL为: ");

            // System.out.println(Wpl(huffmantree));

            // System.out.println("哈弗曼编码表为：");

            // for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {

            //     System.out.println(entry.getKey() + ":" + entry.getValue());

            // }

            System.out.println();

            // System.out.println("哈弗曼编码为：");

            // int count = 0;

            // for (byte a : b) {

            //     System.out.print(huffmanCodes.get(a));

            //     count++;

            //     while (count > 100) {

            //         System.out.println();

            //         count = 0;// 使打印出来的编码更加的立体

            //     }

            // }
            // System.out.println();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        } finally {

            try {

                is.close();

                oos.close();

            } catch (Exception e) {

                System.out.println(e.getMessage());

            }

        }
    }

    /**
     * 
     * @param srcFile 解压文件源路径
     * @param dstFile 解压后编码文件路径
     */
    public static void zipFile(String srcFile, String dstFile) {

        ObjectOutputStream oos = null;

        FileInputStream is = null;

        try {

            is = new FileInputStream(srcFile);

            byte[] b = new byte[is.available()];

            is.read(b);

            // for (int i = 0; i < b.length; i++) {
            //     System.out.print(b[i]+" ");
            // }

            byte[] huffmanBytes = huffmanZip(b);

            // for (int i = 0; i < huffmanBytes.length; i++) {
            //     System.out.println(huffmanBytes[i]);
            // }

            oos = new ObjectOutputStream(new FileOutputStream(dstFile));

            // 对象序列化

            oos.writeObject(huffmanBytes);// 将编码后的字节数组存入文件

            oos.writeObject(huffmanCodes);// 将哈夫曼表也存入文件

            oos.writeObject(endLen);// 最后一个要处理的字节单位长度(可能不足八位)

            System.out.println("压缩成功!");

            System.out.println("压缩比为:");

            System.out.println((double) huffmanBytes.length / b.length); // 定义为哈夫曼编码表的长度/字符构成字节数组长度

            // System.out.println("WPL为: ");

            // System.out.println(Wpl(huffmantree));

            // System.out.println("哈弗曼编码表为：");

            // for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {

            //     System.out.println(entry.getKey() + ":" + entry.getValue());

            // }

            System.out.println();

            // System.out.println("哈弗曼编码为：");

            // int count = 0;

            // for (byte a : b) {

            //     System.out.print(huffmanCodes.get(a));

            //     count++;

            //     while (count > 100) {

            //         System.out.println();

            //         count = 0;// 使打印出来的编码更加的立体

            //     }

            // }
            // System.out.println();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        } finally {

            try {

                is.close();

                oos.close();

            } catch (Exception e) {

                System.out.println(e.getMessage());

            }

        }

    }

    /**
     * 哈夫曼编码压缩
     * @param bytes 读入的文件中字符的ASCII码构成的字节数组
     * @return 压缩后的字节数组
     */
    

    static byte[] huffmanZip(byte[] bytes) {

        List<Node> nodes = getNodes(bytes);

        // 哈夫曼树

        huffmantree = createHuffmanTree(nodes);

        // 哈夫曼编码表

        Map<Byte, String> huffmanCodes = getCodes(huffmantree);

        byte[] zip = zip(bytes, huffmanCodes);

        return zip;

    }

    /**
     * 
     * @param bytes 字符构成的字节数组
     * @param huffmanCodes 哈夫曼编码表
     * @return 压缩后的字节数组
     */

    static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {

        StringBuilder stringBuilder = new StringBuilder();

        for (byte b : bytes) {

            stringBuilder.append(huffmanCodes.get(b)); // 获得由所有字符组成的哈夫曼编码

        }

        int len;

        // 获取新的字节数组长度

        if (stringBuilder.length() % 8 == 0) {// 如果编码长度是八的倍数

            len = stringBuilder.length() / 8;// 新字节数组的长度为哈夫曼编码长度/8

        } else {

            len = stringBuilder.length() / 8 + 1;// 如果不是则最后一组字节算一个字节数组

        }

        endLen = stringBuilder.length() % 8;

        byte[] by = new byte[len];

        int index = 0;

        // 以8作为一个字节单位处理

        for (int i = 0; i < stringBuilder.length(); i += 8) {

            String strByte;

            if (i + 8 > stringBuilder.length()) {// 如果到了最后一组编码不足8位

                strByte = stringBuilder.substring(i);// 截取剩下的编码

                by[index] = (byte) Integer.parseInt(strByte, 2);// 将二进制的strByte字符串转化为十进制的字节

                index++;

            } else {// 如果还没有到最后一组,则每8个一组

                strByte = stringBuilder.substring(i, i + 8);

                by[index] = (byte) Integer.parseInt(strByte, 2);

                index++;

            }

        }

        return by;

    }

    /**
     * 
     * @param node 哈夫曼树存储的结点
     * @param code 结点新加的编码 取值为0或1
     * @param stringBuilder 结点之前存储的编码值
     */


    static void getCodes(Node node, String code, StringBuilder stringBuilder) {

        StringBuilder builder = new StringBuilder(stringBuilder);

        builder.append(code);

        if (node != null) {

            if (node.data == null) { // 如果不是叶子节点

                getCodes(node.left, "0", builder);

                getCodes(node.right, "1", builder);

            } else {

                huffmanCodes.put(node.data, builder.toString()); // 将编码存入哈夫曼编码表

            }

        }

    }

    /**
     * 
     * @param root 哈夫曼树的根节点
     * @return 哈夫曼编码表
     */


    static Map<Byte, String> getCodes(Node root) {

        StringBuilder stringBuilder = new StringBuilder(); // 存储编码

        if (root == null) {

            return null;

        }

        getCodes(root.left, "0", stringBuilder); // 向左进行编码

        getCodes(root.right, "1", stringBuilder); // 向右进行编码

        return huffmanCodes;

    }

    /**
     * 生成哈夫曼树
     * @param nodes 文件字符构成的结点序列
     * @return 哈夫曼树的根节点
     */


    static Node createHuffmanTree(List<Node> nodes) {

        while (nodes.size() > 1) {

            Collections.sort(nodes);

            // 取权重最小的两棵树

            Node leftNode = nodes.get(0);

            Node rightNode = nodes.get(1);

            // 加入新树,移除旧树

            Node parent = new Node(null, leftNode.weight + rightNode.weight);

            parent.left = leftNode;

            parent.right = rightNode;

            nodes.remove(leftNode);

            nodes.remove(rightNode);

            nodes.add(parent);

        }

        return nodes.get(0);

    }

    
    /**
     * 接收字节数组
     * @param bytes 读入的文件中字符的ASCII码构成的字节数组
     * @return key为ASCII码,value为出现次数的结点构成的List<Node>集合
     */


    static List<Node> getNodes(byte[] bytes) {

        List<Node> nodes = new ArrayList<>();

        Map<Byte, Integer> counts = new HashMap<>();

        for (byte b : bytes) {

            Integer count = counts.get(b);

            if (count == null) {// 字符第一次出现

                counts.put(b, 1);

            } else {// 字符重复

                counts.put(b, count + 1);// 对应原来的值加一

            }

        }

        // 遍历map

        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {

            nodes.add(new Node(entry.getKey(), entry.getValue()));// 将键值对加到node数组中

        }

        return nodes;

    }

    static int Wpl(Node root) {

        int wpl = 0;

        if (root != null) {

            if (root.left != null && root.right != null) {

                wpl += root.weight;

            }

            // 递归遍历其左右子树            


            Wpl(root.left);

            Wpl(root.right);

        }

        return wpl;

    }

}