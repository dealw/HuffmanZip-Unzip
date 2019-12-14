package com.deal.HuffmanTree_code;
import java.io.*;
import java.util.*;
import java.lang.*;

public class HuffmanZip {
    public static void main(String[] args)  {
        //        压缩文件
//        String src="1.png";
//        String dst="2.zip";
//        try {
//            HuffmanFileZip(src,dst);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        解压文件
        String src="2.zip";
        String dst="3.png";
        try {
            unZip(src,dst);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static void unZip(String src, String dst) throws IOException, ClassNotFoundException {
        InputStream is=new FileInputStream(src);
        ObjectInputStream osi=new ObjectInputStream(is);
        byte[] strcodes=(byte[]) osi.readObject();
        Map<Byte,String> map=(Map<Byte,String>) osi.readObject();
        is.close();
        osi.close();
        byte[] bytes = decode(strcodes, map);
        OutputStream os=new FileOutputStream(dst);
        os.write(bytes);
        os.close();
    }

    private static byte[] decode(byte[] strcodes, Map<Byte, String> map) {
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<strcodes.length;i++){
            byte by=strcodes[i];
            boolean flag=(i==strcodes.length-1);
            sb.append(getunzipcode(!flag, by));
        }
        Map< String,Byte> map1=new HashMap<>();
        for(Map.Entry<Byte, String> entry:map.entrySet()){
            map1.put(entry.getValue(),entry.getKey());
        }
        List<Byte> list=new ArrayList<>();
        for(int j=0;j<sb.length();){
            Byte b=null;
            int count=1;
            boolean f=false;
            while(!f){
                String str=sb.substring(j,j+count);
                b=map1.get(str);
                if(b==null){
                    count++;
                }else {
                    f=true;
                }
            }
            list.add(b);
            j+=count;
        }
        byte[] byts=new byte[list.size()];
        for(int k=0;k<byts.length;k++){
            byts[k]=list.get(k);
        }
        return byts;
    }

    public static String getunzipcode(boolean flag,byte by){
            int temp=by;
            if(flag){
                temp|=256;
            }
            String str=Integer.toBinaryString(temp);
            if(flag){
                return str.substring(str.length()-8);
            }else {
                return str;
            }
    }
    private static void HuffmanFileZip(String src, String dst) throws IOException {
        InputStream in=new FileInputStream(src);
        byte[] b=new byte[in.available()];
        in.read(b);
        in.close();
        HuffmanTreeNode huffmanzip = huffmanzip(b);
        Map<Byte,String> codecart = huffmanzip.getcode();
        byte[] huffmancode = getHuffmancode(codecart, b);
        OutputStream os=new FileOutputStream(dst);
        ObjectOutputStream oss=new ObjectOutputStream(os);
        oss.writeObject(huffmancode);
        oss.writeObject(codecart);
        oss.close();
        os.close();
    }
    private static byte[] getHuffmancode(Map<Byte, String> getcode, byte[] bytes) {
        StringBuilder sb=new StringBuilder();
        for(Byte by:bytes){
            sb.append(getcode.get(by));
        }
        int len=0;
        if(sb.length()%8==0){
            len=sb.length()/8;
        }else {
            len=sb.length()/8+1;
        }
        byte[] by=new byte[len];
        int index=0;
        for(int i=0;i<sb.length();i+=8){
            String str;
            if(i+8>sb.length()){
                str=sb.substring(i);
            }else {
                str=sb.substring(i,i+8);
            }
            byte b=(byte)Integer.parseInt(str,2);
            by[index]=b;
            index++;
//            System.out.println(b);
        }
        return by;
    }
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
