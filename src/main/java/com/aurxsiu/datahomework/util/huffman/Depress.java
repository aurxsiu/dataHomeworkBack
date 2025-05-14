package com.aurxsiu.datahomework.util.huffman;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Depress {

    static int endLen;

    public static void main(String[] args) {

        String[] zipfiles = {

                "D:\\123.zip"

        };

        String[] dstfiles = {

                "D:\\1234.txt"

        };

        for (int i = 0; i < zipfiles.length; i++) {

            System.out.println("解压文本文件中...");

            unZipFile(zipfiles[i], dstfiles[i]);

            System.out.println("解压成功!");

        }


    }

    public static byte[] unZipFile(File targetFile) {
        InputStream is = null;

        ObjectInputStream ois = null;

        OutputStream os = null;

        try {

            is = new FileInputStream(targetFile);

            ois = new ObjectInputStream(is);

            // 对象的反序列化,从文件中读取对象

            byte[] huffmanBytes = (byte[]) ois.readObject();

            Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();

            endLen = (int) ois.readObject();

            return huffmanUnzip(huffmanCodes, huffmanBytes);

        } catch (Exception e) {

            throw new RuntimeException(e);

        } finally {

            try {

                os.close();

                ois.close();

                is.close();

            } catch (Exception e2) {

                System.out.println(e2.getMessage());

            }

        }
    }

    /**
     * @param zipFile 压缩后编码文件路径
     * @param dstFile 解压文件路径
     */
    @SuppressWarnings("unchecked")
    public static void unZipFile(String zipFile, String dstFile) {

        InputStream is = null;

        ObjectInputStream ois = null;

        OutputStream os = null;

        try {

            is = new FileInputStream(zipFile);

            ois = new ObjectInputStream(is);

            // 对象的反序列化,从文件中读取对象

            byte[] huffmanBytes = (byte[]) ois.readObject();

            Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();

            endLen = (int) ois.readObject();

            byte[] bytes = huffmanUnzip(huffmanCodes, huffmanBytes); // 原文件字符构成的字节数组

            os = new FileOutputStream(dstFile);

            os.write(bytes); // 写入文件

        } catch (Exception e) {

            System.out.println(e.getMessage());

        } finally {

            try {

                os.close();

                ois.close();

                is.close();

            } catch (Exception e2) {

                System.out.println(e2.getMessage());

            }

        }

    }

    /**
     * 哈夫曼解压
     *
     * @param huffmanCodes 哈夫曼编码表
     * @param huffmanBytes 压缩后的字节数组
     * @return 压缩前的字节数组
     */

    static byte[] huffmanUnzip(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < huffmanBytes.length; i++) {

            byte b = huffmanBytes[i];

            boolean flag = (i == huffmanBytes.length - 1);// 标记是否到编码的最后一位

            stringBuilder.append(byteToBitString(!flag, b));

        }

        // 解码,反向编码表

        HashMap<String, Byte> map = new HashMap<>();

        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {

            map.put(entry.getValue(), entry.getKey());

        }

        // 根据编码扫描到对应的ASCLL码对应的字符

        List<Byte> list = new ArrayList<>();

        for (int i = 0; i < stringBuilder.length(); ) {

            int count = 1;

            boolean flag = true;

            Byte b = null;

            while (flag) {

                String key = stringBuilder.substring(i, i + count);

                b = map.get(key);

                if (b == null) {// 如果在hash表中找不到对应的编码,增加子串的长度,逐个比对

                    count++;

                } else {// 找到了，退出循环

                    flag = false;

                }

            }

            list.add(b);// 将编码对应的字节入list

            i += count;// 增加在字符串中的索引

        }

        byte b[] = new byte[list.size()];// 将list中的字节转换为字节数组

        for (int i = 0; i < b.length; i++) {

            b[i] = list.get(i);

        }

        return b;

    }

    /**
     * @param flag 标志是否为最后一组字节,如果为true则不用增加最高位
     * @param b    哈夫曼编码表中 哈夫曼编码对应的字符经压缩后的 字节
     * @return
     */

    static String byteToBitString(boolean flag, byte b) {

        int temp = b;// 将b转换为int

        temp |= 256;

        String str = Integer.toBinaryString(temp);// 返回的是temp对应的二进制补码

        if (flag || (flag == false && endLen == 0)) { // 防止刚好处理完时的空指针异常

            // 字符串的截取，只拿后八位

            return str.substring(str.length() - 8);

        } else {

            // 不满8bit有多少位拿多少位

            return str.substring(str.length() - endLen);

        }

    }

}
