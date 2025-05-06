package com.luotao.container;
import java.util.Arrays;

/**
 * @Classname ArrayDemo
 * @Description 数组
 * - 数组的元素相同数据类型、是有序的、可重复的
 * - 创建后长度和类型不可变
 * 缺点：(后来设计出集合来规避这些缺点)
 * - 底层基于紧密结构(即顺序结构)导致增删效率低
 * - 数组中实际存储的元素数量没有提供对应方法来获取
 * @Version 1.0.0
 * @Date 2025/2/25 20:24
 * @Author LuoTao
 */
public class ArrayDemo {
    public static void main(String[] args) {
        arrayInit();
        arrayBubbleSort();
        arrayReverse();
        arrayTwo();
    }

    /**
     * @Description 创建数组的三种写法
     * 有序。
     * 创建后长度不可变。
     * 元素的数据类型相同。
     * @Author LuoTao
     * @Date 2025/2/25 20:44
     **/
    static void arrayInit(){

        int[] arrA;
        arrA= new int[5];

        int[] arrB={1,2,3,4,5};// 静态初始化
        int[] arrC = new int[]{6,7};// 静态初始化
        System.out.println(arrA.length); //数组长度
        arrB=arrC; //true 引用类型: arrB和arrC指向同一块堆内存地址
        System.out.println(arrB==arrC);
        System.out.println(Arrays.toString(arrB));

    }
    /**
     * @Description: 冒泡排序
     * @Author: LuoTao
     * @Date: 2025-02-25 20:47:54
     **/
    static void arrayBubbleSort(){
        //重复地遍历待排序的序列，依次比较相邻的两个元素并交换顺序不对的元素，直到整个序列有序为止。
        int[] ages = {5,4,3,2,1};
        System.out.println(Arrays.toString(ages));
        //外层循环的次数决定了需要进行多少轮比较和交换。(第0轮比较，未排序的有length个，需要两两比较length-1次；第1轮比较，为排序的有length-1个，需要两两比较length-1-1次
        for (int i=0;i<ages.length-1;i++){
            System.out.println("第"+i+"轮比较,未排序的有" +(ages.length-i) + "个,需要两两交换" + (ages.length-i-1) + "次(每轮循环结束后都排好了一位):"   );
            for (int j=0;j<ages.length-1-i;j++){//在每一轮外层循环结束时，最大的元素会被移动到数组的末尾位置，因此不需要再对其进行比较。
                if( ages[j] >ages[j+1] ){
                    // 交换顺序
                    int tmp = ages[j];
                    ages[j] = ages[j+1];
                    ages[j+1] = tmp;
                }
                System.out.println(Arrays.toString(ages));
            }
        }


//        Arrays.sort(ages); //默认升序 [1, 2, 3, 4, 5]
    }

    /**
     * @Description: 数组元素颠倒顺序
     * 可以想象成一个队伍中的人在换位子，站在队头第一个人和队尾最后一个人交换位置，第二个人和倒数第二个人交换位置，以此类推，直到中间位置为止
     * @Author: LuoTao
     * @Date: 2025-03-03 11:02:24
     **/
    static void arrayReverse(){
        int[] ages = {5,4,3,2,1};
        for (int i=0;i<ages.length/2;i++){
            // 保存当前元素到临时变量
            int tmp = ages[i];
            // 将当前元素替换为对称位置的元素
            ages[i] = ages[ages.length-1-i];
            // 将对称位置的元素替换为临时变量中的元素，完成两个元素的交换
            ages[ages.length-1-i] = tmp;
            System.out.println("第" +i+"轮比较" + Arrays.toString(ages) );
        }
    }

    /**
     * @Description: 二维数组计算个人平均分和各科平均分
     * @Author: LuoTao
     * @Date: 2025-03-13 11:24:13
     **/
    static void arrayTwo(){
        float[][] score = {{100f, 99f, 98.5f}, {80f, 95f, 92f}, {100f, 95.5f, 95f}};
        float sumScore;// 个人总分
        float avgScore;// 个人平均分

        float sum_chinese=0.0f;//语文总分
        float sum_math=0.0f;//数学总分
        float sum_english=0.0f;//英语总分

        for (int row = 0; row < score.length; row++) {
            sumScore=0.0f;
            avgScore=0.0f;

            for (int col = 0; col < score[row].length; col++){
                sumScore += score[row][col];
                avgScore=sumScore/score[row].length;
            }
            System.out.println("第" + (row+1) + "个学生平均分：" + avgScore);

            sum_chinese +=score[row][0];
            sum_math +=score[row][1];
            sum_english +=score[row][2];
        }
        System.out.println("语文平均分：" + sum_chinese/score.length);
        System.out.println("数学平均分：" + sum_math/score.length);
        System.out.println("英语平均分：" + sum_english/score.length);
    }

}


