package com.aurxsiu.datahomework.util;

import java.io.*;
import java.util.*;

public class HuffmanCodeUtil {
    public static class Node implements Comparable<Node>{

        Byte data;
        /**
         * 节点权值
         */
        int weight;
        /**
         * 指向左子结点
         */
        Node left;
        /**
         * 指向右子节点
         */
        Node right;

        public Node(Byte data,int weight){
            this.weight = weight;
            this.data = data;

        }

        /**
         * 前序遍历,先输出父结点，再遍历左子树和右子树
         */
        public  void preOrder(){

            System.out.println(this);

            if(null != this.left){
                this.left.preOrder();
            }
            if(null != this.right){
                this.right.preOrder();
            }
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }

        @Override
        public  String toString(){
            return "Node[data="+data+"weight="+weight+"]";
        }



    }

    public static class HuffmanCode {

        static Map<Byte, String> huffmanCodes = new HashMap();
        static StringBuilder stringBuilder = new StringBuilder();


        public static void main(String[] args) {
            String srcFile = "E:\\code\\123.txt";
            String zipFile = "E:\\code\\123\\123.zip";
            String dstFile = "E:\\code\\123\\123.txt";
            zipFile(srcFile, zipFile);
            System.out.println("压缩成功!");
            unZipFile(zipFile, dstFile);
            System.out.println("解压成功!");
        }


        /**
         * 文件解压
         *
         * @param zipFile
         * @param dstFile
         */
        public static void unZipFile(String zipFile, String dstFile) {
            InputStream is = null;
            ObjectInputStream ois = null;
            FileOutputStream os = null;

            try {
                is = new FileInputStream(zipFile);
                ois = new ObjectInputStream(is);
                byte[] huffmanBytes = (byte[]) ois.readObject();
                Map<Byte, String> huffmanCodes = (Map) ois.readObject();
                byte[] bytes = decode(huffmanCodes, huffmanBytes);
                os = new FileOutputStream(dstFile);
                os.write(bytes);
            } catch (Exception var16) {
                System.out.println(var16.getMessage());
            } finally {
                try {
                    os.close();
                    ois.close();
                    is.close();
                } catch (Exception var15) {
                    System.out.println(var15.getMessage());
                }

            }

        }

        /**
         * 文件压缩
         *
         * @param srcFile
         * @param dstFile
         */
        public static void zipFile(String srcFile, String dstFile) {
            OutputStream os = null;
            ObjectOutputStream oos = null;
            FileInputStream is = null;

            try {
                is = new FileInputStream(srcFile);
                byte[] b = new byte[is.available()];
                is.read(b);
                byte[] huffmanBytes = huffmanZip(b);
                os = new FileOutputStream(dstFile);
                oos = new ObjectOutputStream(os);
                oos.writeObject(huffmanBytes);
                oos.writeObject(huffmanCodes);
            } catch (Exception var15) {
                System.out.println(var15.getMessage());
            } finally {
                try {
                    is.close();
                    oos.close();
                    os.close();
                } catch (Exception var14) {
                    System.out.println(var14.getMessage());
                }

            }

        }


        /**
         * 哈夫曼编码解码
         *
         * @param huffmanCodes
         * @param huffmanBytes
         * @return
         */
        private static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < huffmanBytes.length; ++i) {
                byte b = huffmanBytes[i];
                boolean flag = i == huffmanBytes.length - 1;
                stringBuilder.append(byteToBitString(!flag, b));
            }

