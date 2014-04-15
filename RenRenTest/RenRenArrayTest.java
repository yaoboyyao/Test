package com.test;
/**
 * 转圈遍历N*N维数组
 * 不同的数组只需更改a[][]即可 
 * @author wangyuyuan
 *  测试数据 
 *          {{1,2,3},{4,5,6},{7,8,9}}
 *          {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}}
 *          {{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}}
 *{{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18},{19,20,21,22,23,24},{25,26,27,28,29,30},{31,32,33,34,35,36}}          
 *          
 */

public class RenRenArrayTest {
	public static void main(String[] args){
		int a[][] =  {{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}};
		//二维数组b开辟成和a一样的大小 如果遍历过了就b[i][j]为1 没遍历过就为0
		int b[][] = new int[a.length][a.length];
		//初始化b数组为0
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a.length;j++){
				b[i][j]=0;
			}
		}
		//用于记录遍历过的个数
		int count = 1;
		int max = a.length*a.length;
		int i=0,j=0;
		System.out.print(a[0][0]+" ");
		b[0][0] = 1;
		while(count<max){
			while((j<a.length-1)){
				if(b[i][j+1]==1)//探测此方向上前面的遍历过吗
					break;//遍历过了就转向
				b[i][j++]=1;
				System.out.print(a[i][j]+" ");
				count++;
			}
			while(i<a.length-1){
				if(b[i+1][j]==1)
					break;//遍历过了就转向
				b[i++][j]=1;
				System.out.print(a[i][j]+" ");
				count++;
			}
			while(j>0){
				if(b[i][j-1]==1)
					break;//遍历过了就转向
				b[i][j--]=1;
				System.out.print(a[i][j]+" ");
				count++;
			}
			while(i>0){
				if(b[i-1][j]==1)
					break;//遍历过了就转向
				b[i--][j]=1;
				System.out.print(a[i][j]+" ");
				count++;
			}
		}
	}
}
