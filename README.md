# -利用哈夫曼编码算法实现压缩与解压（练习版）
利用赫夫曼压缩文件的核心思想是“压缩”压缩体现在将八个二进制位变成一个字节存储如11101001转换成字节后是按照补码存储则变成10010111第一位为符号位，此时实际存储在
字节数组中的是00000011对应的3。解压缩的时候将3取出转为int类型也是3但是二进制位只有11前面不会用0补齐负数的时候前面都是用1补齐，所以要按位或256,256实际是100000000
所以按位或完之后11就变成100000011我们只需要取后八位就变成00000011了，与存入的二进制位相匹配，最后将字节数组再通过流输出就完成解压了。