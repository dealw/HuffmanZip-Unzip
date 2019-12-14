package com.deal.HuffmanTree_code;

import com.deal.Tree.HuffmaTree;
import com.deal.Tree.HuffmanTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanTreeNode implements Comparable<HuffmanTreeNode>{
    Byte data;
    int weight;
    HuffmanTreeNode left;
    HuffmanTreeNode right;
//    声明一个静态遍历存储赫夫曼编码
    static String code="";
//    声明一个成员变量用于创建和存储对应字符的赫夫曼编码
    String codes="";

    public HuffmanTreeNode(Byte data, String codes) {
        this.data = data;
        this.codes = codes;
    }

    public void fontshow() {
        System.out.print(weight+" ");
        if(left!=null){
            left.fontshow();
        }
        if(right!=null){
            right.fontshow();
        }
    }
    @Override
    public String toString() {
        return "HuffmanTreeNode{" +
                "weight=" + weight +
                '}';
    }

    public HuffmanTreeNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanTreeNode left) {
        this.left = left;
    }

    public HuffmanTreeNode getRight() {
        return right;
    }

    public void setRight(HuffmanTreeNode right) {
        this.right = right;
    }

    public HuffmanTreeNode(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }
    @Override
    public int compareTo(HuffmanTreeNode o) {
        return o.weight-this.weight;
    }
    public void midshow() {
        if(left!=null){
            left.midshow();
        }
        System.out.print(weight+" ");
        if(right!=null){
            right.midshow();
        }
    }
    public Map<Byte,String> getcodes(Map<Byte,String> map){

        if(left!=null){
//           规定赫夫曼编码左子树是0
           code+="0";
            left.getcodes(map);
//            返回上一层时去掉已经加上去的编码
            code=code.substring(0,code.length()-1);
        }
//        如果没有子节点就是叶子节点将对应的字符和赫夫曼编码填入
        if(left==null&&right==null){
            codes=code;
            map.put(data,codes);
        }
        if(right!=null){
//            规定赫夫曼编码左子树是1
            code+="1";
            right.getcodes(map);
            code=code.substring(0,code.length()-1);
        }
        return map;
    }

    public Map<Byte,String> getcode() {
        Map<Byte,String> map=new HashMap<>();
        Map<Byte,String> getcodes = getcodes(map);
        return getcodes;
    }
}
