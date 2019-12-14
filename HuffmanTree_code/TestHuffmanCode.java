package com.deal.HuffmanTree_code;
import com.deal.Tree.HuffmanTree;
import java.io.*;
import java.util.*;
public class TestHuffmanCode {
    public static void main(String[] args) {
//        String arr="can you as can a canner can a car.";
//        byte[] bytes=arr.getBytes();
////        进行赫夫曼压缩
//        HuffmanTreeNode huffmantreenode = huffmanzip(bytes);
//        huffmantreenode.midshow();
//        System.out.println();
//        huffmantreenode.fontshow();
//        System.out.println();
//        Map<Byte,String> getcode = huffmantreenode.getcode();
////        打印赫夫曼编码
//        for(Byte hfn:getcode.keySet()){
//            char code=(char) Integer.parseInt(Byte.toString(hfn));
//            System.out.print(code+":"+getcode.get(hfn)+"    ");
//        }
////        将字符串转换成哈夫曼编码
//        String huffmancode = getHuffmancode(getcode,bytes);
////        解码输出字符串
//        System.out.println(getstr(getcode,huffmancode));
//        压缩文件
        String src="1.png";
        String dst="2.zip";
        try {
            HuffmanFileZip(src,dst);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        解压文件
//        String src="2.zip";
//        String dst="3.png";
//        try {
//            unZip(src,dst);
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }
    private static void unZip(String src, String dst) throws IOException, ClassNotFoundException {
        InputStream is=new FileInputStream(src);
        ObjectInputStream osi=new ObjectInputStream(is);
        String strcodes=(String) osi.readObject();
        Map<Byte,String> map=(Map<Byte,String>) osi.readObject();
        String[] strcode=strcodes.split(" ");
        is.close();
        osi.close();
        byte[] bytes = getbytes(strcode, map);
        OutputStream os=new FileOutputStream(dst);
        os.write(bytes);
        os.close();
    }
    private static byte[] getbytes(String[] strcode, Map<Byte, String> map) {
        byte[] by=to_byte(strcode);
        List<Byte> list=new ArrayList<>();
        for(byte bys:by){
           for(byte b:map.keySet()){
               if(Byte.toString(bys).equals(map.get(b))){
                   list.add(b);
               }
           }
        }
        byte[] dstbyte=new byte[list.size()];
        for(int i=0;i<dstbyte.length;i++){
            dstbyte[i]=list.get(i);
        }
        return dstbyte;
    }
    public static byte[] to_byte(String[] strs) {
        byte[] bytes=new byte[strs.length];
        for (int i=0; i<strs.length; i++) {
            bytes[i]=Byte.parseByte(strs[i]);
        }
        return bytes;
    }
    private static void HuffmanFileZip(String src, String dst)throws IOException {
        InputStream in=new FileInputStream(src);
        byte[] b=new byte[in.available()];
        in.read(b);
        in.close();
        HuffmanTreeNode huffmanzip = huffmanzip(b);
        Map<Byte, String> getcode = huffmanzip.getcode();
        OutputStream os=new FileOutputStream(dst);
        ObjectOutputStream oss=new ObjectOutputStream(os);
        String huffmancode = getHuffmancode(getcode, b);
        oss.writeObject(huffmancode);
        oss.writeObject(getcode);
        oss.close();
        os.close();
    }
    private static String getstr(Map<Byte, String> getcode, String huffmancode) {
        String str="";
        String [] strcode=huffmancode.split(" ");
        System.out.println();
        for(String s:strcode){
            for(Byte b:getcode.keySet()){
                char code=(char) Integer.parseInt(Byte.toString(b));
                if(s.equals(getcode.get(b))){
                    str+=code;
                }
            }
        }
        return str;
    }
    private static String getHuffmancode(Map<Byte, String> getcode, byte[] bytes) {
        String str="";
        for(Byte by:bytes){
            for(Byte hfn:getcode.keySet()){
                if(by.equals(hfn)){
                    str+=getcode.get(hfn)+" ";
                }
            }
        }
        return str;
    }
//   根据赫夫曼编码获取到字符串

//用节点构建赫夫曼树
    private static HuffmanTreeNode huffmanzip(byte[] bytes) {
//        获取到节点的list
        List<HuffmanTreeNode> list=creathuffmanNode(bytes);
//        构造一个赫夫曼树
        while(list.size()>1){
//           先由大到小排序
            Collections.sort(list);
//           在找出最小的两位数分别作为左右节点，再计算出和权值
            HuffmanTreeNode left=list.get(list.size()-1);
            HuffmanTreeNode right=list.get(list.size()-2);
//           创造新节点权值为左右两节点之和，然后加入list即去掉两个加入一个这样循环才能结束
            HuffmanTreeNode parent=new HuffmanTreeNode(null,left.weight+right.weight);
            list.add(parent);
//          将刚才那两个最小的节点变成parent节点的左右节点
            parent.setLeft(left);
            parent.setRight(right);
//          移除这个两个节点
            list.remove(left);
            list.remove(right);
        }
         return list.get(0);
    }
//  统计每个字符出现的次数并制作成赫夫曼树的节点
    private static List<HuffmanTreeNode> creathuffmanNode(byte[] bytes) {
//        声明存放节点的list
        List<HuffmanTreeNode> list=new ArrayList<>();
//        声明存放每个字符出现多少次的map集合
        Map<Byte,Integer> map=new HashMap<>();
//        统计每个字符出现的次数
        for(Byte by:bytes){
            Integer count=map.get(by);
//            如果为空则为第一次出现
            if(count==null){
                map.put(by,1);
            }
//            否则在原有数量上加一即可
            else {
                map.put(by,count+1);
            }
        }
//        将键值对取出来构造成节点并存入list
        for(Byte b:map.keySet()){
            list.add(new HuffmanTreeNode(b,map.get(b)));
        }
        return list;
    }
}
