package com.test;
/**
 * תȦ����N*Nά����
 * ��ͬ������ֻ�����a[][]���� 
 * @author wangyuyuan
 *  �������� 
 *          {{1,2,3},{4,5,6},{7,8,9}}
 *          {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}}
 *          {{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}}
 *{{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18},{19,20,21,22,23,24},{25,26,27,28,29,30},{31,32,33,34,35,36}}          
 *          
 */

public class RenRenArrayTest {
	public static void main(String[] args){
		int a[][] =  {{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}};
		//��ά����b���ٳɺ�aһ���Ĵ�С ����������˾�b[i][j]Ϊ1 û��������Ϊ0
		int b[][] = new int[a.length][a.length];
		//��ʼ��b����Ϊ0
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a.length;j++){
				b[i][j]=0;
			}
		}
		//���ڼ�¼�������ĸ���
		int count = 1;
		int max = a.length*a.length;
		int i=0,j=0;
		System.out.print(a[0][0]+" ");
		b[0][0] = 1;
		while(count<max){
			while((j<a.length-1)){
				if(b[i][j+1]==1)//̽��˷�����ǰ��ı�������
					break;//�������˾�ת��
				b[i][j++]=1;
				System.out.print(a[i][j]+" ");
				count++;
			}
			while(i<a.length-1){
				if(b[i+1][j]==1)
					break;//�������˾�ת��
				b[i++][j]=1;
				System.out.print(a[i][j]+" ");
				count++;
			}
			while(j>0){
				if(b[i][j-1]==1)
					break;//�������˾�ת��
				b[i][j--]=1;
				System.out.print(a[i][j]+" ");
				count++;
			}
			while(i>0){
				if(b[i-1][j]==1)
					break;//�������˾�ת��
				b[i--][j]=1;
				System.out.print(a[i][j]+" ");
				count++;
			}
		}
	}
}