            Map<String, Byte> map = new HashMap();
            for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
                map.put(entry.getValue(), entry.getKey());
            }

            List<Byte> list = new ArrayList();


            for (int i = 0; i < stringBuilder.length(); ) {
                int count = 1;
                boolean flag = true;
                Byte b = null;

                while (flag) {
                    String key = stringBuilder.substring(i, i + count);
                    b = (Byte) map.get(key);
                    if (b == null) {
                        ++count;
                    } else {
                        flag = false;
                    }
                }

                list.add(b);
                i += count;
            }

            byte[] b = new byte[list.size()];

            for (int i = 0; i < b.length; ++i) {
                b[i] = (Byte) list.get(i);
            }

            return b;
        }

        /**
         * 将一个byte转成一个二进制字符串
         *
         * @param flag
         * @param b
         * @return
         */
        private static String byteToBitString(boolean flag, byte b) {
            int temp = b;
            if (flag) {
                temp = b | 256;
            }

            String str = Integer.toBinaryString(temp);
            return flag ? str.substring(str.length() - 8) : str;
        }

        /**
         * 哈夫曼压缩
         *
         * @param bytes
         * @return
         */
        private static byte[] huffmanZip(byte[] bytes) {
            List<Node> nodes = getNodes(bytes);
            Node huffmanTreeRoot = createHuffmanTree(nodes);
            Map<Byte, String> huffmanCodes = getCodes(huffmanTreeRoot);
            byte[] huffmanCodeBytes = zip(bytes, huffmanCodes);
            return huffmanCodeBytes;
        }

        /**
         * 压缩
         *
         * @param bytes
         * @param huffmanCodes
         * @return
         */
        private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
            StringBuilder stringBuilder = new StringBuilder();

            for (byte b : bytes) {
                stringBuilder.append(huffmanCodes.get(b));
            }


            int len;
            if (stringBuilder.length() % 8 == 0) {
                len = stringBuilder.length() / 8;
            } else {
                len = stringBuilder.length() / 8 + 1;
            }

            byte[] huffmanCodeBytes = new byte[len];
            int index = 0;

            for (int i = 0; i < stringBuilder.length(); i += 8) {
                String strByte;
                if (i + 8 > stringBuilder.length()) {
                    strByte = stringBuilder.substring(i);
                } else {
                    strByte = stringBuilder.substring(i, i + 8);
                }

                huffmanCodeBytes[index] = (byte) Integer.parseInt(strByte, 2);
                ++index;
            }

            return huffmanCodeBytes;
        }

        /**
         * 获取哈夫曼编码
         *
         * @param root
         * @return
         */
        private static Map<Byte, String> getCodes(Node root) {
            if (root == null) {
                return null;
            } else {
                getCodes(root.left, "0", stringBuilder);
                getCodes(root.right, "1", stringBuilder);
                return huffmanCodes;
            }
        }

        /**
         * 获取哈夫曼编码
         *
         * @param node
         * @param code
         * @param stringBuilder
         */
        private static void getCodes(Node node, String code, StringBuilder stringBuilder) {
            StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
            stringBuilder2.append(code);
            if (node != null) {
                if (node.data == null) {
                    getCodes(node.left, "0", stringBuilder2);
                    getCodes(node.right, "1", stringBuilder2);
                } else {
                    huffmanCodes.put(node.data, stringBuilder2.toString());
                }
            }

        }

        /**
         * 前序遍历
         *
         * @param root
         */
        private static void preOrder(Node root) {
            if (root != null) {
                root.preOrder();
            } else {
                System.out.println("赫夫曼树为空");
            }

        }

        /**
         * 获取list
         *
         * @param bytes
         * @return
         */
        private static List<Node> getNodes(byte[] bytes) {
            ArrayList<Node> nodes = new ArrayList();
            //统计每个byte出现的次数
            Map<Byte, Integer> counts = new HashMap();
            for (byte b : bytes) {
                Integer count = (Integer) counts.get(b);
                if (count == null) {
                    counts.put(b, 1);
                } else {
                    counts.put(b, count + 1);
                }
            }


            for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
//                nodes.add(new Node(entry.getKey(),entry.getValue()));
                nodes.add(new Node(entry.getKey(), entry.getValue()));
            }

            return nodes;
        }

        /**
         * 创建哈夫曼树
         *
         * @param nodes
         * @return
         */
        private static Node createHuffmanTree(List<Node> nodes) {

            while (nodes.size() > 1) {
                Collections.sort(nodes);
                Node leftNode = (Node) nodes.get(0);
                Node rightNode = (Node) nodes.get(1);
                Node parent = new Node((Byte) null, leftNode.weight + rightNode.weight);
                parent.left = leftNode;
                parent.right = rightNode;
                nodes.remove(leftNode);
                nodes.remove(rightNode);
                nodes.add(parent);
            }

            return (Node) nodes.get(0);
        }
    }
}
